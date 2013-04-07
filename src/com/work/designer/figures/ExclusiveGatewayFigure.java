package com.work.designer.figures;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ExclusiveGatewayFigure extends GatewayFigure
{
	private static final Image icon = ImageDescriptor.createFromFile(
			TaskFigure.class, "icons/48/gateway_exclusive.png").createImage();

	protected void customizeFigure() {
		setIcon(icon);
	}
}
