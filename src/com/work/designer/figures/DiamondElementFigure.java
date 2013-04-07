package com.work.designer.figures;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class DiamondElementFigure extends AbstractElementFigure {
	
	protected Polygon diamond;

	@Override
	protected void customizeFigure() {
		diamond = new Polygon();
		diamond.setLineWidth(1);
		add(diamond, 0);
		setSize(50, 50);
		diamond.setPoints(calculatePointList());
	}
	
    public void setBounds(Rectangle rectangle) {
        super.setBounds(rectangle);
        diamond.setPoints(calculatePointList());
    }
    
    private PointList calculatePointList() {
		PointList result = new PointList();
		Rectangle bounds = getBounds();
		result.addPoint(bounds.x + bounds.width / 2 - diamond.getLineWidth() / 4, bounds.y + diamond.getLineWidth() / 2);
		result.addPoint(bounds.x + bounds.width - diamond.getLineWidth(), bounds.y + bounds.height / 2 - diamond.getLineWidth() / 4);
		result.addPoint(bounds.x + bounds.width / 2 - diamond.getLineWidth() / 4, bounds.y + bounds.height - diamond.getLineWidth());
		result.addPoint(bounds.x + diamond.getLineWidth() / 2, bounds.y + bounds.height / 2 - diamond.getLineWidth() / 4);
		return result;
    }
    
    public ConnectionAnchor getSourceConnectionAnchor() {
        return new DiamondAnchor(this);
    }

    public ConnectionAnchor getTargetConnectionAnchor() {
        return new DiamondAnchor(this);
    }
    
}
