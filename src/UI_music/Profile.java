/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.Cashing;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author HCARACH
 */
public class Profile {

    Button savebt = new Button("Save");
    Button cancelbt = new Button("Cancel");
    Button editbt = new Button("Edit");
    Button buyPremiumbtn = new Button("Upgrade Premium");
    Account userAccount;

    BorderPane accountPane = new BorderPane();

    public Profile(Account userAccount) {

        this.userAccount = userAccount;
        MyAccount myAccount = new MyAccount(this.userAccount);

        accountPane.setTranslateY(20);

        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(20, 20, 0, 0));
        bottom.setAlignment(Pos.CENTER_RIGHT);

        savebt.getStyleClass().add("savebtn");
        savebt.setOnAction(event -> {
            if (myAccount.saveAccount()) {
                accountPane.setTranslateY(20);
                AlertBox.displayAlert("Edit Profile", "Saved.");
                this.userAccount = myAccount.getMyAccount();
                myAccount.showAccount(this.userAccount);
                accountPane.setCenter(myAccount.getProfilePane());
                bottom.getChildren().clear();
                if (!"admin".equals(userAccount.getUserRole())) {
                    bottom.getChildren().addAll(buyPremiumbtn);
                }
                bottom.getChildren().addAll(editbt);
            } else {
                AlertBox.displayAlert("Edit Profile", "Failed.");
            }
        });
        cancelbt.getStyleClass().add("cancelbtn");
        cancelbt.setOnAction(event -> {
            accountPane.setTranslateY(20);
            myAccount.showAccount(userAccount);
            accountPane.setCenter(myAccount.getProfilePane());
            myAccount.Clear();
            bottom.getChildren().clear();
            if (!"admin".equals(userAccount.getUserRole())) {
                bottom.getChildren().addAll(buyPremiumbtn);
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
            Cashing cashPremium = new Cashing();
            cashPremium.buyPremium(new Stage(), userAccount);
        });
        if (!"admin".equals(userAccount.getUserRole())) {
            bottom.getChildren().addAll(buyPremiumbtn);
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
