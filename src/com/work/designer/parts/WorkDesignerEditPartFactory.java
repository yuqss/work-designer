package com.work.designer.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.work.designer.DesignerEditor;
import com.work.designer.model.*;
import com.work.designer.model.Process;

public class WorkDesignerEditPartFactory implements EditPartFactory
{
	private DesignerEditor editor;
	
	public WorkDesignerEditPartFactory(DesignerEditor editor)
	{
		this.editor = editor;
	}
	
	@Override
	public EditPart createEditPart(EditPart context, Object model)
	{
	    EditPart part = null;
		if ((model instanceof Process)) {
			part = new ProcessEditPart(this.editor, (Process) model);
		} else if ((model instanceof Task)) {
			part = new TaskEditPart(this.editor, (Task) model);
		} else if ((model instanceof Transition)) {
			part = new TransitionEditPart((Transition)model);
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
