
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
   This program implements an animation that moves
   a car shape.
*/
public class AnimationTester
{

   private static final int ICON_WIDTH = 400;
   private static final int ICON_HEIGHT = 100;
   private static final int CAR_WIDTH = 100;
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();

      final MoveableShape shape = new CarShape(-500, 0, CAR_WIDTH);
      final MoveableShape shape1 = new CarShape(-200,50, CAR_WIDTH);
      final MoveableShape shape2 = new CarShape(-100,0, CAR_WIDTH);
      final MoveableShape shape3 = new CarShape(-400,50, CAR_WIDTH);

      ShapeIcon icon = new ShapeIcon(ICON_WIDTH, ICON_HEIGHT);
      icon.addMoveableShape(shape);
      icon.addMoveableShape(shape1);
      icon.addMoveableShape(shape2);
      icon.addMoveableShape(shape3);
    
      final JLabel label = new JLabel(icon);
      label.repaint();
      frame.setLayout(new FlowLayout());
      frame.add(label);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setVisible(true);

      final int DELAY = 10;
      // Milliseconds between timer ticks
      Timer t = new Timer(DELAY, event ->
      {
            shape.move();
            shape1.move();
            shape2.move();
            shape3.move();
            label.repaint();
            for(int i = 0; i<icon.getArrayListOfShapes().size(); i++)
            {
            	CarShape cs = (CarShape) icon.getArrayListOfShapes().get(i);
            	if(cs.getX()>frame.getWidth())
            	{
            		cs.setXY(cs.getOriginalX(),cs.getOriginalY());
            	}
            }
      });
      t.start();
   }
}