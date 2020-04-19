/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.DetailSongPopUp;
import Component_Music.SearchSystem;
import Component_Music.Song;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author poomi
 */
public class Guest_UI extends UI {

    SearchSystem searchSystemMyLibrary = new SearchSystem();

    // Create File for downloader
   private File fileForDownload;
   private String songNameSelected;
   private String nameSet;
   private Song songSelected;
   private Account userAccount;
   private String page;

    public Guest_UI(Stage stage, Account userAccount) {
        super(stage,userAccount);
        this.userAccount = userAccount;

        Scene scene = new Scene(allPane(), 1280, 960);
        String stylrSheet = getClass().getResource("/style_css/style.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public AnchorPane firstPagePane(String page) {
        AnchorPane pane = new AnchorPane();
        this.page = page;
        pane.getChildren().addAll(AllSong());

        return pane;
    }
    
    
    private AnchorPane pane = new AnchorPane();
    private VBox detailDownload = new VBox(10);
    
    @Override
    public AnchorPane secondPagePane() {
        return null;
    }
    
    public void updateDetailDownload(){
        pane.getChildren().remove(0);
        ((Label)detailDownload.getChildren().get(0)).setText("Song : "+ songSelected.getNameSong());
        ((Label)detailDownload.getChildren().get(1)).setText("Artist : "+ songSelected.getArtistSong());
        ((Label)detailDownload.getChildren().get(2)).setText("Downloadable(Time) : "+"3"); // wait
        
        AnchorPane img = new AnchorPane();
        img.setMaxSize(300, 400);
        img.setLayoutX(1030 - 300 - 20);
        img.setLayoutY(20);

        Image imageMy = songSelected.getPhoto();
        ImageView imgMy = new ImageView(imageMy);
        imgMy.setFitHeight(400);
        img.getChildren().add(imgMy);
        
        pane.getChildren().add(0, img);
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
        table.setPrefSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());
 
        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                //System.out.println(table.getSelectionModel().getSelectedItem().getNameSong());
                songSelected = table.getSelectionModel().getSelectedItem();
                updateDetailDownload();
                songNameSelected = table.getSelectionModel().getSelectedItem().getNameSong() + table.getSelectionModel().getSelectedItem().getArtistSong() + table.getSelectionModel().getSelectedItem().getDetailSong();
                nameSet = table.getSelectionModel().getSelectedItem().getNameSong();
                System.out.println(songNameSelected);
                fileForDownload = new File("src/MusicFile/" + songNameSelected + ".mp3");
                
               // System.out.println(NameCol.getWidth() + " "+artistCol.getWidth()+ " "+detailCol.getWidth()+ " "+Downloadable.getWidth());
            }
        });

               // Create column NameSong (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("Name Song");
        NameCol.setMinWidth(200);

        // Create column NameArtist (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        artistCol.setMinWidth(150);

        // Create column Detail (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("Detail");
        detailCol.setMinWidth(220);
        
