package org.settlersofcatan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * GUI SETUP *
	 ************************************************************************************
	 ************************************************************************************/
	
	private Stage stage;
	private int currentPlayer, setUpPhase, roll1, roll2;
	private boolean inSetup;
	private Scene mainScene;
	private OfflineGameScene offlineGameScene;
	private VertexLink vertexes[][];
	private EdgeLink edges[][];
	private Tile[] tileArray;
	private TileRow rowOne, rowTwo, rowThree, rowFour, rowFive;
	
	public void start(Stage stage) 
	{
		this.stage = stage;
		
		//Setting stage
		stage.setTitle("Settlers of Catan");
		stage.getIcons().add(new Image("res/window_icon.png"));
		mainScene = new Scene(new VBox());
		stage.setScene(mainScene);
		setTitleScene();
		stage.show();
	}
	
	private void setTitleScene() 
	{
		Button startButton = new Button("Start Game");
		
		mainScene.setRoot((Parent) Scenes.titleScene(startButton));
		stage.setScene(mainScene);
		stage.setMaximized(true);
		stage.setResizable(true);

		//Properties
		currentPlayer = 0;
		inSetup = false;
		setUpPhase = 0;
		
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
		initializeAdjacentEdges();
		initializeTiles();
		offlineGameScene = new OfflineGameScene(vertexes, edges, players, tileArray);
		mainScene.setRoot(offlineGameScene);
		//initialBuild();
		rollMode();
	}
	
	private void initialBuild() 
	{
		//Incrementing player by snake order
		if(setUpPhase < 7 && setUpPhase % 2 == 0 && setUpPhase != 0) 
		{
			currentPlayer++;
		}
		else if (setUpPhase > 9 && setUpPhase % 2 == 0 && setUpPhase != 16)
		{
			currentPlayer --;
		}
		
		//Loops through the players in snake order to allow initial builds
		//Increment phase after every build, increment currentPlayer after every road built
		//Break once everyone has built two settlements and two roads
		if(setUpPhase > 15) 
		{
			inSetup = false;
			rollMode();
		}
		else if(setUpPhase % 2 == 0) 
		{
			offlineGameScene.requestFirstBuild(currentPlayer, "settlement."); 
			offlineGameScene.enableStartingBuildSettlement();
			setUpPhase++;
		}
		else if (setUpPhase % 2 == 1) 
		{
			offlineGameScene.requestFirstBuild(currentPlayer, "road");
			offlineGameScene.enableBuild(1);
			setUpPhase++; 
		}
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * INITIALIZATION OF GAME COMPONENTS *
	 ************************************************************************************
	 ************************************************************************************/
	
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
							players.get(currentPlayer).buildSettlement(bank, vertexes, v.getGridRow(), v.getGridCol(), inSetup);
						}
						else 
						{
							players.get(currentPlayer).upgradeSettlement(bank, vertexes, v.getGridRow(), v.getGridCol());
						}
						
						//Disable buttons after build
						offlineGameScene.disableBuild();
						Platform.runLater(new Runnable() 
						{
							@Override
							public void run() 
							{
								offlineGameScene.updateGUI(vertexes, edges, players);
																		
								//Call initial build if still setting up, else return to buildMode
								if(inSetup) 
								{
									initialBuild();
								}
								else 
								{
									buildMode();
								}
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
		
		initializeHarbors();
	}
	
	//Reads from a file to determine which vertexes are connected to a harbor
	private void initializeHarbors() 
	{
		Scanner fileIn = null;
		try 
		{
			fileIn = new Scanner(new File("src/res/data/shipVertex.txt"));
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		int lineNum = 1;
		while(fileIn.hasNextLine()) 
		{
			String[] line = fileIn.nextLine().split("\\s+");
			for(int i = 0; i < line.length; i += 2) 
			{
				vertexes[Integer.parseInt(line[i])][Integer.parseInt(line[i+1])].harborCode = lineNum;
			}
			lineNum++;
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
							players.get(currentPlayer).buildRoad(bank, edges, e.getGridRow(), e.getGridCol(), inSetup);
							//Disable after build
							offlineGameScene.disableBuild();
							Platform.runLater(new Runnable() 
							{
								@Override
								public void run() 
								{
									offlineGameScene.updateGUI(vertexes, edges, players);
									//Call initial build if still setting up, else return to buildMode
									if(inSetup) 
									{
										initialBuild();
									}
									else 
									{
										buildMode();
									}
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
	
	private void initializeAdjacentEdges()
	{
		File file = new File("src/res/data/adjacent_edges.txt");
	    try
	    {
	        Scanner sc = new Scanner(file);
	        while (sc.hasNextLine())
	        {
	            String s = sc.nextLine();
	            String[] parsed = s.split("\\s+");
	            
	            //First two numbers are the coordinates of the vertex
	            VertexLink temp = vertexes[Integer.parseInt(parsed[0])][Integer.parseInt(parsed[1])];
	            
	            //Rest of the numbers are the coordinates of the adjacent edges
	            for(int i = 2; i < parsed.length; i += 2)
	            {
	            		int num1 = Integer.parseInt(parsed[i]);
		            	int num2 = Integer.parseInt(parsed[i+1]);
		            	temp.adjacentEdges[i/2 -1] = edges[num1][num2];
	            }
	        }
	        sc.close();
	    } 
	    catch (FileNotFoundException e)
	    {
	        e.printStackTrace();
	    }
	}
	
	private void initializeTiles()
	{
		// 19 Total Tiles
		//Create array with 1 desert, 4 fields, 4 forests, 3 hills, 3 mountains, 4 pastures	
		
		tileArray = new Tile[19];
		
		for(int i = 0;i<19;i++)
		{
			if(i == 0)
				tileArray[i] = new Tile("desert", 0);
			else if(i > 0 && i < 5)
				tileArray[i] = new Tile("grain", 0);
			else if(i >= 5 && i < 9)
				tileArray[i] = new Tile("wood", 0);
			else if(i >= 9 && i < 12)
				tileArray[i] = new Tile("brick", 0);
			else if(i >= 12 && i < 15)
				tileArray[i] = new Tile("ore", 0);
			else
				tileArray[i] = new Tile("wool", 0);
		}
		
		//Shuffling and then setting the numbers
		int[] tileNums = {2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12};
		int count = 0;
		shuffleTiles(tileArray); //Shuffling tiles
		for(int i = 0; i < 19; i++) 
		{			
			if(tileArray[i].type.equals("desert")) 
			{
				tileArray[i].setNumber(7);
			}
			else
			{
				tileArray[i].setNumber(tileNums[count]);
				count++;
			}
		}
		shuffleTiles(tileArray); //Shuffling again for numbers
		setVertexesOnTiles(); //Determining vertexes
		
		//Creating rows of tiles
		Tile[] ar1 = {tileArray[0], tileArray[1], tileArray[2]};
	    rowOne = new TileRow(ar1);
				
	    Tile[] ar2 = {tileArray[3], tileArray[4], tileArray[5], tileArray[6]};
		rowTwo = new TileRow(ar2);
				
		Tile[] ar3 = {tileArray[7], tileArray[8], tileArray[9], tileArray[10], tileArray[11]};
		rowThree = new TileRow(ar3);
				
		Tile[] ar4 = {tileArray[12], tileArray[13], tileArray[14], tileArray[15]};
		rowFour = new TileRow(ar4);
				
		Tile[] ar5 = {tileArray[16], tileArray[17], tileArray[18]};
		rowFive = new TileRow(ar5);
	}
	
	private void setVertexesOnTiles() 
	{
		//Reads from file to determine which vertexes are located on each tile
		Scanner fileIn = null;
		try 
		{
			fileIn = new Scanner(new File("src/res/data/tile_vertexlinks.txt"));
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		//Setting vertexes from file
		for(int i = 0; i < 19; i++) 
		{
			tileArray[i].vertexArray = new VertexLink[6];
			String[] list = fileIn.nextLine().split("\\s+");
			for(int l = 0; l < list.length; l += 2) 
			{
				int row = Integer.parseInt(list[l]);
				int col = Integer.parseInt(list[l+1]);
				tileArray[i].vertexArray[l/2] = vertexes[row][col];
			}
		}
		
		fileIn.close();
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * ROLL AND BUILD *
	 ************************************************************************************
	 ************************************************************************************/
	
	private void rollMode() 
	{
		//Roll Button
		Button rollButton = new Button("Roll Dice");
		
		rollButton.setOnMouseClicked(
				(MouseEvent me) -> {

					roll1 = rollDice();
					roll2 = rollDice();
					
					Button continueButton = new Button("Continue");
					
					if(roll1 + roll2 != 7) 
					{
						continueButton.setOnMouseClicked((MouseEvent e) -> displayResults());
					}
					else if(!players.get(currentPlayer).devList.isEmpty())
					{
						continueButton.setOnMouseClicked((MouseEvent e) -> devCardMode());
					}
					else 
					{
						continueButton.setOnMouseClicked((MouseEvent e) -> tradeModePhase1());
					}
					
					offlineGameScene.showRollResults(roll1, roll2, continueButton);
				}
				);
		offlineGameScene.requestRollDice(currentPlayer, rollButton);
	}
	
	//Returns string including whoever received resources
	private ArrayList<String> distributeResources() 
	{
		int total = roll1 + roll2;
		ArrayList<String> receivedStrings = new ArrayList <String> ();
		for(int i = 0; i < 4; i++) 
		{
			receivedStrings.add(players.get(i).playerName + " received: ");
		}
		
		//Gives resources to players that own a settlement on the tile
		for(Tile tile: tileArray) 
		{
			if(tile.number == total) 
			{
				for(VertexLink v: tile.vertexArray) 
				{
					int levelOfSettlement = v.getHasBuilding();
					if(levelOfSettlement > 0) 
					{
						//Only needs to check getSettlement because the player would be the same for settlements and cities
						Player owner = v.settlement.p;
						bank.giveResource(tile.type, players.get(owner.playerNumber), levelOfSettlement);
						
						receivedStrings.set(owner.playerNumber, receivedStrings.get(owner.playerNumber) + levelOfSettlement + " " + tile.type + " ");
					}
				}
			}
		}
		
		return receivedStrings;
	}
	
	//Displays what each player received from the roll
	private void displayResults() 
	{
		Button continueButton = new Button("Continue");
		if(!players.get(currentPlayer).devList.isEmpty())
			continueButton.setOnMouseClicked((MouseEvent me) -> devCardMode());
		else
			continueButton.setOnMouseClicked((MouseEvent me) -> tradeModePhase1());
		offlineGameScene.displayText(distributeResources(), 12, continueButton);
		offlineGameScene.displayTileNumbers();
	}
	
	private void buildMode() 
	{
		//Build Button mouse events
		Button build1Button = new Button("Road");
		Button build2Button = new Button("Settlement");
		Button build3Button = new Button("Upgrade Settlement");
		Button build4Button = new Button("Development Card");
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
		build4Button.setOnMouseClicked(
				(MouseEvent e) -> {
					players.get(currentPlayer).buildDevelopmentCard(bank);
					buildMode();
					}
				);
		
		//Disable all build buttons and move onto trading
		noButton.setOnMouseClicked(
				(MouseEvent e) -> 
				{
					//End game if someone won
					if(checkWin(players)) 
					{
						gameWonMode();
						return;
					}
					
					offlineGameScene.disableBuild(); 
					//currentPlayer++;
					if(currentPlayer == 4) 
					{
						currentPlayer = 0;
					}
					rollMode();
				});
		offlineGameScene.requestBuild(currentPlayer, build1Button, build2Button, build3Button, build4Button, noButton);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * DEVELOPMENT CARDS *
	 ************************************************************************************
	 ************************************************************************************/
	
	private void devCardMode() 
	{
		Button knightButton = new Button("Knight");
		Button yopButton = new Button("Year of Plenty");
		Button monopolyButton = new Button("Monopoly");
		Button roadBuildButton = new Button("Road Building");
		Button dontPlayButton = new Button("No");
		
		//devQuantities holds the number of each dev cards following the order: knight, yop, monopoly, road building
		ArrayList<Integer> devQuantities = new ArrayList<>();
		for(int i = 0; i < 4; i++) 
		{
			devQuantities.add(0);
		}
		
		ArrayList <Button> availableDevCards = new ArrayList<>();
		
		for(int i = 0; i < players.get(currentPlayer).devList.size(); i++)
		{
			if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Knight")) 
			{
				if(!availableDevCards.contains(knightButton))
					availableDevCards.add(knightButton);
				devQuantities.set(0, devQuantities.get(0) + 1);
				
			}
			else if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Year of Plenty")) 
			{
				if(!availableDevCards.contains(yopButton))
					availableDevCards.add(yopButton);
				devQuantities.set(1, devQuantities.get(1) + 1);
			}
			else if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Monopoly")) 
			{
				if(!availableDevCards.contains(monopolyButton))
					availableDevCards.add(monopolyButton);
				devQuantities.set(2, devQuantities.get(2) + 1);
			}
			else if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Road Building")) 
			{
				if(!availableDevCards.contains(roadBuildButton))
					availableDevCards.add(roadBuildButton);
				devQuantities.set(3, devQuantities.get(3) + 1);
			}
		}
		
		availableDevCards.add(dontPlayButton);
		dontPlayButton.setOnMouseClicked((MouseEvent me) -> tradeModePhase1());
		
		offlineGameScene.requestDevCards(currentPlayer, availableDevCards);
		offlineGameScene.displayDevCards(devQuantities);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * TRADING *
	 ************************************************************************************
	 ************************************************************************************/
	
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
		ArrayList<String> resources = new ArrayList<>();
		ArrayList<String> rNums = new ArrayList<>();
		ArrayList<String> oppResources = new ArrayList<>();
		ArrayList<String> oppRNums = new ArrayList<>();
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
		Button option4Button = new Button("Harbor");
		Button cancelButton = new Button("Cancel");

		offlineGameScene.requestTradePhaseTwo(option1Button, option2Button, option3Button, option4Button, cancelButton);
		option1Button.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase3(otherPlayers[0],resources,rNums,oppResources,oppRNums);
				});
		option2Button.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase3(otherPlayers[1],resources,rNums,oppResources,oppRNums);
				});
		option3Button.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase3(otherPlayers[2],resources,rNums,oppResources,oppRNums);
				});
		option4Button.setOnMouseClicked(
				(MouseEvent me) -> {
					harborTradeModePhase1();
				});
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase1();
				});
	}
	
	private void tradeModePhase3(int otherPlayer, ArrayList<String> resources,ArrayList<String> rNums,ArrayList<String> oppResources,ArrayList<String> oppRNums ) 
	{

		//Phase 3: player 1 chooses what to request
		Button brickButton = new Button("Brick");
		Button grainButton = new Button("Grain");
		Button oreButton = new Button("Ore");
		Button woodButton = new Button("Wood");
		Button woolButton = new Button("Wool");
		Button cancelButton = new Button("Cancel");

		offlineGameScene.requestTradePhaseThree(brickButton, grainButton, oreButton, woodButton, woolButton, cancelButton);
		brickButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource=new String("Brick");
					resources.add(resource);
					tradeModePhase4(otherPlayer, resources,rNums,oppResources,oppRNums);
				});
		grainButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource=new String("Grain");
					resources.add(resource);
					tradeModePhase4(otherPlayer, resources,rNums,oppResources,oppRNums);
				});
		oreButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource=new String("Ore");
					resources.add(resource);
					tradeModePhase4(otherPlayer, resources,rNums,oppResources,oppRNums);
				});
		woodButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource=new String("Wood");
					resources.add(resource);
					tradeModePhase4(otherPlayer, resources,rNums,oppResources,oppRNums);
				});
		woolButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource=new String("Wool");
					resources.add(resource);
					tradeModePhase4(otherPlayer, resources,rNums,oppResources,oppRNums);
				});
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase2();
				});
	}

	private void tradeModePhase4(int otherPlayer,ArrayList<String> resources,ArrayList<String> rNums,ArrayList<String> oppResources,ArrayList<String> oppRNums ) 
	{
		//Phase 4: Player 1 chooses how many of said resource they want to trade
		TextField resourceNum = new TextField();
		
		Button enter = new Button("Enter");
		Button cancelButton = new Button("Cancel");
		
		offlineGameScene.requestTradePhaseFour(cancelButton, resourceNum, enter, resources,resources.get(resources.size()-1));
		enter.setOnMouseClicked(
				(MouseEvent me) -> {
					String rNum=resourceNum.getText();
					rNums.add(rNum);
					tradeModePhase5(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					resources.remove(resources.size()-1);
					tradeModePhase3(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
	}
	
	private void tradeModePhase5(int otherPlayer, ArrayList<String> resources,ArrayList<String> rNums,ArrayList<String> oppResources,ArrayList<String> oppRNums) 
	{
		//Phase 5: Player 1 chooses if they want to trade another resource
		Button yes = new Button("Yes");
		Button no = new Button("No");
		
		offlineGameScene.requestTradePhaseFive(yes, no);
		yes.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase3(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		no.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase6(otherPlayer, resources,rNums,oppResources,oppRNums);
				});
	}
	
	private void tradeModePhase6(int otherPlayer, ArrayList<String> resources,ArrayList<String> rNums,ArrayList<String> oppResources,ArrayList<String> oppRNums) 
	{
		String Player2=new String(players.get(otherPlayer).getName());
		
		//Phase 6: player 2 chooses what to request
		Button brickButton = new Button("Brick");
		Button grainButton = new Button("Grain");
		Button oreButton = new Button("Ore");
		Button woodButton = new Button("Wood");
		Button woolButton = new Button("Wool");
		Button cancelButton = new Button("No");

		offlineGameScene.requestTradePhaseSix(brickButton, grainButton, oreButton, woodButton, woolButton, cancelButton, Player2, resources,rNums);
		brickButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource2=new String("Brick");
					oppResources.add(resource2);
					tradeModePhase7(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		grainButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource2=new String("Grain");
					oppResources.add(resource2);
					tradeModePhase7(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		oreButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource2=new String("Ore");
					oppResources.add(resource2);
					tradeModePhase7(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		woodButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource2=new String("Wood");
					oppResources.add(resource2);
					tradeModePhase7(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		woolButton.setOnMouseClicked(
				(MouseEvent me) -> {
					String resource2=new String("Wool");
					oppResources.add(resource2);
					tradeModePhase7(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase1();
				});
	}
	
	private void tradeModePhase7(int otherPlayer, ArrayList<String> resources,ArrayList<String> rNums, ArrayList<String> oppResources,ArrayList<String> oppRNums)
	{
		//Phase7: player 2 chooses how many of said resource to request
		TextField resourceNum = new TextField();
		
		Button enter = new Button("Enter");
		Button cancelButton = new Button("Cancel");
		
		offlineGameScene.requestTradePhaseSeven(cancelButton, oppResources, enter, oppRNums,resourceNum,oppResources.get(oppResources.size()-1));
		enter.setOnMouseClicked(
				(MouseEvent me) -> {
					String rNum=resourceNum.getText();
					oppRNums.add(rNum);
					tradeModePhase8(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					oppResources.remove(oppResources.size()-1);
					tradeModePhase6(otherPlayer,resources,rNums,oppResources,oppRNums);
				});
	}
	
	private void tradeModePhase8(int otherPlayer,ArrayList<String> resources,ArrayList<String> rNums, ArrayList<String> oppResources,ArrayList<String> oppRNums)
	{
		//Phase 8: Player 2 chooses if they want to trade another resource
				Button yes = new Button("Yes");
				Button no = new Button("No");
				
				offlineGameScene.requestTradePhaseEight(yes, no);
				yes.setOnMouseClicked(
						(MouseEvent me) -> {
							tradeModePhase6(otherPlayer,resources,rNums,oppResources,oppRNums);
						});
				no.setOnMouseClicked(
						(MouseEvent me) -> {
							tradeModePhase9(otherPlayer, resources,rNums,oppResources,oppRNums);
						});
	}
	
	private void tradeModePhase9(int otherPlayer,ArrayList<String> resources,ArrayList<String> rNums, ArrayList<String> oppResources,ArrayList<String> oppRNums)
	{
		//Phase 9: Player 1 chooses if they want to accept player 2's demands
				Button yes = new Button("Yes");
				Button no = new Button("No");

				offlineGameScene.requestTradePhaseNine(yes,no,resources, rNums, oppResources, oppRNums);
				yes.setOnMouseClicked(
						(MouseEvent me) -> {
							//trade work
							trade(currentPlayer,otherPlayer,resources,rNums,oppResources,oppRNums);
							tradeModePhase1();
						});
				no.setOnMouseClicked(
						(MouseEvent me) -> {
							tradeModePhase1();
						});
	}
	
	private void harborTradeModePhase1() 
	{	
		//Using list to make sure that the method doesn't make more than one button for each harbor if the player has multiple settlements near the same harbor
		ArrayList<Integer> availableHarbors = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++) 
		{
			availableHarbors.add(i);
		}
		
		ArrayList <Button> shipOptions = new ArrayList<Button>();
		//Checking if the player has a settlement near a harbor
		for(Settlement settlement: players.get(currentPlayer).settleList) 
		{
			int harborCode = settlement.v.harborCode;
			if(availableHarbors.contains(harborCode)) 
			{
				availableHarbors.remove(Integer.valueOf(harborCode));
				Button button = null;
				
				//Ship options
				switch(harborCode) 
				{
				case 0:
					button = new Button("4:1 Any");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("4 of any"));
					break;
				case 1:
					button = new Button("3:1 Any");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("3 of any"));
					break;
				case 2:
					button = new Button("2:1 Wool");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("wool"));
					break;
				case 3:
					button = new Button("3:1 Any");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("3 of any"));
					break;
				case 4:
					button = new Button("3:1 Any");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("3 of any"));
					break;
				case 5:
					button = new Button("2:1 Brick");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("brick"));
					break;
				case 6:
					button = new Button("2:1 Wood");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("wood"));
					break;
				case 7:
					button = new Button("3:1 Any");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("3 of any"));
					break;
				case 8:
					button = new Button("2:1 Grain");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("grain"));
					break;
				case 9:
					button = new Button("2:1 Ore");
					button.setOnMouseClicked((MouseEvent me) -> harborTradeModePhase2("ore"));
					break;
				}
				
				shipOptions.add(button);
			}
		}
		
		Button backButton = new Button("Back");
		backButton.setOnMouseClicked((MouseEvent me) -> tradeModePhase1());
		
		shipOptions.add(backButton);
		
		offlineGameScene.requestShipTradePhase("Which harbor would you like to trade with?", shipOptions);
	}
	
	//Select what to receive in exchange
	private void harborTradeModePhase2(final String typeToGiveAway) 
	{
		Button brickButton = new Button("Brick");
		Button grainButton = new Button("Grain");
		Button oreButton = new Button("Ore");
		Button woodButton = new Button("Wood");
		Button woolButton = new Button("Wool");
		Button cancelButton = new Button("No");
		final int quantity;
		if(typeToGiveAway.equals("3 of any")) 
		{
			quantity = 3;
		}
		else if(typeToGiveAway.equals("4 of any")) 
		{
			quantity = 4;
		}
		else 
		{
			quantity = 2;
		}

		if(!typeToGiveAway.equals("3 of any") && !typeToGiveAway.equals("4 of any")) 
		{
			brickButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("brick", typeToGiveAway, quantity);
					});
			grainButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("grain", typeToGiveAway, quantity);
					});
			oreButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("ore", typeToGiveAway, quantity);
					});
			woodButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("wood", typeToGiveAway, quantity);
					});
			woolButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("wool", typeToGiveAway, quantity);
					});
		}
		else 
		{
			brickButton.setOnMouseClicked(
					(MouseEvent me) -> {
						harborTradeModePhase3("brick", quantity);
					});
			grainButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("grain", typeToGiveAway, 2);
					});
			oreButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("ore", typeToGiveAway, 2);
					});
			woodButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("wood", typeToGiveAway, 2);
					});
			woolButton.setOnMouseClicked(
					(MouseEvent me) -> {
						tradeWithShip("wool", typeToGiveAway, 2);
					});
		}
		
		cancelButton.setOnMouseClicked(
				(MouseEvent me) -> {
					tradeModePhase1();
				});
		ArrayList <Button> buttons = new ArrayList<Button>();
		buttons.addAll(Arrays.asList(brickButton, grainButton, oreButton, woodButton, woolButton, cancelButton));
		
		offlineGameScene.requestShipTradePhase("What would you like in exchange for " + typeToGiveAway, buttons);
	}
	
	//If 3:1 or 4:1 option chosen, select what give trade away
	private void harborTradeModePhase3(final String typeToReceive, int quantity) 
	{
		
	}
	
	private void tradeWithShip(String resourceToReceive, String resourceToGive, int quantity) 
	{
		//Converting values to a list so that they can be checked with tradeworks
		ArrayList <String> typeList = new ArrayList<>();
		typeList.add(resourceToGive);
		
		ArrayList <Integer> quantList = new ArrayList<>();
		quantList.add(quantity);
		
		if(ResourceCard.tradeWorks(players.get(currentPlayer), typeList, quantList)) 
		{
			bank.takeResource(resourceToGive, players.get(currentPlayer), quantity);
			bank.giveResource(resourceToReceive, players.get(currentPlayer), 1);
		}
		
		tradeModePhase1();
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * OTHER GUI STUFF *
	 ************************************************************************************
	 ************************************************************************************/

	private void gameWonMode() 
	{
		Button continueButton = new Button("Continue");
		continueButton.setOnMouseClicked((MouseEvent me) -> setTitleScene());
		mainScene.setRoot((Parent) Scenes.gameOverScene(continueButton, players.get(currentPlayer).playerName));
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * GAME STUFF *
	 ************************************************************************************
	 ************************************************************************************/
	
	private ArrayList<Player> players = new ArrayList<>();
	private Bank bank;
	
	//Starting Game/Initialization
	public void gameStart(String[] names) 
	{
		for(int i = 0; i < names.length; i++)
		{
			ArrayList<ResourceCard> resList = new ArrayList<>();
			for(int a = 0; a < 20; a++) 
			{
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
	
	private void trade(int firstPlayer, int secondPlayer, ArrayList<String> resources, ArrayList<String> rNums, ArrayList<String> oppResources, ArrayList<String> oppRNums)
	{
		
		Player p1 = players.get(firstPlayer);
		Player p2 = players.get(secondPlayer);
		ArrayList<String> ans1 = new ArrayList<>();
		
		for(int i=0;i<resources.size();i++)
		{
			ans1.add(resources.get(i));
		}
		
		ArrayList<Integer> quantity1 = new ArrayList<>();
		
		for(int i=0;i < rNums.size();i++)
		{
			quantity1.add(Integer.parseInt(rNums.get(i)));
		}
		
		ArrayList<String> ans2 = new ArrayList<>();
		
		for(int i = 0;i < oppResources.size();i++)
		{
			ans2.add(oppResources.get(i));
		}
		
		ArrayList<Integer> quantity2 = new ArrayList<>();
		
		for(int i=0;i < oppRNums.size();i++)
		{
			quantity2.add(Integer.parseInt(oppRNums.get(i)));
		}
		
		if(ResourceCard.tradeWorks(p1, ans1, quantity1) == true && ResourceCard.tradeWorks(p2, ans2, quantity2) == true)
		{			
			for(int a = 0; a < ans1.size(); a++) 
			{
				int numTraded = 0;
				for(int b = 0; b < p2.resList.size(); b++) 
				{
					if(p2.resList.get(b).cardType.equalsIgnoreCase(ans1.get(a)))
					{
						p2.resList.remove(b);
						p1.resList.add(new ResourceCard(ans1.get(a).toLowerCase()));
						numTraded++;
					}
					if(numTraded == quantity1.get(a))
						break;
				}
			}
			
			for(int a = 0; a < ans2.size(); a++) 
			{
				int numTraded = 0;
				for(int b = 0; b < p1.resList.size(); b++) 
				{
					if(p1.resList.get(b).cardType.equalsIgnoreCase(ans2.get(a)))
					{
						p1.resList.remove(b);
						p2.resList.add(new ResourceCard(ans2.get(a).toLowerCase()));
						numTraded++;
					}
					if(numTraded == quantity2.get(a))
						break;
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
	
	private void shuffleTiles(Tile[] array)
	{
		Random rgen = new Random();  // Random number generator			
 
		for (int i=0; i<array.length; i++) {
		    int randomPosition = rgen.nextInt(array.length);
		    Tile temp = array[i];
		    array[i] = array[randomPosition];
		    array[randomPosition] = temp;
		}
	}
}
