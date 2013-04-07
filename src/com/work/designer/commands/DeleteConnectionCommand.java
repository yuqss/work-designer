package com.work.designer.commands;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.Task;
import com.work.designer.model.Transition;

public class DeleteConnectionCommand extends Command {

	private Task source;
	private Task target;
	private Transition transition;

	public void execute()
	{
		source.removeOutput(transition);
		target.removeInput(transition);
		transition.setSource(null);
		transition.setTarget(null);
	}

	public void setSource(Task activity) {
		source = activity;
	}

	public void setTarget(Task activity) {
		target = activity;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

	public void undo() 
	{
		transition.setSource(source);
		transition.setTarget(target);
		source.addOutput(transition);
		target.addInput(transition);
	}

}
