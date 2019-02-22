package org.settlersofcatan;

public class TileRow
{
	Tile[] tiles;
	
	public TileRow()
	{
		tiles = null;
	}
	
	public TileRow(Tile[] t)
	{
		tiles = t;
	}
	
	public TileRow(TileRow tileRow)
	{
		this.tiles = tileRow.tiles;
	}
}