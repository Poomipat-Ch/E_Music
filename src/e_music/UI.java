/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author poomi
 */
abstract public class UI {
   
    Stage stage;
    public UI(Stage stage){
        this.stage = stage;
    }
    
   public BorderPane allPane(){
       BorderPane borderPane = new BorderPane();
       borderPane.getStyleClass().add("bg-border");
       
       borderPane.setBottom(vbox);
       
       
       borderPane.setLeft(menu());
       borderPane.setCenter(mainBox());
      
      
       
       return borderPane;
   }
           VBox vbox = new VBox();
    private VBox mainBox(){

        vbox.getStyleClass().add("mainBox");
        vbox.getChildren().addAll(tilePane(),allSongPane());
        
        return vbox;
    }

    
    
    private Button CreaButton(String text){
        Button btn = new Button(text);
        btn.getStyleClass().add("menubtn");
        btn.setMinWidth(250);
        btn.setMinHeight(50);
        
        return btn;
    }
    
    private VBox menu(){
        VBox vBox = new VBox();
        vBox.getStyleClass().add("menu");
        vBox.setMaxWidth(200);
        vBox.setPadding(Insets.EMPTY);
        
        Label logoLabel = new Label("E-Music");
        logoLabel.getStyleClass().add("logoName");
        logoLabel.setMinWidth(250);
        logoLabel.setAlignment(Pos.CENTER);
        
        Button main = CreaButton("All Song");
        main.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(allSongPane());
        });
        Button myLibrary = CreaButton("My Library");
        myLibrary.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(mySongPane());
        });
        Button logOut = CreaButton("Logout");
        
        VBox bottomVBox = new VBox(logOut);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(bottomVBox, Priority.ALWAYS);
        
        
        vBox.getChildren().addAll(logoLabel,main,myLibrary,bottomVBox);
        
        
        return vBox;
    }
    
    
    
    double mouse_x = 0,mouse_y = 0; // position mouse
    
    
    
    public AnchorPane tilePane(){

        
        
       AnchorPane anchorPane = new AnchorPane();
       anchorPane.getStyleClass().add("title");
       anchorPane.setPadding(new Insets(5));
       anchorPane.getChildren().addAll(exitButton(),minimizeButton());
       anchorPane.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
       });
       anchorPane.setOnMouseDragged(e -> {
           stage.setX(e.getScreenX() - mouse_x);
           stage.setY(e.getScreenY() - mouse_y);
       });
       
       
       return anchorPane;
    }
    
    private Button exitButton(){
        
       Image exit_icon = new Image("/icon/close-512.png");
       Image exit_hover_icon = new Image("/icon/close-512.png");
       
       Button exit = new Button("",new ImageView(exit_icon));
       exit.setOnMouseEntered(e -> { 
           exit.setGraphic(new ImageView(exit_hover_icon));
       });
       exit.setOnMouseExited(e -> {
           exit.setGraphic(new ImageView(exit_icon));
       });
       exit.setOnMouseClicked(e -> {
           stage.close();
       });
       exit.setStyle("-fx-background-color : transparent;");
       exit.setPadding(Insets.EMPTY);
       exit.setLayoutX(974);
       exit.setLayoutY(10);
       
       return exit;
    }
    
    private Button minimizeButton(){
        Image minimize_icon = new Image("/icon/minimize-window.png");
        Button minimize = new Button("",new ImageView(minimize_icon));
        
       minimize.setStyle("-fx-background-color : transparent;");
       minimize.setPadding(Insets.EMPTY);
       minimize.setLayoutX(974-36-20);
       minimize.setLayoutY(10);
       minimize.setOnMouseClicked(e -> {
           stage.setIconified(true);
       });
        
        return minimize;
    }
    
    abstract public AnchorPane allSongPane();
    abstract public AnchorPane mySongPane();
    abstract public HBox searchBoxAll();
    abstract public HBox searchBoxMy();
    
    
}
