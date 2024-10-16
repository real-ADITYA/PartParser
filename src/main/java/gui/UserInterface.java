package gui;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

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
import parts.CPU;
import parts.GPU;
import utils.HibernateUtility;

public class UserInterface extends Application{

	private final int GUI_WIDTH = 640;
	private final int GUI_HEIGHT = 480;
	private Stage primaryStage;
	private Button backButton;
	private Button quitButton;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Part Parser Home");
		
		Image icon = new Image(getClass().getResourceAsStream("/icon/cpu16.png"));
		primaryStage.getIcons().add(icon);
		HibernateUtility.start();
		
		// Initialize some variables
		this.backButton = backButton();
		this.quitButton = quitButton();
		
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
	
	private Button quitButton() {
		Button quitButton = new Button("Exit");
        quitButton.setFont(Font.font("System", FontWeight.LIGHT, FontPosture.ITALIC, 14));
        quitButton.setPrefWidth(GUI_WIDTH/4);
        quitButton.setLayoutX((GUI_WIDTH - quitButton.getWidth()) / 2.7);
        quitButton.setLayoutY((GUI_HEIGHT - quitButton.getHeight()) / 2);
        quitButton.setOnAction(e -> primaryStage.close() );
        return quitButton;
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
        
        // Add buttons 
        Button cpuButton = new Button("CPU Tool");
        cpuButton.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        cpuButton.setPrefWidth(GUI_WIDTH/4);
        cpuButton.setLayoutX((GUI_WIDTH - cpuButton.getWidth()) / 2.7);
        cpuButton.setLayoutY((GUI_HEIGHT - cpuButton.getHeight()) / 2.5);
        cpuButton.setOnAction(e -> cpuScreenInit() );
        
        Button gpuButton = new Button("GPU Tool");
        gpuButton.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        gpuButton.setPrefWidth(GUI_WIDTH/4);
        gpuButton.setLayoutX(cpuButton.getLayoutX());
        gpuButton.setLayoutY(cpuButton.getLayoutY() + 50);
        gpuButton.setOnAction(e -> gpuScreenInit() );
        
        quitButton.setLayoutY(gpuButton.getLayoutX() + 50);
        
        anchorPaneMain.getChildren().addAll(programName, programNameBelow, cpuButton, gpuButton, quitButton);
        mainLayout.getChildren().addAll(topMenu, anchorPaneMain);
        
        Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
	}
	
