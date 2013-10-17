package org.eclipse.bpmn2.modeler.core.merrimac.clad;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpmn2.modeler.core.merrimac.clad.messages"; //$NON-NLS-1$
	public static String AbstractDetailComposite_Attributes;
	public static String AbstractListComposite_Add;
	public static String AbstractListComposite_Close;
	public static String AbstractListComposite_Delete;
	public static String AbstractListComposite_Details;
	public static String AbstractListComposite_Edit;
	public static String AbstractListComposite_List;
	public static String AbstractListComposite_Move_Down;
	public static String AbstractListComposite_Move_Up;
	public static String AbstractListComposite_Remove;
	public static String DefaultDetailComposite_Documentation;
	public static String DefaultListComposite_Cannot_Delete_Title;
	public static String DefaultListComposite_Internal_Error_Title;
	public static String PropertiesCompositeFactory_Internal_Error_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
