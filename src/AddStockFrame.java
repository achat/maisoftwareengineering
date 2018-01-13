import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
/***
 * @author nasia desktop
 * In this frame the broker can buy a stock for a client
 * only if the stock is available on market
 */
public class AddStockFrame extends JFrame{
	
	//Declare frame components
	JLabel guideLbl, selectClientLbl, selectStockLbl, amountToBuyLbl, availableAmountGuideLbl, availableAmountLbl, totalCostGuideLbl, totalCostLbl;
	JComboBox<Customer> clientList;
	JComboBox<Stock> stockList;
	JFormattedTextField amountTxt;
	JButton buyBt;	
	JPanel mainPanel;
	
	//Variable declaration
	Customer selectedCustomer; //The selected customer from the customer drop down menu
	Stock selectedStock; //The selected stock from the stock drop down menu	 
	double totalCost; //Total cost depending on the selected stock value and the amount of them
	
	/**
	 * Frame constructor	 
	 */
	public AddStockFrame()
	{
		//Window component setup
		guideLbl=new JLabel("Buy Stock");
		
		selectClientLbl=new JLabel("Select client");
		clientList=new JComboBox<Customer>();
		clientList.setToolTipText("Select client");
		clientList.setModel(new DefaultComboBoxModel(getClientsNames().toArray()));		
		selectedCustomer = getSelectedCustomer((String)clientList.getSelectedItem());
		
		ChangeCustomerListener actionListener = new ChangeCustomerListener();
		clientList.addItemListener(actionListener);
		
		selectStockLbl=new JLabel("Select stock");
		stockList=new JComboBox<Stock>();
		stockList.setToolTipText("Select stock");
		stockList.setModel(new DefaultComboBoxModel(getStockNames().toArray()));
		selectedStock = getSelectedStock((String)stockList.getSelectedItem());
				
		ChangeStockListener stockListener = new ChangeStockListener();
		stockList.addItemListener(stockListener);
				
		availableAmountGuideLbl=new JLabel("Available amount:");
		availableAmountLbl=new JLabel(Integer.toString(selectedStock.getFreeAmount()));
		amountToBuyLbl=new JLabel("Amount to buy");
		
		//Only numbers must be acceptable in the desired amount of stock
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    JFormattedTextField amountTxt = new JFormattedTextField(formatter);
	    
	    totalCostGuideLbl=new JLabel("Total cost");
		totalCostLbl=new JLabel();
	    
		/**
		 * Control every change of stocks desired amount in order to calculate on the fly the cost of it
		 * The event is fired every time a key from keyboard is pressed when the amountTxt has focus
		 * */
	    PropertyChangeListener l = new PropertyChangeListener() {

	        @Override
	        public void propertyChange(PropertyChangeEvent evt) {
	        	//Get the total value of the textfield
	            String text = evt.getNewValue() != null ? evt.getNewValue().toString() : "";
	            try
	            {
	            	//try to convert it into int
	            	int stockAmount = (int) evt.getNewValue();
	            	//calculate total cost depending on the stock value and the amount the user typed in the amountTxt at that time
	            	totalCost = stockAmount * selectedStock.getValue();
	            	//set the cost as the text of the label
		            totalCostLbl.setText(Double.toString(totalCost));
	            }
	            catch (Exception ex)
	            {
	            	//if somehow there is no numeric value in the formatted textfield then set it to zero
	            	totalCostLbl.setText("0");
	            
	            }
	        }			
	    };
	    
	    	amountTxt.addPropertyChangeListener("value", l);	    	
	    	
	    	buyBt=new JButton("Buy Stock");
	    	
	    	/**
	    	 * Hit the button to buy the stock
	    	 */
			buyBt.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent arg0) {
		        	 /*Check if the stock is available
		        	   If not show warning */
		        	 if(!selectedStock.thereIsAvailable((int) amountTxt.getValue()))
		        		 JOptionPane.showMessageDialog(null, "The amount of stocks you ask for is higher than the actual available amount!");
		        	 else
		        	 {
		        		 //If stock is available call the buy stock method update stock table then update the portfolio table
		        		 selectedStock.buyStock((int) amountTxt.getValue());
		        		 selectedCustomer.updateCustomerPortfolio(selectedStock.getId(), (int) amountTxt.getValue(), totalCost, true);
		        		 JOptionPane.showMessageDialog(null, "The transaction was completed successfully");
		        		 //Close current frame return to main menu
		        		 CloseFrame();
		        	 }		        		 
		         }
		      });
			
		//Add components on the main panel of the frame
		mainPanel=new JPanel(new GridLayout(12,1));
		
		mainPanel.add(guideLbl);
		mainPanel.add(selectClientLbl);
		mainPanel.add(clientList);
		mainPanel.add(selectStockLbl);
		mainPanel.add(stockList);
		mainPanel.add(availableAmountGuideLbl);
		mainPanel.add(availableAmountLbl);
		mainPanel.add(amountToBuyLbl);
		mainPanel.add(amountTxt);
		mainPanel.add(totalCostGuideLbl);
		mainPanel.add(totalCostLbl);
		mainPanel.add(buyBt);
		
		//Set general frame properties
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(500,500);
		this.setTitle("Buy Stock");		
	}
	
	/**
	 * Close frame
	 * @return void
	 * */
	private void CloseFrame()
	{
		setVisible(false); //you can't see me!
		dispose(); //Destroy the JFrame object
	}
	
	
	/**
	 * Populates an arrayList with the id and the name of each stock as a single string
	 * @param 
	 * @return ArrayList<String>
	 * */
	private ArrayList<String> getStockNames()
	{
		ArrayList<String> stockNames = new ArrayList<String>();
		//For each stock get its id and name and store it on the arraylist
		for(Stock stock : StockList.getTotalStocks())
		{
			stockNames.add(stock.getId() + " " + stock.getName());
		}
		return stockNames;
	}
	
	/**
	 * Populates an arrayList with the id and the fullname of each client as a single string
	 * @param 
	 * @return ArrayList<String>
	 * */
	private ArrayList<String> getClientsNames()
	{
		ArrayList<String> clientNames = new ArrayList<String>();
		//For each customer get its id and firstname and lastname and store it on the arraylist
		for(Customer customer : CustomerList.getTotalClients())
		{
			clientNames.add(customer.getId() + " " + customer.getFirstName() + " "+customer.getLastName());
		}
		//Set the initial selected customer to the first of the list which is also the default selected value on the dropdown
		selectedCustomer=CustomerList.getTotalClients().get(0);
		return clientNames;
	}
	/**
	 * Gets the string of the selection of the dropdown client list and find the customer from the customers list we got from the DB
	 * @param String customerDropDownSelectionString
	 * @return Customer customer*/
	private Customer getSelectedCustomer(String customerString)
	{
		//On the drop down the customers are stored with a single string which contains their id and fullname
		//We need to split the string using spaces in order to get the id which is also the primary key of customers on the DB
		String[] customerSplitStirng = customerString.split("\\s+");
		//Using the id we are able to locate uniquely our client from the list
		for(Customer customer : CustomerList.getTotalClients())
		{
			if(Integer.toString(customer.getId()).equals(customerSplitStirng[0]))
				return customer;
		}
		
		return null;
	}
	
	/**
	 * Gets the string of the dropdown stock list and find the stock from the list we got from the DB
	 * @param String stockDropDowSelectionString
	 * @return Stock stock*/
	private Stock getSelectedStock(String stockString)
	{
		//On the drop down the stocks are stored with a single string which contains their id and name
		//We need to split the string using spaces in order to get the id which is also the primary key of stocks on the DB
		String[] stockSplitStirng = stockString.split("\\s+");
		//Using the id we are able to locate uniquely our client from the list
		for(Stock stock : StockList.getTotalStocks())
		{
			if(Integer.toString(stock.getId()).equals(stockSplitStirng[0]))
				return stock;
		}
		
		return null;
	}
	/**
	 * This listener fires every time the selection of the stock in the stock drop down list changes
	 * This updates the current selected stock and the value of the availableAmountLbl*/
	class ChangeStockListener implements ItemListener {
		  // This method is called only if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object item = evt.getItem();
		    //There are actually 2 events happening here on item is selected and one is unselected we choose which actions will be happening
		    //During each event
		    //Here we really only need the selected event to fire
		    if (evt.getStateChange() == ItemEvent.SELECTED) {
		    	selectedStock = getSelectedStock((String)cb.getSelectedItem());
		    	availableAmountLbl.setText(Integer.toString(selectedStock.getFreeAmount()));
		    } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
		    	
		    }
		  }
	}
	
	/**
	 * This listener fires every time the selection of the customer in the customer drop down list changes
	 * This updates the current selected customerl*/
	class ChangeCustomerListener implements ItemListener {
		  // This method is called only if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object item = evt.getItem();
		    //There are actually 2 events happening here on item is selected and one is unselected we choose which actions will be happening
		    //During each event
		    //Here we really only need the selected event to fire
		    if (evt.getStateChange() == ItemEvent.SELECTED) {		    	
		    	selectedCustomer = getSelectedCustomer((String)cb.getSelectedItem());
		    } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
		    
		    }
		  }
	}
	
}
