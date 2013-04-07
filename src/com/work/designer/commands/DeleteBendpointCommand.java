package com.work.designer.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.work.designer.model.Transition;

public class DeleteBendpointCommand extends Command
{
    private Transition connection;
    private Point oldLocation;
    private int index;

    public void execute() {
        oldLocation = (Point) connection.getBendpoints().get(index);
        connection.removeBendpoint(index);
    }

    public void setConnectionModel(Object model) {
        connection = (Transition) model;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void undo() {
        connection.addBendpoint(index, oldLocation);
    }
}