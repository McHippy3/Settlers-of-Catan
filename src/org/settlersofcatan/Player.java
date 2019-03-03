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
	public boolean buildRoad(Bank bank, EdgeLink[][] grid, int row, int col, boolean inSetup)
	{
		int w = this.getWood();
		int b = this.getBrick();
		
		if(w >= 1 && b >= 1)
		{
			if(!inSetup) 
			{
				ResourceCard.subtractWood(1, this, bank);
				ResourceCard.subtractBrick(1, this, bank);
			}
			
			Road r = new Road(this);
			this.roadList.add(r);
			grid[row][col].road = new Road(this);
			return true;
		}
		else
		{
			System.out.println("Cannot build due to lack of resources");
			return false;
		}
	}
	
	public boolean buildSettlement(Bank bank, VertexLink[][] grid, int row, int col, boolean inSetup)
	{
		int w = this.getWood();
		int b = this.getBrick();
		int s = this.getWool();
		int g = this.getGrain();
		
		if(w >= 1 && b >= 1 && s >= 1 && g >= 1)
		{
			if(!inSetup) 
			{
				ResourceCard.subtractWood(1, this, bank);
				ResourceCard.subtractBrick(1, this, bank);
				ResourceCard.subtractWool(1, this, bank);
				ResourceCard.subtractGrain(1, this, bank);
			}
			
			Settlement temp = new Settlement(this, grid[row][col]);
			this.settleList.add(temp);
			grid[row][col].settlement = temp;
			victoryPoints += 1;
			return true;
		}
		else
		{
			System.out.println("Cannot build due to lack of resources");
			return false;
		}
	}
	
	public boolean upgradeSettlement(Bank bank, VertexLink[][] grid, int row, int col)
	{
		int o = this.getOre();
		int g = this.getGrain();
		
		if(o >= 3 && g >= 2)
		{
				ResourceCard.subtractOre(3, this, bank);
				ResourceCard.subtractGrain(2, this, bank);
				
				City temp = new City(this, grid[row][col]);
				this.cityList.add(temp);
				grid[row][col].city = temp;
				victoryPoints += 1;
				return true;
		}
		else
		{
			System.out.println("Cannot build due to lack of resources");
			return false;
		}
	}
	
	public boolean buildDevelopmentCard(Bank bank)
	{
		int ore = this.getOre();
		int wool = this.getWool();
		int grain = this.getGrain();
		
		Random r = new Random();
		int choice = r.nextInt(25);
		
		if(ore >= 1 && wool >= 1 && grain >= 1)
		{
			ResourceCard.subtractOre(1, this, bank);
			ResourceCard.subtractWool(1, this, bank);
			ResourceCard.subtractGrain(1, this, bank);
			
			if(choice<14)
			{
				this.devList.add(new DevelopmentCard("knight", "res/dev_cards/knight.png"));
			}
			else if(choice>=14&&choice<16)
			{
				this.devList.add(new DevelopmentCard("road building", "res/dev_cards/roadbuild.png"));
			}
			else if(choice>=16&&choice<18)
			{
				this.devList.add(new DevelopmentCard("year of plenty", "res/dev_cards/yop.png"));
			}
			else if(choice>=18&&choice<20)
			{
				this.devList.add(new DevelopmentCard("monopoly", "res/dev_cards/monopoly.png"));
			}
			else if(choice==20)
			{
				this.devList.add(new DevelopmentCard("chapel", "res/dev_cards/chapel.png"));
			}
			else if(choice==21)
			{
				this.devList.add(new DevelopmentCard("library", "res/dev_cards/library.png"));
			}
			else if(choice==22)
			{
				this.devList.add(new DevelopmentCard("market", "res/dev_cards/market.png"));
			}
			else if(choice==23)
			{
				this.devList.add(new DevelopmentCard("palace", "res/dev_cards/palace.png"));
			}
			else
			{
				this.devList.add(new DevelopmentCard("university", "res/dev_cards/university.png"));
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
	
	public void hasLongestRoad()
	{
		
	}
	
}