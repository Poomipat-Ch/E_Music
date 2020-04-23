/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.ReadWriteFile;
import UI_music.UI;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Frontae
 */
public class Cashing {

    private Stage paymentStage;
    private Scene infoScene;
    private Song song;

    private File user = new File("src/data/user.dat");
    private File priceFile = new File("src/data/price.dat");

    private ArrayList<Account> listAccount = new ArrayList<>();
    private ArrayList<Account> addAccount = new ArrayList<>();

    private ReadWriteFile file = new ReadWriteFile();

    private double mouse_x = 0, mouse_y = 0; // position mouse

    private double lowPrice, medPrice, largePrice, price, promotion, total; //<--

        
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    ///// Format From String -> TwoDecimal
    private double twoDF(double num){    // Double Num -> Double Num 
        DecimalFormat twoDF = new DecimalFormat("#.00");
        Double doubleNum = stringToDouble(twoDF.format(num));
        return doubleNum;
    }
    
    private double stringToDouble(String string){  // String -> Double 
        return Double.parseDouble(string);
    }
    
    private double stringToTwoDecimal(String string){
        return twoDF(stringToDouble(string));
    }
    ///// End 
    
    //Promotion Function
    private double totalPercentPromotion(){
        return (100.0-getPromotion())/100.0;
    }

    public void Info(Stage paymentStage, Song song) {
        this.paymentStage = paymentStage;
        this.song = song;

        PricePremium pricePremium = new PricePremium();
        try {
            pricePremium = ReadWriteFile.readPricePremium(priceFile);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        
        //double lowPrice, medPrice, largePrice; // <-------------- Song Price ------------------------
        setLowPrice(stringToTwoDecimal(song.getPriceSong())); //<-- //May Error if it get REAL String !!!
        setMedPrice(twoDF(twoDF(getLowPrice()*0.90)*3)); //<--
        setLargePrice(twoDF(twoDF(getLowPrice()*0.80)*5)); //<--

        setPromotion(pricePremium.getDiscountPercentInt());

        /*Integer.parseInt(song.getPriceSong()) <- Old one | New one ->*/
        setPrice(getLowPrice()); // <-------------------- Total Price will be calculate below --------------------------
        if(UI.userAccount.getUserRole().equals("premium")) setTotal(twoDF(getPrice()*totalPercentPromotion()));
        else setTotal(price);

        Label title = new Label("YOUR ORDER");
        Label noteText = new Label("Please check every detail before making purchase.");
        VBox topPane = new VBox(20);
        topPane.getChildren().addAll(title, noteText);
        topPane.setAlignment(Pos.CENTER_LEFT);
        BorderPane newTopPane = new BorderPane();
        newTopPane.setLeft(topPane);
        newTopPane.setRight(exitButton());

        Label orderText = new Label(song.getNameSong() + " - " + song.getArtistSong());
        Label orderPrice = new Label("฿ " + getPrice());
        Label toggleText = new Label("Number of download");
        ToggleGroup songPackage = new ToggleGroup();
        RadioButton lowPack = new RadioButton("1 time");
        RadioButton medPack = new RadioButton("3 times");
        RadioButton largePack = new RadioButton("5 times");
        lowPack.setToggleGroup(songPackage);
        songPackage.selectToggle(lowPack);
        medPack.setToggleGroup(songPackage);
        largePack.setToggleGroup(songPackage);
     
        Label colon1 = new Label(": ");
        
        HBox priceRow = new HBox(10);
        priceRow.setAlignment(Pos.CENTER);
        priceRow.getChildren().addAll(toggleText, lowPack, medPack, largePack, colon1);
        
        Label save10 = new Label("Save 10 %");
        Label save20 = new Label("Save 20 %");
        save10.getStyleClass().add("detailPremiumYellow");
        save20.getStyleClass().add("detailPremiumYellow");
        HBox saveHBox = new HBox(15);
        saveHBox.getChildren().addAll(save10,save20);
        saveHBox.setTranslateX(280);
        
        BorderPane leftRow1 = new BorderPane();
        leftRow1.setTop(orderText);
        BorderPane.setMargin(orderText, new Insets(0, 0, 20, 0));
        leftRow1.setLeft(priceRow);
        leftRow1.setRight(orderPrice);
        leftRow1.setBottom(saveHBox);
        leftRow1.setPadding(new Insets(20));
        
        Label describeText = new Label("All prices include VAT if applicable.");
        Label totalPrice = new Label("ORDER TOTAL : ฿ " + getTotal()); //<--
        BorderPane leftRow2 = new BorderPane();
        if(UI.userAccount.getUserRole().equals("premium")) {
            describeText.setText("Discounts " + getPromotion() + "% for Premium users");
        }
        leftRow2.setLeft(describeText);
        leftRow2.setRight(totalPrice);
        leftRow2.setPadding(new Insets(20));
        
        toggleText.getStyleClass().add("detailPremium");
        lowPack.getStyleClass().add("detailPremiumChoice");
        medPack.getStyleClass().add("detailPremiumChoice");
        largePack.getStyleClass().add("detailPremiumChoice");
        colon1.getStyleClass().add("detailPremium");
        
        title.getStyleClass().add("titlePremium");
        noteText.getStyleClass().add("detailPremium");
        noteText.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.5) , 5,0,2,2);");
        orderText.getStyleClass().add("detailPremium");
        orderPrice.getStyleClass().add("detailPremiumSmall");
        leftRow1.getStyleClass().add("subPane");
        describeText.getStyleClass().add("detailPremiumSmall");
        totalPrice.getStyleClass().add("detailPremiumSmall");
        leftRow2.getStyleClass().add("subPane");

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

