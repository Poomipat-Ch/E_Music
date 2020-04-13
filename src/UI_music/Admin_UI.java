/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.MusicFunc;
import Component_Music.SearchSystem;
import Component_Music.SearchSystemAccount;
import Component_Music.Song;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sirawit
 */
public class Admin_UI extends UI{
    
    private LocalDate dOB;
    private File user = new File("src/data/user.dat");
    private boolean dateSet = false;   
    private ArrayList<Account> listAccount = new ArrayList<>(); 
    private ArrayList<Account> addAccount = new ArrayList<>();
    
    private ObservableList<Account> list = null; 
    
    private SearchSystem searchSystemMain = new SearchSystem();
   private  SearchSystemAccount searchAccount = new SearchSystemAccount();

    private TableView<Account> table;
    
    private Account userAccount;
    
    private ReadWriteFile file = new ReadWriteFile();
    
    public Admin_UI(Stage stage, Account userAccount) {
        super(stage);
        
        this.userAccount = userAccount;
        
        Scene scene = new Scene(allPane2(), 1280, 960);
        String stylrSheet = getClass().getResource("/style_css/style.css").toExternalForm();
        String stylrSheet2 = getClass().getResource("/style_css/styleAdmin.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        scene.getStylesheets().add(stylrSheet2);
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
    public AnchorPane allSongPane() {   //First Page 1
        AnchorPane pane = new AnchorPane();
        
        Label title1 = new Label("Welcome to Administrative Page!");
        title1.getStyleClass().add("titleAdmin");
        title1.setLayoutX(50);
        title1.setLayoutY(5);

            Button editBtn = CreaButton("Edit Song");       //Edit Button
            editBtn.setLayoutX(780);
            editBtn.setLayoutY(600);
            editBtn.setOnAction(e->{
                //Gut //Edit profile / file e.g. artist, name, time, and file .mp3
            });
            
            Button uploadBtn = CreaButton("Upload");        //Upload Button
            uploadBtn.setLayoutX(780);
            uploadBtn.setLayoutY(700);
            uploadBtn.setOnAction(e->{
                //Gut
            });
            
            Button deleteBtn = CreaButton("Delete");        //Delete Button
            deleteBtn.setLayoutX(780);
            deleteBtn.setLayoutY(770);
            deleteBtn.setOnAction(e->{
                //Gut
            });
            
        pane.getChildren().addAll(AllSong(),UpdateClikedPane(),title1,editBtn,uploadBtn,deleteBtn);

        return pane;   
    }

    @Override
    public AnchorPane mySongPane() {    //Accounts Page 2
        AnchorPane pane = new AnchorPane();
        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);
        
        Label title2 = new Label("Account Management System");
        title2.getStyleClass().add("titleAdmin");
        title2.setLayoutX(50);
        title2.setLayoutY(5);
        
        Button addAccountBtn = CreaButton("Add Account");
        addAccountBtn.setLayoutX(290);
        addAccountBtn.setLayoutY(675); 
        addAccountBtn.setOnAction(e -> {
            register();
            refreshTable();
        });
        
        Button updateAccountBtn = CreaButton("Update Account");
        updateAccountBtn.setLayoutX(520);
        updateAccountBtn.setLayoutY(675); 
        updateAccountBtn.setOnAction(e -> {
            //RACH -<<<
            refreshTable();
        });

        Button deleteAccountBtn = CreaButton("Delete Account");
        deleteAccountBtn.setLayoutX(750);
        deleteAccountBtn.setLayoutY(675);
        deleteAccountBtn.setOnAction(e -> {
            
            try {
                deleteAccountClicked();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Admin_UI.class.getName()).log(Level.SEVERE, null, ex);
            }     
            refreshTable();
        });
        
        pane.getChildren().addAll(addAccountBtn, updateAccountBtn, deleteAccountBtn, tableAccount(), searchBoxMy(),title2);

        return pane;    
    }
     
    private AnchorPane tableAccount() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(933, 500); //1030 - 300 - 60, 700
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(150);
        
