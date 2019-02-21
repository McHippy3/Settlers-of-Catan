package org.settlersofcatan;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class OfflineGameScene extends StackPane
{
	private VertexLink vertexes[][];
	private EdgeLink edges[][];
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
	
	public OfflineGameScene(VertexLink[][] vertexes, EdgeLink[][] edges, ArrayList<Player> players) 
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
        
        for(int r = 0; r < 5; r++) 
        {
        	for(int c = 0; c < numOfColumns; c++) 
        	{
        		ImageView img = new ImageView(new Image("res/tile_photos/mountains.png"));
        		img.setFitHeight(210 * sf);
        		img.setFitWidth(240 * sf);
        		img.setRotate(90);
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
        		gameTiles.getChildren().add(img);
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
				/*Circle cir = new Circle( xOffSet + c * 105 * sf, yOffSet + y[r] * sf - 10, 3);
				gameTiles.getChildren().add(cir);*/
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
        
        //Command Panel Elements/Nodes
        updateCommandPanel();
	}
	
	//Update GUI helper
	private void updatePlayerTiles() 
	{
		for(int i = 0; i < 4; i++) 
		{
			//Finding and removing victory point labels
			for(int i2 = 0; i2 < playerTiles[i].getChildren().size(); i2++) 
			{
				Node node = playerTiles[i].getChildren().get(i2);
				if(node instanceof Text && ((Text) node).getText().contains("Victory Points: "))
				{
					playerTiles[i].getChildren().remove(node);
					break;
				}
			}
			
			//Updating victory points Text
			Text updatedPlayerText = new Text("Victory Points: " + players.get(i).getVP());
			playerTiles[i].add(updatedPlayerText, 0, 1, 1, 1);
			
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
					ImageView roadImg = new ImageView(new Image(roadURL[edges[r][c].road.p.playerNumber]));
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
			        gameTiles.getChildren().add(20, roadImg);
        		}
        	}
        }
	}
	
	//Update GUI helper
	private void updateBuildings() 
	{
		//Removing old settlement and city images
		while(gameTiles.getChildren().size() != 155) 
		{
			gameTiles.getChildren().remove(20);
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
			        		new Image(buildingURL[vertexes[r][c].getSettlement().p.playerNumber][0]));
			        settlementImg.setX(xOffSet + c * 105 * sf - 15);
			        settlementImg.setY(yOffSet + y[r] * sf - 25);
			        settlementImg.setFitHeight(30);
			        settlementImg.setFitWidth(30);
			        //Place images at index 20 to be on top of tiles but below buttons
			        gameTiles.getChildren().add(20, settlementImg);
				}
				//Cities
				else if(vertexes[r][c] != null && vertexes[r][c].getHasBuilding() == 2) 
				{
					//getSettlement() can be used as buildingURL simply needs the player number 
			        ImageView cityImg = new ImageView(
			        		new Image(buildingURL[vertexes[r][c].getSettlement().p.playerNumber][1]));
			        cityImg.setX(xOffSet + c * 105 * sf - 15);
			        cityImg.setY(yOffSet + y[r] * sf - 25);
			        cityImg.setFitHeight(30);
			        cityImg.setFitWidth(30);
			        //Place images at index 20 to be on top of tiles but below buttons
			        gameTiles.getChildren().add(20, cityImg);
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
	 * ROLL METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	public void requestRollDice(int currentPlayer, Button rollButton) 
	{
		this.currentPlayer = currentPlayer;
		//Prevent stacking
		updateGUI(vertexes, edges, players);
		
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
					e.setDisable(false);
				}
			}
		}
		for(VertexLink[] r: vertexes) 
		{
			for(VertexLink v: r) 
			{
				if(v != null && v.getHasBuilding() == 0 && buildCode == 2) 
				{
					v.setDisable(false);
				}
				else if(v != null && v.getHasBuilding() > 0 && buildCode == 3) 
				{
					if(v.settlement.p.playerNumber == currentPlayer)
						v.setDisable(false);
				}
			}
		}
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
	
	public void requestBuild(int currentPlayer, Button build1Button, Button build2Button, Button build3Button, Button noButton)
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

		noButton.setPrefWidth(150);
		buildOptions.add(noButton, 3, 1);
		    		
		commandPanel.getChildren().add(0, buildOptions);
    }

	/************************************************************************************
	 ************************************************************************************
	 * TRADE METHODS *
	 ************************************************************************************
	 ************************************************************************************/
	
	/*
	 * Phase 1: yes or no to trade
	 * Phase 2: if yes, request player to trade with
	 * Phase 3: player 1 chooses what to request
	 * Phase 4: player 2 chooses what to take in exchange
	 */
	public void requestTradePhaseOne(Button yesButton, Button noButton) 
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
		Text tradeLabel = new Text("Would you like to trade?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//Build Buttons
		yesButton.setPrefWidth(150);
		tradeOptions.add(yesButton, 2, 0);

		noButton.setPrefWidth(150);
		tradeOptions.add(noButton, 2, 1);
		    		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseTwo(int currentPlayer, Button option1Button, Button option2Button, Button option3Button, Button cancelButton) 
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
		Text tradeLabel = new Text("Who would you like to trade with?");
		tradeOptions.add(tradeLabel, 0, 0, 2, 2);
		
		//Build Buttons
		option1Button.setPrefWidth(150);
		tradeOptions.add(option1Button, 2, 0);
		
		option2Button.setPrefWidth(150);
		tradeOptions.add(option2Button, 3, 0);
		
		option3Button.setPrefWidth(150);
		tradeOptions.add(option3Button, 2, 1);

		cancelButton.setPrefWidth(150);
		tradeOptions.add(cancelButton, 3, 1);
		    		
		commandPanel.getChildren().add(0, tradeOptions);
	}
	
	public void requestTradePhaseThree(int currentPlayer, Button brickButton , Button grainButton, Button oreButton, Button sheepButton, Button woodButton) 
	{
		
	}
	
	public void requestTradePhaseFour(int currentPlayer, Button brickButton , Button grainButton, Button oreButton, Button sheepButton, Button woodButton) 
	{
		
	}
}
