import java.util.ArrayList;

public class Broker extends User{
	
	private String companyName;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	
	
	public Broker(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

	
	//εκτύπωση customers
	public void printCustomerList()
	{
		for(Customer customer: customers)
			System.out.println(customer.firstName + " " + customer.lastName );
	}

	//πληθος customers
	public int getTotalNumberOfCustomers()
	{
		return customers.size();
	}
	
	//Συνολικη αξία Portfolio
	public double getTotalPortfolioValue()
	{
		return 0;
		
	}
	
	//αγορα Stock
	public void buyStock()
	{
		
	}
	
	//πωληση Stock
	public void sellStock(){
		
		
	}

}
