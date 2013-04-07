package com.work.designer.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.OrderedLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.work.designer.commands.CommandHelper;

public class FieldOrderedLayoutPolicy extends OrderedLayoutEditPolicy
{
	@Override
	public Command getCommand(Request request)
	{
		Object model = getHost().getModel();
		Object parent = getHost().getParent().getModel();
		Command cmd = CommandHelper.getCommand(request, model, parent);
		if (cmd != null)
		{
			return cmd;
		}
		return super.getCommand(request);
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) 
	{
		return null;
	}

	@Override
	public boolean understandsRequest(Request request)
	{
	    if (CommandHelper.understandCommand(request, getHost().getModel()))
	    {
	      return true;
	    }
	    return super.understandsRequest(request);
	}

	@Override
	protected Command createAddCommand(EditPart child, EditPart after)
	{
		return null;
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after)
	{
		return null;
	}

	@Override
	protected EditPart getInsertionReference(Request request)
	{
		return null;
	}
}
