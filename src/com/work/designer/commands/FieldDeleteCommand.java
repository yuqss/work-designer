package com.work.designer.commands;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.BaseElement;
import com.work.designer.model.Field;
import com.work.designer.model.Form;
import com.work.designer.model.Task;

public class FieldDeleteCommand extends Command
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
	
	@Override
	public void execute() 
	{
		if(this.parent instanceof Form)
		{
			((Form)this.parent).removeField(this.model);
		}
		else if(this.parent instanceof Task)
		{
			((Task)this.parent).removeField(this.model);
		}
	}

	@Override
	public void undo() 
	{
		if(this.parent instanceof Form)
		{
			((Form)this.parent).addField(this.model);
		}
		else if(this.parent instanceof Task)
		{
			((Task)this.parent).addField(this.model);
		}
	}
}
