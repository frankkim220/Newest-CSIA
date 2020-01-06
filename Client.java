public class Client 
{
	private String myName;
	private int myA;
	private int myB;
	private int myC;
	
	public Client(String name, int cfeA, int cfeB, int cfeC)
	{
		myName = name;
		myA = cfeA;
		myB = cfeB;
		myC = cfeC;
	}
	
	public String getName()
	{
		return myName;
	}
	
	public int getA()
	{
		return myA;
	}
	
	public int getB()
	{
		return myB;
	}
	
	public int getC()
	{
		return myC;
	}
	
	public void setName(String newName)
	{
		myName = newName;
	}
	
	public void setA(int newA)
	{
		myA = newA;
	}
	
	public void setB(int newB)
	{
		myB = newB;
	}
	
	public void setC(int newC)
	{
		myC = newC;
	}
	
	public String toString()
	{
		return myName;
	}
	
	public String[] toArray()
	{
		String[] arr = new String[4];
		arr[0] = this.getName();
		arr[1] = String.valueOf(this.getA());
		arr[2] = String.valueOf(this.getB());
		arr[3] = String.valueOf(this.getC());
		return arr;
	}
}
