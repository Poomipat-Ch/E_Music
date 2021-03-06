/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Artist;
import Component_Music.ImageCircle;
import Component_Music.ImageRectangle;
import Component_Music.ShowMusicPage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author HCARACH
 */
public class BrowsePane {

    private ScrollPane scrollpane;
    private AnchorPane backgroundpane;

    private ObservableList<Artist> artistList;

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
        
        ImageView imageview = new ImageView(new Image("/image/BrowseBackground.jpg"));
        imageview.setFitWidth(1030);
        imageview.setPreserveRatio(true);

        Label browse = createHead("Browse", 50, 70);
        Button thaigenresbtn = createButton("เพลงไทย", 40, 190, 260, 1);
        Button thaigenresbg = createBackgroundButton(40, 190, 260);
        Button thaiartistbtn = createButton("ศิลปิน", 320, 190, 180, 2);
        Button thaiartistbg = createBackgroundButton(320, 190, 180);
        Button intergenresbtn = createButton("GLOBAL SONGS", 520, 190, 260, 0);
        Button intergenresbg = createBackgroundButton(520, 190, 260);
        Button interartistbtn = createButton("ARTIST", 800, 190, 180, 2);
        Button interartistbg = createBackgroundButton(800, 190, 180);

        try {
            backgroundpane.getChildren().addAll(imageview, thaigenresbg, thaigenresbtn, thaiartistbg, thaiartistbtn, intergenresbg, intergenresbtn,
                    interartistbg, interartistbtn, browse, borderList("เพลงไทย", 1));
        } catch (IOException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        backgroundpane.setMinHeight(backgroundpane.getHeight() + 50);

        scrollpane.setContent(backgroundpane);

    }

    public ScrollPane getBrowsePane() {

        return this.scrollpane;
    }

