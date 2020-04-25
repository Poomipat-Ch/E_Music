/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.UI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;

/**
 *
 * @author HCARACH
 *
 *
 */
public class ShowMusicPage {

    private SearchSystem searchSystemMyLibrary = new SearchSystem();

    private File fileForDownload;
    private String songNameSelected;
    private String nameSet;
    private Song songSelected;
    private String page;

    private TableView<Song> table;
    private ObservableList<Song> list = null;

    int top = 0;

    public ShowMusicPage(String name, String content, Image image) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(990, 901);
        anchorPane.getStyleClass().add("mainBox");

        AnchorPane profilePicture = new AnchorPane();
        profilePicture.getChildren().add(new ImageRectangle(30, 50, 250, 250, image).getMyRectangle());

        table = new TableView<>();
        table.setEditable(true);
        table.getStyleClass().add("tableTopChartMusic");

        table.setPrefWidth(anchorPane.getMinWidth() - 40);

        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                songSelected = table.getSelectionModel().getSelectedItem();
//                updateDetailDownload();
                songNameSelected = table.getSelectionModel().getSelectedItem().getNameSong() + table.getSelectionModel().getSelectedItem().getArtistSong() + table.getSelectionModel().getSelectedItem().getDetailSong();
                nameSet = table.getSelectionModel().getSelectedItem().getNameSong();
                System.out.println(songNameSelected);
                fileForDownload = new File("src/MusicFile/" + songNameSelected + ".mp3");

                try {
                    new DetailSongPopUp(table.getSelectionModel().getSelectedItem().getSong());
                } catch (InterruptedException ex) {
                    System.out.println("79 ShowMusicPage : InterrruoteddExeption DetailSongPopUp in updateScrollPane");
                }

            }
        });

        // Create column NameSong (Data type of String).
        TableColumn<Song, String> sequenceCol = new TableColumn<>("#");
        sequenceCol.setMinWidth(30);
        
        TableColumn<Song, String> NameCol = new TableColumn<>("TITLE");
        

        // Create column NameArtist (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("ARTIST");
        artistCol.setMinWidth(283);

        // Create column Detail (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("DETAIL");
        detailCol.setMinWidth(92);

        TableColumn<Song, String> downloadCol = new TableColumn<>("DOWNLOAD");
        downloadCol.setMinWidth(155);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        sequenceCol.setCellValueFactory(new PropertyValueFactory<>("sequence"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));
        downloadCol.setCellValueFactory(new PropertyValueFactory<>("totalDownload"));
        //Downloadable.setCellValueFactory(new PropertyValueFactory<>("downloadable")); // wait nichida add dowloadable in account

        // Set Sort type for userName column
        downloadCol.setSortType(TableColumn.SortType.DESCENDING);
        sequenceCol.setSortable(false);
        downloadCol.setSortable(false);
        detailCol.setSortable(false);
        NameCol.setSortable(false);
        artistCol.setSortable(false);

        list = FXCollections.observableArrayList();

        FilteredList<Song> filterData = new FilteredList<>(list, b -> true);
        searchSystemMyLibrary.setFilterData(filterData);

        SortedList<Song> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        top = 0;

        // Display row data
        if (name.toLowerCase().contains("top 50")) {
            
            if (name.toLowerCase().contains("thailand")) {
                try {

                    Song.getMyMusicList().forEach(song -> {
                        if (song.getNationality().equals("thai")) {
                            if (top < 50) {
                                list.add(song);
                            }
                            top++;
                        }
                    });
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("137 ShowMusicPage : IOException get my music list from class song");
                }
            } else if (name.toLowerCase().contains("international")){
                try {
                    Song.getMyMusicList().forEach(song -> {
                        if (song.getNationality().equals("international")) {
                            if (top < 50) {
                                list.add(song);
                            }
                            top++;
                        }
                    });
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("147 ShowMusicPage : IOException get my music list from class song");

                }
            }

            ImageView imageview = new ImageView(new Image("/image/" + name + ".png"));
            imageview.setFitWidth(1030);
            imageview.setPreserveRatio(true);
            
            NameCol.setMinWidth(418);

            anchorPane.getChildren().addAll(imageview, table, searchBoxMy());

        } else if (content.equals("artist")) {
            NameCol.setMinWidth(418);
            System.out.println("yes");
            try {
                Song.getMyMusicList().forEach(song -> {
                    System.out.println(song.getArtistSong());
                    if (song.getArtistSong().toLowerCase().contains(name.toLowerCase())) {
                        list.add(song);
                    }
                });
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(ShowMusicPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ImageView imageview = new ImageView(new Image("/image/BrowseBackground.jpg"));
            imageview.setFitWidth(1030);
            imageview.setPreserveRatio(true);
            
            anchorPane.getChildren().addAll(table, createLabel(name), profilePicture, searchBoxMy());
            
        } else {
            NameCol.setMinWidth(418);
            try {
                Song.getMyMusicList().forEach(song -> {
                    song.getListStyleSong().forEach(style -> {
                        if (style.equals(name)) {
                            list.add(song);
                        }
                    });
                });
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("173 ShowMusicPage : IOException get my music list from class song");
            }

            ImageView imageview = new ImageView(new Image("/image/BrowseBackground.jpg"));
            imageview.setFitWidth(1030);
            imageview.setPreserveRatio(true);

            anchorPane.getChildren().addAll(table, createLabel(name), profilePicture, searchBoxMy());
        }
        
        table.getColumns().addAll(NameCol, artistCol, detailCol, downloadCol);

        table.setItems(sortedList);

        table.setLayoutX(30);
        table.setLayoutY(380);

        ScrollPane scrollpane = new ScrollPane();

        scrollpane.setPadding(Insets.EMPTY);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollpane.fitToWidthProperty().set(true);
        scrollpane.setLayoutX(-4);
        scrollpane.setLayoutY(-4);
        scrollpane.setPrefSize(1030, 901);

        scrollpane.setContent(anchorPane);

        UI.vbox.getChildren().remove(1);
        UI.vbox.getChildren().add(scrollpane);

    }

    private AnchorPane createLabel(String string) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.setPrefSize(500, 200);
        anchorpane.setLayoutX(325);
        anchorpane.setLayoutY(75);

        AnchorPane background = new AnchorPane();
        background.getStyleClass().add("backgroundpane");
        background.setPrefSize(550, 200);

        Label label = new Label(string);
        label.getStyleClass().add("labelheadinshowpage");
        label.setPrefSize(550, 200);
        label.setAlignment(Pos.CENTER);

        anchorpane.getChildren().addAll(background, label);

        return anchorpane;
    }

    public HBox searchBoxMy() {
        HBox hBox = new HBox();
        hBox.setPrefSize(1030 - 80, 30);
        hBox.setLayoutX(30);
        hBox.setLayoutY(320);

//        AnchorPane anchorpane = new AnchorPane();
//        anchorpane.setPrefSize(1030 - 200, 10);
//        anchorpane.getStyleClass().add("bgsearchfield");
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Filter");
        searchTextField.setPrefSize(1030 - 175, 10);
        searchTextField.getStyleClass().add("searchfield");

//        searchTextField.set
        Button searchButton = creaButton("Refresh");
        searchButton.setStyle("-fx-font-size : 15px;");
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMyLibrary);

//        anchorpane.getChildren().add(searchTextField);
        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }

    private Button creaButton(String text) {
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setPrefSize(80, 40);
        return downLoadButton;

    }

}
