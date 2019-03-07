package org.settlersofcatan;

public class City 
{
	Player p;
	VertexLink v;
	
	public City()
	{
		p = null;
		v = null;
	}
	
	public City(Player p)
	{
		this.p = p;
	}
	
	public City(Player i, VertexLink l)
	{
		p = i;
		v = l;
	}
}