package com.work.designer.io;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.eclipse.draw2d.geometry.Rectangle;

public class LayoutXmlAdapter extends XmlAdapter<String, Rectangle>
{
	@Override
	public String marshal(Rectangle v) throws Exception 
	{
		if(v == null) return "";
		return v.x + "," + v.y + "," + v.width + "," + v.height;
	}

	@Override
	public Rectangle unmarshal(String v) throws Exception
	{
		if(v != null && v.trim().length() > 0)
		{
			String[] ps = v.split(",");
			if(ps.length ==4)
			{
				int x = Integer.parseInt(ps[0]);
				int y = Integer.parseInt(ps[1]);
				int width = Integer.parseInt(ps[2]);
				int height = Integer.parseInt(ps[3]);
				return new Rectangle(x, y, width, height);
			}
		}
		return new Rectangle(0, 0, 0, 0);
	}
}
