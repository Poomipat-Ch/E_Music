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
import javafx.scene.control.CheckBox;
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
    AnchorPane backgroundpane;
    
    public BrowsePane() {
        scrollpane = new ScrollPane();
        scrollpane.setPrefSize(1030, 920);
        scrollpane.getStyleClass().add("scroll-bar");
        scrollpane.setPadding(Insets.EMPTY);
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollpane.fitToWidthProperty().set(true);
        
        backgroundpane = new AnchorPane();
        backgroundpane.setPrefWidth(1030);
        backgroundpane.setMinHeight(901);
        backgroundpane.getStyleClass().add("mainBox");
        //backgroundpane.setPadding(new Insets(0, 0 ,50,0));

        Label browse = CreateHead("Browse", 50, 70);
        Button thaigenresbtn = CreateButton("หมวดหมู่ และ อารมณ์",40, 190, 260, 1);
            Button thaigenresbg = CreateBackgroundButton(40, 190, 260);
        Button thaiartistbtn = CreateButton("ศิลปิน",320, 190, 180, 1);
            Button thaiartistbg = CreateBackgroundButton(320, 190, 180);
        Button intergenresbtn = CreateButton("GENRES & MOODS",520, 190, 260, 0);
            Button intergenresbg = CreateBackgroundButton(520, 190, 260);
        Button interartistbtn = CreateButton("ARTIST",800, 190, 180, 0);
            Button interartistbg = CreateBackgroundButton(800, 190, 180);
        
        try {
            backgroundpane.getChildren().addAll(thaigenresbg, thaigenresbtn, thaiartistbg, thaiartistbtn, intergenresbg, intergenresbtn, 
                    interartistbg, interartistbtn, browse, BorderList("หมวดหมู่ และ อารมณ์", 1));
        } catch (IOException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundpane.setMinHeight(backgroundpane.getHeight() + 50);
        
        scrollpane.setContent(backgroundpane);
        
    }
    
    public ScrollPane getBrowsePane() {
        
        return this.scrollpane;
    }
    
    private Button CreateButton(String name,double x, double y, double width, int index) {
        Button button = new Button(name);
        button.getStyleClass().add("buttoninbrowse");
        button.setPrefSize(width, 50);
        button.setLayoutX(x);
        button.setLayoutY(y);
        
        button.setOnAction(event -> {
            this.backgroundpane.getChildren().remove(9);
            
            try {
                this.backgroundpane.getChildren().add(BorderList(name, index));
            } catch (IOException ex) {
                Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        return button;
    }
    
    private Button CreateBackgroundButton(double x, double y, double width) {
        Button button = new Button();
        button.getStyleClass().add("backgroundbuttoninbrowse");
        button.setPrefSize(width, 50);
        button.setLayoutX(x);
        button.setLayoutY(y);
        
        return button;
    }
    
    private ImageView CreateImageView(double x, double y) {
        ImageView image = new ImageView(new Image("/UI_music/defaultprofile.png"));
        image.setFitHeight(80);
        image.setFitWidth(80);
        image.setLayoutX(x);
        image.setLayoutY(y);
        
        return image;
    }
    
    private Label CreateHead(String string, double x, double y) {
        Label label = new Label(string);
        label.getStyleClass().add("browseheadlabel");
        label.setLayoutX(x);
        label.setLayoutY(y);
        
        return label;
    }
    
    private AnchorPane BorderList(String name, double index) throws IOException {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("borderlist");
        anchorpane.setPrefWidth(1030);
        anchorpane.setMinHeight(614);
        anchorpane.setLayoutY(240);
        anchorpane.setPadding(new Insets(0, 0, 50, 0));

        //VBox vBox;
        int i = 0;
        
        ArrayList<String> list = ReadFile().get((int) index);
        
        for (String string : list) {
            AnchorPane listAlbums = CreateAlbumsList(string, (235 * (i % 4)) + 50, (235 * (i / 4)) + 75);
            i++;
            
            listAlbums.setOnMouseClicked(e -> {
                System.out.println("click" + listAlbums.getLayoutX());
            });
            
            anchorpane.getChildren().add(listAlbums);
        }
        
        anchorpane.getChildren().add(CreateLabel(name, 50, 15));
        
        return anchorpane;
    }
    
    private Label CreateLabel(String string, double x, double y) {
        Label label = new Label(string);
        label.setPadding(new Insets(5));
        label.getStyleClass().add("browselabel");
        label.setMinWidth(930);
        label.setLayoutX(x);
        label.setLayoutY(y);
        
        return label;
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
//        System.out.println(name);
        ImageView image = new ImageView(new Image("/icon/" + name + "icon.png"));
        image.setFitHeight(60);
        image.setFitWidth(60);
        image.setPreserveRatio(true);
        image.setLayoutX(82.5);
        image.setLayoutY(70);
        image.getStyleClass().add("image");

        return image;
    }
    
    private Button CreateLabel(String string) {
        Button button = new Button(string);
        button.setPrefSize(225, 225);
//        button.setLayoutY(180);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("browsebuttonlabel");
        button.setPadding(new Insets(160, 0, 0, 0));
        
        button.setOnMouseClicked(event -> {
            new TopChartMusicPage(string);
        });
        
        return button;
    }
    
    private ArrayList<ArrayList> ReadFile() {
        
        ArrayList<ArrayList> list = new ArrayList<>();
        
//        ObjectOutputStream outfile = null;
//        try {
//            outfile = new ObjectOutputStream(new FileOutputStream("src/data/stylemusiclist.dat"));
//        } catch (IOException ex) {
//            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        ArrayList<String> interList = new ArrayList<>();
//
//        interList.add("Pop");
//        interList.add("Romance");
//        interList.add("Chill");
//        interList.add("Jazz");
//        interList.add("Rock");
//        interList.add("R&B");
//        interList.add("Hip-Hop");
//        interList.add("Country");
//        interList.add("Blues");
//        interList.add("Classical");
//        interList.add("Metal");
//        interList.add("Reggae");
//        interList.add("Folk & Acoustic");
//        interList.add("Punk");
//        interList.add("Anime");
//        interList.add("Party");
//        interList.add("Dance");
//        interList.add("Indie");
//        interList.add("Soul");
//        
//        ArrayList<String> thaiList = new ArrayList<>();
//
//        thaiList.add("ป๊อป");
//        thaiList.add("โรแมนติก");
//        thaiList.add("สบาย");
//        thaiList.add("แจ๊ส");
//        thaiList.add("ร็อค");
//        thaiList.add("อาร์แอนด์บี");
//        thaiList.add("ฮิป-ฮอป");
//        thaiList.add("ลูกทุ่ง");
//        thaiList.add("คลาสสิก");
//        thaiList.add("เร็กเก");
//        thaiList.add("สังสรรค์");
//        thaiList.add("แดนซ์");
//        thaiList.add("อินดี้");
//        thaiList.add("เพื่อชีวิต");
//        
//        list.add(interList);
//        list.add(thaiList);
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
                list = (ArrayList<ArrayList>) file.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
}
