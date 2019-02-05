package org.settlersofcatan;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class main 
{	
	static String vertex[][] = 
	{
	      {"        ","  ","  ","  ","  ","  ","  ","  ","          "},
	      {"        ","01","02","03","04","05","06","07","          "},
		 {"    ","11","12","13","14","15","16","17","18","19 ","     "},
	    {"","21","22","23","24","25","26","27","28","29","210","211",""},
	    {"","31","32","33","34","35","36","37","38","39","310","311",""},
		 {"    ","41","42","43","44","45","46","47","48","49 ","     "},
	      {"        ","51","52","53","54","55","56","57","          "},
	      {"        ","  ","  ","  ","  ","  ","  ","  ","          "}
	};
	static String edges[][] = 
	{
			       {"		", "01", "02", "03", "04", "05", "06", "		"},
				         {"                   ", "21", "22", "23", "24", "                  "},
		     {"	    ", "31", "32", "33", "34", "35", "36", "37", "38", "          "},
		            {"                 ", "41", "42", "43", "44", "45", "                "},
		{"	", "51", "52", "53", "54", "55", "56", "57", "58", "59", "510", "	"},
		           {"		", "61", "62", "63", "64", "65", "66", "		"},
		     {"	    ", "71", "72", "73", "74", "75", "76", "77", "78", "          "},
		           {"	        ", "81", "82", "83", "84", "85", "86", "		"},
		                 {"                   ", "91", "92", "93", "94", "                  "},
		        {"   	     ", "101", "102", "103", "104", "105", "106", "	        "}
		
	};
	static ArrayList<Player> players = new ArrayList<>();
	static Boolean gameloop = true;
	static int playernumber = 0;
	static Scanner sc = new Scanner(System.in);
	static String buildtest;
	static String input;
	static Boolean playagain = false;
	
	//START
	public static void main(String[] args)
	{
		do
		{
			start();
			play();
		} while(playagain == true);
		
		
		
		
		
		
		
		
		// Noah's Code Up top
		// Chris's code down low
		
		
		
		
		
		
		/*	/*Player p1 = new Player();
		p1.playerName = "David";
		Player p2 = new Player();
		p2.playerName = "Chris";
		Player p3 = new Player();
		p3.playerName = "Mathiew";
		Player p4 = new Player();
		p4.playerName = "Noah";
		
		playerList.add(p1);
		playerList.add(p2);
		playerList.add(p3);
		playerList.add(p4);
		
		while(checkWin(playerList) == false)
		{
			if(checkWin(playerList) == true)
			{
				break;
			}
			
			// Game loop
		} 
		
		Tile t1 = new Tile("brick", 1);
		Tile t2 = new Tile("sheep", 2);
		Tile t3 = new Tile("stone", 3);
		
		Tile[]r1A = {t1, t2, t3};
		
		Line r1 = new Line(r1A); 
		
		// 19 Total Tiles
		Tile[] tileArray = new Tile[19];
		
		for(int i = 0;i<19;i++)
		{
			tileArray[i] = new Tile(i);
		}
		
		Tile[] ar1 = {tileArray[0], tileArray[1], tileArray[2]};
		Line one = new Line(ar1);
		
		Tile[] ar2 = {tileArray[3], tileArray[4], tileArray[5], tileArray[6]};
		Line two = new Line(ar2);
		
		Tile[] ar3 = {tileArray[7], tileArray[8], tileArray[9], tileArray[10], tileArray[11]};
		Line three = new Line(ar3);
		
		Tile[] ar4 = {tileArray[12], tileArray[13], tileArray[14], tileArray[15]};
		Line four = new Line(ar4);
		
		Tile[] ar5 = {tileArray[16], tileArray[17], tileArray[18]};
		Line five = new Line(ar5);
		
		ArrayList<VertexLink>vertexLinkList = new ArrayList<VertexLink>();
		
		
		
		// Line 1 Checked
		
		for(int i = 0;i<one.tiles.length;i++)
		{
			
			vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].tileID)}));
			
			if(one.tiles[i].hasRight(one) == true)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, one.tiles[i].getRight(one, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasBelowRight(one, two, i) == true && one.tiles[i].hasRight(one) == true)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, one.tiles[i].getRight(one, i).tileID), new Vertex(0, one.tiles[i].getBelowRight(one, two, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasBelowRight(one, two, i) == true && one.tiles[i].hasRight(one) == false)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowRight(one, two, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasBelowLeft(one, two, i) == true && one.tiles[i].hasLeft(one) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(one, two, i).tileID), new Vertex(2, one.tiles[i].getLeft(one, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasBelowLeft(one, two, i) == true && one.tiles[i].hasLeft(one) == false)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(one, two, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasLeft(one) == true)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, one.tiles[i].getLeft(one, i).tileID)}));
			}

			
			
			if(one.tiles[i].hasLeft(one) == false)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, one.tiles[i].tileID)}));
			}
			
			if(one.tiles[i].hasRight(one) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, one.tiles[i].tileID)}));
			}
		}
		
		
		
		// Line 2 Checked
		
		for(int i = 0;i<two.tiles.length;i++)
		{
			
			if(two.tiles[i].hasAbove(one, two, i) == true)
			{
				if(two.tiles[i].hasAboveRight(one, two, i) == true)
				{
					vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getAboveRight(one, two, i).tileID)}));
				}
				
				if(two.tiles[i].hasAboveLeft(one, two, i) == true)
				{	
					vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(2, two.tiles[i].getAboveLeft(one, two, i).tileID)}));
				}
			}
			
			
			
			if(two.tiles[i].hasRight(two) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasBelowRight(two, three, i) == true && two.tiles[i].hasRight(two) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID), new Vertex(4, two.tiles[i].getRight(two, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasBelowRight(two, three, i) == true && two.tiles[i].hasRight(two) == false)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasBelowLeft(two, three, i) == true && two.tiles[i].hasLeft(two) == true)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasBelowLeft(two, three, i) == true && two.tiles[i].hasLeft(two) == false)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasLeft(two) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasLeft(two) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].tileID)}));
			}
			
			
			
			if(two.tiles[i].hasRight(two) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].tileID)}));
			}
		}
		
		
		
		// Line 3 Checked
		
		for(int i = 0;i<three.tiles.length;i++)
		{
			
			if(three.tiles[i].hasAbove(two, three, i) == true)
			{
				if(three.tiles[i].hasAboveRight(two, three, i) == true)
				{	
					vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getAboveRight(two, three, i).tileID)}));
				}
				
				if(three.tiles[i].hasAboveLeft(two, three, i) == true)
				{	
					vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(2, three.tiles[i].getAboveLeft(two, three, i).tileID)}));
				}
			}
			
			if(three.tiles[i].hasRight(three) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
			}
			
			
			
			if(three.tiles[i].hasBelowRight(three, four, i) == true && three.tiles[i].hasRight(three) == true)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID), new Vertex(4, three.tiles[i].getRight(three, i).tileID)}));
			}
			
			
			
			if(three.tiles[i].hasBelowLeft(three, four, i) == true && three.tiles[i].hasLeft(three) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
			}
			
			
			
			if(three.tiles[i].hasLeft(three) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
			}
			
			
			
			if(three.tiles[i].hasLeft(three) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].tileID)}));
			}
			
			
			
			if(three.tiles[i].hasRight(three) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].tileID)}));
			}
		}
		
		
		
		// Line 4 
		
		for(int i = 0;i<four.tiles.length;i++)
		{
			vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].tileID), new Vertex(2, four.tiles[i].getAboveLeft(three, four, i).tileID), new Vertex(4, four.tiles[i].getAboveRight(three, four, i).tileID)}));
			
			if(four.tiles[i].hasRight(four) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
			}
			
			if(four.tiles[i].hasBelowRight(four, five, i) == true && four.tiles[i].hasRight(four) == true)
			{
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID), new Vertex(4, four.tiles[i].getRight(four, i).tileID)}));
			}
			
			if(four.tiles[i].hasBelowLeft(four, five, i) == true && four.tiles[i].hasLeft(four) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
			}
			
			if(four.tiles[i].hasLeft(four) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
			}
			
			if(four.tiles[i].hasLeft(four) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(3, four.tiles[i].getAboveLeft(three, four, i).tileID), new Vertex(5, four.tiles[i].tileID)}));
				
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].tileID)}));
			}
			
			if(four.tiles[i].hasRight(four) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, four.tiles[i].getAboveLeft(three, four, i).tileID), new Vertex(1, four.tiles[i].tileID)}));
				
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(2, four.tiles[i].tileID)}));
			}
		}
		
		
		
		// Line 5
		for(int i = 0;i<five.tiles.length;i++)
		{	
			vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
			
			
					
			vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].getAboveRight(four, five, i).tileID), new Vertex(3, five.tiles[i].getAboveRight(four, five, i).tileID)}));
			
				
					
			vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].getAboveLeft(four, five, i).tileID), new Vertex(2, five.tiles[i].getAboveLeft(four, five, i).tileID)}));
			
			
			
			if(five.tiles[i].hasRight(five) == true)
			{	
				vertexLinkList.add(new VertexLink( new Vertex[] { new Vertex(5, five.tiles[i].getRight(five, i).tileID), new Vertex(3, five.tiles[i].getAboveRight(four, five, i).tileID)}));
				
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].getRight(five, i).tileID)}));
			}
			
			if(five.tiles[i].hasLeft(five) == true)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(1, five.tiles[i].getLeft(five, i).tileID), new Vertex(3, five.tiles[i].getAboveLeft(four, five, i).tileID)}));
				
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(2, five.tiles[i].getLeft(five, i).tileID)}));
			}
			
			if(five.tiles[i].hasLeft(five) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].getAboveLeft(four, five, i).tileID), new Vertex(5, five.tiles[i].tileID)}));
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].tileID)}));
			}
			
			if(five.tiles[i].hasRight(five) == false)
			{	
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].getAboveRight(four, five, i).tileID), new Vertex(1, five.tiles[i].tileID)}));
				
				vertexLinkList.add(new VertexLink(new Vertex[] { new Vertex(2, five.tiles[i].tileID)}));
			}
		}

		
		VertexLink[][]grid = new VertexLink[12][11];
		
		int ends = 3;
		boolean flag = false;
		
		for(int x = 0;x<12;x++)
		{
			for(int y = 0;y<11;y++)
			{
				grid[x][y] = new VertexLink();
			}
		}
		
		grid = loadGrid(grid);
		
		
			
		int t = 0;
			
		for(int i = 0;i<12;i++)
		{
			for(int k = 0;k<11;k++)
			{
				if(grid[i][k] != null && vertexLinkList.get(t).v[0].tileID == i)
				{
					grid[i][k] = vertexLinkList.get(t);
					t += 1;
				}
			}
		}
		
	}
	
	// Dice Trade Build
	
	public static int rollDice()
	{
		Random r = new Random();
		return r.nextInt(11) + 2;
	}
	
	//sheepCount+" "+brickCount+" "+wheatCount+" "+woodCount+" "+stoneCount;
	
	public static void trade(Player p1, Player p2)
	{
		Scanner sc = new Scanner(System.in);
		
		int[]p1Inventory = p1.countInventory();
		int[]p2Inventory = p2.countInventory();
		
		boolean tradeWorks = false;
		
		p1.listInventory();
		
		p2.listInventory();
		
		System.out.println(p1.playerName+", what item do you want from "+p2.playerName+"?: ");
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
		
		
		
		System.out.println(p2.playerName+", what item do you want from "+p1.playerName+"?: ");
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
	
	// Use method at the beginning of every single turn
	public static boolean checkWin(ArrayList<Player> p)
	{
		for(int i = 0;i<p.size();i++)
		{
			if(p.get(i).victoryPoints >= 10)
			{
				return true;
			}
		}
		return false;
	}
		
	public void turn(Player p)
	{
		Scanner sc = new Scanner(System.in);
		// Roll Dice, get resources
		int current = rollDice();
		
		
		
		
		
		// Trade
		System.out.println("Do you want to trade?(Y/N): ");
		String ans = sc.nextLine();
		if(ans.equalsIgnoreCase("y"))
		{
			System.out.println("Who do you want to trade with?: ");
			String n = sc.nextLine();
			for(int i = 0;i<playerList.size();i++)
			{
				if(n.equalsIgnoreCase(playerList.get(i).playerName))
				{
					trade(p, playerList.get(i));
				}
			}
		}
		// Build
	}
	
	public static VertexLink[][] loadGrid(VertexLink[][] grid)
	{
		int ends = 3;
		boolean flag = false;
		for (int x = 0; x < 12; x++) 
		{
			if (x % 2 == 0 && ends % 2 == 1) 
			{
				
				
				// Line 0
				
				
				// Beginning
				for (int i = 0; i < ends; i++) 
				{
					grid[x][i] = null;
				}

				// Middle

				for (int i = ends; i < 11 - ends - 1; i++) 
				{
					if (i % 2 == 0)
					{
						grid[x][i] = null;
					}
				}

				// End

				for (int i = 11 - ends; i < 11; i++) 
				{
					grid[x][i] = null;
				}
			}
			
			
			
			// Line 1
			
			

			if (x % 2 == 1 && ends % 2 == 0) 
			{
				for (int i = 0; i < ends; i++) 
				{
					grid[x][i] = null;
				}

				// Middle

				for (int i = ends; i < 11 - ends; i++) 
				{
					if (i % 2 == 1) 
					{
						grid[x][i] = null;
					}
				}

				// End

				for (int i = 11 - ends; i < 11; i++)
				{
					grid[x][i] = null;
				}
			}
			
			
			
			// Line 2
			
			
			
			if (x % 2 == 0 && ends % 2 == 0) 
			{

				// Beginning

				for (int i = 0; i < ends; i++) 
				{
					grid[x][i] = null;
				}

				// Middle

				for (int i = ends + 1; i < 11 - ends; i++) 
				{
					if (i % 2 == 1) 
					{
						grid[x][i] = null;
					}
				}

				// End

				for (int i = 11 - ends; i < 11; i++) 
				{
					grid[x][i] = null;
				}
			}
			
			if (x % 2 == 1 && ends % 2 == 1) 
			{

				// Beginning

				for (int i = 0; i < ends; i++) 
				{
					grid[x][i] = null;
				}

				// Middle

				for (int i = ends + 1; i < 11 - ends; i++) 
				{
					if (i % 2 == 0) 
					{
						grid[x][i] = null;
					}
				}

				// End

				for (int i = 11 - ends; i < 11; i++) 
				{
					grid[x][i] = null;
				}
			}
			
			
			
			
			if (x % 2 == 0 && flag == true) 
			{
				ends += 1;
			}

			if (x % 2 == 0 && flag == false) 
			{
				ends -= 1;
				if (ends == 0) 
				{
					flag = true;
				}
			}
		}
		
		return grid;
	}
	*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	// End of Main
	
	public static void start()
	{
		String playername;
		
		System.out.print("Enter number of players: ");
		playernumber = sc.nextInt();
		sc.nextLine();
		
		for(int x = 0; x < playernumber; x++)
		{
			ArrayList<ResourceCard> resourcedeck = new ArrayList<>();
			resourcedeck.add(new ResourceCard("brick"));
			resourcedeck.add(new ResourceCard("ore"));
			resourcedeck.add(new ResourceCard("grain"));
			resourcedeck.add(new ResourceCard("sheep"));
			resourcedeck.add(new ResourceCard("wood"));
			System.out.print("Enter player name: ");
			playername = sc.nextLine();
			players.add(new Player(playername, x, resourcedeck, players, vertex, edges));
		}
	}
	
	public static void play()
	{
		do
		{	
			for(int x = 0; x < players.size(); x++)
			{
				for(int w = 0; w < players.size(); w++)
				{
					if(players.get(w).getVP() >= 10)
					{
						gameloop = false;
						System.out.println("Player " + players.get(0).getName() + " wins!");
						System.out.print("Do you want to play again?");
						input = sc.nextLine();
						if(input.equalsIgnoreCase("Y"))
						{
							playagain = true;
						}
						else
							playagain = false;
					}
				}
				if(gameloop == false)
					break;
				
				players.get(x).turn();
				System.out.println("Are you finished with your turn? (Y/N)");
				sc.nextLine();
				
				//players.get(0).setVP(10);
				
				if(x == 3)
				{
					x = -1;
				}
			}
		}
		while(gameloop == true);
	}
	
	public void test()
	{
		
	}
	
	public static int rollDice()
	{
		Random r = new Random();
		return r.nextInt(11) + 2;
	}
	
	public static void collectResources(int n)
	{
		
	}
}
