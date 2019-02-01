package org.settlersofcatan;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Player 
{
	ArrayList<ResourceCard> resourceCards = new ArrayList<>();
	ArrayList<DevelopementCard> developementCards = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	int victorypoints = 0;
	ArrayList<Settlement> settlements = new ArrayList<>();
	ArrayList<Road> roads = new ArrayList<>();
	ArrayList<City> cities = new ArrayList<>();
	String playerName;
	String input;
	static String vertex[][];
	int playernumber;
	static Scanner sc = new Scanner(System.in);
	
	public Player(String name, int number, ArrayList<ResourceCard> resource, ArrayList<Player> player, String vertex[][])
	{
		this.playerName = name.toUpperCase();
		this.playernumber = number;
		this.resourceCards = resource;
		this.players = player;
		Player.vertex = vertex;
	}
	
	public static void trade(Player p1, Player p2)
	{
		Scanner sc = new Scanner(System.in);
		
		int[]p1Inventory = p1.countInventory();
		int[]p2Inventory = p2.countInventory();
		
		boolean tradeWorks = false;
		
		p1.listInventory();
		System.out.println();
		p2.listInventory();
		System.out.println();
		
		System.out.println(p1.getName()+", what item do you want from "+p2.getName()+"?: ");
		String ans1 = sc.nextLine();
		
		int qS1 = 0;
		int qB1 = 0;
		int qWh1 = 0;
		int qWo1 = 0;
		int qSt1 = 0;
		
		int qS2 = 0;
		int qB2 = 0;
		int qWh2 = 0;
		int qWo2 = 0;
		int qSt2 = 0;
		
		switch(ans1)
		{
		case "sheep":
			System.out.println("How many?: ");
			qS1 = sc.nextInt();
			// Trade
			if(qS1 <= p2Inventory[0])
			{
				tradeWorks = true;
			}
			// Don't trade
			else
			{
				tradeWorks = false;
				break;
			}
			break;
		case "brick":
			System.out.println("How many?: ");
			qB1 = sc.nextInt();
			if(qB1 <= p2Inventory[1])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		case "wheat":
			System.out.println("How many?: ");
			qWh1 = sc.nextInt();
			if(qWh1 <= p2Inventory[2])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		case "wood":
			System.out.println("How many?: ");
			qWo1 = sc.nextInt();
			if(qWo1 <= p2Inventory[3])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		case "stone":
			System.out.println("How many?: ");
			qSt1 = sc.nextInt();
			if(qSt1 <= p2Inventory[4])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		}
		
		
		
		System.out.println(p2.getName()+", what item do you want from "+p1.getName()+"?: ");
		String ans2 = sc.next();
		
		switch(ans2)
		{
		case "sheep":
			System.out.println("How many?: ");
			qS2 = sc.nextInt();
			// Trade
			if(qS2 <= p2Inventory[0])
			{
				tradeWorks = true;
				break;
			}
			// Don't trade
			else
			{
				tradeWorks = false;
				break;
			}
		case "brick":
			System.out.println("How many?: ");
			qB2 = sc.nextInt();
			if(qB2 <= p2Inventory[1])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		case "wheat":
			System.out.println("How many?: ");
			qWh2 = sc.nextInt();
			if(qWh2 <= p2Inventory[2])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		case "wood":
			System.out.println("How many?: ");
			qWo2 = sc.nextInt();
			if(qWo2 <= p2Inventory[3])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		case "stone":
			System.out.println("How many?: ");
			qSt2 = sc.nextInt();
			if(qSt2 <= p2Inventory[4])
			{
				tradeWorks = true;
				break;
			}
			else
			{
				tradeWorks = false;
				break;
			}
		}
		
		if(tradeWorks == true)
		{
			p2Inventory[0] += qS1;
			p2Inventory[1] += qB1;
			p2Inventory[2] += qWh1;
			p2Inventory[3] += qWo1;
			p2Inventory[4] += qS1;
			
			p1Inventory[0] += qS2;
			p1Inventory[1] += qB2;
			p1Inventory[2] += qWh2;
			p1Inventory[3] += qWo2;
			p1Inventory[4] += qS2;
		}
		else
		{
			return;
		}
	}
	
	public void turn()
	{
		String p2trade;
		System.out.println("Player: " + playerName + "'s turn");	
		//Roll Dice
		main.rollDice();
		
		
		//Trade
		System.out.println("Do you want to trade? (Y/N)");
		input = sc.nextLine();
		if(input.equalsIgnoreCase("y"))
		{
			System.out.println("Who do you want to trade with: ");
			p2trade = sc.nextLine();
			p2trade = p2trade.toUpperCase();
			for(int x = 0; x < 3; x++)
			{
				if(p2trade.equalsIgnoreCase(players.get(x).getName()))
				{
					trade(this,players.get(x));
				}
			}
		}	
		
		
		System.out.println("Do you want to build? (Y/N)");
		input = sc.nextLine();
		if(input.equalsIgnoreCase("y"))
		{
			//Build
			System.out.println();
			for(String[] row : vertex)
				System.out.println(Arrays.toString(row));
		
			System.out.print("Enter number to build: ");
			String buildtest = sc.nextLine();
		
			for (int r = 0; r < vertex.length; r++) 		  
				//Loop through all elements of current row 
				for (int c = 0; c < vertex[r].length; c++) 
				{
					if(vertex[r][c].equalsIgnoreCase(buildtest) && build(r,c) == true)
					{
						String playernum = Integer.toString(this.playernumber);
						vertex[r][c] = playernum + " ";
					}
				}
		}
	}
	
	public static Boolean build(int r, int c)
	{
		Boolean output = true;
		if (vertex[r][c-1] == "0 " || vertex[r][c-1] == "1 " || vertex[r][c-1] == "2 " || vertex[r][c-1] == "3 ")
		{  
			System.out.println("building to your left");
			output=false;
	    }
	    if (vertex[r+1][c] == "0 " || vertex[r+1][c] == "1 " || vertex[r+1][c] == "2 " || vertex[r+1][c] == "3 ")
	    {          
	    	System.out.println("building to your bottom");
	    	output=false;
	    }	        
	    if (vertex[r][c+1] == "0 " || vertex[r][c+1] == "1 " || vertex[r][c+1] == "2 " || vertex[r][c+1] == "3 ")
	    {          
	    	System.out.println("building to your right");
	    	output=false;
	    }
	    return output;
	}
	
	public void upgrade(Settlement s)
	{
		
	}
	
	public int getVP()
	{
		return victorypoints;
	}
	
	//test method for win condition
	public void setVP(int n)
	{
		victorypoints = n;
	}
	
	public int[] countInventory()
	{
		int[] resources = new int[5];
		int sheepCount = 0;
		int brickCount = 0;
		int wheatCount = 0;
		int woodCount = 0;
		int stoneCount = 0;
		
		for(int i = 0;i<resourceCards.size();i++)
		{
			switch(resourceCards.get(i).cardType)
			{
			case "sheep":
				sheepCount += 1;
				break;
			case "brick":
				brickCount += 1;
				break;
			case "wheat":
				wheatCount += 1;
				break;
			case "wood":
				woodCount += 1;
				break;
			case "stone":
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
	
	public void listInventory()
	{
		ArrayList<ResourceCard>temp = resourceCards;
		int sheepCount = 0;
		int brickCount = 0;
		int wheatCount = 0;
		int woodCount = 0;
		int stoneCount = 0;
		
		for(int i = 0;i<temp.size();i++)
		{
			switch(temp.get(i).cardType)
			{
			case "sheep":
				sheepCount += 1;
				break;
			case "brick":
				brickCount += 1;
				break;
			case "wheat":
				wheatCount += 1;
				break;
			case "wood":
				woodCount += 1;
				break;
			case "stone":
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
	
	public String getName()
	{
		return this.playerName;
	}
}