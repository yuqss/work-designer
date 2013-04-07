package com.work.designer.actions;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

public class FormSelectionAction extends SelectionAction 
{
	private String commandType;
	
	public FormSelectionAction(IWorkbenchPart part, String commandType)
	{
		super(part);
		this.commandType = commandType;
		setText(getCommandType());
		setToolTipText(getCommandType());
		setId(commandType);
	}

	@Override
	protected boolean calculateEnabled()
	{
	    Command cmd = createFormDesignerCommand(getSelectedObjects());
	    if (cmd == null) 
	    {
			return false;
		}
		return cmd.canExecute();
	}
	
	protected boolean isAgree(Object model)
	{
		return true;
	}

	protected Command createFormDesignerCommand(List<?> selectedEditParts)
	{
		if (selectedEditParts.isEmpty()) 
		{
			return null;
		}
		if (!(selectedEditParts.get(0) instanceof EditPart))
		{
			return null;
		}
		Request request = new Request(getCommandType());
		CompoundCommand compoundCmd = new CompoundCommand(getCommandType());

		for (int i = 0; i < selectedEditParts.size(); i++)
		{
			EditPart editPart = (EditPart) selectedEditParts.get(i);
			if (!editPart.understandsRequest(request))
			{
				continue;
			}
			if (!isAgree(editPart.getModel()))
			{
				continue;
		 	}
			Command cmd = editPart.getCommand(request);
			if (cmd == null)
			{
				continue;
			}
			compoundCmd.add(cmd);
		}

		return compoundCmd;
	}
	

	@Override
	public void run() 
	{
		execute(createFormDesignerCommand(getSelectedObjects()));
	}

	@Override
	protected void init() 
	{
		super.init();
		setText(getCommandType());
		setToolTipText(getCommandType());
		setId(getCommandType());
		setEnabled(false);
	}

	public String getCommandType() 
	{
		return commandType;
	}

	public void setCommandType(String commandType) 
	{
		this.commandType = commandType;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
