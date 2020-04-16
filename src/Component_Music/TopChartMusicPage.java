/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.User_UI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

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

    public TopChartMusicPage(String string) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(990, 900);
        anchorPane.setLayoutX(-4);
        anchorPane.setLayoutY(-4);
        anchorPane.getStyleClass().add("mainBox");
        
        
        imageview = new ImageView(new Image("/UI_music/defaultprofile.png"));
        imageview.setFitHeight(250);
        imageview.setFitWidth(250);
        imageview.setLayoutX(30);
        imageview.setLayoutY(50);
        

        table = new TableView<>();
        table.setEditable(true);

        table.setPrefSize(anchorPane.getMinWidth()-20, anchorPane.getMinHeight());
        
        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                songSelected = table.getSelectionModel().getSelectedItem();
//                updateDetailDownload();
                songNameSelected = table.getSelectionModel().getSelectedItem().getNameSong() + table.getSelectionModel().getSelectedItem().getArtistSong() + table.getSelectionModel().getSelectedItem().getDetailSong();
                nameSet = table.getSelectionModel().getSelectedItem().getNameSong();
                System.out.println(songNameSelected);
                fileForDownload = new File("src/MusicFile/" + songNameSelected + ".mp3");

            }
        });

        // Create column NameSong (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("TITLE");
        NameCol.setMinWidth(500);

        // Create column NameArtist (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("ARTIST");
        artistCol.setMinWidth(350);

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

        table.getColumns().addAll(NameCol, artistCol, detailCol);
        
        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setContent(table);
        scrollpane.setLayoutX(30);
        scrollpane.setLayoutY(380);

        anchorPane.getChildren().addAll(scrollpane, CreateLabel(string), imageview);
        
        User_UI.totalPane.getChildren().add(anchorPane);

    }
    
    private Label CreateLabel(String string) {
        Label label = new Label(string);
        label.getStyleClass().add("labelhead");
        label.setLayoutX(350);
        label.setLayoutY(180);
        return label;      
    }
    

}
