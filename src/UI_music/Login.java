/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author poomi
 */
public class Login {

    Scene scene1;
    static Stage stage;

    File user = new File("src/data/user.dat");
    File tempId = new File("src/data/user.txt");

    ArrayList<Account> listUserAccount = new ArrayList<>();
    Account userAccount = new Account();
    ArrayList<Account> addAccount = new ArrayList<>();
    

    public Login(Stage stage) throws FileNotFoundException, IOException, ClassNotFoundException {

//**READ THIS***** This save in user.dat file, if you can't run admin, try run this code only once and comment again
//        listUserAccount.add(new Account("admin", "admin", "admin", "admin@gmail.com", "admin", "N/A", LocalDate.now(), "admin", "admin", "admin", new Image("/image/defaultprofile.png")));
//        writeFile(user, listUserAccount); 

        Login.stage = stage;

        Label title1 = new Label("Sign in");
        //Get Username
        Label idLabel = new Label("Email / username:");
        TextField idInput = new TextField();

        try {
            DataInputStream tempID = new DataInputStream(new FileInputStream(tempId));
            idInput.setText(tempID.readLine());
        } catch (FileNotFoundException ex) {
            System.out.println("Login : FileNotFoundExeption read user.txt in Login constructor");
        }

        //Get Password
        Label passLabel = new Label("Password");
        TextField passInput = new PasswordField();

        //Remember me Checkbox
        CheckBox chk1 = new CheckBox("Remember this");

        //init read file
        //Login Action Event
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {

            try {
                listUserAccount = readFile(user);
            } catch (IOException ex) {
                System.out.println("Login : IOExeption readfile in login constructor");
            } catch (ClassNotFoundException ex) {
                System.out.println("Login : ClassNotFoundExeption writefile in Login constructor");
            }

            //If checkbox is checked, program will remember username to ID field
            if (chk1.isSelected()) {
                try {
                    DataOutputStream tempO = new DataOutputStream(new FileOutputStream(tempId));
                    tempO.writeUTF(idInput.getText());
                } catch (Exception ex) {
                    System.out.println("Login : Exeption write user.txt in login constructor");
                }
            } else {
                try {
                    DataOutputStream tempO = new DataOutputStream(new FileOutputStream(tempId));
                    tempO.writeUTF("");
                } catch (Exception ex) {
                    System.out.println("Login : Exeption write user.txt in Login constructor");
                }
            }
            System.out.println("Logging in...");
            boolean logIn = false; //Validating account
            for (Account account : listUserAccount) {
                String thisUser = idInput.getText(), thisPass = passInput.getText();
                String chkUser = account.getUsername(), chkPass = account.getPassword();
                String chkEmail = account.getEmail();
                if ((thisUser.equals(chkUser) || thisUser.equals(chkEmail)) && thisPass.equals(chkPass)) {
                    userAccount = account;
                    logIn = true;
                    break;
                }
            }
            //Can Login
            if (logIn) {
                //success goto user page
                System.out.println("Login complete.\nWelcome, " + userAccount.getName());
                // AlertBox.display("Login Complete", "Go to main page.");
                Login.stage.hide();
                if (userAccount.getUserRole().equals("admin")) {
                    Admin_UI admin_UI = new Admin_UI(new Stage(), userAccount); // <-- EDIT HERE Mr.Sirawit
                } else {
                    User_UI user_UI = new User_UI(new Stage(), userAccount);
                }
                idInput.clear();
                passInput.clear();
            } else {
                //show error
                if (idInput.getText().isBlank() || passInput.getText().isBlank()) {
                    AlertBox.displayAlert("Login Failed", "Please fill username/password");
                } else {
                    AlertBox.displayAlert("Login Failed", "Username or Password is not correct.");
                }
            }
        });

        Button forgotBtn = new Button("Forgot Password?");
        forgotBtn.setOnAction(e -> {
            System.out.println("User forgot password.");
            forgetPassword();
        });

        Button registerBtn = new Button("Create a new account");
        registerBtn.setOnAction(e -> {
            System.out.println("User want to register.");
            register();
        });

        VBox rightMenu = new VBox(10);
        rightMenu.getChildren().addAll(title1, idLabel, idInput, passLabel, passInput, chk1, loginBtn, forgotBtn, registerBtn);
        rightMenu.setAlignment(Pos.CENTER_LEFT);
        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(rightMenu, Pos.CENTER);
        BorderPane.setMargin(rightMenu, new Insets(15, 15, 15, 15));
        borderPane.setRight(rightMenu);
        borderPane.getStyleClass().add("backgroundImage");

