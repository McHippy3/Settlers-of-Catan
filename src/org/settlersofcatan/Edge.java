package org.settlersofcatan;

import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class Edge extends Button{
	int gridRow, gridCol;
	int mainGridRowStart, mainGridColStart;
	int mainGridRowEnd, mainGridColEnd;
	boolean hasRoad, exists;
	int gridX;
	int gridY;
	int startGridRow;
	int endGridRow;
	int startGridCollum;
	int endGridCollum;
	int tileID, edgeID;
	Road r;
	
	public Edge()
	{
		gridX = 0;
		gridY = 0;
		r = null;
	}
	
	public Edge(int a, int b, int c, int d)
	{
		gridX = a;
		gridY = b;
	}
	
	public Edge(int row, int col, boolean exists) 
	{
		this.exists = exists;
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
		if(r != null)
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
		return hasRoad;
	}

	public void setHasRoad(boolean hasRoad) 
	{
		this.hasRoad = hasRoad;
	}
	
	
	public boolean getExists() 
	{
		return exists;
	}

	public void setExists(boolean exists) 
	{
		this.exists = exists;
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
}
