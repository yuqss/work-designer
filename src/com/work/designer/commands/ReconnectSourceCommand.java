package com.work.designer.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.*;

public class ReconnectSourceCommand extends Command {

	protected Task source;
	protected Task target;
	protected Transition transition;
	protected Task oldSource;

	public boolean canExecute() {
		if (transition.getTarget().equals(source)) {
			return false;
		}

		List<Transition> transitions = source.getOutTransitions();
		for (int i = 0; i < transitions.size(); i++) {
			Transition trans = ((Transition) (transitions.get(i)));
			if (trans.getTarget().equals(target)
					&& !trans.getTarget().equals(oldSource))
				return false;
		}
		return true;
	}

	public void execute() {
		if (source != null) {
			oldSource.removeOutput(transition);
			transition.setSource(source);
			transition.setParent(source.getParent());
			source.addOutput(transition);
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
		target = trans.getTarget();
		oldSource = trans.getSource();
	}

	public void undo() {
		source.removeOutput(transition);
		transition.setSource(oldSource);
		oldSource.addOutput(transition);
	}

}
