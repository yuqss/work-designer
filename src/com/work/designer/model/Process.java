package com.work.designer.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="process")
public class Process extends BaseElement
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3354375166425842591L;
	
	public static String PROP_TASK = "Process";

	private List<Task> tasks = new ArrayList<Task>();
	
    public void addTask(Task task) {
    	tasks.add(task);
        fireStructureChange(PROP_TASK, tasks);
    }

    public void removeTask(Task task) {
    	tasks.remove(task);
        fireStructureChange(PROP_TASK, tasks);
    }

    @XmlElement(name="task")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
