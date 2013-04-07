package com.work.designer.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.work.designer.FormDesignerEditor;
import com.work.designer.model.*;

public class FormDesignerEditPartFactory implements EditPartFactory
{
	private FormDesignerEditor editor;
	
	public FormDesignerEditPartFactory(FormDesignerEditor editor)
	{
		this.editor = editor;
	}
	
	@Override
	public EditPart createEditPart(EditPart context, Object model)
	{
	    EditPart part = null;
		if ((model instanceof Form)) {
			part = new FormEditPart(this.editor, (Form)model);
		} else if ((model instanceof Field)) {
			part = new FieldEditPart((Field)model);
		}
		if(part != null)
		{
			part.setModel(model);
		}
		return part;
	}

}
