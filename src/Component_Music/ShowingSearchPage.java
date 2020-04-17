/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.UI;
import UI_music.User_UI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author HCARACH
 */
public class ShowingSearchPage {

    public ShowingSearchPage(String page, String foundtext) {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.setMinSize(1030, 901);
//        anchorpane.setLayoutX(-3);
//        anchorpane.setLayoutY(-3);
        anchorpane.getStyleClass().add("mainBox");
        
//        AnchorPane background = new AnchorPane();
//        background.getStyleClass().add("backgroundsearch");
//        background.setPrefSize(1030, 901);
//        background.setPadding(Insets.EMPTY);
//        background.setLayoutX(-1);
//        background.setLayoutY(1);
    
//        HBox hBox = new HBox();
//        hBox.setPrefSize(1030, 30);
//        hBox.setAlignment(Pos.CENTER);
//        hBox.setLayoutY(100);

//        TextField searchTextField = new TextField();
//        searchTextField.setPromptText("Search");
//        searchTextField.setStyle("-fx-font-size: 18px;");
//        searchTextField.setPrefSize(1030-300-60-70, 30);
//        
//        searchTextField.textProperty().addListener((ov, t, t1) -> {
//            User_UI.totalPane.getChildren().remove(1);
//            //User_UI.totalPane.getChildren().add(updateScrollPane(searchTextField.getText()));
//        });
//        
//        hBox.getChildren().addAll(searchTextField);
        
        if(page.toLowerCase().equals("songs"))
            anchorpane.getChildren().addAll(CreateHead(page, foundtext), new ShowingSongs().table);
        else if(page.toLowerCase().equals("artists"))
            anchorpane.getChildren().addAll(CreateHead(page, foundtext));
        else
            anchorpane.getChildren().addAll(CreateHead(page, foundtext));          
        
        UI.vbox.getChildren().remove(1);
        UI.vbox.getChildren().add(anchorpane);
    }
    
    private HBox CreateHead(String page, String foundtext) {
        HBox hbox = new HBox();
        hbox.setLayoutX(50);
        hbox.setLayoutY(100);
        
        Label label = new Label("Showing " + page.toLowerCase() + " for ");
        label.getStyleClass().add("showing");
        
        Label found = new Label(foundtext);
        found.getStyleClass().add("foundtext");
        
        
        hbox.getChildren().addAll(label, found);
        
        
        return hbox;
    }
    
    
    
}
