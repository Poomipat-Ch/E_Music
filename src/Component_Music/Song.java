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
                new Song("Maps", "3.10s", "Maroon 5"),
                new Song("Sugar", "3.30s", "Maroon 5"),
                new Song("Payphone", "4.05s", "Maroon 5"),
                new Song("One More Night", "3.05s", "Maroon 5"),
                new Song("Natural Birds ", "3.15s", "Imagine Dragons"),
                new Song("Thunder", "3.20s", "Imagine Dragons"),
                new Song("I Bet My Life", "3.08s", "Imagine Dragons"),
                new Song("Radioactive", "3.30s", "Imagine Dragons"),
                new Song("The Scientist", "3.17s", "Coldplay"),
                new Song("Paradise", "4.15s", "Coldplay"),
                new Song("Yellow", "3.15s", "Coldplay"),
                new Song("Everyday Life", "3.16s", "Coldplay")
                
        );
        return list;
    }
    
    public static ObservableList<Song> getMusicList(){
        ObservableList list = FXCollections.observableArrayList(
                new Song("Maps", "3.10s", "Maroon 5"),
                new Song("Sugar", "3.30s", "Maroon 5"),
                new Song("Payphone", "4.05s", "Maroon 5"),
                new Song("One More Night", "3.05s", "Maroon 5"),
                new Song("Natural Birds ", "3.15s", "Imagine Dragons"),
                new Song("Thunder", "3.20s", "Imagine Dragons"),
                new Song("I Bet My Life", "3.08s", "Imagine Dragons"),
                new Song("Radioactive", "3.30s", "Imagine Dragons"),
                new Song("The Scientist", "3.17s", "Coldplay"),
                new Song("Paradise", "4.15s", "Coldplay"),
                new Song("Yellow", "3.15s", "Coldplay"),
                new Song("Everyday Life", "3.16s", "Coldplay")
                
        );
        return list;
    }
    
    
}
