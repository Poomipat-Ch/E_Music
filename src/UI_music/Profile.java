/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.AlertBox;
import Component_Music.Cashing;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author HCARACH
 */
public class Profile {

    private Button savebt = new Button("Save");
    private Button cancelbt = new Button("Cancel");
    private Button editbt = new Button("Edit");
    private Button buyPremiumbtn = new Button("Upgrade Premium");
    private Button cancelPremiumbtn = new Button("Cancel Premium");

    private BorderPane accountPane = new BorderPane();

    public Profile() {

        MyAccount myAccount = new MyAccount();

        accountPane.setTranslateY(20);

        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(20, 20, 0, 0));
        bottom.setAlignment(Pos.CENTER_RIGHT);

        savebt.getStyleClass().add("savebtn");
        savebt.setOnAction(event -> {
            if (myAccount.saveAccount()) {
                accountPane.setTranslateY(20);
                AlertBox.displayAlert("Edit Profile", "Saved.");
//                UI.userAccount = myAccount.getMyAccount();
                myAccount.showAccount();
                accountPane.setCenter(myAccount.getProfilePane());
                bottom.getChildren().clear();
                if (!"admin".equals(UI.userAccount.getUserRole()) && !"premium".equals(UI.userAccount.getUserRole())) {
                    bottom.getChildren().addAll(buyPremiumbtn);
                }
                if ("premium".equals(UI.userAccount.getUserRole())) {
                    bottom.getChildren().addAll(cancelPremiumbtn);
                }
                bottom.getChildren().addAll(editbt);
            } else {
                AlertBox.displayAlert("Edit Profile", "Failed.");
            }
        });
        cancelbt.getStyleClass().add("cancelbtn");
        cancelbt.setOnAction(event -> {
            accountPane.setTranslateY(20);
            myAccount.showAccount();
            accountPane.setCenter(myAccount.getProfilePane());
            myAccount.clear();
            bottom.getChildren().clear();
            if (!"admin".equals(UI.userAccount.getUserRole()) && !"premium".equals(UI.userAccount.getUserRole())) {
                bottom.getChildren().addAll(buyPremiumbtn);
            }
            if ("premium".equals(UI.userAccount.getUserRole())) {
                bottom.getChildren().addAll(cancelPremiumbtn);
            }
            bottom.getChildren().addAll(editbt);
        });

        VBox right = new VBox(10);
        right.setPadding(new Insets(20, 20, 20, 20));
        editbt.getStyleClass().add("detailbtn");
        editbt.setOnAction(event -> {
            accountPane.setTranslateY(10);
            myAccount.editAccount();
            accountPane.setCenter(myAccount.getProfilePane());
            bottom.getChildren().clear();
            bottom.getChildren().addAll(savebt, cancelbt);
        });

        buyPremiumbtn.getStyleClass().add("premiumbtn");
        buyPremiumbtn.setOnMouseClicked(e -> {
            // Font dono << ------------
            System.out.println("NEW CASHING");
            Cashing cashPremium = new Cashing();
            cashPremium.buyPremium(new Stage(), UI.userAccount);

            if ("premium".equals(UI.userAccount.getUserRole())) {
                bottom.getChildren().clear();
                bottom.getChildren().addAll(cancelPremiumbtn, editbt);
                UI.titlepane.getChildren().remove(3);
                UI.titlepane.getChildren().add(UI.premium);

                UI.vbox.getChildren().remove(0);
                UI.vbox.getChildren().add(0, UI.titlepane);
            }

//            Label premium = new Label("PREMIUM");
//            premium.setLayoutX(750);
//            premium.setLayoutY(12);
//            premium.setAlignment(Pos.CENTER);
//            premium.setPrefSize(150, 30);
//            premium.getStyleClass().add("showpremium");



        });

        cancelPremiumbtn.getStyleClass().add("cancelbtn");
        cancelPremiumbtn.setOnMouseClicked(e -> {
            Cashing cancelPremium = new Cashing();
            cancelPremium.cancelPremium(UI.userAccount);

            if (!"admin".equals(UI.userAccount.getUserRole()) && !"premium".equals(UI.userAccount.getUserRole())) {
                bottom.getChildren().clear();
                bottom.getChildren().addAll(buyPremiumbtn, editbt);
                UI.titlepane.getChildren().remove(3);
                UI.titlepane.getChildren().add(UI.upgradepremium);

                UI.vbox.getChildren().remove(0);
                UI.vbox.getChildren().add(0, UI.titlepane);
            }

//            Label upgradepremium = new Label("UPGRADE PREMIUM");
//            upgradepremium.setLayoutX(650);
//            upgradepremium.setLayoutY(12);
//            upgradepremium.setAlignment(Pos.CENTER);
//            upgradepremium.setPrefSize(250, 30);
//            upgradepremium.getStyleClass().add("premiumbtn");



        });

        if (!"admin".equals(UI.userAccount.getUserRole()) && !"premium".equals(UI.userAccount.getUserRole())) {
            bottom.getChildren().addAll(buyPremiumbtn);
        }
        if ("premium".equals(UI.userAccount.getUserRole())) {
            bottom.getChildren().addAll(cancelPremiumbtn);
        }
        bottom.getChildren().addAll(editbt);

        //accountPane.setTop(head);
        accountPane.setCenter(myAccount.getProfilePane());
        accountPane.setPadding(new Insets(50, 50, 50, 50));
        accountPane.setBottom(bottom);
        accountPane.getStyleClass().add("outerPane");

    }

    public BorderPane getMainPane() {
        return accountPane;
    }

}
