package com.work.designer.actions;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

public class FormContextMenuProvider extends ContextMenuProvider 
{
	private ActionRegistry actionRegistry;
	
	private List<String> actionIds;
	
	public FormContextMenuProvider(EditPartViewer viewer, List<String> actionIds, ActionRegistry registry)
	{
	    super(viewer);
	    setActionRegistry(registry);
	    this.actionIds = actionIds;
	}

	@Override
	public void buildContextMenu(IMenuManager menu) 
	{
		GEFActionConstants.addStandardActionGroups(menu);

		ActionHelper.buildContextMenu(menu, actionIds, getActionRegistry());
	}

	public ActionRegistry getActionRegistry() 
	{
		return actionRegistry;
	}

	public void setActionRegistry(ActionRegistry actionRegistry) 
	{
		this.actionRegistry = actionRegistry;
	}
}
