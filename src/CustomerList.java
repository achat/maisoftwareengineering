import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerList {

	private static ArrayList<Customer> totalClients = new ArrayList<Customer>();
	
	
	
	public static ArrayList<Customer> getCustomersFromDB()
	{
		try {
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			Statement myStatement = myCon.createStatement();
			ResultSet myRes = myStatement.executeQuery("select * from clients");
			
			while (myRes.next())
			{
				if(totalClients==null)
					totalClients=new ArrayList<Customer>();
				
				totalClients.add(new Customer(myRes.getInt("id"),myRes.getString("firstname"),myRes.getString("lastname")));
			}
			myCon.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
		
		return totalClients;
	}
	
	public static void updateCustomerList()
	{
		totalClients=getCustomersFromDB();
	}



	public static ArrayList<Customer> getTotalClients() {
		
		if((totalClients==null)||(totalClients.size()==0))
			totalClients=getCustomersFromDB();
		
		return totalClients;
	}



	public static void setTotalClients(ArrayList<Customer> totalClients) {
		CustomerList.totalClients = totalClients;
	}
	
	
	
}
