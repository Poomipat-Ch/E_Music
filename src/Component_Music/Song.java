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
                new Song("asdasd", "3.15s", "Poom"),
                new Song("asdzzzasd", "3.15s", "pop"),
                new Song("asdasqqqd", "3.15s", "gut"),
                new Song("asdasdasd", "3.15s", "font"),
                new Song("asdahhsd", "3.15s", "rarch"),
                new Song("asdasd", "3.15s", "Poom"),
                new Song("das", "3.15s", "Poom"),
                new Song("aszzxdasd", "3.15s", "Poom"),
                new Song("aertweasd", "3.15s", "Poom"),
                new Song("asxcasdasd", "3.15s", "Poom"),
                new Song("aasdsdasd", "3.15s", "Poom")
                
        );
        return list;
    }
    
        public static ObservableList<Song> getMusicList(){
        ObservableList list = FXCollections.observableArrayList(
                new Song("asdasd", "3.15s", "Poom"),
                new Song("asdzzzasd", "3.15s", "pop"),
                new Song("asdasqqqd", "3.15s", "gut"),
                new Song("asdasdasd", "3.15s", "font"),
                new Song("asdahhsd", "3.15s", "rarch"),
                new Song("asdasd", "3.15s", "Poom"),
                new Song("das", "3.15s", "Poom"),
                new Song("aszzxdasd", "3.15s", "Poom"),
                new Song("aertweasd", "3.15s", "Poom"),
                new Song("asxcasdasd", "3.15s", "Poom"),
                new Song("aasdsdasd", "3.15s", "Poom")
                
        );
        return list;
    }
    
    
}
