package com.work.designer.model;

import java.util.List;

import com.work.designer.config.ConfigManager;

public class ModelManager 
{
	public static int getPlaceHolderAttrIndex(Field field) 
	{
		List<Attr> list = field.getAttrs();
		if(list != null)
		{
			for (int i = 0; i < list.size(); i++) 
			{
				Attr attr = (Attr) list.get(i);
				if (ConfigManager.PROPERTIES_PLACEHOLDER.equalsIgnoreCase(attr.getName())) 
				{
					return i;
				}
			}
		}
		return -1;
	}
	
	public static String getElementLineBR(BaseElement element)
	{
	    List<Attr> list = element.getAttrs();
	    for (int i = 0; (list != null) && (i < list.size()); i++)
	    {
	    	Attr attr = (Attr)list.get(i);
	    	if (attr.getName().equalsIgnoreCase(ConfigManager.PROPERTIES_LINEBR))
	    	{
	    		return attr.getValue();
	    	}
	    }
	    return "false";
	}
	
	public static String getElementLineBR(BaseElement element, String attrName)
	{
	    List<Attr> list = element.getAttrs();
	    for (int i = 0; (list != null) && (i < list.size()); i++)
	    {
	    	Attr attr = (Attr)list.get(i);
	    	if (attr.getName().equalsIgnoreCase(attrName))
	    	{
	    		return attr.getValue();
	    	}
	    }
	    return "";
	}
	
	
	public static void exchangePlaceHolder(Field before, Field after)
	{
		String beforeValue = "";
		String afterValue = "";
		int beforeIndex = 0;
		int afterIndex = 0;
		Attr beforeAttr = null;
		Attr afterAttr = null;
		List<Attr> beforeAttrs, afterAttrs;
		beforeAttrs = before.getAttrs();
		for (int i = 0; i < beforeAttrs.size(); i++)
		{
			Attr attr = (Attr) beforeAttrs.get(i);
			if (!ConfigManager.PROPERTIES_LINEBR.equalsIgnoreCase(attr.getName()))
			{
				continue;
			}
			beforeValue = attr.getValue();
			beforeIndex = i;
			beforeAttr = attr;
		}
		
		afterAttrs = after.getAttrs();
		for (int i = 0; i < afterAttrs.size(); i++)
		{
			Attr attr = (Attr) afterAttrs.get(i);
			if (!ConfigManager.PROPERTIES_LINEBR.equalsIgnoreCase(attr.getName()))
			{
				continue;
			}
			afterValue = attr.getValue();
			afterIndex = i;
			afterAttr = attr;
		}
		beforeAttr.setValue(afterValue);
		beforeAttrs.set(beforeIndex, beforeAttr);
		afterAttr.setValue(beforeValue);
		afterAttrs.set(afterIndex, afterAttr);
	}
	
	public static void IncreasePlaceHolder(Field field)
	{
		List<Attr> attrs = field.getAttrs();
		boolean isExists = false;
		for (int i = 0; i < attrs.size(); i++)
		{
			Attr attr = (Attr) attrs.get(i);
			if (!ConfigManager.PROPERTIES_PLACEHOLDER.equalsIgnoreCase(attr.getName()))
			{
				continue;
			}
			isExists = true;
			int len = Integer.parseInt(attr.getValue());
			attr.setValue(String.valueOf(len + 1));
			field.setAttr(i, attr);
			return;
		}
		
		if(!isExists)
		{
			Attr attr = new Attr();
			attr.setName(ConfigManager.PROPERTIES_PLACEHOLDER);
			attr.setValue(String.valueOf(2));
			field.addAttr(attr);
		}
	}
	
	public static void DecreasePlaceHolder(Field field)
	{
		List<Attr> attrs = field.getAttrs();
		boolean isExists = false;
		for (int i = 0; i < attrs.size(); i++) 
		{
			Attr attr = (Attr) attrs.get(i);
			if (!ConfigManager.PROPERTIES_PLACEHOLDER.equalsIgnoreCase(attr.getName()))
			{
				continue;
			}
			isExists = true;
			int len = Integer.parseInt(attr.getValue());
			if (1 == len)
			{
				break;
			}
			attr.setValue(String.valueOf(len - 1));
			field.setAttr(i, attr);
			return;
		}
		
		if(!isExists)
		{
			Attr attr = new Attr();
			attr.setName(ConfigManager.PROPERTIES_PLACEHOLDER);
			attr.setValue(String.valueOf(1));
			field.addAttr(attr);
		}
	}
}
