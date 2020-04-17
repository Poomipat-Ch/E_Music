/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.SearchSystem;
import Component_Music.SearchSystemAccount;
import Component_Music.Song;
import Component_Music.Artist;
import Component_Music.UploadArtistPopUp;
import Component_Music.UploadSongPopUp;
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
import javafx.scene.control.Label;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sirawit
 */
public class Admin_UI extends UI {

    LocalDate dOB;
    File user = new File("src/data/user.dat");
    boolean dateSet = false;
    ArrayList<Account> listAccount = new ArrayList<>();
    ArrayList<Account> addAccount = new ArrayList<>();

    //Gut add
    static String songSelectString;
//    static ObservableList<Song> songArrayList;
    static ArrayList<Song> songArrayList = new ArrayList<>();
    static ArrayList<Artist> artistArrayList = new ArrayList<>();
    static File musicFile = new File("src/data/music.dat");
    static File artistFile = new File("src/data/artist.dat");
    static String editName, editArtist, editDetail, editPrice;
    static Image editImage;

    ObservableList<Account> list = null;

    SearchSystem searchSystemMain = new SearchSystem();
    SearchSystemAccount searchAccount = new SearchSystemAccount();

    private TableView<Account> table;

    private Account userAccount;

    private ReadWriteFile file = new ReadWriteFile();
    
    public Admin_UI () {
        
    }

