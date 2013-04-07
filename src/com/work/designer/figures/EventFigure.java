package com.work.designer.figures;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.geometry.Rectangle;

public abstract class EventFigure extends AbstractElementFigure {

	public EventFigure() {
		setSize(48, 48);
	}

	public void setText(String text) {
	}

	public void setBounds(Rectangle r) {
		r.setSize(48, 48);
		super.setBounds(r);
	}

	public ConnectionAnchor getSourceConnectionAnchor() {
		return new EllipseAnchor(this);
	}

	public ConnectionAnchor getTargetConnectionAnchor() {
		return new EllipseAnchor(this);
	}

}
