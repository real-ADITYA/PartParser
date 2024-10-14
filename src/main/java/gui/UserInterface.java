package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UserInterface extends Application{

	private final int GUI_WIDTH = 640;
	private final int GUI_HEIGHT = 480;
	private Stage primaryStage;
	private Button backButton = backButton();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Part Parser Home");
		
		Image icon = new Image(getClass().getResourceAsStream("/icon/cpu16.png"));
		primaryStage.getIcons().add(icon);
		
		// Login Screen
		homeScreen();
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	private Button backButton() {
		Button backButton = new Button("<");
        backButton.setPrefWidth(30);
        backButton.setPrefHeight(30);
        backButton.setLayoutX(10);
        backButton.setOnAction(e -> homeScreen());
        return backButton;
	}
	
	private void homeScreen() {
		// Main screen setup
		VBox mainLayout = new VBox(10);
		
		/* ========= MENUBAR ========= */
        MenuBar topMenu = new MenuBar();
        
        // File sub-menu
        Menu fileSubmenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction(e -> System.out.println("New file created"));
        MenuItem saveItem = new MenuItem("Save As");
        MenuItem openItem = new MenuItem("Open");
        MenuItem quitItem = new MenuItem("Quit");
        fileSubmenu.getItems().addAll(newItem, saveItem, openItem, new SeparatorMenuItem(), quitItem);
        
        // About sub-menu
        Menu aboutSubmenu = new Menu("About");
        
        // Add to menubar at the top of screen
        topMenu.getMenus().addAll(fileSubmenu, aboutSubmenu);
        
        /* ========= Main UI Interface ========= */
        AnchorPane anchorPaneMain = new AnchorPane();

        // Add text
        Label programName = new Label("PART PARSER");
        programName.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 50));
        programName.setLayoutX((GUI_WIDTH - programName.getWidth()) / 4);
        programName.setLayoutY((GUI_HEIGHT - programName.getHeight()) / 8);
        
        Label programNameBelow = new Label("Please make a selection to begin!");
        programNameBelow.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 14));
        programNameBelow.setLayoutX((GUI_WIDTH - programNameBelow.getWidth()) / 3);
        programNameBelow.setLayoutY((GUI_HEIGHT - programNameBelow.getHeight()) / 4);
        
        // Add button  
        Button beginButton = new Button("Begin!");
        beginButton.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        beginButton.setPrefWidth(GUI_WIDTH/4);
        beginButton.setLayoutX((GUI_WIDTH - beginButton.getWidth()) / 2.7);
        beginButton.setLayoutY((GUI_HEIGHT - beginButton.getHeight()) / 2.5);
        beginButton.setOnAction(e -> optionsScreen() );
        
        Button quitButton = new Button("Exit");
        quitButton.setFont(Font.font("System", FontWeight.LIGHT, FontPosture.ITALIC, 14));
        quitButton.setPrefWidth(GUI_WIDTH/4);
        quitButton.setLayoutX((GUI_WIDTH - quitButton.getWidth()) / 2.7);
        quitButton.setLayoutY((GUI_HEIGHT - quitButton.getHeight()) / 2);
        quitButton.setOnAction(e -> primaryStage.close() );
        
        anchorPaneMain.getChildren().addAll(programName, programNameBelow, beginButton, quitButton);
        mainLayout.getChildren().addAll(topMenu, anchorPaneMain);
        
        Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
	}
	
	private void searchCPUScreen() {
		// Main screen setup
		VBox mainLayout = new VBox(10);
        
        /* ========= Main UI Interface ========= */
        AnchorPane anchorPaneMain = new AnchorPane();

        // Add text
        Pane styleTop = new Pane();
        Label programName = new Label("Begin Selection:");
        programName.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 20));
        
        Label programNameBelow = new Label("Enter a CPU below");
        programNameBelow.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        programNameBelow.setLayoutY(30);
        
        TextField textField = new TextField();
        textField.setLayoutY(50);
        textField.setPrefWidth(100);
        
        // buttons        
        Button searchButton = new Button(">");
        searchButton.setLayoutX(100);
        searchButton.setLayoutY(50);
        searchButton.setPrefWidth(30);

        
        anchorPaneMain.getChildren().addAll(styleTop, programName, programNameBelow, textField, searchButton, backButton);
        mainLayout.getChildren().addAll(anchorPaneMain);
        
        Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
	}
	
	private void optionsScreen() {
		// Main screen setup
		VBox mainLayout = new VBox(10);
		
		/* ========= Main UI Interface ========= */
        AnchorPane anchorPaneMain = new AnchorPane();

        // Add text
        Label programName = new Label("PART PARSER");
        programName.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 50));
        programName.setLayoutX((GUI_WIDTH - programName.getWidth()) / 4);
        programName.setLayoutY((GUI_HEIGHT - programName.getHeight()) / 16);
        
        // Add button        
        Button buttonCPU = new Button("CPU Search");
        buttonCPU.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        buttonCPU.setPrefWidth(GUI_WIDTH/4);
        buttonCPU.setLayoutX((GUI_WIDTH - buttonCPU.getWidth()) / 2.7);
        buttonCPU.setLayoutY((GUI_HEIGHT - buttonCPU.getHeight()) / 3);
        buttonCPU.setOnAction(e -> searchCPUScreen() );
        
        Button buttonGPU = new Button("GPU Search");
        buttonGPU.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        buttonGPU.setPrefWidth(GUI_WIDTH/4);
        buttonGPU.setLayoutX(buttonCPU.getLayoutX());
        buttonGPU.setLayoutY(buttonCPU.getLayoutY() + 40);
        buttonGPU.setOnAction(e -> searchCPUScreen() );
        
        Button quitButton = new Button("Exit");
        quitButton.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        quitButton.setPrefWidth(GUI_WIDTH/4);
        quitButton.setLayoutX(buttonCPU.getLayoutX());
        quitButton.setLayoutY(buttonCPU.getLayoutY() + 80);
        quitButton.setOnAction(e -> primaryStage.close() );
        
        anchorPaneMain.getChildren().addAll(programName, buttonCPU, buttonGPU, quitButton, backButton);
        mainLayout.getChildren().addAll(anchorPaneMain);
        
        Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
		
	}
	
	
	
}
