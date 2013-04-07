package com.work.designer.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.work.designer.model.Transition;

public class MoveBendpointCommand extends Command
{

    private Transition connection;
    private Point oldLocation, newLocation;
    private int index;

    public void execute() {
        oldLocation = (Point) connection.getBendpoints().get(index);
        connection.replaceBendpoint(index, newLocation);
    }

    public void setConnectionModel(Object model) {
        connection = (Transition) model;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setNewLocation(Point point) {
        newLocation = point;
    }

    public void undo() {
        connection.replaceBendpoint(index, oldLocation);
    }
}