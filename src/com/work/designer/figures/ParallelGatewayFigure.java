package com.work.designer.figures;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ParallelGatewayFigure extends GatewayFigure
{
	private static final Image icon = ImageDescriptor.createFromFile(
			TaskFigure.class, "icons/48/gateway_parallel.png").createImage();

	protected void customizeFigure() {
		setIcon(icon);
	}
	
}
