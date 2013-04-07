package com.work.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;
import com.work.designer.model.*;

public class ReconnectTargetCommand extends Command {

	protected Task source;
	protected Task target;
	protected Transition transition;
	protected Task oldTarget;

	public boolean canExecute() {
		if (transition.getSource().equals(target))
			return false;

		List<Transition> transitions = source.getOutTransitions();
		for (int i = 0; i < transitions.size(); i++) {
			Transition trans = ((Transition) (transitions.get(i)));
			if (trans.getTarget().equals(target)
					&& !trans.getTarget().equals(oldTarget))
				return false;
		}
		return true;
	}

	public void execute() {
		if (target != null) {
			oldTarget.removeInput(transition);
			transition.setTarget(target);
			target.addInput(transition);
		}
	}

	public Task getSource() {
		return source;
	}

	public Task getTarget() {
		return target;
	}

	public Transition getTransition() {
		return transition;
	}

	public void setSource(Task activity) {
		source = activity;
	}

	public void setTarget(Task activity) {
		target = activity;
	}

	public void setTransition(Transition trans) {
		transition = trans;
		source = trans.getSource();
		oldTarget = trans.getTarget();
	}

	public void undo() {
		target.removeInput(transition);
		transition.setTarget(oldTarget);
		oldTarget.addInput(transition);
	}

}
