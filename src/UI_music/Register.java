/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.Cashing;
import Component_Music.ImageCircle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author HCARACH
 */
public class Register {

    private File user = new File("src/data/user.dat");

    private LocalDate dOB;
    private boolean dateSet = false;

    private FileChooser fileChooser;
    private File filePath;
    private ImageView photo;
    private Image image;
    private String roles = "member";

    private ArrayList<Account> listUserAccount = new ArrayList<>();
    private Account userAccount = new Account();
    private ArrayList<Account> addAccount = new ArrayList<>();

    private ReadWriteFile file = new ReadWriteFile();
    
    private Stage regisStage;
    
    private double mouse_x = 0, mouse_y = 0;
    
    private AnchorPane profilePicture = new AnchorPane();

    public Register(String userRole) {

        image = new Image("/image/defaultprofile.png");

        regisStage = new Stage();
        regisStage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Create New Account");
        title.getStyleClass().add("title");
        title.setPadding(new Insets(10));
        title.setAlignment(Pos.TOP_CENTER);

        //Admin add account
        ToggleGroup statusToggle = new ToggleGroup();
        RadioButton adminSelect = new RadioButton("Admin");
        RadioButton premiumSelect = new RadioButton("Premium");
        RadioButton userSelect = new RadioButton("Member");

        //Fill name surname username email password
        TextField nameIn = new TextField();
        nameIn.setPromptText("Name");
        TextField surnameIn = new TextField();
        surnameIn.setPromptText("Surname");
        TextField usernameIn = new TextField();
        usernameIn.setPromptText("Username");
        TextField mailIn = new TextField();
        mailIn.setPromptText("Email e.g. Spookify@gmail.com");
        TextField passIn = new PasswordField();
        passIn.setPromptText("New Password");
        TextField cfPassIn = new PasswordField();
        cfPassIn.setPromptText("Confirm Password");
        
        nameIn.getStyleClass().add("detailUploadTextFill");
        surnameIn.getStyleClass().add("detailUploadTextFill");
        usernameIn.getStyleClass().add("detailUploadTextFill");
        mailIn.getStyleClass().add("detailUploadTextFill");
        passIn.getStyleClass().add("detailUploadTextFill");
        cfPassIn.getStyleClass().add("detailUploadTextFill");
                

        // create a date picker 
        DatePicker date = new DatePicker();
        // show week numbers 
        date.setShowWeekNumbers(false);
        // when datePicker is pressed 
        date.setOnAction(e -> {
            dOB = date.getValue();
            dateSet = true;
        });
        
        date.getStyleClass().add("detailUploadTextFill");

        //Select Gender
        Label sexText = new Label("Gender");
        ToggleGroup sexToggle = new ToggleGroup(); //create radio button group
        RadioButton male = new RadioButton("Male"); //create radio button
        RadioButton female = new RadioButton("Female");
        RadioButton otherRadio = new RadioButton("Not specify");
        male.setToggleGroup(sexToggle); //Set radio button group
        female.setToggleGroup(sexToggle);
        otherRadio.setToggleGroup(sexToggle);
        sexToggle.selectToggle(male); // Set default = male
        
        sexText.getStyleClass().add("detailRegist");
        male.getStyleClass().add("detailRegistChoice");
        female.getStyleClass().add("detailRegistChoice");
        otherRadio.getStyleClass().add("detailRegistChoice");

        //FORGOT QUESTION
        Label qText = new Label("Security Question");
        qText.getStyleClass().add("detailRegist");
        ComboBox<String> question = new ComboBox<>();
        question.getStyleClass().add("detailUploadTextFill");
        question.getItems().addAll(
                "What's your first school.",
                "Your favourite pet's name.",
                "Your father's name"
        );
        question.setPromptText("Select / write a question.");
        question.setEditable(true); //USER CAN WRITE THEIR OWN QUESTION
        //FORGOT ANSWER
        TextField answer = new TextField();
        answer.getStyleClass().add("detailUploadTextFill");
        answer.setPromptText("Answer");

        CheckBox premiumBox = new CheckBox("I want to have a premium account");
        premiumBox.getStyleClass().add("detailRegist");

        Button saveBtn = new Button("Save");
        saveBtn.getStyleClass().add("savebtn");
        saveBtn.setOnAction(e -> {
            System.out.println("Checking information...");

            String userGender = "";

            //Check sex radioButton which is selected
            if (male.isSelected()) {
                userGender = "Male";
            } else if (female.isSelected()) {
                userGender = "Female";
            } else {
                userGender = "N/A";
            }
            ArrayList<Account> addAccount = new ArrayList<>();
//            if (premiumBox.isSelected()) { <---------------------------------------
//                roles = "premium";
//            }

            try {
                listUserAccount = file.readFile(this.user);
            } catch (Exception ex) {
                System.out.println("Register : IOExeption read file in Register consturtor before check");
            }
            boolean uniqueID = true;
            //Check already username / email
            for (Account account : listUserAccount) {
                String userId = usernameIn.getText(), emailID = mailIn.getText();
                String chkUser = account.getUsername(), chkEmail = account.getEmail();
                if ((userId.equals(chkUser) || emailID.equals(chkEmail))) {
                    uniqueID = false;
                    break;
                }
            }
            if (uniqueID == false) {
                AlertBox.displayAlert("Something went wrong!", "Email / username is already exists.");
            } //Check comfirm password is the same as password will call error password
            else if (passIn.getText().equals(cfPassIn.getText())) {
                //Check if it has Blank Field will call error blank field
                if (nameIn.getText().isBlank() || passIn.getText().isBlank() || usernameIn.getText().isBlank()
                        || mailIn.getText().isBlank() || dateSet == false || question.equals(null) || answer.getText().isBlank()) {
                    AlertBox.displayAlert("Something went wrong!", "Please check all the form.\nAnd make sure it was filled.");
                } else {
                    //Check Date of Birth
                    if (dOB.isAfter(LocalDate.now())) { // checkdate if user born after present
                        System.out.println("User is come from the future.");
                        AlertBox.displayAlert("Something went wrong!", "Please check a date field again.\n"
                                + "Make sure that's your date of birth.");
                    } //Check email if it's not will call error email [ see function isEmail for more information]
                    else if (!isEmail(mailIn.getText())) {
                        AlertBox.displayAlert("Something went wrong!", "Please use another email.");

                    } else {
                        try {
                            addAccount = file.readFile(this.user);
                        } catch (IOException | ClassNotFoundException ex) {
                            System.out.println("Register : IOExeption read file in Register consturtor after check");
                        }

                        if (userRole.equals("admin") == false) { //<-------------------------------------------------
//                            if (roles.equals("premium")) {
//                                addAccount.add(new Account(nameIn.getText(), surnameIn.getText(), usernameIn.getText(), mailIn.getText(),
//                                        passIn.getText(), userGender, dOB, question.getValue(), answer.getText(), "premium", image));
//                            } else {
                            System.out.println("Adding Account1 userRole.equals(admin) == false");
                            addAccount.add(new Account(nameIn.getText(), surnameIn.getText(), usernameIn.getText(), mailIn.getText(),
                                    passIn.getText(), userGender, dOB, question.getValue(), answer.getText(), "member", image));
//                            }

                        } else {
                            String userRoleReg = "member";
                            if (adminSelect.isSelected()) {
                                userRoleReg = "admin";
                            } else if (premiumSelect.isSelected()) {
                                userRoleReg = "premium";
                            }

                            addAccount.add(new Account(nameIn.getText(), surnameIn.getText(), usernameIn.getText(), mailIn.getText(),
                                    passIn.getText(), userGender, dOB, question.getValue(), answer.getText(), userRoleReg, image));
                        }

                        try {
                            file.writeFile(this.user, addAccount);
                            System.out.println("Saving account.");
                        } catch (IOException ex) {
                            System.out.println("Register : IOExeption write file in Register consturtor save account");
                        }
                        try {
                            listUserAccount = file.readFile(this.user);
                        } catch (Exception ex) {
                            System.out.println("Register : Exeption read file in Register consturtor after save account");
                        }

                        if (userRole.equals("admin")) {
                            AlertBox.displayAlert("Add Account Complete", "This account has been saved.");
                        } else {
                            if (premiumBox.isSelected()) { //NEW UPDATE buy premium after register then use account to upgrade can use this in user_ui too (maybe)
                                for (Account account : listUserAccount) {
                                    String userId = usernameIn.getText(), emailID = mailIn.getText();
                                    String chkUser = account.getUsername(), chkEmail = account.getEmail();
                                    if ((userId.equals(chkUser) || emailID.equals(chkEmail))) {
                                        Cashing cashPremium = new Cashing();
                                        cashPremium.buyPremium(new Stage(), account);
                                        break;
                                    }
                                }

                            }
                            AlertBox.displayAlert("Register Complete", "Your account has been saved.\nTry to login now.");
                        }

                        System.out.println("Registeraion Complete!\n");

                        regisStage.close();
                    }
                }
            } else {
                AlertBox.displayAlert("Something went wrong!", "Confirm password is not as same as password.");
            }

        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnAction(e -> {
            System.out.println("Canceling Registeration.");
            regisStage.close();
        });

        HBox row1 = new HBox(20); //Name Row
        row1.getChildren().addAll(nameIn, surnameIn);
        row1.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(20);//Password Row
        row3.getChildren().addAll(passIn, cfPassIn);
        row3.setAlignment(Pos.CENTER);

        Label title2 = new Label("Date of birth");
        title2.getStyleClass().add("detailRegist");

        HBox sexRow = new HBox(20);
        sexRow.getChildren().addAll(male, female, otherRadio);
        sexRow.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20); //Button Row
        row2.getChildren().addAll(saveBtn, cancelBtn);
        row2.setAlignment(Pos.CENTER);
        row2.setPadding(new Insets(20));

        Button changeImage = new Button("Change Image");
        changeImage.getStyleClass().add("buybtn");
        changeImage.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));


