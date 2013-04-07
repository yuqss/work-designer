package com.work.designer.model;

import java.util.List;
import java.util.Vector;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.config.Property;
import com.work.designer.utils.BeanUtil;
import com.work.designer.validators.NameUniqueValidator;

public class ModelPropertySource implements IPropertySource 
{
	private BaseElement object;
	
	private Component component;
	
	public ModelPropertySource(BaseElement object, Component component)
	{
		this.object = object;
		this.component = component;
	}
	
	@Override
	public Object getEditableValue()
	{
		return this.object;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() 
	{
		Vector<IPropertyDescriptor> descriptors = new Vector<IPropertyDescriptor>();

		try
		{
			return getPropertyDescriptors(component, descriptors);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return (IPropertyDescriptor[]) descriptors.toArray(new IPropertyDescriptor[0]);
		}
	}
	
	private IPropertyDescriptor[] getPropertyDescriptors(Component component, Vector<IPropertyDescriptor> descriptors) throws Exception
	{
		List<Property> listProps = component.getProperties();
	    if (listProps == null)
	    {
	    	return (IPropertyDescriptor[])descriptors.toArray(new IPropertyDescriptor[0]);
	    }
	    int index = 1;
		for (int i = 0; i < listProps.size(); i++) 
		{
			Property prop = (Property)listProps.get(i);
			addPropertyDescriptor(index, prop.getName(), descriptors, prop);
			index++;
		}
		return (IPropertyDescriptor[]) descriptors.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public Object getPropertyValue(Object id) 
	{
		Object value = null;
		Property prop = ConfigManager.getProperty(component, (String)id);
		try
		{
			value = BeanUtil.getPropertyValue(this.object, (String)id);
			if(value == null && prop != null)
			{
				value = prop.getDefaultValue();
			}
		}
		catch (Exception e)
		{
			List<Attr> attrs = this.object.getAttrs();
			if(attrs != null && !attrs.isEmpty())
			{
				for(Attr attr : attrs)
				{
					if(attr.getName().equalsIgnoreCase((String)id))
					{
						value = attr.getValue();
					}
				}
			}
			if(value == null && prop != null) value = prop.getDefaultValue();
		}
		
		if(prop.getType() != null && Property.TYPE_LIST.equalsIgnoreCase(prop.getType()))
		{
			String values = "";
//			if(this.object instanceof Transition && prop.getName().equals("actor"))
//			{
//				Transition t = (Transition)this.object;
//				values = ModelHelper.getTransitionActors(t);
//			}
//			else
//			{
			values = prop.getValues();
//			}
			
			String[] valueArr = values.split(";");
			value = getItemIndex(valueArr, (String)value);
		}
		else
		{
			if(value == null) value = "";
		}
		return value;
	}
	
	@Override
	public boolean isPropertySet(Object id) 
	{
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) 
	{

	}

	@Override
	public void setPropertyValue(Object id, Object value)
	{
		try
		{
		Property prop = ConfigManager.getProperty(component, (String)id);
		String v = "";
		if(prop.getType() != null && Property.TYPE_LIST.equalsIgnoreCase(prop.getType()))
		{
			String values = "";
//			if(this.object instanceof Transition && prop.getName().equals("actor"))
//			{
//				Transition t = (Transition)this.object;
//				values = ModelHelper.getTransitionActors(t);
//			}
//			else
//			{
			values = prop.getValues();
//			}
			
			String[] valueArr = values.split(";");
			v = valueArr[(Integer)value];
		}
		else
		{
			v = (String)value;
		}
		if(BeanUtil.hasPropertyName(this.object, (String)id))
		{
			try 
			{
				BeanUtil.setPropertyValue(this.object, (String)id, v);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			List<Attr> attrs = this.object.getAttrs();
			boolean isExists = false;
			for(int i = 0; i < attrs.size(); i++)
			{
				Attr attr = attrs.get(i);
				if(attr.getName().equalsIgnoreCase((String)id))
				{
					attr.setValue(v);
					this.object.setAttr(i, attr);
					isExists = true;
				}
			}
			if(!isExists)
			{
				Attr attr = new Attr();
				attr.setName((String)id);
				attr.setValue(v);
				this.object.addAttr(attr);
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean addPropertyDescriptor(int index, Object id, Vector<IPropertyDescriptor> descriptors, final Property prop) 
	{
		if ((prop.getVisible() != null) && (prop.getVisible().equalsIgnoreCase("false")))
		{
			return false;
		}
		
//		if(this.object instanceof Transition && prop.getName().equals("actor"))
//		{
//			Transition t = (Transition)this.object;
//			String actors = ModelHelper.getTransitionActors(t);
//			if(actors.length() > 0)
//			{
//				if(actors.length() > 0) actors.substring(0, actors.length() - 1);
//				ComboBoxPropertyDescriptor descriptor = new ComboBoxPropertyDescriptor(id, formatNumber(index) + "." + prop.getName(), getListTypeItems(actors)){
//					public String getCategory() {
//						return prop.getGroup();
//					}
//				};
//				descriptors.add(descriptor);
//			}
//			else
//			{
//				TextPropertyDescriptor descriptor = new TextPropertyDescriptor(id, formatNumber(index) + "." + prop.getName()){
//					public String getCategory() {
//						return prop.getGroup();
//					}
//				};
//				descriptors.add(descriptor);
//			}
//			return true;
//		}
		
		if(prop.getValues() != null && prop.getValues().indexOf(";") >= 0)
		{
			ComboBoxPropertyDescriptor descriptor = new ComboBoxPropertyDescriptor(id, formatNumber(index) + "." + prop.getName(), getListTypeItems(prop.getValues())){
				public String getCategory() {
					return prop.getGroup();
				}
			};
			descriptors.add(descriptor);
		}
		else
		{
			TextPropertyDescriptor descriptor = new TextPropertyDescriptor(id, formatNumber(index) + "." + prop.getName()){
				public String getCategory() {
					return prop.getGroup();
				}
			};
			if("name".equalsIgnoreCase(prop.getName()))
			{
				descriptor.setValidator(new NameUniqueValidator(this.object));
			}
			descriptors.add(descriptor);
		}
		return true;
	}
	
	/**
	 * 获取列表值数组
	 * @param value
	 * @return
	 */
	public String[] getListTypeItems(String value)
	{
		if(value == null) value = "";
		if(value.lastIndexOf(";") == value.length() - 1)
		{
			value = value.substring(0, value.length() - 1);
		}
		String[] items = value.split(";");
		if (items == null) 
		{
			items = new String[] { "" };
		}
		return items;
	}
	
	/**
	 * 根据列表数组、值获取该值对应的索引号
	 * @param items
	 * @param value
	 * @return
	 */
	private static int getItemIndex(String[] items, String value)
	{
		for (int i = 0; i < items.length; i++)
		{
			if (items[i].equals(value))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 获取格式化数字（如：1->01）
	 * @param number
	 * @return
	 */
	private static String formatNumber(int number) 
	{
		if (number < 10)
		{
			return "0" + String.valueOf(number);
		}
		return String.valueOf(number);
	}
}
