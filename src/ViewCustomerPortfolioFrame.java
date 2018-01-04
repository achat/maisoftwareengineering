import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

public class ViewCustomerPortfolioFrame extends JFrame{

	JLabel selectCustomerLbl;
	JComboBox<Customer> clientList;
	Customer selectedCustomer;
	JPanel mainPanel;
	JTable table;
	DefaultTableModel tableModel;
	
	
	public ViewCustomerPortfolioFrame()
	{
		selectCustomerLbl=new JLabel("Select customer to view his portfolio:");
		
		clientList=new JComboBox<Customer>();
		clientList.setToolTipText("Select client");
		clientList.setModel(new DefaultComboBoxModel(getClientsNames().toArray()));		
		selectedCustomer = getSelectedCustomer((String)clientList.getSelectedItem());
		
		ChangeCustomerListener actionListener = new ChangeCustomerListener();
		clientList.addItemListener(actionListener);
		
		String[] columnNames = {"Stock name", "Amount", "Value"};
		
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);		
		
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
		
	
	private ArrayList<String> getClientsNames()
	{
		ArrayList<String> clientNames = new ArrayList<String>();
		
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
	
	private void updatePortfolioTable(ArrayList<PortfolioEntry> pes)
	{
		System.out.println("Enter here");
		System.out.println(pes.size());
		tableModel.setRowCount(0);
		
		for(PortfolioEntry pe : pes)
		{
			String stockName = getStockName(pe.getStockid());
			int stockAmount = pe.getAmount();
			double stockValue = pe.getValue();
			
			Object[] data = {stockName, stockAmount, stockValue};

			tableModel.addRow(data);
		}
	}
	
	private String getStockName(int stockid)
	{
		for(Stock st:StockList.getTotalStocks())
		{
			if(st.getId()==stockid)
				return st.getName();
		}
		return "";
	}
	
	class ChangeCustomerListener implements ItemListener {
		  // This method is called only if a new item has been selected.
		  public void itemStateChanged(ItemEvent evt) {
		    JComboBox cb = (JComboBox) evt.getSource();
		    Object item = evt.getItem();

		    if (evt.getStateChange() == ItemEvent.SELECTED) {		    	
		    	selectedCustomer = getSelectedCustomer((String)cb.getSelectedItem());
		    	System.out.println(selectedCustomer.getFirstName());
		    	
		    	updatePortfolioTable(selectedCustomer.getPortfolio().getPortfolioEntries(selectedCustomer.getId()));
		    } else if (evt.getStateChange() == ItemEvent.DESELECTED) {
		    
		    }
		  }
	}
	
	
}
