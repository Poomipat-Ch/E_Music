/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.User_UI;
import static UI_music.UI.searchTextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author HCARACH
 */
public class SearchPage {

    private AnchorPane anchorPane;
    private int songCount = 0;
    private int artistCount = 0;

    public SearchPage(String text) {
        anchorPane = new AnchorPane();
        anchorPane.setMinSize(990, 900);
        anchorPane.getStyleClass().add("mainBox");

        AnchorPane background = new AnchorPane();
        background.getStyleClass().add("backgroundsearch");
        background.setPrefSize(1030, 902);
        background.setPadding(Insets.EMPTY);
        background.setLayoutX(0);
        background.setLayoutY(2);

        BorderPane borderpane = new BorderPane();
        borderpane.getStyleClass().add("backgroundsearch");
        borderpane.setPrefWidth(970);
        borderpane.setMinHeight(762);
        borderpane.setLayoutX(30);
        borderpane.setLayoutY(60);

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            anchorPane.getChildren().remove(1);
            if (searchTextField.getText().equals("")) {
                anchorPane.getChildren().addAll(BlankPane());
            } else {
                anchorPane.getChildren().addAll(FoundListPane(searchTextField.getText()));
                if (this.songCount == 0 && this.artistCount == 0) {
                    anchorPane.getChildren().remove(1);
                    anchorPane.getChildren().addAll(CantFoundPane(searchTextField.getText()));
                }
            }

        });

        anchorPane.getChildren().addAll(background, BlankPane());

    }

    public AnchorPane getSearchPane() {
        return anchorPane;
    }

