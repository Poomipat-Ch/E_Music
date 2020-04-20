/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author 62010710
 */
public class Song implements Serializable {

    private String nameSong;
    private String detailSong;
    private String artistSong;
    private String priceSong;
    private String nationality;
    private int width, height;
    private int[][] data;

    private static File musicFile = new File("src/data/music.dat");
    
    private ArrayList<String> listStyleSong = new ArrayList<>();

    public Song() {
    }

    public Song(String nameSong, String detailSong, String artistSong, String priceSong, ArrayList<String> listStyleSong, Image image, String nationality) {
        this.nameSong = nameSong;
        this.detailSong = detailSong;
        this.artistSong = artistSong;
        this.priceSong = priceSong;
        this.setPhoto(image);
        this.nationality = nationality;
        
        this.listStyleSong = listStyleSong;
    }

    public Song(Song song) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNameSong() {
        return nameSong;
    }

    public String getDetailSong() {
        return detailSong;
    }

    public String getArtistSong() {
        return artistSong;
    }

    public String getPriceSong() {
        return priceSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public void setDetailSong(String detailSong) {
        this.detailSong = detailSong;
    }

    public void setArtistSong(String artistSong) {
        this.artistSong = artistSong;
    }

    public void setPriceSong(String priceSong) {
        this.priceSong = priceSong;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
 
    @Override
    public String toString() {
        return "name : " + nameSong + " artist : " + artistSong + " detail : " + detailSong + "\n";
    }

    public static ObservableList<Song> getMyMusicList() throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(musicFile));

        ObservableList list = FXCollections.observableArrayList();

        for (Song song : (ArrayList<Song>) in.readObject()) {
            list.add(song);
        }

        return list;
    }

    public static ObservableList<Song> getMusicList() {
        ObservableList list = FXCollections.observableArrayList( //                new Song("Maps", "3.10s", "Maroon 5"),
                //                new Song("Sugar", "3.30s", "Maroon 5"),
                //                new Song("Payphone", "4.05s", "Maroon 5"),
                //                new Song("One More Night", "3.05s", "Maroon 5"),
                //                new Song("Natural Birds ", "3.15s", "Imagine Dragons"),
                //                new Song("Thunder", "3.20s", "Imagine Dragons"),
                //                new Song("I Bet My Life", "3.08s", "Imagine Dragons"),
                //                new Song("Radioactive", "3.30s", "Imagine Dragons"),
                //                new Song("The Scientist", "3.17s", "Coldplay"),
                //                new Song("Paradise", "4.15s", "Coldplay"),
                //                new Song("Yellow", "3.15s", "Coldplay"),
                //                new Song("Everyday Life", "3.16s", "Coldplay")
                );
        return list;
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

    public ArrayList<String> getListStyleSong() {
        return listStyleSong;
    }
    
    public Song getSong() {
        return this;
    }
    
}
