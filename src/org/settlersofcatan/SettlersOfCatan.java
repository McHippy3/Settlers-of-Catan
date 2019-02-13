package org.settlersofcatan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.stage.Screen.*;
import java.io.File;
import javafx.geometry.Rectangle2D;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SettlersOfCatan extends Application
{
	
	private Scene mainScene;
	private OfflineGameScene offlineGameScene;
	private Stage stage;
	private Vertex vertexes[][];
	private Edge edges[][];
	
	/*********************************************
	 * GUI STUFF *
	 *********************************************/
	public void start(Stage stage) 
	{
		this.stage = stage;
		
		Button startButton = new Button("Start Game");
		
		mainScene = new Scene((Parent) Scenes.titleScene(startButton));

		//Setting stage
		stage.setScene(mainScene);
		stage.setTitle("Settlers of Catan");
		stage.getIcons().add(new Image("res/window_icon.png"));
		stage.setMaximized(true);
		stage.setResizable(true);
		stage.show();
		startButton.setOnMouseClicked(
				(MouseEvent e) -> setNameScene()
				);
	}
	
	private void setNameScene() 
	{
		//Getting Player Names
		Button submitButton = new Button("Submit Names");
		TextField name1 = new TextField();
		TextField name2 = new TextField();
		TextField name3 = new TextField();
		TextField name4 = new TextField();
		
		mainScene.setRoot((Parent) Scenes.nameScene(submitButton, name1, name2, name3, name4));
		
		//Starts Game
		submitButton.setOnMouseClicked(
				(MouseEvent e) -> {
					String[] names = {name1.getText(), name2.getText(), name3.getText(), name4.getText()};
					gameStart(names);
					setOfflineGameScene();
					//play();
					}
				);
	}
	
	private void setOfflineGameScene() 
	{
		vertexes = new Vertex[12][11];
		edges = new Edge[11][11];
		offlineGameScene = new OfflineGameScene(vertexes, edges, players);
		mainScene.setRoot(offlineGameScene);
		offlineGameScene.requestBuild(0);
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	/*********************************************
	 * GAME STUFF *
	 *********************************************/
	private String vertex[][] = 
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
	private String edgey[][] = 
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
	private ArrayList<Player> players = new ArrayList<>();
	private Boolean gameloop = true;
	private int playernumber = 0;
	private Scanner sc = new Scanner(System.in);
	private String input;
	private Boolean playagain = false;
	
	//Starting Game/Initialization
	public void gameStart(String[] names) 
	{
		for(int i = 0; i < names.length; i++)
		{
			ArrayList<ResourceCard> resList = new ArrayList<>();
			resList.add(new ResourceCard("brick"));
			resList.add(new ResourceCard("ore"));
			resList.add(new ResourceCard("grain"));
			resList.add(new ResourceCard("sheep"));
			resList.add(new ResourceCard("wood"));
			players.add(new Player(null, 0, names[i], resList));
		}
	}
	
	//Main game loop
	private void play()
	{
		do
		{	
			for(int x = 0; x < players.size(); x++)
			{
				for(int w = 0; w < players.size(); w++)
				{
					//Check if Player has won
					if(players.get(w).getVP() >= 10)
					{
						gameloop = false;
						System.out.println("Player " + players.get(0).getName() + " wins!");
					}
				}
				if(!gameloop)
					break;
				
				//players.get(x).turn();
				System.out.println("Are you finished with your turn? (Y/N)");
				sc.nextLine();
								
				if(x == 3)
				{
					x = -1;
				}
			}
		}
		while(gameloop == true);
	}
	
	//Individual Player Turns
	private void playTurn(int playerNum) 
	{
		/*
		String p2trade;
		System.out.println("Player: " + players.get(playerNum) + "'s turn");
		
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
					//trade(this,players.get(x));
				}
			}
		}*/
		
		
		System.out.println("Do you want to build? (Y/N)");
		input = sc.nextLine();
		if(input.equalsIgnoreCase("y"))
		{
			System.out.println("Sheep: " );
			System.out.println("Brick: " );
			System.out.println("Grain: " );
			System.out.println("Ore: " );
			System.out.println("Wood: " );
			
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
					
				}
				case "2": 
				{
				
				}
			}
		}
	}
	
	private int rollDice()
	{
		Random r = new Random();
		return r.nextInt(11) + 2;
	}

}
