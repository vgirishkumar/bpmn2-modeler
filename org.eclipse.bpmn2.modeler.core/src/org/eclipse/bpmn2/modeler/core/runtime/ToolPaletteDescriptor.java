package org.eclipse.bpmn2.modeler.core.runtime;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;

import org.eclipse.bpmn2.modeler.core.features.CompoundCreateFeature;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;

public class ToolPaletteDescriptor extends BaseRuntimeDescriptor {

	// The Drawers
	public static class CategoryDescriptor {
		private ToolPaletteDescriptor parent;
		private String name;
		private String description;
		private String icon;
		private List<ToolDescriptor> tools = new ArrayList<ToolDescriptor>();
		
		public CategoryDescriptor(ToolPaletteDescriptor parent, String name, String description, String icon) {
			this.parent = parent;
			this.name = name;
			this.description = description;
			this.icon = icon;
		}
		
		public ToolDescriptor addTool(String name, String description, String icon, String object) {
			ToolDescriptor tool = new ToolDescriptor(this, name, description, icon, object);
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
		
		public ToolPaletteDescriptor getParent() {
			return parent;
		}
	};
	
	// The Tools
	public static class ToolDescriptor {
		private CategoryDescriptor parent;
		private String name;
		private String description;
		private String icon;
		private List<ToolPart> toolParts = new ArrayList<ToolPart>();
		
		public ToolDescriptor(CategoryDescriptor parent, String name, String description, String icon, String object) {
			this.parent = parent;
			this.name = name;
			this.description = description;
			this.icon = icon;
			String toolpartName = "";
			Stack<ToolPart> toolStack = new Stack<ToolPart>();
			ToolPart tool = null;
			char chars[] = object.toCharArray();
			for (int i=0; i<chars.length; ++i) {
				char c = chars[i];
				if (c=='+') {
					toolStack.push(tool);
					if (tool!=null) {
						ToolPart newTool = new ToolPart(this, toolpartName);
						tool.children.add(newTool);
						tool = newTool;
					}
					else {
						tool = new ToolPart(this, toolpartName);
						toolParts.add(tool);
					}
					toolpartName = "";
				}
				else if (c=='-') {
					if (!"".equals(toolpartName))
						tool.children.add( new ToolPart(this, toolpartName) );
					tool = toolStack.pop();
					toolpartName = "";
				}
				else if (c==',') {
					ToolPart newTool = new ToolPart(this, toolpartName);
					if (tool==null) {
						toolParts.add(newTool);
					}
					else {
						tool.children.add(newTool);
					}
					toolpartName = "";
				}
				else if (c=='[') {
					ToolPart newTool = new ToolPart(this, toolpartName);
					if (tool==null) {
						toolParts.add(newTool);
					}
					else {
						tool.children.add(newTool);
					}
					toolpartName = "";
					
					// data for preceding object type follows:
					// [name=value] or [name1=value1,name2=value2]
					// are valid
					++i;
					do {
						String prop = "";
						while (i<chars.length) {
							c = chars[i++];
							if (c=='\\')
								c = chars[i++];
							else if (c=='=')
								break;
							prop += c;
						}
						String value = "";
						while (i<chars.length) {
							c = chars[i++];
							if (c=='\\')
								c = chars[i++];
							else if (c==',' || c==']')
								break;
							value += c;
						}
						newTool.putProperty(prop,value);
					} while (i<chars.length && c!=']');
				}
				else if ("".equals(toolpartName)) {
					if (Character.isJavaIdentifierStart(c))
						toolpartName += c;
				}
				else if (Character.isJavaIdentifierPart(c)) {
					toolpartName += c;
				}
				
				if (i==chars.length-1) {
					if (tool==null) {
						tool = new ToolPart(this, toolpartName);
						toolParts.add(tool);
					}
					else {
						tool.children.add( new ToolPart(this, toolpartName) );
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
		
		public CategoryDescriptor getParent() {
			return parent;
		}
	};
	
	public static class ToolPart {
		private ToolDescriptor parent;
		private String name;
		private List<ToolPart> children = new ArrayList<ToolPart>();
		private Hashtable<String, String> properties = null;
		
		public ToolPart(ToolDescriptor parent, String name) {
			this.parent = parent;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public List<ToolPart> getChildren() {
			return children;
		}
		
		public void putProperty(String name, String value) {
			getProperties().put(name, value);
		}
		
		public Hashtable<String, String> getProperties() {
			if (properties==null)
				properties = new Hashtable<String, String>();
			return properties;
		}
		
		public String getProperty(String name) {
			if (properties==null)
				return null;
			return properties.get(name);
		}
		
		public boolean hasProperties() {
			return properties!=null && properties.size()>0;
		}
		
		public ToolDescriptor getParent() {
			return parent;
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
		CategoryDescriptor category = new CategoryDescriptor(this, name, description, icon);
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
