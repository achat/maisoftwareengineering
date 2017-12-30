import java.awt.*;

import javax.swing.*;

public class AddStockFrame extends JFrame{
	
	JLabel guideLbl, selectClientLbl, selectStockLbl, amountToBuyLbl, availableAmountGuideLbl, availableAmountLbl, totalCostGuideLbl, totalCostLbl;
	JComboBox<Client> clientList;
	JComboBox<Stock> stockList;
	JTextField amountTxt;
	JButton buyBt;
	
	JPanel mainPanel;
	
	
	public AddStockFrame()
	{
		guideLbl=new JLabel("Buy Stock");
		
		selectClientLbl=new JLabel("Select client");
		clientList=new JComboBox<Client>();
		clientList.setToolTipText("Select client");
		selectStockLbl=new JLabel("Select stock");
		stockList=new JComboBox<Stock>();
		stockList.setToolTipText("Select stock");
		
		availableAmountGuideLbl=new JLabel("Available amount:");
		availableAmountLbl=new JLabel();
		amountToBuyLbl=new JLabel("Amount to buy");
		amountTxt=new JTextField();
		totalCostGuideLbl=new JLabel("Total cost");
		totalCostLbl=new JLabel();
		
		buyBt=new JButton("Buy Stock");
		
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

}
