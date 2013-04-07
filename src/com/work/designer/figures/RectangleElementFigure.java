package com.work.designer.figures;

import org.eclipse.draw2d.LineBorder;

public class RectangleElementFigure extends AbstractElementFigure
{
	protected void customizeFigure() {
		setBorder(new LineBorder(1));
		setSize(80, 40);
	}

	public void setSelected(boolean b) {
		super.setSelected(b);
		((LineBorder) getBorder()).setWidth(b ? 3 : 1);
		repaint();
	}
}
