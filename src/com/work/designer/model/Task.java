package com.work.designer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="task")
public class Task extends BaseElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 862281580390510339L;
	
    public static final String PROP_INPUTS = "INPUTS";

    public static final String PROP_OUTPUTS = "OUTPUTS";
    
    public static final String PROP_FIELDS = "FIELDS";
    
	private String type;
	
	private List<Field> fields = new ArrayList<Field>();
	
	private List<Transition> inTransitions = new ArrayList<Transition>();
	
	private List<Transition> outTransitions = new ArrayList<Transition>();

    public void addInput(Transition connection) {
        this.inTransitions.add(connection);
        fireStructureChange(PROP_INPUTS, connection);
    }

    public void addOutput(Transition connection) {
        this.outTransitions.add(connection);
        fireStructureChange(PROP_OUTPUTS, connection);
    }
    
	public void removeInput(Transition connection) {
        this.inTransitions.remove(connection);
        fireStructureChange(PROP_INPUTS, connection);
    }

    public void removeOutput(Transition connection) {
        this.outTransitions.remove(connection);
        fireStructureChange(PROP_OUTPUTS, connection);
    }
    
    public void addField(Field field)
    {
    	this.fields.add(field);
    	fireStructureChange(PROP_FIELDS, fields);
    }
    
    public void removeField(Field field)
    {
    	this.fields.remove(field);
    	fireStructureChange(PROP_FIELDS, fields);
    }
    
    public void swapIndex(int i, int j)
    {
    	Collections.swap(fields, i, j);
    	fireStructureChange(PROP_FIELDS, fields);
    }
    
    @XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlTransient
	public List<Transition> getInTransitions() {
		return inTransitions;
	}

	public void setInTransitions(List<Transition> inTransitions) {
		this.inTransitions = inTransitions;
	}

	@XmlTransient
	public List<Transition> getOutTransitions() {
		return outTransitions;
	}

	public void setOutTransitions(List<Transition> outTransitions) {
		this.outTransitions = outTransitions;
	}

	@XmlElement(name="field")
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
