public class CoffeeBean 
{
	private int myId;
	private double myPrice;
	private double myCost;
	private double myWeight;
	
	public CoffeeBean(int id, double price, double cost, double weight)
	{
		myId = id;
		myPrice = price;
		myCost = cost;
		myWeight = weight;
	}
	
	public CoffeeBean(int id)
	{
		myId = id;
		myPrice = 0;
		myCost = 0;
		myWeight = 0;
	}
	
	public int getId()
	{
		return myId;
	}
	
	public double getPrice()
	{
		return myPrice;
	}
	
	public double getCost()
	{
		return myCost;
	}
	
	public double getWeight()
	{
		return myWeight;
	}
	
	public void setPrice(double newPrice)
	{
		myPrice = newPrice;
	}
	
	public void setCost(double newCost)
	{
		myCost = newCost;
	}
	
	public void setWeight(double newWeight)
	{
		myWeight = newWeight;
	}
	
	public String toString()
	{
		if (myId == 0)
		{
			return "Coffee A";
		}
		else if (myId == 1)
		{
			return "Coffee B";
		}
		else
		{
			return "Coffee C";
		}
	}
}
