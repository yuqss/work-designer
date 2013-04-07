package com.work.designer.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="attr")
public class Attr extends Element
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6432248669768438286L;

	private String name;
	
	private String value;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
