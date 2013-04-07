package com.work.designer.commands;

import org.eclipse.gef.commands.Command;

import com.work.designer.model.BaseElement;

public class RenameElementCommand extends Command
{
    private BaseElement source;
    private String name;
    private String oldName;
    

    public void execute()
    {
    	if (source != null)
    	{
    		source.setDisplayName(name);
    	}
    }

    public void setName(String string)
    {
        name = string;
    }

    public void setOldName(String string)
    {
        oldName = string;
    }

    public void setSource(BaseElement source) {
        this.source = source;
    }

    public void undo()
    {
    	if (source != null) {
    		source.setDisplayName(oldName);
    	}
    }
}
