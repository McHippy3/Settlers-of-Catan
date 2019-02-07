package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.image.ImageView;

import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class Tile 
{
	/*
	 * 
	 * Tile Vertex Convention:
	 * The highest vertice in the Y direction is the "0th" index
	 * Going Clockwise from there, increases the index number
	 * 
	 */
	
	String type;
	ImageView img;
	int posX;
	int posY;
	int locX;
	int locY;
	int number;
	int tileID;
	boolean hasRobber;
	Vertex[] vertexArray = new Vertex[6];
	
	public Tile()
	{
		img = null;
		posX = 0;
		posY = 0;
		locX = 0;
		locY = 0;
		hasRobber = false;
		
		// Number
		Random r = new Random();
		number = r.nextInt(11) + 2;
		
		// 3 Bricks, 3 Stone, 4 Wood, 4 Wheat, 4 Sheep, 1 Desert
		
		// Type
		Random r1 = new Random();
		number = r1.nextInt(4);
		Math.round(number);
		switch(number)
		{
		case 0:
			type = "sheep";
			break;
		case 1:
			type = "brick";
			break;
		case 2:
			type = "wheat";
			break;
		case 3:
			type = "wood";
			break;
		case 4:
			type = "stone";
			break;
		}
	}
	
	public Tile(String t, int n)
	{
		type = t;
		number = n;
	}
	
	public Tile(int n)
	{
		tileID = n;
	}
	
	public boolean isDesert()
	{
		if(type == "desert")
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasLeft(Line l)
	{
		if(tileID-1 < l.tiles[0].tileID)
		{	
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean hasRight(Line l)
	{
		if(tileID+1 <= l.tiles[l.tiles.length-1].tileID)
		{	
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasAbove(Line a, Line b, int i)
	{
		if(b.tiles[i].hasAboveLeft(a, b, i) == true || b.tiles[i].hasAboveRight(a, b, i))
		{
			return true;
		}
		return false;
	}
	
	public boolean hasAboveRight(Line a, Line b, int i)
	{
		if(b.tiles.length > a.tiles.length)
		{
			if(i < a.tiles.length)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		if(a.tiles.length > b.tiles.length)
		{
			return true;
		}
		return false;
	}
	
	public boolean hasAboveLeft(Line a, Line b, int i)
	{
		if(b.tiles.length > a.tiles.length)
		{
			if(i-1 >= 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		if(a.tiles.length > b.tiles.length)
		{
			return true;
		}
		return false;
	}
	
	public boolean hasBelowLeft(Line a, Line b, int i)
	{
		if(a.tiles.length > b.tiles.length)
		{
			if(i == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return true;
	}
	
	public boolean hasBelowRight(Line a, Line b, int i)
	{
		if(a.tiles.length > b.tiles.length)
		{
			if(i == a.tiles.length-1)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return true;
	}
	
	public boolean hasBelow(Line a, Line b)
	{
		if(a.tiles.length < b.tiles.length)
		{
			if(b.tiles[tileID+b.tiles.length] != null && b.tiles[tileID+b.tiles.length-1] != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		if(a.tiles.length > b.tiles.length)
		{
			if(b.tiles[tileID+a.tiles.length] != null || b.tiles[tileID+a.tiles.length+1] != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}
	
	public Tile getBelowLeft(Line a, Line b, int i)
	{
		if(b.tiles.length > a.tiles.length)
		{
			return b.tiles[i];
		}
		else
		{
			return b.tiles[i-1];
		}
	}
	
	public Tile getBelowRight(Line a, Line b, int i)
	{
		if(b.tiles.length > a.tiles.length)
		{
			return b.tiles[i+1];
		}
		else
		{
			return b.tiles[i];
		}
	}
	
	public Tile getAbove(Line a, Line b)
	{
		return a.tiles[tileID-a.tiles.length];
	} 
	
	public Tile getAboveRight(Line a, Line b, int i)
	{
		return a.tiles[i];
	}
	
	public Tile getAboveLeft(Line a, Line b, int i)
	{
		if(a.tiles.length > b.tiles.length)
		{
			return a.tiles[i];
		}
		else
		{
			return a.tiles[i-1];
		}
	}
	
	public Tile getRight(Line c, int i)
	{
		return c.tiles[i+1];
	}
	
	public Tile getLeft(Line c, int i)
	{
		return c.tiles[i-1];
	}
}
