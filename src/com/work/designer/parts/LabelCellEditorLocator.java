package com.work.designer.parts;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;

public class LabelCellEditorLocator implements CellEditorLocator
{
    private Label label;

    public LabelCellEditorLocator(Label label)
    {
        setLabel(label);
    }

    public void relocate(CellEditor cellEditor)
    {
        Text text = (Text) cellEditor.getControl();
        Point pref = text.computeSize(-1, -1);
        Rectangle rect = label.getTextBounds().getCopy();
        label.translateToAbsolute(rect);
        text.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
    }

    protected Label getLabel()
    {
        return label;
    }

    protected void setLabel(Label label)
    {
        this.label = label;
    }

}