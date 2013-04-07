package com.work.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.work.designer.FormDesignerEditor;
import com.work.designer.model.Field;
import com.work.designer.model.Form;
import com.work.designer.policies.FormXYEditLayoutPolicy;

public class FormEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener
{
	private FormDesignerEditor editor;
	
	public FormEditPart(FormDesignerEditor editor, Form model)
	{
		setModel(model);
		this.editor = editor;
	}
	
	@Override
	protected IFigure createFigure()
	{
		IFigure f = new FreeformLayer();
	    f.setLayoutManager(new FreeformLayout());
	    f.setOpaque(true);
		return f;
	}

	@Override
	protected void createEditPolicies() 
	{
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FormXYEditLayoutPolicy());
	}

	@Override
	protected List<Field> getModelChildren() 
	{
		return getForm().getFields();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
        String prop = evt.getPropertyName();
        if(this.editor != null) this.editor.setDirty(true);
        if (Form.PROP_FORM.equals(prop))
        {
            refreshChildren();
        }
        else if(Form.PROP_LIST.equals(prop))
        {
        	refreshChildren();
        	refreshVisuals();
        	EditPartHelper.refreshAllChildren(this);
        }
	}
	
	public Form getForm() 
	{
		return (Form)getModel();
	}
	
	@Override
	public void activate()
	{
	    if (isActive())
	    {
	    	return;
	    }
	    getForm().addPropertyChangeListener(this);
	    super.activate();
	}
	
	@Override
    public void deactivate()
	{
	    if (isActive())
	    {
	    	return;
	    }
	    getForm().removePropertyChangeListener(this);
        super.deactivate();
	}
}
