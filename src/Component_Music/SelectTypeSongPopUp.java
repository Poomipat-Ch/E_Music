/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author HCARACH
 */
public class SelectTypeSongPopUp extends SelectTypePopUp {

    public SelectTypeSongPopUp() {
        super();
//        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
//        scene.getStylesheets().add(stylrSheet); // CSS
//
//        scene.setFill(Color.TRANSPARENT);
//        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.showAndWait();
    }

    @Override
    AnchorPane CreateHeadLabel() {

        Label label = new Label("Please select song's type /\n\tโปรดเลือกประเภทของเพลง");
        label.getStyleClass().add("selectlabel");
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(500);
        label.setLayoutY(85);

        Label headlabel = new Label("Upload Song / เพิ่มเพลง");
        headlabel.getStyleClass().add("headselectlabel");
        headlabel.setPadding(new Insets(5));
        headlabel.setLayoutX(5);
        headlabel.setLayoutY(5);

        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getChildren().addAll(headlabel, label);

        return anchorpane;
    }

    @Override
    AnchorPane CreateButton() {
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.setStyle("-fx-background-color: transparent;");
        anchorpane.setPrefSize(400, 380);
        anchorpane.setLayoutX(50);
        anchorpane.setLayoutY(60);

        AnchorPane backgroundpane = new AnchorPane();
        backgroundpane.setPrefSize(400, 380);
        backgroundpane.getStyleClass().add("backgroundSelect");
        
        anchorpane.getChildren().addAll(backgroundpane);

        ArrayList<String> list = new ArrayList<>();

        list.add("International Song");
        list.add("เพลงไทย");
        int i = 0;

        for (String string : list) {
            Button button = new Button(string);
            button.getStyleClass().add("buttonselect");
            button.setPrefSize(250, 100);
            button.setLayoutY((120 * i++) + 120);
            button.setLayoutX(200 - 125);

            button.setOnAction(clicked -> {
                if (string.equals("International Song")) {
                    new UploadSongPopUp("Upload Song");
                } else {
                    new UploadSongPopUp("เพิ่มเพลง");
                }
            });
            
            anchorpane.getChildren().addAll(button);
            
        }

        return anchorpane;
    }

}