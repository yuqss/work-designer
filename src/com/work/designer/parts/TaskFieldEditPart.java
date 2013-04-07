package com.work.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.work.designer.DesignerEditor;
import com.work.designer.model.Task;
import com.work.designer.policies.FormXYEditLayoutPolicy;

public class TaskFieldEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener 
{
	private DesignerEditor editor;
	
	public TaskFieldEditPart(DesignerEditor editor, Object parent)
	{
	    setModel(parent);
	    this.editor = editor;
	}

	@Override
	protected IFigure createFigure()
	{
	    Figure f = new FreeformLayer();
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
	protected List<?> getModelChildren()
	{
		return getTask().getFields();
	}
	
	public boolean isLayout() {
		if (getRoot() == null)
			return false;
		return getRoot().equals(getParent());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
        String prop = evt.getPropertyName();
        if(this.editor != null) this.editor.setDirty(true);
        if (Task.PROP_DISPLAYNAME.equals(prop) || Task.PROP_LOCATION.equals(prop))
        {
        	refreshVisuals();
        }
        else if(Task.PROP_FIELDS.equals(prop))
        {
        	refreshChildren();
        	refreshVisuals();
        	EditPartHelper.refreshAllChildren(this);
        }
	}
	
	private Task getTask()
	{
		return (Task)getModel();
	}
	
	@Override
	public void activate()
	{
	    if (isActive())
	    {
	    	return;
	    }
	    getTask().addPropertyChangeListener(this);
	    super.activate();
	}
	
	@Override
    public void deactivate()
	{
	    if (isActive())
	    {
	    	return;
	    }
	    getTask().removePropertyChangeListener(this);
        super.deactivate();
	}
}
