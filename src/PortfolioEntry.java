import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class PortfolioEntry {
	
	private int id;
	private int clientid;
	private int stockid;
	private int amount;
	private double value;
	
	public PortfolioEntry()
	{
		
	}
	
	

	public PortfolioEntry(int id, int clientid, int stockid, int amount, double value) {
		super();
		this.id = id;
		this.clientid = clientid;
		this.stockid = stockid;
		this.amount = amount;
		this.value = value;
	}
	
	public PortfolioEntry(int clientid, int stockid, int amount, double value) {
		super();
		//this.id = id;
		this.clientid = clientid;
		this.stockid = stockid;
		this.amount = amount;
		this.value = value;
	}
	
	public void insertToDB()
	{
		try {
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			
			String sql = "INSERT INTO portfolio (clientid, stockid, amount, value) VALUES (?, ?, ?, ?)";
			 
			PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(sql);
			statement.setInt(1, clientid);
			statement.setInt(2, stockid);
			statement.setInt(3, amount);
			statement.setDouble(4, value);
			 
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
			    //System.out.println("A new user was inserted successfully!");
			}
						
			myCon.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
	}
	
	public void udateExistingToDB()
	{
		try {
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			
			String sql = "UPDATE portfolio SET clientid=?, stockid=?, amount=?, value=? WHERE id=?";
			 
			PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(sql);
			statement.setInt(1, clientid);
			statement.setInt(2, stockid);
			statement.setInt(3, amount);
			statement.setDouble(4, value);
			statement.setInt(5, this.id);
			
			 
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0) {
			    //System.out.println("An existing user was updated successfully!");
			}
			myCon.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientid() {
		return clientid;
	}

	public void setClientid(int clientid) {
		this.clientid = clientid;
	}

	public int getStockid() {
		return stockid;
	}

	public void setStockid(int stockid) {
		this.stockid = stockid;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	

}
