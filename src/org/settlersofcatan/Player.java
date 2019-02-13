package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class Player 
{
	private Color color;
	ArrayList<ResourceCard>resList;
	ArrayList<DevelopementCard>devList;
	int victoryPoints;
	ArrayList<Settlement>settleList;
	ArrayList<Road>roadList;
	ArrayList<City>cityList;
	ArrayList<Player>playerList;
	int playerNumber;
	String playerName;
	
	public Player()
	{
		color = null;
		resList = null;
		devList = null;
		victoryPoints = 0;
		settleList = null;
		roadList = null;
		cityList = null;
		playerList = null;
		playerNumber = 0;
		playerName = "";
	}
	
	public Player(Color color, int victoryPoints, String playerName, ArrayList<ResourceCard> resourceList)
	{
		this.color = color;
		this.victoryPoints = victoryPoints;
		this.playerName = playerName;
		for(int i = 0;i<playerName.length();i++)
		{
			if(this.playerName.charAt(i) == ' ')
			{
				this.playerName.trim();
			}
		}
		this.resList = resourceList;
	}
	
	public void listInventory()
	{
		ArrayList<ResourceCard>temp = resList;
		int sheepCount = 0;
		int brickCount = 0;
		int wheatCount = 0;
		int woodCount = 0;
		int stoneCount = 0;
		
		for(int i = 0;i<temp.size();i++)
		{
			switch(temp.get(i).cardType)
			{
			case "wool":
				sheepCount += 1;
				break;
			case "brick":
				brickCount += 1;
				break;
			case "grain":
				wheatCount += 1;
				break;
			case "wood":
				woodCount += 1;
				break;
			case "ore":
				stoneCount += 1;
				break;
			}
		}
		System.out.println(playerName+"'s inventory:");
		System.out.println(sheepCount+" "+"sheep");
		System.out.println(brickCount+" "+"brick");
		System.out.println(wheatCount+" "+"wheat");
		System.out.println(woodCount+" "+"wood");
		System.out.println(stoneCount+" "+"stone");
		
	}
	
	public int[] countInventory()
	{
		int[] resources = new int[5];
		int sheepCount = 0;
		int brickCount = 0;
		int wheatCount = 0;
		int woodCount = 0;
		int stoneCount = 0;
		
		for(int i = 0;i<resList.size();i++)
		{
			switch(resList.get(i).cardType)
			{
			case "wool":
				sheepCount += 1;
				break;
			case "brick":
				brickCount += 1;
				break;
			case "grain":
				wheatCount += 1;
				break;
			case "wood":
				woodCount += 1;
				break;
			case "ore":
				stoneCount += 1;
				break;
			}
		}
		
		resources[0] = sheepCount;
		resources[1] = brickCount;
		resources[2] = wheatCount;
		resources[3] = woodCount;
		resources[4] = stoneCount;
		
		return resources;
	}
	
	public int getVP()
	{
		return victoryPoints;
	}
	
	public  int getBrick()
	{
		int r = 0;
		for(int i = 0;i<this.resList.size();i++)
		{
			if(this.resList.get(i).cardType.equalsIgnoreCase("brick"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public int getWood()
	{
		int r = 0;
		for(int i = 0;i<this.resList.size();i++)
		{
			if(this.resList.get(i).cardType.equalsIgnoreCase("wood"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public int getWool()
	{
		int r = 0;
		for(int i = 0;i<this.resList.size();i++)
		{
			if(this.resList.get(i).cardType.equalsIgnoreCase("wool"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public int getGrain()
	{
		int r = 0;
		for(int i = 0;i<this.resList.size();i++)
		{
			if(this.resList.get(i).cardType.equalsIgnoreCase("grain"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public int getOre()
	{
		int r = 0;
		for(int i = 0;i<this.resList.size();i++)
		{
			if(this.resList.get(i).cardType.equalsIgnoreCase("ore"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public boolean buildRoad()
	{
		int w = this.getWood();
		int b = this.getBrick();
		
		if(w >= 1 && b >= 1)
		{
			Road r = new Road(this);
			this.roadList.add(r);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean buildSettlement()
	{
		int w = this.getWood();
		int b = this.getBrick();
		int s = this.getWool();
		int g = this.getGrain();
		
		if(w >= 1 && b >= 1 && s >= 1 && g >= 1)
		{
			Settlement r = new Settlement(this);
			this.settleList.add(r);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Change return type to boolean when ready
	// Use Recursion
	// Each Road has 2-3 paths it can take at any given time
	
	public void hasLongestRoad()
	{
		
	}
	
	public String getName() 
	{
		return playerName;
	}
}
