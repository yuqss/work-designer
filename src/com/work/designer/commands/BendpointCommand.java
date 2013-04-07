package com.work.designer.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.work.designer.model.Transition;

public class BendpointCommand extends Command
{
	protected int index;
	
	protected Transition connection;
	
	protected Dimension d1, d2;
	
	protected Point p1, p2;

	public void setConnection(Transition connection)
	{
		this.connection = connection;
	}

	public void redo()
	{
		execute();
	}

	public void setRelativeDimensions(Dimension dim1, Dimension dim2)
	{
		d1 = dim1;
		d2 = dim2;
	}
	
	public void setRelativePoints(Point dim1, Point dim2)
	{
		p1 = dim1;
		p2 = dim2;
	}

	public void setIndex(int i)
	{
		index = i;
	}

}
