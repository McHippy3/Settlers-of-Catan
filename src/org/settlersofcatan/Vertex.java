package org.settlersofcatan;

public class Vertex {
	private int gridCol, gridRow;
	
	public Vertex(int row, int col) 
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
