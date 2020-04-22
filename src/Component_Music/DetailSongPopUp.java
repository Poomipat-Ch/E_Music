/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.Register;
import UI_music.UI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author poomi
 */
public class DetailSongPopUp {
    private Song song;
    private VBox detail;
    //private BorderPane totalDetail;
    private Stage stage;
            
    public DetailSongPopUp(Song song) throws InterruptedException {
        this.song = song;
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Detail Song");
        stage.setResizable(false);
        
        DetailSong();
        Scene scene = new Scene(detail);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }

    
    double mouse_x = 0,mouse_y = 0; // position mouse
    
    private void DetailSong() {
        this.detail = new VBox();
        detail.getStyleClass().add("allPane");
        detail.setAlignment(Pos.TOP_CENTER);
        detail.setPadding(new Insets(50));
        detail.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
       });
       detail.setOnMouseDragged(e -> {
           stage.setX(e.getScreenX() - mouse_x);
           stage.setY(e.getScreenY() - mouse_y);
       });
        
        //Image Song
        Image imgSong = new Image("/image/1.jpg");
        ImageView imageSong = new ImageView(imgSong);
        imageSong.setFitWidth(250);
        imageSong.setFitHeight(300);
        
        //detail song
        Label nameSong = new Label("Song : "+this.song.getNameSong());
        nameSong.getStyleClass().add("nameSong");
        
        Label nameArtist = new Label("Artist : " + this.song.getArtistSong());
        nameArtist.getStyleClass().add("nameArtist");
        
        Label detailSong = new Label("Detail : " + this.song.getDetailSong());
        detailSong.getStyleClass().add("detailSong");
        
        //Button Buy
        Label songPrice = new Label(song.getPriceSong() +" Bath"); // Gut add Price Song
        songPrice.getStyleClass().add("price");
        
        Button buyButton = new Button("Buy");
        buyButton.getStyleClass().add("buybtn");
        buyButton.setOnMouseClicked(e ->{
            // Gut <<<<<<<------<<<< La Tonnee pen Font <-----------------------------------------------------------------------------------**********
            System.out.println("Clicked");
            if ("guest".equals(UI.userAccount.getUserRole())) { // wait
                
                AlertBox registerFirst = new AlertBox();
                registerFirst.displayAlert("Register First", "Register Free Account to Buy");
                new Register("member");
           
                        
            }else{
                Cashing cashing = new Cashing();
                cashing.Info(new Stage(),song);
            }
            
            System.out.println("Calling Info");
        });
        
        HBox buyPane = new HBox(20,songPrice,buyButton);
        buyPane.setAlignment(Pos.CENTER);
        buyPane.setPadding(new Insets(10));
        

        detail.getChildren().addAll(imageSong,nameSong,nameArtist,detailSong,buyPane,exitButton());
    }
    
    private Button exitButton(){
        
       Image exit_icon = new Image("/icon/close-512-detail.png");
       Image exit_hover_icon = new Image("/icon/close-512_hover.png");
       
       ImageView imageView = new ImageView(exit_icon);
       imageView.setFitHeight(20);
       imageView.setFitWidth(20);
       
       ImageView imageView_hover = new ImageView(exit_hover_icon);
       imageView_hover.setFitHeight(20);
       imageView_hover.setFitWidth(20);
       
       Button exit = new Button("",imageView);
       exit.setOnMouseEntered(e -> { 
           exit.setGraphic(imageView_hover);
       });
       exit.setOnMouseExited(e -> {
           exit.setGraphic(imageView);
       });
       exit.setOnMouseClicked(e -> {
           stage.close();
       });
       exit.setStyle("-fx-background-color : transparent;");
       exit.setPadding(Insets.EMPTY);
       
       return exit;
    }
    
    
    
    
}
