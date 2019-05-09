import javax.swing.DefaultListModel;

/**
 * Generic TaskList class which embodies a data structure for tasks to be added to
 */
public class TaskList<T> extends DefaultListModel<T>
{
	public TaskList()
	{
		super();
	}
	
	public void addTask(T task)
	{
		this.addElement(task);
	}
	
	public void removeTask(T task)
	{
		this.removeElement(task);
	}
}
