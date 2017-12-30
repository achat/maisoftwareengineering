import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;
public class MenuFrame extends JFrame{
	
	JLabel menuLbl;
	JButton viewCustomerListBt, viewStockListBt, buyStockBt, sellStockBt;
	JPanel mainPanel;
	
	public MenuFrame()
	{
		menuLbl=new JLabel("Available Actions");
		
		mainPanel=new JPanel(new GridLayout(5,1));
		
		viewCustomerListBt=new JButton("Customer List");
		viewCustomerListBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 JOptionPane.showMessageDialog(null, "Action not implemented yet!");
	         }
	      });
		
		viewStockListBt=new JButton("Stock List");
		viewStockListBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 JOptionPane.showMessageDialog(null, "Action not implemented yet!");
	         }
	      });
		
		buyStockBt=new JButton("Buy Stock");
		buyStockBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 JOptionPane.showMessageDialog(null, "Action not implemented yet!");
	         }
	      });
		
		sellStockBt=new JButton("Sell Stock");
		sellStockBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 JOptionPane.showMessageDialog(null, "Action not implemented yet!");
	         }
	      });
		
		mainPanel.add(menuLbl);
		mainPanel.add(viewCustomerListBt);
		mainPanel.add(viewStockListBt);
		mainPanel.add(buyStockBt);
		mainPanel.add(sellStockBt);
		
		this.setContentPane(mainPanel);
		this.setSize(500,500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Stock Market");		
	}
	
	class myMenuButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==viewCustomerListBt)
			{
				//JOptionPane.showMessageDialog(this, "Action not implemented yet!");
			}
			else if(e.getSource()==viewStockListBt)
			{
				
			}
			else if(e.getSource()== buyStockBt)
			{
				
			}
			else if(e.getSource()==sellStockBt)
			{
				
			}
									
			//else if(e.getSource()==viewPostsBt)
			//		new FriendCriteriaFrame();
			//else if(e.getSource()==viewTotalUsersBt)
			//		new FriendCriteriaFrame();
			//else if(e.getSource()==viewTotalGroupsBt)
			//		new FriendCriteriaFrame();
		}
		
}
}
