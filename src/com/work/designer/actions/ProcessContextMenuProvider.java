package com.work.designer.actions;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

public class ProcessContextMenuProvider extends ContextMenuProvider 
{
	private ActionRegistry actionRegistry;
	
	public ProcessContextMenuProvider(ActionRegistry registry, EditPartViewer viewer) 
	{
	    super(viewer);
	    this.actionRegistry = registry;
	}
	
	@Override
	public void buildContextMenu(IMenuManager manager) 
	{
		GEFActionConstants.addStandardActionGroups(manager);
		IAction action = null;
	    action = this.actionRegistry.getAction(ActionFactory.UNDO.getId());
	    manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
	    action = this.actionRegistry.getAction(ActionFactory.REDO.getId());
	    manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
		action = this.actionRegistry.getAction(ActionFactory.DELETE.getId());
		if (action != null && action.isEnabled())
		{
			manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		}
	}
}