            //Set to user's directory or go to the default C drvie if cannot access
            String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
            File userDirectory = new File(userDirectoryString);

            if (!userDirectory.canRead()) {
                userDirectory = new File("user.home");
            }

            fileChooser.setInitialDirectory(userDirectory);

            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image
            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
//                this.photo.setImage(image);
                profilePicture.getChildren().remove(0);
                profilePicture.getChildren().addAll(new ImageCircle(100, 100, 100, image).getMyCircle());
            } catch (IOException e) {
                System.out.println("Register : IOExeption uplode file picture in Register consturtor");
            }
        });

        VBox column1 = new VBox(20);
        column1.setPadding(new Insets(10)); //add gap 10px
        column1.setMaxWidth(330);

        HBox statusRow = new HBox(20);
        statusRow.setAlignment(Pos.CENTER);

        if (userRole.equals("admin")) {
            statusRow.getChildren().addAll(userSelect, premiumSelect, adminSelect);
            column1.getChildren().add(statusRow);
            column1.getChildren().addAll(row1, usernameIn, mailIn, row3, title2, date, sexText, sexRow, qText, question, answer);
        } else {
            column1.getChildren().addAll(row1, usernameIn, mailIn, row3, title2, date, sexText, sexRow, qText, question, answer, premiumBox);
        }
        column1.setAlignment(Pos.CENTER);
        
        profilePicture.getChildren().add(new ImageCircle(100, 100, 100, new Image("/image/defaultprofile.png")).getMyCircle());

        photo = new ImageView(new Image("/image/defaultprofile.png"));
        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);
        photo.getStyleClass().add("pictureAppear");

        adminSelect.setToggleGroup(statusToggle);
        userSelect.setToggleGroup(statusToggle);
        premiumSelect.setToggleGroup(statusToggle);
        statusToggle.selectToggle(userSelect);
        adminSelect.getStyleClass().add("detailRegistChoiceAdmin");
        userSelect.getStyleClass().add("detailRegistChoiceAdmin");
        premiumSelect.getStyleClass().add("detailRegistChoiceAdmin");

        VBox titleRow = new VBox(5);
        titleRow.getChildren().add(title);
        titleRow.setAlignment(Pos.CENTER);
        titleRow.setPadding(new Insets(10));

        VBox column2 = new VBox(10);
        column2.setPadding(new Insets(40));
        column2.getChildren().addAll(profilePicture, changeImage);
        column2.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        //pane.setPadding(new Insets(10));

        pane.setTop(titleRow);
        pane.setCenter(column1);
        pane.setRight(column2);
        pane.setBottom(row2);

        pane.setAlignment(title, Pos.CENTER);
        pane.setAlignment(column2, Pos.CENTER);

        
        HBox totalHBox = new HBox(pane,exitButton());
        totalHBox.setPadding(new Insets(20));
        totalHBox.getStyleClass().add("allPane");
        totalHBox.getStyleClass().add("allPane"); //CSS
        totalHBox.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        totalHBox.setOnMouseDragged(e -> {
            regisStage.setX(e.getScreenX() - mouse_x);
            regisStage.setY(e.getScreenY() - mouse_y);
        });
        
        
        Scene regScene;
        if (userRole.equals("admin")) {
            regScene = new Scene(totalHBox); //, 600, 630
        } else {
            regScene = new Scene(totalHBox); //, 550, 630
        }
        
        regScene.setFill(Color.TRANSPARENT);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        regScene.getStylesheets().add(stylrSheet); // CSS

        regisStage.initStyle(StageStyle.TRANSPARENT);
        regisStage.setScene(regScene);
        regisStage.setResizable(false);
        regisStage.setTitle("Registeration.");
        regisStage.showAndWait();
    }

    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.contains(" ") || email.contains(";") || email.contains(",") || email.contains("..")
                || email.length() <= 12 || email.length() > 64);
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
            regisStage.close();
        });
        exit.setStyle("-fx-background-color : transparent;"); //CSS
        //exit.setPadding(Insets.EMPTY);

        return exit;
    }
    
}
