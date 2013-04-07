package com.work.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.work.designer.config.ConfigManager;
import com.work.designer.model.*;
import com.work.designer.model.Process;

public class CreateTaskCommand extends Command 
{
	private Process process;
	
	private Task task;
	
	public CreateTaskCommand(Process process, Object obj)
	{
		this.process = process;
		this.task = (Task)obj;
	}

	public void execute() 
	{
		this.process.addTask(this.task);
		this.task.setParent(process);
		this.task.setName(ModelHelper.getModelName(this.task, task.getType()));
		this.task.setDisplayName(this.task.getName());
	}

	@Override
	public void undo() 
	{
		this.process.removeTask(this.task);
	}

	@Override
	public boolean canExecute() 
	{
		if(this.task.getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_START))
		{
			List<Task> tasks = this.process.getTasks();
			for(Task task : tasks)
			{
				if(task.getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_START))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean canUndo()
	{
		if(this.process == null || this.task == null)
		{
			return false;
		}
		return this.process.getTasks().contains(this.task);
	}
	
	
}
