import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Holds all the customers that are stored in the DB*/
public class CustomerList {

	
	private static ArrayList<Customer> totalClients = new ArrayList<Customer>();
	
	/**
	 * Gets the customers from the list and stores them to the local totalClients list
	 * @param
	 * @return ArrayList(Customer)*/
	public static ArrayList<Customer> getCustomersFromDB()
	{
		try {
			//Initialize connection to the DB
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			//Create and execute the sql query
			Statement myStatement = myCon.createStatement();
			ResultSet myRes = myStatement.executeQuery("select * from clients");
			
			//While we have more results create customers with their info and add them to the list
			while (myRes.next())
			{
				if(totalClients==null)
					totalClients=new ArrayList<Customer>();
				
				totalClients.add(new Customer(myRes.getInt("id"),myRes.getString("firstname"),myRes.getString("lastname")));
			}
			//Remember to close connection with the DB
			myCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return totalClients;
	}
	
	/**
	 * Refresh customer list getting then from DB again
	 */
	public static void updateCustomerList()
	{
		totalClients=getCustomersFromDB();
	}

	/**
	 * Check if the local client list has elements if not get them from the DB and return local client list
	 * @return ArrayList(Customer)*/
	public static ArrayList<Customer> getTotalClients() {
		
		if((totalClients==null)||(totalClients.size()==0))
			totalClients=getCustomersFromDB();
		
		return totalClients;
	}

	/**
	 * Set total client list
	 * @param ArrayList<Customer> total clients*/
	public static void setTotalClients(ArrayList<Customer> totalClients) {
		CustomerList.totalClients = totalClients;
	}
	
	
	
}
