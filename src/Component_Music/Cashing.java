/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.ReadWriteFile;
import UI_music.User_UI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Frontae
 */
public class Cashing {

    private Stage paymentStage;
    private Scene infoScene;
    private Song song;
    
    private Account userAccount;

    public void Info(Stage paymentStage, Song song, Account userAccount) {
        this.paymentStage = paymentStage;
        this.song = song;
        this.userAccount = userAccount;
        
        Label title = new Label("YOUR ORDER");
        Label noteText = new Label("Please check every detail before making purchase.");
        VBox topPane = new VBox(20);
        topPane.getChildren().addAll(title, noteText);
        topPane.setAlignment(Pos.CENTER_LEFT);
        BorderPane newTopPane = new BorderPane();
        newTopPane.setLeft(topPane);
        newTopPane.setRight(exitButton());

        Label orderText = new Label(song.getNameSong() + " - " + song.getArtistSong());
        Label orderPrice = new Label("฿ " + song.getPriceSong()); //<------------------------------------------- Wait for update
        BorderPane leftRow1 = new BorderPane();
        leftRow1.setLeft(orderText);
        leftRow1.setRight(orderPrice);
        leftRow1.setPadding(new Insets(20));
        leftRow1.setStyle("-fx-background-color:#ebebeb;");

        Label describeText = new Label("All prices include VAT if applicable.");
        Label totalPrice = new Label("ORDER TOTAL: ฿ " + song.getPriceSong()); //<------------------------------ And this
        BorderPane leftRow2 = new BorderPane();
        leftRow2.setLeft(describeText);
        leftRow2.setRight(totalPrice);
        leftRow2.setPadding(new Insets(20));
        leftRow2.setStyle("-fx-background-color:#ebebeb;");

        VBox leftPane = new VBox(20);
        leftPane.getChildren().addAll(leftRow1, leftRow2);
        leftPane.setAlignment(Pos.TOP_LEFT);

        //=============================================================//
        Label title2 = new Label("YOUR PAYMENT");

        RadioButton creditRadio = new RadioButton("CREDIT/DEBIT CARD");
        creditRadio.setSelected(true);

        TextField ccNumber = new TextField();
        ccNumber.setPromptText("Card Number");
        ComboBox<String> month = new ComboBox<>();
        month.setPromptText("Expiry date");
        for (int i = 1; i <= 12; i++) {
            String[] mth = {"January", "Febuary", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};
            month.getItems().add(Integer.toString(i) + " - " + mth[i - 1]);
        }
        ComboBox<String> year = new ComboBox<>();
        year.setPromptText("Year");
        for (int i = 2020; i <= 2041; i++) {
            year.getItems().add(Integer.toString(i));
        }

        TextField ccvNumber = new TextField();
        ccvNumber.setPromptText("CVV");
        TextField ccName = new TextField();
        ccName.setPromptText("Name on the card");
        HBox row1 = new HBox(20);
        row1.getChildren().addAll(month, year, ccvNumber);

        VBox inputField = new VBox(20);
        inputField.getChildren().addAll(ccNumber, row1, ccName);
        inputField.setAlignment(Pos.CENTER_LEFT);

        Label confirmPrice = new Label("฿ " + song.getPriceSong());//<----------------------------- PRICE AGAIN
        Button payButton = new Button("PAY NOW");
        HBox row3 = new HBox(20);
        row3.getChildren().addAll(confirmPrice, payButton);
        row3.setAlignment(Pos.CENTER);
        VBox rightPane = new VBox(20);
        rightPane.getChildren().addAll(title2, creditRadio, inputField, row3);
        rightPane.setStyle("-fx-background-color:#ebebeb;");

        rightPane.setAlignment(Pos.TOP_LEFT);
        rightPane.setPadding(new Insets(10));

        payButton.setOnAction(e -> {
            //Check Blank vong clash
            if (ccNumber.getText().isBlank() || ccvNumber.getText().isBlank()
                    || ccName.getText().isBlank() || month.getValue() == null || year.getValue() == null) {
                AlertBox.displayAlert("Something went wrong!", "Some of infomation are missing.\n Please check again.");
            } else if (ccvNumber.getText().length() != 3 || !isInteger(ccvNumber.getText()) || ccNumber.getText().length() != 16) {
                AlertBox.displayAlert("Something went wrong!", "Some of infomation are Incorrect.\n Please check card no. / cvv again.");
            } else if (hasNumber(ccName.getText())) {
                AlertBox.displayAlert("Something went wrong!", "Some of infomation are Incorrect.\n Please check your name again.");
            } else {
                if (AlertBox.confirmAlert("Are you sure", "\"" + song.getNameSong() + " - " + song.getArtistSong() + "\" will cost " + song.getPriceSong() + "฿\n"
                        + "Please confirm to make a purchase.")) {
                    AlertBox.displayAlert("Purchase Success", "\"" + song.getNameSong() + " - " + song.getArtistSong() + "\" will add to your playlist soon.");
                    System.out.println("Purchase complete");
                    this.paymentStage.close();
                    this.userAccount.addSong(song);
                    this.userSaveSong();
                    //<------------------------------------------------------------ EVERYTHING CHECK PURCHASE COMPLETE Set song EVERYTHINGto the playlist pls thank you by font
                }
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(leftPane);
        borderPane.setRight(rightPane);
        borderPane.setTop(newTopPane);
        Insets insets = new Insets(10);
        BorderPane.setMargin(leftPane, insets);
        BorderPane.setMargin(rightPane, insets);
        BorderPane.setMargin(newTopPane, insets);
        borderPane.setStyle("-fx-background-color:#bbbbbb;");

        infoScene = new Scene(borderPane, 800, 600);
        paymentStage.setScene(infoScene);
        //paymentStage.setTitle("PAYMENT");
        paymentStage.show();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    
    public static boolean hasNumber(String s) {
        for (int i = 0; i < 10; i++) {
            if(s.contains(Integer.toString(i))) return true;
        }
        return false;
    }
    
    private Button exitButton(){
        
       Image exit_icon = new Image("/icon/close-512-detail.png");
       Image exit_hover_icon = new Image("/icon/close-512_hover.png");
       
       ImageView imageView = new ImageView(exit_icon);
       imageView.setFitHeight(20);
       imageView.setFitWidth(20);
       
       ImageView imageView_hover = new ImageView(exit_hover_icon);
       imageView_hover.setFitHeight(20);
       imageView_hover.setFitWidth(20);
       
       Button exit = new Button("",imageView);
       exit.setOnMouseEntered(e -> { 
           exit.setGraphic(imageView_hover);
       });
       exit.setOnMouseExited(e -> {
           exit.setGraphic(imageView);
       });
       exit.setOnMouseClicked(e -> {
           paymentStage.close();
       });
       exit.setStyle("-fx-background-color : transparent;");
       exit.setPadding(Insets.EMPTY);
       
       return exit;
    }
    
    ArrayList<Account> updateAccount = new ArrayList<>();
    File user = new File("src/data/user.dat");

    public void userSaveSong() {
        
        ReadWriteFile file = new ReadWriteFile();
        ArrayList<Account> nowAccount = null;
        
        try {
            nowAccount = file.readFile(user);
        } catch (IOException ex) {
            Logger.getLogger(User_UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Account account : nowAccount) {
            if(account.getUsername().equals(userAccount.getUsername()))
                updateAccount.add(userAccount);
            else
                updateAccount.add(account);
        }
        
        try {
            file.writeFile(user, updateAccount);
        } catch (IOException ex) {
            Logger.getLogger(User_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
