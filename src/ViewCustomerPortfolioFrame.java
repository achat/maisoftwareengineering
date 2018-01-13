import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
/**
 * A frame to view the portfolio of each user*/
public class ViewCustomerPortfolioFrame extends JFrame{

	//Frame components 
	JLabel selectCustomerLbl;
	JComboBox<Customer> clientList;
	Customer selectedCustomer;
	JPanel mainPanel;
	JTable table;
	DefaultTableModel tableModel;
	
	//Constructor
	public ViewCustomerPortfolioFrame()
	{
		selectCustomerLbl=new JLabel("Select customer to view his portfolio:");
		
		clientList=new JComboBox<Customer>();
		clientList.setToolTipText("Select client");
		clientList.setModel(new DefaultComboBoxModel(getClientsNames().toArray()));		
		selectedCustomer = getSelectedCustomer((String)clientList.getSelectedItem());
		
		ChangeCustomerListener actionListener = new ChangeCustomerListener();
		clientList.addItemListener(actionListener);
		
		//JTable setup
		String[] columnNames = {"Stock name", "Amount", "Value"};
		
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);		
		
		//Put components on the frame
		BorderLayout bl = new BorderLayout();
		mainPanel = new JPanel(bl);
		
		JPanel selectClientPanel = new JPanel(new BorderLayout());
		selectClientPanel.add(selectCustomerLbl,BorderLayout.WEST);
		selectClientPanel.add(clientList,BorderLayout.EAST);
		
		mainPanel.add(selectClientPanel, BorderLayout.NORTH);
		mainPanel.add(table,BorderLayout.CENTER);	
		
		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setTitle("View Portfolios");
		this.setSize(500,500);
	}
		
	/**
	 * Populate the drop down with the names of the clients
	 * @return ArrayList (String)*/
	private ArrayList<String> getClientsNames()
	{
		ArrayList<String> clientNames = new ArrayList<String>();
		//For each client use id, first and last name
		for(Customer customer : CustomerList.getTotalClients())
		{
			clientNames.add(customer.getId() + " " + customer.getFirstName() + " "+customer.getLastName());
		}
		//Set the first client as the selected one
		selectedCustomer=CustomerList.getTotalClients().get(0);
		return clientNames;
	}
	
	/**
	 * Each time the client drop down changes update the selected client instance
	 * @param String the selection of drop down
	 * @return Customer selected customer*/
	private Customer getSelectedCustomer(String customerString)
	{
		//Split in space to get the id then use it as a key to find the customer from DB list
		String[] customerSplitStirng = customerString.split("\\s+");
		for(Customer customer : CustomerList.getTotalClients())
		{
			if(Integer.toString(customer.getId()).equals(customerSplitStirng[0]))
				return customer;
		}
		
		return null;
	}
	
	/**
	 * When the user changes the table that shows the stocks must change too
	 * @param ArrayList of customer portfolio entries*/
	private void updatePortfolioTable(ArrayList<PortfolioEntry> pes)
	{
		//Reset table model
		tableModel.setRowCount(0);
		//Re populate the table model with the new values
		for(PortfolioEntry pe : pes)
		{
			String stockName = getStockName(pe.getStockid());
			int stockAmount = pe.getAmount();
			double stockValue = pe.getValue();
			
			Object[] data = {stockName, stockAmount, stockValue};

			tableModel.addRow(data);
		}
	}
	
	/**
	 * Find the stock name from the stock id
	 * @param int stock id
	 * @return string stock name*/
	private String getStockName(int stockid)
	{
		for(Stock st:StockList.getTotalStocks())
		{
			if(st.getId()==stockid)
				return st.getName();
		}
		return "";
	}
	
	/**
	 * Fires every time the selection in the drop down of clients changes*/
	class ChangeCustomerListener implements ItemListener {
		  // This method is called only if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object item = evt.getItem();

		    if (evt.getStateChange() == ItemEvent.SELECTED) {		    	
		    	selectedCustomer = getSelectedCustomer((String)cb.getSelectedItem());
		    	
		    	updatePortfolioTable(selectedCustomer.getPortfolio().getPortfolioEntries(selectedCustomer.getId()));
		    } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
		    
		    }
		  }
	}
	
	
}
