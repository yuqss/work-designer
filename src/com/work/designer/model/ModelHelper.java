package com.work.designer.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.Element;
import org.eclipse.draw2d.geometry.Point;

import com.work.designer.config.Component;
import com.work.designer.config.Property;

public class ModelHelper
{
	public static String encoding = "GBK";
	
	public static Map<String, Integer> countMap = new HashMap<String, Integer>();
	
	/**
	 * 模型处理
	 * 根据xml文件，处理模型中的transition元素
	 * @param process
	 * @param file
	 */
	@SuppressWarnings("unchecked")
	public static void processModel(Process process, File file)
	{
        try
        {
            List<Task> tasks = process.getTasks();
            if(tasks == null || tasks.isEmpty()) return;
            
            SAXReader reader = new SAXReader();
            reader.setEncoding(encoding);
            Document document = reader.read(file);
            Element rootElm = document.getRootElement();
            
            Element sourceE = null;
            Element targetE = null;
            Element transiE = null;
    		List<Element> taskEs = rootElm.elements("task");
    		for(Element taskE : taskEs)
    		{
    			List<Element> transitionEs = taskE.elements("transition");
        		for(Element transitionE : transitionEs)
        		{
        			sourceE = taskE;
        			transiE = transitionE;
        			String to = transitionE.attributeValue("to");
        			for(Element taskEE : taskEs)
        			{
        				String taskName = taskEE.attributeValue("name");
        				if(to != null && to.equalsIgnoreCase(taskName))
        				{
        					targetE = taskEE;
        				}
        			}
        			buildTransition(process, sourceE, targetE, transiE);
        		}
    		}
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	/**
	 * XML处理
	 * 根据模型对象，处理Transition对象，转换为xml元素
	 * @param process
	 * @param file
	 */
	public static void processXML(Process process, File file)
	{
        try
        {
            SAXReader reader = new SAXReader();
            reader.setEncoding(encoding);
            Document document = reader.read(file);
            Element rootElm = document.getRootElement();
            
            List<Task> tasks = process.getTasks();
            for(Task task : tasks)
            {
            	if(!task.getOutTransitions().isEmpty())
            	{
            		Element e = getElementByTask(task, rootElm);
            		buildTransition(task.getOutTransitions(), e);
            	}
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(encoding);
            XMLWriter writer = new XMLWriter(new FileWriter(file),format); 
            writer.write(document);
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public static void loadAttribute(BaseElement model, Component component)
	{
		List<Property> listProps = component.getProperties();
		if (listProps == null) return;
		boolean isExists = false;
		for (int i = 0; i < listProps.size(); i++) 
		{
			isExists = false;
			Property prop = (Property)listProps.get(i);
			if(prop.getName().equals("name") || prop.getName().equals("displayName"))
			{
				continue;
			}
			for(Attr attr : model.getAttrs())
			{
				if(attr.getName() != null && attr.getName().equalsIgnoreCase(prop.getName()))
				{
					isExists = true;
					break;
				}
			}
			if(!isExists)
			{
				Attr attr = new Attr();
				attr.setName(prop.getName());
				attr.setValue(prop.getDefaultValue());
				model.addAttr(attr);
			}
		}
	}
	
	/**
	 * 构建模型级联父子关系，设置parent属性
	 * @param model
	 */
	public static void buildCascade(BaseElement model)
	{
		if(model == null) return;
		if(model instanceof Process)
		{
			Process process = (Process)model;
			List<Task> tasks = process.getTasks();
			for(Task task : tasks)
			{
				task.setParent(process);
				List<Field> fields = task.getFields();
				for(Field field : fields)
				{
					field.setParent(task);
				}
			}
		}
		else if(model instanceof Form)
		{
			Form form = (Form)model;
			List<Field> fields = form.getFields();
			for(Field field : fields)
			{
				field.setParent(form);
			}
		}
	}
	
	/**
	 * 根据模型对象、模型组件类型获取新建模型的名称
	 * @param model
	 * @param type
	 * @return
	 */
	public static String getModelName(BaseElement model, String type)
	{
		try
		{
		if(model == null || type == null) return "";
		if(model instanceof Field)
		{
			BaseElement parent = model.getParent();
			if(parent != null && parent instanceof Form)
			{
				Form form = (Form)parent;
				List<Field> fields = form.getFields();
				if(fields != null && !fields.isEmpty())
				{
					return getModelName(fields, type.toLowerCase(), 1);
				}
			}
			else if(parent instanceof Task)
			{
				Task task = (Task)parent;
				List<Field> fields = task.getFields();
				if(fields != null && !fields.isEmpty())
				{
					return getModelName(fields, type.toLowerCase(), 1);
				}
			}
		}
		else if(model instanceof Task)
		{
			Process process = (Process)model.getParent();
			List<Task> tasks = process.getTasks();
			if(tasks != null && !tasks.isEmpty())
			{
				return getModelName(tasks, type.toLowerCase(), 1);
			}
		}
		else if(model instanceof Transition)
		{
			Process process = (Process)model.getParent();
			List<Task> tasks = process.getTasks();
			if(tasks != null && !tasks.isEmpty())
			{
				List<Transition> ts = new ArrayList<Transition>();
				for(Task task : tasks)
				{
					ts.addAll(task.getOutTransitions());
				}
				return getModelName(ts, type.toLowerCase(), 1);
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据Transition判断是否有重叠的连接线
	 * @param t
	 * @return
	 */
	public static boolean isMultiLine(Transition t)
	{
		try
		{
			Process process = (Process)t.getSource().getParent();
			Task source = t.getSource();
			Task target = t.getTarget();
			
			List<Task> tasks = process.getTasks();
			for(Task task : tasks)
			{
				List<Transition> ts = task.getOutTransitions();
				for(Transition tt : ts)
				{
					if(source == tt.getTarget() && target == tt.getSource())
					{
						return true;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 根据Transition对象获取参与者
	 * @param t
	 * @return
	 */
	public static String getTransitionActors(Transition t)
	{
		Task source = t.getSource();
		StringBuffer actors = new StringBuffer();
		actors.append("no:no;");
		for(Field field : source.getFields())
		{
			actors.append(source.getName()).append(":").append(field.getName()).append(";");
		}
		
		Process process = (Process)source.getParent();
		for(Task task : process.getTasks())
		{
			if(task == source) continue;
			for(Field field : task.getFields())
			{
				actors.append(task.getName()).append(":").append(field.getName()).append(";");
			}
		}
		
		if(actors.length() > 0) actors.deleteCharAt(actors.length() - 1);
		return actors.toString();
	}
	
	@SuppressWarnings("unchecked")
	private static String getModelName(List objects, String type, int idx)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			BaseElement element = (BaseElement)objects.get(i);
			if(element.getName() != null && element.getName().equals(type + idx))
			{
				return getModelName(objects, type, ++idx);
			}
		}
		return type + idx;
	}
	
	/**
	 * 构造Transition对象
	 * @param process
	 * @param sourceE
	 * @param targetE
	 * @param transitionE
	 */
	@SuppressWarnings("unchecked")
	private static void buildTransition(Process process, Element sourceE, Element targetE, Element transitionE)
	{
		Transition transition = new Transition();
		transition.setDisplayName(transitionE.attributeValue("displayName"));
		transition.setName(transitionE.attributeValue("name"));
		transition.setBendpoints(buildBendPoints(transitionE.attributeValue("g")));
		transition.setOffset(buildLabelOffsetPoint(transitionE.attributeValue("offset")));
		
		List attrs = transitionE.elements("attr");
		List<Attr> as = transition.getAttrs();
		for(int i = 0; i < attrs.size(); i++)
		{
			Element aE = (Element)attrs.get(i);
			Attr attr = new Attr();
			attr.setName(aE.attributeValue("name"));
			attr.setValue(aE.attributeValue("value"));
			as.add(attr);
		}
		
		Task source = getTaskByModel(process, sourceE);
		Task target = getTaskByModel(process, targetE);
		
		transition.setSource(source);
		transition.setTarget(target);
		
		source.addOutput(transition);
		target.addInput(transition);
	}
	
	/**
	 * 构造Transition对应的ELement
	 * @param transitions
	 * @param taskE
	 */
	private static void buildTransition(List<Transition> transitions, Element taskE)
	{
		for(Transition t : transitions)
		{
			Element tE = taskE.addElement("transition")
				.addAttribute("name", t.getName())
				.addAttribute("displayName", t.getDisplayName())
				.addAttribute("to", t.getTarget().getName())
				.addAttribute("g", buildSplitConnection(t))
				.addAttribute("offset", buildLabelOffset(t));
			List<Attr> attrs = t.getAttrs();
			for(Attr attr : attrs)
			{
				tE.addElement("attr").addAttribute("name", attr.getName()).addAttribute("value", attr.getValue());
			}
		}
	}
	
	private static String buildLabelOffset(Transition t)
	{
		Point offset = t.getOffset();
		if(offset == null) return "0,0";
		return offset.x + "," + offset.y;
	}
	
	private static Point buildLabelOffsetPoint(String s)
	{
		if(s == null || s.trim().length() == 0) return null;
		String[] xy = s.split(",");
		return new Point(new Integer(xy[0]), new Integer(xy[1]));
	}
	
	private static String buildSplitConnection(Transition t)
	{
		String result = "";
        for (Iterator<Point> iterator = t.getBendpoints().iterator(); iterator.hasNext(); ) {
        	Point point = iterator.next();
            result += point.x + "," + point.y + (iterator.hasNext() ? ";" : "");
        }
		return result;
	}
	
	private static List<Point> buildBendPoints(String s)
	{
        List<Point> result = new ArrayList<Point>();
        if (s == null || s.trim().length() == 0) {
            return result;
        }
        String[] bendpoints = s.split(";");
        for (String bendpoint: bendpoints) {
            String[] xy = bendpoint.split(",");
            result.add(new Point(new Integer(xy[0]), new Integer(xy[1])));
        }
        return result;
	}
	
	/**
	 * 根据task的Element返回Process对象对应的Task对象
	 * @param process
	 * @param taskE
	 * @return
	 */
	private static Task getTaskByModel(Process process, Element taskE)
	{
		List<Task> tasks = process.getTasks();
		String taskName = taskE.attributeValue("name");
		for(Task task : tasks)
		{
			if(task.getName() != null && task.getName().equalsIgnoreCase(taskName))
			{
				return task;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static Element getElementByTask(Task task, Element root)
	{
		String taskName = task.getName();
		
		List<Element> elements = root.elements();
		for(Element e : elements)
		{
			String eId = e.attributeValue("name");
			if(eId != null && eId.equalsIgnoreCase(taskName))
			{
				return e;
			}
		}
		return null;
	}
}
