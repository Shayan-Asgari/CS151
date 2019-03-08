import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.Icon;

/**
   An icon that contains a moveable shape.
*/
public class ShapeIcon implements Icon
{
   private int width;
   private int height;
   private ArrayList<MoveableShape> moveableShapes;
   public ShapeIcon(int width, int height)
   {
	  moveableShapes = new ArrayList<>();
      this.width = width;
      this.height = height;
   }
   public void addMoveableShape(MoveableShape shape)
   {
	   moveableShapes.add(shape);
   }
   
   public int getIconWidth()
   {
      return width;
   }

   public int getIconHeight()
   {
      return height;
   }
   
   public ArrayList<MoveableShape> getArrayListOfShapes()
   {
	   return moveableShapes;
   }

   public void paintIcon(Component c, Graphics g, int x, int y)
   {
      Graphics2D g2 = (Graphics2D) g;
      for(MoveableShape shape: moveableShapes)
      {
    	  shape.draw(g2);
      }
   }
}