package org.settlersofcatan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * GUI STUFF *
	 ************************************************************************************
	 ************************************************************************************/
	
	//TODO wool or sheep, order in listInventory()
	private int currentPlayer;
	private Scene mainScene;
	private OfflineGameScene offlineGameScene;
	private Stage stage;
	private VertexLink vertexes[][];
	private Edge edges[][];
	
	public void start(Stage stage) 
	{
		this.stage = stage;
		
		Button startButton = new Button("Start Game");
		
		mainScene = new Scene((Parent) Scenes.titleScene(startButton));
		
		//Integer properties
		currentPlayer = 0;

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
					}
				);
	}
	
	private void setOfflineGameScene()
	{
		initializeVertexes();
		initializeEdges();
		offlineGameScene = new OfflineGameScene(vertexes, edges, players);
		mainScene.setRoot(offlineGameScene);
		tradeModePhase1();
	}
	
	private void buildMode() 
	{
		//Build Button mouse events
		Button build1Button = new Button("Road");
		Button build2Button = new Button("Settlement");
		Button build3Button = new Button("Upgrade Settlement");
		Button noButton = new Button("No");

		build1Button.setOnMouseClicked(
				(MouseEvent e) -> {
					offlineGameScene.disableBuild();
					offlineGameScene.enableBuild(1);
					}
				);
		build2Button.setOnMouseClicked(
				(MouseEvent e) -> {
					offlineGameScene.disableBuild();
					offlineGameScene.enableBuild(2);
					}
				);
		build3Button.setOnMouseClicked(
				(MouseEvent e) -> {
					offlineGameScene.disableBuild();
					offlineGameScene.enableBuild(3);
					}
				);
		
		//Disable all build buttons and move onto trading
		noButton.setOnMouseClicked(
				(MouseEvent e) -> 
				{
					offlineGameScene.disableBuild(); 
					System.out.println("Next Player Turn");
					currentPlayer++;
					if(currentPlayer == 4) 
					{
						currentPlayer = 0;
					}
					tradeModePhase1();
				});
		offlineGameScene.requestBuild(currentPlayer, build1Button, build2Button, build3Button, noButton);
	}
	
	private void tradeModePhase1() 
	{
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		offlineGameScene.requestTradePhaseOne(currentPlayer, yesButton, noButton);
		yesButton.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase2();
				});
		noButton.setOnMouseClicked((MouseEvent me) -> buildMode());
	}
	
	private void tradeModePhase2() 
	{
		//Only non current turn players
		int[] otherPlayers = new int[3];
		int count = 0;
		for(int i = 0; i < 4; i++) 
		{
			if(i != currentPlayer)
			{
				otherPlayers[count] = i;
				count++;
			}
		}
				
		Button option1Button = new Button(players.get(otherPlayers[0]).getName());
		Button option2Button = new Button(players.get(otherPlayers[1]).getName());
		Button option3Button = new Button(players.get(otherPlayers[2]).getName());
		Button cancelButton = new Button("Cancel");

		offlineGameScene.requestTradePhaseTwo(currentPlayer, option1Button, option2Button, option3Button, cancelButton);
		option1Button.setOnMouseClicked(
				(MouseEvent me) -> {
					buildMode();
				});
		option2Button.setOnMouseClicked(
				(MouseEvent me) -> {
					buildMode();
				});
		option3Button.setOnMouseClicked(
				(MouseEvent me) -> {
					buildMode();
				});
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase1();
				});
	}
	
	private void tradeModePhase3() 
	{
		
	}
	
	private void tradeModePhase4() 
	{
		
	}
	
	private void initializeVertexes() 
	{
		vertexes = new VertexLink[12][11];
		//List of all the vertexes based on the main grid system
		int[] rowExists = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11};
		int[] colExists = {3, 5, 7, 2, 4, 6, 8, 2, 4, 6, 8, 1, 3, 5, 7, 9, 1, 3, 5, 7, 9, 0, 2, 4, 6, 8, 10, 0, 2, 4, 6, 8, 10, 1, 3, 5, 7, 9, 1, 3, 5, 7, 9, 2, 4, 6, 8, 2, 4, 6, 8, 3, 5, 7};
		int count = 0;
		for(int r = 0; r < 12; r++) 
		{
			for(int c = 0; c < 11; c++) 
			{
				//If vertex exists, then set it up
				if(count != rowExists.length && r == rowExists[count] && c == colExists[count]) 
				{
					//Setting properties
					vertexes[r][c] = new VertexLink(r, c);
					vertexes[r][c].setHasBuilding(false);
					vertexes[r][c].setMaxSize(30, 30);
					vertexes[r][c].setPrefSize(30, 30);
					vertexes[r][c].setDisable(true);
					vertexes[r][c].setStyle("-fx-background-color: transparent;");
					
					//MouseEvents
					vertexes[r][c].setOnMouseClicked(
							(MouseEvent e) -> {
								VertexLink v = (VertexLink) e.getSource();
								v.setHasBuilding(true);
								//Disable buttons after build
								offlineGameScene.disableBuild();
								System.out.println("Vertex Clicked " + v.getGridRow() + " " + v.getGridCol());
								Platform.runLater(new Runnable() 
								{
									@Override
									public void run() 
									{
										System.out.println("Updating");
										offlineGameScene.updateGUI(vertexes, edges, players);
										
										//Grant Points to player that owns the Settlement/Upgraded
										players.get(currentPlayer).victoryPoints++;
										
										//Call build again after being clicked
										buildMode();
									}
								});
							}
							);
					vertexes[r][c].setOnMouseEntered(
							(me) -> 
							{
								VertexLink v = (VertexLink) me.getSource();
								v.setStyle("-fx-background-color: #fcffaa");
							}
						);
					vertexes[r][c].setOnMouseExited(
							(me) -> 
							{
								VertexLink v = (VertexLink) me.getSource();
								v.setStyle("-fx-background-color: transparent");
							}
						);
					count++;
				}
				else 
				{
					vertexes[r][c] = null;
				}
			}
		}
	}
	
	private void initializeEdges() 
	{
		edges = new Edge[11][11];
		//Lists of edges that exist
		int[] rowExists = {0,0,0,0,0,0,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,6,6,6,6,7,7,7,7,7,8,8,8,8,8,8,8,8,9,9,9,9,10,10,10,10,10,10};
		int[] colExists = {3,4,5,6,7,8,2,4,6,8,2,3,4,5,6,7,8,9,1,3,5,7,9,1,2,3,4,5,6,7,8,9,10,0,2,4,6,8,10,1,2,3,4,5,6,7,8,9,10,1,3,5,7,9,2,3,4,5,6,7,8,9,2,4,6,8,3,4,5,6,7,8};
		
		int count = 0;
		for(int r = 0; r < 11; r++) 
		{
			for(int c = 0; c < 11; c++) 
			{
				//Only initialize edge if it exists on the grid
				if(count != rowExists.length && r == rowExists[count] && c == colExists[count]) 
				{
					edges[r][c] = new Edge(r, c);
					edges[r][c].setHasRoad(false);
					edges[r][c].setMaxSize(30, 30);
					edges[r][c].setPrefSize(30, 30);
					edges[r][c].setDisable(true);
					edges[r][c].setStyle("-fx-background-color: transparent;");
					
					//MouseEvents 
					edges[r][c].setOnMouseClicked(
							(MouseEvent me) -> 
							{
								Edge e = (Edge) me.getSource();
								e.setHasRoad(true);
								System.out.println("Edge Clicked " + e.getGridRow() + " " + e.getGridCol());
								//Disable after build
								offlineGameScene.disableBuild();
								Platform.runLater(new Runnable() 
								{
									@Override
									public void run() 
									{
										System.out.println("Updating");
										offlineGameScene.updateGUI(vertexes, edges, players);
										//Call build again
										buildMode();
									}
								});
							}
							);
					edges[r][c].setOnMouseEntered(
							(me) -> 
							{
								Edge e = (Edge) me.getSource();
								e.setStyle("-fx-background-color: #aafffa");
							}
						);
					edges[r][c].setOnMouseExited(
							(me) -> 
							{
								Edge e = (Edge) me.getSource();
								e.setStyle("-fx-background-color: transparent");
							}
						);
					count++;
				}
				else 
				{
					edges[r][c] = null;
				}
					
			}
		}
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * GAME STUFF *
	 ************************************************************************************
	 ************************************************************************************/
	
	private ArrayList<Player> players = new ArrayList<>();
	private Boolean gameloop = true;
	private int playernumber = 0;
	private Scanner sc = new Scanner(System.in);
	private String input;
	private Boolean gameInProgress = true, playAgain = false;
	
	//Starting Game/Initialization
	public void gameStart(String[] names) 
	{
		for(int i = 0; i < names.length; i++)
		{
			ArrayList<ResourceCard> resList = new ArrayList<>();
			resList.add(new ResourceCard("brick"));
			resList.add(new ResourceCard("ore"));
			resList.add(new ResourceCard("grain"));
			resList.add(new ResourceCard("wool"));
			resList.add(new ResourceCard("wood"));
			players.add(new Player(null, 0, names[i], resList));
		}
	}
	
	//Main game loop
	//Play method after you initialize the players
	private void play()
	{
		//Infinite loop until a player reaches 10 victory points
		do
		{
			//Loop through the list of players 
			for(int x = 0; x < players.size(); x++)
			{
				//At the start of each turn, check if that player has won
				if(checkWin(players))
				{
					gameInProgress = false;
					System.out.println("Game Won!!!");
					System.out.print("Do you want to play again?");
					input = sc.nextLine();
					if(input.equalsIgnoreCase("Y"))
						playAgain = true;
					else
						playAgain = false;
				}
				
				//Breaks the infinite turn loop
				if(gameInProgress == false)
					break;
								
				//turn(players.get(x), players);
				System.out.println("Are you finished with your turn? (Y/N)");
				sc.nextLine();
				
				//Makes the loop go on forever 
				if(x == 3)
				{
					x = -1;
				}
			}
		}
		while(gameInProgress);
	}
	
	//Individual Player Turns
	private void turn(Player p, ArrayList<Player> l)
	{
		Scanner sc = new Scanner(System.in);
		String p2trade;
		System.out.println("Player: " + p.playerName + "'s turn");
		
		//Roll Dice
		rollDice();
		
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
	
	private void trade(Player p1, Player p2)
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
			sc.close();
			return;
		}
	}
	
	//Game ends if player reaches 10 victory points
	private boolean checkWin(ArrayList<Player> p)
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
	
	private int rollDice()
	{
		Random r = new Random();
		return r.nextInt(11) + 2;
	}

}
