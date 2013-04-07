package com.work.designer.figures;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

import com.work.designer.utils.ColorUtils;

public class FieldBorder extends LineBorder
{
	 protected int grabBarWidth = -1;

	  private String text = "";
	  private int BLANK_SPACES = 2;
	  private int WORD_HEIGHT = 6;

	  protected Dimension grabBarSize = null;

	  public FieldBorder(String text)
	  {
	    this.text = text;
	    this.grabBarWidth = 100;
	    this.grabBarSize = new Dimension(this.grabBarWidth, 18);
	  }

	  public void setText(String text)
	  {
	    this.text = text;
	  }

	  public Insets getInsets(IFigure figure)
	  {
	    return new Insets(getWidth() + 2, this.grabBarWidth + 2, getWidth() + 2, 
	      getWidth() + 2);
	  }

	  public Dimension getPreferredSize()
	  {
	    return this.grabBarSize;
	  }

	  public void paint(IFigure figure, Graphics graphics, Insets insets)
	  {
	    Rectangle bounds = figure.getBounds();
	    Rectangle r = new Rectangle(bounds.x, bounds.y, this.grabBarWidth, 
	      bounds.height);
	    AbstractBorder.tempRect.setBounds(r);
	    graphics.setBackgroundColor(ColorUtils.Blue);

	    graphics.setForegroundColor(ColorUtils.xorGate);
	    graphics.fillRectangle(AbstractBorder.tempRect);
	    figure.setOpaque(false);

	    int i = AbstractBorder.tempRect.bottom() - AbstractBorder.tempRect.height / 2;
	    graphics.drawText(getText(), AbstractBorder.tempRect.x + this.BLANK_SPACES, i - this.WORD_HEIGHT);
	    super.paint(figure, graphics, insets);
	  }

	  public void setGrabBarWidth(int width)
	  {
	    this.grabBarWidth = width;
	  }

	  public String getText()
	  {
		  if(this.text == null)
			  this.text = "";
	    return this.text;
	  }
}
