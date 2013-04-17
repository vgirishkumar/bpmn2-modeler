package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.features.RouteSolver.Aisle.Adjacence;
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
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class RouteSolver {

	protected final static IGaService gaService = Graphiti.getGaService();
	protected static final IPeService peService = Graphiti.getPeService();
	static final int topMargin = 50;
	static final int bottomMargin = 50;
	static final int leftMargin = 50;
	static final int rightMargin = 50;

	IFeatureProvider fp;
	List<ContainerShape> allShapes;
	ContainerShape source; 
	ContainerShape target;
	int top, left, bottom, right;
	AisleList verticalAisles;
	AisleList horizontalAisles;
	private boolean rotate = false;
	
	/**
	 * This class represents an "aisle" or blank space between shapes, which can
	 * be used to lay connection lines. Aisles are linked together in a network;
	 * the linkage is created when two Aisle nodes share a common left or right
	 * edge. See also AisleList.
	 */
	public static class Aisle {
		protected final static AisleList EMPTY_LIST = new AisleList();
		protected Rectangle rect;
		protected List<Aisle> leftAdjacent;
		protected List<Aisle> rightAdjacent;
		public String type;
		protected ContainerShape shape;

		public static enum Adjacence { LEFT, TOP, BOTTOM, RIGHT, NONE };
		
		public Aisle(String type, int x, int y, int width, int height) {
			this(new Rectangle(x,y,width,height));
			this.type = type;
		}
		
		public Aisle(Rectangle r) {
			rect = new Rectangle(r);
		}
		
		public Adjacence adjacent(Aisle node) {
			return adjacent(node.rect);
		}
		
		public Adjacence adjacent(Rectangle other) {
			if (rect.y==other.bottom()) {
				if (rect.right()<=other.x || other.right()<=rect.x)
					return Adjacence.NONE;
				return Adjacence.TOP;
			}
			if (rect.x==other.right()) {
				if (rect.bottom()<=other.y || other.bottom()<=rect.y)
					return Adjacence.NONE;
				return Adjacence.LEFT;
			}
			if (rect.right()==other.x) {
				if (rect.bottom()<=other.y || other.bottom()<=rect.y)
					return Adjacence.NONE;
				return Adjacence.RIGHT;
			}
			if (rect.bottom()==other.y) {
				if (rect.right()<=other.x || other.right()<=rect.x)
					return Adjacence.NONE;
				return Adjacence.BOTTOM;
			}
			return Adjacence.NONE;
		}
		
		public void addLeft(Aisle node) {
			if (leftAdjacent==null)
				leftAdjacent = new ArrayList<Aisle>();
			if (!leftAdjacent.contains(node))
				leftAdjacent.add(node);
			if (node.rightAdjacent==null)
				node.rightAdjacent = new ArrayList<Aisle>();
			if (!node.rightAdjacent.contains(this))
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
			if (!rightAdjacent.contains(node))
				rightAdjacent.add(node);
			if (node.leftAdjacent==null)
				node.leftAdjacent = new ArrayList<Aisle>();
			if (!node.leftAdjacent.contains(this))
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

		@Override
		public boolean equals(Object that) {
			if (that instanceof Aisle)
				return this.rect.equals(((Aisle)that).rect);
			return false;
		}

		public void rotate(boolean b) {
			rotateRectangle(rect);
		}
	}
	
	/**
	 * This class manages a network of Aisle nodes. The nodes are linked if their
	 * physical rectangles share an edge. Depending on the orientation in which the net was created,
	 * only the left/right (for a "vertical" or non-rotated net) or top/bottom (a "horizontal"
	 * or rotated net) edges are tested for adjacency.
	 */
	protected static class AisleList extends ArrayList<Aisle> {
		
		private static final long serialVersionUID = -3041403111796385182L;
		boolean isRotated = false;
		ContainerShape source;
		ContainerShape target;
		List<Aisle> sourceAdjacentAisles = new ArrayList<Aisle>();
		List<Aisle> targetAdjacentAisles = new ArrayList<Aisle>();
		
		@Override
		public boolean add(Aisle a) {
			if (!contains(a) && a.getWidth()>0 && a.getHeight()>0)
				return super.add(a);
			return false;
		}
		
		public void link(ContainerShape source, ContainerShape target) {
			this.source = source;
			this.target = target;
			Rectangle sourceBounds = getBounds(isRotated, source);
			Rectangle targetBounds = getBounds(isRotated, target);
			sourceAdjacentAisles.clear();
			targetAdjacentAisles.clear();
			for (Aisle a1 : this) {
				for (Aisle a2 : this) {
					if (a1!=a2) {
						switch (a1.adjacent(a2)) {
						case LEFT:
						case TOP:
							a1.addLeft(a2);
							break;
						case RIGHT:
						case BOTTOM:
							a1.addRight(a2);
							break;
						case NONE:
							break;
						}
					}
				}
				
				if (a1.adjacent(sourceBounds) != Aisle.Adjacence.NONE) {
					sourceAdjacentAisles.add(a1);
				}
				
				if (a1.adjacent(targetBounds) != Aisle.Adjacence.NONE) {
					targetAdjacentAisles.add(a1);
				}
			}
		}

		public void rotate(boolean b) {
			if (isRotated!=b) {
				for (Aisle node : this) {
					node.rotate(b);
				}
				isRotated = b;
			}
		}
		
		public List<Aisle> getAislesAdjacentTo(ContainerShape shape, Adjacence adjacence) {
			List<Aisle> adjacentAisles;
			List<Aisle> list = new ArrayList<Aisle>();
			if (shape==source) {
				adjacentAisles = sourceAdjacentAisles;
			}
			else if (shape==target) {
				adjacentAisles = targetAdjacentAisles;
			}
			else
				return list;
			
			Rectangle bounds = getBounds(isRotated, shape);
			for (Aisle a : adjacentAisles) {
				if (a.adjacent(bounds) == adjacence)
					list.add(a);
			}
			return list;
		}
	}
	
	/**
	 * RouteSolver constructor.
	 * 
	 * @param fp - Graphiti Feature Provider
	 * @param allShapes - a list of all shapes that are considered in the routing solution.
	 */
	public RouteSolver(IFeatureProvider fp, List<ContainerShape> allShapes) {
		this.fp = fp;
		this.allShapes = new ArrayList<ContainerShape>();
		this.allShapes.addAll(allShapes);
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
	
	public boolean solve(ContainerShape source, ContainerShape target) {
		this.source = source;
		this.target = target;

		verticalAisles.link(source, target);
		List<Aisle> list = verticalAisles.getAislesAdjacentTo(source, Adjacence.RIGHT);
		drawAisles(verticalAisles);
		drawAisleConnections(verticalAisles);
		
		horizontalAisles.link(source, target);
		drawAisles(horizontalAisles);
		drawAisleConnections(horizontalAisles);
		return true;
	}
	
	public boolean initialize() {
		
		clear();
		
		if (allShapes.size()<2)
			return false;
		
		rotate = false;
		Rectangle r = calculateDiagramBounds();
		sortAllShapes();
		top = r.y;
		left = r.x;
		bottom = top + r.height;
		right = left + r.width;
		verticalAisles = calculateAisles();

		rotate = true;
		r = calculateDiagramBounds();
		sortAllShapes();
		top = r.y;
		left = r.x;
		bottom = top + r.height;
		right = left + r.width;
		horizontalAisles = calculateAisles();
		rotate = false;
		
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
	
	protected AisleList calculateAisles() {

		AisleList aisles = new AisleList();
		
		aisles.add(new Aisle("start", left, top, leftMargin, bottom-top));
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
				
				aisles.add(newAisle);
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
					aisles.add(newAisle);
				}
				
				c1 = c2;
			}

			Aisle a = getTrailingAisle(shape);
			if (a!=null)
				aisles.add(a);
		}
		aisles.add(new Aisle("end",right-rightMargin, top, rightMargin, bottom-top));
		
		// rotate the horizontal aisle nodes
		aisles.rotate(rotate);

		return aisles;
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

	private Rectangle getBounds(ContainerShape shape) {
		return getBounds(rotate,shape);
	}

	protected static Rectangle getBounds(boolean rotate, ContainerShape shape) {
		ILocation loc = peService.getLocationRelativeToDiagram(shape);
		IDimension size = GraphicsUtil.calculateSize(shape);
		if (rotate) {
			return rotateRectangle(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
		}
		return new Rectangle(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
	}

	protected static Rectangle rotateRectangle(int x, int y, int width, int height) {
		return rotateRectangle(new Rectangle(x,y,width,height));
	}
	
	public static Rectangle rotateRectangle(Rectangle r) {
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
	
	protected void drawAisles(AisleList aisle) {
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
	
	protected void drawAisleConnections(AisleList aisles) {
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		ContainerShape sourceShape;
		Anchor sourceAnchor;
		ContainerShape targetShape;
		Anchor targetAnchor;
		
		for (Aisle n1 : aisles) {
			for (Aisle n2 : n1.getRight()) {
				if (n1!=n2) {
					sourceShape = n1.getShape();
					targetShape = n2.getShape();
					if (sourceShape!=null && targetShape!=null) {
						if (sourceShape.getAnchors().size()>0)
							sourceAnchor = sourceShape.getAnchors().get(0);
						else {
							FixPointAnchor a = peService.createFixPointAnchor(sourceShape);
							Rectangle r = getBounds(sourceShape);
							a.setLocation(GraphicsUtil.createPoint(r.width/2, r.height/2));
							gaService.createInvisibleRectangle(a);
							sourceAnchor = a;
						}

						if (targetShape.getAnchors().size()>0)
							targetAnchor = targetShape.getAnchors().get(0);
						else {
							FixPointAnchor a = peService.createFixPointAnchor(targetShape);
							Rectangle r = getBounds(targetShape);
							a.setLocation(GraphicsUtil.createPoint(r.width/2, r.height/2));
							gaService.createInvisibleRectangle(a);
							targetAnchor = a;
						}
						AddConnectionContext context = new AddConnectionContext(sourceAnchor, targetAnchor);
						context.setTargetContainer(diagram);
						context.setNewObject(new Object[] {n1, n2});
						AddAisleConnectionFeature feature = new AddAisleConnectionFeature(fp);
						feature.add(context);
					}
				}
			}
		}
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
			IColorConstant background = new ColorConstant(128,128,128);
			double transparency = 0.75;
//			if ("start".equals(aisle.type))
//				background = new ColorConstant(0,255,150);
//			if ("end".equals(aisle.type))
//				background = new ColorConstant(150,255,0);
//			if ("above".equals(aisle.type))
//				background = new ColorConstant(0,0,255);
//			if ("below".equals(aisle.type))
//				background = new ColorConstant(150,150,0);
//			if ("trailing".equals(aisle.type))
//				background = new ColorConstant(250,200,50);
			
			Rectangle bounds = getBounds(source);
			boolean sourceAdjacent = false;
			if (aisle.adjacent(bounds) != Aisle.Adjacence.NONE) {
				background = new ColorConstant(0,255,0);
				transparency = 0.25;
				sourceAdjacent = true;
			}
			bounds = getBounds(target);
			if (aisle.adjacent(bounds) != Aisle.Adjacence.NONE) {
				if (sourceAdjacent) {
					background = new ColorConstant(255,255,0);
				}
				else {
					background = new ColorConstant(255,0,0);
				}
				transparency = 0.25;
			}
			
			Diagram diagram = getDiagram();
			
			ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
			org.eclipse.graphiti.mm.algorithms.Rectangle invisibleRect = gaService.createInvisibleRectangle(containerShape);
			gaService.setLocationAndSize(invisibleRect, x, y, width, height);

			Shape rectShape = peService.createShape(containerShape, false);
			RoundedRectangle roundedRect = gaService.createRoundedRectangle(rectShape, 1, 1);
			roundedRect.setForeground(gaService.manageColor(diagram, foreground));
			roundedRect.setBackground(gaService.manageColor(diagram, background));
			roundedRect.setFilled(true);
			roundedRect.setTransparency(transparency);
			roundedRect.setLineWidth(2);

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
			peService.setPropertyValue(connection, "CONNECTION_ROUTING_LINK", "true");

			Polyline connectionLine = Graphiti.getGaService().createPolyline(connection);

			connectionLine.setLineWidth(1);
			IColorConstant foreground = new ColorConstant(0,0,255);
			
			int w = 3;
			int l = 15;
			
			ConnectionDecorator decorator = peService.createConnectionDecorator(connection, false, 1.0, true);
			Polyline arrowhead = gaService.createPolygon(decorator, new int[] { -l, w, 0, 0, -l, -w, -l, w });
			arrowhead.setForeground(gaService.manageColor(diagram, foreground));
			connectionLine.setForeground(gaService.manageColor(diagram, foreground));

			return connection;
		}
	}
}
