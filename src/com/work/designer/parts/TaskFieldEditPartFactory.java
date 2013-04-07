package com.work.designer.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.work.designer.DesignerEditor;
import com.work.designer.model.*;

public class TaskFieldEditPartFactory implements EditPartFactory
{
	private DesignerEditor editor;
	
	public TaskFieldEditPartFactory(DesignerEditor editor)
	{
		this.editor = editor;
	}
	
	@Override
	public EditPart createEditPart(EditPart context, Object model)
	{
	    EditPart part = null;
		if ((model instanceof Field))
		{
			part = new FieldEditPart((Field)model);
		} 
		else if(model instanceof Task)
		{
			part = new TaskFieldEditPart(this.editor, (Task)model);
		}
		if(part != null)
		{
			part.setModel(model);
		}
		return part;
	}

}
