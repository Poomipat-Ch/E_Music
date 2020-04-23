package Component_Music;

import javafx.geometry.Insets;
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

    public static boolean display(String title, String message) {
        try {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setResizable(false);

            Label label1 = new Label();
            label1.setStyle("-fx-font-size : 18px");
            label1.setText(message);

            Button yesBtn = new Button("Yes");
            yesBtn.setStyle("-fx-font-size : 15px");
            yesBtn.setOnAction(e -> {
                ans = true;
                stage.close();
            });

            Button noBtn = new Button("No");
            noBtn.setStyle("-fx-font-size : 15px");
            noBtn.setOnAction(e -> {
                ans = false;
                stage.close();
            });

            HBox row1 = new HBox(20);
            row1.getChildren().addAll(yesBtn, noBtn);
            row1.setAlignment(Pos.CENTER);

            VBox layout1 = new VBox(20);
            layout1.setPadding(new Insets(15));
            layout1.getChildren().addAll(label1, row1);
            layout1.setAlignment(Pos.CENTER);
            layout1.setMinSize(200, 100);

            Scene scene = new Scene(layout1);
            stage.setOnCloseRequest(e -> {
                ans = false;
                stage.close();
            });
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("AlertBox : Exeption in display");
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
            label1.setStyle("-fx-font-size : 18px");
            label1.setText(message);

            Button okBtn = new Button("OK");
            okBtn.setStyle("-fx-font-size : 15px");
            okBtn.setOnAction(e -> {
                stage.close();
            });

            VBox layout1 = new VBox(20);
            layout1.setPadding(new Insets(15));
            layout1.getChildren().addAll(label1, okBtn);
            layout1.setAlignment(Pos.CENTER);
            layout1.setMinSize(200, 100);

            Scene scene = new Scene(layout1);

            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("AlertBox : Exeption in displayAlert");
        }
    }

    public static boolean confirmAlert(String title, String message) {
        try {
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setResizable(false);

            Label label1 = new Label();
            label1.setStyle("-fx-font-size : 18px");
            label1.setText(message);

            Button yesBtn = new Button("Confirm");
            yesBtn.setStyle("-fx-font-size : 15px");
            yesBtn.setOnAction(e -> {
                ans = true;
                stage.close();
            });

            Button noBtn = new Button("Cancel");
            noBtn.setStyle("-fx-font-size : 15px");
            noBtn.setOnAction(e -> {
                ans = false;
                stage.close();
            });

            HBox row1 = new HBox(20);
            row1.getChildren().addAll(yesBtn, noBtn);
            row1.setAlignment(Pos.CENTER);

            VBox layout1 = new VBox(20);
            layout1.setPadding(new Insets(15));
            layout1.getChildren().addAll(label1, row1);
            layout1.setAlignment(Pos.CENTER);
            layout1.setMinSize(350, 100);

            Scene scene = new Scene(layout1);

            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("AlertBox : Exeption in confirmAlert");
        }

        return ans;
    }

}
