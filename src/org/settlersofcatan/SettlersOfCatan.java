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

public class SettlersOfCatan extends Application
{
	
	private Scene mainScene;
	private Stage stage;
	
	public void start(Stage stage) 
	{
		this.stage = stage;
		
		//TODO Getting Screen info
		
		Button startButton = new Button("Start Game");
		
		mainScene = new Scene((Parent) Scenes.titleScene(startButton));

		//Setting stage
		stage.setScene(mainScene);
		stage.setTitle("Settlers of Catan");
		stage.getIcons().add(new Image("res/window_icon.png"));
		stage.setMaximized(true);
		stage.setResizable(false);
		stage.show();
		startButton.setOnMouseClicked(
				(MouseEvent e) -> setOfflineGameScene()
				);
	}

	
	private void setOfflineGameScene() 
	{
		mainScene.setRoot((Parent) Scenes.offlineGameScene());
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
