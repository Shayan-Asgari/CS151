import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

public class Circle implements Icon
{
	 private int width;
	 private int height;
	 private Color color;
	 public Circle(int width, int height, Color color)
	 {
		 this.width = width;
	     this.height = height;
	     this.color = color;
	 }
	 @Override
	 public int getIconHeight()
	 {
	 	return this.height;
	 }

	 @Override
	 public int getIconWidth() 
	 {
		// TODO Auto-generated method stub
	 	return this.width;
	 }	
	 
	 /*
	  * Changes the color of the circle object when repaint is called for the JLabel
	  *  depending  https://sjsu.zoom.us/j/774396333on which button is called
	  *  @param newColor the color of the button clicked
	  */
	 public void setColor(Color newColor)
	 {
	 	this.color = newColor;
	 }
	 
	 /*
	  * Paints a circle initially with the color red and is subject to change when different buttons are clicked
	  * @param c component of the Icon
	  * @param g Graphics used by Java which is to be converted to 2D using casting
	  * @param x x coordinate of the icon
	  * @param y y coordinate of the icon
	  */
	 @Override
	 public void paintIcon(Component c, Graphics g, int x, int y) 
	 {
	 	Graphics2D g2d = (Graphics2D) g;	 	
	 	Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, width, height);
 		g2d.setColor(color);
		g2d.fill(circle);
	 	g2d.draw(circle);
	 }
}
