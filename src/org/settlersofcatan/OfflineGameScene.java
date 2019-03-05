package org.settlersofcatan;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class OfflineGameScene extends StackPane
{
	private VertexLink vertexes[][];
	private EdgeLink edges[][];
	private Tile tileArray[];
	private double xOffSet, yOffSet, sf;
	private int currentPlayer;
	private Group gameTiles, resourceImages;
	private BorderPane borderPane;
	private VBox center, leftBox, rightBox;
	private HBox commandPanel;
	private GridPane[] playerTiles;
	private ArrayList<Player> players;
	
	/************************************************************************************
	 ************************************************************************************
	 * INITIALIZE GUI *
	 ************************************************************************************
	 ************************************************************************************/
	
	public OfflineGameScene(VertexLink[][] vertexes, EdgeLink[][] edges, ArrayList<Player> players, Tile[] tileArray) 
	{		
		//Initializing StackPane
		super();
		
		//Numeric properties
		sf = 0.60;
        xOffSet = 65 * sf;
        yOffSet = 125 * sf;
        currentPlayer = 0;
        
        //Copying matrices
        this.vertexes = vertexes;
        this.edges = edges;
        
        //Getting Players
        this.players = players;
        
        //ETC
        this.tileArray = tileArray;
                
		//Creating GUI and initializing
		initializeGUI();
		updateGUI(vertexes, edges, players);
	}
	
	private void initializeGUI() 
	{
		//StackPane Layer 1: Ocean
		Group oceanLayer = new Group();
		for(int r = 0; r < 10; r ++) 
		{
			int yPos = r * 253;
			for(int c = 0; c < 10; c++) 
			{		
				ImageView oceanPic = new ImageView(new Image("res/ocean.png"));
				oceanLayer.getChildren().add(oceanPic);
				oceanPic.setX(c * 199);
				oceanPic.setY(yPos);
			}
		}
		getChildren().add(oceanLayer);
		
		//StackPane Layer 2: BorderPane
		borderPane = new BorderPane();

		//leftBox 
		leftBox = new VBox();
		leftBox.setPadding(new Insets(25));
		leftBox.setSpacing(25);
		
		//rightBox
		rightBox = new VBox();
		rightBox.setPadding(new Insets(25));
		rightBox.setSpacing(25);
		
		//Player Tiles
        playerTiles = new GridPane[4];
        for(int i = 0; i < playerTiles.length; i++)
        {
            playerTiles[i] = new GridPane();
            playerTiles[i].setAlignment(Pos.CENTER);
            playerTiles[i].setPrefSize(252, 500);
            playerTiles[i].setPadding(new Insets((10)));
            playerTiles[i].setVgap(5);
            playerTiles[i].setHgap(5);
            playerTiles[i].add(new Text(players.get(i).getName()), 0, 0, 2, 1);
            playerTiles[i].add(new Text("Victory Points: " + players.get(i).getVP()), 0, 1, 1, 1);

            //Setting Color
            BackgroundFill backgroundFill;
            switch(i)
            {
                case 0:
                    backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                case 1:
                    backgroundFill = new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                case 2:
                    backgroundFill = new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
                default:
                    backgroundFill = new BackgroundFill(Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY);
                    break;
            }
            playerTiles[i].setBackground(new Background(backgroundFill));
        }
        
        leftBox.getChildren().addAll(playerTiles[0], playerTiles[1]);
        rightBox.getChildren().addAll(playerTiles[2], playerTiles[3]);
        
        /* Center */ 
        center = new VBox();
        center.setSpacing(50);
        center.setAlignment(Pos.CENTER);
        
        // Game Tiles
        gameTiles = new Group();
        int numOfColumns = 3;
        
        //Rectangle to establish size
        Rectangle rect = new Rectangle(0,0, 1200 * sf, 1200 * sf);
        rect.setFill(Color.TRANSPARENT);
        gameTiles.getChildren().add(rect);
        
        int count = 0;
        for(int r = 0; r < 5; r++) 
        {
        	for(int c = 0; c < numOfColumns; c++) 
        	{
        		String picURL = "";
        		switch(tileArray[count].type) 
        		{
        		case "desert": 
        			picURL = "res/tile_photos/desert.png"; 
        			break;
        		case "wool":
        			picURL = "res/tile_photos/pasture.png"; 
        			break;
        		case "wood": 
        			picURL = "res/tile_photos/forest.png";
        			break;
        		case "brick": 
        			picURL = "res/tile_photos/hills.png"; 
        			break;
        		case "ore": 
        			picURL = "res/tile_photos/mountains.png"; 
        			break;
        		default: 
        			picURL = "res/tile_photos/fields.png"; 
        			break;
        		}
        		
        		ImageView img = new ImageView(new Image(picURL));
        		img.setFitHeight(210 * sf);
        		img.setFitWidth(240 * sf);
        		img.setRotate(270);
        		int x = 205 * c - (105 * r % 2);
        		
        		
        		//Shift right to center
        		if(r != 2) 
        		{
        			x += 105;
        		}
        		
        		if(r == 0 || r == 4) 
        		{
        			x += 105;
        		}
        		
        		img.setX((int) (x * sf) + xOffSet);
        		img.setY(180 * r * sf + yOffSet);
        		tileArray[count].setLayoutX((int) (x * sf) + xOffSet);
        		tileArray[count].setLayoutY(180 * r * sf + yOffSet);        		
        		gameTiles.getChildren().addAll(img, tileArray[count]);
        		count++;
        	}
        	
        	if(r < 2) 
        	{
        		numOfColumns++;
        	}
        	else numOfColumns--;
        }
        
        //Ships/Harbors
        String shipImages[] = 
        	{
        		"res/ships/brick_ship.png",
        		"res/ships/log_ship.png",
        		"res/ships/rock_ship.png",
        		"res/ships/sheep_ship.png",
        		"res/ships/wheat_ship.png",
        		"res/ships/ship.png",
        		"res/ships/ship.png",
        		"res/ships/ship.png",
        		"res/ships/ship.png"
        	};
        double x[] = {950, 660, 80, 650, 80, 250, 250, 950, 1150};
        double y[] = {875, 1050, 350, 10, 725, 10, 1050, 170, 530};
        double rotation[] = {335, 330, 130, 200, 70, 150, 30, 200, 270};
        for(int i = 0; i < 9; i++) 
        {
        	ImageView img = new ImageView(new Image(shipImages[i]));
        	img.setFitHeight(100 * sf);
        	img.setFitWidth(74 * sf);
        	img.setX(x[i] * sf);
        	img.setY(y[i] * sf);
        	img.setRotate(rotation[i]);
        	gameTiles.getChildren().add(img);
        }
        
        //Settlements + Vertexes
        y = new double[] {0, 60, 180, 240, 360, 420, 540, 600, 720, 780, 900, 960, 1080, 1140, 1260};
        for(int r = 0; r < 12; r++) 
        {
        	for(int c = 0; c < 11; c++) 
			{
				if(vertexes[r][c] != null) 
				{
					vertexes[r][c].setLayoutX(xOffSet + c * 105 * sf - 15);
			        vertexes[r][c].setLayoutY(yOffSet + y[r] * sf - 25);
			        gameTiles.getChildren().add(vertexes[r][c]);
				}
        	}
        }
        
        //Edge Buttons
        y = new double[] { 15, 120, 200, 300, 380, 480, 560, 660, 740, 840, 910, 1040, 100, 1210};
        for(int r = 0; r < 11; r++)
        {
        	for(int c = 0; c < 11; c++) 
        	{        		
        		if(edges[r][c] != null && !edges[r][c].getHasRoad()) 
        		{
	        		edges[r][c].setLayoutX((r%2)*30+ (xOffSet/2) + c * 104 * sf - 20);
					edges[r][c].setLayoutY(yOffSet + y[r] * sf - 20);
					gameTiles.getChildren().add(edges[r][c]);
        		}
        	}
        }
        
        //Command Panel
        commandPanel = new HBox();
        commandPanel.setAlignment(Pos.CENTER);
        commandPanel.setMinSize(1300, 200);
        commandPanel.setMaxSize(1300, 200);
        commandPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        commandPanel.setBorder(new Border
        		(new BorderStroke
        				(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))
        		));
        
        center.getChildren().addAll(gameTiles, commandPanel);
        
        //Adding
        borderPane.setLeft(leftBox);
		borderPane.setRight(rightBox);
		borderPane.setCenter(center);
		
		getChildren().add(borderPane);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * UPDATE METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void updateGUI(VertexLink[][] vertexes, EdgeLink[][] edges, ArrayList<Player> players) 
	{
		//Get most recent version of the game state
		this.vertexes = vertexes;
		this.edges = edges;
		this.players = players;
		
		//Player Tiles
		updatePlayerTiles();
		
        //Settlements + Vertexes
        updateBuildings();
        
		//Roads
        updateRoads();
        
        //Robber
        updateRobber();
        
        //Command Panel Elements/Nodes
        updateCommandPanel();
	}
	
	//Update GUI helper
	private void updatePlayerTiles() 
	{
		for(int i = 0; i < 4; i++) 
		{
			//Removing old labels
			while(playerTiles[i].getChildren().size() != 1) 
			{
				playerTiles[i].getChildren().remove(1);
			}
			
			//Updating victory points Text
			Text updatedPlayerText = new Text("Victory Points: " + players.get(i).getVP());
			
			//Hidden VP from development cards
			players.get(i).actualVP = players.get(i).victoryPoints + players.get(i).secretVP;
			if(i == currentPlayer) 
			{
				updatedPlayerText.setText(updatedPlayerText.getText() + " ("+players.get(i).actualVP + ")");
			}
			
			playerTiles[i].add(updatedPlayerText, 0, 1);
			
			//Bonus Point Awards
			if(players.get(i).hasLongestRoad) 
			{
				Text longestRoadText = new Text("Has Longest Road");
				playerTiles[i].add(longestRoadText, 0, 2);
			}
			if(players.get(i).hasLargestArmy) 
			{
				Text largestArmyText = new Text("Has Largest Army");
				playerTiles[i].add(largestArmyText, 0, 3);
			}
			
			//Distinguish current player
			if(currentPlayer == i) 
            {
				playerTiles[i].setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(10))));
            }
			else 
			{
				playerTiles[i].setBorder(null);
			}
		}
	}
	
	//Update Robber
	private void updateRobber() 
	{
		int numOfColumns = 3;
		int count = 0;
        for(int r = 0; r < 5; r++) 
        {
        	for(int c = 0; c < numOfColumns; c++) 
        	{
        		if(tileArray[count].hasRobber) 
        		{
	        		ImageView robberImg = new ImageView(new Image("res/other/robber.png"));
	        		robberImg.setFitHeight(90);
	        		robberImg.setFitWidth(36);
	        		
	        		int x = 205 * c - (105 * r % 2);
	        		
	        		//Shift right to center
	        		if(r != 2) 
	        		{
	        			x += 105;
	        		}
	        		
	        		if(r == 0 || r == 4) 
	        		{
	        			x += 105;
	        		}
	        		
	        		robberImg.setX((int) (x * sf) + xOffSet + 53);
	        		robberImg.setY(180 * r * sf + yOffSet + 20);
	        		
	        		//Adding at index 20 so that updateBuildings() will remove it
	        		gameTiles.getChildren().add(40, robberImg);
        		}
        		
        		count++;
        	}
        	
    		

    		
        	if(r < 2) 
        	{
        		numOfColumns++;
        	}
        	else numOfColumns--;
        }
	}
	
	//UpdateGUI helper
	private void updateRoads() 
	{
		//URLs for the road images
		String[] roadURL = {"res/roads/blue_road.png", "res/roads/red_road.png", "res/roads/white_road.png", "res/roads/orange_road.png"};
		
        double[] y = new double[] { 15, 120, 200, 300, 380, 480, 560, 660, 740, 840, 910, 1040, 100, 1210};
        for(int r = 0; r < 11; r++) 
        {
        	for(int c = 0; c < 11; c++) 
			{
        		if(edges[r][c] != null && edges[r][c].getHasRoad()) 
        		{
					ImageView roadImg = new ImageView(new Image(roadURL[edges[r][c].road.player.playerNumber]));
			        roadImg.setFitHeight(48);
			        roadImg.setFitWidth(12);
			        roadImg.setX((((edges[r][c].getGridRow()%2)*30)+ (xOffSet/2) + edges[r][c].getGridCol() * 104 * sf)-10);
			        roadImg.setY(( yOffSet + y[edges[r][c].getGridRow()] * sf)-25);
			        
			        //Determine whether road is slanted, on even row, odd row, etc.
			        if (edges[r][c].getGridRow()%2==0)
			        {
			        	if (edges[r][c].getGridRow()%4==0)
			        	{	
			        		if (edges[r][c].getGridCol()%2==1)
			        			roadImg.setRotate(60);
			        		if (edges[r][c].getGridCol()%2==0)
			        			roadImg.setRotate(300);
			        	}
			        	if (edges[r][c].getGridRow()%4==2)
			        	{	
			        		if (edges[r][c].getGridCol()%2==1)
			        			roadImg.setRotate(300);
			        		if (edges[r][c].getGridCol()%2==0)
			        			roadImg.setRotate(60);
			        	}
			        }
			        else
			        {
			              roadImg.setRotate(0);
			        }
			        gameTiles.getChildren().add(40, roadImg);
        		}
        	}
        }
	}
	
	//Update GUI helper
	private void updateBuildings() 
	{
		//Removing old settlement and city images
		while(gameTiles.getChildren().size() != 174) 
		{
			gameTiles.getChildren().remove(40);
		}
		
		double[] y = new double[] {0, 60, 180, 240, 360, 420, 540, 600, 720, 780, 900, 960, 1080, 1140, 1260};
        String[][] buildingURL = 
        	{
        		{"res/settlements/blue_settlement.png", "res/cities/blue_city.png"},
        		{"res/settlements/red_settlement.png", "res/cities/red_city.png"},
        		{"res/settlements/white_settlement.png", "res/cities/white_city.png"},
        		{"res/settlements/orange_settlement.png", "res/cities/orange_city.png"}
    		};
        
		for(int r = 0; r < 12; r++) 
        {
        	for(int c = 0; c < 11; c++) 
			{
        		//Settlements
				if(vertexes[r][c] != null && vertexes[r][c].getHasBuilding() == 1) 
				{
			        ImageView settlementImg = new ImageView(
			        		new Image(buildingURL[vertexes[r][c].settlement.p.playerNumber][0]));
			        settlementImg.setX(xOffSet + c * 105 * sf - 15);
			        settlementImg.setY(yOffSet + y[r] * sf - 25);
			        settlementImg.setFitHeight(30);
			        settlementImg.setFitWidth(30);
			        //Place images at index 20 to be on top of tiles but below buttons
			        gameTiles.getChildren().add(40, settlementImg);
				}
				//Cities
				else if(vertexes[r][c] != null && vertexes[r][c].getHasBuilding() == 2) 
				{
					//getSettlement() can be used as buildingURL simply needs the player number 
			        ImageView cityImg = new ImageView(
			        		new Image(buildingURL[vertexes[r][c].settlement.p.playerNumber][1]));
			        cityImg.setX(xOffSet + c * 105 * sf - 15);
			        cityImg.setY(yOffSet + y[r] * sf - 25);
			        cityImg.setFitHeight(30);
			        cityImg.setFitWidth(30);
			        //Place images at index 20 to be on top of tiles but below buttons
			        gameTiles.getChildren().add(40, cityImg);
				}
        	}
        }
	}
	
	//Update GUI helper
	private void updateCommandPanel() 
	{
		//Removing all nodes from the command panel so that they don't stack
        commandPanel.getChildren().clear();
        
        //Drawing Resource Cards
        resourceImages = new Group();
        String[] resourceImageLoc = {"res/resource_cards/brick.jpg", "res/resource_cards/grain.jpg", "res/resource_cards/ore.jpg", "res/resource_cards/wood.jpg", "res/resource_cards/wool.jpg"};
        for(int i = 0; i < 5; i++) 
        {
        	ImageView cardImg = new ImageView(resourceImageLoc[i]);
        	cardImg.setX(650 + 130 * i);
        	cardImg.setY(23);
        	cardImg.setFitHeight(154);
        	cardImg.setFitWidth(100);
        	
        	resourceImages.getChildren().add(cardImg);
        }
        
        //Placing Numbers Beside Resource Cards
        for(int i = 0; i < 5; i++) 
        {
        	Text t = new Text(725 + i * 130, 170, "" + players.get(currentPlayer).listInventory()[i]);
        	t.setFont(Font.font("Arial", 20));
        	t.setFill(Color.WHITE);
        	t.setStroke(Color.WHITE);
        	t.setStrokeWidth(1);
        	resourceImages.getChildren().add(t);
        }
        commandPanel.getChildren().add(resourceImages);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * STARTING BUILD METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void requestFirstBuild(int currentPlayer, String toBuild) 
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);

		Text suggestionText = new Text(players.get(currentPlayer).playerName + " select one spot to build a " + toBuild);
		suggestionText.setFont(Font.font("Verdana", 15));
		//HBox to center text
		HBox container = new HBox(suggestionText);
		container.setPrefWidth(650);
		container.setAlignment(Pos.CENTER);
		
		commandPanel.getChildren().add(0, container);
	}
	
	//Similar to enable build but does not prevent building settlement 
	//without a road and does not allow upgrading
	public void enableStartingBuildSettlement() 
	{
		for(VertexLink[] r: vertexes) 
		{
			for(VertexLink v: r) 
			{
				if(v != null && v.getHasBuilding() == 0) 
				{
					v.setDisable(false);
					
					//Prevent building directly beside another settlement while starting the game
					for(int i = 0; i < 3; i++) 
					{
						if(v.adjacentEdges[i] != null && v.adjacentEdges[i].getHasRoad()) 
						{
							v.setDisable(true);
							break;
						}
						else if (v.adjacentEdges[i] != null && !checkForNearBuildings(v, v.adjacentEdges[i])) 
						{
							v.setDisable(true);
							break;
						}
					}
				}
			}
		}
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * ROLL METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void requestRollDice(int currentPlayer, Button rollButton) 
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Display tile numbers
		displayTileNumbers();
		
		//Build Options
    	GridPane rollOptions = new GridPane();
    	rollOptions.setPrefWidth(650);
    	rollOptions.setAlignment(Pos.CENTER);
    	rollOptions.setVgap(25.0);
    	rollOptions.setHgap(25.0);
    	rollOptions.setPadding(new Insets(10));
		
		//Build Buttons
		rollButton.setPrefWidth(150);
		rollOptions.add(rollButton, 0, 0);
		
		commandPanel.getChildren().add(0, rollOptions);
	}
	
	public void showRollResults(int roll1, int roll2, Button continueButton) 
	{
		//Clearing past items
		updateGUI(vertexes, edges, players);
		
		//Display tile numbers
		displayTileNumbers();
		
		//Container
		VBox rollResults = new VBox();
		rollResults.setAlignment(Pos.CENTER);
		rollResults.setSpacing(25.0);
		rollResults.setPrefWidth(650);
		
		//Dice images
		HBox diceImages = new HBox();
		diceImages.setAlignment(Pos.CENTER);
		diceImages.setSpacing(25.0);
		ImageView roll1Result;
		ImageView roll2Result;
		
		switch(roll1) 
		{
			case 1: roll1Result = new ImageView(new Image("res/dice/one.png")); break;
			case 2: roll1Result = new ImageView(new Image("res/dice/two.png")); break;
			case 3: roll1Result = new ImageView(new Image("res/dice/three.png")); break;
			case 4: roll1Result = new ImageView(new Image("res/dice/four.png")); break;
			case 5: roll1Result = new ImageView(new Image("res/dice/five.png")); break;
			default: roll1Result = new ImageView(new Image("res/dice/six.png")); break;
		}
		
		switch(roll2) 
		{
			case 1: roll2Result = new ImageView(new Image("res/dice/one.png")); break;
			case 2: roll2Result = new ImageView(new Image("res/dice/two.png")); break;
			case 3: roll2Result = new ImageView(new Image("res/dice/three.png")); break;
			case 4: roll2Result = new ImageView(new Image("res/dice/four.png")); break;
			case 5: roll2Result = new ImageView(new Image("res/dice/five.png")); break;
			default: roll2Result = new ImageView(new Image("res/dice/six.png")); break;
		}
		roll1Result.setFitWidth(50);
		roll1Result.setFitHeight(50);
		
		roll2Result.setFitWidth(50);
		roll2Result.setFitHeight(50);
		
		diceImages.getChildren().addAll(roll1Result, roll2Result);
		
		Text rollTotal = new Text("Total: " + (roll1 + roll2));
		continueButton.setPrefWidth(150);
		
		rollResults.getChildren().addAll(diceImages, rollTotal, continueButton);
		
		commandPanel.getChildren().add(0, rollResults);
	}

	public void displayTileNumbers() 
	{
		int numOfColumns = 3;
		int count = 0;
        for(int r = 0; r < 5; r++) 
        {
        	for(int c = 0; c < numOfColumns; c++) 
        	{
        		Text tileNum = new Text(Integer.toString(tileArray[count].number));
        		tileNum.setFont(Font.font(35));
        		tileNum.setFill(Color.WHITE);
        		int x = 205 * c - (105 * r % 2);
        		
        		//Shift right to center
        		if(r != 2) 
        		{
        			x += 105;
        		}
        		
        		if(r == 0 || r == 4) 
        		{
        			x += 105;
        		}
        		
        		tileNum.setX((int) (x * sf) + xOffSet + 53);
        		tileNum.setY(180 * r * sf + yOffSet + 72);
        		//Adding at index 20 so that updateBuildings() will remove it
        		gameTiles.getChildren().add(40, tileNum);
        		count++;
        	}
        	
        	if(r < 2) 
        	{
        		numOfColumns++;
        	}
        	else numOfColumns--;
        }
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * DEVELOPMENT CARD METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void requestDevCards(int currentPlayer, ArrayList<Button> devButtons)
	{
		this.currentPlayer = currentPlayer;
		
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		commandPanel.getChildren().clear();
		
		GridPane devCardOptions = new GridPane();
    	devCardOptions.setPrefWidth(650);
    	devCardOptions.setAlignment(Pos.CENTER);
    	devCardOptions.setVgap(25.0);
    	devCardOptions.setHgap(25.0);
    	devCardOptions.setPadding(new Insets(10));
    	
		Text devCardLabel = new Text("Would you like to play a dev card?");
		devCardOptions.add(devCardLabel, 0, 0, 2, 2);
		
		//Integers to manage rows and columns of the buttons
		int row = 0;
		int col = 2;
		for(Button button: devButtons) 
		{
			button.setPrefWidth(150);
			devCardOptions.add(button, col, row);
			
			if(row < 2) 
			{
				row++;
			}
			else 
			{
				row = 0;
				col++;
			}
		}
		
		commandPanel.getChildren().add(0, devCardOptions);
	}
	
	public void displayDevCards(ArrayList<Integer> developmentQuantities) 
	{
		
		//Drawing Development Cards
        Group devImages = new Group();
        
        //Adding rectangle so that the node will be 650 x 200
        Rectangle r = new Rectangle(650, 200);
        r.setFill(Color.TRANSPARENT);
        devImages.getChildren().add(r);
        
        String[] devCardLoc = {"res/dev_card/knight.png", "res/dev_card/yop.png", "res/dev_card/monopoly.png", "res/dev_card/roadBuild.png"};
        for(int i = 0; i < 4; i++) 
        {
        	ImageView cardImg = new ImageView(devCardLoc[i]);
        	cardImg.setX(140 * i);
        	cardImg.setFitHeight(200);
        	cardImg.setFitWidth(123);
        	
        	devImages.getChildren().add(cardImg);
        }
        
        //Placing Numbers Beside Resource Cards
        for(int i = 0; i < 4; i++) 
        {
        	Text t = new Text(100 + i * 140, 180, "" + developmentQuantities.get(i));
        	t.setFont(Font.font("Arial", 20));
        	t.setFill(Color.WHITE);
        	t.setStroke(Color.WHITE);
        	t.setStrokeWidth(1);
        	devImages.getChildren().add(t);
        }
        commandPanel.getChildren().add(devImages);
	}
	
	public void yopMode(int currentPlayer, Button brickButton, Button grainButton, Button oreButton, Button woodButton, Button woolButton, Button cancelButton)
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane yopOptions = new GridPane();
    	yopOptions.setPrefWidth(650);
    	yopOptions.setAlignment(Pos.CENTER);
    	yopOptions.setVgap(25.0);
    	yopOptions.setHgap(25.0);
    	yopOptions.setPadding(new Insets(10));
		Text yopLabel = new Text("What resource would you like?");
		yopOptions.add(yopLabel, 0, 0, 2, 2);
		
		//resource Buttons
		brickButton.setPrefWidth(150);
		yopOptions.add(brickButton, 2, 0);
		
		grainButton.setPrefWidth(150);
		yopOptions.add(grainButton, 3, 0);
		
		oreButton.setPrefWidth(150);
		yopOptions.add(oreButton, 2, 1);

		woodButton.setPrefWidth(150);
		yopOptions.add(woodButton, 3, 1);
		
		woolButton.setPrefWidth(150);
		yopOptions.add(woolButton, 2, 2);
		
		cancelButton.setPrefWidth(150);
		yopOptions.add(cancelButton, 3, 2);
		    		
		commandPanel.getChildren().add(0, yopOptions);
	}
	
	public void monopolyMode(int currentPlayer, Button brickButton, Button grainButton, Button oreButton, Button woodButton, Button woolButton, Button cancelButton)
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane monopolyOptions = new GridPane();
    	monopolyOptions.setPrefWidth(650);
    	monopolyOptions.setAlignment(Pos.CENTER);
    	monopolyOptions.setVgap(25.0);
    	monopolyOptions.setHgap(25.0);
    	monopolyOptions.setPadding(new Insets(10));
		Text monopolyLabel = new Text("What resource would you like a monopoly of?");
		monopolyOptions.add(monopolyLabel, 0, 0, 2, 2);
		
		//resource Buttons
		brickButton.setPrefWidth(150);
		monopolyOptions.add(brickButton, 2, 0);
		
		grainButton.setPrefWidth(150);
		monopolyOptions.add(grainButton, 3, 0);
		
		oreButton.setPrefWidth(150);
		monopolyOptions.add(oreButton, 2, 1);

		woodButton.setPrefWidth(150);
		monopolyOptions.add(woodButton, 3, 1);
		
		woolButton.setPrefWidth(150);
		monopolyOptions.add(woolButton, 2, 2);
		
		cancelButton.setPrefWidth(150);
		monopolyOptions.add(cancelButton, 3, 2);
		    		
		commandPanel.getChildren().add(0, monopolyOptions);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * ROBBER METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void requestMoveRobber() 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		Text suggestionText = new Text("Select tile to move robber to");
		
		HBox container = new HBox(suggestionText);
		container.setPrefWidth(650);
		container.setAlignment(Pos.CENTER);
		
		commandPanel.getChildren().add(0, container);
	}
	
	public void requestStealResource(ArrayList<Button> buttons) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane stealOptions = new GridPane();
    	stealOptions.setPrefWidth(650);
    	stealOptions.setAlignment(Pos.CENTER);
    	stealOptions.setVgap(25.0);
    	stealOptions.setHgap(25.0);
    	stealOptions.setPadding(new Insets(10));
		Text stealLabel = new Text("Who would you like to steal from?");
		stealOptions.add(stealLabel, 0, 0, 2, 2);
		
		//Integers to manage rows and columns of the buttons
		int row = 0;
		int col = 2;
		for(Button button: buttons) 
		{
			button.setPrefWidth(150);
			stealOptions.add(button, col, row);
			
			if(row < 2) 
			{
				row++;
			}
			else 
			{
				row = 0;
				col++;
			}
		}
		
		commandPanel.getChildren().add(0, stealOptions);
	}

	public void enableTileSelection() 
	{
		for(Tile t: tileArray) 
		{
			if(!t.hasRobber)
				t.setDisable(false);
		}
	}
	
	public void disableTileSelection() 
	{
		for(Tile t: tileArray) 
		{
			t.setDisable(true);
		}
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * BUILD METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void enableBuild(int buildCode) 
	{
		/*Allow build option according to a code
		 * 1. New Road
		 * 2. New Settlement
		 * 3. Upgrade Settlement to City
		 */
		
		for(EdgeLink[] r: edges) 
		{
			for(EdgeLink e: r) 
			{
				if(e != null && !e.getHasRoad() && buildCode == 1) 
				{
					if(edgeValidCheck(e)) 
					{
						e.setDisable(false);
					}
				}
			}
		}
		
		for(VertexLink[] r: vertexes) 
		{
			for(VertexLink v: r) 
			{
				if(v != null && v.getHasBuilding() == 0 && buildCode == 2) 
				{
					//Making sure it is connected to a road
					for(int i = 0; i < 3; i++) 
					{
						if(vertexValidCheck(v)) 
						{
							v.setDisable(false);
						}
					}
				}
				else if(v != null && v.getHasBuilding() > 0 && buildCode == 3) 
				{
					if(v.settlement.p.playerNumber == currentPlayer)
						v.setDisable(false);
				}
			}
		}
	}
	
	private boolean edgeValidCheck(EdgeLink e) 
	{
		VertexLink start = vertexes[e.getMainGridRowStart()][e.getMainGridColStart()];
		VertexLink end = vertexes[e.getMainGridRowEnd()][e.getMainGridColEnd()];
		
		//Checking if connected to a building
		if((start.getHasBuilding() > 0 
				&& start.settlement.p.playerNumber == currentPlayer)
				|| (end.getHasBuilding() > 0
				&& end.settlement.p.playerNumber == currentPlayer)) 
		{
			return true;
		}
		
		//Checking if connected to a road that belongs to the currentPlayer
		for(int i = 0; i < 3; i++) 
		{
			if(start.adjacentEdges[i] != null 
					&& start.adjacentEdges[i].getHasRoad()
					&& start.adjacentEdges[i].road.player.playerNumber == currentPlayer) 
			{
				return true;
			}
			
			if(end.adjacentEdges[i] != null 
					&& end.adjacentEdges[i].getHasRoad()
					&& end.adjacentEdges[i].road.player.playerNumber == currentPlayer) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean vertexValidCheck(VertexLink v) 
	{
		boolean valid = false;
		for(int i = 0; i < 3; i++) 
		{
			//Checking to make sure it is connected to a road
			EdgeLink temp = v.adjacentEdges[i];
			if(temp != null 
					&& temp.getHasRoad() 
					&& temp.road.player.playerNumber == currentPlayer) 
			{
				valid = true;
				if(!checkForNearBuildings(v, temp))
					valid = false;
			}
		}
		
		return valid;
	}
	
	//Separate method from the one above since it is needed for initial build as well
	private boolean checkForNearBuildings(VertexLink v, EdgeLink edge) 
	{
		//Making sure it isn't beside another settlement
		int row = v.getGridRow();
		int col = v.getGridCol();
		
		if(edge.getMainGridRowStart() == row && edge.getMainGridColStart() == col)
			if(vertexes[edge.getMainGridRowEnd()][edge.getMainGridColEnd()].getHasBuilding() == 0)
				return true;
		if(edge.getMainGridRowEnd() == row && edge.getMainGridColEnd() == col)
			if(vertexes[edge.getMainGridRowStart()][edge.getMainGridColStart()].getHasBuilding() == 0)
				return true;
		return false;
	}
	
	public void disableBuild() 
	{
		//Disables all build spots
		for(EdgeLink[] r: edges) 
		{
			for(EdgeLink e: r) 
			{
				if(e != null) 
				{
					e.setDisable(true);
				}
			}
		}
		
		for(VertexLink[] r: vertexes) 
		{
			for(VertexLink v: r) 
			{
				if(v != null) 
				{
					v.setDisable(true);
				}
			}
		}
	}
	
	public void requestBuild(int currentPlayer, Button build1Button, Button build2Button, Button build3Button, Button build4Button, Button noButton)
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane buildOptions = new GridPane();
    	buildOptions.setPrefWidth(650);
    	buildOptions.setAlignment(Pos.CENTER);
    	buildOptions.setVgap(25.0);
    	buildOptions.setHgap(25.0);
    	buildOptions.setPadding(new Insets(10));
		Text buildLabel = new Text("Would you like to build?");
		buildOptions.add(buildLabel, 0, 0, 2, 2);
		
		//Build Buttons
		build1Button.setPrefWidth(150);
		buildOptions.add(build1Button, 2, 0);
		
		build2Button.setPrefWidth(150);
		buildOptions.add(build2Button, 3, 0);
		
		build3Button.setPrefWidth(150);
		buildOptions.add(build3Button, 2, 1);
		
		build4Button.setPrefWidth(150);
		buildOptions.add(build4Button, 3, 1);

		noButton.setPrefWidth(150);
		buildOptions.add(noButton, 2, 2, 2, 2);
		    		
		commandPanel.getChildren().add(0, buildOptions);
    }
	
	
	/************************************************************************************
	 ************************************************************************************
	 * TRADE METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	/*
	 * Phase 1: yes or no to trade
	 * Phase 2: if yes, ask who to trade with
	 * Phase 3: player 1 chooses what to request
	 * Phase 4: player 1 chooses how much to request
	 * Phase 5: player 1 decides if they want to trade multiple resources
	 * Phase 6: player 2 decides if they want to trade and if so, what they want to trade
	 * Phase 7: player 2 decides how much they want to trade
	 * Phase 8: player 2 decides if they want to trade multiple resources
	 * Phase 9: player 1 decides if they want to accept player 2's offer
	 * 
	 * Ship Trade Phase 1: decides which ship (if applicable) to trade with
	 * Ship Trade Phase 2: player selects what to receive in exchange
	 * Ship Trade Phase 3: if 3:1, select what to give away
	 */
	
	public void requestTradePhaseOne(int currentPlayer, Button yesButton, Button noButton) 
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("Would you like to trade?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//trade Buttons
		yesButton.setPrefWidth(150);
		tradeOptions.add(yesButton, 2, 0);

		noButton.setPrefWidth(150);
		tradeOptions.add(noButton, 2, 1);
		    		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseTwo(Button option1Button, Button option2Button, Button option3Button, Button option4Button, Button cancelButton) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("Who would you like to trade with?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//player Buttons
		option1Button.setPrefWidth(150);
		tradeOptions.add(option1Button, 2, 0);
		
		option2Button.setPrefWidth(150);
		tradeOptions.add(option2Button, 3, 0);
		
		option3Button.setPrefWidth(150);
		tradeOptions.add(option3Button, 2, 1);
		
		//Ship Button
		option4Button.setPrefWidth(150);
		tradeOptions.add(option4Button, 3, 1);
		
		//Cancel Button
		cancelButton.setPrefWidth(150);
		tradeOptions.add(cancelButton, 2, 2);
		    		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseThree(Button brickButton , Button grainButton, Button oreButton, Button woodButton, Button woolButton,Button cancelButton) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("What would you like to trade for?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//resource Buttons
		brickButton.setPrefWidth(150);
		tradeOptions.add(brickButton, 2, 0);
		
		grainButton.setPrefWidth(150);
		tradeOptions.add(grainButton, 3, 0);
		
		oreButton.setPrefWidth(150);
		tradeOptions.add(oreButton, 2, 1);

		woodButton.setPrefWidth(150);
		tradeOptions.add(woodButton, 3, 1);
		
		woolButton.setPrefWidth(150);
		tradeOptions.add(woolButton, 2, 2);
		
		cancelButton.setPrefWidth(150);
		tradeOptions.add(cancelButton, 3, 2);
		    		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseFour(Button cancelButton,TextField resourceNum,Button enter,ArrayList <String> resources,String resource) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("How many "+resource+ " would you like to trade for?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//number of said resource
		resourceNum.setPrefWidth(150);
		tradeOptions.add(resourceNum, 2, 0);
		
		enter.setPrefWidth(150);
		tradeOptions.add(enter, 3, 0);
		
		cancelButton.setPrefWidth(150);
		tradeOptions.add(cancelButton, 2, 1);
		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseFive(Button Yes, Button No) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("Would you like to trade for another item?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//trade again buttons
		No.setPrefWidth(150);
		tradeOptions.add(No, 2, 1);
		
		Yes.setPrefWidth(150);
		tradeOptions.add(Yes, 2, 0);
		
		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseSix(Button brickButton , Button grainButton, Button oreButton, Button woodButton, Button woolButton, Button No, String player2,ArrayList<String> resources,ArrayList<String> rNums) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text(player2+", what would you like to receive in exchange for");
		tradeLabel.setWrappingWidth(250);
		for(int i = 0; i < resources.size(); i++)
		{
			tradeLabel.setText(tradeLabel.getText() + " " + rNums.get(i) + " " + resources.get(i)+"(s)");
		}
		tradeLabel.setText(tradeLabel.getText() + "?");
		
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);

		//resource Buttons
		brickButton.setPrefWidth(150);
		tradeOptions.add(brickButton, 2, 0);
		
		grainButton.setPrefWidth(150);
		tradeOptions.add(grainButton, 3, 0);
		
		oreButton.setPrefWidth(150);
		tradeOptions.add(oreButton, 2, 1);

		woodButton.setPrefWidth(150);
		tradeOptions.add(woodButton, 3, 1);
		
		woolButton.setPrefWidth(150);
		tradeOptions.add(woolButton, 2, 2);
		
		No.setPrefWidth(150);
		tradeOptions.add(No, 3, 2);
				
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseSeven(Button cancelButton,ArrayList<String> oppResources, Button enter,ArrayList<String> oppRNums,TextField resourceNum,String oppResource) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("How many "+oppResource+ " would you like to trade for?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//number of said resource
		resourceNum.setPrefWidth(150);
		tradeOptions.add(resourceNum, 2, 0);
		
		enter.setPrefWidth(150);
		tradeOptions.add(enter, 2, 1);
		
		cancelButton.setPrefWidth(150);
		tradeOptions.add(cancelButton, 3, 1);
		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseEight(Button Yes, Button No) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("Would you like to trade for another item?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//trade again buttons
		No.setPrefWidth(150);
		tradeOptions.add(No, 2, 1);
		
		Yes.setPrefWidth(150);
		tradeOptions.add(Yes, 2, 0);
		
		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseNine(Button Yes,Button No, ArrayList<String> resources, ArrayList<String> rNums, ArrayList<String> oppResources, ArrayList<String> oppRNums)
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text("do you accept the terms of trade");
		for(int i=0;i<resources.size();i++)
		{
			tradeLabel.setText(tradeLabel.getText() + rNums.get(i) + " " + resources.get(i)+"(s)");
			if(i+1<resources.size())
			{
				tradeLabel.setText(tradeLabel.getText() + ",\n");
			}
		}
		tradeLabel.setText(tradeLabel.getText() + " for");
		for(int i=0;i<oppResources.size();i++)
		{
			tradeLabel.setText(tradeLabel.getText() + oppRNums.get(i) + " " + oppResources.get(i)+"(s)");
			if(i+1<oppResources.size())
			{
				tradeLabel.setText(tradeLabel.getText() + ",\n");
			}
		}
		tradeLabel.setText(tradeLabel.getText()+ "?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//trade again buttons
		No.setPrefWidth(150);
		tradeOptions.add(No, 2, 1);
		
		Yes.setPrefWidth(150);
		tradeOptions.add(Yes, 2, 0);
		
		
		commandPanel.getChildren().add(0, tradeOptions);
	}

	public void requestShipTradePhase(String message, ArrayList <Button> buttons) 
	{
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
		//Build Options
    	GridPane tradeOptions = new GridPane();
    	tradeOptions.setPrefWidth(650);
    	tradeOptions.setAlignment(Pos.CENTER);
    	tradeOptions.setVgap(25.0);
    	tradeOptions.setHgap(25.0);
    	tradeOptions.setPadding(new Insets(10));
		Text tradeLabel = new Text(message);
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//Integers to manage rows and columns of the buttons
		int row = 0;
		int col = 2;
		for(Button button: buttons) 
		{
			button.setPrefWidth(150);
			tradeOptions.add(button, col, row);
			
			if(row < 2) 
			{
				row++;
			}
			else 
			{
				row = 0;
				col++;
			}
		}
		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	/************************************************************************************
	 ************************************************************************************
	 * HELPER METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	//Displays the passed in text in the command panel
	public void displayText(ArrayList<String> text, int textSize, Button continueButton) 
	{
		//Prevent Stacking
		updateGUI(vertexes, edges, players);
		
		VBox textBox = new VBox(10);
		textBox.setPrefWidth(650);
		textBox.setAlignment(Pos.CENTER);
		
		for(String str: text) 
		{
			Text t = new Text(str);
			t.setFont(Font.font(textSize));
			textBox.getChildren().add(t);
		}
		
		continueButton.setPrefWidth(150);
		textBox.getChildren().add(continueButton);
		
		commandPanel.getChildren().add(0, textBox);
	}
}
