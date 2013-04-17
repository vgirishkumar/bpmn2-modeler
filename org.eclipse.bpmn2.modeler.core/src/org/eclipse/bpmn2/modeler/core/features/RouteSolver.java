package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class RouteSolver {

	protected final IGaService gaService = Graphiti.getGaService();
	static final IPeService peService = Graphiti.getPeService();
	static final int topMargin = 50;
	static final int bottomMargin = 50;
	static final int leftMargin = 50;
	static final int rightMargin = 50;

	IFeatureProvider fp;
	List<ContainerShape> allShapes;
	ContainerShape source; 
	ContainerShape target;
	int top, left, bottom, right;
	boolean rotate = false;
	
	protected static class Aisle {
		protected final static List<Aisle> EMPTY_LIST = new ArrayList<Aisle>();
		private Rectangle rect;
		private List<Aisle> leftAdjacent;
		private List<Aisle> rightAdjacent;
		public String type;
		private ContainerShape shape;
		public static enum Adjacence { LEFT, TOP, BOTTOM, RIGHT, NONE };
		
		public Aisle(String type, int x, int y, int width, int height) {
			this.type = type;
			rect = new Rectangle(x,y,width,height);
		}
		
		public Aisle(Rectangle r) {
			rect = new Rectangle(r);
		}
		
		public Adjacence adjacent(Aisle node) {
			return adjacent(node.rect);
		}
		
		public Adjacence adjacent(Rectangle other) {
			if (rect.y==other.bottom())
				return Adjacence.TOP;
			if (rect.x==other.right())
				return Adjacence.LEFT;
			if (rect.right()==other.x)
				return Adjacence.RIGHT;
			if (rect.bottom()==other.y)
				return Adjacence.BOTTOM;
			return Adjacence.NONE;
		}
		
		public void addLeft(Aisle node) {
			if (leftAdjacent==null)
				leftAdjacent = new ArrayList<Aisle>();
			leftAdjacent.add(node);
			if (node.rightAdjacent==null)
				node.rightAdjacent = new ArrayList<Aisle>();
			node.rightAdjacent.add(this);
		}
		
		public boolean hasLeft() {
			return leftAdjacent!=null && leftAdjacent.size()>0;
		}
		
		public List<Aisle> getLeft() {
			if (hasLeft())
				return leftAdjacent;
			return EMPTY_LIST;
		}
		
		public void addRight(Aisle node) {
			if (rightAdjacent==null)
				rightAdjacent = new ArrayList<Aisle>();
			rightAdjacent.add(node);
			if (node.leftAdjacent==null)
				node.leftAdjacent = new ArrayList<Aisle>();
			node.leftAdjacent.add(this);
		}
		
		public boolean hasRight() {
			return rightAdjacent!=null && rightAdjacent.size()>0;
		}
		
		public List<Aisle> getRight() {
			if (hasRight())
				return rightAdjacent;
			return EMPTY_LIST;
		}

		public int getX() {
			return rect.x;
		}

		public int getY() {
			return rect.y;
		}

		public void setX(int i) {
			rect.x = i;
		}
		
		public void setY(int i) {
			rect.y = i;
		}
		
		public int getWidth() {
			return rect.width;
		}

		public int getHeight() {
			return rect.height;
		}
		
		public void setWidth(int i) {
			rect.width = i;
		}

		public void setHeight(int i) {
			rect.height = i;
		}

		public void setShape(PictogramElement shape) {
			this.shape = (ContainerShape)shape;
		}
		
		public ContainerShape getShape() {
			return shape;
		}
	}
	
	public RouteSolver(IFeatureProvider fp, List<ContainerShape> allShapes, ContainerShape source, ContainerShape target) {
		this.fp = fp;
		this.allShapes = new ArrayList<ContainerShape>();
		this.allShapes.addAll(allShapes);
		this.source = source;
		this.target = target;
		
		initialize();
	}

	protected void clear() {
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		List<ContainerShape> delete = new ArrayList<ContainerShape>();
		TreeIterator iter = diagram.eAllContents();
		while (iter.hasNext()) {
			Object o = iter.next();
			if (o instanceof ContainerShape) {
				ContainerShape s = (ContainerShape)o;
				if (peService.getPropertyValue(s, "CONNECTION_ROUTING_AISLE")!=null) {
					delete.add(s);
				}
			}
		}
		for (ContainerShape s : delete) {
			DeleteContext context = new DeleteContext(s);
			DeleteAisleFeature feature = new DeleteAisleFeature(fp);
			feature.delete(context);
		}

	}
	
	public boolean solve() {
		return true;
	}
	
	public boolean initialize() {
		
		clear();
		
		if (allShapes.size()<2)
			return false;
		
		Rectangle r = calculateDiagramBounds();
		sortAllShapes();
		top = r.y;
		left = r.x;
		bottom = top + r.height;
		right = left + r.width;
		List<Aisle> verticalAisles = calculateAisles();
		drawAisles(verticalAisles);
//		drawAisleConnections(verticalAisles);

		rotate = true;
		r = calculateDiagramBounds();
		sortAllShapes();
		top = r.y;
		left = r.x;
		bottom = top + r.height;
		right = left + r.width;
		List<Aisle> horizontalAisles = calculateAisles();
		// rotate the horizontal void nodes
		for (Aisle node : horizontalAisles) {
			rotateRectangle(node.rect);
		}

		drawAisles(horizontalAisles);
//		drawAisleConnections(horizontalAisles);
		
		return true;
	}

	protected Rectangle calculateDiagramBounds() {
		// find bounding rectangle that contains all shapes
		int left = Integer.MAX_VALUE;
		int right = Integer.MIN_VALUE;
		int top = Integer.MAX_VALUE;
		int bottom = Integer.MIN_VALUE;
		int x, y;
		for (ContainerShape s : allShapes) {
			Rectangle r = getBounds(s);
			x = r.x;
			if (x < left)
				left = x;
			x = r.right();
			if (x > right)
				right = x;
			y = r.y;
			if (y < top)
				top = y;
			y = r.bottom();
			if (y > bottom)
				bottom = y;
		} 

		left -= leftMargin;
		top -= topMargin;
		bottom += bottomMargin;
		right += rightMargin;
		
		return new Rectangle(left, top, right-left, bottom-top);
	}
	
	protected void drawAisles(List<Aisle> aisle) {
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		for (Aisle node : aisle) {
			AddContext context = new AddContext();
			context.setTargetContainer(diagram);
			context.setNewObject(node);
			context.setX(node.getX());
			context.setY(node.getY());
			context.setSize(node.getWidth(), node.getHeight());
			AddAisleFeature feature = new AddAisleFeature(fp);
			node.setShape(feature.add(context));
		}
	}
	
	protected void drawAisleConnections(List<Aisle> aisles) {
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		ContainerShape sourceShape;
		Anchor sourceAnchor;
		ContainerShape targetShape;
		Anchor targetAnchor;
		
		for (Aisle n1 : aisles) {
			for (Aisle n2 : aisles) {
				if (n1!=n2) {
					sourceShape = targetShape = null;
					switch (n1.adjacent(n2)) {
					case LEFT:
						n1.addLeft(n2);
						sourceShape = n1.getShape();
						targetShape = n2.getShape();
						break;
					case RIGHT:
						n1.addRight(n2);
						sourceShape = n1.getShape();
						targetShape = n2.getShape();
						break;
					case TOP:
					case BOTTOM:
					case NONE:
						break;
					}
					if (sourceShape!=null && targetShape!=null) {
						if (sourceShape.getAnchors().size()>0)
							sourceAnchor = sourceShape.getAnchors().get(0);
						else
							sourceAnchor = peService.createChopboxAnchor(sourceShape);

						if (targetShape.getAnchors().size()>0)
							targetAnchor = targetShape.getAnchors().get(0);
						else
							targetAnchor = peService.createChopboxAnchor(targetShape);
						AddConnectionContext context = new AddConnectionContext(sourceAnchor, targetAnchor);
						context.setTargetContainer(diagram);
						context.setNewObject(new Object[] {n1, n2});
						AddAisleConnectionFeature feature = new AddAisleConnectionFeature(fp);
//						feature.add(context);
					}
				}
			}
		}
	}
	
	protected List<Aisle> calculateAisles() {

		List<Aisle> aisle = new ArrayList<Aisle>();
		
		aisle.add(new Aisle("start", left, top, leftMargin, bottom-top));
		for (int i=0; i<allShapes.size(); ++i) {
			ContainerShape shape = allShapes.get(i);
			if (GraphicsUtil.getDebugText(shape).contains("Task_1")) {
				GraphicsUtil.debug = true;
			}
			else
				GraphicsUtil.debug = false;

			// get bounding rectangle for current shape
			Rectangle shapeBounds = getBounds(shape);
			// The rectangular region below the current shape will be sliced
			// into smaller rectangles (a.k.a. "aisle"). To do this we'll
			// create a horizontal slicer that keeps track of the location
			// and width of each void defined by the top edge of the current
			// shape, and the left/right edges of the shapes below it.
			Slice slice = new Slice(shapeBounds.x, shapeBounds.right());
			List<ContainerShape> below = getShapesBelow(shape);
			for (ContainerShape shapeBelow : below) {
				Rectangle shapeBelowBounds = getBounds(shapeBelow);
				if (slice.remove(shapeBelowBounds.x, shapeBelowBounds.right()) == 0)
					break;
			}
			
			List<Integer> cuts = slice.getCuts();
			int c1 = cuts.get(0);
			for (int ic=1; ic<cuts.size(); ++ic) {
				int c2 = cuts.get(ic);
				Aisle newAisle = new Aisle("below",c1,shapeBounds.y+shapeBounds.height,c2-c1,Integer.MIN_VALUE);
				
				for (ContainerShape shapeBelow : below) {
					Rectangle shapeBelowBounds = getBounds(shapeBelow);
					if (shapeBelowBounds.x<=c1 && c1<shapeBelowBounds.right()) {
						newAisle.setHeight(shapeBelowBounds.y - shapeBounds.y - shapeBounds.height);
						break;
					}
				}
				if (newAisle.getHeight()==Integer.MIN_VALUE) {
					newAisle.setHeight(bottom - shapeBounds.y - shapeBounds.height);
				}
				
				aisle.add(newAisle);
				c1 = c2;
			}

			// calculate aisle above the current shape
			slice = new Slice(shapeBounds.x, shapeBounds.right());
			List<ContainerShape> above = getShapesAbove(shape);
			for (ContainerShape shapeAbove : above) {
				Rectangle shapeAboveBounds = getBounds(shapeAbove);
				if (slice.remove(shapeAboveBounds.x, shapeAboveBounds.right()) == 0)
					break;
			}

			// Now we'll do the same thing for aisle above the current shape,
			// but only those that extend all the way to the top of the diagram.
			// We don't want any overlapping aisle.
			cuts = slice.getCuts();
			c1 = cuts.get(0);
			for (int ic=1; ic<cuts.size(); ++ic) {
				int c2 = cuts.get(ic);
				Aisle newAisle = new Aisle("above",c1,top,c2-c1,Integer.MIN_VALUE);
				
				for (ContainerShape shapeAbove : above) {
					Rectangle shapeAboveBounds = getBounds(shapeAbove);
					if (shapeAboveBounds.x<=c1 && c1<shapeAboveBounds.right()) {
						newAisle.setHeight(shapeAboveBounds.y - shapeBounds.y - shapeBounds.height);
						break;
					}
				}
				if (newAisle.getHeight()==Integer.MIN_VALUE) {
					newAisle.setHeight(shapeBounds.y - top);
					// only add the void if there is no shape above this region.
					aisle.add(newAisle);
				}
				
				c1 = c2;
			}

			Aisle a = getTrailingAisle(shape);
			if (a!=null)
				aisle.add(a);
		}
		aisle.add(new Aisle("end",right-rightMargin, top, rightMargin, bottom-top));
		
		return aisle;
	}
	
	protected Aisle getTrailingAisle(ContainerShape shape) {
		Rectangle shapeBounds = getBounds(shape);
		int x = shapeBounds.right();
		int size = allShapes.size();
		for (int i=0; i<size; ++i) {
			ContainerShape s = allShapes.get(i);
			if (s!=shape) {
				Rectangle b = getBounds(s);
				if (b.x<=x && x<b.right())
					return null;
			}
		}
		
		for (int i=0; i<size; ++i) {
			ContainerShape s = allShapes.get(i);
			if (s==shape) {
				for (int n=i+1; n<size; ++n) {
					ContainerShape nextShape = allShapes.get(n);
					Rectangle nextShapeBounds = getBounds(nextShape);
					if (nextShapeBounds.x>shapeBounds.right()) {
						return new Aisle("trailing",shapeBounds.right(),top, nextShapeBounds.x - shapeBounds.right(), bottom-top);
					}
				}
			}
		}
		return null;
	}

	protected List<ContainerShape> getShapesBelow(ContainerShape shape) {
		final Rectangle bounds = getBounds(shape);
		List<ContainerShape> shapes = new ArrayList<ContainerShape>();
		for (ContainerShape s : allShapes) {
			if (s!=shape) {
				Rectangle b = getBounds(s);
				if (b.x>bounds.right())
					break;
				int d = b.y - bounds.y;
				if (
						d>=0 && (
							(bounds.x<=b.x && b.x<=bounds.right()) ||
							(bounds.x<=b.right() && b.right()<=bounds.right()) ||
							(b.x<=bounds.x && bounds.right()<=b.right())
						)
							
				) {
					shapes.add(s);
				}
					
			}
		}
		// sort the rectangles by increasing distance from the bottom of the given shape
		Collections.sort(shapes, new Comparator<ContainerShape>() {
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				Rectangle b1 = getBounds(s1);
				Rectangle b2 = getBounds(s2);
				int d1 = b1.y - bounds.bottom();
				int d2 = b2.y - bounds.bottom();
				int i = d1 - d2;
				if (i==0) {
					i = b1.x - b2.x;
				}
				return i;
			}
		});
		
		for (int i=0; i<shapes.size(); ++i) {
			ContainerShape s = shapes.get(i);
			Rectangle b = getBounds(s);
			if (b.x<=bounds.x && b.right()>=bounds.right()) {
				++i;
				while (i<shapes.size()) {
					shapes.remove(i);
				}
			}
		}
		return shapes;
	}

	protected List<ContainerShape> getShapesAbove(ContainerShape shape) {
		final Rectangle bounds = getBounds(shape);
		List<ContainerShape> shapes = new ArrayList<ContainerShape>();
		for (ContainerShape s : allShapes) {
			if (s!=shape) {
				Rectangle b = getBounds(s);
				if (b.x>bounds.right())
					break;
				int d = b.y - bounds.y;
				if (
						d<=0 && (
							(bounds.x<=b.x && b.x<=bounds.right()) ||
							(bounds.x<=b.right() && b.right()<=bounds.right()) ||
							(b.x<=bounds.x && bounds.right()<=b.right())
						)
							
				) {
					shapes.add(s);
				}
					
			}
		}
		// sort the rectangles by increasing distance from the top of the given shape
		Collections.sort(shapes, new Comparator<ContainerShape>() {
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				Rectangle b1 = getBounds(s1);
				Rectangle b2 = getBounds(s2);
				int d1 = b1.y - bounds.y;
				int d2 = b2.y - bounds.y;
				int i = d1 - d2;
				if (i==0) {
					i = b1.x - b2.x;
				}
				return i;
			}
		});
		
		for (int i=0; i<shapes.size(); ++i) {
			ContainerShape s = shapes.get(i);
			Rectangle b = getBounds(s);
			if (b.x<=bounds.x && b.right()>=bounds.right()) {
				++i;
				while (i<shapes.size()) {
					shapes.remove(i);
				}
			}
		}
		return shapes;
	}
	
	class Slice {
		protected Slice parent;
		protected int left, right;
		List<Integer> cuts = new ArrayList<Integer>();
		List<Slice> children = new ArrayList<Slice>();
		
		public Slice(int left, int right) {
			this(null,left,right);
		}
		
		protected Slice(Slice parent, int left, int right) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			cuts.add(left);
			cuts.add(right);
		}
		
		public int remove(int l, int r) {
			if (r<left || right<l) {
				for (Slice s : children) {
					s.remove(l, r);
				}
			}
			else if (l<=left && left<=r && r<=right) {
				for (Slice s : children) {
					s.remove(l, r);
				}
				cut(left);
				left = r;
			}
			else if (l<=left && right<=r) {
				for (Slice s : children) {
					s.remove(l, left);
					s.remove(right, r);
				}
				cut(left);
				cut(right);
				right = left;
			}
			else if (l>left && r<right) {
				children.add(new Slice(this,left,l));
				children.add(new Slice(this,r,right));
				cut(left);
				left = l;
				cut(right);
				right = r;
			}
			else if (left<=l && l<=right && r>=right) {
				for (Slice s : children) {
					s.remove(r, right);
				}
				cut(right);
				right = l;
			}
			return length();
		}
		
		public int length() {
			int length = right - left;
			for (Slice s : children) {
				length += s.length();
			}
			return length;
		}
		
		protected void cut(int point) {
			if (!cuts.contains(point))
				cuts.add(point);
		}
		
		public List<Integer> getCuts() {
			cut(left);
			cut(right);
			for (Slice s : children) {
				for (Integer p : s.getCuts())
					cut(p);
			}
			if (parent==null) {
				Collections.sort(cuts);
			}
			return cuts;
		}
	}
	
	protected void sortAllShapes() {
		Collections.sort(allShapes, new Comparator<ContainerShape>() {

			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				Rectangle r1 = getBounds(s1);
				Rectangle r2 = getBounds(s2);
				int i = r1.x - r2.x;
				if (i==0) {
					i = r1.y - r2.y;
				}
				return i;
			}
		});
	}

	protected Rectangle getBounds(ContainerShape shape) {
		ILocation loc = peService.getLocationRelativeToDiagram(shape);
		IDimension size = GraphicsUtil.calculateSize(shape);
		if (rotate) {
			return rotateRectangle(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
		}
		return new Rectangle(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
	}

	protected Rectangle rotateRectangle(int x, int y, int width, int height) {
		return rotateRectangle(new Rectangle(x,y,width,height));
	}
	
	protected Rectangle rotateRectangle(Rectangle r) {
		int y = r.x;
		int x = r.y;
		int w = r.height;
		int h = r.width;
		r.x = x;
		r.y = y;
		r.width = w;
		r.height = h;
		return r;
	}
	
	protected class AddAisleFeature extends AbstractAddShapeFeature {
		
		public AddAisleFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canAdd(IAddContext context) {
			return true;
		}
		@Override
		public PictogramElement add(IAddContext context) {
			int x = context.getX();
			int y = context.getY();
			int width = context.getWidth();
			int height = context.getHeight();
			IColorConstant foreground = new ColorConstant(0,0,255);
			Aisle aisle = (Aisle)context.getNewObject();
			IColorConstant background = new ColorConstant(0,0,0);
			if ("start".equals(aisle.type))
				background = new ColorConstant(0,255,150);
			if ("end".equals(aisle.type))
				background = new ColorConstant(150,255,0);
			if ("above".equals(aisle.type))
				background = new ColorConstant(0,0,255);
			if ("below".equals(aisle.type))
				background = new ColorConstant(150,150,0);
			if ("trailing".equals(aisle.type))
				background = new ColorConstant(250,200,50);
			
			Rectangle bounds = getBounds(source);
			if (aisle.adjacent(bounds) != Aisle.Adjacence.NONE) {
				background = new ColorConstant(0,255,0);
			}
			bounds = getBounds(target);
			if (aisle.adjacent(bounds) != Aisle.Adjacence.NONE) {
				background = new ColorConstant(255,0,0);
			}
			
			Diagram diagram = getDiagram();
			
			ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
			org.eclipse.graphiti.mm.algorithms.Rectangle invisibleRect = gaService.createInvisibleRectangle(containerShape);
			gaService.setLocationAndSize(invisibleRect, x, y, width, height);

			Shape rectShape = peService.createShape(containerShape, false);
			RoundedRectangle roundedRect = gaService.createRoundedRectangle(rectShape, 10, 10);
			roundedRect.setForeground(gaService.manageColor(diagram, foreground));
			roundedRect.setBackground(gaService.manageColor(diagram, background));
			roundedRect.setFilled(true);
			roundedRect.setTransparency(0.75);
			roundedRect.setLineWidth(4);

//			link(rectShape, context.getNewObject());
			peService.setPropertyValue(containerShape, "CONNECTION_ROUTING_AISLE", "true");
			
			gaService.setLocationAndSize(roundedRect, 0, 0, width, height);
			peService.sendToFront(containerShape);
			return containerShape;
		}
	}
	
	private class DeleteAisleFeature extends DefaultDeleteFeature {

		public DeleteAisleFeature(IFeatureProvider fp) {
			super(fp);
		}
		
	}
	
	private class AddAisleConnectionFeature extends AbstractAddShapeFeature {

		public AddAisleConnectionFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canAdd(IAddContext ac) {
			return true;
		}

		@Override
		public PictogramElement add(IAddContext ac) {
			IAddConnectionContext context = (IAddConnectionContext) ac;
			Anchor sourceAnchor = context.getSourceAnchor();
			Anchor targetAnchor = context.getTargetAnchor();
			ContainerShape sourceShape = (ContainerShape) sourceAnchor.getParent();
			ContainerShape targetShape = (ContainerShape) targetAnchor.getParent();
			Object[] newObject = (Object[]) context.getNewObject();
			Aisle sourceNode = (Aisle)newObject[0];
			Aisle targetNode = (Aisle)newObject[1];
			
			Diagram diagram = getDiagram();
			Connection connection = peService.createFreeFormConnection(diagram);
			connection.setStart(sourceAnchor);
			connection.setEnd(targetAnchor);
			Polyline connectionLine = Graphiti.getGaService().createPolyline(connection);

			connectionLine.setLineWidth(3);
			IColorConstant foreground;
			int ri = sourceNode.getRight().indexOf(targetNode);
			int li = sourceNode.getLeft().indexOf(targetNode);
			if (ri>=0)
				foreground = new ColorConstant(0,255,0);
			else if (li>=0)
				foreground = new ColorConstant(255,0,0);
			else
				foreground = new ColorConstant(0,0,0);
			connectionLine.setForeground(gaService.manageColor(diagram, foreground));

			return connection;
		}
		
	}
}
