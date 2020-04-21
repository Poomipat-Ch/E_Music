/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.ReadWriteFile;
import UI_music.User_UI;
import static UI_music.User_UI.searchTextField;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author HCARACH
 */
public class SearchPage {

    private AnchorPane anchorPane;
    private int songCount = 0;
    private int artistCount = 0;

    File musicfile = new File("src/data/music.dat");
    File artistfile = new File("src/data/artist.dat");
    ArrayList<Song> Song = null;
    ArrayList<Artist> Artist = null;

    public SearchPage(String text) {
        anchorPane = new AnchorPane();
        anchorPane.setMinSize(990, 900);
//        anchorPane.setLayoutX(-3);
//        anchorPane.setLayoutY(-3);
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

        try {
            Song = new ReadWriteFile().readFileSong(musicfile);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("SearchPage: ERROR READ MUSIC.DAT");
        }

        try {
            Artist = new ReadWriteFile().readFileArtist(artistfile);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("SearchPage: ERROR READ MUSIC.DAT");
        }

        searchTextField.textProperty().addListener((ov, t, t1) -> {
            anchorPane.getChildren().remove(1);
            if (searchTextField.getText().equals("")) {
                anchorPane.getChildren().addAll(BlankPane());
            } else {
                anchorPane.getChildren().addAll(FoundListPane(searchTextField.getText()));
            }

        });

        anchorPane.getChildren().addAll(background, BlankPane());

    }

    public AnchorPane getSearchPane() {
        return anchorPane;
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

//    private AnchorPane //empty page
    private BorderPane FoundListPane(String foundtext) {
        BorderPane borderpane = new BorderPane();
        //borderpane.getStyleClass().add("backgroundsearch");
        borderpane.setPadding(new Insets(0, 10, 0, 10));

        borderpane.setPrefWidth(970);
        borderpane.setMinHeight(762);
        borderpane.setLayoutX(30);
        borderpane.setLayoutY(60);

        borderpane.setLeft(PlaylistPane("Songs", "music.dat", foundtext));
//        borderpane.setLeft(PlaylistPane("Genres & Moods", "stylemusiclist.txt", foundtext));
//        borderpane.setRight(PlaylistPane("Artists", "artist.dat", foundtext));
        if (this.songCount == 0) {
            borderpane.setLeft(PlaylistPane("Artists", "artist.dat", foundtext));
        } else {
            borderpane.setRight(PlaylistPane("Artists", "artist.dat", foundtext));
        }

        if (this.songCount == 0 && this.artistCount == 0) {
            borderpane.setLeft(PlaylistPane("Artists", "artist.dat", foundtext));
        }

        return borderpane;
    }

    private BorderPane PlaylistPane(String string, String filename, String foundtext) {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(20, 0, 20, 0));

        borderpane.setPrefWidth(455);
        borderpane.setPrefHeight(341);

        borderpane.setTop(HeadPane(string, filename, foundtext, 400));
        borderpane.setCenter(Playlist(foundtext, filename, 4, 12));

        return borderpane;
    }

    private AnchorPane HeadPane(String string, String filename, String foundtext, double x) {
        AnchorPane pane = new AnchorPane();
        pane.getStyleClass().add("hbox");
        Label seeall = CreateSeeAll(string, filename, foundtext, x);

        pane.getChildren().addAll(CreateLabel(string), seeall);

        return pane;
    }

    private Label CreateLabel(String string) {
        Label label = new Label(string);
        label.getStyleClass().add("headlabel");
        label.setAlignment(Pos.CENTER_LEFT);

        return label;
    }

    private Label CreateSeeAll(String string, String filename, String foundtext, double x) {
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

        File file = new File("src/data/" + filename);

        if (filename.equals("music.dat")) {
            songCount = 0;
            for (Song song : Song) {
                if (song.getNameSong().toLowerCase().contains(foundtext.toLowerCase())) {
                    anchorpane.getChildren().add(CreateList((230 * (songCount % column)) + dis, (80 * (songCount / column)) + 50, song.getNameSong()));
                    songCount++;
                }
            }
        } else {
            artistCount = 0;
            for (Artist artist : Artist) {
                if (artist.getName1().toLowerCase().contains(foundtext.toLowerCase()) || artist.getName2().toLowerCase().contains(foundtext.toLowerCase())) {
                    anchorpane.getChildren().add(CreateList((230 * (artistCount % column)) + dis, (80 * (artistCount / column)) + 50, artist.getName1()));
                    artistCount++;
                }
            }
        }

        return anchorpane;
    }

    private AnchorPane CreateList(double x, double y, String namesong) {
        AnchorPane anchorpane = new AnchorPane();
        //anchorpane.getStyleClass().add("borderplaylist");
        anchorpane.setMaxWidth(215);
        anchorpane.setPrefSize(215, 60);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);

        Button button = new Button(namesong);
        button.setPrefSize(215, 60);
        button.getStyleClass().add("borderplaylist");
        button.setLayoutX(0);
        button.setLayoutY(0);

        ImageView imageview = new ImageView(new Image("/UI_music/defaultprofile.png"));
        imageview.setFitHeight(60);
        imageview.setFitWidth(60);
        imageview.setLayoutX(0);
        imageview.setLayoutY(0);

        anchorpane.getChildren().addAll(button, imageview);

        return anchorpane;
    }

}
