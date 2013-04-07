package com.work.designer.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class FieldContainerEditPolicy extends ContainerEditPolicy 
{
	@Override
	public Command getCommand(Request request)
	{
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) 
	{
		return null;
	}

	@Override
	public boolean understandsRequest(Request req) 
	{
		return true;
	}
}
