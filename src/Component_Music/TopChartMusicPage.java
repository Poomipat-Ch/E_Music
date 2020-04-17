/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.UI;
import UI_music.User_UI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
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
 */
public class TopChartMusicPage {

    SearchSystem searchSystemMyLibrary = new SearchSystem();

    private File fileForDownload;
    private String songNameSelected;
    private String nameSet;
    private Song songSelected;
    private Account userAccount;
    private String page;

    private ImageView imageview;

    TableView<Song> table;

    public TopChartMusicPage(String string, Account userAccount) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(990, 901);
        anchorPane.setLayoutX(-3);
        anchorPane.setLayoutY(-3);
        anchorPane.getStyleClass().add("mainBox");

        imageview = new ImageView(new Image("/UI_music/defaultprofile.png"));
        imageview.setFitHeight(250);
        imageview.setFitWidth(250);
        imageview.setLayoutX(30);
        imageview.setLayoutY(50);

        table = new TableView<>();
        table.setEditable(true);

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
                    new DetailSongPopUp(table.getSelectionModel().getSelectedItem().getSong(), userAccount);
                } catch (InterruptedException ex) {
                    System.out.println("TopChartMusicPane : InterrruoteddExeption DetailSongPopUp in updateScrollPane");
                }

            }
        });

        // Create column NameSong (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("TITLE");
        NameCol.setMinWidth(488);

        // Create column NameArtist (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("ARTIST");
        artistCol.setMinWidth(340);

        // Create column Detail (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("DETAIL");
        detailCol.setMinWidth(120);

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
        ObservableList<Song> list = null;

        try {
            list = Song.getMyMusicList();
        } catch (IOException ex) {
            System.out.println("TopChartMusicPage : IOException get my music list from class song");
        } catch (ClassNotFoundException ex) {
            System.out.println("TopChartMusicPage : ClassNotFoundException get my music list from class song");
        }
        FilteredList<Song> filterData = new FilteredList<>(list, b -> true);
        searchSystemMyLibrary.setFilterData(filterData);

        SortedList<Song> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
        
        table.setLayoutX(30);
        table.setLayoutY(380);

        table.getColumns().addAll(NameCol, artistCol, detailCol);

        ScrollPane scrollpane = new ScrollPane();

        scrollpane.setPadding(Insets.EMPTY);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollpane.fitToWidthProperty().set(true);
        scrollpane.setLayoutX(-4);
        scrollpane.setLayoutY(-4);
        scrollpane.setPrefSize(1030, 901);

        anchorPane.getChildren().addAll(table, CreateLabel(string), imageview, searchBoxMy());

        scrollpane.setContent(anchorPane);

        UI.vbox.getChildren().remove(1);
        UI.vbox.getChildren().add(scrollpane);

    }

    private Label CreateLabel(String string) {
        Label label = new Label(string);
        label.getStyleClass().add("labelhead");
        label.setLayoutX(350);
        label.setLayoutY(180);
        return label;
    }

    public HBox searchBoxMy() {
        HBox hBox = new HBox();
        hBox.setPrefSize(1030 - 100, 30);
        hBox.setLayoutX(30);
        hBox.setLayoutY(320);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Filter");
        searchTextField.setStyle("-fx-font-size: 18px;");
        searchTextField.setPrefSize(1030 - 200, 10);

        Button searchButton = CreaButton("Refresh");
        searchButton.setStyle("-fx-font-size : 15px;");
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMyLibrary);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }

    private Button CreaButton(String text) {
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setPrefSize(80, 40);
        return downLoadButton;

    }

}
