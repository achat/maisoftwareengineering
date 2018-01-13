import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;
/**
 * The total amount of stock a customers owns*/
public class Portfolio {

	//Attribute Declarations
	private int id;
	private Customer customer;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private ArrayList<PortfolioEntry> portfolioEntries = new ArrayList<PortfolioEntry>();
	private double totalValue;

	//Constructor
	public Portfolio()
	{

	}

	/**
	 * Get a list for each entry in the DB which has the id of the current customer and means that this customers owns a specific amount of 
	 * a current stock
	 * @param int clientid
	 * @return ArrayList(PortfolioEntry)*/
	public ArrayList<PortfolioEntry> getPortfolioEntries(int clientid) {
		
		portfolioEntries.clear();
			try{
				//Initialize connection
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);

			//Create and execute a prepared statement
			String newSql = "SELECT * FROM portfolio WHERE clientid=?";
			PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(newSql);
			statement.setInt(1, clientid);
			ResultSet myRes = statement.executeQuery();

			//While there are entries for the current customer add them to the list to return
			PortfolioEntry pe=new PortfolioEntry();
			while (myRes.next())
			{
				pe = new PortfolioEntry(myRes.getInt("id"),myRes.getInt("clientid"),myRes.getInt("stockid"),myRes.getInt("amount"),myRes.getDouble("value"));
				portfolioEntries.add(pe);
			}
			//Remember to close the connection
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();			
		}

	return portfolioEntries;
}

/**
 * Set portfolio entries
 * @param ArrayList<PortfolioEntry>
 * @return void*/
public void setPortfolioEntries(ArrayList<PortfolioEntry> portfolioEntries) {
	this.portfolioEntries = portfolioEntries;
}

/**
 * Checks if there is already an entry which connects the specific client with the specific stock
 * @param int clientid
 * @param int stockid
 * @return PortfolioEntry
 * */
public PortfolioEntry checkIfEntryExists(int clientid, int stockid)
{
	try {
		//Initialize connection
		Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
		
		//Create and execute query
		String newSql = "SELECT * FROM portfolio WHERE clientid=? AND stockid=?";
		PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(newSql);
		statement.setInt(1, clientid);
		statement.setInt(2, stockid);
		ResultSet myRes = statement.executeQuery();

		//If we have a result form it as a portfolio entry and return it
		PortfolioEntry pe=new PortfolioEntry();
		while (myRes.next())
		{
			pe = new PortfolioEntry(myRes.getInt("id"),myRes.getInt("clientid"),myRes.getInt("stockid"),myRes.getInt("amount"),myRes.getDouble("value"));
		}
		//Remember to close connection
		myCon.close();
		return pe;

	} catch (SQLException e) {
		e.printStackTrace();
		return null;		
	}
}

/**
 * Get id
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
 * Get customer
 * @return Customer customer*/
public Customer getCustomer() {
	return customer;
}

/**
 * Set customer
 * @param customer*/
public void setCustomer(Customer customer) {
	this.customer = customer;
}

/**
 * Get stock list
 * @return ArrayList<Stock>*/
public ArrayList<Stock> getStocks() {
	return stocks;
}

/**
 * Set stock list
 * @param ArrayList<Stock>*/
public void setStocks(ArrayList<Stock> stocks) {
	this.stocks = stocks;
}

/**
 * Get total value of portfolio
 * @return double totalValue*/
public double getTotalValue() {
	return totalValue;
}

/**
 * Set total value of portfolio
 * @param double totalvalue*/
public void setTotalValue(double totalValue) {
	this.totalValue = totalValue;
}



}
