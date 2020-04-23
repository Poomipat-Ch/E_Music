/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.AlertBox;
import Component_Music.PricePremium;
import java.io.File;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sirawit
 */
public class SetupPricePremium {
    
    
    private File priceFile = new File("src/data/price.dat");
    private PricePremium pricePremium = new PricePremium();
    private Stage stage;
    private TextField setUpPriceTextFill;
    
    private double mouse_x = 0, mouse_y = 0; // position mouse

    public SetupPricePremium() {
    
        stage = new Stage();
        
        setUpPriceTextFill = new TextField();
        setUpPriceTextFill.setPromptText("Default is 25");
        setUpPriceTextFill.setMaxWidth(300);
        setUpPriceTextFill.getStyleClass().add("detailPremiumTextFill"); //CSS
        
        try {
            pricePremium = ReadWriteFile.readPricePremium(priceFile);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: Can't Read pricePremium File");
            System.out.println(ex);
        }
        
        
        Button saveBtn= new Button("Save");
        saveBtn.getStyleClass().add("savebtn"); //CSS
        saveBtn.setOnAction(e->{
            
            int priceCorrect = 0;
            boolean priceCorrectBoolean = false;
            
            try {
                priceCorrect = Integer.parseInt(setUpPriceTextFill.getText());
                priceCorrectBoolean = true;
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                priceCorrectBoolean = false;
            }
               
            if (priceCorrect >= 0 && priceCorrectBoolean){
                
                if(AlertBox.display("Confirmation", "Are you sure you want to change\nDiscount Premium to "+ priceCorrect+"% ?")){
                    pricePremium.setDiscountPercentInt(priceCorrect);
                    try {
                        ReadWriteFile.writePricePremium(priceFile, pricePremium);
                        System.out.println("Discount change to "+priceCorrect+" And Write to File! :)");
                        
                    } catch (IOException ex) {
                        System.out.println("Error: Can't Write pricePremium File");
                        System.out.println(ex);
                    }
                    stage.close();
                }
            }
            else{
                AlertBox.display("Confirmation", "Setup Discount Premium Fail!\nPlease Try again");
            }
            
            
        });
        
        Button cancelBtn= new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn"); //CSS
        cancelBtn.setOnAction(e->{
            stage.close();
        });

        
        Label showPrice = new Label("Now, in Premium Account is : "+pricePremium.getDiscountPercentInt()+"%");
        Label showText = new Label("Set up your new Discount Percent!");
        showPrice.getStyleClass().add("detailPremiumPrice");
        showText.getStyleClass().add("detailPremiumPrice");
        
        
        HBox saveAndCancelHBox = new HBox(20,saveBtn,cancelBtn);
        saveAndCancelHBox.setAlignment(Pos.CENTER);
        
        VBox totalVBox = new VBox(20);
        totalVBox.setAlignment(Pos.CENTER);
        totalVBox.getChildren().addAll(exitButton(),showText, showPrice, setUpPriceTextFill, saveAndCancelHBox);
        totalVBox.getStyleClass().add("allPane"); //CSS
        totalVBox.setPadding(new Insets(5,5,30,5));
        totalVBox.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        totalVBox.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - mouse_x);
            stage.setY(e.getScreenY() - mouse_y);
        });
                
        Scene scene = new Scene(totalVBox,500,300);
        scene.setFill(Color.TRANSPARENT);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        scene.getStylesheets().add(stylrSheet); // CSS
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
        


        
    }
    
    private Button exitButton() {

        //Exit with Decoration
        Image exit_icon = new Image("/icon/close-512-detail.png");
        Image exit_hover_icon = new Image("/icon/close-512_hover.png");

        ImageView imageView = new ImageView(exit_icon);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        ImageView imageView_hover = new ImageView(exit_hover_icon);
        imageView_hover.setFitHeight(20);
        imageView_hover.setFitWidth(20);

        Button exit = new Button("", imageView);
        exit.setOnMouseEntered(e -> {
            exit.setGraphic(imageView_hover);
        });
        exit.setOnMouseExited(e -> {
            exit.setGraphic(imageView);
        });
        exit.setOnMouseClicked(e -> {
            stage.close();
        });
        exit.setStyle("-fx-background-color : transparent;"); //CSS
        exit.setTranslateX(200);
        exit.setTranslateY(-5);
        //exit.setPadding(Insets.EMPTY);

        return exit;
    }
    
    
    
   
}
