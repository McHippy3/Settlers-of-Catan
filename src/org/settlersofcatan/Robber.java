package org.settlersofcatan;

public class Robber 
{
	static Tile t;
	
	public Robber()
	{
		t = null;
	}
	
	public Robber(Tile x)
	{
		t = x;
	}
	
	public void stealResources()
	{
		
	}
	
	public static void moveRobber(Tile x)
	{
		t = x;
	}
}