/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Song;
import java.io.File;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author poomi
 */
public class MusicPlayer {
    static Song song;
    static MediaPlayer playerSong;
    Slider musicSlider;
    Slider volumeMusic;
    Boolean interrupt;

    public MusicPlayer() {
    }
    
    public MusicPlayer(Song song) {
        this.song = song;
        this.playerSong = new MediaPlayer(new Media(new File("src/MusicFile/"+song.getNameSong()+song.getArtistSong()+".mp3").toURI().toString()));
        this.musicSlider = new Slider();
        this.volumeMusic = new Slider();
        this.interrupt = false;
        this.musicSlider.setPrefSize(350, 30);
        this.volumeMusic.setPrefSize(110, 30);
        this.volumeMusic.setValue(playerSong.getVolume()*100);
        
        playerSong.play();
        Stage primaryStage = new Stage();
        BorderPane root = new BorderPane();
        
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        
        Button playPauseButton = new Button();
        ImageView play = new ImageView(new Image("/icon/play-button.png"));
        ImageView pause = new ImageView(new Image("/icon/pause.png"));
        play.setFitWidth(30);
        play.setFitHeight(30);
        pause.setFitWidth(30);
        pause.setFitHeight(30);
        playPauseButton.setGraphic(pause);
        
        Button stopButton = new Button();
        ImageView stop = new ImageView(new Image("/icon/stop.png"));
        stop.setFitWidth(30);
        stop.setFitHeight(30);
        stopButton.setGraphic(stop);
       
        playPauseButton.setOnAction(e -> {
            if(playerSong.getStatus() == MediaPlayer.Status.PLAYING){
                playerSong.pause();
                playPauseButton.setGraphic(play);
            }else{
                playerSong.play();
                interrupt = false;
                playPauseButton.setGraphic(pause);
            }
        });
        stopButton.setOnAction(e -> {
            playerSong.stop();
        });
        
        Label songDetail = new Label(this.song.getNameSong() + " - "+this.song.getArtistSong());
        
        ImageView songImg = new ImageView(song.getPhoto());
        songImg.setFitWidth(200);
        songImg.setFitHeight(200);

        hbox.getChildren().addAll(playPauseButton, stopButton);
        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(songDetail,songImg,hbox);
        root.setCenter(vbox);
        
        Button muteButton = new Button();
        ImageView mute = new ImageView(new Image("/icon/mute.png"));
        mute.setFitWidth(20);
        mute.setFitHeight(20);
        ImageView unMute = new ImageView(new Image("/icon/volume.png"));
        unMute.setFitWidth(20);
        unMute.setFitHeight(20);
        muteButton.setGraphic(unMute);
        
        muteButton.setOnMouseClicked(e ->{
            playerSong.setMute(!playerSong.isMute());
            if (playerSong.isMute()) {
                muteButton.setGraphic(mute);
            }else{
                muteButton.setGraphic(unMute);
            }
        });
        
        HBox Slidebar = new HBox(5);
        Slidebar.getChildren().addAll(musicSlider,muteButton,volumeMusic);
        root.setBottom(Slidebar);
        root.setPadding(new Insets(20));
        
        volumeMusic.valueProperty().addListener((ov, t, t1) -> {
            playerSong.setVolume(t1.doubleValue()/100);
            if (t1.doubleValue() == 0) {
                 muteButton.setGraphic(mute);
            }else{
                muteButton.setGraphic(unMute);
            }
        });
        
        
        musicSlider.valueProperty().addListener((ov, t, t1) -> {
            musicSlider.valueChangingProperty().addListener((o) -> {
                playerSong.seek(Duration.seconds((t1.doubleValue()/100)*playerSong.getStopTime().toSeconds()));
                }); 
            musicSlider.setOnMouseClicked(e ->{
                interrupt = true;
                playerSong.seek(Duration.seconds((musicSlider.getValue()/100)*playerSong.getStopTime().toSeconds()));
            });
        });

        
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                playerSong.currentTimeProperty().addListener((ov, t, t1) -> {
                if (!interrupt) {
                    musicSlider.setValue((t1.toSeconds()/playerSong.getStopTime().toSeconds())*100);
                }
                  });
                return null ;
            }
        };

         new Thread(task).run();
        
        
        Scene scene = new Scene(root, 500, 450);
        scene.getStylesheets().add(getClass().getResource("/style_css/musicPlayerStyle.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Spookify Media Player");
        primaryStage.show();
        
        
        
        
       primaryStage.setOnCloseRequest(e ->{
           playerSong.stop();
           this.song = null;
       });

    }
    
    
    
    public static String getStatus(){
        if(song == null)
            return "";
      return playerSong.getStatus().name();
    }
}
