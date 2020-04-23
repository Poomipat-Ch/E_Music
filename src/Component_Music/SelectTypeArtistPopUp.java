/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author HCARACH
 */
public class SelectTypeArtistPopUp extends SelectTypePopUp {

    public SelectTypeArtistPopUp() {
        super();
    }

    @Override
    AnchorPane CreateButton() {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.setPrefSize(400, 380);
        anchorpane.setLayoutX(50);
        anchorpane.setLayoutY(60);

        AnchorPane backgroundpane = new AnchorPane();
        backgroundpane.setPrefSize(400, 380);
        backgroundpane.getStyleClass().add("backgroundSelect");
        
        anchorpane.getChildren().addAll(backgroundpane);

        ArrayList<String> list = new ArrayList<>();

        list.add("International Artist");
        list.add("ศิลปินคนไทย");
        int i = 0;

        for (String string : list) {
            Button button = new Button(string);
            button.getStyleClass().add("buttonselect");
            button.setPrefSize(250, 100);
            button.setLayoutY((120 * i++) + 120);
            button.setLayoutX(200 - 125);

            button.setOnAction(clicked -> {
                if (string.equals("International Artist")) {
                    new UploadArtistPopUp("New Artist", "international");
                } else {
                    new UploadArtistPopUp("เพิ่มศิลปิน", "thai");
                }
                SelectTypePopUp.stage.close();
            });
            
            anchorpane.getChildren().addAll(button);
            
        }

        return anchorpane;
    }

    @Override
    AnchorPane CreateHeadLabel() {
        Label label = new Label("Please select artist's type\nโปรดเลือกประเภทของศิลปิน");
        label.getStyleClass().add("selectlabel");
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(500);
        label.setLayoutY(85);

        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getChildren().addAll( label);

        return anchorpane;
    }
    
}
