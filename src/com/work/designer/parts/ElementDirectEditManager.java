package com.work.designer.parts;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Text;

import com.work.designer.model.BaseElement;

public class ElementDirectEditManager extends DirectEditManager
{
	private String displayName;
	
    public ElementDirectEditManager(GraphicalEditPart source, CellEditorLocator locator)
    {
        super(source, TextCellEditor.class, locator);
        Object object = source.getModel();
        if (object instanceof BaseElement)
        {
        	displayName = ((BaseElement)object).getDisplayName();
        }
    }

    protected void initCellEditor()
    {
    	String initialValue = null;
    	if (displayName != null && displayName.trim().length() > 0)
    	{
    		initialValue = displayName;
    	}
        getCellEditor().setValue(initialValue == null ? "" : initialValue);
        Text text = (Text) getCellEditor().getControl();
        text.selectAll();
    }
}
