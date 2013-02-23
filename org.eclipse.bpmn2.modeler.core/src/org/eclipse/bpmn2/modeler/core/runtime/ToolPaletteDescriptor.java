package org.eclipse.bpmn2.modeler.core.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.bpmn2.modeler.core.features.CompoundCreateFeature;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;

public class ToolPaletteDescriptor extends BaseRuntimeDescriptor {

	// The Drawers
	public class CategoryDescriptor {
		private String name;
		String description;
		String icon;
		List<ToolDescriptor> tools = new ArrayList<ToolDescriptor>();
		
		public CategoryDescriptor(String name, String description, String icon) {
			this.name = name;
			this.description = description;
			this.icon = icon;
		}
		
		public ToolDescriptor addTool(String name, String description, String icon, String object) {
			ToolDescriptor tool = new ToolDescriptor(name, description, icon, object);
			tools.add(tool);
			return tool;
		}
		
		public List<ToolDescriptor> getTools() {
			return tools;
		}

		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}

		public String getIcon() {
			return icon;
		}
	};
	
	// The Tools
	public class ToolDescriptor {
		String name;
		String description;
		String icon;
		List<ToolPart> toolParts = new ArrayList<ToolPart>();
		
		public ToolDescriptor(String name, String description, String icon, String object) {
			this.name = name;
			this.description = description;
			this.icon = icon;
			String type = "";
			Stack<ToolPart> toolStack = new Stack<ToolPart>();
			ToolPart tool = null;
			char chars[] = object.toCharArray();
			for (int i=0; i<chars.length; ++i) {
				char c = chars[i];
				if (c=='+') {
					toolStack.push(tool);
					if (tool!=null) {
						ToolPart newTool = new ToolPart(type);
						tool.children.add(newTool);
						tool = newTool;
					}
					else {
						tool = new ToolPart(type);
						toolParts.add(tool);
					}
					type = "";
				}
				else if (c=='-') {
					if (!"".equals(type))
						tool.children.add( new ToolPart(type) );
					tool = toolStack.pop();
					type = "";
				}
				else if (c==',') {
					if (tool==null) {
						toolParts.add(new ToolPart(type));
					}
					else {
						tool.children.add( new ToolPart(type) );
					}
					type = "";
				}
				else if ("".equals(type)) {
					if (Character.isJavaIdentifierStart(c))
						type += c;
				}
				else if (Character.isJavaIdentifierPart(c)) {
					type += c;
				}
				
				if (i==chars.length-1) {
					if (tool==null) {
						tool = new ToolPart(type);
						toolParts.add(tool);
					}
					else {
						tool.children.add( new ToolPart(type) );
					}
				}
			}
		}
		
		public List<ToolPart> getToolParts() {
			return toolParts;
		}
		
		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getIcon() {
			return icon;
		}
	};
	
	public class ToolPart {
		String parent;
		List<ToolPart> children = new ArrayList<ToolPart>();
		
		public ToolPart(String type) {
			parent = type;
		}
		
		public String getParent() {
			return parent;
		}
		
		public List<ToolPart> getChildren() {
			return children;
		}
	}
	
	String id; // unique ID
	String type; // the diagram type for which this toolPalette is to be used
	String profile; // the model enablement profile for which this toolPalette is to be used
	// the list of categories in the toolPalette
	List<CategoryDescriptor> categories = new ArrayList<CategoryDescriptor>();
	
	public ToolPaletteDescriptor() {
		super();
	}

	protected void create(IConfigurationElement e) {
		id = e.getAttribute("id");
		type = e.getAttribute("type");
		profile = e.getAttribute("profile");
		for (IConfigurationElement c : e.getChildren()) {
			if (c.getName().equals("category")) {
				String name = c.getAttribute("name");
				String description = c.getAttribute("description");
				String icon = c.getAttribute("icon");
				CategoryDescriptor category = addCategory(name, description, icon);
				for (IConfigurationElement t : c.getChildren()) {
					if (t.getName().equals("tool")) {
						name = t.getAttribute("name");
						description = t.getAttribute("description");
						icon = t.getAttribute("icon");
						String object = t.getAttribute("object");
						if (object!=null && !object.isEmpty())
							category.addTool(name, description, icon, object);
					}
				}
			}
		}
	}
	
	protected CategoryDescriptor addCategory(String name, String description, String icon) {
		CategoryDescriptor category = new CategoryDescriptor(name, description, icon);
		categories.add(category);
		return category;
	}

	public String getProfile() {
		return profile;
	}

	public String getType() {
		return type;
	}
	
	public List<CategoryDescriptor> getCategories() {
		return categories;
	}
}
