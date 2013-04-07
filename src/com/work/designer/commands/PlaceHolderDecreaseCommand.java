package com.work.designer.commands;


import org.eclipse.gef.commands.Command;

import com.work.designer.model.Field;
import com.work.designer.model.ModelManager;

public class PlaceHolderDecreaseCommand extends Command
{
	private Field model;

	public Field getModel() 
	{
		return this.model;
	}

	public void setModel(Field model) 
	{
		this.model = model;
	}

	public void execute() 
	{
		ModelManager.DecreasePlaceHolder(this.model);
	}
}