        scene1 = new Scene(borderPane, 720, 576);

        scene1.getStylesheets().add(getClass().getResource("/style_css/styleLogin.css").toExternalForm());

        stage.setScene(scene1);
        stage.setTitle("Spookify - Login/Register");

        stage.setOnCloseRequest(e -> {
            e.consume();
            boolean ans = AlertBox.display("Close Program", "Are you sure to exit?");
            if (ans) {
                stage.close();
            }
        });
        stage.show();

    }

    public void register() {
        //StringProperty name, surname, mail, password, sex;
        new Register("member");
    }

    public void forgetPassword() {
        Stage fgtStage = new Stage();
        fgtStage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Find your account");
        Label descrip = new Label("Enter your email address to find your account.");
        TextField mailIn = new TextField();
        mailIn.setPromptText("e.g. spookify@gmail.com");

        Label title2 = new Label("Reset Password");
        Label askQ = new Label("Question: " + userAccount.getQuestion());
        TextField answer = new TextField();
        answer.setPromptText("Type Answer here");
        TextField passIn1 = new PasswordField();
        passIn1.setPromptText("New Password.");
        TextField passIn2 = new PasswordField();
        passIn2.setPromptText("Re-enter Password.");
        Button resetBtn = new Button("Reset Password");

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(title2, askQ, answer, passIn1, passIn2, resetBtn);
        vBox.setAlignment(Pos.CENTER);
        Scene newPassScene = new Scene(vBox, 360, 200);

        Button ok = new Button("Find");
        ok.setOnAction(e -> {
            boolean foundEmail = false;
            System.out.println("Finding...");
            String thisUser = mailIn.getText();
            for (Account account : listUserAccount) {
                String chkEmail = account.getEmail();
                if (thisUser.equals(chkEmail)) {
                    System.out.println("User email founded!");
                    userAccount = account;
                    foundEmail = true;
                    break;
                }
            }
            if (foundEmail) {
                AlertBox.displayAlert("Email Founded!", "Click OK to reset your password");
                askQ.setText("Question: " + userAccount.getQuestion());
                fgtStage.setScene(newPassScene);
            } else {
                System.out.println("User email not found");
                AlertBox.displayAlert("Email not found!", "Your email is invalid.");
            }
        });

        resetBtn.setOnAction(e -> {
            if (answer.getText().equals(userAccount.getAnswer())) {
                if (passIn1.getText().equals(passIn2.getText())) {
                    System.out.println("Changing password.");
                    ArrayList<Account> addAccount = new ArrayList<>();

                    addAccount.add(new Account(userAccount.getName(), userAccount.getSurname(), userAccount.getUsername(), userAccount.getEmail(),
                            passIn1.getText(), userAccount.getGender(), userAccount.getDateOfBirth(), userAccount.getQuestion(), userAccount.getAnswer(), "member", userAccount.getPhoto()));
                    addAccount.remove(userAccount);

                    try {
                        writeFile(user, addAccount);
                    } catch (IOException ex) {
                        System.out.println("Login : IOExeption writefile in forgotpassword ");
                    }

                    userAccount.setPassword(passIn1.getText());
                    AlertBox.displayAlert("Reset Password", "Password changed successfully.");
                    fgtStage.close();
                }
            } else {
                System.out.println("Answer not correct.");
                AlertBox.displayAlert("Error", "Sorry, Your answer is not correct.");
            }
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            System.out.println("Canceling finding password.");
            fgtStage.close();
        });
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(ok, cancel);
        hbox.setAlignment(Pos.CENTER);
        VBox box1 = new VBox(10);
        box1.getChildren().addAll(title, descrip, mailIn, hbox);

        Scene forgScene = new Scene(box1, 360, 200);
        fgtStage.setScene(forgScene);
        fgtStage.setTitle("Forget Password / Cannot login");
        fgtStage.showAndWait();

    }
    
    private ArrayList<Account> readFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        ArrayList<Account> list = (ArrayList<Account>) in.readObject();
        in.close();
        return list;
    }

    private void writeFile(File file, ArrayList<Account> listAccount) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(listAccount);
        out.close();
    }

    //Verify email address bab easy
    //.contains = have that string in email
    //catch case dai pra marn nee

    public static Stage getStage() {

        return stage;
    }

}
