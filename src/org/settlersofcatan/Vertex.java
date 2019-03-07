package org.settlersofcatan;

import javafx.scene.control.Button;
<<<<<<< HEAD

=======
>>>>>>> 3762622fd9e7c990f52641c305fc5aa8bdd2fa17

public class Vertex extends Button
{
	private int gridCol, gridRow;
	private boolean hasBuilding;
	private Building building; 
	int vertexID;
	int tileID;
	
	public Vertex()
	{
		vertexID = 0;
		tileID = 0;
	}
	
	//TODO: get rid of boolean b
	public Vertex(int vertexID, int tileID, boolean b)
	{
		this.vertexID = vertexID;
		this.tileID = tileID;
	}
	
	public Vertex(Vertex v)
	{
		vertexID = v.vertexID;
		tileID = v.tileID;
	}
	
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

	public boolean getHasBuilding() 
	{
		return hasBuilding;
	}

	public void setHasBuilding(boolean hasBuilding) 
	{
		this.hasBuilding = hasBuilding;
	}

	public Building getBuilding() 
	{
		return building;
	}

	public void setBuilding(Building building) 
	{
		this.building = building;
	}
	
	public String toString()
	{
		return tileID+", "+tileID;
	}
	
}
