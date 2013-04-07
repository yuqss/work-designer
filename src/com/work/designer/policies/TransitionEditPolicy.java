package com.work.designer.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

import com.work.designer.commands.DeleteConnectionCommand;
import com.work.designer.model.Transition;

public class TransitionEditPolicy extends ConnectionEditPolicy
{
	@Override
	protected Command getDeleteCommand(GroupRequest request)
	{
	    DeleteConnectionCommand command = new DeleteConnectionCommand();
	    Transition t = (Transition)getHost().getModel();
	    command.setTransition(t);
	    command.setSource(t.getSource());
	    command.setTarget(t.getTarget());
		return command;
	}

	@Override
    public Command getCommand(Request request)
	{
        if (REQ_CREATE.equals(request.getType()))
        {
            return getSplitConnectionCommand(request);
        }
        return super.getCommand(request);
    }
	
    protected Command getSplitConnectionCommand(Request request)
    {
    	System.out.println(((CreateRequest) request).getNewObject());
        return null;
    }
}
