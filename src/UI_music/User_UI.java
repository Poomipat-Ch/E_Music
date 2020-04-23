
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AddSong;
import Component_Music.Artist;
import Component_Music.DetailSongPopUp;
import Component_Music.SearchPage;
import Component_Music.SearchSystemAddSong;
import Component_Music.Song;
import Component_Music.TopChartPane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
public class User_UI extends UI {

    private SearchSystemAddSong searchSystemMyLibrary = new SearchSystemAddSong();

    // Create File for downloader
    private File fileForDownload;
    private String songNameSelected;
    private String nameSet;
    private AddSong songSelected;
    private String page;

    private ReadWriteFile writeFile = new ReadWriteFile();
    private ObservableList<AddSong> list;
    private Stage stage;

    private File musicfile = new File("src/data/music.dat");
    private File artistfile = new File("src/data/artist.dat");

    public static ArrayList<Song> SongArrayList = new ArrayList<>();
    public static ArrayList<Artist> ArtistArrayList = new ArrayList<>();
    public static ObservableList<Account> addAccount;
    public static String playerStatus;

    public User_UI() {
    }

    public User_UI(Stage stage, Account userAccount) {
        super(stage, userAccount);

        this.stage = stage;

        try {
            SongArrayList = ReadWriteFile.readFileSong(musicfile);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("SearchPage: ERROR READ MUSIC.DAT");
        }

        try {
            ArtistArrayList = new ReadWriteFile().readFileArtist(artistfile);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("SearchPage: ERROR READ MUSIC.DAT");
        }
        this.playerStatus = "NotReady";

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
    public AnchorPane thirdPagePane() {
        AnchorPane pane = this.pane;
        pane.getChildren().clear();
        detailDownload.getChildren().clear();
        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);
        pane.getStyleClass().add("bg-2");
        AnchorPane img = new AnchorPane();
        img.setPrefSize(300, 400);
        img.setLayoutX(1030 - 300 - 20);
        img.setLayoutY(20);

        Image imageMy = null;
        ImageView imgMy = new ImageView(imageMy);
        img.getChildren().add(imgMy);

        detailDownload.getStyleClass().add("downloadSelected");
        detailDownload.setLayoutX(1030 - 300 - 20);
        detailDownload.setLayoutY(450);
        Label nameSong = new Label();
        Label nameArtist = new Label();
        Label DownloadAble = new Label();
        detailDownload.getChildren().addAll(nameSong, nameArtist, DownloadAble);

        Button downloadBtn = CreaButton("Download");
        downloadBtn.setLayoutX(1030 - 250 - 20);
        downloadBtn.setLayoutY(750);

        //Download Button action
        downloadBtn.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                this.downloader();
            }
        });

        pane.getChildren().addAll(img, downloadBtn, detailDownload, tableMyMusic(), searchBoxMy());
        return pane;
    }

    public void updateDetailDownload(AddSong songSelected) {
        pane.getChildren().remove(0);
        Image imageMy;
        if (songSelected != null) {
            ((Label) detailDownload.getChildren().get(0)).setText("Song : " + songSelected.getNameSong());
            ((Label) detailDownload.getChildren().get(1)).setText("Artist : " + songSelected.getArtistSong());
            ((Label) detailDownload.getChildren().get(2)).setText("Downloadable(Time) : " + songSelected.getNumberOfDownload());
            imageMy = songSelected.getSong().getPhoto();
        }else{
            ((Label) detailDownload.getChildren().get(0)).setText("");
            ((Label) detailDownload.getChildren().get(1)).setText("");
            ((Label) detailDownload.getChildren().get(2)).setText("");
            imageMy = null;
        }


        VBox img = new VBox(50);
        
        img.setMinSize(300, 400);
        img.setMaxSize(300, 400);
        img.setLayoutX(1030 - 300 - 20);
        img.setLayoutY(20);
        img.setAlignment(Pos.CENTER);
        
        

        ImageView imgMy = new ImageView(imageMy);
        imgMy.setFitHeight(200);
        imgMy.setFitWidth(200);
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

        TableView<AddSong> table = new TableView<>();
        table.setEditable(true);
        table.getStyleClass().add("tableMyLibrary");

        table.setPrefSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                //System.out.println(table.getSelectionModel().getSelectedItem().getNameSong());
                songSelected = table.getSelectionModel().getSelectedItem();
                if (table.getSelectionModel().getSelectedItem() != null) {
                    updateDetailDownload(this.songSelected);
                }
               
                songNameSelected = (table.getSelectionModel().getSelectedItem().getSong().getNameSong() + table.getSelectionModel().getSelectedItem().getSong().getArtistSong()).replaceAll("\\s", "");
                nameSet = table.getSelectionModel().getSelectedItem().getSong().getNameSong();
                System.out.println(songNameSelected+".mp3");
                fileForDownload = new File("src/MusicFile/" + songNameSelected + ".mp3");

                // System.out.println(NameCol.getWidth() + " "+artistCol.getWidth()+ " "+detailCol.getWidth()+ " "+Downloadable.getWidth());
            }
        });

        // Create column NameSong (Data type of String).
        TableColumn<AddSong, String> NameCol = new TableColumn<>("Name Song");
        NameCol.setMinWidth(200);

        // Create column NameArtist (Data type of String).
        TableColumn<AddSong, String> artistCol = new TableColumn<>("Artist");
        artistCol.setMinWidth(140);

        // Create column Detail (Data type of String).
        TableColumn<AddSong, String> detailCol = new TableColumn<>("Detail");
        detailCol.setMinWidth(190);

        // Create column Downloadable (Data type of String).
        TableColumn<AddSong, Integer> Downloadable = new TableColumn<>("Downloadable");
        Downloadable.setMinWidth(138);
        
        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));
        Downloadable.setCellValueFactory(new PropertyValueFactory<>("numberOfDownload"));

        // Set Sort type for userName column
        NameCol.setSortType(TableColumn.SortType.DESCENDING);
        detailCol.setSortable(false);

        // Display row data
        list = UI.userAccount.getMyListSong();
        FilteredList<AddSong> filterData = new FilteredList<>(list, b -> true);
        searchSystemMyLibrary.setFilterData(filterData);

        SortedList<AddSong> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        table.getColumns().addAll(NameCol, artistCol, detailCol,Downloadable);

        anchorPane.getChildren().addAll(table);

        return anchorPane;
    }

    

    @Override
    public HBox searchBoxAll() {
        HBox hBox = new HBox();
        hBox.setPrefSize(1030 - 300 - 60 - 70, 30);
        //hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutX(20);
        hBox.setLayoutY(15);

        searchTextField = new TextField();
        searchTextField.setPromptText("Search");
        searchTextField.setStyle("-fx-font-size: 12px;");
        searchTextField.setPrefSize(250, 30);

        searchTextField.setOnMouseClicked(event -> {
            UI.vbox.getChildren().remove(1);
            UI.vbox.getChildren().addAll(new SearchPage("").getSearchPane());
        });

        /// 1030-300-60-70
        hBox.getChildren().addAll(searchTextField);

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

    public static AnchorPane totalPane;

    private BorderPane AllSong() {

        BorderPane scrollPane = new BorderPane();
        scrollPane.setPrefSize(1030, 900);
        scrollPane.setPadding(new Insets(10));
        scrollPane.getStyleClass().add("scroll-bar");
        totalPane = new AnchorPane();
        totalPane.getStyleClass().add("allSong");

        totalPane.getChildren().addAll(new TopChartPane().getTopchartpane());

        scrollPane.setCenter(totalPane);

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
            System.out.println("User_UI : IOExeption getMyMusicList from class Song in updateScrollPane");
        } catch (ClassNotFoundException ex) {
            System.out.println("User_UI : ClassNotFoundExeption getMyMusicList from class Song in updateScrollPane");
        }

        String lowerCase = text.toLowerCase();

        for (Song song : list) {

            for (String styleSong : song.getListStyleSong()) {

                if ((song.getNameSong().toLowerCase().contains(lowerCase) || song.getArtistSong().toLowerCase().contains(lowerCase)) && styleSong.contains(page)) {

                    boolean inMyList = false;
                    for (AddSong song1 : UI.userAccount.getMyListSong()) {
                        if (!UI.userAccount.isFirstSong()) {
                            if ((song.getNameSong().toLowerCase().contains(song1.getSong().getNameSong().toLowerCase()) && song.getArtistSong().toLowerCase().contains(song1.getSong().getArtistSong().toLowerCase()))) {
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
                                System.out.println("User_UI : InterrruoteddExeption DetailSongPopUp in updateScrollPane");
                            }
                        });

                        tilePane.getChildren().add(contentButton);
                    }
                    break;
                }
            }
        }
        return tilePane;
    }

    private Button editbt;
    private Button savebt;
    private Button cancelbt;

    @Override
    public BorderPane myAccount() {
        return new Profile().getMainPane();
    }

        
    public void downloader() {
        ArrayList<Account> accountUpdate = new ArrayList<>();
        
        try {
            addAccount = Account.getAccountList();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(User_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Download");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        fileChooser.setInitialFileName(nameSet);
        File downloadFile = fileChooser.showSaveDialog(stage);
        if (downloadFile != null) {
            try {
                Files.copy(fileForDownload.toPath(), downloadFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
                System.out.println("asdasdsd");

                    UI.userAccount.addSong(songSelected.getSong(), -1);
                
                for (Account account : addAccount) {
                    if (account.getUsername().equals(UI.userAccount.getUsername())) {
                        account = UI.userAccount;
                    }
                      accountUpdate.add(account);
                    
                }
                writeFile.writeFile(user, accountUpdate);
                    UI.vbox.getChildren().remove(1);
                    UI.vbox.getChildren().add(thirdPagePane());
                    updateDetailDownload(null);
            } catch (IOException ex) {
                System.out.println("User_UI : IOExeption download file from class Song in downloader");
            }
        }
    }

    private ArrayList<Account> updateAccount = new ArrayList<>();
    private File user = new File("src/data/user.dat");

    @Override
    public void userLogout() {

        ReadWriteFile file = new ReadWriteFile();
        ArrayList<Account> nowAccount = null;

        try {
            nowAccount = file.readFile(user);
        } catch (IOException ex) {
            System.out.println("User_UI : IOExeption read file in userLogin");
        } catch (ClassNotFoundException ex) {
            System.out.println("User_UI : ClassNotFoundExeption read file in userLogin");
        }

        for (Account account : nowAccount) {
            if (account.getUsername().equals(userAccount.getUsername())) {
                updateAccount.add(UI.userAccount);
            } else {
                updateAccount.add(account);
            }
        }

        try {
            file.writeFile(user, updateAccount);
        } catch (IOException ex) {
            System.out.println("User_UI : IOExeption write file in userLogin");
        }
    }

}
