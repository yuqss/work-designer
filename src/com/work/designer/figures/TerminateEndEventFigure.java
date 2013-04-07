package com.work.designer.figures;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class TerminateEndEventFigure extends EventFigure
{ 
	private static final Image icon = ImageDescriptor.createFromFile(
			TaskFigure.class, "icons/48/end_event_terminate.png").createImage();

	protected void customizeFigure() {
		setIcon(icon);
	}

}
