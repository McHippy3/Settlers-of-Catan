package org.settlersofcatan;

import javafx.scene.control.Button;

public class Vertex extends Button
{
	private int gridCol, gridRow;
	private boolean hasBuilding, exists;
	private Building building; 
	
	public Vertex(int row, int col, boolean exists) 
	{
		super("");
		this.exists = exists;
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

	public boolean getExists() 
	{
		return exists;
	}

	public void setExists(boolean exists) 
	{
		this.exists = exists;
	}

	public Building getBuilding() 
	{
		return building;
	}

	public void setBuilding(Building building) 
	{
		this.building = building;
	}
	
}
