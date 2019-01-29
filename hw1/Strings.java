package hw1;

import java.util.HashMap;

public class Strings 
{
	public static String uniqueLetters(String str)
	{
		StringBuilder sb = new StringBuilder();
		HashMap<Character,Integer> uniqueLetters = new HashMap<>();
		for(int i = 0; i<str.length(); i++)
		{
			uniqueLetters.put(str.charAt(i), uniqueLetters.getOrDefault(str.charAt(i),0) + 1);
		}
		for(int i = 0; i<str.length(); i++)
		{
			if(uniqueLetters.get(str.charAt(i)) == 1)
				sb.append(str.charAt(i));
		}
		return sb.toString();
	}
}
