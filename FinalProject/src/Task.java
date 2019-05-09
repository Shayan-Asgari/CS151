/**
 * Task class which embodies an entity in a Task List
 *
 */
public class Task
{
	/**
    Construct a Task object.
    @param messageText the text of the message
	 */
	private String text;
	public Task(String taskText)
	{
		text = taskText;
	}

	/**
    Get the message text.
   	@return message the text of the message
	 */
	public String getText()
	{
		return text;
	}
}
