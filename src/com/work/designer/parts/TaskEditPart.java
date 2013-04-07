package com.work.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.ui.views.properties.IPropertySource;

import com.work.designer.DesignerEditor;
import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.figures.AbstractElementFigure;
import com.work.designer.figures.ExclusiveGatewayFigure;
import com.work.designer.figures.ParallelGatewayFigure;
import com.work.designer.figures.StartEventFigure;
import com.work.designer.figures.TaskFigure;
import com.work.designer.figures.TerminateEndEventFigure;
import com.work.designer.model.BaseElement;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.ModelPropertySource;
import com.work.designer.model.Task;
import com.work.designer.model.Transition;
import com.work.designer.policies.ElementDirectEditPolicy;
import com.work.designer.policies.ProcessGraphicalNodeEditPolicy;
import com.work.designer.policies.TaskComponentEditPolicy;

public class TaskEditPart extends AbstractGraphicalEditPart implements NodeEditPart, PropertyChangeListener 
{
	private DirectEditManager manager;
	
	private DesignerEditor editor;
	
	private IPropertySource propertySource = null;
	
	private AbstractElementFigure figure = null;
	
	public TaskEditPart(DesignerEditor editor, Task model)
	{
		this.editor = editor;
		setModel(model);
	}

	@Override
	protected IFigure createFigure() 
	{
		if(getTask().getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_START))
			figure = new StartEventFigure();
		else if(getTask().getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_END))
			figure = new TerminateEndEventFigure();
		else if(getTask().getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_DECISION))
			figure = new ExclusiveGatewayFigure();
		else if(getTask().getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_FORK))
			figure = new ParallelGatewayFigure();
		else if(getTask().getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_JOIN))
			figure = new ParallelGatewayFigure();
		else
			figure = new TaskFigure(getTask());
		return figure;
	}
	
	private Task getTask()
	{
		return (Task)getModel();
	}

	@Override
	protected void createEditPolicies()
	{
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ElementDirectEditPolicy());
	    installEditPolicy(EditPolicy.COMPONENT_ROLE, new TaskComponentEditPolicy());
	    installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ProcessGraphicalNodeEditPolicy());
	}

	public void refreshVisuals()
	{
        AbstractElementFigure figure = (AbstractElementFigure) getFigure();
        figure.setText(getTask().getDisplayName());
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, getTask().getLayout());
        super.refreshVisuals();
	}

	@SuppressWarnings("unchecked")
	public List<?> getModelChildren()
	{
		return new ArrayList();
	}

	public void activate()
	{
		if (isActive())
		{
			return;
		}
		((Task)getModel()).addPropertyChangeListener(this);
		super.activate();
	}

	public void deactivate()
	{
		if (!isActive())
		{
			return;
		}
		((Task)getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class key) 
	{
		if (IPropertySource.class == key)
		{
			return getPropertySource();
		}
		return super.getAdapter(key);
	}

	protected IPropertySource getPropertySource()
	{
		if (this.propertySource == null)
		{
	    	Component component = ConfigManager.getComponentByGroupAndType(ConfigManager.COMPONENT_TYPE_TASK, getTask().getType());
	    	ModelHelper.loadAttribute((BaseElement)getModel(), component);
			this.propertySource = new ModelPropertySource(getTask(), component);
		}
		return this.propertySource;
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(getModel().getClass());
	}
	
	@Override
	protected List<Transition> getModelSourceConnections()
	{
		return getTask().getOutTransitions();
	}

	@Override
	protected List<Transition> getModelTargetConnections() 
	{
		return getTask().getInTransitions();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}
	
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	public DesignerEditor getEditor() {
		return editor;
	}

	public void setEditor(DesignerEditor editor) {
		this.editor = editor;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
        if (evt.getPropertyName().equals(Task.PROP_LAYOUT))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Task.PROP_NAME))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Task.PROP_DISPLAYNAME))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Task.PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(Task.PROP_OUTPUTS))
            refreshSourceConnections();
	}

	@Override
	public void performRequest(Request request)
	{
		if (request.getType() == RequestConstants.REQ_OPEN) 
            doubleClicked();
		else if (request.getType() == RequestConstants.REQ_DIRECT_EDIT)
            performDirectEdit();
		else 
            super.performRequest(request);
	}
	
    protected void performDirectEdit()
    {
    	AbstractElementFigure figure = (AbstractElementFigure)getFigure();
    	Label label = figure.getLabel();
    	if (label == null)
    		return;
        if (manager == null)
            manager = new ElementDirectEditManager(this, new LabelCellEditorLocator(label));
        manager.show();
    }
	
    protected void doubleClicked()
    {
    	if(getTask().getType() != null && getTask().getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_TASK))
    	{
			if (-1 != this.editor.getFormEditPageID())
			{
				this.editor.removeFormEditPage();
			}
			if (-1 == this.editor.getFormEditPageID()) 
			{
				this.editor.createFormEditPage(getTask());
			}
			this.editor.setActivePage(this.editor.getFormEditPageID());
    	}
    }
}
