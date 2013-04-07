package com.work.designer;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;

import com.work.designer.actions.ActionHelper;
import com.work.designer.actions.FormContextMenuProvider;
import com.work.designer.config.ConfigManager;
import com.work.designer.model.Form;
import com.work.designer.model.ModelHelper;
import com.work.designer.palette.PaletteFactory;
import com.work.designer.parts.FormDesignerEditPartFactory;

public class FormDesignerEditor extends GraphicalEditorWithPalette implements IAdaptable
{
	private boolean isDirty = false;
	
	private Form model;
	
	private List<String> actionIds = new ArrayList<String>();
	
	public FormDesignerEditor()
	{
		setEditDomain(new DefaultEditDomain(this));
	}
	
	@Override
	protected void setInput(IEditorInput input) 
	{
		super.setInput(input);
	    IFile file = ((IFileEditorInput)input).getFile();
	    try
	    {
	    	JAXBContext context = JAXBContext.newInstance(Form.class);
	    	Unmarshaller unmarshaller = context.createUnmarshaller();
	    	FileReader reader = new FileReader(new File(file.getLocationURI()));
	    	model = (Form)unmarshaller.unmarshal(reader);
	    	ModelHelper.buildCascade(model);
	    }
	    catch (Exception e)
	    {
	        model = new Form();
	    }
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) 
	{
	    try 
	    {
            JAXBContext context = JAXBContext.newInstance(Form.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            IFile file = ((IFileEditorInput)getEditorInput()).getFile();
            marshaller.marshal(model, new File(file.getLocationURI()));
    		getCommandStack().markSaveLocation();
		} 
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected PaletteRoot getPaletteRoot() {
		PaletteFactory factory = new PaletteFactory();
		return factory.getPaletteRoot(ConfigManager.COMPONENT_TYPE_FIELD);
	}

	@Override
	protected void initializeGraphicalViewer()
	{
		if(model == null)
		{
			model = new Form();
		}
		getGraphicalViewer().setContents(this.model);
	}
	
	@Override
	protected void configureGraphicalViewer()
	{
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setEditPartFactory(new FormDesignerEditPartFactory(this));
		ContextMenuProvider provider = new FormContextMenuProvider(viewer, actionIds, getActionRegistry());
		viewer.setContextMenu(provider);

		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));

		((FigureCanvas) viewer.getControl()).setScrollBarVisibility(FigureCanvas.ALWAYS);
	}

	@Override
	public void commandStackChanged(EventObject event) 
	{
		if (((CommandStack)event.getSource()).isDirty())
		{
			setDirty(true);
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		else 
		{
			setDirty(false);
		}
		super.commandStackChanged(event);
	}
	
	@Override
	public SelectionSynchronizer getSelectionSynchronizer() 
	{
		return super.getSelectionSynchronizer();
	}

	@Override
	public ActionRegistry getActionRegistry() 
	{
		return super.getActionRegistry();
	}

	public Object getModel()
	{
	    return this.model;
	}
	
	@Override
	public boolean isDirty() 
	{
		return isDirty;
	}
	
	public void setDirty(boolean dirty)
	{
	    if (this.isDirty != dirty) 
	    {
			this.isDirty = dirty;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	public boolean isSaveAsAllowed() 
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions()
	{
		super.createActions();
		ActionHelper.registerFormActions(this, actionIds, getActionRegistry());
		for(String actionId : actionIds)
		{
			getSelectionActions().add(actionId);
		}
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection)
	{
		if (part.getSite().getWorkbenchWindow().getActivePage() == null)
		{
			return;
		}
		super.selectionChanged(part, selection);
	}
}
