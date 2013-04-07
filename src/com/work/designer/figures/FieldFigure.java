package com.work.designer.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.model.Field;

public class FieldFigure extends Figure
{
	private Field model;
	
	private int placeHolder = 1;
	
	private Component component;
	
	public FieldFigure(Field field)
	{
		this.model = field;
		this.component = ConfigManager.getComponentByGroupAndType(ConfigManager.COMPONENT_TYPE_FIELD, field.getType());
	    setLayoutManager(new FlowLayout());
	    
	    setForegroundColor(ColorConstants.blue);
	    setBackgroundColor(ColorConstants.blue);
	    setBorder(new FieldBorder(field.getDisplayName()));
	    setOpaque(true);
	    SimpleLabel sl = new SimpleLabel();
	    sl.setIcon(getImageByType());
	    add(sl, ToolbarLayout.ALIGN_CENTER);
	    
	    placeHolder = ConfigManager.getPlaceHolderValue(component);
	    int iWidth = 200 * placeHolder + 1;
	    setSize(iWidth, 22);
	}
	
	public Field getModel()
	{
		return this.model;
	}
	
	private Image getImageByType()
	{
		return ImageDescriptor.createFromFile(FieldFigure.class, this.component.getIconSmall()).createImage();
	}

	public void setText(String text)
	{
		FieldBorder border = (FieldBorder)getBorder();
	    border.setText(text);
	}
}
