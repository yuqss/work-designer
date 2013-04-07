package com.work.designer.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.draw2d.geometry.Rectangle;

import com.work.designer.io.LayoutXmlAdapter;

@XmlRootElement(name="baseElement")
public class BaseElement extends Element
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1411896981775313634L;
	
    public static final String PROP_LOCATION = "LOCATION";
    
    public static final String PROP_SIZE = "SIZE";
    
    public static final String PROP_NAME = "NAME";
    
    public static final String PROP_LAYOUT = "LAYOUT";
    
    public static final String PROP_DISPLAYNAME = "DISPLAYNAME";
    
    public static final String PROP_ATTRS = "ATTRS";

	private String name;
	
	private String displayName;
	
	private Rectangle layout;
	
	private List<Attr> attrs = new ArrayList<Attr>();
	
	private BaseElement parent;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		firePropertyChange(PROP_NAME, null, name);
	}

	@XmlAttribute
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		firePropertyChange(PROP_DISPLAYNAME, null, displayName);
	}
	
	@XmlJavaTypeAdapter(value=LayoutXmlAdapter.class)
	@XmlAttribute
	public Rectangle getLayout() {
		return layout;
	}

	public void setLayout(Rectangle layout) {
		this.layout = layout;
		firePropertyChange(PROP_LAYOUT, null, layout);
	}

	@XmlElement(name="attr")
	public List<Attr> getAttrs()
	{
		if(attrs == null)
		{
			attrs = new ArrayList<Attr>();
		}
		return attrs;
	}

	public void setAttrs(List<Attr> attrs) {
		this.attrs = attrs;
	}
	
	public void setAttr(int i, Attr attr)
	{
		getAttrs().set(i, attr);
		firePropertyChange(PROP_ATTRS, null, attr);
	}
	
	public void addAttr(Attr attr)
	{
		getAttrs().add(attr);
		firePropertyChange(PROP_ATTRS, null, attr);
	}

	@XmlTransient
	public BaseElement getParent() {
		return parent;
	}

	public void setParent(BaseElement parent) {
		this.parent = parent;
	}
}
