package com.work.designer.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public abstract class Element implements Cloneable, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8601132591065517723L;
	
	PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        listeners.addPropertyChangeListener(l);
    }

    protected void firePropertyChange(String prop, Object old, Object newValue)
    {
        listeners.firePropertyChange(prop, old, newValue);
    }

    protected void fireStructureChange(String prop, Object child)
    {
        listeners.firePropertyChange(prop, null, child);
    }

    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        listeners.removePropertyChangeListener(l);
    }

}