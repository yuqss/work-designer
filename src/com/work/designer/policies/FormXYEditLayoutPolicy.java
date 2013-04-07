package com.work.designer.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.work.designer.commands.FieldCreateCommand;
import com.work.designer.model.BaseElement;

public class FormXYEditLayoutPolicy extends XYLayoutEditPolicy 
{
	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) 
	{
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) 
	{
		if(request.getType() == REQ_CREATE)
		{
		    String newObjectType = (String)request.getNewObjectType();
			FieldCreateCommand cmd = new FieldCreateCommand();
			cmd.setParent((BaseElement)getHost().getModel());
			cmd.setField(request.getNewObject());
			cmd.setLabel(newObjectType);
			
			Rectangle constraint = (Rectangle)getConstraintFor(request);
			constraint.x = (constraint.x < 0) ? 0 : constraint.x;
			constraint.y = (constraint.y < 0) ? 0 : constraint.y;
			constraint.width = (constraint.width <= 0) ? 120 : constraint.width;
			constraint.height = (constraint.height <= 0) ? 40 : constraint.height;
			cmd.setLayout(constraint);
			return cmd;
		}
		return null;
	}

}
