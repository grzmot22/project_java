package Demos.NewAccountServer;

/** Demonstration of scope, needs Lec12Example1.java as orchestrating program
	Des, 9th December 2004
*/
public class Account 
{
	private int	balance;
	private int	creditLimit;
	private String accountName;

	public Account(String accN, int startAmount)	// Constructor method for an account
	{
		balance = startAmount;
		creditLimit = 0;
		accountName = accN;
	}
	public void setBalance(int amount)
	{
		balance = amount;
	}
	public int getBalance()
	{
		return balance;
	}
	public String getName()
	{
		return accountName;
	}
	public void setCreditLimit(int amount)
	{
		creditLimit = amount;
	}
	public int getCreditLimit()
	{
		return creditLimit;
	}
	public String toString()
	{
		return getName()+" "+getBalance()+" "+getCreditLimit();
	}
}
