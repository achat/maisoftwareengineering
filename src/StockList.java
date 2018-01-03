import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StockList {

	private static ArrayList<Stock> totalStocks = new ArrayList<Stock>();
	
	
	
	public static ArrayList<Stock> getStocksFromDB()
	{
		try {
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			Statement myStatement = myCon.createStatement();
			ResultSet myRes = myStatement.executeQuery("select * from stocks");
			
			while (myRes.next())
			{
				if(totalStocks==null)
					totalStocks=new ArrayList<Stock>();
				
				totalStocks.add(new Stock(myRes.getInt("id"),myRes.getString("name"),myRes.getDouble("value"),myRes.getInt("totalAmount"),myRes.getInt("boughtAmount"),myRes.getInt("availableAmount")));
			}
			myCon.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
		
		return totalStocks;
	}



	public static ArrayList<Stock> getTotalStocks() {
		
		if((totalStocks==null)||(totalStocks.size()==0))
			totalStocks=getStocksFromDB();
		
		return totalStocks;
	}



	public static void setTotalStocks(ArrayList<Stock> totalStocks) {
		StockList.totalStocks = totalStocks;
	}
	
	
	
}
