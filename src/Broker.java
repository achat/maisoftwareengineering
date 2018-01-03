import java.util.ArrayList;

public class Broker extends User{
	
	private String companyName;
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	
	
	public Broker(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		// TODO Auto-generated constructor stub
	}

	
	//�������� customers
	public void printCustomerList()
	{
		for(Customer customer: customers)
			System.out.println(customer.firstName + " " + customer.lastName );
	}

	//������ customers
	public int getTotalNumberOfCustomers()
	{
		return customers.size();
	}
	
	//�������� ���� Portfolio
	public double getTotalPortfolioValue()
	{
		return 0;
		
	}
	
	//����� Stock
	public void buyStock()
	{
		
	}
	
	//������ Stock
	public void sellStock(){
		
		
	}

}
