package com.work.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.Attr;
import com.work.designer.model.Field;

public class LineBRSetCommand extends Command 
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
		List<Attr> attrs = this.model.getAttrs();
		boolean isExists = false;
		for (int i = 0; i < attrs.size(); i++)
		{
			Attr attr = (Attr) attrs.get(i);
			if (!"lineBR".equalsIgnoreCase(attr.getName()))
			{
				continue;
			}
			isExists = true;
			if("true".equalsIgnoreCase(attr.getValue()))
			{
				return;
			}
			attr.setValue("true");
			this.model.setAttr(i, attr);
			return;
		}
		
		if(!isExists)
		{
			Attr attr = new Attr();
			attr.setName("lineBR");
			attr.setValue("true");
			this.model.addAttr(attr);
		}
	}
}
