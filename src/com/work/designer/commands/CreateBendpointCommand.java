package com.work.designer.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.work.designer.model.Transition;

public class CreateBendpointCommand extends Command
{
    private Transition connection;
    
    private Point location;
    
    private int index;

    public void execute()
    {
        connection.addBendpoint(index, location);
    }

    public void setConnection(Object model)
    {
        connection = (Transition) model;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setLocation(Point point) {
        location = point;
    }

    public void undo() {
        connection.removeBendpoint(index);
    }
}