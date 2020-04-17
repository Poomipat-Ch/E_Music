/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.User_UI;
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

    AnchorPane anchorPane;

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

        if (text.toLowerCase().equals("")) {
            anchorPane.getChildren().addAll(background, FoundListPane("Spokify"));
        } else {
            anchorPane.getChildren().addAll(background, FoundListPane(text));
        }

    }

    public AnchorPane getSearchPane() {
        return anchorPane;
    }

//    private AnchorPane //empty page
    private BorderPane FoundListPane(String foundtext) {
        BorderPane borderpane = new BorderPane();
        borderpane.getStyleClass().add("backgroundsearch");
        borderpane.setPadding(new Insets(0, 10, 0, 10));

        borderpane.setPrefWidth(970);
        borderpane.setMinHeight(762);
        borderpane.setLayoutX(30);
        borderpane.setLayoutY(60);
        
        borderpane.setTop(TopPlaylistPane("Songs", "music.dat", foundtext));
        borderpane.setLeft(PlaylistPane("Genres & Moods", "stylemusiclist.txt", foundtext));
        borderpane.setRight(PlaylistPane("Artists", "artist.dat", foundtext));

        return borderpane;
    }
    
    private BorderPane TopPlaylistPane(String string, String filename, String foundtext) {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(0, 10, 20, 10));

        borderpane.setPrefWidth(950);
        borderpane.setPrefHeight(341);

        borderpane.setTop(HeadBox(string, filename, foundtext, 880));
        borderpane.setCenter(Playlist(foundtext,4,12));

        return borderpane;
    }

    private BorderPane PlaylistPane(String string, String filename, String foundtext) {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(0, 10, 0, 10));
        
        borderpane.setPrefWidth(460);
        borderpane.setPrefHeight(341);

        borderpane.setTop(HeadBox(string, filename, foundtext, 390));
        borderpane.setCenter(Playlist(foundtext,2,0));

        return borderpane;
    }

    private AnchorPane HeadBox(String string, String filename, String foundtext, double x) {
        AnchorPane head = new AnchorPane();
        head.getStyleClass().add("hbox");
        Label seeall = CreateSeeAll(string, filename, foundtext, x);

        head.getChildren().addAll(CreateLabel(string), seeall);

        return head;
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
        });

        return label;
    }

    private AnchorPane Playlist(String foundtextt, int column, int dis) {
        AnchorPane anchorpane = new AnchorPane();
//        borderpane.set

        for (int i = 0; i < 8 + (dis/3); ++i) {
            anchorpane.getChildren().add(CreateList((230 * (i % column)) + dis, (80 * (i / column)) + 30, ""));

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

        Button button = new Button();
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
