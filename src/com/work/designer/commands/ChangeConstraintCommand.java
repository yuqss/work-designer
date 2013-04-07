package com.work.designer.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.work.designer.model.Task;

public class ChangeConstraintCommand extends Command {
	private Object obj;
	private Rectangle oldConstraint;
	private Rectangle newConstraint;

	public ChangeConstraintCommand(Object element, Rectangle newConstraint) {
		this.newConstraint = newConstraint;
		this.obj = element;
	}

	public void execute() {
		if ((this.obj instanceof Task)) {
			this.oldConstraint = ((Task) this.obj).getLayout();
			((Task) this.obj).setLayout(newConstraint);
		}
	}

	public void redo() {
		if ((this.obj instanceof Task)) {
			((Task) this.obj).setLayout(newConstraint);
		}
	}

	public void undo() {
		if ((this.obj instanceof Task)) {
			((Task) this.obj).setLayout(oldConstraint);
		}
	}
}
