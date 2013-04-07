package com.work.designer.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.gef.ui.actions.RedoAction;
import org.eclipse.gef.ui.actions.UndoAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

import com.work.designer.commands.CommandHelper;

public class ActionHelper 
{
	private static List<IAction> formActions = new ArrayList<IAction>();
	
	private static void loadFormActions(IWorkbenchPart part)
	{
		if(!formActions.isEmpty())
		{
			return;
		}
		formActions.add(new FieldDeleteAction(part, CommandHelper.REQUEST_TYPE_DELETE));
		
		formActions.add(new PlaceHolderIncreaseAction(part, CommandHelper.REQUEST_TYPE_ADDPLACEHOLDER));
		formActions.add(new PlaceHolderDecreaseAction(part, CommandHelper.REQUEST_TYPE_DELPLACEHOLDER));
		
		formActions.add(new LineBRSetAction(part, CommandHelper.REQUEST_TYPE_SETLINEBR));
		formActions.add(new LineBRCancelAction(part, CommandHelper.REQUEST_TYPE_CANCELLINEBR));
		
		formActions.add(new FieldForwardAction(part, CommandHelper.REQUEST_TYPE_FORWARD));
		formActions.add(new FieldBackwardAction(part, CommandHelper.REQUEST_TYPE_BACKWARD));
	}
	
	public static void createCommonAction(EditorPart part, List<String> actions, ActionRegistry registry) 
	{
		IAction action = null;
		action = new UndoAction(part);
		registry.registerAction(action);
		actions.add(action.getId());

		action = new RedoAction(part);
		registry.registerAction(action);
		actions.add(action.getId());

		action = new DeleteAction(part.getEditorSite().getPart());
		registry.registerAction(action);
		actions.add(action.getId());
	}
	
	public static void buildContextMenu(IMenuManager menu, List<String> actionIds, ActionRegistry actionRegistry)
	{
		for(String id : actionIds)
		{
			IAction action = actionRegistry.getAction(id);
			if(action != null && action.isEnabled())
			{
				menu.appendToGroup("group.add", action);
			}
		}
	}
	
	public static void registerFormActions(IWorkbenchPart part, List<String> actionIds, ActionRegistry actionRegistry)
	{
		loadFormActions(part);
		if(formActions == null || formActions.isEmpty())
		{
			return;
		}
		for(IAction action : formActions)
		{
			actionRegistry.registerAction(action);
			actionIds.add(action.getId());
		}
	}
}
