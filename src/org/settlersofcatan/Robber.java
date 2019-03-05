package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Random;

public class Robber 
{
	static Tile t;
	
	
	// Default Constructor, tileArray is the global variable
	public Robber(Tile[] tileArray)
	{
		for(int i = 0;i<tileArray.length;i++)
		{
			if(tileArray[i].type.equalsIgnoreCase("desert"))
			{
				t = tileArray[i];
			}
		}
	}
	
	public Robber(Tile x)
	{
		t = x;
	}
	
	
	// playerList is a global variable, same w/ Bank
	public void stealResources(ArrayList<Player> playerList, Bank global)
	{
		for(int i = 0;i<playerList.size();i++)
		{
			if(playerList.get(i).resList.size() > 7)
			{
				for(int k = 0;k < playerList.get(i).resList.size();k++)
				{
					switch(playerList.get(i).resList.remove(k).cardType)
					{
					case "brick":
						global.brickList.add(new ResourceCard("brick"));
						break;
					case "wool":
						global.woolList.add(new ResourceCard("wool"));
						break;
					case "grain":
						global.grainList.add(new ResourceCard("grain"));
						break;
					case "ore":
						global.oreList.add(new ResourceCard("ore"));
						break;
					case "wood":
						global.woodList.add(new ResourceCard("wood"));
						break;
					}
				}
			}
		}
	}
	
	public void stealSingleResource(Player receiver, Player giver) 
	{
		if(!giver.resList.isEmpty()) 
		{
			Random rand = new Random();
			receiver.resList.add(giver.resList.remove(rand.nextInt(giver.resList.size())));
		}
	}
	
	public static void moveRobber(Tile x)
	{
		t = x;
	}
}