package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class Player 
{
	private Color c;
	
	ArrayList<ResourceCard>resList;
	ArrayList<DevelopmentCard>devList = new ArrayList<DevelopmentCard>();
	
	ArrayList<Settlement>settleList = new ArrayList<Settlement>();
	ArrayList<Road>roadList = new ArrayList<Road>();
	ArrayList<City>cityList = new ArrayList<City>();
	
	int victoryPoints;
	int playerNumber;
	String playerName;
	
	public Player()
	{
		c = null;
		resList = null;
		devList = null;
		victoryPoints = 0;
		settleList = null;
		roadList = null;
		cityList = null;
		playerNumber = 0;
		playerName = "";
	}
	
	public Player(Color c, int vp, String name)
	{
		this.c = c;
		victoryPoints = vp;
		playerName = name;
		for(int i = 0;i<playerName.length();i++)
		{
			if(playerName.charAt(i) == ' ')
			{
				playerName.trim();
			}
		}
	}
	
	public Player(int playerNum, String name, ArrayList<ResourceCard> resList)
	{
		
		playerNumber = playerNum;
		playerName = name;
		this.resList = resList;
		for(int i = 0;i<playerName.length();i++)
		{
			if(playerName.charAt(i) == ' ')
			{
				playerName.trim();
			}
		}
	}
	
	public int[] listInventory()
	{
		ArrayList<ResourceCard>temp = resList;
		int brickCount = 0;
		int grainCount = 0;
		int oreCount = 0;
		int woodCount = 0;
		int woolCount = 0;
		
		for(int i = 0;i<temp.size();i++)
		{
			switch(temp.get(i).cardType)
			{
			case "brick":
				brickCount += 1;
				break;
			case "grain":
				grainCount += 1;
				break;
			case "ore":
				oreCount += 1;
				break;
			case "wood":
				woodCount += 1;
				break;
			case "wool":
				woolCount += 1;
				break;
			}
		}
		return new int[]{brickCount, grainCount, oreCount, woodCount, woolCount};
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
	
	
	// Add the Bank x, and EdgeLink[][] e as arguments soon
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
	
	public boolean buildSettlement(Bank x, VertexLink[][] grid)
	{
		Scanner sc = new Scanner(System.in);
		int w = this.getWood();
		int b = this.getBrick();
		int s = this.getWool();
		int g = this.getGrain();
		
		if(w >= 1 && b >= 1 && s >= 1 && g >= 1)
		{
			System.out.println("Which row do you want to build on?: ");
			int row = sc.nextInt();
			System.out.println("Which collumn do you want to build on?: ");
			int collumn = sc.nextInt();
			if(grid[row][collumn] != null && !grid[row + 1][collumn - 1].getHasBuilding()&& !grid[row + 1][collumn + 1].getHasBuilding())
			{
				ResourceCard.subtractWood(1, this, x);
				ResourceCard.subtractBrick(1, this, x);
				ResourceCard.subtractWool(1, this, x);
				ResourceCard.subtractGrain(1, this, x);
				
				Settlement temp = new Settlement(this);
				this.settleList.add(temp);
				grid[row][collumn].s = temp;
				grid[row][collumn].s.v = grid[row][collumn];
				victoryPoints += 1;
				return true;
			}
			else
			{
				System.out.println("You cannot build here.");
				return false;
			}
		}
		else
		{
			System.out.println("You lack the resources to build.");
			return false;
		}
	}
	
	public boolean upgradeSettlement(Bank x, VertexLink[][] grid)
	{
		Scanner sc = new Scanner(System.in);
		int o = this.getOre();
		int g = this.getGrain();
		
		if(o >= 3 && g >= 2)
		{
			System.out.println("Which row do you want to upgrade?: ");
			int row = sc.nextInt();
			System.out.println("Which collumn do you want to upgrade?: ");
			int collumn = sc.nextInt();
			if(grid[row][collumn].s != null)
			{
				ResourceCard.subtractOre(3, this, x);
				ResourceCard.subtractGrain(2, this, x);
				
				City temp = new City(this);
				this.cityList.add(temp);
				grid[row][collumn].c = temp;
				grid[row][collumn].c.v = grid[row][collumn];
				victoryPoints += 2;
				return true;
			}
			else
			{
				System.out.println("You cannot build here.");
				return false;
			}
		}
		else
		{
			System.out.println("You lack the resources to upgrade.");
			return false;
		}
	}
	
	public boolean buildDevelopmentCard(Bank x)
	{
		int ore = this.getOre();
		int wool = this.getWool();
		int grain = this.getGrain();
		
		Random r = new Random();
		int choice = r.nextInt(3);
		
		if(ore >= 1 && wool >= 1 && grain >= 1)
		{
			switch(choice)
			{
			case 0:
				this.devList.add(new DevelopmentCard("yearOfPlenty"));
				break;
			case 1:
				this.devList.add(new DevelopmentCard("monopoly"));
				break;
			case 2:
				this.devList.add(new DevelopmentCard("knight"));
				break;
			}
			return true;
		}
		else
		{
			System.out.println("You lack the resources to build a Development Card.");
			return false;
		}
	}
	
	public String getName() 
	{
		return this.playerName;
	}
	
	// Change return type to boolean when ready
	// Use Recursion
	// Each Road has 2-3 paths it can take at any given time
	/*
	 * if(temp.hasNextRoad() == false)
	 * {
	 * 
	 * 		return int;
	 * 
	 * }
	 *
	 */
	
	public static void hasLongestRoad()
	{
		
	}
	
}