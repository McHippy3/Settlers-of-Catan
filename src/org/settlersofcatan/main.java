package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;

public class main 
{
	static Bank b = new Bank();
	static ArrayList<Player>players = new ArrayList<>();
	static boolean gameLoop = true;
	static int playernumber = 0;
	static Scanner sc = new Scanner(System.in);
	static String buildtest;
	static String input;
	static boolean playagain = false;
	static VertexLink[][]vertexes = new VertexLink[12][11];
	//static EdgeLink[][] edges = new EdgeLink[][];
	
	public static void main(String[] agrs)
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
		
		for(int x = 0;x<12;x++)
		{
			for(int y = 0;y<11;y++)
			{
				vertexes[x][y] = new VertexLink();
			}
		}
		
		vertexes = loadGrid(vertexes);
		
		// Line 1 Checked
		
		for(int i = 0;i<one.tiles.length;i++)
		{
			// i = current tile ID
			int ends = 3;
			
			// Done
			vertexes[0][ends + (i % ends) * 2] = new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].tileID)});
			
			
			// Done
			if(one.tiles[i].hasLeft(one) == false)
			{
				ends = 2;
				vertexes[1][ends + (i % 2) * i] = (new VertexLink(new Vertex[] { new Vertex(5, one.tiles[i].tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasRight(one) == false)
			{	
				ends = 2;
				vertexes[1][ends + (ends * i) + i] = (new VertexLink(new Vertex[] { new Vertex(1, one.tiles[i].tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasRight(one) == true)
			{
				ends = 2;
				vertexes[1][ends + (ends * one.tiles[i].getRight(one, i).tileID)] = (new VertexLink(new Vertex[] { new Vertex(5, one.tiles[i].getRight(one, i).tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasBelowLeft(one, two, i) == true && one.tiles[i].hasLeft(one) == true)
			{	
				ends = 2;
				vertexes[2][ends + (one.tiles[i].getLeft(one, i).tileID * 2) + 2] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(one, two, i).tileID), new Vertex(2, one.tiles[i].getLeft(one, i).tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasBelowLeft(one, two, i) == true && one.tiles[i].hasLeft(one) == false)
			{
				ends = 2;
				vertexes[2][ends + (i % 2) * i] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(one, two, i).tileID)}));
			}
			
			
			// Done
			if(one.tiles[i].hasBelowRight(one, two, i) == true && one.tiles[i].hasRight(one) == true)
			{
				vertexes[2][ends + (ends * one.tiles[i].getRight(one, i).tileID)] = (new VertexLink(new Vertex[] { new Vertex(4, one.tiles[i].getRight(one, i).tileID), new Vertex(0, one.tiles[i].getBelowRight(one, two, i).tileID)}));
			}
			
			
			// Check
			if(one.tiles[i].hasBelowRight(one, two, i) == true && one.tiles[i].hasRight(one) == false)
			{
				ends = 2;
				vertexes[2][ends + (one.tiles[i].getBelowRight(one, two, i).tileID /2 ) * i] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowRight(one, two, i).tileID)}));
			}
			
			
			
			if(one.tiles[i].hasLeft(one) == true)
			{
				ends = 2;
				vertexes[1][ends + (ends * i)] = (new VertexLink(new Vertex[] { new Vertex(1, one.tiles[i].getLeft(one, i).tileID)}));
			}
		}
		
		
		
		// Line 2 Checked
		
		for(int i = 0;i<two.tiles.length;i++)
		{
			
			if(two.tiles[i].hasLeft(two) == false)
			{
				vertexes[3][1] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].tileID)}));
			}
			
			
			
			if(two.tiles[i].hasRight(two) == false)
			{
				vertexes[3][9] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].tileID)}));
			}
			
			

			if(two.tiles[i].hasRight(two) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[3][3] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
					break;
				case 1:
					vertexes[3][5] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
					break;
				case 2:
					vertexes[3][7] = (new VertexLink(new Vertex[] { new Vertex(5, two.tiles[i].getRight(two, i).tileID)}));
					break;
				}
			}
			
			
			
			if(two.tiles[i].hasBelowLeft(two, three, i) == true && two.tiles[i].hasLeft(two) == true)
			{	
				switch(i)
				{
				case 1:
					vertexes[4][3] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 2:
					vertexes[4][5] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 3:
					vertexes[4][7] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowLeft(two, three, i).tileID), new Vertex(2, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				}
			}
			
			
			if(two.tiles[i].hasBelowLeft(two, three, i) == true && two.tiles[i].hasLeft(two) == false)
			{
				vertexes[4][1] = (new VertexLink(new Vertex[] { new Vertex(0, one.tiles[i].getBelowLeft(two, three, i).tileID)}));
			}
			
			
	
			if(two.tiles[i].hasBelowRight(two, three, i) == true && two.tiles[i].hasRight(two) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[4][3] = (new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getRight(two, i).tileID), new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
					break;
				case 1:
					vertexes[4][5] = (new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getRight(two, i).tileID), new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
					break;
				case 2:
					vertexes[4][7] = (new VertexLink(new Vertex[] { new Vertex(4, two.tiles[i].getRight(two, i).tileID), new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
					break;
				}
			}
			
			
			
			if(two.tiles[i].hasBelowRight(two, three, i) == true && two.tiles[i].hasRight(two) == false)
			{
				vertexes[4][9] = (new VertexLink(new Vertex[] { new Vertex(0, two.tiles[i].getBelowRight(two, three, i).tileID)}));
			}
			
			
			
			if(two.tiles[i].hasLeft(two) == true)
			{
				switch(i)
				{
				case 1:
					vertexes[3][3] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 2:
					vertexes[3][5] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				case 3:
					vertexes[3][7] = (new VertexLink(new Vertex[] { new Vertex(1, two.tiles[i].getLeft(two, i).tileID)}));
					break;
				}
			}
		}
		
		
		
		// Line 3 Checked
		
		for(int i = 0;i<three.tiles.length;i++)
		{
			
			if(three.tiles[i].hasLeft(three) == false)
			{
				vertexes[5][0] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].tileID)}));
				vertexes[6][0] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].tileID)}));
			}
			
			
			
			if(three.tiles[i].hasRight(three) == false)
			{
				vertexes[5][10] = (new VertexLink(new Vertex[] { new Vertex(1, three.tiles[i].tileID)}));
				vertexes[6][10] = (new VertexLink(new Vertex[] { new Vertex(2, three.tiles[i].tileID)}));
			}
			
			

			if(three.tiles[i].hasRight(three) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[5][2] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				case 1:
					vertexes[5][4] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				case 2:
					vertexes[5][6] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				case 3:
					vertexes[5][8] = (new VertexLink(new Vertex[] { new Vertex(5, three.tiles[i].getRight(three, i).tileID)}));
					break;
				}
			}
			
			
			
			if(three.tiles[i].hasBelowLeft(three, four, i) == true && three.tiles[i].hasLeft(three) == true)
			{	
				switch(i)
				{
				case 1:
					vertexes[6][2] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 2:
					vertexes[6][4] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 3:
					vertexes[6][6] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				case 4:
					vertexes[6][8] = (new VertexLink(new Vertex[] { new Vertex(0, three.tiles[i].getBelowLeft(three, four, i).tileID), new Vertex(2, three.tiles[i].getLeft(three, i).tileID)}));
					break;
				}
			}
			
			
	
			if(three.tiles[i].hasBelowRight(three, four, i) == true && three.tiles[i].hasRight(three) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[6][2] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
				case 1:
					vertexes[6][4] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
				case 2:
					vertexes[6][6] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
				case 3:
					vertexes[6][8] = (new VertexLink(new Vertex[] { new Vertex(4, three.tiles[i].getRight(three, i).tileID), new Vertex(0, three.tiles[i].getBelowRight(three, four, i).tileID)}));
					break;
					
				}
			}
		}
		
		
		
		// Line 4 
		
		for(int i = 0;i<four.tiles.length;i++)
		{
			if(four.tiles[i].hasLeft(four) == false)
			{
				vertexes[7][1] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].tileID), new Vertex(3, four.tiles[i].getAboveLeft(three, four, i).tileID)}));
				vertexes[8][1] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].tileID)}));
			}
			
			
			
			if(four.tiles[i].hasRight(four) == false)
			{
				vertexes[7][9] = (new VertexLink(new Vertex[] { new Vertex(1, four.tiles[i].tileID), new Vertex(3, four.tiles[i].getAboveRight(three, four, i).tileID)}));
				vertexes[8][9] = (new VertexLink(new Vertex[] { new Vertex(2, four.tiles[i].tileID)}));
			}
			
			

			if(four.tiles[i].hasRight(four) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[7][3] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
					break;
				case 1:
					vertexes[7][5] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
					break;
				case 2:
					vertexes[7][7] = (new VertexLink(new Vertex[] { new Vertex(5, four.tiles[i].getRight(four, i).tileID)}));
					break;
				}
			}
			
			
			
			if(four.tiles[i].hasBelowLeft(four, five, i) == true && four.tiles[i].hasLeft(four) == true)
			{	
				switch(i)
				{
				case 1:
					vertexes[8][2] = (new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
					break;
				case 2:
					vertexes[8][4] = (new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
					break;
				case 3:
					vertexes[8][6] = (new VertexLink(new Vertex[] { new Vertex(0, four.tiles[i].getBelowLeft(four, five, i).tileID), new Vertex(2, four.tiles[i].getLeft(four, i).tileID)}));
					break;
				}
			}
			
			
	
			if(four.tiles[i].hasBelowRight(four, five, i) == true && four.tiles[i].hasRight(four) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[8][3] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].getRight(four, i).tileID), new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID)}));
					break;
				case 1:
					vertexes[8][5] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].getRight(four, i).tileID), new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID)}));
					break;
				case 2:
					vertexes[8][7] = (new VertexLink(new Vertex[] { new Vertex(4, four.tiles[i].getRight(four, i).tileID), new Vertex(0, four.tiles[i].getBelowRight(four, five, i).tileID)}));
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
				vertexes[11][3] = (new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
				break;
			case 1:
				vertexes[11][5] = (new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
				break;
			case 2:
				vertexes[11][7] = (new VertexLink(new Vertex[] { new Vertex(3, five.tiles[i].tileID)}));
				break;
				
			}
			if(five.tiles[i].hasLeft(five) == false)
			{
				vertexes[9][1] = (new VertexLink(new Vertex[] { new Vertex(5, five.tiles[i].tileID), new Vertex(3, five.tiles[i].getAboveLeft(four, five, i).tileID)}));
				vertexes[10][1] = (new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].tileID)}));
			}
			
			
			
			if(five.tiles[i].hasRight(five) == false)
			{
				vertexes[9][8] = (new VertexLink(new Vertex[] { new Vertex(1, five.tiles[i].tileID), new Vertex(3, five.tiles[i].getAboveRight(four, five, i).tileID)}));
				vertexes[10][8] = (new VertexLink(new Vertex[] { new Vertex(2, five.tiles[i].tileID)}));
			}
			
			

			if(five.tiles[i].hasRight(five) == true)
			{
				switch(i)
				{
				case 0:
					vertexes[9][4] = (new VertexLink(new Vertex[] { new Vertex(5, five.tiles[i].getRight(five, i).tileID)}));
					vertexes[10][4] = (new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].getRight(five, i).tileID)}));
					break;
				case 1:
					vertexes[9][4] = (new VertexLink(new Vertex[] { new Vertex(5, five.tiles[i].getRight(five, i).tileID)}));
					vertexes[10][4] = (new VertexLink(new Vertex[] { new Vertex(4, five.tiles[i].getRight(five, i).tileID)}));
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
		
		do
		{
			start();
			play();
		}while(playagain == true);
	}
	
	// Dice Trade Build
	
	public static int rollDice()
	{
		Random r = new Random();
		return r.nextInt(11) + 2;
	}
	
	//sheepCount+" "+brickCount+" "+wheatCount+" "+woodCount+" "+stoneCount;
	
	public static void trade(int firstPlayer, int secondPlayer)
	{
		Scanner sc = new Scanner(System.in);
		
		Player p1 = players.get(firstPlayer);
		Player p2 = players.get(secondPlayer);
		
		System.out.println(p1.playerName+" which resource do you want from "+p2.playerName+"?");
		String ans1 = sc.nextLine();
		System.out.println("How many?: ");
		int quantity1 = sc.nextInt();
		sc.nextLine();
		
		System.out.println(p2.playerName+" which resource do you want from "+p1.playerName+"?");
		String ans2 = sc.nextLine();
		System.out.println("How many?: ");
		int quantity2 = sc.nextInt();
		sc.nextLine();
		

		
		if(ResourceCard.tradeWorks(p1, ans1, quantity1) == true && ResourceCard.tradeWorks(p2, ans2, quantity2) == true)
		{
			int cap1 = 0;
			int cap2 = 0;
			for(int x = 0;x<p2.resList.size();x++)
			{
				if(p2.resList.get(x).cardType.equalsIgnoreCase(ans1) && cap1 <= quantity1)
				{
					p2.resList.remove(x);
					p1.resList.add(new ResourceCard(ans1.toLowerCase()));
					cap1 += 1;
				}
			}
			
			for(int x = 0;x<p1.resList.size();x++)
			{
				if(p1.resList.get(x).cardType.equalsIgnoreCase(ans2) && cap2 <= quantity2)
				{
					p1.resList.remove(x);
					p2.resList.add(new ResourceCard(ans2.toLowerCase()));
					cap2 += 1;
				}
			}
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
			resList.add(new ResourceCard("grain"));
			resList.add(new ResourceCard("ore"));
			resList.add(new ResourceCard("wood"));
			resList.add(new ResourceCard("wool"));
			System.out.print("Enter player name: ");
			playername = sc.nextLine();
			Player t = new Player(x, playername, resList);
			players.add(t);
		}
	}
	
	// Where l = players
	
	public static void turn(Player p)
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
			for(int x = 0; x < players.size(); x++)
			{
				if(p2trade.equalsIgnoreCase(players.get(x).playerName))
				{
					trade(p.playerNumber, x);
				}
			}
		}
		
		
		System.out.println("Do you want to build? (Y/N)");
		input = sc.nextLine();
		if(input.equalsIgnoreCase("y"))
		{
			p.listInventory();
			
			//Build
			System.out.println("What do you want to build?");
			System.out.println("1. Road");
			System.out.println("2. Settlement");
			System.out.println("3. Upgrade Settlement");
			System.out.println("4. Development Card");
			input = sc.nextLine();
			switch(input)
			{
				case "1": 
				{
					p.buildRoad();
					p.listInventory();
					break;
				}
				case "2": 
				{
					p.buildSettlement(b, vertexes);
					p.listInventory();
					break;
				}
				
				case "3":
				{
					p.upgradeSettlement(b, vertexes);
					break;
				}
				
				case "4":
				{
					p.buildDevelopmentCard(b);
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
				turn(players.get(x));
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