import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CircleFrame
{
	public static void main(String[] args)
	{
		//Create a frame
		JFrame frame = new JFrame();
		//Initialize three buttons to put on the JFrame
		JButton redButton = new JButton("Red");
		JButton greenButton = new JButton("Green");
		JButton blueButton = new JButton("Blue");
		
		//Circle created that is initially Red width and height is 100
		Circle colorCircle = new Circle(100,100, Color.RED);
		//Label is created with the circle put on it. 
		JLabel label = new JLabel(colorCircle);
	
		//Add three buttons on the frame
		frame.add(redButton);
		frame.add(greenButton);//Add buttons to frame
		frame.add(blueButton); 
		frame.add(label); //Add label and repaint it so that the default color is RED
		label.repaint();
		
		//Customizing the frame
		frame.setLayout(new FlowLayout());
		frame.setSize(300,250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		//Lambda expressions which control ActionListeners
		redButton.addActionListener(event -> 
		{
				colorCircle.setColor(Color.RED);
				label.repaint(); //Repaint ensures new color of circle is taken into effect
		});
		blueButton.addActionListener(event -> 
		{
				colorCircle.setColor(Color.BLUE);
				label.repaint();
		});
		greenButton.addActionListener(event -> 
		{
				colorCircle.setColor(Color.GREEN);
				label.repaint();
		});
	}
}