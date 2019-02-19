package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;

import javafx.scene.control.Button;

// VertexLink is used whenever a Vertex is shared between two tiles, also used as a Vertex for whenever a Vertex ISN'T shared between 2 tiles

public class VertexLink extends Button
{
	Vertex[] v;
	int gridRow, gridCol;
	Settlement s;
	City c;
	boolean hadBuilding = false;
	
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
	
	public boolean getHasBuilding() 
	{
		if(s != null || c != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void setHasBuilding(int playerNum) 
	{
		s = new Settlement(null, this);
	}

	public String toString(Vertex v)
	{
		return v.tileID+", "+v.vertexID;
	}
	
	/*public boolean hasCluster()
	{
		if(v[0].vertexID == 2 && v[1].vertexID == 4 && v[2].vertexID == 0)
		{
			return true;
		}
		
		if()
		{
			
		}
	}*/
}