        table = new TableView<>();
        
        table.setEditable(true);
        table.setPrefSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(table.getSelectionModel().getSelectedItem().getName()); //.getNameSong()
                
            }
        });

        // Create column Name (Data type of String).
        TableColumn<Account, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(120);

        // Create column Surname (Data type of String).
        TableColumn<Account, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setMinWidth(120);

        // Create column Username (Data type of String).
        TableColumn<Account, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setMinWidth(120);
        
        // Create column Email (Data type of String).
        TableColumn<Account, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(250);
        
        // Create column Gender (Data type of String).
        TableColumn<Account, String> genderCol = new TableColumn<>("Gender");
        genderCol.setMinWidth(90);
        
        // Create column DoB (Data type of LocalDate).
        TableColumn<Account, LocalDate> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setMinWidth(130);
        
        // Create column isAdmin (Data type of Boolean).
        TableColumn<Account, Boolean> adminCol = new TableColumn<>("Admin");
        adminCol.setMinWidth(100);

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
        refreshTable(); // get.list account-> sorted ** see function below
        
        table.getColumns().addAll(nameCol, surnameCol, usernameCol, emailCol, genderCol, dobCol, adminCol);
        anchorPane.getChildren().addAll(table);

        return anchorPane;
    }
    

    @Override
    public HBox searchBoxAll() { // All Song First Page
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setAlignment(Pos.CENTER);
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setOnMouseClicked(e ->{
            Admin_UI.totalPane.getChildren().remove(1);
            Admin_UI.totalPane.getChildren().add(Admin_UI.updateScrollPane(searchTextField.getText()));
        });
        
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            Admin_UI.totalPane.getChildren().remove(1);
            Admin_UI.totalPane.getChildren().add(Admin_UI.updateScrollPane(searchTextField.getText()));
        });

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }

    @Override
    public HBox searchBoxMy() {  // All Account Second page
        HBox hBox = new HBox();
        hBox.setMinSize(670, 30); //1030 - 300 - 60
        hBox.setLayoutX(20);
        hBox.setLayoutY(100);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Account");
        searchTextField.setMinSize(850, 32); //1030 - 300 - 60 - 70

        Button searchButton = CreaButton("Refresh");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 32);
        searchButton.setOnAction(e->{
            searchTextField.clear();
            list.removeAll();
            refreshTable();
            System.out.println("reFresh Table");
        });
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchAccount);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }
    
    @Override
    public BorderPane myAccount() { 
        return new Profile(userAccount).getMainPane();
    }
    
    private static VBox totalPane;
    private ScrollPane  AllSong(){
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(750, 800);
        scrollPane.setLayoutY(100);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPadding(new Insets(10));
        scrollPane.getStyleClass().add("allSong"); //CSS
        
        totalPane = new VBox();
        totalPane.setAlignment(Pos.CENTER);
        totalPane.getStyleClass().add("allSong"); //CSS

        totalPane.getChildren().addAll(searchBoxAll(),updateScrollPane(""));
        
        scrollPane.setContent(totalPane);
        
        return scrollPane;
    }
    
    static Label selectNameSong = new Label("");
    static Label selectArtist = new Label(""); 
    static ImageView selectImage;
    
    private static TilePane updateScrollPane(String text){
        VBox paneContent;
        Button contentButton;
        ImageView imageView;
       
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 10, 10, 10)); // Top,Bottom,Right,Left
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setAlignment(Pos.CENTER);
        
        ObservableList<Song> list = Song.getMyMusicList();
        
        String lowerCase = text.toLowerCase();
        
        for (Song song : list) {
            
            if (song.getNameSong().contains(text) || song.getArtistSong().toLowerCase().contains(lowerCase)) {
                contentButton = new Button();
                contentButton.getStyleClass().add("contentDetailbtn"); //CSS
                contentButton.setOnAction(e->{
                    //SELECTION 
                    Admin_UI.updateVBox.getChildren().removeAll(selectImage,selectNameSong,selectArtist);
            
                    selectNameSong = new Label(song.getNameSong());
                    selectArtist = new Label("ARTIST : " + song.getArtistSong());
                    selectImage = new ImageView(new Image("/image/1.jpg"));   //DATA...Collection from database..
                    selectImage.setFitHeight(300);
                    selectImage.setFitWidth(250); 
                    
                    selectNameSong.getStyleClass().add("nameSong");
                    selectArtist.getStyleClass().add("nameArtist");
                    
                    Admin_UI.updateVBox.getChildren().addAll(selectImage,selectNameSong,selectArtist);        
                });
                
                paneContent = new VBox();
                paneContent.setAlignment(Pos.CENTER);
                paneContent.setPadding(new Insets(10,10,10,10));
                paneContent.getStyleClass().add("content-allSong"); //CSS

                imageView = new ImageView(new Image("/image/1.jpg"));
                imageView.setFitHeight(200); //160
                imageView.setFitWidth(150); //120
                
                
                paneContent.getChildren().addAll(imageView, new Label(song.getNameSong()), new Label("ARTIST : " + song.getArtistSong()));
                contentButton.setGraphic(paneContent);
                contentButton.setMinHeight(300);
                contentButton.setMinWidth(300);

                tilePane.getChildren().add(contentButton);
            }
        }
       return tilePane;
    }
    
    //UPDATE CLICKPANE // RUN ONLY ONCE THE PROGRAM RUN 1 PAGE
    private static VBox updateVBox;
    private AnchorPane UpdateClikedPane(){
        //Image
        AnchorPane updatePane = new AnchorPane();
        updatePane.setLayoutX(760);
        updatePane.setLayoutY(100);
        
        updateVBox = new VBox();

        selectImage = new ImageView(new Image("/image/blankimage.jpg"));
        selectImage.setFitHeight(300);
        selectImage.setFitWidth(250);
        
        selectNameSong = new Label("N/A");
        selectArtist = new Label("Artist: N/A"); 
        
        selectNameSong.getStyleClass().add("nameSong");
        selectArtist.getStyleClass().add("nameArtist");
        
        selectNameSong.setAlignment(Pos.CENTER);
        selectArtist.setAlignment(Pos.CENTER_LEFT);
        
        updateVBox.getChildren().addAll(selectImage,selectNameSong,selectArtist);
        updatePane.getChildren().add(updateVBox);
        
        return updatePane;
    } 
    
    private void register() {
        new Register(true);
    }
        
    private void refreshTable(){ //get.list -> sorted
        //TRY -CATCH FOR EXCEPTION ... NOTHING TO DO WITH IT
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
    }
    
    private int deleteAccountClicked() throws IOException, FileNotFoundException, ClassNotFoundException {
        
        String selectUsername = table.getSelectionModel().getSelectedItem().getUsername();
        String selectEmail = table.getSelectionModel().getSelectedItem().getEmail();
        
        ArrayList<Account> oldAccounts = new ArrayList<>();
        ArrayList<Account> presentAccounts = new ArrayList<>();
        oldAccounts = file.readFile(user);   
        
        for (Account account : oldAccounts) {
            String chkUser = account.getUsername();
            String chkEmail = account.getEmail();
            
            if(selectUsername.equals(userAccount.getUsername()) && selectEmail.equals(userAccount.getEmail())) {
                AlertBox.displayAlert("Delect Account.","You cannot delete your account.");
                presentAccounts.add(account);
            } 
            else if (!(selectUsername.equals(chkUser) && selectEmail.equals(chkEmail))) {  
                presentAccounts.add(account);
            }
            else{
                if(AlertBox.display("Delect Account.","Are you sure to delete this account?")) {
                    AlertBox.displayAlert("Delect Account.","Delete account successed.");
                    System.out.println("delete " + account);
                } else {
                    AlertBox.displayAlert("Delect Account.","Delete account failed.");
                    presentAccounts.add(account);
                }
                    
            }
        }
        
        file.writeFile(user, presentAccounts);
        
        return 1;

    }
    
}
