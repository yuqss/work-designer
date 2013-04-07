package com.work.designer.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.work.designer.commands.RenameElementCommand;
import com.work.designer.figures.AbstractElementFigure;
import com.work.designer.model.BaseElement;

public class ElementDirectEditPolicy extends DirectEditPolicy
{
    protected Command getDirectEditCommand(DirectEditRequest request)
    {
        RenameElementCommand cmd = new RenameElementCommand();
        cmd.setSource((BaseElement)getHost().getModel());
        cmd.setOldName(((BaseElement)getHost().getModel()).getDisplayName());
        cmd.setName((String) request.getCellEditor().getValue());
        return cmd;
    }

    @Override
    protected void showCurrentEditValue(DirectEditRequest request)
    {
        String value = (String) request.getCellEditor().getValue();
        IFigure figure = getHostFigure();
        if (figure instanceof Label)
        {
        	((Label)figure).setText(value);
        } 
        else if (figure instanceof AbstractElementFigure)
        {
        	((AbstractElementFigure)figure).setText(value);
        }
    }

}
