package com.work.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.figures.FieldFigure;
import com.work.designer.model.Attr;
import com.work.designer.model.BaseElement;
import com.work.designer.model.Field;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.ModelManager;
import com.work.designer.model.ModelPropertySource;
import com.work.designer.policies.FieldContainerEditPolicy;
import com.work.designer.policies.FieldFlowLayoutEditPolicy;
import com.work.designer.policies.FieldOrderedLayoutPolicy;

public class FieldEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener
{
	private IPropertySource propertySource = null;
	
	public FieldEditPart(Field model)
	{
		setModel(model);
	}
	
	@Override
	protected IFigure createFigure()
	{
	    FieldFigure fieldFigure = new FieldFigure(getField());
	    Point p = EditPartHelper.getCurFigureLocation(this, fieldFigure);
	    fieldFigure.setLocation(p);
	    ((Field)getModel()).setLayout(fieldFigure.getBounds());
	    return fieldFigure;
	}

	@Override
	protected void createEditPolicies() 
	{
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new FieldContainerEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FieldFlowLayoutEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new FieldOrderedLayoutPolicy());
	}
	
	private Field getField()
	{
		return (Field)getModel();
	}

	@Override
	protected void refreshVisuals()
	{
		refreshFieldFigure(getFigure());
		refreshPlaceholder((Figure)getFigure());
		getFigure().repaint();
	}
	

	private void refreshFieldFigure(IFigure figure) 
	{
		FieldFigure f = (FieldFigure) figure;
		f.setText(getField().getDisplayName());

		List<EditPart> listRestEditParts = EditPartHelper.getRestEditParts(this);
		EditPartHelper.refreshEditPartsLocation(listRestEditParts);
	}
	
	private void refreshPlaceholder(Figure figure)
	{
		int index = ModelManager.getPlaceHolderAttrIndex(getField());
		if (-1 == index) 
		{
			return;
		}
		Attr attr = (Attr)(getField().getAttrs()).get(index);
		int placeholder = Integer.parseInt(attr.getValue());
		int iWidth = 200 * placeholder + 1;
		getFigure().setSize(iWidth, 22);
	}
	
	public void refreshLocation() 
	{
		Point p = EditPartHelper.getCurFigureLocation(this);
		if (p == null) 
		{
			p = new Point(16, 16);
		}
		getFigure().setLocation(p);
	}	

	@SuppressWarnings("unchecked")
	@Override
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
			Component component = ConfigManager.getComponentByGroupAndType(ConfigManager.COMPONENT_TYPE_FIELD, getField().getType());
			ModelHelper.loadAttribute((BaseElement)getModel(), component);
			this.propertySource = new ModelPropertySource((BaseElement)getModel(), component);
		}
		return this.propertySource;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
        if (evt.getPropertyName().equals(Field.PROP_LOCATION))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Field.PROP_NAME))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Field.PROP_DISPLAYNAME))
            refreshVisuals();
        else if (evt.getPropertyName().equals(Field.PROP_ATTRS))
        {
        	refreshVisuals();
        	EditPartHelper.refreshAllEditPartLocation(this);
        }
	}
	
	public boolean isLayout()
	{
		if (getRoot() == null)
			return false;
		return getRoot().equals(getParent());
	}
	
	public void activate()
	{
		if (isActive())
		{
			return;
		}
		getField().addPropertyChangeListener(this);
		super.activate();
	}

	public void deactivate()
	{
		if (!isActive())
		{
			return;
		}
		getField().removePropertyChangeListener(this);
		super.deactivate();
	}
}
