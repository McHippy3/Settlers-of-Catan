package org.settlersofcatan;

public class EdgeLink 
{
	Edge[] e;
	
	public EdgeLink()
	{
		e=null;
	}
	
	public EdgeLink(Edge[] e1)
	{
		e = e1;
	}
	
	public String toString(Edge e)
	{
		return e.tileID+", "+e.edgeID;
	}
	

}
