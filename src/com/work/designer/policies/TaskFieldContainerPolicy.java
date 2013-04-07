package com.work.designer.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.work.designer.commands.CommandHelper;
import com.work.designer.parts.EditPartHelper;
import com.work.designer.parts.TaskFieldEditPart;

public class TaskFieldContainerPolicy extends ContainerEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request)
	{
	    Object parent = EditPartHelper.getParentModel((TaskFieldEditPart)getHost());
	    Command command = CommandHelper.getCreateCommand(request, parent);
	    return command;
	}
}
