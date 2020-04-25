/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.UI;
import javafx.scene.control.Label;
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
        anchorpane.getStyleClass().add("mainBox");
        
        if(page.toLowerCase().equals("songs"))
            anchorpane.getChildren().addAll(createHead(page, foundtext), new ShowingSongs(foundtext).table);
        else if(page.toLowerCase().equals("artists"))
            anchorpane.getChildren().addAll(createHead(page, foundtext), new ShowingArtist(foundtext).getShowingartist());   
        
        UI.vbox.getChildren().remove(1);
        UI.vbox.getChildren().add(anchorpane);
    }
    
    private HBox createHead(String page, String foundtext) {
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