//        // Create column Downloadable (Data type of String).
//        TableColumn<Song, String> Downloadable = new TableColumn<>("Downloadable");
//        detailCol.setMinWidth(100);
        

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));
        //Downloadable.setCellValueFactory(new PropertyValueFactory<>("downloadable")); // wait nichida add dowloadable in account

        // Set Sort type for userName column
        NameCol.setSortType(TableColumn.SortType.DESCENDING);
        detailCol.setSortable(false);

        // Display row data
        ObservableList<Song> list = userAccount.getMyListSong();
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
        searchButton.setOnMouseClicked(e -> {
            Guest_UI.totalPane.getChildren().remove(1);
            Guest_UI.totalPane.getChildren().add(updateScrollPane(searchTextField.getText()));
        });

        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            Guest_UI.totalPane.getChildren().remove(1);
            Guest_UI.totalPane.getChildren().add(updateScrollPane(searchTextField.getText()));
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

    private ScrollPane AllSong() {

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1030, 900);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPadding(new Insets(10));
        scrollPane.getStyleClass().add("scroll-bar");
        totalPane = new VBox();
        totalPane.setAlignment(Pos.CENTER);
        totalPane.getStyleClass().add("allSong");

        totalPane.getChildren().addAll(searchBoxAll(), updateScrollPane(""));

        scrollPane.setContent(totalPane);

        return scrollPane;
    }

    public TilePane updateScrollPane(String text) {
        VBox paneContent;
        Button contentButton;
        ImageView imageView;

        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10, 10, 10, 10));
        tilePane.setVgap(10);
        tilePane.setHgap(10);
        tilePane.setAlignment(Pos.CENTER_LEFT); // By POP
        ObservableList<Song> list = null;
        try {
            list = Song.getMyMusicList();
        } catch (IOException ex) {
            System.out.println("song 1");
        } catch (ClassNotFoundException ex) {
            System.out.println("song 2");
        }

        String lowerCase = text.toLowerCase();

        for (Song song : list) {

            if ((song.getNameSong().toLowerCase().contains(lowerCase) || song.getArtistSong().toLowerCase().contains(lowerCase))) {

                boolean inMyList = false;
                for (Song song1 : userAccount.getMyListSong()) {
                    System.out.println("1");
                    if (!userAccount.isFirstSong()) {
                        if ((song.getNameSong().toLowerCase().contains(song1.getNameSong().toLowerCase()) && song.getArtistSong().toLowerCase().contains(song1.getArtistSong().toLowerCase()))) {
                            System.out.println("2");
                            inMyList = true;
                            break;
                        }
                    }

                }

                if (!inMyList) {
                    contentButton = new Button();
                    contentButton.getStyleClass().add("contentDetailbtn");
                    paneContent = new VBox();
                    paneContent.setAlignment(Pos.CENTER);
                    paneContent.setPadding(new Insets(20));
                    paneContent.getStyleClass().add("content-allSong");

                    imageView = new ImageView(new Image("/image/1.jpg"));
                    imageView.setFitHeight(200); // By pop
                    imageView.setFitWidth(150); // By pop

                    paneContent.getChildren().addAll(imageView, new Label(song.getNameSong()), new Label("ARTIST : " + song.getArtistSong()));
                    contentButton.setGraphic(paneContent);
                    contentButton.setMinHeight(300); // By Pop
                    contentButton.setMinWidth(300); // By Pop
                    contentButton.setOnMouseClicked(e -> {
                        try {
                            new DetailSongPopUp(song);
                        } catch (InterruptedException ex) {
                            System.out.println("Detail Song Popup : " + ex);
                        }
                    });

                    tilePane.getChildren().add(contentButton);
                }
            }
        }
        return tilePane;
    }


    @Override
    public BorderPane myAccount() {
        return null;
    }

    public void downloader() {

        System.out.println("Download");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        fileChooser.setInitialFileName(nameSet);
        File downloadFile = fileChooser.showSaveDialog(null);

        if (downloadFile != null) {
            try {
                Files.copy(fileForDownload.toPath(), downloadFile.toPath());
            } catch (IOException ex) {
                System.out.println("DownloadFile" + ex);
            }
        }

    }

    ArrayList<Account> updateAccount = new ArrayList<>();
    File user = new File("src/data/user.dat");

    @Override
    public void userLogout() {

        ReadWriteFile file = new ReadWriteFile();
        ArrayList<Account> nowAccount = null;

        try {
            nowAccount = file.readFile(user);
        } catch (IOException ex) {
            Logger.getLogger(Guest_UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Guest_UI.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Account account : nowAccount) {
            if (account.getUsername().equals(userAccount.getUsername())) {
                updateAccount.add(userAccount);
            } else {
                updateAccount.add(account);
            }
        }

        try {
            file.writeFile(user, updateAccount);
        } catch (IOException ex) {
            Logger.getLogger(Guest_UI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
