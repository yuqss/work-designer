package com.work.designer.editors;

import java.io.File;
import java.io.FileReader;
import java.util.EventObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleSnapToGeometryAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;

import com.work.designer.DesignerEditor;
import com.work.designer.actions.ProcessContextMenuProvider;
import com.work.designer.config.ConfigManager;
import com.work.designer.model.BaseElement;
import com.work.designer.model.ModelHelper;
import com.work.designer.model.Process;
import com.work.designer.palette.PaletteFactory;
import com.work.designer.parts.WorkDesignerEditPartFactory;

public class ProcessDesignerEditor extends AbstractEditorPage 
{
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
	    super.init(site, input);
		try 
		{
			IFile file = ((IFileEditorInput)input).getFile();
			this.model = create(file);
			if (this.model == null)
			{
				throw new PartInitException("The specified input is not a valid network.");
			}
		} 
		catch (CoreException e) 
		{
			throw new PartInitException(e.getStatus());
		} 
		catch (ClassCastException e)
		{
			throw new PartInitException("The specified input is not a valid network.", e);
		}

		setSite(site);
		setInput(input);
	}

	public ProcessDesignerEditor(DesignerEditor parent, IFile file) 
	{
		super(parent);
	    try
	    {
			parent.model = ((BaseElement) this.model);
		} 
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
		DefaultEditDomain defaultEditDomain = new DefaultEditDomain(this);
		setEditDomain(defaultEditDomain);
	}
	
	private Process create(IFile file) throws CoreException 
	{
		Process process = null;

		try 
		{
	    	JAXBContext context = JAXBContext.newInstance(Process.class);
	    	Unmarshaller unmarshaller = context.createUnmarshaller();
	    	
	    	File location = new File(file.getLocationURI());
	    	FileReader reader = new FileReader(location);
	    	process = (Process)unmarshaller.unmarshal(reader);
	    	
	    	ModelHelper.processModel(process, location);
	    	ModelHelper.buildCascade(process);
		}
		catch (Exception e)
		{
			process = new Process();
		}
		return process;
	}

	@Override
	public String getPageName() 
	{
		return "流程定义";
	}

	@Override
	protected PaletteRoot getPaletteRoot() 
	{
		PaletteFactory factory = new PaletteFactory();
		return factory.getPaletteRoot(ConfigManager.COMPONENT_TYPE_TASK);
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
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();

	    GraphicalViewer viewer = getGraphicalViewer();
	    viewer.setRootEditPart(rootEditPart);
	    getGraphicalViewer().setEditPartFactory(new WorkDesignerEditPartFactory(getParentEditor()));
	    configureEditPartViewer(viewer);
	    ((FigureCanvas)viewer.getControl()).setScrollBarVisibility(FigureCanvas.ALWAYS);
        getActionRegistry().registerAction(new ToggleGridAction(getGraphicalViewer())); 
        getActionRegistry().registerAction(new ToggleSnapToGeometryAction(getGraphicalViewer()));    
	}

	protected void configureEditPartViewer(EditPartViewer viewer) 
	{
		ScalableFreeformRootEditPart rootEditPart = new ScalableFreeformRootEditPart();
		viewer.setRootEditPart(rootEditPart);
	    getGraphicalViewer().setContextMenu(new ProcessContextMenuProvider(getParentEditor().getActionRegistry(), getGraphicalViewer()));
		
		((FigureCanvas) viewer.getControl()).setScrollBarVisibility(FigureCanvas.ALWAYS);
	}

	@Override
	public void doSave(IProgressMonitor monitor)
	{
	    try 
	    {
            JAXBContext context = JAXBContext.newInstance(Process.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            IFile file = ((IFileEditorInput)getEditorInput()).getFile();
            File location = new File(file.getLocationURI());
            marshaller.marshal(model, location);
            
            ModelHelper.processXML((Process)this.model, location);
    		getCommandStack().markSaveLocation();
		} 
	    catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void save(IFile file, IProgressMonitor progressMonitor) throws CoreException
	{
		if (progressMonitor == null) 
		{
			progressMonitor = new NullProgressMonitor();
		}
		progressMonitor.beginTask("Saving " + file, 2);
		try 
		{
			progressMonitor.worked(1);
			file.refreshLocal(0, new SubProgressMonitor(progressMonitor, 1));
			progressMonitor.done();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void commandStackChanged(EventObject event)
	{
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection)
	{
		if (part.getSite().getWorkbenchWindow().getActivePage() == null)
		{
			return;
		}
		super.selectionChanged(part, selection);
	}
}
