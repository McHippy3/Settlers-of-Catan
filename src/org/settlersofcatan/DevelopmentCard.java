package org.settlersofcatan;

import java.util.ArrayList;
<<<<<<< HEAD

=======
>>>>>>> 3762622fd9e7c990f52641c305fc5aa8bdd2fa17

public class DevelopmentCard 
{
	private String type, imageURL;
	boolean playable;
	private int index;
	
	
	// The boolean playable is used as a flag to check whether or not one can play a Dev. Card. (See rules)
	
	public DevelopmentCard()
	{
		type = "";
		playable = false;
	}
	
	public DevelopmentCard(String type, String imageURL, int index)
	{
		this.type = type;
		this.imageURL=imageURL;
		this.index=index;
		playable = false;
	}
	
	public String getType()
	{
		return type;
	}
	public int getIndex()
	{
		return index;
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
			System.out.println("wool");
			break;
		case "brick":
			bank.giveResource("brick", p, 2);
			System.out.println("brick");
			break;
		case "grain":
			bank.giveResource("grain", p, 2);
			System.out.println("grain");
			break;
		case "wood":
			bank.giveResource("wood", p, 2);
			System.out.println("wood");
			break;
		case "ore":
			bank.giveResource("ore", p, 2);
			System.out.println("ore");
			break;
		}
	}
	
	// Pick one resource and take all from player
	public static void monopoly(Player p, String s, ArrayList<Player> l)
	{
		for(int i = 0;i<l.size();i++)
		{
			if(l.get(i).playerNumber != p.playerNumber)
			{
				for(int k = 0; k < l.get(i).resList.size();k++)
				{
					if(l.get(i).resList.get(k).cardType.equalsIgnoreCase(s))
					{
						l.get(i).resList.remove(k);
						k--;
						p.resList.add(new ResourceCard(s));
					}
				}
			}
		}
	}
	


}