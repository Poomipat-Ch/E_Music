/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 62010710
 */
public class Song {
    
    private String nameSong;
    private String detailSong;
    private String artistSong;

    public Song() {
    }

    public Song(String nameSong, String detailSong, String artistSong) {
        this.nameSong = nameSong;
        this.detailSong = detailSong;
        this.artistSong = artistSong;
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

    @Override
    public String toString() {
        return "name : " + nameSong + " artist : " + artistSong + " detail : " + detailSong + "\n";
    }
    
    
    public static ObservableList<Song> getMyMusicList(){
        ObservableList list = FXCollections.observableArrayList(
                new Song("A", "3.15s", "Bodyslam"),
                new Song("B", "3.15s", "Bodyslam"),
                new Song("C", "3.15s", "Bodyslam"),
                new Song("D", "3.15s", "Bodyslam"),
                new Song("X", "3.15s", "Paradox"),
                new Song("Y", "3.15s", "Paradox"),
                new Song("Z", "3.15s", "Paradox"),
                new Song("W", "3.15s", "Paradox"),
                new Song("I", "3.15s", "BigAss"),
                new Song("J", "3.15s", "BigAss"),
                new Song("K", "3.15s", "BigAss")
                
        );
        return list;
    }
    
    public static ObservableList<Song> getMusicList(){
        ObservableList list = FXCollections.observableArrayList(
                new Song("A", "3.15s", "Bodyslam"),
                new Song("B", "3.15s", "Bodyslam"),
                new Song("C", "3.15s", "Bodyslam"),
                new Song("D", "3.15s", "Bodyslam"),
                new Song("X", "3.15s", "Paradox"),
                new Song("Y", "3.15s", "Paradox"),
                new Song("Z", "3.15s", "Paradox"),
                new Song("W", "3.15s", "Paradox"),
                new Song("I", "3.15s", "BigAss"),
                new Song("J", "3.15s", "BigAss"),
                new Song("K", "3.15s", "BigAss")
                
        );
        return list;
    }
    
    
}
