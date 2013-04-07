package com.work.designer.config;

public class Property
{
	public static final String TYPE_LIST = "list";
	
	private String group;
	
	private String name;
	
	private String type;

	private String defaultValue;
	
	private String values;
	
	private String visible;
	
	private String validator;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getValues() {
		return values == null ? "" : values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getVisible() {
		return visible == null ? "" : visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getValidator() {
		return validator == null ? "" : validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getDefaultValue() {
		return defaultValue == null ? "" : defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
