package com.work.designer.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

public class FieldFlowLayoutEditPolicy extends FlowLayoutEditPolicy 
{
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
	protected Command getDeleteDependantCommand(Request request) 
	{
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) 
	{
		return null;
	}
}
