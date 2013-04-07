package com.work.designer.config;

import java.util.ArrayList;
import java.util.List;

public class Component
{
	private String name;
	
	private String displayName;
	
	private String type;
	
	private String group;
	
	private String descript;
	
	private String iconSmall;
	
	private String iconLarge;
	
	private String visible;
	
	private List<Property> properties = new ArrayList<Property>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getIconSmall() {
		return iconSmall;
	}

	public void setIconSmall(String iconSmall) {
		this.iconSmall = iconSmall;
	}

	public String getIconLarge() {
		return iconLarge;
	}

	public void setIconLarge(String iconLarge) {
		this.iconLarge = iconLarge;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
}
