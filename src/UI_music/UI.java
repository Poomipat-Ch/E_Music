/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;


import Component_Music.Account;
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
   
    private Stage stage;
    private int menuBtnClicked = 0;
    private Account userAccount;
    boolean showAllStyleSong;
    boolean showMyStyleSong;
    public UI(Stage stage, Account userAccount){
        this.stage = stage;
        this.userAccount = userAccount;
        showAllStyleSong = false;
        showMyStyleSong = false;
    }
    
   public BorderPane allPane(){
       BorderPane borderPane = new BorderPane();
       borderPane.getStyleClass().add("bg-border");
       
       
       borderPane.setLeft(menu());
       borderPane.setCenter(mainBox());
      
      
       
       return borderPane;
   }
   
   public BorderPane allPane2(){
       BorderPane borderPane = new BorderPane();
       borderPane.getStyleClass().add("bg-border");
       
       
       borderPane.setLeft(menu2());
       borderPane.setCenter(mainBox());
       
      
       
       return borderPane;
   }
   
    VBox vbox = new VBox();
    private VBox mainBox(){

        vbox.getStyleClass().add("mainBox");
        vbox.getChildren().addAll(tilePane(),allSongPane(""));
        
        return vbox;
    }

    
    
    private Button CreaButton(String text){
        Button btn = new Button(text);
        btn.getStyleClass().add("menubtn");
        btn.setMinWidth(250);
        btn.setMinHeight(50);

        
        return btn;
    }
    
    private Button CreateStyleButton(String text){
        Button btn = new Button(text);
        btn.getStyleClass().add("stylebtn");
        btn.setMinWidth(250);
        btn.setMinHeight(35);
        
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
        
        VBox listStyleSong = new VBox();
        
        Button pop = CreateStyleButton("Pop");
        pop.setOnMouseClicked(e -> {

        });
        
        Button jazz = CreateStyleButton("Jazz");
        jazz.setOnMouseClicked(e -> {

        });
        
        Button rock = CreateStyleButton("Rock");
        rock.setOnMouseClicked(e -> {

        });
        
        Button rnb = CreateStyleButton("R&B");
        rnb.setOnMouseClicked(e -> {

        });
        
        Button hiphop = CreateStyleButton("Hip Hop");
        hiphop.setOnMouseClicked(e -> {

        });
        
        listStyleSong.getChildren().addAll(pop,jazz,rock,rnb,hiphop);
        
        VBox allsong = new VBox();
        
        Button main = CreaButton("All Song");
        main.setOnMouseClicked(e -> {
            menuBtnClicked = 0;
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(allSongPane(""));
        });
        
        Button myLibrary = CreaButton("My Library");
        myLibrary.setOnMouseClicked(e -> {
            menuBtnClicked = 1;
           // if (userAccount.getUserRole() != "guest") { // wait
           
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(mySongPane());    
            
           // }
            
        });
        Button myAccount = CreaButton("My Account");
        myAccount.setOnMouseClicked(e -> {
            menuBtnClicked = 2;
          // if (userAccount.getUserRole() != "guest") { // wait
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(myAccount());
            
          // }
        });
        Button logOut = CreaButton("Logout");
        logOut.setOnMouseClicked(e -> {
            this.stage.close();
            Login.stage.show();
        });
        
        VBox bottomVBox = new VBox(myAccount,logOut);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        VBox.setVgrow(bottomVBox, Priority.ALWAYS);
        
        
        vBox.getChildren().addAll(logoLabel,main,myLibrary,bottomVBox);
        
        
        return vBox;
    }
    
    
    
    private VBox menu2(){
        VBox vBox = new VBox();
        vBox.getStyleClass().add("menu");
        vBox.setMaxWidth(200);
        vBox.setPadding(Insets.EMPTY);
        
        Label logoLabel = new Label("E-Music");
        logoLabel.getStyleClass().add("logoName");
        logoLabel.setMinWidth(250);
        logoLabel.setAlignment(Pos.CENTER);
        
        Button main = CreaButton("Song Management");
        main.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(allSongPane(""));
        });
        Button myLibrary = CreaButton("Account Management");
        myLibrary.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(mySongPane());
        });
        Button myAccount = CreaButton("My Account");
        myAccount.setOnMouseClicked(e -> {
            this.vbox.getChildren().remove(1);
            this.vbox.getChildren().add(myAccount());
        });
        Button logOut = CreaButton("Logout");
        logOut.setOnMouseClicked(e -> {
            this.stage.close();
            Login.stage.show();
        });
        
        VBox bottomVBox = new VBox(myAccount, logOut);
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
       Image exit_hover_icon = new Image("/icon/close-512_hover.png");
       
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
        Image minimize_icon_hover_icon = new Image("/icon/minimize-window_hover.png");
        
        Button minimize = new Button("",new ImageView(minimize_icon));
       minimize.setOnMouseEntered(e -> { 
           minimize.setGraphic(new ImageView(minimize_icon_hover_icon));
       });
       minimize.setOnMouseExited(e -> {
           minimize.setGraphic(new ImageView(minimize_icon));
       });
        
       minimize.setStyle("-fx-background-color : transparent;");
       minimize.setPadding(Insets.EMPTY);
       minimize.setLayoutX(974-36-20);
       minimize.setLayoutY(10);
       minimize.setOnMouseClicked(e -> {
           stage.setIconified(true);
       });
        
        return minimize;
    }
    
    abstract public AnchorPane allSongPane(String page);
    abstract public AnchorPane mySongPane();
    abstract public HBox searchBoxAll();
    abstract public HBox searchBoxMy();
    abstract public BorderPane myAccount();
    abstract public void userLogout();
    
    
}
