package org.settlersofcatan;

public class Edge 
{
	int edgeID;
	int tileID;
	Player player;
	
	public Edge()
	{
		edgeID = 0;
		tileID = 0;
	}
	
	public Edge(int edgeN, int tileN)
	{
		edgeID = edgeN;
		tileID = tileN;
	}
	
	public Edge(Edge copy)
	{
		edgeID = copy.edgeID;
		tileID = copy.tileID;
	}
}