    private Button createButton(String name, double x, double y, double width, int index) {
        Button button = new Button(name);
        button.getStyleClass().add("buttoninbrowse");
        button.setPrefSize(width, 50);
        button.setLayoutX(x);
        button.setLayoutY(y);

        button.setOnAction(event -> {
            this.backgroundpane.getChildren().remove(10);

            try {
                this.backgroundpane.getChildren().add(borderList(name, index));
            } catch (IOException ex) {
                Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return button;
    }

    private Button createBackgroundButton(double x, double y, double width) {
        Button button = new Button();
        button.getStyleClass().add("backgroundbuttoninbrowse");
        button.setPrefSize(width, 50);
        button.setLayoutX(x);
        button.setLayoutY(y);

        return button;
    }

    private Label createHead(String string, double x, double y) {
        Label label = new Label(string);
        label.getStyleClass().add("browseheadlabel");
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    private AnchorPane borderList(String name, double index) throws IOException {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("borderlist");
        anchorpane.setPrefWidth(1030);
        anchorpane.setMinHeight(664);
        anchorpane.setLayoutY(240);
        anchorpane.setPadding(new Insets(0, 0, 50, 0));

        artistList = FXCollections.observableArrayList();
        try {
            artistList = Artist.getArtistList();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BrowsePane.class.getName()).log(Level.SEVERE, null, ex);
        }
        //VBox vBox;
        if (index == 2) {
            int i = 0;
            if (name.contains("ARTIST")) {
                for (Artist artist : artistList) {
                    if (artist.getNationality().equals("international")) {
                        AnchorPane listAlbums = createArtistList(artist, (235 * (i % 4)) + 50, (235 * (i / 4)) + 75);
                        i++;

                        anchorpane.getChildren().add(listAlbums);
                    }
                }
            } else {
                for (Artist artist : artistList) {
                    if (artist.getNationality().equals("thai")) {
                        AnchorPane listAlbums = createArtistList(artist, (235 * (i % 4)) + 50, (235 * (i / 4)) + 75);
                        i++;

                        anchorpane.getChildren().add(listAlbums);
                    }
                }
            }

        } else {
            int i = 0;

            ArrayList<StyleMusicList> list = readFile().get((int) index);

            for (StyleMusicList object : list) {
                AnchorPane listAlbums = createAlbumsList(object, (235 * (i % 4)) + 50, (235 * (i / 4)) + 75);
                i++;

                listAlbums.setOnMouseClicked(e -> {
                    System.out.println("click" + listAlbums.getLayoutX());
                });

                anchorpane.getChildren().add(listAlbums);
            }
        }

        anchorpane.getChildren().add(BrowsePane.this.createLabel(name, 50, 15));

        return anchorpane;
    }

    private Label createLabel(String string, double x, double y) {
        Label label = new Label(string);
        label.setPadding(new Insets(5));
        label.getStyleClass().add("browselabel");
        label.setMinWidth(930);
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    private AnchorPane createArtistList(Artist artist, double x, double y) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("artistbuttonlist");
        anchorpane.setPrefSize(225, 225);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);
        anchorpane.getChildren().addAll(new ImageCircle(112.5, 92, 90, artist.getPhoto()).getMyCircle(), createLabel(artist.getName1(), 190, artist.getPhoto()));

        return anchorpane;
    }

    private AnchorPane createAlbumsList(StyleMusicList object, double x, double y) {
        System.out.println(object.getName());
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getStyleClass().add("buttonlist");
        anchorpane.setPrefSize(225, 225);
        anchorpane.setLayoutX(x);
        anchorpane.setLayoutY(y);
        
        AnchorPane background = new AnchorPane();
        background.setPrefSize(225, 225);
        background.getStyleClass().add("backgroundlist");
        
        anchorpane.getChildren().addAll(new ImageRectangle(0, 0, 225, 225, object.getPhoto()).getMyRectangle(), background, createIcon(object.getName()), createLabel(object.getName(), 160, object.getPhoto()));

        return anchorpane;
    }

    private ImageView createIcon(String name) {
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

    private Button createLabel(String string, int dis, Image image) {
        Button button = new Button(string);
        button.setPrefSize(225, 225);
//        button.setLayoutY(180);
        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("browsebuttonlabel");
        button.setPadding(new Insets(dis, 0, 0, 0));

        button.setOnMouseClicked(event -> {
            if (dis == 160) 
                new ShowMusicPage(string, "", image); // song
            else
                new ShowMusicPage(string, "artist", image); // artist
        });

        return button;
    }

    private ArrayList<ArrayList> readFile() {

        ArrayList<ArrayList> list = new ArrayList<>();

//        ObjectOutputStream outfile = null;
//        try {
//            outfile = new ObjectOutputStream(new FileOutputStream("src/data/genreslist.dat"));
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
//        ArrayList<StyleMusicList> interNewList = new ArrayList<>();
//        
//        for (String string : interList) {
//            interNewList.add(new StyleMusicList(string, new Image("/image/" + string + "Background.jpg") ));
//        }
//    
//        
//        ArrayList<String> thaiList = new ArrayList<>();
//
//        thaiList.add("คลาสสิก");
//        thaiList.add("เร็กเก");
//        thaiList.add("สังสรรค์");
//        thaiList.add("แดนซ์");
//        thaiList.add("อินดี้");
//        thaiList.add("เพื่อชีวิต");
//        thaiList.add("ป๊อป");
//        thaiList.add("โรแมนติก");
//        thaiList.add("สบาย");
//        thaiList.add("ลูกทุ่ง");
//        thaiList.add("แจ๊ส");
//        thaiList.add("ร็อค");
//        thaiList.add("อาร์แอนด์บี");
//        thaiList.add("ฮิป-ฮอป");
//        
//        ArrayList<StyleMusicList> thaiNewList = new ArrayList<>();
//        
//        for (String string : thaiList) {
//            thaiNewList.add(new StyleMusicList(string, new Image("/image/" + string + "Background.jpg") ));
//        }
//        
//        list.add(interNewList);
//        list.add(thaiNewList);
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
//        
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream("src/data/genreslist.dat"));
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

class StyleMusicList implements Serializable{
    
    private int width, height;
    private int[][] data;
    
    private String name;

    public StyleMusicList(String name, Image image) {
        this.name = name;
        
        this.setPhoto(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
     public void setPhoto(Image image) {
        width = ((int) image.getWidth());
        height = ((int) image.getHeight());
        data = new int[width][height];

        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = r.getArgb(i, j);
            }
        }
    }

    public Image getPhoto() {
        WritableImage img = new WritableImage(width, height);

        PixelWriter w = img.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                w.setArgb(i, j, data[i][j]);
            }
        }

        return img;
    }
    
}
