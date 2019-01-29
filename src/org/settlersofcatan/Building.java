package org.settlersofcatan;

public class Building 
{
	int gridRow;
	int gridCol;
	Player player;
	
	public Building(int gr, int gc, Player player)
	{
		this.gridRow = gr;
		this.gridCol = gc;
		this.player = player;
	}
	
	public Building(int gr, int gc)
	{
		this.gridRow = gr;
		this.gridCol = gc;
	}
	
	public int getGridRow()
	{
		return gridRow;
	}

	public int getGridCol() 
	{
		return gridCol;
	}

	public void setGridCol(int gridCol) 
	{
		this.gridCol = gridCol;
	}

	public void setGridRow(int gridRow) 
	{
		this.gridRow = gridRow;
	}
	
	public String getOwner()
	{
		return player.getName();
	}
}
