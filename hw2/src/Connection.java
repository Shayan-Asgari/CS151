/**
   Connects a phone to the mail system. The purpose of this
   class is to keep track of the state of a connection, since
   the phone itself is just a source of individual key presses.
*/
public class Connection
{
	
	   private MailSystem system;
	   private Mailbox currentMailbox;
	   private String currentRecording;
	   private String accumulatedKeys;
	   private Telephone phone;
	   private int state;
	   
	   /* Added instance variables */
	   //Two boolean values control the flow of option and which questions to ask
	   private boolean mailBox;
	   private boolean password;
	   
	   //Controls flow if option 2 is pressed to later then go into password/login phase
	   private boolean actionOfLogin;
	   //Saves the repeatedMenu
	   private String repeatedMenu;
	   
	   private static final int CONNECTED = 1;
	   private static final int RECORDING = 2;
	   private static final int MAILBOX_MENU = 3;
	   private static final int MESSAGE_MENU = 4;
	   private static final int CHANGE_PASSCODE = 5;
	   private static final int CHANGE_GREETING = 6;
	   
	   
	   private static final String INVALID_ENTRY  = "Invalid Entry. Please try again.";
	   private static final String ENTER_MAILBOX = "Enter mailbox number followed by #";   
	   private static final String MAILBOX_MENU_TEXT = 
	         "Enter 1 to listen to your messages\n"
	         + "Enter 2 to change your passcode\n"
	         + "Enter 3 to change your greeting";
	   private static final String MESSAGE_MENU_TEXT = 
	         "Enter 1 to listen to the current message\n"
	         + "Enter 2 to save the current message\n"
	         + "Enter 3 to delete the current message\n"
	         + "Enter 4 to return to the main menu";
	private static final String MODIFIED_PROMPT = "To leave message, press (1), to access your mailbox, press (2)";
	private static final String ENTER_MAILBOX_FOR_PASSWORD = "Please enter your mailbox number followed by #";
	private static final String PASSWORD_PROMPT = "Please enter your passcode followed by #";
   /**
      Construct a Connection object.
      @param s a MailSystem object
      @param p a Telephone object
   */
   public Connection(MailSystem s, Telephone p)
   {
      system = s;
      phone = p;
      resetConnection();
   }

   /**
      Respond to the user's pressing a key on the phone touchpad
      @param key the phone key pressed by the user which has to be of length 1, and contain a number 
      or hashtag
   */
   public void dial(String key)
   {
      if (state == CONNECTED)
         connect(key);
      else if (state == RECORDING) //changed to add or state
         login(key);
      else if (state == CHANGE_PASSCODE)
         changePasscode(key);
      else if (state == CHANGE_GREETING)
         changeGreeting(key);
      else if (state == MAILBOX_MENU)
         mailboxMenu(key);
      else if (state == MESSAGE_MENU)
         messageMenu(key);
   }

   /**
      Record voice.
      @param voice voice spoken by the user
   */
   public void record(String voice)
   {
	  //If user types anything but number when state is MAILBOX_MENU or MESSAGE_MENU, then repeat the menu
	  if(state == MAILBOX_MENU)
		  phone.speak(MAILBOX_MENU_TEXT);
	  else if(state == MESSAGE_MENU)
		  phone.speak(MESSAGE_MENU_TEXT);  
	  else if(state == CONNECTED)
	  {
		  if(!mailBox && !password)
			  phone.speak(MODIFIED_PROMPT);
		  else if(voice.equals("*"))
			  phone.speak(repeatedMenu);
		  else
			  accumulatedKeys+=voice;
	  }
	  else if (state == RECORDING || state == CHANGE_GREETING)
		  /*In this case, we found a mailbox and they do not type in a number so we check if they are trying to 
		   log in, so we still consider it to be an accumulated key, BUT, eventually when they do type '#', 
		   the phone will print out invalid entry and the accumulated keys will be set to an empty string*/
		  if(actionOfLogin)
			  accumulatedKeys += voice;
	  	// At this point we know they are typing in letters for their message
		  else
			  this.currentRecording += currentRecording.isEmpty() ? voice: " " + voice;
      
     
   }

   /**
      The user hangs up the phone.
   */
   public void hangup()
   {
      if (state == RECORDING)
         currentMailbox.addMessage(new Message(currentRecording));
      resetConnection();
   }

   /**
      Reset the connection to the initial state and prompt
      for mailbox number
   */
   private void resetConnection()
   {
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
      phone.speak(MODIFIED_PROMPT);
      mailBox = false;
      password = false;
      actionOfLogin = false;
   }

