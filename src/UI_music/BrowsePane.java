/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Artist;
import Component_Music.TopChartMusicPage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        scrollpane.getStyleClass().add("scroll-bar");
        scrollpane.setPadding(Insets.EMPTY);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollpane.fitToWidthProperty().set(true);
        
        AnchorPane backgroundpane = new AnchorPane();
        backgroundpane.setPrefWidth(1030);
        backgroundpane.setMinHeight(901);
        backgroundpane.getStyleClass().add("mainBox");
        //backgroundpane.setPadding(new Insets(0, 0 ,50,0));

        ImageView playlist = CreateImageView(50, 200);
        ImageView artist = CreateImageView(350, 200);
        Label genres = CreateLabel("GENRES & MOODS", 150, 220);
        Label production = CreateLabel("ARTIST", 450, 220);
        Label browse = CreateHead("Browse", 50, 100);
        HBox genresbtn = CreateButton(40, 190, 280);
        HBox artistbtn = CreateButton(340, 190, 200);
        
        try {
            backgroundpane.getChildren().addAll(genresbtn, artistbtn, playlist, artist, genres, production, browse, BorderList());
        } catch (IOException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundpane.setMinHeight(backgroundpane.getHeight() + 50);
        
        scrollpane.setContent(backgroundpane);
        
    }
    
    public ScrollPane getBrowsePane() {
        
        return this.scrollpane;
    }
    
    private HBox CreateButton(double x, double y, double width) {
        HBox button = new HBox();
        button.getStyleClass().add("buttoninbrowse");
        button.setPrefSize(width, 100);
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
    
    private AnchorPane BorderList() throws IOException {
        AnchorPane borderpane = new AnchorPane();
        borderpane.getStyleClass().add("borderlist");
        borderpane.setPrefWidth(1030);
        borderpane.setMinHeight(614);
        borderpane.setLayoutY(290);
        borderpane.setPadding(new Insets(0, 0, 50, 0));

        //VBox vBox;
        int i = 0;
        for (String string : ReadFile()) {
            AnchorPane listAlbums = CreateAlbumsList(string, (235 * (i % 4)) + 50, (235 * (i / 4)) + 50);
            i++;
            
            listAlbums.setOnMouseClicked(e -> {
                System.out.println("click" + listAlbums.getLayoutX());
            });
            
            borderpane.getChildren().add(listAlbums);
        }
        return borderpane;
    }
    
    private AnchorPane CreateAlbumsList(String string, double x, double y) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("buttonlist");
        anchorpane.setPrefSize(225, 225);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);
        anchorpane.getChildren().addAll(CreateIcon(string),CreateLabel(string));
        
        return anchorpane;
    }
    
    private ImageView CreateIcon(String name) {
        System.out.println(name);
        ImageView image = new ImageView(new Image("/icon/" + name + "icon.png"));
        image.setFitHeight(100);
        image.setFitWidth(100);
        image.setPreserveRatio(true);
        image.setLayoutX(62.5);
        image.setLayoutY(50);
        image.getStyleClass().add("image");
        
        return image;
    }
    
    private Button CreateLabel(String string) {
        Button button = new Button(string);
        button.setPrefSize(225, 225);
//        button.setLayoutY(180);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("browselabel");
        button.setPadding(new Insets(160, 0, 0, 0));
        
        button.setOnMouseClicked(event -> {
            new TopChartMusicPage(string, User_UI.userAccount);
        });
        
        return button;
    }
    
    private ArrayList<String> ReadFile() {
        
        ArrayList<String> list = new ArrayList<>();

//        ObjectOutputStream outfile = null;
//        try {
//            outfile = new ObjectOutputStream(new FileOutputStream("src/data/stylemusiclist.dat"));
//        } catch (IOException ex) {
//            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        list.add("Pop");
//        list.add("Romance");
//        list.add("Chill");
//        list.add("Jazz");
//        list.add("Rock");
//        list.add("R&B");
//        list.add("Hip-Hop");
//        list.add("Country");
//        list.add("Blues");
//        list.add("Classical");
//        list.add("Metal");
//        list.add("Reggae");
//        list.add("Folk & Acoustic");
//        list.add("Punk");
//        list.add("Anime");
//        list.add("Party");
//        list.add("Dance");
//        list.add("Indie");
//        list.add("Soul");
//
//
//        try {
//            outfile.writeObject(list);
//        } catch (IOException ex) {
//            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            outfile.close();
//        } catch (IOException ex) {
//            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream("src/data/stylemusiclist.dat"));
        } catch (IOException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            try {
                list = (ArrayList<String>) file.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
}
