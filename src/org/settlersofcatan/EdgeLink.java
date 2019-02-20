package org.settlersofcatan;

import javafx.scene.control.Button;

public class EdgeLink extends Button
{
	private int gridRow, gridCol;
	private int mainGridRowStart, mainGridColStart;
	private int mainGridRowEnd, mainGridColEnd;
	private boolean hasRoad;
	private int gridX;
	private int gridY;
	private int startGridRow;
	private int endGridRow;
	private int startGridCollum;
	private int endGridCollum;
	private int tileID, edgeID;
	Road road;
	Edge[] e;
	
	public EdgeLink()
	{
		e =null;
	}
	
	public EdgeLink(Edge[] e1)
	{
		e = e1;
	}
	
	public EdgeLink(int row, int col) 
	{
		gridRow = row;		
		gridCol = col;	
		
		//Even Rows
		if(gridRow % 2 == 0) 
		{
			if(gridRow % 4 == 0)
			{
				//Slant Upwards
				if(gridCol % 2 == 1) 
				{
					mainGridRowStart = gridRow + 1;
					mainGridRowEnd = gridRow;
					mainGridColStart = gridCol - 1;
					mainGridColEnd = gridCol;
				}
				//Slant Downwards
				else if (gridCol % 2 == 0) 
				{
					mainGridRowStart = gridRow;
					mainGridRowEnd = gridRow + 1;
					mainGridColStart = gridCol - 1;
					mainGridColEnd = gridCol;
				}
			}
			else if(gridRow % 4 == 2)
			{
				//Slant Upwards
				if (gridCol % 2 == 0)
				{
					mainGridRowStart = gridRow + 1;
					mainGridRowEnd = gridRow;
					mainGridColStart = gridCol - 1;
					mainGridColEnd = gridCol;
				}
				//Slant Downwards
				else if (gridCol % 2 ==1)
				{
					mainGridRowStart = gridRow;
					mainGridRowEnd = gridRow + 1;
					mainGridColStart = gridCol - 1;
					mainGridColEnd = gridCol;
				}
			}
		}
		//Odd Rows
		else 
		{
			mainGridRowStart = gridRow;
			mainGridRowEnd = gridRow + 1;
			mainGridColStart = gridCol;
			mainGridColEnd = gridCol;
		}
	}
	
	public boolean hasSettlement()
	{
		if(road != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int getGridRow() 
	{
		return gridRow;
	}
	
	public int getGridCol() 
	{
		return gridCol;
	}
	
	public boolean getHasRoad() 
	{
		if(this.road != null)
			return true;
		else return false;
	}

	public int getMainGridRowStart() {
		return mainGridRowStart;
	}


	public int getMainGridColStart() 
	{
		return mainGridColStart;
	}
	
	public int getMainGridRowEnd() 
	{
		return mainGridRowEnd;
	}

	public int getMainGridColEnd() 
	{
		return mainGridColEnd;
	}
	
	public String toString(Edge e)
	{
		//return e.tileID+", "+e.edgeID;
		return "";
	}
	
	
}