   /**
      Try to connect the user with the specified mailbox.
      @param key the phone key pressed by the user
   */
   private void connect(String key)
   {
	  if(key.equals("1") && !mailBox && !password)
	  {
		  phone.speak(ENTER_MAILBOX);
		  mailBox = true;
		  repeatedMenu = ENTER_MAILBOX;
	  }
	  else if(key.equals("2") && !password && !mailBox)
	  {
		  System.out.println(ENTER_MAILBOX_FOR_PASSWORD);
		  password = true;
		  repeatedMenu = ENTER_MAILBOX_FOR_PASSWORD;
		
	  }
	  // if the 1 or 2 as not been types upon start up then repeat the modified/initial prompt
	  else if(!password && !mailBox)
	  {
		  phone.speak(MODIFIED_PROMPT);
	  }
	  //If accumulatedKeys is empty(' #'), or if it contains letters('2lg#') then key is invalid
	  else if((accumulatedKeys.isEmpty() || accumulatedKeys.matches(".*[a-z].*"))&& key.equals("#")) //since recorded letter
	  {
		  phone.speak(INVALID_ENTRY);
		  accumulatedKeys = "";
	  }
	  //Want to check for a valid mailbox since we know input is reasonable
	  else if (key.equals("#"))
      {
         currentMailbox = system.findMailbox(accumulatedKeys);
         if (currentMailbox != null)
         {
        	 if(password)
        	 {
        		 phone.speak(PASSWORD_PROMPT);
        		 repeatedMenu = PASSWORD_PROMPT;
        		 actionOfLogin = true; 
        		
        	 }
        	 else
        	 {
        		actionOfLogin = false;
        	 	phone.speak(currentMailbox.getGreeting());
        	 	
        	 }
        	 state = RECORDING;
        	 accumulatedKeys = "";
         }
      }
      else
         accumulatedKeys += key;
   }

   /**
      Try to log in the user.
      @param key the phone key pressed by the user
   */
   private void login(String key)
   {
	   if(key.equals("*"))
		   phone.speak(repeatedMenu);
	  // key must be apart of the login
	   else if(!key.equals("#") && actionOfLogin)
	         accumulatedKeys += key;
	  else if(key.equals("#") && actionOfLogin)
      {
		 //Checking if accumulatedKeys is an actual Password
         if (currentMailbox.checkPasscode(accumulatedKeys))
         {
            state = MAILBOX_MENU;
            phone.speak(MAILBOX_MENU_TEXT);
         }
         else
            phone.speak("Incorrect mailbox passcode. Please try again!");
         accumulatedKeys = "";
      }
	  //Must be recording/leaving a message or had mistyped in which that should still be apart of the method
	  else
	  {
		  /*To nicely format the message even if they mistype a key by putting a space after the current
		  recording then the key itself*/
		  this.currentRecording += currentRecording.isEmpty() ? key: " " + key;
				  
	  }
   }

   /**
      Change passcode.
      @param key the phone key pressed by the user
   */
   private void changePasscode(String key)
   {
      if (key.equals("#"))
      {
         currentMailbox.setPasscode(accumulatedKeys);
         state = MAILBOX_MENU;
         phone.speak(MAILBOX_MENU_TEXT);
         accumulatedKeys = "";
      }
      else
         accumulatedKeys += key;
   }

   /**
      Change greeting.
      @param key the phone key pressed by the user
   */
   private void changeGreeting(String key)
   {
      if (key.equals("#"))
      {
         currentMailbox.setGreeting(currentRecording);
         currentRecording = "";
         state = MAILBOX_MENU;
         phone.speak(MAILBOX_MENU_TEXT);
      }
   }

   /**
      Respond to the user's selection from mailbox menu.
      @param key the phone key pressed by the user
   */
   private void mailboxMenu(String key)
   {
      if (key.equals("1"))
      {
         state = MESSAGE_MENU;
         phone.speak(MESSAGE_MENU_TEXT);
      }
      else if (key.equals("2"))
      {
         state = CHANGE_PASSCODE;
         phone.speak("Enter new passcode followed by the # key");
      }
      else if (key.equals("3"))
      {
         state = CHANGE_GREETING;
         phone.speak("Record your greeting, then press the # key");
      }
      else
    	  phone.speak(MAILBOX_MENU_TEXT);
   }

   /**
      Respond to the user's selection from message menu.
      @param key the phone key pressed by the user
   */
   private void messageMenu(String key)
   {
      if (key.equals("1"))
      {
         String output = "";
         Message m = currentMailbox.getCurrentMessage();
         if (m == null) output += "No messages." + "\n";
         else output += m.getText() + "\n";
         output += MESSAGE_MENU_TEXT;
         phone.speak(output);
      }
      else if (key.equals("2"))
      {
         currentMailbox.saveCurrentMessage();
         phone.speak(MESSAGE_MENU_TEXT);
      }
      else if (key.equals("3"))
      {
         currentMailbox.removeCurrentMessage();
         phone.speak(MESSAGE_MENU_TEXT);
      }
      else if (key.equals("4"))
      {
         state = MAILBOX_MENU;
         phone.speak(MAILBOX_MENU_TEXT);
      }
      else
    	  phone.speak(MESSAGE_MENU_TEXT); 
   }
}
