/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.io.Serializable;

/**
 *
 * @author Frontae
 */
public class AddSong implements Serializable{
    private static final long serialVersionUID = 6529685098267757693L;
    private Song song;
    private int numberOfDownload;
    private String nameSong;
    private String detailSong;
    private String artistSong;
    
    public AddSong(){
        
    }
    
    public AddSong(Song song, int numberOfDownload){
        this.song = song;
        this.numberOfDownload += numberOfDownload;
        this.nameSong = song.getNameSong();
        this.detailSong = song.getDetailSong();
        this.artistSong = song.getArtistSong();
    }
    

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getNumberOfDownload() {
        return this.numberOfDownload;
    }

    public void setNumberOfDownload(int numberOfDownload) {
        this.numberOfDownload += numberOfDownload;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getDetailSong() {
        return detailSong;
    }

    public void setDetailSong(String detailSong) {
        this.detailSong = detailSong;
    }

    public String getArtistSong() {
        return artistSong;
    }

    public void setArtistSong(String artistSong) {
        this.artistSong = artistSong;
    }
}
