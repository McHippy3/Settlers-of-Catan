package org.settlersofcatan;

import java.util.*;

public class main 
{
	static ArrayList<Player>players = new ArrayList<>();
	static boolean gameLoop = true;
	static int playernumber = 0;
	static Scanner sc = new Scanner(System.in);
	static String buildtest;
	static String input;
	static boolean playagain = false;
	
	public static void main(String[] args)
	{	
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

		VertexLink[][]grid = new VertexLink[12][11];
		
		for(int x = 0;x<12;x++)
		{
			for(int y = 0;y<11;y++)
			{
				grid[x][y] = new VertexLink();
			}
		}
		
		grid = loadGrid(grid);
		
		// Line 1 Checked
		
		for(int i = 0;i<one.tiles.length;i++)
		{
			// i = current tile ID
			int ends = 3;
			
			// Done
			grid[0][ends + (i % ends) * 2] = new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].tileID)});
			
			
			// Done
			if(one.tiles[i].hasLeft(one) == false)
			{
				ends = 2;
				grid[1][ends + (i % 2) * i] = (new VertexLink(new Vertex[] { new Vertex(5, one.tiles[i].tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasRight(one) == false)
			{	
				ends = 2;
				grid[1][ends + (ends * i) + i] = (new VertexLink(new Vertex[] { new Vertex(1, one.tiles[i].tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasRight(one) == true)
			{
				ends = 2;
				grid[1][ends + (ends * one.tiles[i].getRight(one, i).tileID)] = (new VertexLink(new Vertex[] { new Vertex(5, one.tiles[i].getRight(one, i).tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasBelowLeft(one, two, i) == true && one.tiles[i].hasLeft(one) == true)
			{	
				ends = 2;
				grid[2][ends + (one.tiles[i].getLeft(one, i).tileID * 2) + 2] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(one, two, i).tileID), new Vertex(2, one.tiles[i].getLeft(one, i).tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasBelowLeft(one, two, i) == true && one.tiles[i].hasLeft(one) == false)
			{
				ends = 2;
				grid[2][ends + (i % 2) * i] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(one, two, i).tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasBelowRight(one, two, i) == true && one.tiles[i].hasRight(one) == true)
			{
				grid[2][ends + (ends * one.tiles[i].getRight(one, i).tileID)] = (new VertexLink(new Vertex[] { new Vertex(4, one.tiles[i].getRight(one, i).tileID), new Vertex(0, one.tiles[i].getBelowRight(one, two, i).tileID)}));
			}
			
			
			// Check
			if(one.tiles[i].hasBelowRight(one, two, i) == true && one.tiles[i].hasRight(one) == false)
			{
				ends = 2;
				grid[2][ends + (one.tiles[i].getBelowRight(one, two, i).tileID /2 ) * i] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowRight(one, two, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasLeft(one) == true)
			{
				ends = 2;
				grid[1][ends + (ends * i)] = (new VertexLink(new Vertex[] { new Vertex(1, one.tiles[i].getLeft(one, i).tileID)}));
			}
		}
		
		
		
		// Line 2 Checked
		
		for(int i = 0;i<two.tiles.length;i++)
		{
			
			if(two.tiles[i].hasLeft(two) == false)
			{
				grid[3][1] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].tileID)}));
			}
			
			
			
			if(two.tiles[i].hasRight(two) == false)
			{
				grid[3][9] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].tileID)}));
			}
			
			

			if(two.tiles[i].hasRight(two) == true)
			{
				switch(i)
				{
				case 0:
					grid[3][3] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
					break;
				case 1:
					grid[3][5] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
					break;
				case 2:
					grid[3][7] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
					break;
				}
			}
			
			
			
			if(two.tiles[i].hasBelowLeft(two, three, i) == true && two.tiles[i].hasLeft(two) == true)
			{	
				switch(i)
				{
				case 1:
					grid[4][3] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 2:
					grid[4][5] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 3:
					grid[4][7] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				}
			}
			
			
			if(two.tiles[i].hasBelowLeft(two, three, i) == true && two.tiles[i].hasLeft(two) == false)
			{
				grid[4][1] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(two, three, i).tileID)}));
			}
			
			
	
			if(two.tiles[i].hasBelowRight(two, three, i) == true && two.tiles[i].hasRight(two) == true)
			{
				switch(i)
				{
				case 0:
					grid[4][3] = (new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getRight(two, i).tileID), new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
					break;
				case 1:
					grid[4][5] = (new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getRight(two, i).tileID), new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
					break;
				case 2:
					grid[4][7] = (new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getRight(two, i).tileID), new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
					break;
				}
			}
			
			
			
			if(two.tiles[i].hasBelowRight(two, three, i) == true && two.tiles[i].hasRight(two) == false)
			{
				grid[4][9] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasLeft(two) == true)
			{
				switch(i)
				{
				case 1:
					grid[3][3] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 2:
					grid[3][5] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 3:
					grid[3][7] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				}
			}
		}
		
		
		
		// Line 3 Checked
		
		for(int i = 0;i<three.tiles.length;i++)
		{
			
			if(three.tiles[i].hasLeft(three) == false)
			{
				grid[5][0] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].tileID)}));
				grid[6][0] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].tileID)}));
			}
			
			
			
			if(three.tiles[i].hasRight(three) == false)
			{
				grid[5][10] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].tileID)}));
				grid[6][10] = (new VertexLink(new Vertex[] { new Vertex(2, three.tiles[i].tileID)}));
			}
			
			

			if(three.tiles[i].hasRight(three) == true)
			{
				switch(i)
				{
				case 0:
					grid[5][2] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				case 1:
					grid[5][4] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				case 2:
					grid[5][6] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				case 3:
					grid[5][8] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				}
			}
			
			
			
			if(three.tiles[i].hasBelowLeft(three, four, i) == true && three.tiles[i].hasLeft(three) == true)
			{	
				switch(i)
				{
				case 1:
					grid[6][2] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 2:
					grid[6][4] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 3:
					grid[6][6] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 4:
					grid[6][8] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				}
			}
			
			
	
			if(three.tiles[i].hasBelowRight(three, four, i) == true && three.tiles[i].hasRight(three) == true)
			{
				switch(i)
				{
				case 0:
					grid[6][2] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
				case 1:
					grid[6][4] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
				case 2:
					grid[6][6] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
				case 3:
					grid[6][8] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
					
				}
			}
		}
		
		
		
		// Line 4 
		
		for(int i = 0;i<four.tiles.length;i++)
		{
			if(four.tiles[i].hasLeft(four) == false)
			{
				grid[7][1] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].tileID), new Vertex(3, four.tiles[i].getAboveLeft(three, four, i).tileID)}));
				grid[8][1] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].tileID)}));
			}
			
			
			
			if(four.tiles[i].hasRight(four) == false)
			{
				grid[7][9] = (new VertexLink(new Vertex[] { new Vertex(1, four.tiles[i].tileID), new Vertex(3, four.tiles[i].getAboveRight(three, four, i).tileID)}));
				grid[8][9] = (new VertexLink(new Vertex[] { new Vertex(2, four.tiles[i].tileID)}));
			}
			
			

			if(four.tiles[i].hasRight(four) == true)
			{
				switch(i)
				{
				case 0:
					grid[7][3] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
					break;
				case 1:
					grid[7][5] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
					break;
				case 2:
					grid[7][7] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
					break;
				}
			}
			
			
			
			if(four.tiles[i].hasBelowLeft(four, five, i) == true && four.tiles[i].hasLeft(four) == true)
			{	
				switch(i)
				{
				case 1:
					grid[8][2] = (new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
					break;
				case 2:
					grid[8][4] = (new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
					break;
				case 3:
					grid[8][6] = (new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
					break;
				}
			}
			
			
	
			if(four.tiles[i].hasBelowRight(four, five, i) == true && four.tiles[i].hasRight(four) == true)
			{
				switch(i)
				{
				case 0:
					grid[8][3] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].getRight(four, i).tileID), new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID)}));
					break;
				case 1:
					grid[8][5] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].getRight(four, i).tileID), new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID)}));
					break;
				case 2:
					grid[8][7] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].getRight(four, i).tileID), new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID)}));
					break;
				}
			}

			
			
			/*if(three.tiles[i].hasLeft(three) == true)
			{
				switch(i)
				{
				case 1:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 2:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 3:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 4:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				}
			}*/
		}
		
		
		
		// Line 5
		for(int i = 0;i<five.tiles.length;i++)
		{	
			switch(i)
			{
			case 0:
				grid[11][3] = (new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
				break;
			case 1:
				grid[11][5] = (new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
				break;
			case 2:
				grid[11][7] = (new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
				break;
				
			}
			if(five.tiles[i].hasLeft(five) == false)
			{
				grid[9][1] = (new VertexLink(new Vertex[] { new Vertex(5, five.tiles[i].tileID), new Vertex(3, five.tiles[i].getAboveLeft(four, five, i).tileID)}));
				grid[10][1] = (new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].tileID)}));
			}
			
			
			
			if(five.tiles[i].hasRight(five) == false)
			{
				grid[9][8] = (new VertexLink(new Vertex[] { new Vertex(1, five.tiles[i].tileID), new Vertex(3, five.tiles[i].getAboveRight(four, five, i).tileID)}));
				grid[10][8] = (new VertexLink(new Vertex[] { new Vertex(2, five.tiles[i].tileID)}));
			}
			
			

			if(five.tiles[i].hasRight(five) == true)
			{
				switch(i)
				{
				case 0:
					grid[9][4] = (new VertexLink(new Vertex[] { new Vertex(5, five.tiles[i].getRight(five, i).tileID)}));
					grid[10][4] = (new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].getRight(five, i).tileID)}));
					break;
				case 1:
					grid[9][4] = (new VertexLink(new Vertex[] { new Vertex(5, five.tiles[i].getRight(five, i).tileID)}));
					grid[10][4] = (new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].getRight(five, i).tileID)}));
					break;
				}
			}

			/*if(three.tiles[i].hasLeft(three) == true)
			{
				switch(i)
				{
				case 1:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 2:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 3:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 4:
					grid[4][2] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				}
			}*/
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
		case "wool":
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
		case "grain":
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
		case "ore":
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
		case "wool":
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
		case "grain":
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
		case "ore":
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
	
	// n represents the number from the rollDice() method
	// Create checkers for all 3 ^ 
	// 0 == GetLeft, 1 == GetRight, 2 == Below
	// Create method in Tile class w/ an int as an argument and then look through a Tile list to get tileType
	public static void collectResources(int n, ArrayList<Player> p, ArrayList<Tile>x, Bank b)
	{
		for(int i = 0;i<p.size();i++)
		{
			for(int k = 0;k<p.get(i).settleList.size();k++)
			{
				for(int l = 0;l<p.get(i).settleList.get(k).v.v.length;l++)
				{
					if(p.get(i).settleList.get(k).v.v[l].tileID == n)
					{
						switch(getCardType(p.get(i).settleList.get(k).v.v[l].tileID, x))
						{
						case "brick":
							ResourceCard.subtractBrick(1, p.get(i), b);
							break;
						case "wood":
							ResourceCard.subtractWood(1, p.get(i), b);
							break;
						case "wool":
							ResourceCard.subtractWool(1, p.get(i), b);
							break;
						case "grain":
							ResourceCard.subtractGrain(1, p.get(i), b);
							break;
						case "ore":
							ResourceCard.subtractOre(1, p.get(i), b);
							break;
						}
					}
				}
			}
		}
		
		for(int i = 0;i<p.size();i++)
		{
			for(int k = 0;k<p.get(i).cityList.size();k++)
			{
				for(int l = 0;l<p.get(i).cityList.get(k).v.v.length;l++)
				{
					if(p.get(i).cityList.get(k).v.v[l].tileID == n)
					{
						switch(getCardType(p.get(i).cityList.get(k).v.v[l].tileID, x))
						{
						case "brick":
							ResourceCard.subtractBrick(2, p.get(i), b);
							break;
						case "wood":
							ResourceCard.subtractWood(2, p.get(i), b);
							break;
						case "wool":
							ResourceCard.subtractWool(2, p.get(i), b);
							break;
						case "grain":
							ResourceCard.subtractGrain(2, p.get(i), b);
							break;
						case "ore":
							ResourceCard.subtractOre(2, p.get(i), b);
							break;
						}
					}
				}
			}
		}
	}
	
	public static String getCardType(int i, ArrayList<Tile>p)
	{
		for(int k = 0;k<p.size();k++)
		{
			if(p.get(k).tileID == i)
			{
				return p.get(k).type;
			}
		}
		return null;
	}
	
	public static void start()
	{
		String playername;
		
		System.out.print("Enter number of players: ");
		playernumber = sc.nextInt();
		sc.nextLine();
		
		for(int x = 0; x < playernumber; x++)
		{
			ArrayList<ResourceCard> resList = new ArrayList<>();
			//Add one of each resource card to each player's deck
			resList.add(new ResourceCard("brick"));
			resList.add(new ResourceCard("ore"));
			resList.add(new ResourceCard("grain"));
			resList.add(new ResourceCard("wool"));
			resList.add(new ResourceCard("wood"));
			System.out.print("Enter player name: ");
			playername = sc.nextLine();
			players.add(new Player());
		}
	}
	
	// Where l = players
	
	public static void turn(Player p, ArrayList<Player> l)
	{
		Scanner sc = new Scanner(System.in);
		String p2trade;
		System.out.println("Player: " + p.playerName + "'s turn");	
		//Roll Dice
		main.rollDice();
		
		
		//Trade
		System.out.println("Do you want to trade? (Y/N)");
		String input = sc.nextLine();
		if(input.equalsIgnoreCase("y"))
		{
			System.out.println("Who do you want to trade with: ");
			p2trade = sc.nextLine();
			for(int x = 0; x < l.size(); x++)
			{
				if(p2trade.equalsIgnoreCase(l.get(x).playerName))
				{
					trade(p, players.get(x));
				}
			}
		}	
		
		
		System.out.println("Do you want to build? (Y/N)");
		input = sc.nextLine();
		if(input.equalsIgnoreCase("y"))
		{
			System.out.println("Wool: " + p.resList.get(p.playerNumber).getWool(p));
			System.out.println("Brick: " + p.resList.get(p.playerNumber).getBrick(p));
			System.out.println("Grain: " + p.resList.get(p.playerNumber).getGrain(p));
			System.out.println("Ore: " + p.resList.get(p.playerNumber).getOre(p));
			System.out.println("Wood: " + p.resList.get(p.playerNumber).getWood(p));
			
			//Build
			System.out.println("What do you want to build?");
			System.out.println("1. Road");
			System.out.println("2. Settlement");
			System.out.println("3. Upgrade Settlement");
			input = sc.nextLine();
			switch(input)
			{
				case "1": 
				{
					p.buildRoad();
					System.out.println("Wool: " + p.resList.get(p.playerNumber).getWool(p));
					System.out.println("Brick: " + p.resList.get(p.playerNumber).getBrick(p));
					System.out.println("Grain: " + p.resList.get(p.playerNumber).getGrain(p));
					System.out.println("Ore: " + p.resList.get(p.playerNumber).getOre(p));
					System.out.println("Wood: " + p.resList.get(p.playerNumber).getWood(p));
					break;
				}
				case "2": 
				{
					p.buildSettlement();
					System.out.println("Wool: " + p.resList.get(p.playerNumber).getWool(p));
					System.out.println("Brick: " + p.resList.get(p.playerNumber).getBrick(p));
					System.out.println("Grain: " + p.resList.get(p.playerNumber).getGrain(p));
					System.out.println("Ore: " + p.resList.get(p.playerNumber).getOre(p));
					System.out.println("Wood: " + p.resList.get(p.playerNumber).getWood(p));
					break;
				}
			}
		}
	}
	
	//Play method after you initialize the players
	public static void play()
	{
		//Infinite loop until a player reaches 10 victory points
		do
		{	
			//Loop through the arraylist of players 
			for(int x = 0; x < players.size(); x++)
			{
				for(int w = 0; w < players.size(); w++)
				{
					//At the start of each turn, check if that player has won
					if(players.get(w).getVP() >= 10)
					{
						gameLoop = false;
						System.out.println("Player " + players.get(w).playerName+" wins!");
						System.out.print("Do you want to play again?");
						input = sc.nextLine();
						if(input.equalsIgnoreCase("Y"))
							playagain = true;
						else
							playagain = false;
					}
				}
				//Breaks the infinite turn loop
				if(gameLoop == false)
					break;
				
				//Specific player's turn
				turn(players.get(x), players);
				System.out.println("Are you finished with your turn? (Y/N)");
				sc.nextLine();
				
				//Makes the loop go on forever 
				if(x == 3)
				{
					x = -1;
				}
			}
		}
		while(gameLoop == true);
	}
}