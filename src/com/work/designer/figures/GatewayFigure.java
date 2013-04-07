package com.work.designer.figures;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Rectangle;

public abstract class GatewayFigure extends AbstractElementFigure 
{
	public GatewayFigure() {
		setSize(48, 48);
	}

	public void setText(String text) {
	}

	public void setBounds(Rectangle r) {
		r.setSize(48, 48);
		super.setBounds(r);
	}

    public ConnectionAnchor getSourceConnectionAnchor() {
        return new DiamondAnchor(this);
    }

    public ConnectionAnchor getTargetConnectionAnchor() {
        return new DiamondAnchor(this);
    }

}
