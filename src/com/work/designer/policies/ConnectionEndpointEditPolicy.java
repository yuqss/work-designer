package com.work.designer.policies;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.MoveHandleLocator;

public class ConnectionEndpointEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy
{
	@SuppressWarnings("unchecked")
	@Override
	protected List createSelectionHandles() {
		List handles = super.createSelectionHandles();
		List<IFigure> children = (List<IFigure>) getHostFigure().getChildren();
		for (IFigure figure : children) {
			if (figure instanceof Label)
				handles.add(new MoveHandle((GraphicalEditPart) getHost(),
						new MoveHandleLocator(figure)));
		}
		return handles;
	}
}

	 


