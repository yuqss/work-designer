package com.work.designer;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

public class DesignerEditorActionBarContributor extends ActionBarContributor 
{
	@Override
	protected void buildActions() 
	{
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
        addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY, "网格", IAction.AS_CHECK_BOX));   
        addRetargetAction(new RetargetAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY, "几何对齐", IAction.AS_CHECK_BOX));
	}

	@Override
	protected void declareGlobalActionKeys() 
	{

	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) 
	{
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
		toolBarManager.add(new Separator());
        toolBarManager.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
        toolBarManager.add(getAction(GEFActionConstants.TOGGLE_SNAP_TO_GEOMETRY));	
	}
}
