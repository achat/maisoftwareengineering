
public class Customer extends User{
	
	private Portfolio portfolio;
	private double creditBalance;
	
	
	public Customer(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		// TODO Auto-generated constructor stub
	}
	
	public void updateCustomerPortfolio(int stockId, int stockAmount, double stockValue)
	{
		if(portfolio==null)
			portfolio = new Portfolio();
		
		PortfolioEntry pe = portfolio.checkIfEntryExists(id,stockId);
		
		if(pe==null)
		{
			pe = new PortfolioEntry(id,stockId,stockAmount,stockValue);
			pe.insertToDB();
		}
		else
		{
			pe.setAmount(pe.getAmount()+stockAmount);
			pe.setValue(pe.getValue()+stockValue);
			pe.udateExistingToDB();
		}
	}
	
	//�������� ����������� Portfolio
	public void printPortfolioInfo()
	{
		
	}
	
	//�������� ��������� 
	public void printCreditBalances()
	{	
		
	}
	
}
