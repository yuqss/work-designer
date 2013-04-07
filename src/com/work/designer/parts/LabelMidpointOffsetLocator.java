package com.work.designer.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.geometry.Point;

public class LabelMidpointOffsetLocator extends MidpointLocator
{
	private Point offset;
	   
	public LabelMidpointOffsetLocator(Connection c, int i, Point offset)
	{
		super(c, i);
	 	this.offset = offset == null ? new Point(0, 0) : offset;
	}
	   
	@Override
	protected Point getReferencePoint()
	{
	  	Point point = super.getReferencePoint();
	  	return point.getTranslated(offset);
	}

	public Point getOffset()
	{
	    return offset;
	}
	   
	public void setOffset(Point offset)
	{
	    this.offset = offset;
	}
}
