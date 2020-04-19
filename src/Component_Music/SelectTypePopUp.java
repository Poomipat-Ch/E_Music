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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author HCARACH
 */
abstract public class SelectTypePopUp {
    
    static Stage stage;

    public SelectTypePopUp() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Upload New Song");
        stage.setResizable(false);
        

        Scene scene = new Scene(ShowPane());
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        scene.getStylesheets().add(stylrSheet);
         scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }
    
    private  AnchorPane ShowPane() {
        AnchorPane anchorpane =  new AnchorPane();
        anchorpane.setPrefSize(500, 500);
        anchorpane.getStyleClass().add("mainBox");
        
        AnchorPane backgroundpane = new AnchorPane();
        backgroundpane.setStyle("-fx-background-color: tranparent");
        //backgroundpane.getStyleClass().add("backgroundSelect");
        backgroundpane.setPrefSize(500, 500);
        
        
        AnchorPane detailpane = new AnchorPane();
        detailpane.setPrefSize(500, 500);      
        
        anchorpane.getChildren().addAll(backgroundpane, detailpane, CreateButton(), CreateHeadLabel(), CreateCloseButton());
        
        return anchorpane;
    }
    
    private Label CreateCloseButton () {
                
        Label close = new Label("CLOSE");
        close.getStyleClass().add("cancelbtn");
        close.setAlignment(Pos.CENTER);
        close.setPrefSize(100, 20);
        close.setLayoutX(200);
        close.setLayoutY(460);
        
        close.setOnMouseClicked(clicked -> {
            this.stage.close();
        });
        
        return close;
    }
    
    abstract AnchorPane CreateButton();
    abstract AnchorPane CreateHeadLabel();
    
}
