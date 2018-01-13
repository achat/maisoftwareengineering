import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;
/** Initial frame to show the menu of available actions
 * */
public class MenuFrame extends JFrame{
	
	//Frame components
	JLabel menuLbl;
	JButton viewCustomerListBt, viewStockListBt, buyStockBt, sellStockBt;
	JPanel mainPanel;
	
	//Constructor
	public MenuFrame()
	{
		menuLbl=new JLabel("Available Actions");
		
		mainPanel=new JPanel(new GridLayout(5,1));
		
		/*Four main actions*/
		//View each customers portfolio
		viewCustomerListBt=new JButton("Customer Portfolio List");
		viewCustomerListBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 new ViewCustomerPortfolioFrame();
	         }
	      });
		
		viewStockListBt=new JButton("Stock List");
		viewStockListBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 JOptionPane.showMessageDialog(null, "Action not implemented yet!");
	         }
	      });
		
		//Buy stock
		buyStockBt=new JButton("Buy Stock");
		buyStockBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 new AddStockFrame();
	         }
	      });
		
		//Sell stock
		sellStockBt=new JButton("Sell Stock");
		sellStockBt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent arg0) {
	        	 new SellStockFrame();
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
	
}
