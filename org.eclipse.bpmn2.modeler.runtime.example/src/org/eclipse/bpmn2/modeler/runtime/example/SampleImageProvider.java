package org.eclipse.bpmn2.modeler.runtime.example;

import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class SampleImageProvider extends AbstractImageProvider {
	
	public final static String IMAGE_ID_PREFIX =
			SampleImageProvider.class.getPackage().getName() + ".";
	public final static String ICONS_FOLDER = "icons/";

	public SampleImageProvider() {
		super();
	}

	@Override
	protected void addAvailableImages() {
//		super.addAvailableImages();
		
		TargetRuntime rt = TargetRuntime.getRuntime(SampleRuntimeExtension.RUNTIME_ID);
		for (CustomTaskDescriptor ctd : rt.getCustomTasks()) {
			String icon = (String)ctd.getProperty("icon");
			if (icon != null && icon.trim().length() > 0) {
				icon = icon.trim();
				String imageId = IMAGE_ID_PREFIX + icon;
				addImageFilePath(imageId, ICONS_FOLDER + icon);
			}
		}
	}

	public static Image createImage(GraphicsAlgorithmContainer ga, CustomTaskDescriptor ctd, int w, int h) {
		String icon = (String) ctd.getProperty("icon"); 
		if (icon != null && icon.trim().length() > 0) {
			String imageId = IMAGE_ID_PREFIX + icon.trim();
			Image img = Graphiti.getGaService().createImage(ga, imageId);
			img.setProportional(false);
			img.setWidth(w);
			img.setHeight(h);
			img.setStretchH(true);
			img.setStretchV(true);
		}
		return null;
	}
}
