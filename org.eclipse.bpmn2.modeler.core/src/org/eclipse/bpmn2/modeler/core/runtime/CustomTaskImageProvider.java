package org.eclipse.bpmn2.modeler.core.runtime;

import java.net.URL;

import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.internal.GraphitiUIPlugin;
import org.eclipse.graphiti.ui.platform.AbstractImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

/**
 * Image provider class for our Custom Task extensions.
 * 
 * TODO: In Kepler, this may change at which time we can register these icons
 * in the plugin.xml as a Graphiti extension point. If this doesn't happen,
 * we should probably consider pushing image registration up to the core editor.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=366452
 * @author bbrodt
 */
public class CustomTaskImageProvider {
	
	public final static String ICONS_FOLDER = "icons/";

	// Sneaky tip: The values of this enum correspond to the subfolder names in "icons"
	public enum IconSize {
		SMALL("small"),
		LARGE("large");
		String value;
		IconSize(String value) {
			this.value = value;
		}
	}
	private static boolean registered = false;

	public CustomTaskImageProvider(Package pluginPackage) {
		
		super();
	}

	public static void registerAvailableImages() {
		if (!registered) {
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				for (CustomTaskDescriptor ctd : rt.getCustomTasks()) {
					String icon = ctd.getIcon();
					if (icon!=null)
						registerImage(ctd, icon);
				}
			}
			registered = true;
		}
	}

	public static Image createImage(CustomTaskDescriptor ctd, GraphicsAlgorithmContainer ga, String icon, IconSize size) {
		// To create an image of a specific size, use the "huge" versions
		// to prevent pixelation when stretching a small image
		String imageId = ctd.getImageId(icon, size); 
		if (imageId != null) {
			Image img = Graphiti.getGaService().createImage(ga, imageId);
			img.setProportional(false);
			return img;
		}
		return null;
	}

	public static Image createImage(CustomTaskDescriptor ctd, GraphicsAlgorithmContainer ga, String icon, int w, int h) {
		// To create an image of a specific size, use the "huge" versions
		// to prevent pixelation when stretching a small image
		String imageId = ctd.getImageId(icon, IconSize.LARGE); 
		if (imageId != null) {
			Image img = Graphiti.getGaService().createImage(ga, imageId);
			img.setProportional(false);
			img.setWidth(w);
			img.setHeight(h);
			img.setStretchH(true);
			img.setStretchV(true);
			return img;
		}
		return null;
	}

	protected static void registerImage(CustomTaskDescriptor ctd, String icon) {
		ImageRegistry imageRegistry = GraphitiUIPlugin.getDefault().getImageRegistry();
		for (IconSize size : IconSize.values()) {
			String imageId = ctd.getImageId(icon,size); 
			if (imageId != null) {
				String filename = ctd.getImagePath(icon,size);
				URL url = ctd.getFeatureContainer().getClass().getClassLoader().getResource(filename);
				ImageDescriptor descriptor =  ImageDescriptor.createFromURL(url);
				imageRegistry.put(imageId, descriptor);
			}
		}
	}
}
