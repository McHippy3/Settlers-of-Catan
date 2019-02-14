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
	
	// The add methods are used to add Resource Cards to a player's deck

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
	
	// The get methods are used to get an amount of Resource Cards in a player's deck
	
	
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
	
	// The subtract methods are used when building roads, settlements and cities
	
	public static void subtractBrick(int i, Player p, Bank b)
	{
		for(int a = 0;a<i;a++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("brick"))
			{
				p.resList.remove(i);
				b.brickList.add(p.resList.get(i));
			}
		}
	}
	
	public static void subtractWood(int i, Player p, Bank b)
	{
		for(int a = 0;a<i;a++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("wood"))
			{
				p.resList.remove(i);
				b.woodList.add(p.resList.get(i));
			}
		}
	}
	
	public static void subtractWool(int i, Player p, Bank b)
	{
		for(int a = 0;a<i;a++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("wool"))
			{
				p.resList.remove(i);
				b.woolList.add(p.resList.get(i));
			}
		}
	}
	
	public static void subtractGrain(int i, Player p, Bank b)
	{
		for(int a = 0;a<i;a++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("grain"))
			{
				p.resList.remove(i);
				b.grainList.add(p.resList.get(i));
			}
		}
	}
	
	public static void subtractOre(int i, Player p, Bank b)
	{
		for(int a = 0;a<i;a++)
		{
			if(p.resList.get(i).cardType.equalsIgnoreCase("ore"))
			{
				p.resList.remove(i);
				b.oreList.add(p.resList.get(i));

			}
		}
	}
}