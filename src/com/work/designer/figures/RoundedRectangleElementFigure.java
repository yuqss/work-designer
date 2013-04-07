package com.work.designer.figures;

import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class RoundedRectangleElementFigure extends AbstractElementFigure {
	protected RoundedRectangle rectangle;

	protected void customizeFigure() {
		this.rectangle = new RoundedRectangle();
		this.rectangle.setCornerDimensions(new Dimension(25, 25));
		add(this.rectangle, 0);
		setSize(92, 52);
		setSelected(false);
	}

	public void setColor(Color color) {
		this.rectangle.setBackgroundColor(color);
	}

	public void setBounds(Rectangle rectangle) {
		super.setBounds(rectangle);
		Rectangle bounds = rectangle.getCopy();
		this.rectangle.setBounds(bounds.translate(6, 6).resize(-12, -12));
	}

	public void setSelected(boolean b) {
		super.setSelected(b);
		this.rectangle.setLineWidth(b ? 3 : 1);
		repaint();
	}
}
