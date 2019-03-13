
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ScrollShape 
{
	CarShape shape = new CarShape(0, 500, 300,300);
	JLabel label;

	public static void main(String[] args)
	{
		CarShape shape = new CarShape(0, 500, 300,300);
		JLabel label;
		ScrollShape s = new ScrollShape();
		//Create a frame
		JFrame frame = new JFrame();
		//Initialize three buttons to put on the JFrame
		JSlider js= new JSlider(JSlider.HORIZONTAL,
                -15, 15, 0);
		frame.add(js);
		js.setMajorTickSpacing(5);
		js.setMinorTickSpacing(1);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent event) 
			{
				int originalWidth = s.shape.getIconWidth();
				if(js.getValue()<0)
					s.shape.setWidthHeight(originalWidth/2, originalWidth/2);
				else if(js.getValue()>0)
					s.shape.setWidthHeight(originalWidth*2, originalWidth*2);
				s.label.repaint();
				
				
			}
			
		});
		//Circle created that is initially Red width and height is 100
		//Label is created with the circle put on it. 
		s.label = new JLabel(s.shape);
		s.label.setSize(400, 1000);
	
		frame.add(s.label,BorderLayout.SOUTH); //Add label and repaint it so that the default color is RED
		s.label.repaint();
		
		
		frame.setSize(400,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
}
