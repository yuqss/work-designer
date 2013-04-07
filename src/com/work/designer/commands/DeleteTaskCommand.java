package com.work.designer.commands;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.Process;
import com.work.designer.model.Task;

public class DeleteTaskCommand extends Command
{
	private Process process;
	
	private Task task;
	
	public DeleteTaskCommand(Process process, Object obj)
	{
		this.process = process;
		this.task = (Task)obj;
	}

	public void execute() 
	{
		this.process.removeTask(this.task);
	}

	@Override
	public void undo() 
	{
		this.process.addTask(this.task);
	}

	@Override
	public boolean canExecute() 
	{
		return true;
	}

	@Override
	public boolean canUndo()
	{
		return true;
	}
	
	
}
