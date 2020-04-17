/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Artist;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author HCARACH
 */
public class BrowsePane {

    ScrollPane scrollpane;

    public BrowsePane() {
        scrollpane = new ScrollPane();
        scrollpane.setPrefSize(1030, 920);
        scrollpane.getStyleClass().add("mainBox");
        scrollpane.setPadding(Insets.EMPTY);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollpane.fitToWidthProperty().set(true);

        AnchorPane backgroundpane = new AnchorPane();
        backgroundpane.setPrefSize(1030, 900);
        backgroundpane.setLayoutX(1);
        backgroundpane.getStyleClass().add("mainBox");

        ImageView playlist = CreateImageView(50, 200);
        ImageView artist = CreateImageView(350, 200);
        Label genres = CreateLabel("GENRES & MOODS", 150, 220);
        Label production = CreateLabel("ARTIST", 450, 220);
        Label browse = CreateHead("Browse", 50, 100);
        HBox genresbtn = CreateButton(40, 190, 280);
        HBox artistbtn = CreateButton(340, 190, 200);

        backgroundpane.getChildren().addAll(genresbtn, artistbtn, playlist, artist, genres, production, browse, BorderList());

        scrollpane.setContent(backgroundpane);

    }

    public ScrollPane getBrowsePane() {

        return this.scrollpane;
    }

    private HBox CreateButton(double x, double y, double width) {
        HBox button = new HBox();
        button.getStyleClass().add("buttoninbrowse");
        button.setPrefSize(width, 99);
        button.setLayoutX(x);
        button.setLayoutY(y);

        return button;
    }

    ;
    
    private ImageView CreateImageView(double x, double y) {
        ImageView image = new ImageView(new Image("/UI_music/defaultprofile.png"));
        image.setFitHeight(80);
        image.setFitWidth(80);
        image.setLayoutX(x);
        image.setLayoutY(y);

        return image;
    }

    private Label CreateLabel(String string, double x, double y) {
        Label label = new Label(string);
        label.getStyleClass().add("browselabel");
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    private Label CreateHead(String string, double x, double y) {
        Label label = new Label(string);
        label.getStyleClass().add("browseheadlabel");
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    private AnchorPane BorderList(){
        AnchorPane borderpane = new AnchorPane();
        borderpane.getStyleClass().add("borderlist");
        borderpane.setPrefWidth(1030);
        borderpane.setMinHeight(614);
        borderpane.setLayoutY(290);
        
        //VBox vBox;
        
                for (int i = 0; i < 8; ++i) {
                    //vBox = new VBox(5);
                    Button buttonlist = CreateButtonList((235 * (i % 4)) + 50, (235 * (i / 4)) + 50);
//                    ImageView imageView = new ImageView(Artist.getArtistList().get(i).getPhoto());
//                    Label nameLabel = new Label(Artist.getArtistList().get(i).getName1());
//                    Label name2Label = new Label(Artist.getArtistList().get(i).getName2());
//                    Label imfoLabel = new Label(Artist.getArtistList().get(i).getInfomation());
//                    vBox.getChildren().addAll(imageView,nameLabel,name2Label,imfoLabel);
                    
                   // buttonlist.setGraphic(vBox);
                    borderpane.getChildren().add(buttonlist);
                }
 
        
        return borderpane;
    }

    private Button CreateButtonList(double x, double y) {
        Button button = new Button();
        button.getStyleClass().add("buttonlist");
        button.setPrefSize(225, 225);
        button.setLayoutX(x);
        button.setLayoutY(y);

        return button;
    }

}
