package org.settlersofcatan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
}
