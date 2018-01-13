/**
 * The entity who actually gives the command to the broker to buy or sell stocks
 * Child class of User
 * **/
public class Customer extends User{
	//Attribute Declaration
	private Portfolio portfolio;
	private double creditBalance;
	
	/**
	 * Constructor
	 * @param int id
	 * @param String firstName
	 * @param String lastName
	 * */
	public Customer(int id, String firstName, String lastName) {
		super(id, firstName, lastName);		
	}
	
	/** Updates customer portfolio to the DB
	 * If the user has already any amount of the current stock then update it else insert new entry to the DB
	 * @param int stockid
	 * @param int stockAmount
	 * @param double stockValue
	 * @param boolean buyProc (if buy command then true, if sell command then false)
	 * */
	public void updateCustomerPortfolio(int stockId, int stockAmount, double stockValue, boolean buyProc)
	{
		//Check if there is portfolio else create one
		if(portfolio==null)
			portfolio = new Portfolio();
		
		//Check if there is an entry for the current customer with the same stock already in the portfolio table
		PortfolioEntry pe = portfolio.checkIfEntryExists(id,stockId);
		
		//If the id we got back id zero then there is no entry on the table and the pe is actually an empty object
		//We need to add a new entry to the DB with this customer and this stock
		//This can only happen on buy mode since a customer can not sell a stock that does not have it is prevented on UI level
		if(pe.getId()==0)
		{
			pe = new PortfolioEntry(id,stockId,stockAmount,stockValue);
			pe.insertToDB();
		}
		else
		{
			//If the porftolio entry does exists we must call the buy or sell method according to the command
			//And update the existing entry
			if(buyProc)
				buy(pe, stockAmount, stockValue);
			else
				sell(pe, stockAmount, stockValue);
		}
	}
	
	/** Adds the amount and value of the stock in the existing portfolio entry and updated the portfolio on the DB
	 * @param PortfolioEntry pe
	 * @param int stockAmount
	 * @param double stockValue
	 * */
	public void buy(PortfolioEntry pe, int stockAmount, double stockValue)
	{
		pe.setAmount(pe.getAmount()+stockAmount);
		pe.setValue(pe.getValue()+stockValue);
		pe.udateExistingToDB();
	}
	
	/** Subtracts the amount and value of the stock in the existing portfolio entry and updated the portfolio on the DB
	 * @param PortfolioEntry pe
	 * @param int stockAmount
	 * @param double stockValue
	 * */
	public void sell(PortfolioEntry pe, int stockAmount, double stockValue)
	{
		pe.setAmount(pe.getAmount()-stockAmount);
		pe.setValue(pe.getValue()-stockValue);
		pe.udateExistingToDB();
	}
	
	/**
	 * Initialize the portfolio if needed and return it
	 * @return portfolio
	 * */
	public Portfolio getPortfolio() {
		if(portfolio==null)
			portfolio=new Portfolio();
		
		return portfolio;
	}

	/** Set portfolio
	 * @param Portfolio portfolio
	 * @return void
	 * */
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * Get credit balance
	 * @return double*/
	public double getCreditBalance() {
		return creditBalance;
	}

	/**
	 * Set credit balance
	 * @param double creditBalance
	 * @return void*/
	public void setCreditBalance(double creditBalance) {
		this.creditBalance = creditBalance;
	}	
}
