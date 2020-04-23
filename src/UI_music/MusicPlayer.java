/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.AlertBox;
import static UI_music.Login.stage;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author poomi
 */
public class MusicPlayer {
    private MediaPlayer playerSong;
    private String status;
    private Slider musicSlider;
    private Slider volumeMusic;
    private Boolean interrupt;
    private File selectMusicFile;
    private Stage primaryStage;
    private Scene scene; 
    private FileChooser fileChooser;
    private Label songDetail;
    private double mouse_x,mouse_y;

    
    public MusicPlayer() {
        this.musicSlider = new Slider();
        this.volumeMusic = new Slider();
        this.interrupt = false;
        this.status = "Stop";
        this.musicSlider.setPrefSize(350, 30);
        this.volumeMusic.setPrefSize(110, 30);
        this.mouse_x = 0;
        this.mouse_y = 0;
        fileChooser = new FileChooser();
        this.volumeMusic.setValue(100);
        selectFile(fileChooser);
        
        primaryStage = new Stage();
        BorderPane root = new BorderPane();
        root.setTop(exitButton());
        
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        
        Button playPauseButton = new Button();
        playPauseButton.getStyleClass().add("playControl");
        ImageView play = new ImageView(new Image("/icon/play-button.png"));
        ImageView pause = new ImageView(new Image("/icon/pause.png"));
        play.setFitWidth(30);
        play.setFitHeight(30);
        pause.setFitWidth(30);
        pause.setFitHeight(30);
        playPauseButton.setGraphic(play);
        
        Button stopButton = new Button();
        stopButton.getStyleClass().add("stopControl");
        ImageView stop = new ImageView(new Image("/icon/stop.png"));
        stop.setFitWidth(30);
        stop.setFitHeight(30);
        stopButton.setGraphic(stop);
        
        
        Button selectFile = new Button();
        selectFile.getStyleClass().add("selectFileControl");
        ImageView select = new ImageView(new Image("/icon/file.png"));
        select.setFitWidth(30);
        select.setFitHeight(30);
        selectFile.setGraphic(select);
        
        Button muteButton = new Button();
        muteButton.getStyleClass().add("muteControl");
        ImageView mute = new ImageView(new Image("/icon/mute_0.png"));
        mute.setFitWidth(20);
        mute.setFitHeight(20);
        ImageView unMute = new ImageView(new Image("/icon/speaker.png"));
        unMute.setFitWidth(20);
        unMute.setFitHeight(20);
        muteButton.setGraphic(unMute);
        
        songDetail = new Label("No Music File To Play");
        songDetail.getStyleClass().add("DetailLabel");
       
        playPauseButton.setOnAction(e -> {
            if (!"Stop".equals(status)) {
                if(playerSong.getStatus() == MediaPlayer.Status.PLAYING){
                    playerSong.pause();
                    playPauseButton.setGraphic(play);
                    playPauseButton.getStyleClass().clear();
                    playPauseButton.getStyleClass().add("playControl");
                }else{
                    playerSong.play();
                    interrupt = false;
                    playPauseButton.getStyleClass().clear();
                    playPauseButton.getStyleClass().add("pauseControl");
                    playPauseButton.setGraphic(pause);
                }
            }else{
                selectMusic();
                if ("Playing".equals(status)) {
                    playPauseButton.getStyleClass().clear();
                    playPauseButton.getStyleClass().add("pauseControl");
                    playPauseButton.setGraphic(pause);
                }
            }
        });
        
        stopButton.setOnAction(e -> {
            playerSong.stop();
            playPauseButton.setGraphic(play);
            playPauseButton.getStyleClass().clear();
            playPauseButton.getStyleClass().add("playControl");
        });
        
        
        
        muteButton.setOnMouseClicked(e ->{
            playerSong.setMute(!playerSong.isMute());
            if (playerSong.isMute()) {
                muteButton.setGraphic(mute);
            }else{
                muteButton.setGraphic(unMute);
            }
        });
        
        
       
        
        ImageView songImg = new ImageView("/image/defaultmusic.png");
        songImg.setFitWidth(200);
        songImg.setFitHeight(200);
        
        songImg.setOnMouseClicked(e ->{
            selectMusic();
            if ("Playing".equals(status)) {
                playPauseButton.getStyleClass().clear();
                playPauseButton.getStyleClass().add("pauseControl");
                playPauseButton.setGraphic(pause);
            }

        });
        
        songImg.setOnMouseEntered(e ->{
           scene.setCursor(Cursor.HAND);
        });
        songImg.setOnMouseExited(e ->{
            scene.setCursor(Cursor.DEFAULT);
        });
        
        selectFile.setOnMouseClicked(e ->{
           selectMusic();
            if ("Playing".equals(status)) {
                playPauseButton.getStyleClass().clear();
                playPauseButton.getStyleClass().add("pauseControl");
                playPauseButton.setGraphic(pause);
            }
        });

        hbox.getChildren().addAll(selectFile,playPauseButton, stopButton);
        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(songDetail,songImg,hbox);
        root.setCenter(vbox);
        
        HBox slidebar = new HBox(5);
        slidebar.setAlignment(Pos.CENTER);
        slidebar.getChildren().addAll(musicSlider,muteButton,volumeMusic);
        root.setBottom(slidebar);
        root.setPadding(new Insets(20));
        
        volumeMusic.valueProperty().addListener((ov, t, t1) -> {
            playerSong.setVolume(t1.doubleValue()/100);
            if (t1.doubleValue() == 0) {
                 muteButton.setGraphic(mute);
            }else{
                muteButton.setGraphic(unMute);
            }
        });
        
        root.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        root.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() - mouse_x);
            primaryStage.setY(e.getScreenY() - mouse_y);
        });
        
        
        scene = new Scene(root, 500, 450);
        scene.getStylesheets().add(getClass().getResource("/style_css/musicPlayerStyle.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Spookify Media Player");
        primaryStage.show();
       
        
        
       primaryStage.setOnCloseRequest(e ->{
           if ("Playing".equals(status)) {
               playerSong.stop();
           }
           User_UI.playerStatus = "NotReady";
       });

    }
    
    
     private void selectFile(FileChooser fileChooser) {
      fileChooser.setTitle("Select MusicFile");
 
      fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
 
      fileChooser.getExtensionFilters().addAll(//
              new FileChooser.ExtensionFilter("Music Files", "*.mp3","*.wav","*.m4a"), //
              new FileChooser.ExtensionFilter("MP3", "*.mp3"), //
              new FileChooser.ExtensionFilter("WAV", "*.wav"),
              new FileChooser.ExtensionFilter("M4A", "*.m4a"));
  }
     
     private  void selectMusic(){
         selectMusicFile = fileChooser.showOpenDialog(primaryStage);
            if (selectMusicFile != null) {
                openFile(selectMusicFile);
                songDetail.setText(selectMusicFile.getName().substring(0, selectMusicFile.getName().length()-4));
            }
            interrupt = false;
     }
     
     
     private void openFile(File file) {
        interrupt = true;
        if ("Playing".equals(status)) { 
             playerSong.stop();
        }
        playerSong = new MediaPlayer(new Media(file.toURI().toString()));
        playerSong.play();
        status = "Playing";
        playMusic();
    }
     
     
     private Button exitButton() {

        ImageView exit_icon = new ImageView(new Image("/icon/close-512-detail.png"));
        ImageView exit_hover_icon = new ImageView(new Image("/icon/close-512_hover.png"));
        exit_icon.setFitWidth(15);
        exit_icon.setFitHeight(15);
        exit_hover_icon.setFitWidth(15);
        exit_hover_icon.setFitHeight(15);
        
        Button exit = new Button("", exit_icon);
        exit.setOnMouseEntered(e -> {
            exit.setGraphic(exit_hover_icon);
        });
        exit.setOnMouseExited(e -> {
            exit.setGraphic(exit_icon);
        });
        exit.setOnMouseClicked(e -> {
            primaryStage.close();
            if ("Playing".equals(status)) {
               playerSong.stop();
           }
            
           User_UI.playerStatus = "NotReady";
        });
        exit.setStyle("-fx-background-color : transparent;");
        exit.setPadding(Insets.EMPTY);
        exit.setTranslateX(450);
        exit.setTranslateY(-10);

        return exit;
    }


    private void playMusic() {
            musicSlider.valueProperty().addListener((ov, t, t1) -> {
                musicSlider.valueChangingProperty().addListener((o) -> {
                    playerSong.seek(Duration.seconds((t1.doubleValue()/100)*playerSong.getStopTime().toSeconds()));
                }); 
                musicSlider.setOnMousePressed(e ->{
                    interrupt = true;
                    playerSong.seek(Duration.seconds((musicSlider.getValue()/100)*playerSong.getStopTime().toSeconds()));
                });
                musicSlider.setOnMouseReleased(e ->{
                    interrupt = false;
                });
            });
            playerSong.currentTimeProperty().addListener((ov, t, t1) -> {
                if (!interrupt) {
                    musicSlider.setValue((t1.toSeconds()/playerSong.getStopTime().toSeconds())*100);
                }
            });
    }
}