        Label confirmPrice = new Label("฿ " + getTotal());
        
        //Gu so sleepy deposit here pap by Font
        songPackage.selectedToggleProperty().addListener( //Change text after select by using listener
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle,
                        Toggle new_toggle) -> {
                    if (songPackage.getSelectedToggle() != null) {
                        if (lowPack.isSelected()) {
                            orderPrice.setText("฿ " + getLowPrice());
                            setPrice(getLowPrice()); //<--
                        } else if (medPack.isSelected()) {
                            orderPrice.setText("฿ " + getMedPrice());
                            setPrice(getMedPrice()); //<--
                        } else {
                            orderPrice.setText("฿ " + getLargePrice());
                            setPrice(getLargePrice()); //<--
                        }
                        setTotal(price);
                        if (UI.userAccount.getUserRole().equals("premium")) {
                            describeText.setText("Discounts " + getPromotion() + "% for Premium users");
                            setTotal(twoDF(getTotal()*totalPercentPromotion())); //<- promotion price price = price * ( 100 - discount ) / 100
                        }
                        totalPrice.setText("ORDER TOTAL : ฿ " + getTotal());
                        confirmPrice.setText("฿ " + getTotal());
                    }
                });

        Button payButton = new Button("PAY NOW");
        HBox row3 = new HBox(20);
        row3.getChildren().addAll(confirmPrice, payButton);
        row3.setAlignment(Pos.CENTER);
                       
        title2.getStyleClass().add("detailPremium");
        creditRadio.getStyleClass().add("detailPremiumChoice");
        ccNumber.getStyleClass().add("detailPremiumTextFill");
        month.getStyleClass().add("detailPremiumChoice");
        year.getStyleClass().add("detailPremiumChoice");
        ccvNumber.getStyleClass().add("detailPremiumTextFill");
        ccName.getStyleClass().add("detailPremiumTextFill");
        confirmPrice.getStyleClass().add("detailPremiumSmall");
        payButton.getStyleClass().add("savebtn");
                       
        VBox rightPane = new VBox(20);
        rightPane.getStyleClass().add("subPane");
        rightPane.getChildren().addAll(title2, creditRadio, inputField, row3);

        rightPane.setAlignment(Pos.TOP_LEFT);
        rightPane.setPadding(new Insets(10));

        payButton.setOnAction(e -> {
            
            //Downtime New Feature
            int downloadTime;
            
            if(lowPack.isSelected())downloadTime = 1;
            else if(medPack.isSelected())downloadTime = 3;
            else downloadTime = 5;
            
            if (ccNumber.getText().isBlank() || ccvNumber.getText().isBlank()
                    || ccName.getText().isBlank() || month.getValue() == null || year.getValue() == null) {
                AlertBox.displayAlert("Something went wrong!", "Some of infomation are missing.\n Please check again.");
            } else if (ccvNumber.getText().length() != 3 || !isInteger(ccvNumber.getText()) || ccNumber.getText().length() != 16) {
                AlertBox.displayAlert("Something went wrong!", "Some of infomation are Incorrect.\n Please check card no. / cvv again.");
            } else if (hasNumber(ccName.getText())) {
                AlertBox.displayAlert("Something went wrong!", "Some of infomation are Incorrect.\n Please check your name again.");
            } else {
                if (AlertBox.confirmAlert("Are you sure", "You are going to buy \"" + song.getNameSong() + " - " + song.getArtistSong() + "\"."
                        + "\nPlease confirm to make a purchase.")) {
                    AlertBox.displayAlert("Purchase Success", "\"" + song.getNameSong() + " - " + song.getArtistSong() + "\" was added to your playlist.");
                    System.out.println("Purchase complete");
                    this.paymentStage.close();
                    
                    ArrayList<Song> songList = new ArrayList<Song>();
                    song.isDownload();
                    try {
                        for (Song song1: Song.getMyMusicList()) {
                            if (song1.getNameSong().equals(song.getNameSong())) 
                            {
                                song1.isDownload();
                            }
                            songList.add(song1);
                        }
                        ReadWriteFile.writeFileSong(new File("src/data/Music.dat"), songList);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(Cashing.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    UI.userAccount.addSong(song,downloadTime);
                    this.userSaveSong();
                    //<------------------------------------------------------------ EVERYTHING CHECK PURCHASE COMPLETE Set song EVERYTHINGto the playlist pls thank you by font
                }
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("allPanePremium");
        borderPane.setCenter(leftPane);
        borderPane.setBottom(rightPane);
        borderPane.setTop(newTopPane);
        borderPane.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        borderPane.setOnMouseDragged(e -> {
            paymentStage.setX(e.getScreenX() - mouse_x);
            paymentStage.setY(e.getScreenY() - mouse_y);
        });
        
        Insets insets = new Insets(45);
        BorderPane.setMargin(leftPane, insets);
        BorderPane.setMargin(rightPane, new Insets(10, 45, 20, 45)); //FYI : Insets(top,right,bottom,left)
        BorderPane.setMargin(newTopPane, insets);

        infoScene = new Scene(borderPane); //, 600, 525

        infoScene.setFill(Color.TRANSPARENT);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        infoScene.getStylesheets().add(stylrSheet); // CSS
        paymentStage.setScene(infoScene);
        paymentStage.initStyle(StageStyle.TRANSPARENT);
        //paymentStage.setTitle("PAYMENT");
        paymentStage.showAndWait();

    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(double medPrice) {
        this.medPrice = medPrice;
    }

    public double getLargePrice() {
        return largePrice;
    }

    public void setLargePrice(double largePrice) {
        this.largePrice = largePrice;
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
            if (s.contains(Integer.toString(i))) {
                return true;
            }
        }
        return false;
    }

    private Button exitButton() {

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
            paymentStage.close();
        });
        exit.setStyle("-fx-background-color : transparent;");
        exit.setPadding(Insets.EMPTY);

        return exit;
    }

    private ArrayList<Account> updateAccount = new ArrayList<>();

    public void userSaveSong() {

        ReadWriteFile file = new ReadWriteFile();
        ArrayList<Account> nowAccount = null;

        try {
            nowAccount = file.readFile(user);
        } catch (IOException ex) {
            System.out.println("MusicFunc : IOExeption read file music in userSaveSong");
        } catch (ClassNotFoundException ex) {
            System.out.println("MusicFunc : ClassNotFoundExeption read file music in userSaveSong");
        }

        for (Account account : nowAccount) {
            if (account.getUsername().equals(UI.userAccount.getUsername())) {
                updateAccount.add(UI.userAccount);
            } else {
                updateAccount.add(account);
            }
        }

        try {
            file.writeFile(user, updateAccount);
        } catch (IOException ex) {
            System.out.println("Cashing : IOExeption write file music in userSaveSong");
        }
    }

    private ImageView photo;
    private ArrayList<Account> oldAccounts;
    private ArrayList<Account> presentAccounts;
    private Account updateAccount2;
    private Account userAccount;

    public void buyPremium(Stage paymentStage, Account userAccount) { //copy from Info and UpdateAccountClicked in Admin_UI
        //Payment
        this.paymentStage = paymentStage;
        this.userAccount = userAccount;

        Label title = new Label("SIGN UP PREMIUM");
        title.getStyleClass().add("titlePremium");
        Label noteText = new Label("Please check every detail before making purchase.");
        noteText.getStyleClass().add("detailPremium");
        noteText.setStyle("-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.5) , 5,0,2,2);");
        VBox topPane = new VBox(20);
        topPane.getChildren().addAll(title, noteText);
        topPane.setAlignment(Pos.CENTER_LEFT);
        BorderPane newTopPane = new BorderPane();
        newTopPane.setLeft(topPane);
        newTopPane.setRight(exitButton());

        Label orderText = new Label("Premium Account");
        orderText.getStyleClass().add("detailPremium");
        Label orderPrice = new Label(": ฿ 199"); //<--------------------
        orderPrice.getStyleClass().add("detailPremiumSmall");
        BorderPane leftRow1 = new BorderPane();
        leftRow1.setLeft(orderText);
        leftRow1.setRight(orderPrice);
        leftRow1.setPadding(new Insets(20));
        leftRow1.getStyleClass().add("subPane");

        Label describeText = new Label("All prices include VAT if applicable. ");
        Label totalPrice = new Label("      ORDER TOTAL : ฿ 199"); //<-----------
        describeText.getStyleClass().add("detailPremiumSmall");
        totalPrice.getStyleClass().add("detailPremiumSmall");
        BorderPane leftRow2 = new BorderPane();
        leftRow2.setLeft(describeText);
        leftRow2.setRight(totalPrice);
        leftRow2.setPadding(new Insets(20));
        leftRow2.getStyleClass().add("subPane");

        VBox leftPane = new VBox(20);
        leftPane.getChildren().addAll(leftRow1, leftRow2);
        leftPane.setAlignment(Pos.TOP_LEFT);

        //=============================================================//
        Label title2 = new Label("YOUR PAYMENT");
        title2.getStyleClass().add("detailPremium");

        RadioButton creditRadio = new RadioButton("CREDIT/DEBIT CARD");
        creditRadio.getStyleClass().add("detailPremiumChoice");
        creditRadio.setSelected(true);

        TextField ccNumber = new TextField();
        ccNumber.getStyleClass().add("detailPremiumTextFill");
        ccNumber.setPromptText("Card Number");
        ComboBox<String> month = new ComboBox<>();
        month.getStyleClass().add("detailPremiumChoice");
        month.setPromptText("Expiry date");
        String[] mth = {"January", "Febuary", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 1; i <= 12; i++) {
            month.getItems().add(Integer.toString(i) + " - " + mth[i - 1]);
        }
        ComboBox<String> year = new ComboBox<>();
        year.getStyleClass().add("detailPremiumChoice");
        year.setPromptText("Year");
        for (int i = 2020; i <= 2041; i++) {
            year.getItems().add(Integer.toString(i));
        }

        TextField ccvNumber = new TextField();
        ccvNumber.getStyleClass().add("detailPremiumTextFill");
        ccvNumber.setPromptText("CVV");
        TextField ccName = new TextField();
        ccName.getStyleClass().add("detailPremiumTextFill");
        ccName.setPromptText("Name on the card");
        HBox row1 = new HBox(20);
        row1.getChildren().addAll(month, year, ccvNumber);

        VBox inputField = new VBox(20);
        inputField.getChildren().addAll(ccNumber, row1, ccName);
        inputField.setAlignment(Pos.CENTER_LEFT);

        setPromotion(0); //<-
        setPrice(199.00);  //<-
        double total = getPrice(); //<-
        total = total * (100.0 - getPromotion()) / 100.0; //<- promotion price price = price * ( 100 - discount ) / 100
        Label confirmPrice = new Label("฿ " + total);
        confirmPrice.getStyleClass().add("detailPremiumSmall");

        Button payButton = new Button("PAY NOW");
        payButton.getStyleClass().add("savebtn");
        HBox row3 = new HBox(20);
        row3.getChildren().addAll(confirmPrice, payButton);
        row3.setAlignment(Pos.CENTER);
        VBox rightPane = new VBox(20);
        rightPane.getChildren().addAll(title2, creditRadio, inputField, row3);
        rightPane.getStyleClass().add("subPane");

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
            } else { //<--------------------------------------------------------------------
                if (AlertBox.confirmAlert("Confirmation", "You are going to buy \"Premium account\" for ฿199\n"
                        + "Please confirm to make a purchase.")) {
                    //Change status (drag from admin ui edit status)

                    oldAccounts = new ArrayList<>();
                    presentAccounts = new ArrayList<>();
                    updateAccount2 = new Account();

                    try {
                        oldAccounts = file.readFile(user);
                    } catch (IOException ex) {
                        System.out.println("buyPremium : IOExeption readfile in updateAccountClick");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("buyPremium : ClassNotFoundExeption readfile in upDateAccountClick");
                    }

                    for (Account account : oldAccounts) {
                        String chkUser = account.getUsername();
                        String chkEmail = account.getEmail();

                        if (userAccount.getUsername().equals(chkUser) && userAccount.getEmail().equals(chkEmail)) {
                            updateAccount2 = account;
                            //SKIP TO ADD AFTER
                        } else {
                            presentAccounts.add(account);
                        }
                    }
                    updateAccount2.setUserRole("premium");

                    presentAccounts.add(updateAccount2);

                    try {
                        file.writeFile(user, presentAccounts);
                    } catch (IOException ex) {
                        System.out.println("buyPremium : IOExeption writefile in updateAccountClicked");
                    }

                    System.out.println("Purchase complete");
                    this.paymentStage.close();
//                    this.userAccount.addSong(song);
//                    this.userSaveSong();
                    UI.userAccount = updateAccount2;
                }
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(leftPane);
        borderPane.setBottom(rightPane);
        borderPane.setTop(newTopPane);
        Insets insets = new Insets(55);
        BorderPane.setMargin(leftPane, insets);
        BorderPane.setMargin(rightPane, new Insets(10, 55, 20, 55)); //FYI : Insets(top,right,bottom,left)
        BorderPane.setMargin(newTopPane, insets);
        //borderPane.setStyle("-fx-background-color:#bbbbbb;");
        borderPane.getStyleClass().add("allPanePremium");
        borderPane.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        borderPane.setOnMouseDragged(e -> {
            paymentStage.setX(e.getScreenX() - mouse_x);
            paymentStage.setY(e.getScreenY() - mouse_y);
        });

        infoScene = new Scene(borderPane);//, 600, 525
        infoScene.setFill(Color.TRANSPARENT);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        infoScene.getStylesheets().add(stylrSheet); // CSS
        paymentStage.setScene(infoScene);
        paymentStage.initModality(Modality.APPLICATION_MODAL);
        paymentStage.initStyle(StageStyle.TRANSPARENT);
        //paymentStage.setTitle("PAYMENT");
        paymentStage.showAndWait();

    }
    
    
    public void cancelPremium(Account userAccount){
        
         if (AlertBox.confirmAlert("Confirmation","    Are you sure you want to\n cancel your Premium Account?")) {
                    //Change status (drag from admin ui edit status)

                    oldAccounts = new ArrayList<>();
                    presentAccounts = new ArrayList<>();
                    updateAccount2 = new Account();

                    try {
                        oldAccounts = file.readFile(user);
                    } catch (IOException ex) {
                        System.out.println("buyPremium : IOExeption readfile in updateAccountClick");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("buyPremium : ClassNotFoundExeption readfile in upDateAccountClick");
                    }

                    for (Account account : oldAccounts) {
                        String chkUser = account.getUsername();
                        String chkEmail = account.getEmail();

                        if (userAccount.getUsername().equals(chkUser) && userAccount.getEmail().equals(chkEmail)) {
                            updateAccount2 = account;
                            //SKIP TO ADD AFTER
                        } else {
                            presentAccounts.add(account);
                        }
                    }
                    updateAccount2.setUserRole("member");

                    presentAccounts.add(updateAccount2);

                    try {
                        file.writeFile(user, presentAccounts);
                    } catch (IOException ex) {
                        System.out.println("buyPremium : IOExeption writefile in updateAccountClicked");
                    }

                    System.out.println("Cancel Premium");
                   
                    UI.userAccount = updateAccount2; // Update
                }
    }

}
