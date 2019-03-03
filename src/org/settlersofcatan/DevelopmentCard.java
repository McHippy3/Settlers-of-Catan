package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;

import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class DevelopmentCard 
{
	private OfflineGameScene offlineGameScene;
	private String type, imageURL;
	boolean playable;
	
	
	// The boolean playable is used as a flag to check whether or not one can play a Dev. Card. (See rules)
	
	public DevelopmentCard()
	{
		type = "";
		playable = false;
	}
	
	public DevelopmentCard(String type, String imageURL)
	{
		this.type = type;
		this.imageURL=imageURL;
		playable = false;
	}
	
	public String getType()
	{
		return type;
	}
	
	
	// Probably won't ever need this constructor, just in case
	public DevelopmentCard(String s, boolean flag)
	{
		type = s;
		playable = flag;
	}
	
	// Forced to move Robber
	public static void knight(/*Tile x*/)
	{
		//Robber.moveRobber(x);
	}
	
	// Take 2 of any Resource from the Bank, can build with these in the same turn
	public static void yearOfPlenty(Bank bank, Player p, String res)
	{	
		switch(res)
		{
		case "wool":
			bank.giveResource("wool", p, 2);
			break;
		case "brick":
			bank.giveResource("brick", p, 2);
			break;
		case "grain":
			bank.giveResource("grain", p, 2);
			break;
		case "wood":
			bank.giveResource("wood", p, 2);
			break;
		case "ore":
			bank.giveResource("ore", p, 2);
			break;
		}
	}
	
	// Pick one resource and take all from player
	public static void monopoly(Player p/*, String s, ArrayList<Player> l*/)
	{
		/*for(int i = 0;i<l.size();i++)
		{
			if(l.get(i).playerNumber != p.playerNumber)
			{
				int y = 0;
				for(int k = 0;k<l.get(i).resList.size() && y<ResourceCard.getMaxRsc(l.get(i), s );k++)
				{
					if(l.get(i).resList.get(k).cardType.equalsIgnoreCase(s))
					{
						l.get(i).resList.remove(k);
						p.resList.add(new ResourceCard(s));
						y += 1;
					}
				}
			}
		}*/
	}
	public static void RoadBuild(Player p)
	{
		
		//SettlersOfCatan.roadBuild();

	}


}