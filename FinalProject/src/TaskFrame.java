import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * TaskFrame class that displays a JFrame once a day is selected from the Calendar
 */
public class TaskFrame extends JFrame implements DateFormatter
{
	private LocalDate date;
	private Calendar c;
	private JLabel datePanel;
	private JList<String> list;
	private JPanel scrollPanelWithTasks;
	private TaskList<String> defaultList;
	private JTextArea textField;
	private JPanel speakerPanel;
	
	public TaskFrame(Calendar c)
	{
		this.c = c;
		speakerPanel = new JPanel();
	    datePanel = new JLabel();
	    speakerPanel.setLayout(new BorderLayout());
	    speakerPanel.add(datePanel, BorderLayout.NORTH);
	
	    defaultList = new TaskList<String>();

        scrollPanelWithTasks = new JPanel(new BorderLayout());
        
        this.list = new JList<String>(defaultList);  
        
        textField = new JTextArea(2,1);
        textField.setFont(new Font("Arial",Font.PLAIN,18));
      
        JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(list);
	      
	    list.setLayoutOrientation(JList.VERTICAL);
	    list.setFont(new Font("Arial",Font.BOLD,25));
	    
	    scrollPanelWithTasks.add(scrollPane);
	      

	    JButton deleteTask = new JButton("Delete task");
	    deleteTask.addActionListener(event -> this.delete(list.getSelectedValue()));
     

	    JButton editTask = new JButton("Edit Task");
	    editTask.addActionListener(event ->
	    {
	    	if(defaultList.isEmpty())
	    	{
	    		ImageIcon icon = getResourceImage("image_data/sadface.png", 50,50);
	    		JOptionPane.showMessageDialog(
	    				null,
	    				"There is nothing to edit. Start by adding some tasks.",
	    				"Ooops", JOptionPane.INFORMATION_MESSAGE,
	    				icon);
	    	}
	    	else if(!list.isSelectionEmpty())
	    	{
	    		String taskToEdit = list.getSelectedValue();
	    		String editedTask = JOptionPane.showInputDialog("Enter edited to-do: ");
	    		this.editTask(taskToEdit, editedTask);
	    	}	
	    	else
	    		JOptionPane.showMessageDialog(null, "Do not forget to select an item from the list you wish to edit");
	    	  
	      });
	      JButton addTask = new JButton("Add Task");
	      addTask.addActionListener(event -> 
	      {
	    	 if(!textField.getText().isEmpty())
	    		 this.addTask(textField.getText());   
	      });
	      JButton exportMonth = new JButton("Export");
	      exportMonth.addActionListener(event ->
	      {
			try 
			{
				this.export(this.getDate());
			} 
			catch (IOException e) {
				System.out.println("Unable to write tasks into file");
				System.out.println(e.getStackTrace());
			}
	      });
	      JPanel buttonPanel = new JPanel();
	      buttonPanel.add(deleteTask);
	      buttonPanel.add(editTask);
	      buttonPanel.add(addTask);
	      buttonPanel.add(exportMonth);

	      JPanel microphonePanel = new JPanel();
	      microphonePanel.setLayout(new BorderLayout());
	      microphonePanel.add(new JLabel("Task"),BorderLayout.NORTH);
	      microphonePanel.add(textField, BorderLayout.CENTER);
	      microphonePanel.add(buttonPanel, BorderLayout.SOUTH);
	      
	      this.add(speakerPanel, BorderLayout.NORTH);
	      this.add(scrollPanelWithTasks, BorderLayout.CENTER);
	      this.add(microphonePanel, BorderLayout.SOUTH);
	
	      this.pack();
	      this.setVisible(false);
	      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void setDate(LocalDate date)
	{
		this.date = date;
	}
	
	public void delete(String task)
	{
		c.deleteTask(task);
		defaultList.removeElement(task);
		this.clearInput();
	}
	
	public void addTask(String task)
	{	
		c.addTask(task);
		defaultList.addElement(task);
		this.clearInput();
		
	}
	
	public void clearInput()
	{
		this.textField.setText("");
	}
	public void editTask(String oldTask, String editedTask)
	{
		int indexOfOldTask = defaultList.indexOf(oldTask);
		defaultList.remove(indexOfOldTask);
		defaultList.add(indexOfOldTask, editedTask);
		c.updateDate(date, defaultList);
		this.clearInput();
	}
	public void export(LocalDate date) throws IOException
	{
		String formattedDate = this.format();
		String parent = "ExportedTaskLists/";
		String nameOfFile = formattedDate + ".txt";
		File file = new File(parent + nameOfFile);
		if (file.getParentFile().mkdir()) 
		{
		    file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		Enumeration<String> e = defaultList.elements();
		while(e.hasMoreElements())
		{
			pw.println(e.nextElement());
		}
		pw.flush();
		pw.close();
		bw.close();
		fw.close();
		this.list.repaint();
		scrollPanelWithTasks.repaint();
	}
	public void setDefaultList(DefaultListModel<String> list)
	{
		defaultList.clear();
		Enumeration<String> e = list.elements();
		while(e.hasMoreElements())
		{
			String task = e.nextElement();
			defaultList.addElement(task);
		}
		this.list.repaint();
		scrollPanelWithTasks.repaint();
	}
	public void updateDate(LocalDate date)
	{
		this.date = date;
		this.datePanel.setText("Day: " + format());
		datePanel.repaint();
		speakerPanel.repaint();
	}
	public LocalDate getDate()
	{
		return this.date;
	}
	public ImageIcon getResourceImage(String filepath, int x, int y)
	{
		Image rawImage = new ImageIcon(filepath).getImage();
		Image renderedImage = rawImage.getScaledInstance(x, y, Image.SCALE_DEFAULT);
		ImageIcon imageIcon = new ImageIcon(renderedImage);
		return imageIcon;
	}
	@Override
	public String format() 
	{
		return this.date.getMonth() + " " + this.date.getDayOfMonth() + ", " + this.date.getYear();
	}
}

