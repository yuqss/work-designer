package com.work.designer.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.work.designer.commands.DeleteTaskCommand;
import com.work.designer.model.BaseElement;
import com.work.designer.model.Process;
import com.work.designer.model.Task;

public class TaskComponentEditPolicy extends ComponentEditPolicy 
{
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) 
	{
		Command command = null;
		if ((getHost().getModel() instanceof Task)) 
		{
			command = new DeleteTaskCommand((Process) getHost().getParent().getModel(), (Task) getHost().getModel());
		}

		return command;
	}

	protected BaseElement getModel() {
		return (BaseElement) getHost().getModel();
	}

	@Override
	public Command getCommand(Request request)
	{
		return super.getCommand(request);
	}

	@Override
	public boolean understandsRequest(Request request)
	{
		return true;
	}
}
