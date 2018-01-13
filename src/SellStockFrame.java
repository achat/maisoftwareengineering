import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

/**
 * In this frame the broker can sell the stocks the client owns
 * */
public class SellStockFrame extends JFrame{

	//Frame components
	JLabel guideLbl, selectClientLbl, selectStockLbl, selectAmountToSell, totalRevenueLbl;
	JComboBox<Customer> clientList;
	JComboBox<Stock> stockList;
	JFormattedTextField amountTxt;
	JButton sellBt;
	JPanel mainPanel;

	//Attribute declaration
	ArrayList<String> stockNames;	
	Customer selectedCustomer;
	Stock selectedStock;
	ArrayList<PortfolioEntry> currentCustomerEntries;	
	double totalRevenue;

	//Constructor
	public SellStockFrame()
	{
		stockNames= new ArrayList<String>();
		currentCustomerEntries=new ArrayList<PortfolioEntry>();

		guideLbl=new JLabel("Sell Stock");

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

		selectAmountToSell = new JLabel("Amount to sell");

		//Accept only numeric values
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		// If you want the value to be committed on each keystroke instead of focus lost
		formatter.setCommitsOnValidEdit(true);
		JFormattedTextField amountTxt = new JFormattedTextField(formatter);

		//Perform the sell action
		sellBt=new JButton("Sell stock");	    

		sellBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				boolean thereIsAvailable = false;
				int sellAmount = (int) amountTxt.getValue();
				double sellRevenue = sellAmount * selectedStock.getValue();
				double newStockCost = 0;
				//Check if the current user owns the amount he wants to sell
				for(PortfolioEntry pe : currentCustomerEntries)
				{
					if(selectedStock.getId()==pe.getStockid())
					{
						newStockCost = pe.getValue() - sellRevenue;
						if(pe.getAmount()>=(int) amountTxt.getValue())
							thereIsAvailable=true;
					}

				}

				//If the amount is available sell the stock update Stock table in DB and update also portfolio
				if(thereIsAvailable)
				{
					selectedStock.sellStock((int) amountTxt.getValue());
					selectedCustomer.updateCustomerPortfolio(selectedStock.getId(), (int) amountTxt.getValue(), newStockCost, false);
					JOptionPane.showMessageDialog(null, "The transaction was completed successfully");
					CloseFrame();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "The amount of stocks you want to sell is higher than the actual amount you own!");
				}
			}
		});

		clientList.setSelectedIndex(1);
		clientList.setSelectedIndex(0);

		//Add components on the frame
		mainPanel=new JPanel(new GridLayout(12,1));

		mainPanel.add(guideLbl);
		mainPanel.add(selectClientLbl);
		mainPanel.add(clientList);
		mainPanel.add(selectStockLbl);
		mainPanel.add(stockList);
		mainPanel.add(selectAmountToSell);
		mainPanel.add(amountTxt);
		mainPanel.add(sellBt);

		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(500,500);
		this.setTitle("Sell Stock");
	}

	/**
	 * Close frame after action is complete
	 * */
	private void CloseFrame()
	{
		setVisible(false); //you can't see me!
		dispose(); //Destroy the JFrame object
	}

	/**
	 * Get a stock and return the amount the client owns
	 * @param Stock stock
	 * @return int stock amount*/
	private int returnAvailableAmountToSell(Stock st)
	{
		for(PortfolioEntry pe : currentCustomerEntries)
		{
			if(st.getId()==pe.getStockid())
				return pe.getAmount();
		}
		return 0;
	}


	/**
	 * Get all stock names from the stocks stored in the DB
	 * @return Arraylist(String) stock names*/
	private ArrayList<String> getStockNames()
	{
		ArrayList<String> stockNames = new ArrayList<String>();

		for(Stock stock : StockList.getTotalStocks())
		{
			stockNames.add(stock.getId() + " " + stock.getName());
		}
		return stockNames;
	}

	/**
	 * Get all client names from the stocks stored in the DB
	 * @return Arraylist(String) client names*/
	private ArrayList<String> getClientsNames()
	{
		ArrayList<String> clientNames = new ArrayList<String>();

		for(Customer customer : CustomerList.getTotalClients())
		{
			clientNames.add(customer.getId() + " " + customer.getFirstName() + " "+customer.getLastName());
		}
		//Set the first customer as the selected one
		selectedCustomer=CustomerList.getTotalClients().get(0);
		return clientNames;
	}

	/**
	 * Every time the selected client changes in the drop down find the one from the DB
	 * @param String the drop down selection
	 * @return Customer customer selected*/
	private Customer getSelectedCustomer(String customerString)
	{
		//Split the drop down string in spaces to get the id and use it as key to get the client from DB list
		String[] customerSplitStirng = customerString.split("\\s+");
		for(Customer customer : CustomerList.getTotalClients())
		{
			if(Integer.toString(customer.getId()).equals(customerSplitStirng[0]))
				return customer;
		}

		return null;
	}

	/**
	 * Every time the selected stock changes in the drop down find the one from the DB
	 * @param String the drop down selection
	 * @return Stock stock selected*/
	private Stock getSelectedStock(String stockString)
	{
		if((stockString!=null)&&(stockString.length()>0))
		{
			//Split the drop down string in spaces to get the id
			String[] stockSplitStirng = stockString.split("\\s+");
			for(Stock stock : StockList.getTotalStocks())
			{
				if(Integer.toString(stock.getId()).equals(stockSplitStirng[0]))
					return stock;
			}
		}
		return null;
	}

	/**
	 * Fires when the stock drop down changes to set the selected stock and update the available to sell amount on the UI
	 * */
	class ChangeStockListener implements ItemListener {
		// This method is called only if a new item has been selected.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();
			Object item = evt.getItem();

			if (evt.getStateChange() == ItemEvent.SELECTED) {
				selectedStock = getSelectedStock((String)cb.getSelectedItem());
				selectAmountToSell.setText("Amount to sell (Available:"+ returnAvailableAmountToSell(selectedStock)+")");
			} else if (evt.getStateChange() == ItemEvent.DESELECTED) {
				System.out.println("2");
			}
		}
	}

	private Stock getStock(int stockid)
	{
		for(Stock st:StockList.getTotalStocks())
		{
			if(st.getId()==stockid)
				return st;
		}
		return null;
	}

	/**
	 * Fires every time the customer drop down changes
	 * Updates the selected customer and the drop down list of stocks to show only those that this client owns*/
	class ChangeCustomerListener implements ItemListener {
		// This method is called only if a new item has been selected.
		public void itemStateChanged(ItemEvent evt) {
			JComboBox cb = (JComboBox) evt.getSource();
			Object item = evt.getItem();

			if (evt.getStateChange() == ItemEvent.SELECTED) {		    	
				selectedCustomer = getSelectedCustomer((String)cb.getSelectedItem());
				//Get all the entries of the current customer in the portfolio DB table
				ArrayList<PortfolioEntry> entries = selectedCustomer.getPortfolio().getPortfolioEntries(selectedCustomer.getId());
				currentCustomerEntries.clear();
				currentCustomerEntries = entries;
				//Populate the stock drop down with those that the client owns
				if(entries!=null)
				{
					stockNames.clear();
					for(PortfolioEntry pe : entries)
					{
						Stock currentStock = getStock(pe.getStockid());
						stockNames.add(currentStock.getId() + " " + currentStock.getName());

					}
					stockList.setModel(new DefaultComboBoxModel(stockNames.toArray()));
					selectedStock = getSelectedStock((String)stockList.getSelectedItem());
					//Update the selected amount
					if(selectedStock!=null)
						selectAmountToSell.setText("Amount to sell (Available:"+ returnAvailableAmountToSell(selectedStock)+")");
					else
						selectAmountToSell.setText("Amount to sell (Available: 0)");
				}

			} else if (evt.getStateChange() == ItemEvent.DESELECTED) {

			}
		}
	}

}
