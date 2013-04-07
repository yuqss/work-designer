package com.work.designer;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;

public class MultiPageCommandStackListener implements CommandStackListener 
{
	private List<CommandStack> commandStacks = new ArrayList<CommandStack>(2);
	
	private DesignerEditor editor;

	public MultiPageCommandStackListener(DesignerEditor editor) 
	{
		this.editor = editor;
	}

	public void addCommandStack(CommandStack commandStack)
	{
		this.commandStacks.add(commandStack);
		commandStack.addCommandStackListener(this);
	}

	public void commandStackChanged(EventObject event)
	{
		if (((CommandStack)event.getSource()).isDirty())
		{
			this.editor.setDirty(true);
		}
		else
		{
			boolean oneIsDirty = false;
			for (Iterator<CommandStack> stacks = this.commandStacks.iterator(); stacks.hasNext();)
			{
				CommandStack stack = (CommandStack) stacks.next();
				if (!stack.isDirty())
				{
					continue;
				}
				oneIsDirty = true;
				break;
			}

			this.editor.setDirty(oneIsDirty);
		}
	}

	public void dispose()
	{
		for (Iterator<CommandStack> stacks = this.commandStacks.iterator(); stacks.hasNext();) 
		{
			((CommandStack) stacks.next()).removeCommandStackListener(this);
		}
		this.commandStacks.clear();
	}

	public void markSaveLocations()
	{
		for (Iterator<CommandStack> stacks = this.commandStacks.iterator(); stacks.hasNext();)
		{
			CommandStack stack = (CommandStack) stacks.next();
			stack.markSaveLocation();
		}
	}
}
