import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.Month;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Class which gathers the tasks for the month and displays them through a JFrame
 */
public class TaskForMonth
{
	private Month month;
	private int year;
	private TaskList<String> tasks;
	
	public TaskForMonth(Month month, int year, TaskList<String> tasks)
	{
		this.month = month;
		this.year = year;
		this.tasks = tasks;
	}
	
	public void show()
	{
		String tasksString = "";
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.setLocation(80, 300);
		frame.setLayout(new BorderLayout());
		Enumeration<String> e = tasks.elements();
		JPanel jpMonth = new JPanel();
		jpMonth.setLayout(new BorderLayout());
		
		JLabel jlMonth = new JLabel("Tasks for: " + month.toString() + ", " + year);
		Font fontMonth = new Font("Arial",Font.BOLD, 15);
		jlMonth.setFont(fontMonth);
		jlMonth.setForeground(new Color(0,100,0));
		jpMonth.add(jlMonth,BorderLayout.NORTH);
		while(e.hasMoreElements())
		{
			String task = e.nextElement();
			tasksString += "- " + task;
			tasksString += "\n";
		}
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JTextArea jt = new JTextArea(tasksString.toString());
		Font fontTwo = new Font("Arial",Font.PLAIN,18);
		jt.setFont(fontTwo);
		jt.setForeground(Color.BLACK);
		jt.setBackground(Color.WHITE);
	    	jt.setOpaque(true);
		frame.add(jpMonth, BorderLayout.NORTH);
		jp.add(jt, BorderLayout.NORTH);
		frame.add(jp,BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
