package com.work.designer.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;

import com.work.designer.DesignerEditor;
import com.work.designer.actions.ActionHelper;
import com.work.designer.actions.FormContextMenuProvider;
import com.work.designer.config.ConfigManager;
import com.work.designer.palette.PaletteFactory;
import com.work.designer.parts.TaskFieldEditPartFactory;

public class TaskFieldDesignerEditor extends AbstractEditorPage
{
	public TaskFieldDesignerEditor(DesignerEditor parent, Object model)
	{
		super(parent, model);
	}

	@Override
	public String getPageName()
	{
		return "表单定义";
	}

	@Override
	protected PaletteRoot getPaletteRoot()
	{
		PaletteFactory factory = new PaletteFactory();
		return factory.getPaletteRoot(ConfigManager.COMPONENT_TYPE_FIELD);
	}

	@Override
	protected void initializeGraphicalViewer()
	{
		getGraphicalViewer().setContents(this.model);
	}

	@Override
	protected void configureGraphicalViewer()
	{
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setEditPartFactory(new TaskFieldEditPartFactory(getParentEditor()));
		ContextMenuProvider provider = new FormContextMenuProvider(viewer, getParentEditor().getEditActionList(), getParentEditor().getActionRegistry());
		viewer.setContextMenu(provider);

		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		((FigureCanvas) viewer.getControl()).setScrollBarVisibility(FigureCanvas.ALWAYS);
	}

	@Override
	protected void createActions()
	{
		ActionHelper.registerFormActions(getParentEditor(), getParentEditor().getEditActionList(), getParentEditor().getActionRegistry());
	}

	@Override
	public void doSave(IProgressMonitor monitor)
	{
		super.doSave(monitor);
	}

}