	private void cpuScreenInit() {
		// Main screen setup
		VBox mainLayout = new VBox(10);
        
        /* ========= Main UI Interface ========= */
        AnchorPane anchorPaneMain = new AnchorPane();

        // Add text
        Pane styleTop = new Pane();
        Label programName = new Label("Begin Selection:");
        programName.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 20));
        programName.setLayoutX(programName.getLayoutX() + 50);
        
        Label programNameBelow = new Label("Enter a CPU:");
        programNameBelow.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        programNameBelow.setLayoutY(50);
        programNameBelow.setLayoutX(10);
        
        TextField searchField = new TextField();
        searchField.setLayoutX(100);
        searchField.setLayoutY(50);
        searchField.setPrefWidth(200);
        
        // buttons        
        Button searchButton = new Button(">");
        searchButton.setLayoutX(300);
        searchButton.setLayoutY(50);
        searchButton.setPrefWidth(30);
        
        Button clearButton = new Button("x");
        clearButton.setLayoutX(330);
        clearButton.setLayoutY(50);
        clearButton.setPrefWidth(30);
        clearButton.setOnAction(e -> cpuScreenInit());

        VBox resultsBox = new VBox(5);
        resultsBox.setLayoutX(10);
        resultsBox.setLayoutY(100);
        resultsBox.setPrefWidth(300);
        
        ScrollPane resultsScroll = new ScrollPane(resultsBox);
        resultsScroll.setLayoutX(10);
        resultsScroll.setLayoutY(100);
        resultsScroll.setPrefSize(320, GUI_HEIGHT - 110);
        
        VBox infoBox = new VBox(10);
        infoBox.setLayoutX(350);
        infoBox.setLayoutY(100);
        infoBox.setPrefWidth(280);
        Label infoTitle = new Label("CPU Information:");
        infoTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        Label infoContent = new Label("Select a CPU to view details.");
        infoContent.setWrapText(true);
        infoBox.getChildren().addAll(infoTitle, infoContent);

        Button addButton = new Button();
        
        // search button action
        searchButton.setOnAction(e -> {
        	searchField.setDisable(true);
        	searchField.setStyle("-fx-background-color: lightgrey;");
        	resultsBox.getChildren().clear();
        	String query = searchField.getText();
        	List<String> results = HibernateUtility.searchCPU(query);
        	if(results.size() == 0) {
        		resultsBox.getChildren().add(new Label("No results found :/\nPlease narrow your search as much as possible :)"));
        	}
        	for(String result : results) {
        		// make button for result
        		 Button resultButton = new Button(result);
        		 resultButton.setPrefWidth(resultsBox.getPrefWidth());
        		 resultButton.setOnAction(n -> {
        			 CPU target = HibernateUtility.findCPUInfo(result);
                     infoContent.setText(target.toString());
                     addButton.setText("(+) " + target.getName());
        		 });
        		 resultsBox.getChildren().add(resultButton);
        	}
        });
        
        
        addButton.setPrefWidth(200);
        addButton.setLayoutY(GUI_HEIGHT - 50);
        addButton.setLayoutX(GUI_WIDTH - 250);
        addButton.setOnAction(e -> {
        	String cpuName = (addButton.getText()).substring(4);
        	if (cpuName != null) {
                System.out.println("Added CPU: " + cpuName);
                System.out.println("some way of confirming");
            }
        });
        
        anchorPaneMain.getChildren().addAll(styleTop, programName, programNameBelow, searchField, searchButton,
        		clearButton, resultsScroll, infoBox, addButton, backButton);
        mainLayout.getChildren().addAll(anchorPaneMain);
        
        Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setResizable(false);
	}
	
	private void gpuScreenInit() {
		// Main screen setup
		VBox mainLayout = new VBox(10);
      
       /* ========= Main UI Interface ========= */
       AnchorPane anchorPaneMain = new AnchorPane();
       // Add text
       Pane styleTop = new Pane();
       Label programName = new Label("Begin Selection:");
       programName.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 20));
       programName.setLayoutX(programName.getLayoutX() + 50);
      
       Label programNameBelow = new Label("Enter a GPU:");
       programNameBelow.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
       programNameBelow.setLayoutY(50);
       programNameBelow.setLayoutX(10);
      
       TextField searchField = new TextField();
       searchField.setLayoutX(100);
       searchField.setLayoutY(50);
       searchField.setPrefWidth(200);
      
       // buttons       
       Button searchButton = new Button(">");
       searchButton.setLayoutX(300);
       searchButton.setLayoutY(50);
       searchButton.setPrefWidth(30);
      
       Button clearButton = new Button("x");
       clearButton.setLayoutX(330);
       clearButton.setLayoutY(50);
       clearButton.setPrefWidth(30);
       clearButton.setOnAction(e -> gpuScreenInit());
       VBox resultsBox = new VBox(5);
       resultsBox.setLayoutX(10);
       resultsBox.setLayoutY(100);
       resultsBox.setPrefWidth(300);
      
       ScrollPane resultsScroll = new ScrollPane(resultsBox);
       resultsScroll.setLayoutX(10);
       resultsScroll.setLayoutY(100);
       resultsScroll.setPrefSize(320, GUI_HEIGHT - 110);
      
       VBox infoBox = new VBox(10);
       infoBox.setLayoutX(350);
       infoBox.setLayoutY(100);
       infoBox.setPrefWidth(280);
       Label infoTitle = new Label("GPU Information:");
       infoTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
       Label infoContent = new Label("Select a GPU to view details.");
       infoContent.setWrapText(true);
       infoBox.getChildren().addAll(infoTitle, infoContent);
       Button addButton = new Button();
      
       // search button action
       searchButton.setOnAction(e -> {
       	searchField.setDisable(true);
       	searchField.setStyle("-fx-background-color: lightgrey;");
       	resultsBox.getChildren().clear();
       	String query = searchField.getText();
       	List<String> results = HibernateUtility.searchGPU(query);
       	if(results.size() == 0) {
       		resultsBox.getChildren().add(new Label("No results found :/\nPlease narrow your search as much as possible :)"));
       	}
       	for(String result : results) {
       		// make button for result
       		 Button resultButton = new Button(result);
       		 resultButton.setPrefWidth(resultsBox.getPrefWidth());
       		 resultButton.setOnAction(n -> {
       			 GPU target = HibernateUtility.findGPUInfo(result);
                    infoContent.setText(target.toString());
                    addButton.setText("(+) " + target.getProductName());
       		 });
       		 resultsBox.getChildren().add(resultButton);
       	}
       });
      
      
       addButton.setPrefWidth(200);
       addButton.setLayoutY(GUI_HEIGHT - 50);
       addButton.setLayoutX(GUI_WIDTH - 250);
       addButton.setOnAction(e -> {
       	String gpuName = (addButton.getText()).substring(4);
       	if (gpuName != null) {
               System.out.println("Added GPU: " + gpuName);
               System.out.println("some way of confirming");
           }
       });
      
       anchorPaneMain.getChildren().addAll(styleTop, programName, programNameBelow, searchField, searchButton,
       		clearButton, resultsScroll, infoBox, addButton, backButton);
       mainLayout.getChildren().addAll(anchorPaneMain);
      
       Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
       primaryStage.setScene(mainScene);
       primaryStage.show();
       primaryStage.setResizable(false);
	}


		
}
