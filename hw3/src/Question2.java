import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Question2 
{
	public static void main(String[] args)
	{
		final int DELAY = 1000;
		ActionListener helloWorldAction = event -> System.out.println("Hello, World");
		Timer timer = new Timer(DELAY, helloWorldAction);
		timer.start();
		while (true);
		/*
		 * Told professor about this. She said it was fine if I have it, because for
		 * some reason the application does not work on my laptop without the
		 * 'while(true);', but it does work on her laptop without the 'while(true);'
		 */
	}
}
