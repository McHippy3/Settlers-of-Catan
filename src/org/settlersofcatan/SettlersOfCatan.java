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
	private int currentPlayer, setUpPhase;
	private boolean inSetUp;
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
		inSetUp = true;
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
		initializeTiles();
		initializeVertexes();
		initializeEdges();
		initializeAdjacentEdges();
		offlineGameScene = new OfflineGameScene(vertexes, edges, players, tileArray);
		mainScene.setRoot(offlineGameScene);
		initialBuild();
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
			inSetUp = false;
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
							players.get(currentPlayer).buildSettlement(bank, vertexes, v.getGridRow(), v.getGridCol());
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
								if(inSetUp) 
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
									//Call initial build if still setting up, else return to buildMode
									if(inSetUp) 
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
				tileArray[i] = new Tile("fields", 0);
			else if(i >= 5 && i < 9)
				tileArray[i] = new Tile("forests", 0);
			else if(i >= 9 && i < 12)
				tileArray[i] = new Tile("hills", 0);
			else if(i >= 12 && i < 15)
				tileArray[i] = new Tile("mountains", 0);
			else
				tileArray[i] = new Tile("pastures", 0);
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
					Button continueButton = new Button("Continue");
					continueButton.setOnMouseClicked((MouseEvent e) -> devCardMode());
					
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
					currentPlayer++;
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
		
		ArrayList <Button> availableDevCards = new ArrayList<>();
		
		for(int i = 0; i < players.get(currentPlayer).devList.size(); i++)
		{
			if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Knight")) 
			{
				if(!availableDevCards.contains(knightButton))
					availableDevCards.add(knightButton);
			}
			else if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Year of Plenty")) 
			{
				if(!availableDevCards.contains(yopButton))
					availableDevCards.add(yopButton);
			}
			else if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Monopoly")) 
			{
				if(!availableDevCards.contains(monopolyButton))
					availableDevCards.add(monopolyButton);
			}
			else if(players.get(currentPlayer).devList.get(i).getType().equalsIgnoreCase("Road Building")) 
			{
				if(!availableDevCards.contains(roadBuildButton))
					availableDevCards.add(roadBuildButton);
			}
		}
		availableDevCards.add(dontPlayButton);
		dontPlayButton.setOnMouseClicked((MouseEvent me) -> tradeModePhase1());
		
		offlineGameScene.requestDevCards(currentPlayer, availableDevCards);
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
		Button cancelButton = new Button("Cancel");

		offlineGameScene.requestTradePhaseTwo(currentPlayer, option1Button, option2Button, option3Button, cancelButton);
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

		offlineGameScene.requestTradePhaseThree(currentPlayer, brickButton, grainButton, oreButton, woodButton, woolButton, cancelButton);
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
		
		offlineGameScene.requestTradePhaseFour(currentPlayer, cancelButton, resourceNum, enter, resources,resources.get(resources.size()-1));
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
		
		offlineGameScene.requestTradePhaseFive(currentPlayer, yes, no);
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

				offlineGameScene.requestTradePhaseSix(currentPlayer, brickButton, grainButton, oreButton, woodButton, woolButton, cancelButton, Player2, resources,rNums);
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
		
		offlineGameScene.requestTradePhaseSeven(currentPlayer, cancelButton, oppResources, enter, oppRNums,resourceNum,oppResources.get(oppResources.size()-1));
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
				
				offlineGameScene.requestTradePhaseEight(currentPlayer, yes, no);
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

				offlineGameScene.requestTradePhaseNine(currentPlayer, yes,no,resources, rNums, oppResources, oppRNums);
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
		
		for(int i=0;i<rNums.size();i++)
		{
			quantity1.add(Integer.parseInt(rNums.get(i)));
		}
		
		ArrayList<String> ans2 = new ArrayList<>();
		
		for(int i=0;i<oppResources.size();i++)
		{
			ans2.add(oppResources.get(i));
		}
		
		ArrayList<Integer> quantity2 = new ArrayList<>();
		
		for(int i=0;i<oppRNums.size();i++)
		{
			quantity2.add(Integer.parseInt(oppRNums.get(i)));
		}
		

		
		if(ResourceCard.tradeWorks(p1, ans1, quantity1) == true && ResourceCard.tradeWorks(p2, ans2, quantity2) == true)
		{
			int cap1 = 0;
			int cap2 = 0;
			for(int x = 0;x<p2.resList.size();x++)
			{
				for(int i=0;i < ans1.size();i++)
				{
					if(p2.resList.get(x).cardType.equalsIgnoreCase(ans1.get(i)) && cap1 <= quantity1.get(i))
					{
						p2.resList.remove(x);
						p1.resList.add(new ResourceCard(ans1.get(i).toLowerCase()));
						cap1 += 1;
					}
				}
			}
			
			for(int x = 0;x<p1.resList.size();x++)
			{
				for(int i=0;i<ans2.size();i++)
				{
					if(p1.resList.get(x).cardType.equalsIgnoreCase(ans2.get(i)) && cap2 <= quantity2.get(i))
					{
						p1.resList.remove(x);
						p2.resList.add(new ResourceCard(ans2.get(i).toLowerCase()));
						cap2 += 1;
					}
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
