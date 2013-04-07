package com.work.designer.figures;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class EllipseElementFigure extends AbstractElementFigure
{
	protected Ellipse ellipse;

	protected void customizeFigure() {
		this.ellipse = new Ellipse();
		add(this.ellipse, 0);
		this.ellipse.setBounds(getBounds());
		setSize(40, 40);
	}

	public void setColor(Color color) {
		this.ellipse.setBackgroundColor(color);
	}

	public void setBounds(Rectangle rectangle) {
		super.setBounds(rectangle);
		this.ellipse.setBounds(rectangle);
	}

	public void setSelected(boolean b) {
		super.setSelected(b);
		this.ellipse.setLineWidth(b ? 3 : 1);
		repaint();
	}

	public ConnectionAnchor getSourceConnectionAnchor() {
		return new EllipseAnchor(this);
	}

	public ConnectionAnchor getTargetConnectionAnchor() {
		return new EllipseAnchor(this);
	}
}
