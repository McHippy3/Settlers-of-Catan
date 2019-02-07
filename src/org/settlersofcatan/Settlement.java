package org.settlersofcatan;

public class Settlement extends Building
{	
	Player p;
	public Settlement(int gridrow, int gridcol, Player player)
	{
		super(gridrow, gridcol, player);
		p = null;
	}	
	
	public Settlement(Player x)
	{
		super(x);
	}
	
}
