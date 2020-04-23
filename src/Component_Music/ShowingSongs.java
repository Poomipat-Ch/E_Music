/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

/**
 *
 * @author HCARACH
 */
public class ShowingSongs {

    private SearchSystem searchSystemMyLibrary = new SearchSystem();

    private File fileForDownload;
    private Song songSelected;
    private String songNameSelected;
    private String nameSet;
    
    protected TableView<Song> table;

    private ObservableList<Song> list;

    public ShowingSongs(String foundtext) {
        table = new TableView<>();
        table.setEditable(true);
        table.getStyleClass().add("tableTopChartMusic");

        table.setPrefWidth(900);

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
                    System.out.println("TopChartMusicPane : InterrruoteddExeption DetailSongPopUp in updateScrollPane");
                }

            }
        });

        // Create column NameSong (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("TITLE");
        NameCol.setMinWidth(378);

        // Create column NameArtist (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("ARTIST");
        artistCol.setMinWidth(270);

        // Create column Detail (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("DETAIL");
        detailCol.setMinWidth(90);
        
        TableColumn<Song, String> downloadCol = new TableColumn<>("DOWNLOAD");
        downloadCol.setMinWidth(160);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));
        downloadCol.setCellValueFactory(new PropertyValueFactory<>("totalDownload"));
        //Downloadable.setCellValueFactory(new PropertyValueFactory<>("downloadable")); // wait nichida add dowloadable in account

        // Set Sort type for userName column
        NameCol.setSortType(TableColumn.SortType.DESCENDING);
        downloadCol.setSortable(false);
        detailCol.setSortable(false);
        NameCol.setSortable(false);
        artistCol.setSortable(false);

        // Display row data
        list = FXCollections.observableArrayList();

        try {
            Song.getMyMusicList().forEach(song -> {
                if (song.getNameSong().toLowerCase().contains(foundtext.toLowerCase())) {
                    list.add(song);
                }
            });
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("TopChartMusicPage : IOException get my music list from class song");
        }

        FilteredList<Song> filterData = new FilteredList<>(list, b -> true);
        searchSystemMyLibrary.setFilterData(filterData);

        SortedList<Song> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        table.setLayoutX(65);
        table.setLayoutY(200);

        table.getColumns().addAll(NameCol, artistCol, detailCol, downloadCol);
    }

    public TableView<Song> getTable() {
        return table;
    }

}
