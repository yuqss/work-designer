package com.work.designer.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import com.work.designer.commands.CreateBendpointCommand;
import com.work.designer.commands.DeleteBendpointCommand;
import com.work.designer.commands.MoveBendpointCommand;

public class ConnectionBendpointEditPolicy extends BendpointEditPolicy {
	protected Command getCreateBendpointCommand(BendpointRequest request) {
		Point point = request.getLocation();
		getConnection().translateToRelative(point);

		CreateBendpointCommand command = new CreateBendpointCommand();
		command.setLocation(point);
		command.setConnection(getHost().getModel());
		command.setIndex(request.getIndex());

		return command;
	}

	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		DeleteBendpointCommand command = new DeleteBendpointCommand();
		command.setConnectionModel(getHost().getModel());
		command.setIndex(request.getIndex());
		return command;
	}

	protected Command getMoveBendpointCommand(BendpointRequest request) {
		Point location = request.getLocation();
		getConnection().translateToRelative(location);

		MoveBendpointCommand command = new MoveBendpointCommand();
		command.setConnectionModel(getHost().getModel());
		command.setIndex(request.getIndex());
		command.setNewLocation(location);

		return command;
	}
}
