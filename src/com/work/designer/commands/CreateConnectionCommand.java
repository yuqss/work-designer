package com.work.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.work.designer.config.ConfigManager;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.Task;
import com.work.designer.model.Transition;

public class CreateConnectionCommand extends Command 
{
	private Task source;
	
	private Task target;
	
	private Transition connection;
	
	public Object point;

	public boolean canExecute() {
		if (source.equals(target))
			return false;
		if(source != null && source.getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_END))
		{
			return false;
		}
		
		if(target != null && target.getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_START))
		{
			return false;
		}
		
		List<Transition> transistions = source.getOutTransitions();
		for (int i = 0; i < transistions.size(); i++) {
			if (((Transition)transistions.get(i)).getTarget().equals(target))
				return false;
		}
		return true;
	}

	public void execute() 
	{
		this.target.addInput(this.connection);
		this.source.addOutput(this.connection);
		this.connection.setSource(this.source);
		this.connection.setTarget(this.target);
		this.connection.setParent(this.source.getParent());
		String name = ModelHelper.getModelName(this.connection, ConfigManager.COMPONENT_TYPE_TRANSITION);
		this.connection.setName(name);
		this.connection.setDisplayName("to " + this.target.getDisplayName());
	}

	public void redo() {
		execute();
	}

	public void undo() {
		this.source.removeOutput(this.connection);
		this.target.removeInput(this.connection);
	}

	public void setTarget(Task target) {
		this.target = target;
	}

	public void setTransition(Transition r) {
		this.connection = r;
	}

	public Transition getTransition() {
		return this.connection;
	}

	public void setSource(Task source) {
		this.source = source;
		if(this.connection != null)
		{
			this.connection.setSource(this.source);
		}
	}
}
