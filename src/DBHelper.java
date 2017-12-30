import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {

	public DBHelper()
	{
		
	}
	
	public void SelectAllClients()
	{
		try {
			Connection myCon =DriverManager.getConnection(Constants.dbConnectionString,Constants.dbUsername, Constants.dbPassword);
			Statement myStatement = myCon.createStatement();
			ResultSet myRes = myStatement.executeQuery("select * from clients");
			
			while (myRes.next())
			{
				System.out.println(myRes.getString("firstname"));
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
}
