/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author HCARACH
 */
public class AllSong {
    
    ScrollPane scrollPane;
    VBox vbox;
    HBox hbox;
    
    public AllSong() throws FileNotFoundException {
        
        vbox = new VBox(10);
        scrollPane = new ScrollPane();
        scrollPane.setMinSize(1030-300-60, 200);
        scrollPane.setLayoutX(20);
        scrollPane.setLayoutY(100);
        scrollPane.pannableProperty().set(false);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        scrollPane.setPadding(new Insets(20, 20, 20, 20));
        scrollPane.setStyle("-fx-background-color: #e9967a");
        
        ImageView imageView;
        
        VBox totalbox = new VBox(30);
        //totalbox.setPadding(new Insets(30, 30, 30, 30));
        
        for(int i = 0; i< 10; ++i) {
            hbox = new HBox(10);
            hbox.setPadding(new Insets(0, 30, 0, 30));
            
            for(int k  = 1 ; k < 4 ; ++k) {
                vbox = new VBox(30);
                vbox.setPadding(new Insets(20, 20, 20, 20));
                
                imageView = new ImageView(new Image(new FileInputStream("/image/" + k +".jpg")));
                imageView.setFitHeight(160); 
                imageView.setFitWidth(120); 
                vbox.getChildren().addAll(imageView, new Text("Wahn Goey Dteun"), new Text("ARTIST : GUNGUN"));
                vbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(vbox);
            }
            hbox.setAlignment(Pos.CENTER);
            totalbox.getChildren().addAll(hbox);
        }
        
        totalbox.setAlignment(Pos.CENTER);
        scrollPane.setContent(totalbox);
    }
    
    public void setScrollPane() throws FileNotFoundException {
        
        ImageView imageView;
        
        VBox totalbox = new VBox(30);
        //totalbox.setPadding(new Insets(30, 30, 30, 30));
        
        for(int i = 0; i< 10; ++i) {
            hbox = new HBox(10);
            hbox.setPadding(new Insets(0, 30, 0, 30));
            
            for(int k  = 1 ; k < 4 ; ++k) {
                vbox = new VBox(30);
                vbox.setPadding(new Insets(20, 20, 20, 20));
                
                imageView = new ImageView(new Image(new FileInputStream("/image/" + k +".jpg")));
                imageView.setFitHeight(160); 
                imageView.setFitWidth(120); 
                vbox.getChildren().addAll(imageView, new Text("Wahn Goey Dteun"), new Text("ARTIST : GUNGUN"));
                vbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(vbox);
            }
            hbox.setAlignment(Pos.CENTER);
            totalbox.getChildren().addAll(hbox);
        }
        
        totalbox.setAlignment(Pos.CENTER);
        scrollPane.setContent(totalbox);
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

}
