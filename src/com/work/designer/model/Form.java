package com.work.designer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="form")
public class Form extends BaseElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1933407324643767583L;
	
	public static final String PROP_FORM = "Form";
	
	public static final String PROP_LIST = "FormFields";

	private List<Field> fields = new ArrayList<Field>();
	
    public void addField(Field field) {
    	fields.add(field);
        fireStructureChange(PROP_FORM, fields);
    }

    public void removeField(Field field) {
    	fields.remove(field);
        fireStructureChange(PROP_FORM, fields);
    }
    
    public void swapIndex(int i, int j)
    {
    	Collections.swap(fields, i, j);
    	fireStructureChange(PROP_LIST, fields);
    }

	@XmlElement(name="field")
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
