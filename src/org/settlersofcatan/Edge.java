package org.settlersofcatan;

public class Edge {
	int gridRow, gridCol;

	
	public Edge(int row, int col) 
	{
		gridRow = row;		
		gridCol = col;
	}
	
	public int getGridRow() 
	{
		return gridRow;
	}
	
	public int getGridCol() 
	{
		return gridCol;
	}
}
