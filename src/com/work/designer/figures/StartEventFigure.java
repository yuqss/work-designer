package com.work.designer.figures;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class StartEventFigure extends EventFigure {

	private static final Image icon = ImageDescriptor.createFromFile(
			TaskFigure.class, "icons/48/start_event_empty.png").createImage();

	protected void customizeFigure() {
		setIcon(icon);
	}

}
