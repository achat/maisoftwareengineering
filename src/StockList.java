import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * A list of all available stocks in the market*/
public class StockList {

	private static ArrayList<Stock> totalStocks = new ArrayList<Stock>();
	
	/**
	 * Get stocks from the DB
	 * @return ArrayList (Stock) available stocks*/
	public static ArrayList<Stock> getStocksFromDB()
	{
		try {
			//Initialize DB connection
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			//Create and execute query
			Statement myStatement = myCon.createStatement();
			ResultSet myRes = myStatement.executeQuery("select * from stocks");
			//While we have results create an instance of a stock and add it to the local list
			while (myRes.next())
			{
				if(totalStocks==null)
					totalStocks=new ArrayList<Stock>();
				
				totalStocks.add(new Stock(myRes.getInt("id"),myRes.getString("name"),myRes.getDouble("value"),myRes.getInt("totalAmount"),myRes.getInt("boughtAmount"),myRes.getInt("availableAmount")));
			}
			//Remember to close the DB connection
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return totalStocks;
	}


	/**
	 * Check it the local list of stocks is null then populate it from the DB and return it
	 * @return ArrayList (Stock) available stocks*/
	public static ArrayList<Stock> getTotalStocks() {
		
		if((totalStocks==null)||(totalStocks.size()==0))
			totalStocks=getStocksFromDB();
		
		return totalStocks;
	}


	/**
	 * Set available local stocks
	 * @param ArrayList (Stocks)*/
	public static void setTotalStocks(ArrayList<Stock> totalStocks) {
		StockList.totalStocks = totalStocks;
	}
	
	
	
}
