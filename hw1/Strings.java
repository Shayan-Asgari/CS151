package hw1;

import java.util.HashMap;

public class Strings 
{
	public static String uniqueLetters(String str)
	{
		StringBuilder sb = new StringBuilder();
		//Comparing one character to rest of characters
		for(int i = 0; i<str.length(); i++)
		{
			String first = str.substring(i, i+1);
			for(int j = 0; j<str.length(); j++)
			{
				String second = str.substring(j,j+1);
				if(first.equals(second) && i!=j)
					break;
				else if(j==str.length()-1)
					sb.append(first);
			}
		}
		return sb.toString();
	}
	public static void main(String[] args)
	{
		System.out.println(Strings.uniqueLetters("oorinn"));
	}
}
