
public class Task
{
	/**
    Construct a Task object.
    @param messageText the message text
	 */
	private String text;
	public Task(String taskText)
	{
		text = taskText;
	}

	/**
    Get the message text.
   	@return message text
	 */
	public String getText()
	{
		return text;
	}
}
