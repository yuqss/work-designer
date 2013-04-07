package com.work.designer.config;

import java.util.ArrayList;
import java.util.List;

public class Container 
{
	private List<Component> components = new ArrayList<Component>();

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}
}
