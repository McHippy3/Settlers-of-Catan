package org.settlersofcatan;

import javafx.scene.control.Button;

// VertexLink is used whenever a Vertex is shared between two tiles, also used as a Vertex for whenever a Vertex ISN'T shared between 2 tiles

public class VertexLink extends Button
{
	EdgeLink[] adjacentEdges = new EdgeLink[3];
	int gridRow, gridCol, harborCode;
	Settlement settlement;
	City city;
	
	public VertexLink(int row, int col)
	{
		gridRow = row;
		gridCol = col;
		harborCode = 0;
	}
	
	public int getGridRow() 
	{
		return gridRow;
	}
	
	public int getGridCol() 
	{
		return gridCol;
	}
	
	//Returns 0 for no building, 1 for a settlement and 2 for a city
	public int getHasBuilding() 
	{
		int count = 0;
		if(settlement != null)
			count++;
		if(city != null)
			count++;
		return count;
	}

	public String toString(Vertex v)
	{
		return v.tileID+", "+v.vertexID;
	}
}