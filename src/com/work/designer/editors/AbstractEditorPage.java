package com.work.designer.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.work.designer.DesignerEditor;

public abstract class AbstractEditorPage extends GraphicalEditorWithPalette
{
	private final DesignerEditor parent;
	
	protected Object model = null;

	public AbstractEditorPage(DesignerEditor parent) 
	{
		this.parent = parent;
	}

	public AbstractEditorPage(DesignerEditor parent, Object model)
	{
		this.parent = parent;
		this.model = model;
		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		setEditDomain(defaultEditDomain);
	}

	public void doSave(IProgressMonitor monitor) 
	{
		getParentEditor().doSave(monitor);
	}

	public final void doSaveAs()
	{
		getParentEditor().doSaveAs();
	}

	public final boolean isDirty() 
	{
		return getParentEditor().isDirty();
	}

	public final CommandStack getCommandStack() 
	{
		return getEditDomain().getCommandStack();
	}

	public final boolean isSaveAsAllowed() 
	{
		return getParentEditor().isSaveAsAllowed();
	}

	public abstract String getPageName();

	protected final DesignerEditor getParentEditor()
	{
		return this.parent;
	}

	protected void registerEditPartViewer(EditPartViewer viewer) 
	{
		getEditDomain().addViewer(viewer);

		getParentEditor().getSelectionSynchronizer().addViewer(viewer);

		getSite().setSelectionProvider(viewer);
	}

	public PropertySheetPage getPropertySheetPage() 
	{
		return null;
	}

	public DefaultEditDomain getEditDomain() 
	{
		return super.getEditDomain();
	}

	public GraphicalViewer getGraphicalViewer()
	{
		return super.getGraphicalViewer();
	}

	/**
	 * ²úÉúActions
	 */
	protected void createActions() 
	{
		super.createActions();
	}

	public Object getModel()
	{
		return model;
	}

	public void setModel(Object model) 
	{
		this.model = model;
	}
}
