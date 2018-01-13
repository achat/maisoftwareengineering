import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.*;
/**
 * Entity for a stock in the stock market*/
public class Stock {

	//Attribute declaration
	private int id;
	private String name;
	private double value;
	private int totalAmount;
	private int boughtAmount;
	private int freeAmount;
	
	
	//Constructors
	public Stock(int id, String name, double value, int totalAmount) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.totalAmount = totalAmount;
	}
		
	public Stock(int id, String name, double value, int totalAmount, int boughtAmount, int freeAmount) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.totalAmount = totalAmount;
		this.boughtAmount = boughtAmount;
		this.freeAmount = freeAmount;
	}
	
	/**
	 * Check if there is available amount of the stock in the market
	 * @param int the amount we want to check
	 * @return boolean true if the amount is available, false if not*/
	public boolean thereIsAvailable(int amountToAsk)
	{
		if(amountToAsk<=freeAmount)
			return true;
		
		return false;
	}
	
	/**
	 * Update free and bought amount of the stock and update stock in the DB
	 * @param int amount of the stock
	 * */
	public void buyStock(int amount)
	{
		freeAmount-=amount;
		boughtAmount+=amount;
		updateStockInDB(freeAmount, boughtAmount);		
	}
	
	/**
	 * Update free and bought amount of the stock and update stock in the DB
	 * @param int amount of the stock
	 * */
	public void sellStock(int amount)
	{
		freeAmount+=amount;
		boughtAmount-=amount;
		updateStockInDB(freeAmount, boughtAmount);
		
	}
	
	/**
	 * Update stock in the DB
	 * @param int the new stock free amount
	 * @param int the new stock bought amount
	 * */
	public void updateStockInDB(int freeAmount, int boughtAmount)
	{
		try {
			//Initialize DB connection
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			//Create and execute query
			String query = "update stocks set availableAmount = ?, boughtAmount = ? where id = ?";
		    PreparedStatement preparedStmt = (PreparedStatement) myCon.prepareStatement(query);
		    preparedStmt.setInt(1, freeAmount);
		    preparedStmt.setInt(2, boughtAmount);
		    preparedStmt.setInt(3, id);
		    preparedStmt.executeUpdate();	
		    //Remember to close DB connection
			myCon.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getBoughtAmount() {
		return boughtAmount;
	}
	public void setBoughtAmount(int boughtAmount) {
		this.boughtAmount = boughtAmount;
	}
	public int getFreeAmount() {
		return freeAmount;
	}
	public void setFreeAmount(int freeAmount) {
		this.freeAmount = freeAmount;
	}
	
	
	
	
	
}
