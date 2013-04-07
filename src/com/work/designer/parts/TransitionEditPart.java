package com.work.designer.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.model.BaseElement;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.ModelPropertySource;
import com.work.designer.model.Transition;
import com.work.designer.policies.ConnectionBendpointEditPolicy;
import com.work.designer.policies.ConnectionEndpointEditPolicy;
import com.work.designer.policies.TransitionEditPolicy;

public class TransitionEditPart extends AbstractConnectionEditPart implements PropertyChangeListener
{
	private IPropertySource propertySource = null;
	
	private int dx, dy, anchorX, anchorY;
	
	private Label label;
	
	public TransitionEditPart(Transition model)
	{
		setModel(model);
	}
	  
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new TransitionEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new ConnectionBendpointEditPolicy());
	}

	protected IFigure createFigure()
	{
        final PolylineConnection result = new PolylineConnection();
        result.setForegroundColor(ColorConstants.gray);
        //此处可以设置路由器为：ManhattanConnectionRouter，为直角形路由，但是焦点无法拖动
        result.setConnectionRouter(new BendpointConnectionRouter());
        result.setTargetDecoration(new PolygonDecoration());
        result.setToolTip(new Label(getLabelName()));
        label = new Label(getLabelName());
        label.setOpaque(true);
		result.add(label, new LabelMidpointOffsetLocator(result, 0, getTransition().getOffset()));
		label.addMouseListener(new MouseListener() {
			public void mouseDoubleClicked(MouseEvent me) {
			}

			public void mousePressed(MouseEvent me) {
				anchorX = me.x;
				anchorY = me.y;
				me.consume();
				getViewer().appendSelection(TransitionEditPart.this);
			}

			public void mouseReleased(MouseEvent me) {
				me.consume();
			}
		});
		label.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent me) {
				dx += me.x - anchorX;
				dy += me.y - anchorY;
				anchorX = me.x;
				anchorY = me.y;
				Object constraint = result.getLayoutManager().getConstraint(
						label);
				if (constraint instanceof LabelMidpointOffsetLocator) {
					Point offset = new Point(dx, dy);
					((LabelMidpointOffsetLocator) constraint).setOffset(offset);
					label.revalidate();
					getTransition().setOffset(offset);
				}
				me.consume();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseHover(MouseEvent arg0) {
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			}
		});
		return result;
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
	    	Component component = ConfigManager.getComponentByGroupAndType(ConfigManager.COMPONENT_TYPE_TRANSITION, ConfigManager.COMPONENT_TYPE_TRANSITION);
	    	ModelHelper.loadAttribute((BaseElement)getModel(), component);
	    	this.propertySource = new ModelPropertySource(getTransition(), component);
	    }
	    return this.propertySource;
	}
	
    protected void refreshVisuals()
    {
    	refreshBendpoints();
    }
    
    public void setSelected(int value)
    {
        super.setSelected(value);
        if (value != EditPart.SELECTED_NONE)
            ((PolylineConnection) getFigure()).setLineWidth(2);
        else
            ((PolylineConnection) getFigure()).setLineWidth(1);
    }
    
	public void activate()
	{
		super.activate();
		getTransition().addPropertyChangeListener(this);
	}

	public void deactivate()
	{
		super.deactivate();
		getTransition().removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) 
	{
		String property = evt.getPropertyName();
		if(Transition.PROP_BENDPOINT.equals(property))
			refreshBendpoints();
		else if(Transition.PROP_DISPLAYNAME.equals(property))
			refreshLabel();
		else
		{
			refreshVisuals();
			refreshLabel();
		}
	}
	
	protected void refreshBendpoints()
	{
        List<Point> bendpoints = getTransition().getBendpoints();
        List<Point> constraint = new ArrayList<Point>();
        for (int i = 0; i < bendpoints.size(); i++) {
            constraint.add(new AbsoluteBendpoint((Point) bendpoints.get(i)));
        }
        getConnectionFigure().setRoutingConstraint(constraint);
	}
	
	protected void refreshLabel()
	{
		this.label.setText(getLabelName());
	}
	
	private Transition getTransition()
	{
		return (Transition)getModel();
	}
	
	private String getLabelName()
	{
		return getTransition().getDisplayName();
	}
}
