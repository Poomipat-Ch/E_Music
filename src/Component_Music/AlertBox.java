package Component_Music;

import UI_music.Login;
import UI_music.User_UI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Frontae
 */
public class AlertBox {
    static boolean ans;
    public static boolean display(String title, String message){
        try{
        Stage stage = new Stage();
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);
        
        Label label1 = new Label();
        label1.setText(message);
        
        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(e -> {
            ans = true;
            stage.close();
                });
        
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            ans = false;
            stage.close();
                });
        
        HBox row1 = new HBox(20);
        row1.getChildren().addAll(yesBtn,noBtn);
        row1.setAlignment(Pos.CENTER);
        
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, row1);
        layout1.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout1,200,100);
        
        stage.setScene(scene);
        stage.showAndWait();
    }catch(Exception e){
            System.out.println(e);
    }
        
        return ans;
    }
    public static void displayAlert(String title, String message) {
        try {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setResizable(false);

            Label label1 = new Label();
            label1.setText(message);

            Button okBtn = new Button("OK");
            okBtn.setOnAction(e -> {
                stage.close();
            });

            VBox layout1 = new VBox(20);
            layout1.getChildren().addAll(label1, okBtn);
            layout1.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout1, 200, 100);

            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}