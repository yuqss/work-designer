package com.work.designer.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="field")
public class Field extends BaseElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4169980121781395866L;
	
	private String type;

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
