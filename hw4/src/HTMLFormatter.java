
public class HTMLFormatter implements InvoiceFormatter
{
	private double total;
	private String HTMLString;
	
	@Override
	public String formatHeader() 
	{
		total = 0;
		String invoiceHeader = new String("<b><h1 style = 'color: red'; align = 'center' >"+ "INVOICE" + "</h1></b><br><br><br>");
	    return invoiceHeader;
	}

	public String formatLineItem(LineItem item)
    {
		total += item.getPrice();
	    String lineItem =  (String.format("%s: $%.2f\n",item.toString(),item.getPrice()));
		String invoiceHeader = new String("<b><li>"+ lineItem + "</li></b><br>");
	    return invoiceHeader;
	}
	   
	public String formatDuplicate(LineItem item, int quantity)
    {
		//Creating a list when each item is printed
		total += item.getPrice();
		double valueOfProduct = item.getPrice() * quantity;
	    String lineItemDuplicate = (String.format("%s: $%.2f x%d \n",item.toString(),valueOfProduct, quantity));
		String invoiceHeader = new String("<b><li>"+ lineItemDuplicate +"</li></b><br>");
	    return invoiceHeader;
	}

	public String formatFooter()
    {
		String formattedFooter = String.format("\n\nTOTAL DUE: $%.2f\n", total);
        return "<br><br><center><font color = 'green'> <b>" + formattedFooter + "</font></center></b>";
    }
}
