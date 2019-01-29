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
	static ArrayList<Player> players = new ArrayList<>();
	static Boolean gameloop = true;
	static int playernumber = 0;
	static Scanner sc = new Scanner(System.in);
	static String buildtest;
	static String input;
	static Boolean playagain = false;
	public static void main(String[] args)
	{
		do
		{
			start();
			play();
		} while(playagain == true);
	}

	public static void start()
	{
		ArrayList<ResourceCard> deck = new ArrayList<>();
		String playername;
		
		System.out.print("Enter number of players: ");
		playernumber = sc.nextInt();
		sc.nextLine();
		for(int x = 0; x < playernumber; x++)
		{
			System.out.print("Enter player name: ");
			playername = sc.nextLine();
			players.add(new Player(playername, x, deck, players, vertex));
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
}
