package org.settlersofcatan;

public class Settlement 
{
	Player p;
	VertexLink v;
	
	public Settlement(Player p)
	{
		this.p = p;
	}
	
	public Settlement(Player i, VertexLink l)
	{
		p = i;
		v = l;
	}
}