
public class Customer extends User{
	
	private Portfolio portfolio;
	private double creditBalance;
	
	
	public Customer(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		
		// TODO Auto-generated constructor stub
	}
	
	public void updateCustomerPortfolio(int stockId, int stockAmount, double stockValue, boolean buyProc)
	{
		if(portfolio==null)
			portfolio = new Portfolio();
		
		PortfolioEntry pe = portfolio.checkIfEntryExists(id,stockId);
		System.out.println(pe.getId() + "--- ");
		if(pe.getId()==0)
		{
			pe = new PortfolioEntry(id,stockId,stockAmount,stockValue);
			pe.insertToDB();
		}
		else
		{
			if(buyProc)
				buy(pe, stockAmount, stockValue);
			else
				sell(pe, stockAmount, stockValue);
		}
	}
	
	public void buy(PortfolioEntry pe, int stockAmount, double stockValue)
	{
		pe.setAmount(pe.getAmount()+stockAmount);
		pe.setValue(pe.getValue()+stockValue);
		pe.udateExistingToDB();
	}
	
	public void sell(PortfolioEntry pe, int stockAmount, double stockValue)
	{
		pe.setAmount(pe.getAmount()-stockAmount);
		pe.setValue(pe.getValue()-stockValue);
		pe.udateExistingToDB();
	}
	
	
	public Portfolio getPortfolio() {
		if(portfolio==null)
			portfolio=new Portfolio();
		
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public double getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(double creditBalance) {
		this.creditBalance = creditBalance;
	}

	//εκτυπωση πληροφοριων Portfolio
	public void printPortfolioInfo()
	{
		
	}
	
	//εκτυπωση υπολοιπου 
	public void printCreditBalances()
	{	
		
	}
	
}
