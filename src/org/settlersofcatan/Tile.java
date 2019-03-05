package org.settlersofcatan;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Tile extends Button
{
	/*
	 * 
	 * Tile Vertex Convention:
	 * The highest vertex in the Y direction is the "0th" index
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
	VertexLink[] vertexArray;
	
	public Tile(String type, int number)
	{
		img = null;
		posX = 0;
		posY = 0;
		locX = 0;
		locY = 0;
		hasRobber = false;
		
		this.type = type;
		this.number = number;
		
	}
	
	public Tile(int n)
	{
		tileID = n;
	}
	
	public void setNumber(int n) 
	{
		this.number = n;
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
	
	public boolean hasLeft(TileRow l)
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
	
	public boolean hasRight(TileRow l)
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
	
	public boolean hasAbove(TileRow a, TileRow b, int i)
	{
		if(b.tiles[i].hasAboveLeft(a, b, i) == true || b.tiles[i].hasAboveRight(a, b, i))
		{
			return true;
		}
		return false;
	}
	
	public boolean hasAboveRight(TileRow a, TileRow b, int i)
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
	
	public boolean hasAboveLeft(TileRow a, TileRow b, int i)
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
	
	public boolean hasBelowLeft(TileRow a, TileRow b, int i)
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
	
	public boolean hasBelowRight(TileRow a, TileRow b, int i)
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
	
	public boolean hasBelow(TileRow a, TileRow b)
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
	
	public Tile getBelowLeft(TileRow a, TileRow b, int i)
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
	
	public Tile getBelowRight(TileRow a, TileRow b, int i)
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
	
	public Tile getAbove(TileRow a, TileRow b)
	{
		return a.tiles[tileID-a.tiles.length];
	} 
	
	public Tile getAboveRight(TileRow a, TileRow b, int i)
	{
		return a.tiles[i];
	}
	
	public Tile getAboveLeft(TileRow a, TileRow b, int i)
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
	
	public Tile getRight(TileRow c, int i)
	{
		return c.tiles[i+1];
	}
	
	public Tile getLeft(TileRow c, int i)
	{
		return c.tiles[i-1];
	}
}
