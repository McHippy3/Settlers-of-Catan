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
		
		
		
		
		
		
		/*Player p1 = new Player();
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
		
		Line r1 = new Line(r1A); */
		
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
	
	
	// Use method at the beginning of every single turn
	
	
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	// End of Main
	
	public static void start()
	{
		String playername;
		
		System.out.print("Enter number of players: ");
		playernumber = sc.nextInt();
		sc.nextLine();
		
		for(int x = 0; x < playernumber; x++)
		{
			ArrayList<ResourceCard> resList = new ArrayList<>();
			resList.add(new ResourceCard("brick"));
			resList.add(new ResourceCard("ore"));
			resList.add(new ResourceCard("grain"));
			resList.add(new ResourceCard("sheep"));
			resList.add(new ResourceCard("wood"));
			System.out.print("Enter player name: ");
			playername = sc.nextLine();
			players.add(new Player(playername, x, resList, players, vertex, edges));
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
