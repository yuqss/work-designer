package com.work.designer.palette;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;

import com.work.designer.config.Component;
import com.work.designer.config.ConfigManager;
import com.work.designer.config.Container;
import com.work.designer.figures.TaskFigure;
import com.work.designer.model.ModelCreationFactory;
import com.work.designer.model.Transition;

public class PaletteFactory 
{
	public PaletteRoot getPaletteRoot(String type)
	{
	    PaletteRoot palette = new PaletteRoot();
	    palette.addAll(createCategories(palette, type));
	    return palette;
	}
	
	private List<PaletteEntry> createCategories(PaletteRoot root, String type) {
		List<PaletteEntry> categories = new ArrayList<PaletteEntry>();
		categories.add(createControlGroup(root));
		if (type.equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_FIELD))
		{
			categories.add(createFieldDrawer());
		}
		else if(type.equalsIgnoreCase(ConfigManager.COMPONENT_TYPE_TASK))
		{
			categories.add(createTaskDrawer(root));
		}
		return categories;
	}
	
	private PaletteContainer createTaskDrawer(PaletteRoot root)
	{
		Container container = ConfigManager.getContainer();
		if(container == null)
		{
			return null;
		}
		
		String group = "流程组件";
		
		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();
		PaletteDrawer drawer = new PaletteDrawer(group, ImageDescriptor.createFromFile(TaskFigure.class, "icons/home.png"));

	    ConnectionCreationToolEntry connectionToolEntry = new ConnectionCreationToolEntry(
	    	      "transition", "创建任务连接线", new ModelCreationFactory(null, Transition.class), 
	    	      ImageDescriptor.createFromFile(TaskFigure.class, 
	    	      "icons/flow_sequence.png"), 
	    	      ImageDescriptor.createFromFile(TaskFigure.class, 
	    	      "icons/flow_sequence.png"));
	    
	    drawer.add(connectionToolEntry);
	    
		List<Component> listComp = container.getComponents();

		for (int i = 0; i < listComp.size(); i++) 
		{
			Component comp = (Component) listComp.get(i);
			if (!validate(ConfigManager.COMPONENT_TYPE_TASK, comp)) 
			{
				continue;
			}
			PaletteEntry paletteEntry = createCombinedEntry(comp);
			if (paletteEntry == null)
			{
				continue;
			}
			entries.add(paletteEntry);
		}

		drawer.addAll(entries);
		return drawer;
	}
	
	private PaletteContainer createControlGroup(PaletteRoot root) 
	{
		PaletteGroup controlGroup = new PaletteGroup("Control Group");

		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

		ToolEntry tool = new PanningSelectionToolEntry();
		entries.add(tool);
		root.setDefaultEntry(tool);

		tool = new MarqueeToolEntry();
		entries.add(tool);

		PaletteSeparator sep = new PaletteSeparator("com.work.formdesigner.editor.separator");
		sep.setUserModificationPermission(1);
		entries.add(sep);

		controlGroup.addAll(entries);
		return controlGroup;
	}
	
	private PaletteContainer createFieldDrawer() {
		Container container = ConfigManager.getContainer();
		if(container == null)
		{
			return null;
		}
		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();
		String group = "表单控件";
		PaletteDrawer drawer = new PaletteDrawer(group, ImageDescriptor.createFromFile(TaskFigure.class, "icons/home.png"));

		List<Component> listComp = container.getComponents();

		for (int i = 0; i < listComp.size(); i++) 
		{
			Component comp = (Component) listComp.get(i);
			if (!validate(ConfigManager.COMPONENT_TYPE_FIELD, comp)) 
			{
				continue;
			}
			PaletteEntry paletteEntry = createCombinedEntry(comp);
			if (paletteEntry == null)
			{
				continue;
			}
			entries.add(paletteEntry);
		}

		drawer.addAll(entries);
		drawer.setInitialState(2);
		return drawer;
	}
	
	private boolean validate(String groupName, Component comp)
	{
	    if (!comp.getGroup().equalsIgnoreCase(groupName))
	    {
	    	return false;
	    }

	    return (comp.getVisible() == null) || (!"false".equalsIgnoreCase(comp.getVisible()));
	}	
	
	private CombinedTemplateCreationEntry createCombinedEntry(Component compponent) 
	{
		Class<?> clazz = null;
		try 
		{
			clazz = Class.forName("com.work.designer.model." + compponent.getName());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		if (clazz == null)
		{
			return null;
		}
		
		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				compponent.getDisplayName(), compponent.getDescript(), clazz,
				new ModelCreationFactory(compponent, clazz),
				ImageDescriptor.createFromFile(TaskFigure.class, compponent.getIconSmall()), 
				ImageDescriptor.createFromFile(TaskFigure.class, compponent.getIconLarge()));
		return combined;
	}	
}
