/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.BrowsePane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author HCARACH
 */
public class ShowingArtist {

    private AnchorPane showingartist;
    private ObservableList<Artist> artistList;

    public ShowingArtist(String foundtext) {

        showingartist = new AnchorPane();
        artistList = FXCollections.observableArrayList();
        try {
            artistList = Artist.getArtistList();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;

        for (Artist artist : artistList) {
            if (artist.getName1().toLowerCase().contains(foundtext.toLowerCase()) || artist.getName2().toLowerCase().contains(foundtext.toLowerCase())) {
                AnchorPane listAlbums = CreateArtistList(artist, (235 * (i % 4)) + 50, (255 * (i / 4)) + 250);
                i++;

                showingartist.getChildren().add(listAlbums);
            }
        }
    }

    public AnchorPane getShowingartist() {
        return showingartist;
    }

    private AnchorPane CreateArtistList(Artist artist, double x, double y) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("artistbuttonlist");
        anchorpane.setPrefSize(225, 225);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);
        anchorpane.getChildren().addAll(new ImageCircle(112.5, 92, 90, artist.getPhoto()).getMyCircle(), CreateLabel(artist, 190));

        return anchorpane;
    }

    private Button CreateLabel(Artist artist, int dis) {
        Button button = new Button(artist.getName1());
        button.setPrefSize(225, 225);
//        button.setLayoutY(180);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("browsebuttonlabel");
        button.setPadding(new Insets(dis, 0, 0, 0));

        button.setOnMouseClicked(event -> {
            new ShowMusicPage(artist.getName1(), "artist", artist.getPhoto());
        });

        return button;
    }

}
