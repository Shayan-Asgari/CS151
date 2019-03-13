import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ZoomFrame 
{

	public static void main(String[] args)
	{
		//Create a frame
		JFrame frame = new JFrame();
		//Initialize three buttons to put on the JFrame
		JButton zoomIn = new JButton("Zoom In");
		JButton zoomOut = new JButton("Zoom Out");
		
		CarShape shape = new CarShape(0, 200, 150,150);
		//Circle created that is initially Red width and height is 100
		//Label is created with the circle put on it. 
		JLabel label = new JLabel(shape);
	
		//Add three buttons on the frame
		frame.add(zoomIn);
		frame.add(zoomOut);//Add buttons to frame
		frame.add(label); //Add label and repaint it so that the default color is RED
		label.repaint();
		
		//Customizing the frame
		frame.setLayout(new FlowLayout());
		frame.setSize(300,700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//Lambda expressions which control ActionListeners
		zoomIn.addActionListener(event -> 
		{
				int height = shape.getIconHeight();
				int width = shape.getIconWidth();
				shape.setWidthHeight(width/2, height/2);
				label.repaint(); //Repaint ensures new color of circle is taken into effect
		});
	    zoomOut.addActionListener(event -> 
		{
			int height = shape.getIconHeight();
			int width = shape.getIconWidth();
			shape.setWidthHeight(width*2, height*2);
				label.repaint();
		});
	}

}
