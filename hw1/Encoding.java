import java.util.*;
public class Encoding
{
	 public static Set<String> morseCodes(int m, int n) // m is number of dots, n is number of dashes
	 {
		 /*By initializing it to a tree set we depend on the ASCII table for comparison. 
		  * Our results should be first ordered by -
		  */
	     Set<String> morseResult = new TreeSet<>(); 
	     if(m==1 && n==0)
	     {
	    	 morseResult.add(".");
	    	 return morseResult;
	     }
	     if(n==1 && m==0)
	     {
	    	 morseResult.add("-");
	    	 return morseResult;
	     }
	     Set<String> subMorse;
	     if(m>0)
	     {
	    	 subMorse = morseCodes(m-1,n);
	    	 for(String g : subMorse)
	    	 {
	    		 morseResult.add("." + g);
	    	 }
	     }
	     if(n>0)
	     {
	    	 subMorse = morseCodes(m,n-1);
	    	 for(String g : subMorse)
	    	 {
	    		 morseResult.add( "-" + g);
	    	 }
	     }
	     return morseResult;
	 }
}


