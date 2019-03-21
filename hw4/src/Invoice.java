import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
   An invoice for a sale, consisting of line items.
*/
public class Invoice
{
   /**
      Constructs a blank invoice.
   */
   private ArrayList<LineItem> duplicates;
   public Invoice()
   {
      items = new ArrayList<>();
      listeners = new ArrayList<>();
   }
  /**
      Adds an item to the invoice.
      @param item the item to add
   */
   public void addItem(LineItem item)
   {
	   items.add(item);
      // Notify all observers of the change to the invoice
      ChangeEvent event = new ChangeEvent(this);
      for (ChangeListener listener : listeners)
         listener.stateChanged(event);
   }

   /**
      Adds a change listener to the invoice.
      @param listener the change listener to add
   */
   public void addChangeListener(ChangeListener listener)
   {
      listeners.add(listener);
   }

   /**
      Gets an iterator that iterates through the items.
      @return an iterator for the items
   */
   public Iterator<LineItem> getItems()
   {
      return new
         Iterator<LineItem>()
         {
            public boolean hasNext()
            {
               return current < items.size();
            }

            public LineItem next()
            {
               return items.get(current++);
            }

            public void remove()
            {
               throw new UnsupportedOperationException();
            }

            private int current = 0;
         };
   }
   public int calculateDuplicate(LineItem c)
   {
	   int count = 0;
	   for(LineItem li : items)
	   {
		   if(li.toString().equals(c.toString()))
			   count++;
	   }
	   return count;
   }

   public String format(InvoiceFormatter formatter)
   {
      String header = formatter.formatHeader();
      String body = "";
      Iterator<LineItem> iter = getItems();
      while (iter.hasNext())
      {
    	  LineItem nextItem = iter.next();
    	  int howMany = calculateDuplicate(nextItem);
    	  if(howMany>1)
    	  {
    		  String formatted = formatter.formatDuplicate(nextItem,howMany); 
    		  if(!body.contains(formatted)) 
    			  body += formatted;
    	  }
    	  else 
    	  {
    		  body += formatter.formatLineItem(nextItem);//First time seeing it in array list, then just append
    	  }
      }
      return header + body + formatter.formatFooter();
   }
   
   private ArrayList<LineItem> items;
   private ArrayList<ChangeListener> listeners;
  
}
