package com.work.designer.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.graphics.Color;

import com.work.designer.config.ConfigManager;
import com.work.designer.model.Task;

public class TaskFigure extends RoundedRectangleElementFigure {

	private static final Color veryLightBlue = new Color(null, 246, 247, 255);
	private static final Color lightBlue = new Color(null, 3, 104, 154);
	
	private static final Color veryLightBlue_sub = new Color(null, 240, 80, 50);
	private static final Color lightBlue_sub = new Color(null, 3, 104, 154);
	
	private Task task;
	
	public TaskFigure(Task task)
	{
		this.task = task;
		setText(task.getDisplayName());
	}

	protected void paintChildren(Graphics graphics) {
		Color foregroundColor = graphics.getForegroundColor();
		Color backgroundColor = graphics.getBackgroundColor();
		if(task.getType() != null && task.getType().equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_SUBPROCESS))
		{
			graphics.setBackgroundColor(veryLightBlue_sub);
			graphics.setForegroundColor(lightBlue_sub);
		}
		else
		{
			graphics.setBackgroundColor(veryLightBlue);
			graphics.setForegroundColor(lightBlue);
		}

		super.paintChildren(graphics);
		graphics.setBackgroundColor(backgroundColor);
		graphics.setForegroundColor(foregroundColor);
	}

	protected void customizeFigure() {
		super.customizeFigure();
		getLabel().setForegroundColor(ColorConstants.darkGray);
		setText("流程节点");
		rectangle.setLineWidth(2);
	}

}