//    private AnchorPane //empty page
    private BorderPane FoundListPane(String foundtext) {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(0, 10, 0, 10));

        borderpane.setPrefWidth(970);
        borderpane.setMinHeight(762);
        borderpane.setLayoutX(30);
        borderpane.setLayoutY(60);

        BorderPane songpane = PlaylistPane("Songs", "music.dat", foundtext, 2, 400);
        BorderPane artistpane = PlaylistPane("Artists", "artist.dat", foundtext, 2, 400);

        if (this.songCount == 0) {
            borderpane.setLeft(PlaylistPane("Artists", "artist.dat", foundtext, 4, 880));
        } else if (this.artistCount == 0) {
            borderpane.setLeft(PlaylistPane("Songs", "music.dat", foundtext, 4, 880));
        } else {
            borderpane.setLeft(songpane);
            borderpane.setRight(artistpane);
        }

        return borderpane;
    }

    private BorderPane PlaylistPane(String string, String filename, String foundtext, int column, double seeall) {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(20, 0, 20, 0));

        borderpane.setPrefWidth(445);
        borderpane.setPrefHeight(341);

        borderpane.setTop(HeadPane(string, foundtext, seeall));
        borderpane.setCenter(Playlist(foundtext, filename, column, 12));

        return borderpane;
    }

    private AnchorPane HeadPane(String string, String foundtext, double x) {
        AnchorPane pane = new AnchorPane();
        pane.getStyleClass().add("hbox");
        Label seeall = CreateSeeAll(string, foundtext, x);

        pane.getChildren().addAll(CreateLabel(string), seeall);

        return pane;
    }

    private Label CreateLabel(String string) {
        Label label = new Label(string);
        label.getStyleClass().add("headlabel");
        label.setAlignment(Pos.CENTER_LEFT);

        return label;
    }

    private Label CreateSeeAll(String string, String foundtext, double x) {
        Label label = new Label("SEE ALL");
        label.setAlignment(Pos.CENTER_RIGHT);
        label.getStyleClass().add("seealllabel");
        label.setLayoutX(x);
        label.setLayoutY(8);

        label.setOnMouseClicked(event -> {
            new ShowingSearchPage(string, foundtext);
            searchTextField.clear();
        });

        return label;
    }

    private AnchorPane Playlist(String foundtext, String filename, int column, int dis) {
        AnchorPane anchorpane = new AnchorPane();
//        borderpane.set

        if (filename.equals("music.dat")) {
            songCount = 0;
            for (Song song : User_UI.SongArrayList) {
                if (song.getNameSong().toLowerCase().contains(foundtext.toLowerCase())) {
                    anchorpane.getChildren().add(CreateSongList((230 * (songCount % column)) + dis, (150 * (songCount / column)) + 50, song));
                    songCount++;
                }
            }
        } else {
            artistCount = 0;
            for (Artist artist : User_UI.ArtistArrayList) {
                if (artist.getName1().toLowerCase().contains(foundtext.toLowerCase()) || artist.getName2().toLowerCase().contains(foundtext.toLowerCase())) {
                    anchorpane.getChildren().add(CreateArtistList((230 * (artistCount % column)) + dis, (150 * (artistCount / column)) + 50, artist));
                    artistCount++;
                }
            }
        }

        return anchorpane;
    }

    private AnchorPane CreateSongList(double x, double y, Song song) {
        AnchorPane anchorpane = new AnchorPane();
        //anchorpane.getStyleClass().add("borderplaylist");
        anchorpane.setMaxWidth(220);
        anchorpane.setPrefSize(220, 60);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);

        Label nameLabel = new Label(song.getNameSong());
        nameLabel.setPadding(new Insets(0, 0, 0, 50));
        nameLabel.setPrefSize(190, 60);
        nameLabel.setMaxWidth(190);
        nameLabel.getStyleClass().add("borderplaylist");
        nameLabel.setLayoutX(0);
        nameLabel.setLayoutY(0);

        nameLabel.setOnMouseClicked(event -> {
            try {
                new DetailSongPopUp(song);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        AnchorPane profilePicture = new AnchorPane();
        profilePicture.getChildren().add(new ImageRectangle(0, 0, 60, 60, song.getPhoto()).getMyRectangle());

        nameLabel.setPadding(new Insets(8, 0, 0, 80));
        nameLabel.setAlignment(Pos.TOP_LEFT);

        Label label = new Label(song.getArtistSong());
        label.getStyleClass().add("labelNameArtistInSongButton");
        label.setLayoutX(80);
        label.setLayoutY(30);

        anchorpane.getChildren().addAll(nameLabel, profilePicture, label);

        return anchorpane;
    }

    private AnchorPane CreateArtistList(double x, double y, Artist artist) {
        AnchorPane anchorpane = new AnchorPane();
        //anchorpane.getStyleClass().add("borderplaylist");
        anchorpane.setMaxWidth(215);
        anchorpane.setPrefSize(215, 60);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);

        Label nameLabel = new Label(artist.getName1());
        nameLabel.setPadding(new Insets(0, 0, 0, 80));
        nameLabel.setPrefSize(185, 60);
        nameLabel.setMaxWidth(185);
        nameLabel.getStyleClass().add("borderplaylist");
        nameLabel.setLayoutX(0);
        nameLabel.setLayoutY(0);

        nameLabel.setOnMouseClicked(event -> {
            new ShowMusicPage(artist.getName1(), "artist", artist.getPhoto());
        });

        AnchorPane profilePicture = new AnchorPane();
        profilePicture.getChildren().add(new ImageCircle(30, 30, 30, artist.getPhoto()).getMyCircle());

        nameLabel.setAlignment(Pos.CENTER_LEFT);
        anchorpane.getChildren().addAll(nameLabel, profilePicture);

        return anchorpane;
    }

    private AnchorPane BlankPane() {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.setMinWidth(1030);
        ImageView image = new ImageView(new Image("/icon/search.png"));
        image.setFitWidth(60);
        image.setPreserveRatio(true);
        image.setLayoutX(485);
        image.setLayoutY(350);
        image.setStyle("-fx-opacity: .7;");

        Label searchlabel = new Label("Search Spookify");
        searchlabel.getStyleClass().add("searchlabel");
        searchlabel.setLayoutY(430);
        searchlabel.setMinWidth(1030);
        searchlabel.setAlignment(Pos.CENTER);

        Label detaillabel = new Label("Find your favorite songs and artists.");
        detaillabel.getStyleClass().add("detaillabel");
        detaillabel.setLayoutY(480);
        detaillabel.setMinWidth(1030);
        detaillabel.setAlignment(Pos.CENTER);

        anchorpane.getChildren().addAll(image, searchlabel, detaillabel);

        return anchorpane;
    }

    private AnchorPane CantFoundPane(String foundtext) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.setMinWidth(1030);
        ImageView image = new ImageView(new Image("/icon/flag.png"));
        image.setFitWidth(60);
        image.setPreserveRatio(true);
        image.setLayoutX(485);
        image.setLayoutY(350);
        image.setStyle("-fx-opacity: .7;");

        Label searchlabel = new Label("Not results found for \"" + foundtext + "\"");
        searchlabel.getStyleClass().add("searchlabel");
        searchlabel.setLayoutY(430);
        searchlabel.setMinWidth(1030);
        searchlabel.setAlignment(Pos.CENTER);

        Label detaillabel = new Label("Please make sure your words are spelled correctly or use less or different keywords.");
        detaillabel.getStyleClass().add("detaillabel");
        detaillabel.setLayoutY(480);
        detaillabel.setLayoutX(20);
        detaillabel.setMinWidth(990);
        detaillabel.setMaxWidth(990);
        detaillabel.setAlignment(Pos.CENTER);

        anchorpane.getChildren().addAll(image, searchlabel, detaillabel);

        return anchorpane;
    }

}
