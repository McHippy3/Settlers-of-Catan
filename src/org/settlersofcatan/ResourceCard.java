package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class ResourceCard
{
	String cardType;
	
	// Maybe add switch statement to make sure no typing errors are found
	
	public ResourceCard(String s)
	{
		cardType = s;
	}
	
	public static void addBrick(int i, Player p)
	{
		for(int a = 0;a<i;a++)
		{
			p.resList.add(new ResourceCard("brick"));
		}
	}
	
	public static void addWood(int i, Player p)
	{
		for(int a = 0;a<i;a++)
		{
			p.resList.add(new ResourceCard("wood"));
		}
	}
	
	public static void addWool(int i, Player p)
	{
		for(int a = 0;a<i;a++)
		{
			p.resList.add(new ResourceCard("wool"));
		}
	}
	
	public static void addGrain(int i, Player p)
	{
		for(int a = 0;a<i;a++)
		{
			p.resList.add(new ResourceCard("grain"));
		}
	}
	
	public static void addOre(int i, Player p)
	{
		for(int a = 0;a<i;a++)
		{
			p.resList.add(new ResourceCard("ore"));
		}
	}
	
	
	
	
	
	
	
	
	public static void subtractBrick(int numToRemove, Player p, Bank b)
	{
		int numRemoved = 0;
		for(int k = 0;k<p.resList.size();k++)
		{
			if(p.resList.get(k).cardType.equalsIgnoreCase("brick"))
			{
				b.brickList.add(p.resList.get(k));
				p.resList.remove(k);
				numRemoved++;
				if(numRemoved >= numToRemove)
					break;
			}
		}
	}
	
	public static void subtractWood(int numToRemove, Player p, Bank b)
	{
		int numRemoved = 0;
		for(int k = 0;k<p.resList.size();k++)
		{
			if(p.resList.get(k).cardType.equalsIgnoreCase("wood"))
			{
				b.woodList.add(p.resList.get(k));
				p.resList.remove(k);
				numRemoved++;
				if(numRemoved >= numToRemove)
					break;
			}
		}
	}
	
	public static void subtractWool(int numToRemove, Player p, Bank b)
	{
		int numRemoved = 0;
		for(int k = 0;k<p.resList.size();k++)
		{
			if(p.resList.get(k).cardType.equalsIgnoreCase("wool"))
			{
				b.woolList.add(p.resList.get(k));
				p.resList.remove(k);
				numRemoved++;
				if(numRemoved >= numToRemove)
					break;
			}
		}
	}
	
	public static void subtractGrain(int numToRemove, Player p, Bank b)
	{
		int numRemoved = 0;
		for(int k = 0;k<p.resList.size();k++)
		{
			if(p.resList.get(k).cardType.equalsIgnoreCase("grain"))
			{
				b.grainList.add(p.resList.get(k));
				p.resList.remove(k);
				numRemoved++;
				if(numRemoved >= numToRemove)
					break;
			}
		}
	}
	
	public static void subtractOre(int numToRemove, Player p, Bank b)
	{
		int numRemoved = 0;
		for(int k = 0;k<p.resList.size();k++)
		{
			if(p.resList.get(k).cardType.equalsIgnoreCase("ore"))
			{
				b.oreList.add(p.resList.get(k));
				p.resList.remove(k);
				numRemoved++;
				if(numRemoved >= numToRemove)
					break;
			}
		}
	}
	
	
	
	public static int getBrick(Player p)
	{
		int r = 0;
		for(int i = 0;i<p.resList.size();i++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("brick"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public static int getWood(Player p)
	{
		int r = 0;
		for(int i = 0;i<p.resList.size();i++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("wood"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public static int getWool(Player p)
	{
		int r = 0;
		for(int i = 0;i<p.resList.size();i++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("wool"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public static int getGrain(Player p)
	{
		int r = 0;
		for(int i = 0;i<p.resList.size();i++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("grain"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	public static int getOre(Player p)
	{
		int r = 0;
		for(int i = 0;i<p.resList.size();i++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("ore"))
			{
				r += 1;
			}
		}
		return r;
	}
	
	
	// What player 1 wants from player 2 ( Quantity and Rsc )
		public static boolean tradeWorks(Player target,ArrayList<String> rsc, ArrayList<Integer> quantity)
		{
			for(int i=0;i<rsc.size();i++){
				
			switch(rsc.get(i).toUpperCase()) 
			{
			case "BRICK":
				if(target.getBrick() < quantity.get(i))
				{
					return false;
				}
				break;
			case "WOOD":
				if(target.getWood() < quantity.get(i))
				{
					return false;
				}
				break;
			case "WOOL":
				if(target.getWool() < quantity.get(i))
				{
					return false;
				}
				break;
			case "GRAIN":
				if(target.getGrain() < quantity.get(i))
				{
					return false;
				}
				break;
			case "ORE":
				if(target.getOre() < quantity.get(i))
				{
					return false;
				}
				break;
			}
			
		}
			return true;
		}
	
	
	
	public static int getMaxRsc(Player p, String s)
	{
		int x = 0;
		for(int i = 0;i < p.resList.size();i++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase(s))
			{
				x += 1;
			}
		}
		return x;
	}
}