    public Admin_UI(Stage stage, Account userAccount) {

        super(stage, userAccount);

        try {
            songArrayList = ReadWriteFile.readFileSong(musicFile);
        } catch (Exception e) {
            System.out.println("readFile Song in Admin_UI constuctor ERROR!!!!!");
        }
        
        try {
            artistArrayList = ReadWriteFile.readFileArist(artistFile);
        } catch (Exception e) {
            System.out.println("readFile Artist in Admin_UI constuctor ERROR!!!!!");
        }

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

    String page;

    @Override
    public AnchorPane firstPagePane(String page) {   //First Page 1
        AnchorPane pane = new AnchorPane();
        this.page = page;

        Label title1 = new Label("Music Management / จัดการเพลง");
        title1.getStyleClass().add("titleAdmin");
        title1.setLayoutX(50);
        title1.setLayoutY(5);
        
        Button editSongBtn = CreaButton("Edit Song");       //Edit Button
        editSongBtn.setLayoutX(780);
        editSongBtn.setLayoutY(600);
        editSongBtn.setOnAction(e -> {
            new UploadSongPopUp("Edit Song",editName,editArtist,editDetail,editPrice,"src/MusicFile/"+songSelectString+".mp3",editImage,null); 
        });

        Button uploadBtn = CreaButton("Upload");        //Upload Button
        uploadBtn.setLayoutX(780);
        uploadBtn.setLayoutY(700);
        uploadBtn.setOnAction(e -> {
            new UploadSongPopUp("Upload Song");

        });

        Button deleteBtn = CreaButton("Delete");        //Delete Button
        deleteBtn.setLayoutX(780);
        deleteBtn.setLayoutY(770);
        deleteBtn.setOnAction(e -> {

            try {
                deleteSongClicked();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error: delete Song has a problem when clicked");
            }

        });

        pane.getChildren().addAll(searchBoxAll(),AllSong(), UpdateClikedPane(), title1, editSongBtn, uploadBtn, deleteBtn); 

        return pane;
    }
    
    public AnchorPane ArtistPane(String page) {   //Second Page 2
        AnchorPane pane = new AnchorPane();
        this.page = page;

        Label title2 = new Label("Artist Management / จัดการศิลปิน");
        title2.getStyleClass().add("titleAdmin");
        title2.setLayoutX(50);
        title2.setLayoutY(5);

        Button editArtistBtn = CreaButton("Edit Artist");       //New Artist
        editArtistBtn.setLayoutX(780);
        editArtistBtn.setLayoutY(600);
        editArtistBtn.setOnAction(e -> {
            new UploadArtistPopUp("Upload Artist","select Name Artist1","select Name Artist2(Optional)","select detail",new Image("/image/defaultprofile.png")); //<<-- Gut 
        });
        
        Button newArtistBtn = CreaButton("New Artist");       //New Artist
        newArtistBtn.setLayoutX(780);
        newArtistBtn.setLayoutY(700);
        newArtistBtn.setOnAction(e -> {
            new UploadArtistPopUp("Upload Artist");
        });

        Button deleteArtistBtn = CreaButton("Delete");        //Delete Button
        deleteArtistBtn.setLayoutX(780);
        deleteArtistBtn.setLayoutY(770);
        deleteArtistBtn.setOnAction(e -> {
            try {
                deleteArtistClicked();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Error: delete Artist has a problem when clicked");
            }
        });

        pane.getChildren().addAll(searchArtistBox(), AllArtist(),UpdateClikedArtistPane(), title2, editArtistBtn, newArtistBtn,deleteArtistBtn); // editArtistBtn, newArtistBtn, 

        return pane;
    }
    
    @Override
    public AnchorPane secondPagePane() {    //Accounts Page 3
        AnchorPane pane = new AnchorPane();
        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);

        Label title2 = new Label("Account Management / จัดการบัญชีผู้ใช้");
        title2.getStyleClass().add("titleAdmin");
        title2.setLayoutX(50);
        title2.setLayoutY(5);

        Button addAccountBtn = CreaButton("Add Account");
        addAccountBtn.setLayoutX(290);
        addAccountBtn.setLayoutY(675);
        addAccountBtn.setOnAction(e -> {
            addAccountClicked();
            refreshTable();
        });

        Button updateAccountBtn = CreaButton("Update Account");
        updateAccountBtn.setLayoutX(520);
        updateAccountBtn.setLayoutY(675);
        updateAccountBtn.setOnAction(e -> {
            updateAccountClicked();
            refreshTable();
        });

        Button deleteAccountBtn = CreaButton("Delete Account");
        deleteAccountBtn.setLayoutX(750);
        deleteAccountBtn.setLayoutY(675);
        deleteAccountBtn.setOnAction(e -> {

            try {
                deleteAccountClicked();
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Admin_UI : Exeption in mySongPane");
            }
            refreshTable();
        });

        pane.getChildren().addAll(addAccountBtn, updateAccountBtn, deleteAccountBtn, tableAccount(), searchBoxMy(), title2);

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

        // Create column Role (Data type of String).
        TableColumn<Account, String> adminCol = new TableColumn<>("Role");
        adminCol.setMinWidth(101);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        adminCol.setCellValueFactory(new PropertyValueFactory<>("userRole"));

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
        hBox.setLayoutX(40);
        hBox.setLayoutY(90);
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.getStyleClass().add("searchBox");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setOnMouseClicked(e -> {
            Admin_UI.totalPane.getChildren().remove(0);
            Admin_UI.totalPane.getChildren().add(updateScrollPane(searchTextField.getText()));
        });

        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            Admin_UI.totalPane.getChildren().remove(0);
            Admin_UI.totalPane.getChildren().add(updateScrollPane(searchTextField.getText()));
        });

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }
    
    public HBox searchArtistBox() { // All Song First Page
        HBox hBox = new HBox();
        hBox.setLayoutX(40);
        hBox.setLayoutY(90);
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Artist");
        searchTextField.getStyleClass().add("searchBox");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setOnMouseClicked(e -> {
            Admin_UI.totalArtistPane.getChildren().remove(0);
            Admin_UI.totalArtistPane.getChildren().add(updateScrollArtistPane(searchTextField.getText()));
        });

        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            Admin_UI.totalArtistPane.getChildren().remove(0);
            Admin_UI.totalArtistPane.getChildren().add(updateScrollArtistPane(searchTextField.getText()));
        });

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }

    @Override
    public HBox searchBoxMy() {  // All Account Third page // Account
        HBox hBox = new HBox();
        hBox.setMinSize(670, 30); //1030 - 300 - 60
        hBox.setLayoutX(20);
        hBox.setLayoutY(100);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Account");
        searchTextField.setMinSize(850, 32); //1030 - 300 - 60 - 70
        searchTextField.getStyleClass().add("searchBox");

        Button searchButton = CreaButton("Refresh");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 32);
        searchButton.setOnAction(e -> {
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

    public static VBox totalPane;

    private ScrollPane AllSong() {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(750, 750);
        scrollPane.setLayoutY(140);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPadding(new Insets(10));
        scrollPane.getStyleClass().add("allSong"); //CSS
        scrollPane.getStyleClass().add("scroll-bar");
        totalPane = new VBox();
        totalPane.setAlignment(Pos.CENTER);
        totalPane.getStyleClass().add("allSong"); //CSS
        totalPane.getChildren().addAll(updateScrollPane(""));

        scrollPane.setContent(totalPane);

        return scrollPane;
    }
    
    public static VBox totalArtistPane;
     
    private ScrollPane AllArtist() {

        ScrollPane scrollArtistPane = new ScrollPane();
        scrollArtistPane.setPrefSize(750, 750);
        scrollArtistPane.setLayoutY(140);
        scrollArtistPane.pannableProperty().set(true);
        scrollArtistPane.fitToWidthProperty().set(true);
        scrollArtistPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollArtistPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollArtistPane.setPadding(new Insets(10));
        scrollArtistPane.getStyleClass().add("allSong"); //CSS
        scrollArtistPane.getStyleClass().add("scroll-bar");
        totalArtistPane = new VBox();
        totalArtistPane.setAlignment(Pos.CENTER);
        totalArtistPane.getStyleClass().add("allSong"); //CSS
        totalArtistPane.getChildren().addAll(updateScrollArtistPane(""));

        scrollArtistPane.setContent(totalArtistPane);

        return scrollArtistPane;
    }

    static Label selectNameSong = new Label("");
    static Label selectArtist = new Label("");
    static ImageView selectImage;

    public static TilePane updateScrollPane(String text) {

        VBox paneContent;
        Button contentButton;
        ImageView imageView;

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 10, 10, 10)); // Top,Bottom,Right,Left
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setAlignment(Pos.CENTER);

        String lowerCase = text.toLowerCase();
        try {
            songArrayList = ReadWriteFile.readFileSong(musicFile);
        } catch (Exception e) {
            System.out.println("Admin_UI : dExeption in updateScrollPane");
        }
        
        for (Song song : songArrayList) {
            
            for (String styleSong : song.getListStyleSong()) {
                
                if ((song.getNameSong().toLowerCase().contains(lowerCase) || song.getArtistSong().toLowerCase().contains(lowerCase) )) {
                    contentButton = new Button();
                    contentButton.getStyleClass().add("contentDetailbtn"); //CSS           
                    contentButton.setOnAction(e -> {
                        //SELECTION 
                        Admin_UI.updateVBox.getChildren().removeAll(selectImage, selectNameSong, selectArtist);

                        selectNameSong = new Label(song.getNameSong());
                        selectArtist = new Label("ARTIST : " + song.getArtistSong());
                        selectImage = new ImageView(song.getPhoto());   //DATA...Collection from database..
                        selectImage.setFitHeight(250);
                        selectImage.setFitWidth(250);

                        //Gut add
                        songSelectString = song.getNameSong() + song.getArtistSong() + song.getDetailSong();
                        System.out.println(songSelectString + " is selected");
                        editName = song.getNameSong();
                        editArtist = song.getArtistSong();
                        editDetail = song.getDetailSong();
                        editPrice = song.getPriceSong();
                        editImage = song.getPhoto();
                        
                        selectImage.getStyleClass().add("pictureAppear");
                        selectNameSong.getStyleClass().add("nameSong");
                        selectArtist.getStyleClass().add("nameArtist");

                        Admin_UI.updateVBox.getChildren().addAll(selectImage, selectNameSong, selectArtist);
                    });

                    paneContent = new VBox();
                    paneContent.setAlignment(Pos.CENTER);
                    paneContent.setPadding(new Insets(10, 10, 10, 10));
                    paneContent.getStyleClass().add("content-allSong"); //CSS

                    imageView = new ImageView(song.getPhoto());
                    imageView.setFitHeight(180); //160
                    imageView.setFitWidth(180); //120

                    paneContent.getChildren().addAll(imageView, new Label(song.getNameSong()), new Label("ARTIST : " + song.getArtistSong()));
                    contentButton.setGraphic(paneContent);
                    contentButton.setMinHeight(300);
                    contentButton.setMinWidth(300);

                    tilePane.getChildren().add(contentButton);
                    
                    break;
                }
            }
        }
        return tilePane;
    }
    
    static Label selectArtist2 = new Label("");
    static Label selectDetail2 = new Label("");
    static ImageView selectImageArtist2;
    
    public static TilePane updateScrollArtistPane(String text){
        
        try {
            artistArrayList = ReadWriteFile.readFileArist(artistFile);
        } catch (Exception e) {
            System.out.println("readFile Artist in Admin_UI updateScrollArtistPane ERROR!!!!!");
        }

        VBox paneContent;
        Button contentButton;
        ImageView imageView;

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 10, 10, 10)); // Top,Bottom,Right,Left
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setAlignment(Pos.CENTER);

        String lowerCase = text.toLowerCase();
        
        for (Artist artist : artistArrayList) {
            
            if ((artist.getName1().toLowerCase().contains(lowerCase) || artist.getName2().toLowerCase().contains(lowerCase) || artist.getInfomation().toLowerCase().contains(lowerCase))) {
                contentButton = new Button();
                contentButton.getStyleClass().add("contentDetailbtn"); //CSS           
                contentButton.setOnAction(e -> {
                    //SELECTION 
                    Admin_UI.updateArtistVBox.getChildren().removeAll(selectImageArtist2, selectArtist2, selectDetail2);

                    selectArtist2 = new Label("selected Artists");
                    selectDetail2 = new Label("selected Detail");
                    selectImageArtist2 = new ImageView(new Image("/image/defaultprofile.png"));   //DATA...Collection from database..
                    selectImageArtist2.setFitHeight(250);
                    selectImageArtist2.setFitWidth(250);

                    selectImageArtist2.getStyleClass().add("pictureAppear");
                    selectArtist2.getStyleClass().add("nameSong");
                    selectDetail2.getStyleClass().add("nameArtist");

                    Admin_UI.updateArtistVBox.getChildren().addAll(selectImageArtist2, selectArtist2, selectDetail2);
                });

                paneContent = new VBox();
                paneContent.setAlignment(Pos.CENTER);
                paneContent.setPadding(new Insets(10, 10, 10, 10));
                paneContent.getStyleClass().add("content-allSong"); //CSS

                imageView = new ImageView(artist.getPhoto());
                imageView.setFitHeight(180); //160
                imageView.setFitWidth(180); //120

                paneContent.getChildren().addAll(imageView, new Label(artist.getName1()), new Label("Detail : " + artist.getInfomation()));
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

    private AnchorPane UpdateClikedPane() {

        //Image
        AnchorPane updatePane = new AnchorPane();
        updatePane.setLayoutX(765);
        updatePane.setLayoutY(100);

        updateVBox = new VBox(10);

        selectImage = new ImageView(new Image("/image/defaultmusic.png"));
        selectImage.setFitHeight(230);
        selectImage.setFitWidth(230);

        selectNameSong = new Label("Please select song");
        selectArtist = new Label("");

        selectImage.getStyleClass().add("pictureAppear");
        selectNameSong.getStyleClass().add("nameSong");
        selectArtist.getStyleClass().add("nameArtist");

        selectNameSong.setAlignment(Pos.CENTER);
        selectArtist.setAlignment(Pos.CENTER_LEFT);
        
        if(selectArtist.getText().equals("")) { selectNameSong.setTranslateY(20); } // Ship Y axis 
        else{ selectNameSong.setTranslateY(0); }

        updateVBox.setAlignment(Pos.CENTER);
        updateVBox.getChildren().addAll(selectImage, selectNameSong, selectArtist);
        updatePane.getChildren().add(updateVBox);

        return updatePane;

    }
    
    //UPDATE CLICKPANE ARTIST // RUN ONLY ONCE THE PROGRAM RUN 2 PAGE
    private static VBox updateArtistVBox;

    private AnchorPane UpdateClikedArtistPane() {

        //Image
        AnchorPane updatePane = new AnchorPane();
        updatePane.setLayoutX(765);
        updatePane.setLayoutY(100);

        updateArtistVBox = new VBox(10);

        selectImageArtist2 = new ImageView(new Image("/image/defaultprofile.png"));
        selectImageArtist2.setFitHeight(230);
        selectImageArtist2.setFitWidth(230);

        selectArtist2 = new Label("Please select artist");
        selectDetail2 = new Label("");

        selectImageArtist2.getStyleClass().add("pictureAppear");
        selectArtist2.getStyleClass().add("nameSong");
        selectDetail2.getStyleClass().add("nameArtist");

        selectArtist2.setAlignment(Pos.CENTER);
        selectDetail2.setAlignment(Pos.CENTER_LEFT);
        
        if(selectDetail2.getText().equals("")) { selectArtist2.setTranslateY(20); } // Ship Y axis 
        else{ selectArtist2.setTranslateY(0); }

        updateArtistVBox.setAlignment(Pos.CENTER);
        updateArtistVBox.getChildren().addAll(selectImageArtist2, selectArtist2, selectDetail2);
        updatePane.getChildren().add(updateArtistVBox);

        return updatePane;

    }    

    private void refreshTable() { //get.list -> sorted

        //TRY -CATCH FOR EXCEPTION ... NOTHING TO DO WITH IT
        try {
            list = Account.getAccountList();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Admin_UI : dExeption in rereshTable");
        }

        //Filter for Search and Sorted
        FilteredList<Account> filterData = new FilteredList<>(list, b -> true);
        searchAccount.setFilterData(filterData);

        SortedList<Account> sortedList = new SortedList<>(searchAccount.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(filterData);
    }

    private void addAccountClicked() {

        new Register("admin");
    }

    ImageView photo;
    ArrayList<Account> oldAccounts;
    ArrayList<Account> presentAccounts;
    Account updateAccount;

    private int updateAccountClicked() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit User/Admin Account");
        stage.setResizable(false);

        oldAccounts = new ArrayList<>();
        presentAccounts = new ArrayList<>();
        updateAccount = new Account();

        String selectUsername = table.getSelectionModel().getSelectedItem().getUsername();
        String selectEmail = table.getSelectionModel().getSelectedItem().getEmail();

        try {
            oldAccounts = file.readFile(user);
        } catch (IOException ex) {
            System.out.println("Admin_UI : IOExeption readfile in updateAccountClick");
        } catch (ClassNotFoundException ex) {
            System.out.println("Admin_UI : ClassNotFoundExeption readfile in upDateAccountClick");
        }

        if (selectUsername.equals(userAccount.getUsername()) && selectEmail.equals(userAccount.getEmail())) {
            AlertBox.displayAlert("Delect Account.", "You cannot delete your account.");
            return 0;
        }

        ToggleGroup statusToggle = new ToggleGroup();
        RadioButton adminSelect = new RadioButton("Admin");
        RadioButton premiumSelect = new RadioButton("Premium");
        RadioButton userSelect = new RadioButton("Member");
        adminSelect.setToggleGroup(statusToggle);
        premiumSelect.setToggleGroup(statusToggle);
        userSelect.setToggleGroup(statusToggle);

        for (Account account : oldAccounts) {
            String chkUser = account.getUsername();
            String chkEmail = account.getEmail();

            if (selectUsername.equals(chkUser) && selectEmail.equals(chkEmail)) {
                updateAccount = account;
                if (updateAccount.getUserRole().equals("admin")) {
                    statusToggle.selectToggle(adminSelect);
                } 
                else if(updateAccount.getUserRole().equals("premium")){
                    statusToggle.selectToggle(premiumSelect);
                }
                else {
                    statusToggle.selectToggle(userSelect);
                }
            } else {
                presentAccounts.add(account);
            }
        }

        photo = new ImageView(updateAccount.getPhoto());
        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);

        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(e -> {

            if(adminSelect.isSelected())
                updateAccount.setUserRole("admin");
            else if(premiumSelect.isSelected())
                updateAccount.setUserRole("premium");
            else
                updateAccount.setUserRole("member");

            presentAccounts.add(updateAccount);

            try {
                file.writeFile(user, presentAccounts);
            } catch (IOException ex) {
                System.out.println("Admin_UI : IOExeption writefile in updateAccountClicked");
            }

            stage.close();

            refreshTable();
        });

        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            stage.close();
        });

        VBox row0 = new VBox(10);
        row0.getChildren().addAll(photo);
        row0.setAlignment(Pos.CENTER);
        row0.setPadding(new Insets(10));
        row0.setMinHeight(200);

        HBox row1 = new HBox(20);
        row1.getChildren().addAll(new Label("Username : "), new Label(updateAccount.getUsername()));
        row1.setAlignment(Pos.CENTER);
        row1.setPadding(new Insets(10));

        HBox row2 = new HBox(20);
        row2.getChildren().addAll(new Label("Name : "), new Label(updateAccount.getName()), new Label(updateAccount.getSurname()));
        row2.setAlignment(Pos.CENTER);
        row2.setPadding(new Insets(10));

        HBox row3 = new HBox(20);
        row3.getChildren().addAll(new Label("Status : "), userSelect, premiumSelect, adminSelect);
        row3.setAlignment(Pos.CENTER);
        row3.setPadding(new Insets(30));

        HBox row4 = new HBox(20);
        row4.getChildren().addAll(yesBtn, noBtn);
        row4.setAlignment(Pos.CENTER);
        row4.setPadding(new Insets(20));

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(row0, row1, row2, row3, row4);
        layout1.setAlignment(Pos.TOP_CENTER);
        layout1.setPadding(new Insets(30, 0, 0, 0));

        Scene scene = new Scene(layout1, 500, 600);

        stage.setScene(scene);
        stage.showAndWait();

        return 0;
    }

    private int deleteAccountClicked() throws IOException, FileNotFoundException, ClassNotFoundException {

        String selectUsername = table.getSelectionModel().getSelectedItem().getUsername();
        String selectEmail = table.getSelectionModel().getSelectedItem().getEmail();

        ArrayList<Account> oldAccounts = new ArrayList<>();
        ArrayList<Account> presentAccounts = new ArrayList<>();

        oldAccounts = file.readFile(user);

        if (selectUsername.equals(userAccount.getUsername()) && selectEmail.equals(userAccount.getEmail())) {
            AlertBox.displayAlert("Delect Account.", "You cannot delete your account.");
            return 0;
        }

        for (Account account : oldAccounts) {
            String chkUser = account.getUsername();
            String chkEmail = account.getEmail();

            if (selectUsername.equals(userAccount.getUsername()) && selectEmail.equals(userAccount.getEmail())) {
                AlertBox.displayAlert("Delect Account.", "You cannot delete your account.");
                presentAccounts.add(account);
            } else if (!(selectUsername.equals(chkUser) && selectEmail.equals(chkEmail))) {

                presentAccounts.add(account);

            } else {
                if (AlertBox.display("Delect Account.", "Are you sure to delete this account?")) {
                    AlertBox.displayAlert("Delect Account.", "Delete account successed.");

                    System.out.println("delete " + account);
                } else {
                    AlertBox.displayAlert("Delect Account.", "Delete account failed.");
                    return 0;
                }

            }
        }

        file.writeFile(user, presentAccounts);

        return 1;

    }

    private int deleteSongClicked() throws IOException, FileNotFoundException, ClassNotFoundException {

        ArrayList<Song> oldSongList = new ArrayList<Song>();
        ArrayList<Song> newSongList = new ArrayList<Song>();
        File selectFileDelete = new File("src/MusicFile/" + songSelectString + ".mp3");

        try {
            oldSongList = ReadWriteFile.readFileSong(musicFile);
        } catch (IOException ex) {
            System.out.println("Admin_UI : IOExeption readfile in deleteSongClicked");
        } catch (ClassNotFoundException ex) {
            System.out.println("Admin_UI : ClassNotFoundExeption readfile in deleteSongClickede");
        }

        for (Song song : oldSongList) {

            if (songSelectString.equals(song.getNameSong() + song.getArtistSong() + song.getDetailSong())) {
                System.out.println("delete " + song);
                selectFileDelete.delete();

            } else {
                newSongList.add(song);
            }
        }

        ReadWriteFile.writeFileSong(musicFile, newSongList);
        Admin_UI.totalPane.getChildren().remove(0);
        Admin_UI.totalPane.getChildren().add(updateScrollPane(""));

        return 1;
    }

    
    private int deleteArtistClicked() throws IOException, FileNotFoundException, ClassNotFoundException {

        ArrayList<Artist> oldArtistList = new ArrayList<>();
        ArrayList<Artist> newArtistList = new ArrayList<>();
        File selectFileDelete = new File("src/MusicFile/" + songSelectString + ".mp3");

        
        //Gut ... Do one favor for me By Pop VVV
        
        
//        try {
//            oldSongList = ReadWriteFile.readFileSong(musicFile);
//        } catch (IOException ex) {
//            System.out.println("Admin_UI : IOExeption readfile in deleteSongClicked");
//        } catch (ClassNotFoundException ex) {
//            System.out.println("Admin_UI : ClassNotFoundExeption readfile in deleteSongClickede");
//        }
//
//        for (Song song : oldSongList) {
//
//            if (songSelectString.equals(song.getNameSong() + song.getArtistSong() + song.getDetailSong())) {
//                System.out.println("delete " + song);
//                selectFileDelete.delete();
//
//            } else {
//                newSongList.add(song);
//            }
//        }
//
//        ReadWriteFile.writeFileSong(musicFile, newSongList);
        Admin_UI.totalArtistPane.getChildren().remove(0);
        Admin_UI.totalArtistPane.getChildren().add(updateScrollArtistPane(""));

        return 1;
    }
    
    
    @Override
    public void userLogout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
