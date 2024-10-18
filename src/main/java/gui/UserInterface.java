package gui;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import priceScraper.*;
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
        cpuButton.setLayoutX((GUI_WIDTH - cpuButton.getWidth()) / 4);
        cpuButton.setLayoutY((GUI_HEIGHT - cpuButton.getHeight()) / 2.5);
        cpuButton.setOnAction(e -> cpuScreenInit() );
        
        Button gpuButton = new Button("GPU Tool");
        gpuButton.setFont(cpuButton.getFont());
        gpuButton.setPrefWidth(cpuButton.getPrefWidth());
        gpuButton.setLayoutX(cpuButton.getLayoutX());
        gpuButton.setLayoutY(cpuButton.getLayoutY() + 50);
        gpuButton.setOnAction(e -> gpuScreenInit() );
        
        Button cpuCompareButton = new Button("CPU Value Finder");
        cpuCompareButton.setFont(cpuButton.getFont());
        cpuCompareButton.setPrefWidth(cpuButton.getPrefWidth());
        cpuCompareButton.setLayoutX(cpuButton.getLayoutX() + 180);
        cpuCompareButton.setLayoutY(cpuButton.getLayoutY());
        cpuCompareButton.setOnAction(e -> compareCPUScreenInit() );
        
        Button gpuCompareButton = new Button("GPU Value Finder");
        gpuCompareButton.setFont(cpuButton.getFont());
        gpuCompareButton.setPrefWidth(cpuButton.getPrefWidth());
        gpuCompareButton.setLayoutX(cpuButton.getLayoutX() + 180);
        gpuCompareButton.setLayoutY(gpuButton.getLayoutY());
        //gpuCompareButton.setOnAction(e -> compareGPUScreenInit() );
        
        quitButton.setLayoutY(gpuButton.getLayoutY() + 50);
        quitButton.setLayoutX(gpuButton.getLayoutX());
        
        anchorPaneMain.getChildren().addAll(programName, programNameBelow, cpuButton, gpuButton, quitButton,
        		cpuCompareButton, gpuCompareButton);
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
	
	private void compareCPUScreenInit() {
		// Main screen setup
		VBox mainLayout = new VBox(10);
      
       /* ========= Main UI Interface ========= */
       AnchorPane anchorPaneMain = new AnchorPane();
       // Add text
       Pane styleTop = new Pane();
       Label programName = new Label("Begin Selection:");
       programName.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 20));
       programName.setLayoutX(programName.getLayoutX() + 50);
      
       Label programNameBelow = new Label(" -- Only include results with:");
       programNameBelow.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.ITALIC, 14));
       programNameBelow.setLayoutY(50);
       programNameBelow.setLayoutX(10);
      
       TextField searchField = new TextField();
       searchField.setLayoutX(200);
       searchField.setLayoutY(50);
       searchField.setPrefWidth(200);
      
       // buttons       
       Button searchButton = new Button(">");
       searchButton.setLayoutX(400);
       searchButton.setLayoutY(50);
       searchButton.setPrefWidth(30);
      
       Button clearButton = new Button("x");
       clearButton.setLayoutX(430);
       clearButton.setLayoutY(50);
       clearButton.setPrefWidth(30);
       clearButton.setOnAction(e -> compareCPUScreenInit());

       // output
       VBox infoBox = new VBox(10);
       infoBox.setLayoutX(10);
       infoBox.setLayoutY(100);
       infoBox.setPrefWidth(GUI_WIDTH - 20);
       
       Label infoContent = new Label("This process will take a while, depending on how narrow the search is.");
       infoBox.getChildren().add(infoContent);
       
       // Progress bar for long-running tasks
       ProgressBar progressBar = new ProgressBar(0);
       progressBar.setPrefWidth(100);
       progressBar.setLayoutX(clearButton.getLayoutX() + 50);
       progressBar.setLayoutY(clearButton.getLayoutY());
       progressBar.setVisible(false);
      
       // search button action
       searchButton.setOnAction(e -> {
       	searchField.setDisable(true);
       	searchField.setStyle("-fx-background-color: lightgrey;");
       	infoBox.getChildren().clear();
       	progressBar.setVisible(true);
       	String query = searchField.getText();
       	List<Double> cpuPrices = new ArrayList<Double>();
       	List<String> cpuNames = new ArrayList<String>();
       	
       	Task<Void> compareTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<String> results = HibernateUtility.searchCPU(query);
                if (results.isEmpty()) {
                    Platform.runLater(() -> {
                        infoBox.getChildren().clear();
                        infoBox.getChildren().add(new Label("No results found :/\nPlease narrow your search as much as possible :)"));
                    });
                } else {
                	EBAYDriver driver = new EBAYDriver();
                    int count = results.size();
                    for (int i = 0; i < count; i++) {
                        String cpuName = results.get(i);
                        driver.runScraper(cpuName, 0);

                        // progress bar update
                        updateProgress(i + 1, count);

                        // get the average used cost of the CPU and save its value
                        
                        
                        // updating the infobox to let the user know when the cpu has been processed
                        Platform.runLater(() -> {
                            infoBox.getChildren().add(new Label("Successfully processed: " + cpuName));
                        });

                    }
                }
                return null;
            }
       	};
       	
       	// progress bar update
       	progressBar.progressProperty().bind(compareTask.progressProperty());

       	// Hide the progress bar once the task is done
        compareTask.setOnSucceeded(e2 -> {
            progressBar.setVisible(false);
            searchField.setDisable(false);
        });

        // Start the background task
        new Thread(compareTask).start();
       	
       });
       
   
    
       anchorPaneMain.getChildren().addAll(styleTop, programName, programNameBelow, searchField, searchButton,
       		clearButton, infoBox, progressBar, backButton);
       mainLayout.getChildren().addAll(anchorPaneMain);
      
       Scene mainScene = new Scene(mainLayout, GUI_WIDTH, GUI_HEIGHT);
       primaryStage.setScene(mainScene);
       primaryStage.show();
       primaryStage.setResizable(false);
	}


		
}
