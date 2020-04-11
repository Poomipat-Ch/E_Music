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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    LocalDate dOB;
    boolean dateSet = false;
    static Stage stage;

    File user = new File("src/data/user.dat");
    File admin = new File("src/data/admin.dat");
    File tempId = new File("src/data/user.txt");

    ArrayList<Account> listAccount = new ArrayList<>();
    Account userAccount = new Account();
    ArrayList<Account> addAccount = new ArrayList<>();

    public Login(Stage stage) throws FileNotFoundException, IOException {

        
//**READ THIS***** This save in user.dat file, if you can't run admin, try run this code only once and comment again

//        listAccount.add(new Account("admin", "admin", "admin", "admin@gmail.com", "admin", null, null, null, null, true));
//        writeFile(user, listAccount); 


        Login.stage = stage;

        Label title1 = new Label("Sign in");
        //Get Username
        Label idLabel = new Label("Email / username:");
        TextField idInput = new TextField();

        try {
            DataInputStream tempID = new DataInputStream(new FileInputStream(tempId));
            idInput.setText(tempID.readLine());
        } catch (FileNotFoundException ex) {
            System.out.println("File error: " + ex);
        }

        //Get Password
        Label passLabel = new Label("Password");
        TextField passInput = new PasswordField();

        //Remember me Checkbox
        CheckBox chk1 = new CheckBox("Remember this");

        //init read file
        try {
            listAccount = readFile(user);
        } catch (Exception e) {
            System.out.println("Read File error: " + e);
        }

        //Login Action Event
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            //If checkbox is checked, program will remember username to ID field
            if (chk1.isSelected()) {
                try {
                    DataOutputStream tempO = new DataOutputStream(new FileOutputStream(tempId));
                    tempO.writeUTF(idInput.getText());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                try {
                    DataOutputStream tempO = new DataOutputStream(new FileOutputStream(tempId));
                    tempO.writeUTF("");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            System.out.println("Logging in...");
            boolean logIn = false; //Validating account
            for (Account account : listAccount) {
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
                if (userAccount.getIsAdmin()) {
                    Admin_UI admin_UI = new Admin_UI(new Stage()); // <-- EDIT HERE Mr.Sirawit
                } else {
                    User_UI user_UI = new User_UI(new Stage());
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

    public void register(String email) {
        //StringProperty name, surname, mail, password, sex;
        Stage regisStage = new Stage();
        regisStage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("Sign up");

        //Fill name surname username email password
        TextField nameIn = new TextField();
        nameIn.setPromptText("Name");
        TextField surnameIn = new TextField();
        surnameIn.setPromptText("Surname");
        TextField usernameIn = new TextField();
        usernameIn.setPromptText("Username");
        TextField mailIn = new TextField(email);
        mailIn.setPromptText("Email e.g. Spookify@gmail.com");
        TextField passIn = new PasswordField();
        passIn.setPromptText("New Password");
        TextField cfPassIn = new PasswordField();
        cfPassIn.setPromptText("Confirm Password");

        // create a date picker 
        DatePicker date = new DatePicker();
        // show week numbers 
        date.setShowWeekNumbers(false);
        // when datePicker is pressed 
        date.setOnAction(e -> {
            dOB = date.getValue();
            dateSet = true;
        });

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

        //FORGOT QUESTION
        Label qText = new Label("Security Question");
        ComboBox<String> question = new ComboBox<>();
        question.getItems().addAll(
                "What's your first school.",
                "Your favourite pet's name.",
                "Your father's name"
        );
        question.setPromptText("Select / write a question.");
        question.setEditable(true); //USER CAN WRITE THEIR OWN QUESTION
        //FORGOT ANSWER
        TextField answer = new TextField();
        answer.setPromptText("Answer");

        Button ok = new Button("OK");
        ok.setOnAction(e -> {
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


            try {
                listAccount = readFile(user);
            } catch (Exception ex) {
                System.out.println("error: " + ex);
            }
            boolean uniqueID = true;
            //Check already username / email
            for (Account account : listAccount) {
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
                    }
                    //Check email if it's not will call error email [ see function isEmail for more information]
                    else if (!isEmail(mailIn.getText())) {
                        AlertBox.displayAlert("Something went wrong!", "Please use another email.");

                    } else {
                        try {
                            addAccount = readFile(user);
                        } catch (IOException | ClassNotFoundException ex) {
                            System.out.println("Register readFile " + ex);
                        }

                        addAccount.add(new Account(nameIn.getText(), surnameIn.getText(), usernameIn.getText(), mailIn.getText(),
                                passIn.getText(), userGender, dOB, question.getValue(), answer.getText(), false));

                        try {
                            writeFile(user, addAccount);
                            System.out.println("Saving account.");
                        } catch (IOException ex) {
                            System.out.println("Register writeFile " + ex);
                        }
                        try {
                            listAccount = readFile(user);
                        } catch (Exception ex) {
                            System.out.println("Error: " + ex);
                        }
                        AlertBox.displayAlert("Register Complete", "Your account has been saved.\nTry to login now.");

                        System.out.println("Registeraion Complete!\n");

                        regisStage.close();
                    }
                }
            } else {
                AlertBox.displayAlert("Something went wrong!", "Confirm password is not as same as password.");
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

        Label title2 = new Label("Date of birth");

        HBox sexRow = new HBox(20);
        sexRow.getChildren().addAll(male, female, otherRadio);
        sexRow.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20); //Button Row
        row2.getChildren().addAll(ok, cancel);
        row2.setAlignment(Pos.CENTER);

        VBox column1 = new VBox(20);
        column1.setPadding(new Insets(10)); //add gap 10px
        column1.getChildren().addAll(title, row1, usernameIn, mailIn, row3, title2, date, sexText, sexRow, qText, question, answer, row2);
        column1.setAlignment(Pos.CENTER);
        Scene regScene = new Scene(column1, 360, 600);
        regisStage.setScene(regScene);
        regisStage.setResizable(false);
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
            for (Account account : listAccount) {
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
                            passIn1.getText(), userAccount.getGender(), userAccount.getDateOfBirth(), userAccount.getQuestion(), userAccount.getAnswer(), false));
                    addAccount.remove(userAccount);

                    try {
                        writeFile(user, addAccount);
                    } catch (IOException ex) {
                        System.out.println("Register writeFile " + ex);
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
        return (ArrayList<Account>) in.readObject();
    }

    private void writeFile(File file, ArrayList<Account> listAccount) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(listAccount);
        out.close();
    }

    //Verify email address bab easy
    //.contains = have that string in email
    //catch case dai pra marn nee
    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.contains(" ") || email.contains(";") || email.contains(",") || email.contains("..")
                || email.length() <= 12 || email.length() > 64);
    }

    public static Stage getStage() {

        return stage;
    }

}
