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
	private Vertex vertexes[][];
	private Edge edges[][];
	
	public void start(Stage stage) 
	{
		this.stage = stage;
		
		Button startButton = new Button("Start Game");
		
		mainScene = new Scene((Parent) Scenes.titleScene(startButton));

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
		Button submitButton = new Button("Submit Names");
		TextField name1 = new TextField();
		TextField name2 = new TextField();
		TextField name3 = new TextField();
		TextField name4 = new TextField();
		
		mainScene.setRoot((Parent) Scenes.nameScene(submitButton, name1, name2, name3, name4));
		
		submitButton.setOnMouseClicked(
				(MouseEvent e) -> setOfflineGameScene()
				);
	}
	
	private void setOfflineGameScene() 
	{
		vertexes = new Vertex[12][11];
		edges = new Edge[11][11];
		mainScene.setRoot(new OfflineGameScene(vertexes, edges));
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}
