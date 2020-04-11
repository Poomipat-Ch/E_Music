/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.SearchSystem;
import Component_Music.SearchSystemAccount;
import Component_Music.Song;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sirawit
 */
public class Admin_UI extends UI{
    
    
    SearchSystem searchSystemMain = new SearchSystem();
    SearchSystemAccount searchAccount = new SearchSystemAccount();


    public Admin_UI(Stage stage) {
        super(stage);
        Scene scene = new Scene(allPane2(), 1280, 960);
        String stylrSheet = getClass().getResource("/style_css/style.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    private Button CreaButton(String text) {
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setMinSize(200, 50);

        return downLoadButton;

    }
    
    @Override
    public AnchorPane allSongPane() {
        AnchorPane pane = new AnchorPane();
        
        Label title1 = new Label("Welcome to Administrative Page!");
        title1.getStyleClass().add("titleAdmin");
        title1.setLayoutX(50);
        title1.setLayoutY(5);

        //        pane.setMinHeight(760);
        //        pane.setMaxHeight(Double.MAX_VALUE);
        //        pane.getStyleClass().add("bg-2");
        //        AnchorPane img = new AnchorPane();
        //        img.setPrefSize(300, 400);
        //        img.setLayoutX(1030 - 300 - 20);
        //        img.setLayoutY(20);
        //        Image imageAll = new Image("/image/Music_pic.jpg");
        //        ImageView imgAll = new ImageView(imageAll);
        //        img.getChildren().add(imgAll);
        //
        //        Button priceButton = CreaButton("Buy");
        //        priceButton.setLayoutX(1030 - 250 - 20);
        //        priceButton.setLayoutY(420 + 20);

        pane.getChildren().addAll(AllSong(),title1);

        return pane;   
    }

    @Override
    public AnchorPane mySongPane() {
        AnchorPane pane = new AnchorPane();
        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);
        pane.getStyleClass().add("bg-2");
//        AnchorPane img = new AnchorPane();
//        img.setPrefSize(300, 400);
//        img.setLayoutX(1030 - 300 - 20);
//        img.setLayoutY(20);

//        Image imageMy = new Image("/image/Music_pic.jpg");
//        ImageView imgMy = new ImageView(imageMy);
//        img.getChildren().add(imgMy);
        
        Label title2 = new Label("Account Management System");
        title2.getStyleClass().add("titleAdmin");
        title2.setLayoutX(50);
        title2.setLayoutY(5);
        
        Button addAccountBtn = CreaButton("Add Account");
        addAccountBtn.setLayoutX(520);
        addAccountBtn.setLayoutY(675); 
        addAccountBtn.setOnAction(e -> {
            //Gut it's your turn boi...
        });

        Button deleteAccountBtn = CreaButton("Delete Account");
        deleteAccountBtn.setLayoutX(750);
        deleteAccountBtn.setLayoutY(675);
        deleteAccountBtn.setOnAction(e -> {
            //Gut it's your turn boi... 
        });
        
        pane.getChildren().addAll(addAccountBtn, deleteAccountBtn, tableAccount(), searchBoxMy(),title2);

        return pane;    
    }
    
    private AnchorPane tableAccount() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(925, 500); //1030 - 300 - 60, 700
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(150);

        TableView<Account> table = new TableView<>(); 
        table.setEditable(true);
        table.setPrefSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(table.getSelectionModel().getSelectedItem().getGender()); //.getNameSong()
            }
        });

        // Create column Name (Data type of String).
        TableColumn<Account, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(150);

        // Create column Surname (Data type of String).
        TableColumn<Account, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setMinWidth(150);

        // Create column Username (Data type of String).
        TableColumn<Account, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setMinWidth(150);
        
        // Create column Email (Data type of String).
        TableColumn<Account, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(280);
        
        // Create column Gender (Data type of String).
        TableColumn<Account, String> genderCol = new TableColumn<>("Gender");
        genderCol.setMinWidth(30);
        genderCol.setMaxWidth(60);
        
        // Create column DoB (Data type of LocalDate).
        TableColumn<Account, LocalDate> dobCol = new TableColumn<>("DoB");
        dobCol.setMinWidth(60);
        
        // Create column isAdmin (Data type of Boolean).
        TableColumn<Account, Boolean> adminCol = new TableColumn<>("Admin");
        adminCol.setMinWidth(50);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        adminCol.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
       
        
        // Set Sort type for userName column
        nameCol.setSortType(TableColumn.SortType.DESCENDING);

        // Display row data       
        ObservableList<Account> list = null;        //TRY -CATCH FOR EXCEPTION ... NOTHING TO DO WITH IT
        try {
            list = Account.getAccountList();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Admin_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Filter for Search and Sorted
        FilteredList<Account> filterData = new FilteredList<>(list, b -> true);
        searchAccount.setFilterData(filterData);
                                                       
        SortedList<Account> sortedList = new SortedList<>(searchAccount.getFilterData()); 
        sortedList.comparatorProperty().bind(table.comparatorProperty()); 
        table.setItems(filterData); 

        table.getColumns().addAll(nameCol, surnameCol, usernameCol, emailCol, genderCol, dobCol, adminCol);

        anchorPane.getChildren().addAll(table);

        return anchorPane;
    }
    

    @Override
    public HBox searchBoxAll() { // All Song first page
       
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMain);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
        
    }

    @Override
    public HBox searchBoxMy() {  // All Song Second page
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(100);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchAccount);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }
    
    ScrollPane scrollPane;
    VBox vbox;
    HBox hbox;
    
    private ScrollPane  AllSong(){
        
        vbox = new VBox(10);
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1030 - 300 - 60, 700);
        //scrollPane.setMinSize(1030-300-60, 700);
        scrollPane.setLayoutX(20);
        scrollPane.setLayoutY(100);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(false);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        scrollPane.setPadding(new Insets(20, 20, 20, 20));
        scrollPane.setBackground(Background.EMPTY);
        
        ImageView imageView;
        
        VBox totalbox = new VBox(30);
        //totalbox.setPadding(new Insets(30, 30, 30, 30));
        
        for(int i = 0; i< 10; ++i) {
            hbox = new HBox(20);
            hbox.setPadding(new Insets(0, 30, 0, 30));
            
            for(int k  = 1 ; k < 4 ; ++k) {
                vbox = new VBox(30);
                vbox.setPadding(new Insets(20, 20, 20, 20));
                
                imageView = new ImageView(new Image("/image/" + k +".jpg"));
                imageView.setFitHeight(160); 
                imageView.setFitWidth(120); 
                
                Button buyButton = new Button("Buy");
                buyButton.setOnMouseClicked(e ->{
                    // Buy fuction wait ->  gut nehee
                });
                
                vbox.getChildren().addAll(imageView, new Text("Love"), new Text("ARTIST : Bodyslam"));
                vbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(vbox);
            }
            hbox.setAlignment(Pos.CENTER);
            totalbox.getChildren().addAll(hbox);
        }
        
        totalbox.setAlignment(Pos.CENTER);
        VBox totalPane = new VBox();
        totalPane.getChildren().addAll(searchBoxAll(),totalbox);
        
        
        scrollPane.setContent(totalPane);
        
        return scrollPane;
    }
    
    
}
