package com.work.designer.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.work.designer.config.ConfigManager;
import com.work.designer.model.Attr;
import com.work.designer.model.BaseElement;
import com.work.designer.model.Field;
import com.work.designer.model.ModelManager;

public class EditPartHelper 
{
	public static Point getCurFigureLocation(GraphicalEditPart edit, Figure figure)
	{
		GraphicalEditPart preEdit = getPreviousPart(edit);
		if (preEdit == null)
		{
		 	return new Point(30, 30);
		}
		
		Point point = createCurLocation(preEdit);
		String lineBR = ModelManager.getElementLineBR((BaseElement)preEdit.getModel());
		if(lineBR.equalsIgnoreCase(ConfigManager.VALUE_LIST_FALSE))
		{	
			Point prePoint = ((Figure)preEdit.getFigure()).getLocation();
			if(point.y == prePoint.y)
			{
		    	List<Attr> attrs = ((BaseElement)edit.getModel()).getAttrs();
		    	if(attrs.isEmpty())
		    	{
		    		Attr attr = new Attr();
		    		attr.setName(ConfigManager.PROPERTIES_LINEBR);
		    		attr.setValue(ConfigManager.VALUE_LIST_TRUE);
		    		attrs.add(attr);
		    	}
		    	else
		    	{
			    	for(Attr attr : attrs)
			    	{
			    		if(attr.getName().equalsIgnoreCase(ConfigManager.PROPERTIES_LINEBR))
			    		{
			    			attr.setValue(ConfigManager.VALUE_LIST_TRUE);
			    		}
			    	}
		    	}
			}
		}
		return point;
	}
	
	public static Point getCurFigureLocation(GraphicalEditPart edit) 
	{
		GraphicalEditPart preEdit = getPreviousPart(edit);
		if (preEdit == null) 
		{
			return new Point(30, 30);
		}

		Point point = createCurLocation(preEdit);
		return point;
	}
	  
	@SuppressWarnings("unchecked")
	private static GraphicalEditPart getPreviousPart(GraphicalEditPart curPart)
	{
	    GraphicalEditPart preEdit = null;
	    List listParts = curPart.getParent().getChildren();
	    for (int i = 0; i < listParts.size(); i++)
	    {
	    	if (listParts.get(i) != curPart)
	    		continue;
	    	if (i == 0)
	    		continue;
	    	preEdit = (GraphicalEditPart)listParts.get(i - 1);
	    }

	    return preEdit;
	}
	  
	private static Point createCurLocation(GraphicalEditPart preEdit)
	{
	    String lineBR = ConfigManager.VALUE_LIST_FALSE;
	    Point point = null;
	    Figure figure = (Figure)preEdit.getFigure();

	    if ((preEdit.getModel() instanceof BaseElement))
	    {
	    	lineBR = ModelManager.getElementLineBR((BaseElement)preEdit.getModel());
	    }
	    if (lineBR.equals(ConfigManager.VALUE_LIST_TRUE))
	    {
	    	point = new Point(30, figure.getLocation().y + figure.getSize().height - 1);
	    }
	    else
	    {
	    	point = new Point(figure.getLocation().x + figure.getSize().width - 1, figure.getLocation().y);
	    }
	    return point;
	}
	
	@SuppressWarnings("unchecked")
	public static List<EditPart> getRestEditParts(EditPart editpart)
	{
		List<EditPart> listOut = new ArrayList<EditPart>();
		List<EditPart> listParts = editpart.getParent().getChildren();
		boolean bFlag = false;

		if (listParts == null) 
		{
			return new ArrayList<EditPart>();
		}
		for (int i = 0; i < listParts.size(); i++) 
		{
			if (bFlag) 
			{
				listOut.add(listParts.get(i));
			} 
			else 
			{
				if (editpart != listParts.get(i))
				{
					continue;
				}
				bFlag = true;
				listOut.add(listParts.get(i));
			}
		}
		return listOut;
	}

	public static void refreshEditPartsLocation(List<EditPart> editparts) 
	{
		for (int i = 0; i < editparts.size(); i++) 
		{
			if (i == 0)
			{
				continue;
			}
			FieldEditPart curPart = (FieldEditPart)editparts.get(i);
			FieldEditPart nextPart = (FieldEditPart)editparts.get(i - 1);
			Point p = createCurLocation(nextPart);
			
			curPart.getFigure().setLocation(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void refreshAllChildren(EditPart part)
	{
		if ((part.getModel() instanceof Field)) 
		{
			return;
		}
		List list = part.getChildren();
		for (int i = 0; i < list.size(); i++) 
		{
			FieldEditPart tmpPart = (FieldEditPart)list.get(i);
			tmpPart.refreshLocation();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List getEditPartsInLayouts(EditPart editpart)
	{
		if (!(editpart instanceof FieldEditPart))
		{
			return null;
		}
		FieldEditPart ep = (FieldEditPart) editpart;

		if (ep.isLayout())
		{
			return ep.getChildren();
		}
		EditPart pep = ep.getParent();
		return pep.getChildren();
	}
	
	@SuppressWarnings("unchecked")
	public static void refreshLocation(List editparts)
	{
		for (int i = 0; i < editparts.size(); i++)
		{
			FieldEditPart ep = (FieldEditPart) editparts.get(i);
			if (i == 0) 
			{
				Point p = new Point(30, 30);
				ep.getFigure().setLocation(p);
			} 
			else
			{
				FieldEditPart pep = (FieldEditPart) editparts.get(i - 1);
				Point p = createCurLocation(pep);
				ep.getFigure().setLocation(p);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void refreshAllEditPartLocation(EditPart part)
	{
		List list = getEditPartsInLayouts(part);
		refreshLocation(list);
	}
	
	public static Object getParentModel(TaskFieldEditPart editpart)
	{
		if (editpart == null)
		{
			return null;
		}
		while (!editpart.isLayout())
		{
			if (!(editpart.getParent() instanceof TaskFieldEditPart))
			{
				break;
			}
			editpart = (TaskFieldEditPart) editpart.getParent();
			continue;
		}
		return editpart.getModel();
	}
}
