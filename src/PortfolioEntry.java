import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
/**
 * Each portfolio entry connects a client with a specific stock
 * Keeps track of the amount of stocks the client has and the total value of them*/
public class PortfolioEntry {
	//Attribute declaration
	private int id;
	private int clientid;
	private int stockid;
	private int amount;
	private double value;
	
	//Constructors
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
		this.clientid = clientid;
		this.stockid = stockid;
		this.amount = amount;
		this.value = value;
	}
	
	/**
	 * Insert new entry in the DB
	 * It is called if there is no previous connection of the user with the current stock
	 * */
	public void insertToDB()
	{
		try {
			//Initialize connection
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			
			//Create and execute statement
			String sql = "INSERT INTO portfolio (clientid, stockid, amount, value) VALUES (?, ?, ?, ?)";
			 
			PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(sql);
			statement.setInt(1, clientid);
			statement.setInt(2, stockid);
			statement.setInt(3, amount);
			statement.setDouble(4, value);
			 
			int rowsInserted = statement.executeUpdate();
			//Remember to close connection		
			myCon.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * If a user is already connected with the stock and buys or sells an amount of them the entry is not inserted as new it is just updated
	 * */
	public void udateExistingToDB()
	{
		try {
			//Initialize connection
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			
			//Create and execute query
			String sql = "UPDATE portfolio SET clientid=?, stockid=?, amount=?, value=? WHERE id=?";
			 
			PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(sql);
			statement.setInt(1, clientid);
			statement.setInt(2, stockid);
			statement.setInt(3, amount);
			statement.setDouble(4, value);
			statement.setInt(5, this.id);			
			 
			int rowsUpdated = statement.executeUpdate();

			//Remember to close connection
			myCon.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**Get id
	 * @return int id*/
	public int getId() {
		return id;
	}

	/**Set id
	 * @param int id*/
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get client id
	 * @return int client id*/
	public int getClientid() {
		return clientid;
	}

	/**Set client id
	 * @param int client id*/
	public void setClientid(int clientid) {
		this.clientid = clientid;
	}

	/**
	 * Get stock id
	 * @return int stock id*/
	public int getStockid() {
		return stockid;
	}

	/**Set stock id
	 * @param int stock id*/
	public void setStockid(int stockid) {
		this.stockid = stockid;
	}

	/**
	 * Get stock amount
	 * @return int stock amount*/
	public int getAmount() {
		return amount;
	}

	/**
	 * Set stock amount
	 * @param int stock amount*/
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Get total stock value
	 * @return double stock value*/
	public double getValue() {
		return value;
	}

	/**
	 * Set total stock value
	 * @param double stock value*/
	public void setValue(double value) {
		this.value = value;
	}
	
	

}
