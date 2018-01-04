import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;

public class AddStockFrame extends JFrame{
	
	JLabel guideLbl, selectClientLbl, selectStockLbl, amountToBuyLbl, availableAmountGuideLbl, availableAmountLbl, totalCostGuideLbl, totalCostLbl;
	JComboBox<Customer> clientList;
	JComboBox<Stock> stockList;
	JFormattedTextField amountTxt;
	JButton buyBt;
	
	JPanel mainPanel;
	
	Customer selectedCustomer;
	Stock selectedStock;
	
	double totalCost;
	
	
	public AddStockFrame()
	{
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
		//amountTxt=new JTextField();
		
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
	    
	    PropertyChangeListener l = new PropertyChangeListener() {

	        @Override
	        public void propertyChange(PropertyChangeEvent evt) {
	            String text = evt.getNewValue() != null ? evt.getNewValue().toString() : "";
	            try
	            {
	            	int stockAmount = (int) evt.getNewValue();
	            	totalCost = stockAmount * selectedStock.getValue();
		            totalCostLbl.setText(Double.toString(totalCost));
	            }
	            catch (Exception ex)
	            {
	            	totalCostLbl.setText("0");
	            
	            }
	        }			
	    };
	    
	    	amountTxt.addPropertyChangeListener("value", l);
	    	
	    	buyBt=new JButton("Buy Stock");
			buyBt.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent arg0) {
		        	 if(!selectedStock.thereIsAvailable((int) amountTxt.getValue()))
		        		 JOptionPane.showMessageDialog(null, "The amount of stocks you ask for is higher than the actual available amount!");
		        	 else
		        	 {
		        		 selectedStock.buyStock((int) amountTxt.getValue());
		        		 selectedCustomer.updateCustomerPortfolio(selectedStock.getId(), (int) amountTxt.getValue(), totalCost);
		        		 JOptionPane.showMessageDialog(null, "The transaction was completed successfully");
		        		 CloseFrame();
		        	 }
		        		 
		         }
		      });
			
		
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
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(500,500);
		this.setTitle("Buy Stock");
		
	}
	
	private void CloseFrame()
	{
		setVisible(false); //you can't see me!
		dispose(); //Destroy the JFrame object
	}
	
	
	
	private ArrayList<String> getStockNames()
	{
		ArrayList<String> stockNames = new ArrayList<String>();
		//if(StockList.getTotalStocks().size()==0)
		//	StockList.getStocksFromDB();
		
		for(Stock stock : StockList.getTotalStocks())
		{
			stockNames.add(stock.getId() + " " + stock.getName());
		}
		//selectedStock=StockList.getTotalStocks().get(0);
		//availableAmountLbl.setText(Integer.toString(selectedStock.getFreeAmount()));
		return stockNames;
	}
	
	private ArrayList<String> getClientsNames()
	{
		ArrayList<String> clientNames = new ArrayList<String>();
		//if(CustomerList.getTotalClients().size()==0)
		//	CustomerList.getCustomersFromDB();
		
		for(Customer customer : CustomerList.getTotalClients())
		{
			clientNames.add(customer.getId() + " " + customer.getFirstName() + " "+customer.getLastName());
		}
		selectedCustomer=CustomerList.getTotalClients().get(0);
		return clientNames;
	}
	
	private Customer getSelectedCustomer(String customerString)
	{
		String[] customerSplitStirng = customerString.split("\\s+");
		for(Customer customer : CustomerList.getTotalClients())
		{
			if(Integer.toString(customer.getId()).equals(customerSplitStirng[0]))
				return customer;
		}
		
		return null;
	}
	
	private Stock getSelectedStock(String stockString)
	{
		String[] stockSplitStirng = stockString.split("\\s+");
		for(Stock stock : StockList.getTotalStocks())
		{
			if(Integer.toString(stock.getId()).equals(stockSplitStirng[0]))
				return stock;
		}
		
		return null;
	}
	
	class ChangeStockListener implements ItemListener {
		  // This method is called only if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object item = evt.getItem();

		    if (evt.getStateChange() == ItemEvent.SELECTED) {
		    	selectedStock = getSelectedStock((String)cb.getSelectedItem());
		    	availableAmountLbl.setText(Integer.toString(selectedStock.getFreeAmount()));
		    } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
		    	System.out.println("2");
		    }
		  }
	}
	
	class ChangeCustomerListener implements ItemListener {
		  // This method is called only if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object item = evt.getItem();

		    if (evt.getStateChange() == ItemEvent.SELECTED) {		    	
		    	selectedCustomer = getSelectedCustomer((String)cb.getSelectedItem());
		    } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
		    
		    }
		  }
	}
	
}
