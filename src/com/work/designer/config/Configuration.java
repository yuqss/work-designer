package com.work.designer.config;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 单态实例操作model.xml配置文件
 *
 */
public class Configuration
{
	private static Configuration configuration = new Configuration();
	
	private static final String fileName = "model.xml";
	
	private Container container;
	
	private Configuration()
	{
		load();
	}
	
	public static Configuration getConfiguration()
	{
		return configuration;
	}
	
	/**
	 * 组装对象Component
	 */
	@SuppressWarnings("unchecked")
	public void load()
	{
        try
        {
        	String url = this.getClass().getResource(fileName).toString();
            SAXReader reader = new SAXReader();
            Document document = reader.read(url);
            Element root = document.getRootElement();
            container = new Container();
            Iterator<Element> iter = root.elementIterator("component");
            while(iter.hasNext())
            {
            	Element comp = (Element)iter.next();
            	Component component = new Component();
            	component.setName(comp.attributeValue("name"));
            	component.setDisplayName(comp.attributeValue("displayName"));
            	component.setDescript(comp.attributeValue("descript"));
            	component.setGroup(comp.attributeValue("group"));
            	component.setType(comp.attributeValue("type"));
            	component.setVisible(comp.attributeValue("visible"));
            	component.setIconSmall(comp.attributeValue("iconSmall"));
            	component.setIconLarge(comp.attributeValue("iconLarge"));
            	
            	Iterator<Element> itattrs = comp.elementIterator("property");
            	while(itattrs.hasNext())
            	{
            		Element ep = (Element)itattrs.next();
            		Property prop = new Property();
            		prop.setName(ep.attributeValue("name"));
            		prop.setType(ep.attributeValue("type"));
            		prop.setDefaultValue(ep.attributeValue("defaultValue"));
            		prop.setValidator(ep.attributeValue("validator"));
            		prop.setValues(ep.attributeValue("values"));
            		prop.setVisible(ep.attributeValue("visible"));
            		prop.setGroup(ep.attributeValue("group"));
            		
            		component.getProperties().add(prop);
            	}
            	container.getComponents().add(component);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public Container getContainer()
	{
		return container;
	}
}
