/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.SearchSystem;
import Component_Music.Song;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author poomi
 */
public class User_UI extends UI {

    // SearchSystem searchSystem = new SearchSystem();
    SearchSystem searchSystemMain = new SearchSystem();
    SearchSystem searchSystemMyLibrary = new SearchSystem();
    String nameSongFromTable;
    Account userAccount;

    public User_UI(Stage stage, Account userAccount) {
        super(stage);
        this.userAccount = userAccount;
        
        Scene scene = new Scene(allPane(), 1280, 960);
        String stylrSheet = getClass().getResource("/style_css/style.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public AnchorPane allSongPane() {
        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(AllSong());

        return pane;
    }

    @Override
    public AnchorPane mySongPane() {
        AnchorPane pane = new AnchorPane();
        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);
        pane.getStyleClass().add("bg-2");
        AnchorPane img = new AnchorPane();
        img.setPrefSize(300, 400);
        img.setLayoutX(1030 - 300 - 20);
        img.setLayoutY(20);

        Image imageMy = new Image("/image/Music_pic.jpg");
        ImageView imgMy = new ImageView(imageMy);
        img.getChildren().add(imgMy);

        Button downloadBtn = CreaButton("Download");
        
        downloadBtn.setLayoutX(1030 - 250 - 20);
        downloadBtn.setLayoutY(420 + 20);

        pane.getChildren().addAll(img, downloadBtn, tableMyMusic(), searchBoxMy());

        return pane;
    }
    

    private Button CreaButton(String text) {
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setMinSize(200, 50);

        return downLoadButton;

    }
    

    private AnchorPane tableMyMusic() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(1030 - 300 - 60, 700);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(100);

        TableView<Song> table = new TableView<>();
        table.setEditable(true);
        table.setMinSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

        table.setOnMouseClicked((event) -> {
//            if (event.getButton().equals(MouseButton.PRIMARY)) {
//                System.out.println(table.getSelectionModel().getSelectedItem().getNameSong());
//            }
            nameSongFromTable = table.getSelectionModel().getSelectedItem().getNameSong() + 
                    table.getSelectionModel().getSelectedItem().getArtistSong() + table.getSelectionModel().getSelectedItem().getDetailSong();
        });

        // Create column UserName (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("Name Song");
        NameCol.setMinWidth(250);

        // Create column Email (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        artistCol.setMinWidth(200);

        // Create column FullName (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("Detail");
        detailCol.setMinWidth(220);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));

        // Set Sort type for userName column
        NameCol.setSortType(TableColumn.SortType.DESCENDING);
        detailCol.setSortable(false);

        // Display row data
        ObservableList<Song> list = Song.getMyMusicList();
        FilteredList<Song> filterData = new FilteredList<>(list, b -> true);
        searchSystemMyLibrary.setFilterData(filterData);

        SortedList<Song> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        table.getColumns().addAll(NameCol, artistCol, detailCol);

        anchorPane.getChildren().addAll(table);

        return anchorPane;
    }

    @Override
    public HBox searchBoxAll() {
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setAlignment(Pos.CENTER);
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setOnMouseClicked(e ->{
            User_UI.totalPane.getChildren().remove(1);
            User_UI.totalPane.getChildren().add(User_UI.updateScrollPane(searchTextField.getText()));
        });
        
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            User_UI.totalPane.getChildren().remove(1);
            User_UI.totalPane.getChildren().add(User_UI.updateScrollPane(searchTextField.getText()));
        });

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }

    @Override
    public HBox searchBoxMy() {
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Refresh");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMyLibrary);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }
    

    public static VBox totalPane;
    private ScrollPane  AllSong(){
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1030, 900);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPadding(new Insets(10));
        scrollPane.getStyleClass().add("allSong");
        totalPane = new VBox();
        totalPane.setAlignment(Pos.CENTER);
        totalPane.getStyleClass().add("allSong");

        totalPane.getChildren().addAll(searchBoxAll(),updateScrollPane(""));
        
        scrollPane.setContent(totalPane);
        
        return scrollPane;
    }
    
    public static TilePane updateScrollPane(String text){
        VBox paneContent;
        Button contentButton;
        ImageView imageView;
       
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setAlignment(Pos.CENTER);
        ObservableList<Song> list = Song.getMyMusicList();
        
        String lowerCase = text.toLowerCase();
        
        for (Song song : list) {
            
            if (song.getNameSong().contains(text) || song.getArtistSong().toLowerCase().contains(lowerCase)) {
                contentButton = new Button();
                contentButton.getStyleClass().add("contentDetailbtn");
                paneContent = new VBox();
                paneContent.setAlignment(Pos.CENTER);
                paneContent.setPadding(new Insets(20));
                paneContent.getStyleClass().add("content-allSong");

                imageView = new ImageView(new Image("/image/1.jpg"));
                imageView.setFitHeight(160);
                imageView.setFitWidth(120);
                
                
                paneContent.getChildren().addAll(imageView, new Label(song.getNameSong()), new Label("ARTIST : "+song.getArtistSong()));
                contentButton.setGraphic(paneContent);

                tilePane.getChildren().add(contentButton);
            }
        }
       return tilePane;
    }
    
    Button editbt;
    Button savebt;
    Button cancelbt;
        
    public BorderPane myAccount() {
        
        BorderPane accountPane = new BorderPane();
        BorderPane mainPane = new BorderPane();
        MyAccount myAccount = new MyAccount(userAccount);
                
        VBox head = new VBox(10);
        head.setPadding(new Insets(0,10,20,20));
        head.getChildren().add(new Text("MY ACCOUNT"));
        
         HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(20,20,0,0));
        bottom.setAlignment(Pos.CENTER_RIGHT);
        
        savebt = new Button("Save");
        savebt.setOnAction(event -> {
            if(myAccount.saveAccount()) {
                AlertBox.displayAlert("Edit Profile", "Saved.");;
                userAccount = myAccount.getMyAccount();
                myAccount.showAccount(userAccount);
                accountPane.setCenter(myAccount.getProfilePane());
                bottom.getChildren().clear();
                bottom.getChildren().addAll(editbt);
            } 
        });
        
        cancelbt = new Button("Cancel");
        cancelbt.setOnAction(event -> {
            myAccount.showAccount(userAccount);
            accountPane.setCenter(myAccount.getProfilePane());
            bottom.getChildren().clear();
            bottom.getChildren().addAll(editbt);
        });
        
        editbt = new Button("Edit");
        VBox right = new VBox(10);
        right.setPadding(new Insets(20,20,20,20));
        editbt.setOnAction(event -> {
            myAccount.editAccount();
            accountPane.setCenter(myAccount.getProfilePane());
            bottom.getChildren().clear();
            bottom.getChildren().addAll(cancelbt, savebt);
        });
        
                bottom.getChildren().add(editbt);
        
        accountPane.setTop(head);
        accountPane.setCenter(myAccount.getProfilePane());
        accountPane.setPadding(new Insets(50, 50, 50, 50));
        accountPane.setStyle("-fx-background-color: white");
        accountPane.setBottom(bottom);
        
        mainPane.setCenter(accountPane);
        mainPane.setPadding(new Insets(50, 100, 0, 100));
        
        return mainPane;
    }

}
