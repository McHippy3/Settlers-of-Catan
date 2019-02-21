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
	
	private int currentPlayer;
	private Scene mainScene;
	private OfflineGameScene offlineGameScene;
	private Stage stage;
	private VertexLink vertexes[][];
	private EdgeLink edges[][];
	
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
		rollMode();
	}
	
	private void rollMode() 
	{
		//Roll Button
		Button rollButton = new Button("Roll Dice");
		
		rollButton.setOnMouseClicked(
				(MouseEvent me) -> {
					Button continueButton = new Button("Continue");
					continueButton.setOnMouseClicked((MouseEvent e) -> tradeModePhase1());
					
					offlineGameScene.showRollResults(rollDice(), rollDice(), continueButton);
				}
				);
		offlineGameScene.requestRollDice(currentPlayer, rollButton);
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
					rollMode();
				});
		offlineGameScene.requestBuild(currentPlayer, build1Button, build2Button, build3Button, noButton);
	}
	
	private void tradeModePhase1() 
	{
		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		offlineGameScene.requestTradePhaseOne(yesButton, noButton);
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
					vertexes[r][c].setMaxSize(30, 30);
					vertexes[r][c].setPrefSize(30, 30);
					vertexes[r][c].setDisable(true);
					vertexes[r][c].setStyle("-fx-background-color: transparent;");
					
					//MouseEvents
					vertexes[r][c].setOnMouseClicked(
							(MouseEvent e) -> {
								VertexLink v = (VertexLink) e.getSource();
								
								//Build new settlement if there isn't one yet, else upgrade to city
								if(v.getHasBuilding() == 0) 
								{
									players.get(currentPlayer).buildSettlement(bank, vertexes, v.getGridRow(), v.getGridCol());
								}
								else 
								{
									players.get(currentPlayer).upgradeSettlement(bank, vertexes, v.getGridRow(), v.getGridCol());
								}
								
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
		edges = new EdgeLink[11][11];
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
					edges[r][c] = new EdgeLink(r, c);
					edges[r][c].setMaxSize(30, 30);
					edges[r][c].setPrefSize(30, 30);
					edges[r][c].setDisable(true);
					edges[r][c].setStyle("-fx-background-color: transparent;");
					
					//MouseEvents 
					edges[r][c].setOnMouseClicked(
							(MouseEvent me) -> 
							{
								EdgeLink e = (EdgeLink) me.getSource();
								players.get(currentPlayer).buildRoad(bank, edges, e.getGridRow(), e.getGridCol());
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
								EdgeLink e = (EdgeLink) me.getSource();
								e.setStyle("-fx-background-color: #aafffa");
							}
						);
					edges[r][c].setOnMouseExited(
							(me) -> 
							{
								EdgeLink e = (EdgeLink) me.getSource();
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
	private Bank bank;
	
	//Starting Game/Initialization
	public void gameStart(String[] names) 
	{
		for(int i = 0; i < names.length; i++)
		{
			ArrayList<ResourceCard> resList = new ArrayList<>();
			for(int a = 0; a < 10; a++) {
			resList.add(new ResourceCard("brick"));
			resList.add(new ResourceCard("ore"));
			resList.add(new ResourceCard("grain"));
			resList.add(new ResourceCard("wool"));
			resList.add(new ResourceCard("wood"));
			}
			players.add(new Player(i, names[i], resList));
		}
		bank = new Bank();
	}
		
	public void trade(int firstPlayer, int secondPlayer)
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
		int dice1 = (int)(Math.random() * 6) + 1;
			
		return dice1;
	}
}
