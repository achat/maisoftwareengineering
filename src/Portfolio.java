import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Portfolio {
	
	private int id;
	private Customer customer;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private double totalValue;
	
	public Portfolio()
	{
		
	}
	
	public PortfolioEntry checkIfEntryExists(int clientid, int stockid)
	{
		try {
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			Statement myStatement = myCon.createStatement();
			ResultSet myRes = myStatement.executeQuery("select * from portfolio where clientid = "+clientid+"and stockid = "+stockid);
			
			if (myRes.next())
			{
				PortfolioEntry pe = new PortfolioEntry(myRes.getInt("id"),myRes.getInt("clientid"),myRes.getInt("stockid"),myRes.getInt("amount"),myRes.getDouble("value"));
				return pe;
			}
			myCon.close();
		} catch (SQLException e) {
			return null;
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		finally
		{
			
		}
		return null;
	}
	
	public void insertNewPortfolioEntry(int clientid, int stockid, int amount, double value)
	{
		
	}
	
	public void updateExistingPortfolioEntry(PortfolioEntry previousPortfolioEntry)
	{
		
	}
	
	//εκτυπωση πληροφοριων Portfolio
	public void printPortfolioInfo()
	{
			
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
		


}
