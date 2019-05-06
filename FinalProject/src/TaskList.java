import javax.swing.DefaultListModel;

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
