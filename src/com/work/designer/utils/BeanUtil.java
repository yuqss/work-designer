package com.work.designer.utils;


import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BeanUtil
{
	/**
	 * 获得给定对象与属性名所对应的值
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @return 返回该属性对应的值
	 */
	public static Object getPropertyValue(Object bean, String propertyName) throws Exception
	{
		try 
		{
			return PropertyUtils.getProperty(bean, propertyName);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * 通过给定类的全限定名返回一个该类的对象实例（该对象是通过无参的构建器创建出来的）
	 * @param className 类的全限定名
	 * @return 返回给定类的一个对象
	 */
	public static Object CreateObject(String className)
	{
		Object obj=null;
		try {
			obj= Class.forName(className,true,Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 判断给定的bean下是否包括给定的属性名,属性名可以不限制深度.
	 * 例如给定Bean有个部门属性,名为org,org.manager.id是深度属性名
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @return 如果存在给定的属性名则返回true,否则返回false
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasPropertyName(Object bean, String propertyName)
	{
		String[] propertyNames = StringUtils.strToStrArray(propertyName, ".");
		Class clzz =  bean.getClass();
		Field field = null;
		
		for(int i = 0; i < propertyNames.length; i++){
			
			//类型中是否有给定名称的属性
			field = hasClassField(clzz, propertyNames[i]);
			if(field == null)
				return false;
			
			clzz = field.getType();
		}

		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static Class getProperyClass(Object bean, String propertyName)
	{
		try
		{
			return PropertyUtils.getPropertyType(bean, propertyName);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/**
	 * 递归(直至调用的Object)判断clazz中是否有给定域名(fieldName)的域,如果有则返回对该域对应的<code>Field</code>,否则返回空
	 * @param clzz
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Field hasClassField(Class clazz, String fieldName)
	{
		try 
		{
			return clazz.getDeclaredField(fieldName);
		} 
		catch (Exception e)
		{
			if(clazz.equals(Object.class))
				return null;
			return hasClassField(clazz.getSuperclass(), fieldName); //递归处理,去父类的属性名
		}
	}
	/**
	 * 获得给定对象与属性名所对应的值
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @return 返回该属性对应的值,并将该值造型为String类型
	 */
	public static String getPropertyValueToStr(Object bean, String propertyName) throws Exception
	{
		Object val = getPropertyValue(bean, propertyName);
		
		if(val == null)
			return null;
		
		return val.toString();
	}
	
	/**
	 * 为给定对象属性设置值
	 * @param bean 给定对象
	 * @param propertyName 属性名
	 * @param value 值
	 */
	public static void setPropertyValue(Object bean, String propertyName, Object value) throws Exception
	{
		try
		{
			PropertyUtils.setProperty(bean, propertyName, value);
		}
		catch (Exception e) 
		{
			throw e;
		} 
	}
	
	
	
	/**
	 * 将给定的参数collection转换为一个xml格式的字符串
	 * @param collection 待转换为xml的集合对象
	 * @param collName xml根元素的元素名
	 * @param elementName xml中二级元素的元素名,也就是xml所对应的集合元素的元素名称
	 * @return 返回xml格式的字符串
	 * <p>例如:
	 * <P>&lt;collName&gt;</P>
	 * <P align=left>&nbsp; &lt;elementName&gt;</P>
	 * <P align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ...</P>
	 * <P align=left>&nbsp; &lt;/elementName&gt;</P>
	 * <P align=left>&nbsp;&nbsp;&nbsp;&nbsp; ...</P>
	 * <P align=left>&nbsp; &lt;elementName&gt;</P>
	 * <P align=left>&nbsp; &lt;/elementName&gt;</P>
	 * <P>&lt;/collName&gt;</P>&nbsp;
	 */
	public static String getCollection2XML(Collection<Object> collection, String collName, String elementName ) throws Exception
	{
		if(collection == null || collection.size() == 0) return ""; 
		StringBuffer sb = new StringBuffer("<"+collName+">").append("\n");
		for (Object object : collection) 
			sb.append(getBean2XML(object, elementName, 1, new StringBuffer(),  new HashSet<String>()));
		sb.append("</" + collName +">");
		
		return sb.toString();
	}
	
	/**
	 * 将给定的POJO(bean)对象转换为一个xml格式的字符串,对象的属性名就是xml文档的元素名
	 * @param bean 待转换为xml的POJO对象
	 * @param elementName 返回的xml文档的根元素的元素名
	 * @return 返回与参数bean相对应的xml格式的字符串
	 */
	public static String getBean2XML(Object bean, String elementName) throws Exception
	{
		String xml = getBean2XML(bean, elementName, 0, new StringBuffer(), new HashSet<String>());
		return xml;
	}
	
	@SuppressWarnings("unchecked")
	private static String getBean2XML(Object bean, String elementName, int deep, StringBuffer sb, Set<String> classes) throws Exception
	{
		if(bean == null) return null;
		try
		{
			bean.hashCode();
		}
		catch (Exception e) 
		{
			return null;
		}
		String beanHash = String.valueOf(bean.hashCode());
		if(classes.contains(beanHash) && !originalType(bean)) return null;
		else classes.add(beanHash);
		
		if(bean instanceof Collection)
		{
			for (Object obj : (Collection)bean)
			{
				getBean2XML(obj,elementName, deep, sb, classes);
			}
		}
		else
		{
			for (int i = 0; i < deep; i++) 
				sb.append("\t");
			
			if(elementName == null)
				elementName = StringUtils.lowerFrist(bean.getClass().getSimpleName());
			
				sb.append("<").append(elementName).append(">");
			if(!originalType(bean))
				sb.append("\n");
		}
		
		if (originalType(bean))
		{
			if(bean instanceof Date)
			{
				sb.append(StringUtils.DateToStr((Date)bean, "yyyy-MM-dd HH:mm:ss"));
			}
			else if(bean instanceof String)
			{
				bean = StringUtils.replace((String)bean, "<", "&lt;");
				bean = StringUtils.replace((String)bean, ">", "&gt;");
				bean = StringUtils.replace((String)bean, "&", "&amp;");
				sb.append(bean);
			}
			else
			{
				sb.append(bean.toString());
			}
		}
		
		else{

			PropertyDescriptor[] beanProperties = PropertyUtils.getPropertyDescriptors(bean);
			for (PropertyDescriptor propertyDescriptor : beanProperties)
			{
				if(propertyDescriptor.getReadMethod() == null || propertyDescriptor.getWriteMethod() == null)
					continue;
				String propertyName = propertyDescriptor.getName();
				Object propertyValue = getPropertyValue(bean, propertyName);
				
				getBean2XML(propertyValue, propertyName, deep+1, sb, classes);
			}
		}
		
		if(!(bean instanceof Collection))
		{
			if(!originalType(bean))
				for (int i = 0; i < deep; i++) 
					sb.append("\t");
			
			sb.append("</").append(elementName).append(">").append("\n");
		}
		return sb.toString();	
	}
	
	protected static boolean originalType(Object bean)
	{
		return bean instanceof Boolean || bean instanceof Character
			|| bean instanceof Double || bean instanceof Float
			|| bean instanceof Integer || bean instanceof Long
			|| bean instanceof Short || bean instanceof String
			|| bean instanceof Date || bean instanceof Locale
			|| bean instanceof Timestamp;
	}
	
	/**
	 * 通过给定的内容为一个xml格式的输入流(is)将该内容转换为指定的(clazz)的一个对象
	 * @param is xml格式的字符流
	 * @param clazz 待转换成POJO对象的类
	 * @return 返回一个POJO对象
	 */
	@SuppressWarnings("unchecked")
	public static Object getXML2BeanByInputStream(InputStream is, Class clazz, boolean isColl){
		if(is == null)
			return null;
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance(); 
		try{
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element rootElement = doc.getDocumentElement();
			
			if(!isColl)
				return getXML2Bean(rootElement, clazz);

			List result = new ArrayList();
			NodeList nodes = rootElement.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if(node.getNodeType()!=Node.ELEMENT_NODE)
					continue;
				result.add(getXML2Bean(node, clazz));
			}
			return result;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	/**
	 * 将xml字符串文档转换为一个POJO对象
	 * @param xml 待转换的XML文档格式的字符串
	 * @param clazz 待转换成POJO对象的类
	 * @return 返回一个POJO对象
	 */
	@SuppressWarnings("unchecked")
	public static Object getXML2Bean(String xml, Class clazz){
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xml.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return getXML2BeanByInputStream(is, clazz, false);
	}
	
	/**
	 * 将给你的xml字符串转换为POJO的集合,返回一个List集合
	 * @param xml 给定的xml字符串
	 * @param clazz 待转换成POJO对象的类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getXML2Collectoin(String xml, Class clazz){
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xml.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return (List)getXML2BeanByInputStream(is, clazz, true);
	}
	
	
	@SuppressWarnings("unchecked")
	private static Object getXML2Bean(Node node, Class clazz) throws Exception
	{
		Object bean = CreateObject(clazz.getName());
		PropertyDescriptor[] beanProperties = PropertyUtils.getPropertyDescriptors(bean);
		NodeList nodeList = node.getChildNodes();
		
		for (PropertyDescriptor propertyDescriptor : beanProperties) {
			if(propertyDescriptor.getReadMethod() == null || propertyDescriptor.getWriteMethod() == null)
				continue;
			String propertyName = propertyDescriptor.getName();
			Class propertyClass = originalWrapper(propertyDescriptor.getPropertyType());
			Object value = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node propertyNode = nodeList.item(i);
				if(propertyNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				Element element = (Element)propertyNode;
				if(!element.getTagName().equals(propertyName))
					continue;
				
				
				if (Collection.class.isAssignableFrom(propertyClass)){
					if(getPropertyValue(bean, propertyName) != null)
						continue;
					
					Field field = hasClassField(clazz, propertyName);
					ParameterizedType type = (ParameterizedType)field.getGenericType(); //找到泛型所对应的类型  
					Type[] types = type.getActualTypeArguments();
					Class elementClass = (Class)types[0];
					
					
					Collection deails = null;
					if(List.class.isAssignableFrom(propertyClass))
						deails = new ArrayList();
					if(Set.class.isAssignableFrom(propertyClass))
						deails = new HashSet();
					
					for (int j = 0; j < nodeList.getLength(); j++) {
						Node elementNode = nodeList.item(j);
						if(elementNode.getNodeType() != Node.ELEMENT_NODE)
							continue;
						Element nodeElement = (Element)elementNode;
						if(!nodeElement.getTagName().equals(propertyName))
							continue;
						
						deails.add(getXML2Bean(nodeElement, elementClass));
					}
					
					
					setPropertyValue(bean, propertyName, deails);
					continue;
				}

				
				if(!propertyClass.equals(Boolean.class) && !propertyClass.equals(Character.class) &&
						!propertyClass.equals(Double.class) && !propertyClass.equals(Float.class) && 
						!propertyClass.equals(Integer.class) && !propertyClass.equals(Long.class) && 
						!propertyClass.equals(Short.class) && !propertyClass.equals(String.class) && 
						!propertyClass.equals(Date.class) && !propertyClass.equals(Timestamp.class)){	//如果不是原始类型
					value = getXML2Bean(element, propertyClass);
					setPropertyValue(bean, propertyName, value);
					continue;
				}
				
				
				String textContent = element.getTextContent();
				if(propertyClass.equals(Date.class) || propertyClass.equals(Timestamp.class)){
					SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					value = formatter.parse(textContent);
					if(propertyClass.equals(Timestamp.class)){
						value = new Timestamp(((Date)value).getTime());
					}
				}
				else{
					Constructor constructor = propertyClass.getConstructor(String.class);
					value = constructor.newInstance(textContent);
				}
				setPropertyValue(bean, propertyName, value);
			}
			
		}
		
		return bean;
	}
	
	/**
	 * 如果参数clazz是原始类型,则返回与该原始类型所对应的包装类的类型,否则返回该类型本身
	 * @param clazz 该验测的数据类型
	 * @return 如果是原始类型则返回与其对应的包装类的类型,否则返回对与类型本身
	 */
	@SuppressWarnings("unchecked")
	public static Class originalWrapper(Class clazz){
		if(clazz.equals(boolean.class))return Boolean.class;
		if(clazz.equals(int.class))return Integer.class;
		if(clazz.equals(double.class))return Double.class;
		if(clazz.equals(float.class))return Float.class;
		if(clazz.equals(char.class))return Character.class;
		if(clazz.equals(long.class))return Long.class;
		if(clazz.equals(short.class))return Short.class;
		return clazz;
	}
}