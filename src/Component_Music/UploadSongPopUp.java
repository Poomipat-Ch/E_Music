/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
 * @author Sirawit
 */
public class UploadSongPopUp {
    private HBox totalDetail;
    //private BorderPane totalDetail;
    private Stage stage;

    public UploadSongPopUp() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Upload New Song");
        stage.setResizable(false);
        
        DetailUploadSong();
        Scene scene = new Scene(totalDetail);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        scene.getStylesheets().add(stylrSheet); // CSS
        
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }
    
    double mouse_x = 0,mouse_y = 0; // position mouse
    
    private void DetailUploadSong() {
        totalDetail = new HBox(30);
        
        VBox detail = new VBox(30);
        VBox imageDetail = new VBox(5);
        
        totalDetail.getStyleClass().add("allPane"); //CSS
        totalDetail.setAlignment(Pos.TOP_CENTER);
        totalDetail.setPadding(new Insets(50)); 
       
        //detail.setPadding(new Insets(50)); 

//        detail.setOnMousePressed(e -> {
//            mouse_x = e.getSceneX();
//            mouse_y = e.getSceneY();
//            //System.out.println(mouse_x + " " + mouse_y);
//       });
//       detail.setOnMouseDragged(e -> {
//           stage.setX(e.getScreenX() - mouse_x);
//           stage.setY(e.getScreenY() - mouse_y);
//       });
        
        //Image Song
        Image img = new Image("/image/blankimage.jpg");
        ImageView imageSong = new ImageView(img);
        imageSong.setFitWidth(250);
        imageSong.setFitHeight(300);
        
        //Upload Picture
        Button imageBtn = new Button("Upload Picture");
        imageBtn.setOnAction(e->{
            //GUT -> Upload Picture
        });
        imageBtn.getStyleClass().add("buybtn"); //CSS
        
        imageDetail.getChildren().addAll(imageSong,imageBtn);

        //Detail Song

        
        //Upload detail song
       
        //Song
        Label nameSong = new Label("Song : ");
        TextField fillNameSong = new TextField();
        fillNameSong.setPromptText("Name");
                
        nameSong.getStyleClass().add("nameSong"); //CSS
        //fillNameSong.getStyleClass().add("nameSong"); //CSS
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(nameSong,fillNameSong);
     
        //Artist
        Label nameArtist = new Label("Artist : ");
        TextField fillNameArtist = new TextField();
        fillNameArtist.setPromptText("Name / Band");
        
        nameArtist.getStyleClass().add("nameArtist"); //CSS
        //fillNameArtist.getStyleClass().add("nameArtist"); //CSS
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(nameArtist,fillNameArtist);
        
        //Detail
        Label detailSong = new Label("Detail : ");
        TextField fillDetailSong = new TextField();
        fillDetailSong.setPromptText("min:sec/dateOfSong/Albums");
         
        detailSong.getStyleClass().add("detailSong"); //CSS
        //fillDetailSong.getStyleClass().add("detailSong"); //CSS
        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(detailSong,fillDetailSong);
        
        //Price
        Label songPrice = new Label("Price : "); 
        TextField fillSongPrice = new TextField();
        fillSongPrice.setPromptText("e.g. 100 Bahts");
        
        songPrice.getStyleClass().add("price"); //CSS
        //fillSongPrice.getStyleClass().add("detailSong"); //CSS
        HBox hbox4 = new HBox();
        hbox4.getChildren().addAll(songPrice,fillSongPrice);

        
        //Button Buy

        Button uploadBtn= new Button("Upload File .MP3");
        uploadBtn.getStyleClass().add("buybtn"); // borrow...
        uploadBtn.setOnMouseClicked(e ->{
            // Gut <<<<<<<------<<<<
        });
        uploadBtn.setTranslateX(50); 
        
//        HBox buyPane = new HBox(20,songPrice,buyButton);
//        buyPane.setAlignment(Pos.CENTER);
//        buyPane.setPadding(new Insets(10));
                
        detail.getChildren().addAll(hbox1,hbox2,hbox3,hbox4,uploadBtn,exitButton()); //,buyPane
        totalDetail.getChildren().addAll(imageDetail,detail);

       
    }
    
    private Button exitButton(){
        
       //Exit with Decoration
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
       exit.setStyle("-fx-background-color : transparent;"); //CSS
       exit.setPadding(Insets.EMPTY);
       exit.setTranslateX(100);
       
       return exit;
    }  
    
}
