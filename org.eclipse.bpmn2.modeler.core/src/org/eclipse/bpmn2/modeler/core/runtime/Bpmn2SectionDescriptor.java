/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.runtime;

import java.util.List;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultPropertySection;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;

public class Bpmn2SectionDescriptor extends AbstractSectionDescriptor {

		protected String id;
		protected String tab;
		protected AbstractPropertySection sectionClass;
		protected Class appliesToClass;
		protected String enablesFor;
		protected String filterClassName;
		protected PropertySectionFilter filter;
		
		public Bpmn2SectionDescriptor(Bpmn2TabDescriptor td, IConfigurationElement e) {
			tab = td.getId();
			id = tab + ".section";

			try {
				String className = e.getAttribute("class");
				if ("default".equals(className)) {
					sectionClass = new DefaultPropertySection();
					if (e.getAttribute("features")!=null) {
						String[] properties = e.getAttribute("features").split(" ");
						((DefaultPropertySection)sectionClass).setProperties(properties);
					}
				}
				else {
					sectionClass = (AbstractPropertySection) e.createExecutableExtension("class");
				}
				filterClassName = e.getAttribute("filter");
				if (filterClassName==null || filterClassName.isEmpty())
					filterClassName = "org.eclipse.bpmn2.modeler.core.runtime.PropertySectionFilter";
				filter = (PropertySectionFilter) Class.forName(filterClassName).getConstructor(null).newInstance(null);
				enablesFor = e.getAttribute("enablesFor");
				String type = e.getAttribute("type");
				if (type!=null && !type.isEmpty()) {
					appliesToClass = Class.forName(type);
					if (sectionClass instanceof DefaultPropertySection) {
						((DefaultPropertySection)sectionClass).setAppliesTo(appliesToClass);
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			td.getSectionDescriptors().add(this);
		}
		
		@Override
		public String getId() {
			return id;
		}

		@Override
		public ISection getSectionClass() {
			return sectionClass;
		}

		@Override
		public String getTargetTab() {
			return tab;
		}

		@Override
		public boolean appliesTo(IWorkbenchPart part, ISelection selection) {

			PictogramElement pe = BusinessObjectUtil.getPictogramElementForSelection(selection);
			if (!filter.select(pe))
				return false;
			
			DiagramEditor editor = ModelUtil.getDiagramEditor(pe);
			if (editor!=null) {
				TargetRuntime rt = (TargetRuntime) editor.getAdapter(TargetRuntime.class);
				if (rt!=null) {
					EObject object = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					int selected = 0;
					int count = 0;
					for (CustomTaskDescriptor tc : rt.getCustomTasks()) {
						for (String s : tc.getPropertyTabs()) {
							if (tab.equals(s)) {
								if (tc.getFeatureContainer().getId(object)!=null)
									++selected;
								++count;
							}
						}
					}
					if (count>0 && selected==0)
						return false;
				}
			}

			// should we delegate to the section to determine whether it should be included in this tab?
			if (sectionClass instanceof IBpmn2PropertySection) {
				EObject object = BusinessObjectUtil.getBusinessObjectForSelection(selection);
				if (object==null)
					return false;
				return ((IBpmn2PropertySection)sectionClass).appliesTo(part, selection);
			}
			
			// if an input description was specified, check if the selected business object is of this description. 
			if (appliesToClass!=null) {
				// this is a special hack to allow selection of connection decorator labels:
				// the connection decorator does not have a business object linked to it,
				// but its parent (the connection) does.
				if (pe.getLink()==null && pe.eContainer() instanceof PictogramElement)
					pe = (PictogramElement)pe.eContainer();

				// check all linked BusinessObjects for a match
				if (pe.getLink()!=null) {
					for (EObject eObj : pe.getLink().getBusinessObjects()){
						if (appliesToClass.isInstance(eObj)) {
							return true;
						}
					}
				}
				return false;
			}
			return true;
		}

		@Override
		public int getEnablesFor() {
			try {
				return Integer.parseInt(enablesFor);
			}
			catch (Exception ex) {
				
			}
			return super.getEnablesFor();
		}

		@Override
		public IFilter getFilter() {
			return new IFilter() {

				@Override
				public boolean select(Object toTest) {
					return false;
				}
				
			};
		}

		@Override
		public List getInputTypes() {
			return super.getInputTypes();
		}

		/**
		 * @param replacedId
		 * @param part
		 * @param selection
		 * @return
		 */
		public boolean doReplaceTab(String replacedId, IWorkbenchPart part, ISelection selection) {
			if (sectionClass instanceof IBpmn2PropertySection) {
				return ((IBpmn2PropertySection)sectionClass).doReplaceTab(replacedId, part, selection);
			}
			return appliesTo(part,selection);
		}
		
	}