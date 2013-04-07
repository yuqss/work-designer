package com.work.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.BaseElement;
import com.work.designer.model.Field;
import com.work.designer.model.Form;
import com.work.designer.model.ModelManager;
import com.work.designer.model.Task;

public class FieldBackwardCommand extends Command 
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
		List<Field> list = null;
		if(this.parent instanceof Form)
		{
			list = ((Form)getParent()).getFields();
		    int index = list.indexOf(this.model);
		    if (-1 == index)
		    {
		    	return;
		    }
		    ((Form)getParent()).swapIndex(index + 1, index);
		    Field before = list.get(index);
		    Field after = list.get(index + 1);
		    ModelManager.exchangePlaceHolder(before, after);
		}
		else if(this.parent instanceof Task)
		{
			list = ((Task)getParent()).getFields();
		    int index = list.indexOf(this.model);
		    if (-1 == index)
		    {
		    	return;
		    }
		    ((Task)getParent()).swapIndex(index + 1, index);
		    Field before = list.get(index);
		    Field after = list.get(index + 1);
		    ModelManager.exchangePlaceHolder(before, after);
		}
	}
}
