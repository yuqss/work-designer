package com.work.designer.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.work.designer.model.BaseElement;
import com.work.designer.model.Field;
import com.work.designer.model.Form;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.Task;
import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;

public class FieldCreateCommand extends Command 
{
	private BaseElement parent;
	
	private Field field;
	
	public FieldCreateCommand()
	{
		super();
		field = null;
		parent = null;
	}
	
	public void setField(Object o)
	{
		if(o instanceof Field)
			this.field = (Field)o;
	}

	public void setParent(BaseElement o)
	{
		this.parent = o;
	}
	
	public void setLayout(Rectangle rect)
	{
		if(this.field == null)
			return;
//		this.field.setLocation(rect.getLocation());
//		this.field.setSize(rect.getSize());
		this.field.setLayout(rect);
	}
	
	@Override
	public boolean canExecute() {
		if(this.field == null || this.parent == null)
			return false;
		return true;
	}

	@Override
	public boolean canUndo() {
		if(this.field == null || this.parent == null)
			return false;
		if(this.parent instanceof Form)
		{
			return ((Form)this.parent).getFields().contains(this.field);
		}
		else if(this.parent instanceof Task)
		{
			return ((Task)this.parent).getFields().contains(this.field);
		}
		else
		{
			return false;
		}
	}

	@Override
	public void execute() 
	{
	    if (getLabel() == null || getLabel().trim().length() == 0)
	    {
	    	return;
	    }
		Component component = ConfigManager.getComponent(ConfigManager.COMPONENT_TYPE_FIELD, getLabel()); 
		this.field.setType(getLabel());
		this.field.setDisplayName("ÐÂ½¨" + component.getDisplayName());
		if(this.parent instanceof Form)
		{
			((Form)this.parent).addField(this.field);
		}
		else if(this.parent instanceof Task)
		{
			((Task)this.parent).addField(this.field);
		}
		this.field.setParent(this.parent);
		field.setName(ModelHelper.getModelName(field, component.getType()));
	}

	@Override
	public void undo() {
		if(this.parent instanceof Form)
		{
			((Form)this.parent).removeField(this.field);
		}
		else if(this.parent instanceof Task)
		{
			((Task)this.parent).removeField(this.field);
		}
	}

}
