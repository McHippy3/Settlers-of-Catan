package org.settlersofcatan;

public class Edge {
	int gridRow, gridCol;
	int mainGridRow, mainGridCol;

	
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

	public int getMainGridRow() {
		return mainGridRow;
	}

	public void setMainGridRow(int mainGridRow) 
	{
		this.mainGridRow = mainGridRow;
	}

	public int getMainGridCol() 
	{
		return mainGridCol;
	}

	public void setMainGridCol(int mainGridCol) 
	{
		this.mainGridCol = mainGridCol;
	}
}
