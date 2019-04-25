import java.awt.Rectangle;
public class hw5
{
	public static void dumpArray(Object[] array)
	{
		for(int i = 0; i<array.length; i++)
		{
			if(array[i] instanceof Object)
			{
				System.out.println(array[i]);
			}
		}
	}
	public static void main(String[] args)
	{
		Object[] example =  { 6, "hi", 12, new Rectangle(10, 20) };
        dumpArray(example);
	}
}
