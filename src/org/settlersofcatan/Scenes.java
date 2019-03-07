package org.settlersofcatan;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Scenes 
{
	//Main Menu
	public static Node titleScene(Button startButton) 
	{
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(100);
		
		//Components
		ImageView image = new ImageView(new Image("res/logo.png"));
		startButton.setPrefSize(100, 25);
		root.getChildren().addAll(image, startButton);
		return root;
	}
	
	//Scene for inputting player names
	public static Node nameScene(Button submitButton, TextField name1, TextField name2, TextField name3, TextField name4) 
	{
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(35);
		
		//Components
		name1.setMaxWidth(200);
		name1.setPromptText("Player 1 Name");
		
		name2.setMaxWidth(200);
		name2.setPromptText("Player 2 Name");
	
		name3.setMaxWidth(200);
		name3.setPromptText("Player 3 Name");
		
		name4.setMaxWidth(200);
		name4.setPromptText("Player 4 Name");
		
		submitButton.setPrefSize(100, 25);
		
		root.getChildren().addAll(name1, name2, name3, name4, submitButton);
		
		return root;
	}
	
	public static Node gameOverScene(Button continueButton, String playerName) 
	{
		VBox components = new VBox();
		components.setAlignment(Pos.CENTER);
		components.setSpacing(35.0);
		
		Text winnerText = new Text(playerName + " WON!!!");
		winnerText.setFont(Font.font("Verdana", 75));
		//Animation for the text
		ScaleTransition st = new ScaleTransition();
		st.setDuration(Duration.millis(1000));
		st.setNode(winnerText);
		st.setFromX(1.0);
		st.setFromY(1.0);
		st.setToX(1.5);
		st.setToY(1.5);
		st.setAutoReverse(true);
		st.setCycleCount(10);
		st.play();
		
		RotateTransition rt = new RotateTransition();
		rt.setDuration(Duration.millis(1000));
		rt.setNode(winnerText);
		rt.setByAngle(360);
		rt.setAutoReverse(true);
		rt.setCycleCount(10);
		rt.play();
		
		continueButton.setPrefWidth(150);
		
		components.getChildren().addAll(winnerText, continueButton);
		
		return components;
	}
}
