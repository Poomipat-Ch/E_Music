/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    Button btn;
    int numberOfClick;
    Scene scene1;
    static Stage stage;

    File user = new File("src/data/user.dat");
    File admin = new File("src/data/admin.dat");
    
    ArrayList<Account> listAccount = new ArrayList<>();

    public Login(Stage stage) {
       this.stage = stage;
        
        Label title1 = new Label("Sign in");
        //Username
        Label idLabel = new Label("Email / username:");
        TextField idInput = new TextField();

        //Password
        Label passLabel = new Label("Password");
        TextField passInput = new PasswordField();

        //Checkbox
        CheckBox chk1 = new CheckBox("Remember this");
        //if(chk1.isSelected
        
        try {
            listAccount = readFile(user);
        } catch (Exception e) {
            System.out.println("Login " + e);
        }

        //Button
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            System.out.println("Logging in...");
            boolean logIn = false;
            for (Account account : listAccount) {
                String thisUser = (String) idInput.getText(), thisPass = (String) passInput.getText();
                String chkUser = account.geteMail(), chkPass = account.getPassword();
                if (thisUser.equals(chkUser) && thisPass.equals(chkPass)) {
                    //Go user / admin page
                    logIn = true;
                    break;
                }
            }
            if(logIn){
                //success goto user page
                System.out.println("Login complete.\n");
                //AlertBox.display("Login Complete", "Go to main page.");
                this.stage.hide();
                User_UI user = new User_UI(new Stage());
            }
            else{
                //show error
                AlertBox.display("Login Failed", "Please try again.");
            }
            idInput.clear();
            passInput.clear();
        });

        Button forgotBtn = new Button("Forgot Password?");
        forgotBtn.setOnAction(e -> {
            System.out.println("User forgot password.");
            forgetPassword();
        });

        Button registerBtn = new Button("Create a new account");
        registerBtn.setOnAction(e -> {
            System.out.println("User want to register.");
            register(idInput.getText());
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

        scene1.getStylesheets().add(getClass().getResource("/Style/styleLogin.css").toExternalForm());

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
    
    
    public void register(String email) {
        Stage regisStage = new Stage();
        regisStage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Sign up");

        TextField nameIn = new TextField();
        nameIn.setPromptText("Name");
        TextField surnameIn = new TextField();
        surnameIn.setPromptText("Surname");
        TextField mailIn = new TextField(email);
        mailIn.setPromptText("Email e.g. Spookify@gmail.com");
        TextField passIn = new PasswordField();
        passIn.setPromptText("New Password");
        TextField cfPassIn = new PasswordField();
        cfPassIn.setPromptText("Confirm Password");

        Label title2 = new Label("Date of birth");
        ChoiceBox<String> dOb = new ChoiceBox<>();
        dOb.getItems().addAll("1", "2", "3");
        ChoiceBox<String> mOb = new ChoiceBox<>();
        mOb.getItems().addAll("1", "2", "3");
        ChoiceBox<String> yOb = new ChoiceBox<>();
        yOb.getItems().addAll("1", "2", "3");
        Label title3 = new Label("Gender");
        ToggleGroup sexToggle = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        male.setToggleGroup(sexToggle);
        female.setToggleGroup(sexToggle);
        //sexToggle.getItem

        Button ok = new Button("OK");
        ok.setOnAction(e -> {
            System.out.println("Checking information...");
            ArrayList<Account> addAccount = new ArrayList<>();
            
            System.out.println("");
            
            if(passIn.getText().equals(cfPassIn.getText())) {
                
                try {
                    addAccount = readFile(user);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Register readFile " + ex);
                } 
                
                addAccount.add(new Account(nameIn.getText(), mailIn.getText(), passIn.getText(), "hello", "hi"));
                
                try {
                    writeFile(user, addAccount);
                } catch (IOException ex) {
                    System.out.println("Register writeFile " + ex);
                }
                try {
                    listAccount = readFile(user);
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                AlertBox.display("Register Complete", "Your account has been saved.\nTry to login now.");
                System.out.println("Saving account.");
                System.out.println("Registeraion Complete!\n");
                
                regisStage.close();
            } else {
                System.out.println("Register Failed!\n");
                nameIn.clear();
                surnameIn.clear();
                mailIn.clear();
                passIn.clear();
                cfPassIn.clear();
            }
            
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            System.out.println("Canceling Registeration.");
            regisStage.close();
        });

        HBox row1 = new HBox(20); //Name Row
        row1.getChildren().addAll(nameIn, surnameIn);
        row1.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(20);//Password Row
        row3.getChildren().addAll(passIn, cfPassIn);
        row3.setAlignment(Pos.CENTER);

        HBox dateRow = new HBox(20);
        dateRow.getChildren().addAll(dOb, mOb, yOb);
        dateRow.setAlignment(Pos.CENTER);
        HBox sexRow = new HBox(20);
        sexRow.getChildren().addAll(male, female);
        sexRow.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20); //Button Row
        row2.getChildren().addAll(ok, cancel);
        row2.setAlignment(Pos.CENTER);

        // create a date picker 
        DatePicker date = new DatePicker();

        // show week numbers 
        date.setShowWeekNumbers(false);

        // when datePicker is pressed 
        date.setOnAction(e -> {
            LocalDate dOB = date.getValue();
        });

        VBox column1 = new VBox(20);
        column1.getChildren().addAll(title, row1, mailIn, row3, title2, date, title3, sexRow, row2);
        column1.setAlignment(Pos.CENTER);
        Scene regScene = new Scene(column1, 360, 500);
        regisStage.setScene(regScene);
        regisStage.setTitle("Registeration.");
        regisStage.showAndWait();

    }

    public void forgetPassword() {
        Stage fgtStage = new Stage();
        fgtStage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Find your account");
        Label descrip = new Label("Enter your email address to find your account.");
        TextField mailIn = new TextField();
        mailIn.setPromptText("e.g. spookify@gmail.com");

        Button ok = new Button("Find");
        ok.setOnAction(e -> {
            System.out.println("Finding...");
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            System.out.println("Canceling finding password.");
            fgtStage.close();
        });
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(ok, cancel);
        VBox box1 = new VBox(10);
        box1.getChildren().addAll(title, descrip, mailIn, hbox);
        Scene forgScene = new Scene(box1, 360, 200);
        fgtStage.setScene(forgScene);
        fgtStage.setTitle("Forget Password / Cannot login");
        fgtStage.showAndWait();

    }
    
    private ArrayList<Account> readFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Account>) in.readObject();
    }

    private void writeFile(File file, ArrayList<Account> listAccount) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(listAccount);   
        out.close();
    }

    
    public static Stage getStage(){
        
        return stage;
    }
    
    
}
