import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/**
 * Calendar class which displays a Gregorian based calendar on a JFrame
 */
public class Calendar extends JFrame

{
	private TaskFrame tf;
	HashMap<LocalDate, TaskList<String>> map;
	private YearMonth yearMonth;
	public Calendar() 
	{
		this.setLayout(new BorderLayout());
		tf = new TaskFrame(this);
		tf.setLocation(1300,300);
		tf.setSize(600, 400);
		map = new HashMap<>();
		setTitle("MyCalendar");
		this.setLocation(700,300);
		this.setSize(640, 480);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        yearMonth = YearMonth.now();
        JButton jb = new JButton("Tasks for Month");
        jb.addActionListener(event ->
        {
        	TaskList<String> tasks = new TaskList<String>();
			for(LocalDate ld : map.keySet())
			{
				if((ld.getMonth().equals(yearMonth.getMonth())) && (ld.getYear() == (yearMonth.getYear())))
				{
					TaskList<String> temp = map.get(ld);
					Enumeration<String> e = temp.elements();
					while(e.hasMoreElements())
					{
						String task = e.nextElement();
						tasks.addElement(task);
					}
				}
			}
			TaskForMonth tfm = new TaskForMonth(yearMonth.getMonth(), yearMonth.getYear(), tasks);
			tfm.show();
        }
        );
        
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        DatePickerSettings settings = new DatePickerSettings();
        CalendarPanel calendarPanel = new CalendarPanel(settings);
        settings.setSizeDatePanelMinimumHeight(350);
        settings.setSizeDatePanelMinimumWidth(550);
        calendarPanel.setVisible(true);
        p.add(calendarPanel, BorderLayout.CENTER);
        p.add(jb, BorderLayout.SOUTH);
        this.add(p, BorderLayout.CENTER);
        calendarPanel.addCalendarListener(new SampleCalendarListener());
        this.setVisible(true);
        this.pack();
	}
	
	public HashMap<LocalDate, TaskList<String>> getModel()
	{
		return this.map;
	}
	
	public void addTask(String task)
	{
		LocalDate dateOfTaskFrame = tf.getDate();
		TaskList<String> model = map.get(dateOfTaskFrame);
		model.addElement(task);
		map.put(dateOfTaskFrame, model);
	}
	
	public void addTaskAtIndex(int index, String task)
	{
		LocalDate dateOfTaskFrame = tf.getDate();
		TaskList<String> model = map.get(dateOfTaskFrame);
		model.addElement(task);
		map.put(dateOfTaskFrame, model);
	}
	
	public void deleteTask(String task)
	{
		LocalDate dateOfTaskFrame = tf.getDate();
		TaskList<String> model = map.get(dateOfTaskFrame);
		model.removeElement(task);
		map.put(dateOfTaskFrame, model);
	}
	
	public void updateDate(LocalDate ld, TaskList<String> list)
	{
		TaskList<String> temp = new TaskList<String>();
		Enumeration<String> e = list.elements();
		while(e.hasMoreElements())
		{
			String task = e.nextElement();
			temp.addElement(task);
		}
		map.put(ld, temp);
	}
	
	class SampleCalendarListener implements CalendarListener
	{
        @Override
        public void selectedDateChanged(CalendarSelectionEvent event) 
        {
        	LocalDate date = event.getNewDate();
        	if(date!=null)
        	{
        		tf.setVisible(true);
        		tf.updateDate(date);
        		TaskList<String> model = new TaskList<String>();
        		if(!map.containsKey(date))
        		{
        			map.put(date, model);	
        			tf.setDefaultList(model);
        		}
        		else
    			{	
        			tf.setDefaultList(map.get(date));
    			}
        	}
        
        }
		@Override
		public void yearMonthChanged(YearMonthChangeEvent change) 
		{
			yearMonth = change.getNewYearMonth();
		
		}
	}
}
