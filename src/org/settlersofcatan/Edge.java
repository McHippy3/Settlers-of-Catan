package org.settlersofcatan;

public class Edge {
	int gridRow, gridCol;
	int mainGridRowStart, mainGridColStart;
	int mainGridRowEnd, mainGridColEnd;

	public Edge(int row, int col) 
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
	
	public int getGridRow() 
	{
		return gridRow;
	}
	
	public int getGridCol() 
	{
		return gridCol;
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
