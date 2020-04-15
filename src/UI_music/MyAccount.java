/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author HCARACH
 */
public class MyAccount {

    private BorderPane profilePane = new BorderPane();
    private BorderPane changePane = new BorderPane();

    private File user = new File("src/data/user.dat");
    private ReadWriteFile file = new ReadWriteFile();

    private Account myAccount;
    private ArrayList<Account> listAccount = new ArrayList<>();

    private LocalDate dOB;

    private VBox editBox = new VBox(10);
    private VBox changeBox = new VBox(10);
    private VBox leftBox = new VBox(10);
    private HBox bottomBox = new HBox();

    //TextFiled
    private TextField username = new TextField();
    private TextField firstname = new TextField();
    private TextField lastname = new TextField();
    private TextField email = new TextField();
    private TextField currentPassword = new PasswordField();
    private TextField password = new PasswordField();
    private TextField passwordConfirm = new PasswordField();

    private ToggleGroup sexToggle = new ToggleGroup(); //create radio button group
    private RadioButton male;
    private RadioButton female;
    private RadioButton otherRadio;

    boolean changeStatus = false;

    private FileChooser fileChooser;
    private File filePath;
    private ImageView photo;
    private Image image;

    public MyAccount(Account myAccount) {
        this.showAccount(myAccount);
    }

    public BorderPane getProfilePane() {
        return profilePane;
    }

    public void showAccount(Account myAccount) {
        this.myAccount = myAccount;
        editBox.getChildren().clear();

        VBox row = new VBox(10);
        row.getChildren().addAll(new Text("Username : \t"), new Label(myAccount.getUsername()));

        HBox row2 = new HBox(10);
        row2.getChildren().addAll(new Text("Name : "), new Label(myAccount.getName()), new Label(myAccount.getSurname()));

        HBox row3 = new HBox(5);
        row3.getChildren().addAll(new Text("Last Name : \t\t\t    "), new Label(myAccount.getSurname()));

        VBox row5 = new VBox(10);
        row5.getChildren().addAll(new Text("Email : \t\t"), new Label(myAccount.getEmail()));

        HBox row7 = new HBox(10);
        DatePicker date = new DatePicker();
        dOB = myAccount.getDateOfBirth();
        date.setValue(dOB);
        row7.getChildren().addAll(new Text("Date of Birth : "), new Label(dOB.format(DateTimeFormatter.ISO_DATE)));

        HBox row9 = new HBox(10);
        row9.getChildren().addAll(new Label("Gender : "), new Label(myAccount.getGender()));

        //Blank line
        HBox row1 = new HBox(10);
        row1.getChildren().add(new Text(" "));
        HBox row4 = new HBox(10);
        row4.getChildren().add(new Text(" "));
        HBox row6 = new HBox(10);
        row6.getChildren().add(new Text(" "));
        HBox row8 = new HBox(10);
        row8.getChildren().add(new Text(" "));
        HBox row10 = new HBox(10);
        row10.getChildren().add(new Text(" "));
        HBox row12 = new HBox(10);

        image = myAccount.getPhoto();
        photo = new ImageView(image);
        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);

        VBox profilePictureBox = new VBox(10);
        profilePictureBox.setPadding(new Insets(30));
        profilePictureBox.getChildren().addAll(photo);
        profilePictureBox.setAlignment(Pos.CENTER);
        profilePictureBox.setMinWidth(300);

        editBox.getChildren().addAll(row, row1, row2, row4, row5, row6, row7, row8, row9, row10);
        editBox.setPadding(new Insets(50, 50, 50, 50));

