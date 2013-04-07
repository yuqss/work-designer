package com.work.designer.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.work.designer.commands.ChangeConstraintCommand;
import com.work.designer.commands.CreateTaskCommand;
import com.work.designer.model.Task;
import com.work.designer.model.Process;

public class ProcessXYLayoutEditPolicy extends XYLayoutEditPolicy 
{
	@Override
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}

	@Override
	protected Command createAddCommand(EditPart child, Object constraint) {
		return super.createAddCommand(child, constraint);
	}
	
	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint)
	{
	    return new ChangeConstraintCommand(child.getModel(), (Rectangle)constraint);
	}

	@Override
	protected Command getCreateCommand(CreateRequest request)
	{
		Rectangle constraint = (Rectangle) getConstraintFor(request);
		if ((request.getNewObject() instanceof Task)) 
		{
			Task task = (Task) request.getNewObject();
			task.setLayout(constraint);
		}
		
		return new CreateTaskCommand((Process)getHost().getModel(), request.getNewObject());
	}

	@Override
	public boolean understandsRequest(Request req)
	{
		return true;
	}

}
