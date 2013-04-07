package com.work.designer.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.draw2d.geometry.Point;

@XmlTransient
public class Transition extends BaseElement 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1100956209119361413L;
	
	public static final String PROP_BENDPOINT = "BENDPOINTS";
	
	public static final String PROP_OFFSET = "OFFSET";

	private Task source;
	
	private Task target;
	
	private Point offset;
	
	private List<Point> bendpoints = new ArrayList<Point>();
	
	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
		firePropertyChange(PROP_OFFSET, null, null);
	}

	public List<Point> getBendpoints()
	{
		return bendpoints;
	}

	public void setBendpoints(List<Point> bendpoints)
	{
		this.bendpoints = bendpoints;
	}

	public void addBendpoint(int index, Point point)
	{
		getBendpoints().add(index, point);
		firePropertyChange(PROP_BENDPOINT, null, null);
	}

	public void removeBendpoint(int index) {
		getBendpoints().remove(index);
		firePropertyChange(PROP_BENDPOINT, null, null);
	}
	
    public void replaceBendpoint(int index, Point point) {
        bendpoints.set(index, point);
        firePropertyChange(PROP_BENDPOINT, null, null);
    }

	@XmlTransient
	public Task getSource() {
		return source;
	}

	public void setSource(Task source) {
		this.source = source;
	}

	@XmlTransient
	public Task getTarget() {
		return target;
	}

	public void setTarget(Task target) {
		this.target = target;
	}
}
