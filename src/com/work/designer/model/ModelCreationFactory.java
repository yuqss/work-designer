package com.work.designer.model;

import org.eclipse.gef.requests.CreationFactory;

import com.work.designer.config.Component;

public class ModelCreationFactory implements CreationFactory 
{
	private Class<?> template;
	
	private Component component;
	
	public ModelCreationFactory(Component component, Class<?> clazz)
	{
		this.component = component;
		this.template = clazz;
	}

	@Override
	public Object getNewObject() 
	{
		if(this.template == null)
		{
			return null;
		}
		else
		{
			if(this.template == Task.class)
			{
				Task task = new Task();
		        task.setType(component.getType());
		        return task;
			}
			else if(this.template == Transition.class)
			{
				Transition transition = new Transition();
				return transition;
			}
			else if(this.template == Form.class)
			{
				return new Form();
			}
			else if(this.template == Field.class)
			{
				Field field = new Field();
				field.setDisplayName(component.getDisplayName());
				field.setType(component.getType());
				
				return field;
			}
		}
		return null;
	}

	@Override
	public Object getObjectType() 
	{
		return this.component == null ? "" : this.component.getType();
	}
}
