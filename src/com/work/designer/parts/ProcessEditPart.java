package com.work.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import com.work.designer.DesignerEditor;
import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.model.BaseElement;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.ModelPropertySource;
import com.work.designer.model.Process;
import com.work.designer.policies.ProcessXYLayoutEditPolicy;

public class ProcessEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener 
{
	private DesignerEditor editor;
	
	private IPropertySource propertySource = null;
	
	public ProcessEditPart(DesignerEditor editor, Process model)
	{
		this.editor = editor;
		setModel(model);
	}

	@Override
	protected IFigure createFigure() 
	{
	    FreeformLayer layer = new FreeformLayer();
	    layer.setLayoutManager(new FreeformLayout());
	    return layer;
	}
	
	public Process getProcess() 
	{
		return (Process) getModel();
	}
	
	@Override
	public void activate()
	{
	    if (isActive())
	    {
	    	return;
	    }
	    getProcess().addPropertyChangeListener(this);
	    super.activate();
	}
	
	@Override
    public void deactivate()
	{
	    if (isActive())
	    {
	    	return;
	    }
	    getProcess().removePropertyChangeListener(this);
        super.deactivate();
	}
	
	@Override
	protected void createEditPolicies() 
	{
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ProcessXYLayoutEditPolicy());
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy());
	}

	@Override
	protected List<BaseElement> getModelChildren() 
	{
	    List<BaseElement> l = new ArrayList<BaseElement>();
	    l.addAll(getProcess().getTasks());
	    return l;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if (Process.PROP_TASK.equals(prop))
            refreshChildren();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) 
	{
	    if (IPropertySource.class == key) 
	    {
	        return getPropertySource();
	    }
	    if (key == SnapToHelper.class) {
            List<SnapToHelper> helpers = new ArrayList<SnapToHelper>();
            if (Boolean.TRUE.equals(getViewer().getProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED))) {
                helpers.add(new SnapToGeometry(this));
            }
            if (Boolean.TRUE.equals(getViewer().getProperty(SnapToGrid.PROPERTY_GRID_ENABLED))) {
                helpers.add(new SnapToGrid(this));
            }
            if(helpers.size()==0) {
                return null;
            } else {
                return new CompoundSnapToHelper(helpers.toArray(new SnapToHelper[0]));
            }
        }

	    return super.getAdapter(key);
	}
	
	protected IPropertySource getPropertySource() 
	{
		if (this.propertySource == null) 
		{
	    	Component component = ConfigManager.getComponentByGroupAndType(ConfigManager.COMPONENT_TYPE_PROCESS, ConfigManager.COMPONENT_TYPE_PROCESS);
	    	ModelHelper.loadAttribute((BaseElement)getModel(), component);
			this.propertySource = new ModelPropertySource((BaseElement)getModel(), component);
		}
		return this.propertySource;
	}

	public DesignerEditor getEditor() {
		return editor;
	}

	public void setEditor(DesignerEditor editor) {
		this.editor = editor;
	}

	@Override
	public void performRequest(Request req)
	{
		System.out.println("ProcessEditPart performRequest=" + req.getType());

		super.performRequest(req);
	}
}