        profilePane.setStyle("-fx-background-color: #f5deb3");
        profilePane.setLeft(profilePictureBox);
        profilePane.setCenter(editBox);
    }

    public void editAccount() {
        
        this.changeStatus = false;
        username.setPromptText("Usrname");
        username.setText(this.myAccount.getUsername());
        username.setMaxWidth(300);

        VBox row = new VBox(10);
        row.getChildren().addAll(new Text("Username : \t"), username);

        firstname.setPromptText("First Name");
        firstname.setText(this.myAccount.getName());
        firstname.setMaxWidth(300);

        HBox row2 = new HBox(10);
        row2.getChildren().addAll(new Text("First Name : \t\t\t    "), new Text("Last Name : \t"));

        lastname.setPromptText("Last Name");
        lastname.setText(this.myAccount.getSurname());
        lastname.setMaxWidth(300);

        HBox row3 = new HBox(5);
        row3.getChildren().addAll(firstname, lastname);

        email.setPromptText("Email e.g. Spookify@gmail.com");
        email.setText(this.myAccount.getEmail());
        email.setMaxWidth(300);

        VBox row5 = new VBox(10);
        row5.getChildren().addAll(new Text("Email : \t\t"), email);

        // create a date picker 
        DatePicker date = new DatePicker();
        // show week numbers 
        date.setShowWeekNumbers(false);
        // when datePicker is pressed 
        dOB = myAccount.getDateOfBirth();
        date.setValue(dOB);
        date.setOnAction(e -> {
            dOB = date.getValue();
        });

        HBox row7 = new HBox(10);
        row7.getChildren().addAll(new Text("Date of Birth : "), date);

        //Select Gender
        Label sexText = new Label("Gender : ");
        ToggleGroup sexToggle = new ToggleGroup(); //create radio button group
        male = new RadioButton("Male"); //create radio button
        female = new RadioButton("Female");
        otherRadio = new RadioButton("Not specify");
        male.setToggleGroup(sexToggle); //Set radio button group
        female.setToggleGroup(sexToggle);
        otherRadio.setToggleGroup(sexToggle);

        if (myAccount.getGender().equals("Male")) {
            sexToggle.selectToggle(male);
        } else if (myAccount.getGender().equals("Female")) {
            sexToggle.selectToggle(female);
        } else {
            sexToggle.selectToggle(otherRadio);
        }

        HBox row9 = new HBox(10);
        row9.getChildren().addAll(sexText, male, female, otherRadio);

        currentPassword.setMaxWidth(300);

        VBox row11 = new VBox(10);
        row11.getChildren().addAll(new Text("Current Password : \t"), currentPassword);

        //Blank line
        HBox row1 = new HBox(10);
        row1.getChildren().add(new Text(" "));
        HBox row4 = new HBox(10);
        row4.getChildren().add(new Text(" "));
        HBox row6 = new HBox(10);
        row6.getChildren().add(new Text(" "));
        HBox row8 = new HBox(10);
        row8.getChildren().add(new Text(" "));
        HBox row10 = new HBox(10);
        row10.getChildren().add(new Text(" "));
        HBox row12 = new HBox(10);
        row12.getChildren().add(new Text(" "));
        HBox row14 = new HBox(10);
        row14.getChildren().add(new Text(" "));

        password.setMaxWidth(300);

        HBox row13 = new HBox(10);
        row13.getChildren().addAll(new Text("New Password : \t"), password);

        passwordConfirm.setMaxWidth(300);

        HBox row15 = new HBox(10);
        row15.getChildren().addAll(new Text("Confirm New Password :"), passwordConfirm);

        Label changePassword = new Label("Change Password?");
        changePassword.setStyle("-fx-underline: true");

        changePassword.setOnMouseClicked(event -> {
            editBox.getChildren().remove(13);
            editBox.getChildren().addAll(row13, row14, row15);
            changeStatus = true;
        });

        Button changeImage = new Button("Change Image");
        changeImage.setOnAction(event -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");

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
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                this.photo.setImage(image);
                this.image = image;
            } catch (IOException e) {
                System.out.println("MyAcoount : IOExeption upload picture in editAccount");
            }
        });

        VBox profilePictureBox = new VBox(10);
        profilePictureBox.setPadding(new Insets(50, 0, 0, 0));
        profilePictureBox.getChildren().addAll(photo, changeImage);
        profilePictureBox.setAlignment(Pos.TOP_CENTER);
        profilePictureBox.setMinWidth(300);

        editBox.getChildren().clear();
        editBox.getChildren().addAll(row, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, changePassword);
        editBox.setPadding(new Insets(50, 50, 50, 50));
        profilePane.setStyle("-fx-background-color: #f5deb3");
        profilePane.setLeft(profilePictureBox);
        profilePane.setCenter(editBox);
    }

    public boolean saveAccount() {

        boolean editSave = false;

        String userGender;

        if (male.isSelected()) {
            userGender = "Male";
        } else if (female.isSelected()) {
            userGender = "Female";
        } else {
            userGender = "N/A";
        }

        try {
            listAccount = file.readFile(user);
        } catch (Exception e) {
            System.out.println("MyAcoount : Exeption read file in saveAccount");
        }

        boolean uniqueID = true;
        for (Account account : listAccount) {
            String userId = username.getText(), emailID = email.getText();
            String chkUser = account.getUsername(), chkEmail = account.getEmail();
            if (((userId.equals(chkUser) && !userId.equals(myAccount.getUsername())) || (emailID.equals(chkEmail) && !emailID.equals(myAccount.getEmail())))) {
                uniqueID = false;
                break;
            }
        }
        
        System.out.println(uniqueID);
        
        if (uniqueID == false) {
            AlertBox.displayAlert("Something went wrong!", "Email / username is already exists.");
        } else if (currentPassword.getText().equals(myAccount.getPassword())) {
            if(this.changeStatus && !password.getText().equals(passwordConfirm.getText())) {
                AlertBox.displayAlert("Something went wrong!", "Confirm password is not as same as password.");
            }
            else if (firstname.getText().isBlank() || lastname.getText().isBlank() || username.getText().isBlank()
                    || email.getText().isBlank()) {
                AlertBox.displayAlert("Something went wrong!", "Please check all the form.\nAnd make sure it was filled.");
            } else {
                if (dOB.isAfter(LocalDate.now())) {
                    System.out.println("User is come from the future.");
                    AlertBox.displayAlert("Something went wrong!", "Please check a date field again.\n"
                            + "Make sure that's your date of birth.");
                } else if (!isEmail(email.getText())) {
                    AlertBox.displayAlert("Something went wrong!", "Please use another email.");
                } else {

                    ArrayList<Account> changeAccount = new ArrayList<>();

                    if (changeStatus) {
                        myAccount.setPassword(this.password.getText());
                    }

                    for (Account account : listAccount) {
                        if (account.getUsername().equals(myAccount.getUsername())) {
                            myAccount.setPhoto(image);
                            this.photo.setImage(myAccount.getPhoto());
                            Account newAccount = new Account(firstname.getText(), lastname.getText(), username.getText(), email.getText(),
                                    myAccount.getPassword(), userGender, dOB, myAccount.getQuestion(), myAccount.getAnswer(), myAccount.getIsAdmin(), myAccount.getPhoto());
                            changeAccount.add(newAccount);
                            myAccount = newAccount;

                        } else {
                            changeAccount.add(account);
                        }
                    }

                    try {
                        file.writeFile(user, changeAccount);
                        System.out.println("Saving account.");
                    } catch (IOException ex) {
                        System.out.println("MyAcoount : IOExeption write file in saveAccount");
                    }
                    try {
                        listAccount = file.readFile(user);
                    } catch (Exception ex) {
                        System.out.println("MyAcoount : IOExeption read file in saveAccount");
                    }

                    System.out.println("Edit Profile Complete!\n");

                    editSave = true;
                }
            }

            this.Clear();

        } else if (!currentPassword.getText().equals(myAccount.getPassword())) {
            AlertBox.displayAlert("Something went wrong!", "Current password is not correct.");
        }

        changeStatus = false;
        return editSave;
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public void Clear() {
        currentPassword.clear();
        password.clear();
        passwordConfirm.clear();
    }

    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.contains(" ") || email.contains(";") || email.contains(",") || email.contains("..")
                || email.length() <= 12 || email.length() > 64);
    }

}
