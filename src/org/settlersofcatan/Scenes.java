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
	public static Scene titleScene() 
	{
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(100);
		
		//Components
		ImageView image = new ImageView(new Image("res/logo.png"));
		Button startButton = new Button("Start Game");
		startButton.setPrefSize(100, 25);
		
		root.getChildren().addAll(image, startButton);
		return new Scene(root);
	}
	
	public static Scene offlineGameScene() 
	{
		BorderPane root = new BorderPane();
		VBox[] playerTiles = new VBox[4];
		
		for(int i = 0; i < 4; i++) 
		{
			
		}
		
		root.setLeft();
		root.setRight();
		return new Scene(root);
	}
}
