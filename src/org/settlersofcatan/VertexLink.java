package org.settlersofcatan;

import javafx.scene.control.Button;

// VertexLink is used whenever a Vertex is shared between two tiles, also used as a Vertex for whenever a Vertex ISN'T shared between 2 tiles

public class VertexLink extends Button
{
	Vertex[] v;
	EdgeLink[] adjacentEdges = new EdgeLink[3];
	int gridRow, gridCol;
	Settlement settlement;
	City city;
	
	public VertexLink()
	{
		v = null;
	}
	
	public VertexLink(Vertex[] v1)
	{
		v = v1;
	}
	
	public VertexLink(int row, int col)
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
	
	public Settlement getSettlement() 
	{
		return settlement;
	}

	public String toString(Vertex v)
	{
		return v.tileID+", "+v.vertexID;
	}
}