package org.settlersofcatan;

import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.image.ImageView;

import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

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
