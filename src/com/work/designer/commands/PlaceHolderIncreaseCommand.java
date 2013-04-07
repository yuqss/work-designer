package com.work.designer.commands;


import org.eclipse.gef.commands.Command;

import com.work.designer.model.BaseElement;
import com.work.designer.model.Field;
import com.work.designer.model.ModelManager;

public class PlaceHolderIncreaseCommand extends Command 
{
	private BaseElement parent;
	
	private Field model;

	public Field getModel()
	{
		return this.model;
	}

	public void setModel(Field model)
	{
		this.model = model;
	}

	public BaseElement getParent()
	{
		return this.parent;
	}

	public void setParent(BaseElement parent) 
	{
		this.parent = parent;
	}

	public void execute() 
	{
		ModelManager.IncreasePlaceHolder(this.model);
	}
}
