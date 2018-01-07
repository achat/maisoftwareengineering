import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class Portfolio {

	private int id;
	private Customer customer;
	private ArrayList<Stock> stocks = new ArrayList<Stock>();
	private ArrayList<PortfolioEntry> portfolioEntries = new ArrayList<PortfolioEntry>();
	private double totalValue;

	public Portfolio()
	{

	}





	public ArrayList<PortfolioEntry> getPortfolioEntries(int clientid) {
		//if((portfolioEntries==null)||(portfolioEntries.size()==0))
		//{
		portfolioEntries.clear();
			try{
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);

			String newSql = "SELECT * FROM portfolio WHERE clientid=?";

			//String sql = "SELECT * from portfolio where clientid = ? and stockid = ?";

			PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(newSql);
			statement.setInt(1, clientid);


			ResultSet myRes = statement.executeQuery();

			//Statement myStatement = myCon.createStatement();
			//ResultSet myRes = myStatement.executeQuery("select * from portfolio where clientid = "+clientid+"and stockid = "+stockid);
			PortfolioEntry pe=new PortfolioEntry();
			while (myRes.next())
			{
				pe = new PortfolioEntry(myRes.getInt("id"),myRes.getInt("clientid"),myRes.getInt("stockid"),myRes.getInt("amount"),myRes.getDouble("value"));
				portfolioEntries.add(pe);
			}

			myCon.close();
			//return pe;

		} catch (SQLException e) {
			e.printStackTrace();
			//return null;
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		finally
		{

		}
	//}

	return portfolioEntries;
}





public void setPortfolioEntries(ArrayList<PortfolioEntry> portfolioEntries) {
	this.portfolioEntries = portfolioEntries;
}





public PortfolioEntry checkIfEntryExists(int clientid, int stockid)
{
	try {
		Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);

		System.out.println("Client: "+clientid);
		System.out.println("Stock: "+stockid);

		String newSql = "SELECT * FROM portfolio WHERE clientid=? AND stockid=?";

		//String sql = "SELECT * from portfolio where clientid = ? and stockid = ?";

		PreparedStatement statement = (PreparedStatement) myCon.prepareStatement(newSql);
		statement.setInt(1, clientid);
		statement.setInt(2, stockid);


		ResultSet myRes = statement.executeQuery();

		//Statement myStatement = myCon.createStatement();
		//ResultSet myRes = myStatement.executeQuery("select * from portfolio where clientid = "+clientid+"and stockid = "+stockid);
		PortfolioEntry pe=new PortfolioEntry();
		while (myRes.next())
		{
			System.out.println("Res: "+myRes.getInt("id"));
			pe = new PortfolioEntry(myRes.getInt("id"),myRes.getInt("clientid"),myRes.getInt("stockid"),myRes.getInt("amount"),myRes.getDouble("value"));

		}

		myCon.close();
		return pe;

	} catch (SQLException e) {
		e.printStackTrace();
		return null;
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
	finally
	{

	}
	//return null;
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
