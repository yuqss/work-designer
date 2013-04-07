package com.work.designer.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import com.work.designer.commands.CreateConnectionCommand;
import com.work.designer.commands.ReconnectSourceCommand;
import com.work.designer.commands.ReconnectTargetCommand;
import com.work.designer.model.Task;
import com.work.designer.model.Transition;

public class ProcessGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy 
{
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) 
	{
		Task target = getTask();
		CreateConnectionCommand command = (CreateConnectionCommand)request.getStartCommand();
		command.setTarget(target);
		return command;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request)
	{
		Task source = getTask();
		CreateConnectionCommand command = new CreateConnectionCommand();
		command.setTransition((Transition)request.getNewObject());
		command.setSource(source);
		command.point = request.getLocation();
		request.setStartCommand(command);
		return command;
	}

	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ReconnectSourceCommand cmd = new ReconnectSourceCommand();
		cmd.setTransition((Transition)request.getConnectionEditPart().getModel());
		cmd.setSource(getTask());
		return cmd;
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ReconnectTargetCommand cmd = new ReconnectTargetCommand();
		cmd.setTransition((Transition)request.getConnectionEditPart().getModel());
		cmd.setTarget(getTask());
		return cmd;
	}
	
	protected Task getTask()
	{
		return (Task)getHost().getModel();
	}
}
