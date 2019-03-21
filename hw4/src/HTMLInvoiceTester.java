import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HTMLInvoiceTester 
{
	public static void main(String[] args)
	{
		  final Invoice invoice2 = new Invoice();
	      final InvoiceFormatter formatterHTML = new HTMLFormatter();
	      final InvoiceFormatter formatterHTM = new SimpleFormatter();
	      
	      // Add line items to a combo box
	      final JComboBox combo2 = new JComboBox();
	      Product hammer = new Product("Hammer", 19.95);
	      Product nails = new Product("Assorted nails", 9.95);
	      combo2.addItem(hammer);
	      Bundle bundle2 = new Bundle();
	      bundle2.add(hammer);
	      bundle2.add(nails);
	      combo2.addItem(new DiscountedItem(bundle2, 10));

	      // Make a button for adding the currently selected
	      // item to the invoice
	      JButton addButton2 = new JButton("Add");
	      addButton2.addActionListener(event ->
	      {
	            LineItem item = (LineItem) combo2.getSelectedItem();
	            invoice2.addItem(item);
	      });

	      // Put the combo box and the add button into a panel
	      JPanel panel2 = new JPanel();
	      panel2.add(combo2);
	      panel2.add(addButton2);
	      
	      JEditorPane editor = new JEditorPane();
	      editor.setContentType("text/html");
	      editor.setSize(400,400);
	      editor.setBounds(0, 0, 400, 400);
	   
	      invoice2.addChangeListener(event ->
	      editor.setText(invoice2.format(formatterHTML)));
	
	      JFrame frameForHTML = new JFrame();
	      frameForHTML.setSize(400, 400);
	      final JScrollPane scrollPane = new JScrollPane(editor);
          frameForHTML.add(scrollPane, BorderLayout.CENTER);
         
	      frameForHTML.add(panel2, BorderLayout.SOUTH);
	      frameForHTML.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	      frameForHTML.setVisible(true);
	
	}

}
