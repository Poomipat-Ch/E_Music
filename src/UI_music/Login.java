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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author poomi
 */
public class Login {

    private Scene scene1;
    
    public static Stage stage;
    static Stage fgtStage;
    
    private double mouse_x = 0, mouse_y = 0; // position mouse
    private File user = new File("src/data/user.dat");
    private File tempId = new File("src/data/user.txt");

    private ArrayList<Account> listUserAccount = new ArrayList<>();
    private Account userAccount = new Account();
    private Account guestAccount = new Account("guest", "guest", "guest", "guest@gmail.com", "guest", "N/A", LocalDate.now(), "guest", "guest", "guest", new Image("/image/defaultprofile.png"));
    private ArrayList<Account> addAccount = new ArrayList<>();
    

    public Login(Stage stage) throws FileNotFoundException, IOException, ClassNotFoundException {

//////**READ THIS***** This save in user.dat file, if you can't run admin, try run this code only once and comment again
//        listUserAccount.add(new Account("admin", "admin", "admin", "admin@gmail.com", "admin", "N/A", LocalDate.now(), "admin", "admin", "admin", new Image("/image/defaultprofile.png")));
//        writeFile(user, listUserAccount); 

        Login.stage = stage;

        Label title1 = new Label("ยินดีต้อนรับ");
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
        chk1.getStyleClass().add("check-box");

        //init read file
        //Login Action Event
        Button loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("loginBtn");
        loginBtn.setOnAction(e -> {

            
        readFileAccount();    

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
        forgotBtn.getStyleClass().add("forgotBtn");
        forgotBtn.setOnAction(e -> {
            System.out.println("User forgot password.");
            forgetPassword();
        });

        Button registerBtn = new Button("Create a new account");
        registerBtn.getStyleClass().add("registerBtn");
        registerBtn.setOnAction(e -> {
            System.out.println("User want to register.");
            register();
        });
        
        //Guest Button -> Login with Guest
        Button guestBtn = new Button("Login with guest");
        guestBtn.getStyleClass().add("guestLogin");
        guestBtn.setOnAction(e->{
            System.out.println("User login with guest ");
            Login.stage.hide();
            Guest_UI guest_UI = new Guest_UI(new Stage(), guestAccount);
        });

        VBox rightMenu = new VBox(20);
        rightMenu.setMargin(registerBtn, new Insets(0, 0, 30, 0));
        rightMenu.getStyleClass().add("loginLable");
        rightMenu.setMinSize(350, 600);
        rightMenu.getChildren().addAll( exitButton(0),title1, idLabel, idInput, passLabel, passInput, chk1, loginBtn, forgotBtn, guestBtn, registerBtn);
        rightMenu.setAlignment(Pos.CENTER);
        
        Label backgroundRightMenu = new Label();
        backgroundRightMenu.setMinSize(350, 600);
        backgroundRightMenu.getStyleClass().add("backgroundLoginLabel");
        
        AnchorPane anchorpane = new AnchorPane();
        anchorpane.getChildren().addAll(backgroundRightMenu, rightMenu);
        
        BorderPane borderPane = new BorderPane();
        
        BorderPane.setAlignment(anchorpane, Pos.CENTER);
        //BorderPane.setMargin(rightMenu, new Insets(15, 15, 15, 15));
        borderPane.setRight(anchorpane);
        borderPane.getStyleClass().add("backgroundImage");
        
        borderPane.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        borderPane.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - mouse_x);
            stage.setY(e.getScreenY() - mouse_y);
        });
        
        scene1 = new Scene(borderPane, 900, 600);

        scene1.getStylesheets().add(getClass().getResource("/style_css/styleLogin.css").toExternalForm());

        stage.setScene(scene1);
        stage.setTitle("Spookify - E-Music");

        stage.setOnCloseRequest(e -> {
            e.consume();
            boolean ans = AlertBox.display("Close Program", "Are you sure to exit?");
            if (ans) {
                stage.close();
            }
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }
    
     private Button exitButton(int mode) {

        ImageView exit_icon = new ImageView(new Image("/icon/close-512-detail.png"));
        ImageView exit_hover_icon = new ImageView(new Image("/icon/close-512_hover.png"));
        exit_icon.setFitWidth(15);
        exit_icon.setFitHeight(15);
        exit_hover_icon.setFitWidth(15);
        exit_hover_icon.setFitHeight(15);
        
        Button exit = new Button("", exit_icon);
        exit.setOnMouseEntered(e -> {
            exit.setGraphic(exit_hover_icon);
        });
        exit.setOnMouseExited(e -> {
            exit.setGraphic(exit_icon);
        });
        exit.setOnMouseClicked(e -> {
            if(mode == 0)stage.close();
            else if(mode == 1 || mode == 2)fgtStage.close();
                        
        });
        exit.setStyle("-fx-background-color : transparent;");
        exit.setPadding(Insets.EMPTY);
        if(mode == 0)exit.setTranslateX(145);
        else if(mode == 1 )exit.setTranslateX(370);
        else if(mode == 2 )exit.setTranslateX(200);
        exit.setTranslateY(0);

        return exit;
    }

    public void register() {
        //StringProperty name, surname, mail, password, sex;
        new Register("member");
    }

    public void forgetPassword() {
        readFileAccount();
        
        fgtStage = new Stage();
        fgtStage.initModality(Modality.APPLICATION_MODAL);
        fgtStage.initStyle(StageStyle.TRANSPARENT);

        Label title = new Label("Find your account");
        title.getStyleClass().add("titlePremium");
        Label descrip = new Label("Enter your email address to find your account.");
        descrip.getStyleClass().add("detailPremiumSmall");
        TextField mailIn = new TextField();
        mailIn.getStyleClass().add("detailPremiumTextFill");
        mailIn.setPromptText("e.g. spookify@gmail.com");

        Label title2 = new Label("Reset Password");
        title2.getStyleClass().add("titlePremium");
        Label askQ = new Label("Question: " + userAccount.getQuestion());
        askQ.getStyleClass().add("detailPremium");
        TextField answer = new TextField();
        answer.getStyleClass().add("detailPremiumTextFill");
        answer.setPromptText("Type Answer here");
        TextField passIn1 = new PasswordField();
        passIn1.getStyleClass().add("detailPremiumTextFill");
        passIn1.setPromptText("New Password.");
        TextField passIn2 = new PasswordField();
        passIn2.getStyleClass().add("detailPremiumTextFill");
        passIn2.setPromptText("Re-enter Password.");
        Button resetBtn = new Button("Reset Password");
        resetBtn.getStyleClass().add("savebtn");

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(exitButton(2), title2, askQ, answer, passIn1,passIn2, resetBtn);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("allPane");
        vBox.setPadding(new Insets(30));
        vBox.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        vBox.setOnMouseDragged(e -> {
            fgtStage.setX(e.getScreenX() - mouse_x);
            fgtStage.setY(e.getScreenY() - mouse_y);
        });
        Scene newPassScene = new Scene(vBox, 450, 300);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        newPassScene.getStylesheets().add(stylrSheet); // CSS
        newPassScene.setFill(Color.TRANSPARENT);



        Button ok = new Button("Find");
        ok.getStyleClass().add("savebtn");
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
                    readFileAccount();
                    ArrayList<Account> addAccount = listUserAccount;
                    

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
                else{
                    AlertBox.displayAlert("Error", "Sorry, Please check you password again.");
                }
            } else {
                System.out.println("Answer not correct.");
                AlertBox.displayAlert("Error", "Sorry, Your answer is not correct.");
            }
        });

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("cancelbtn");
        cancel.setOnAction(e -> {
            System.out.println("Canceling finding password.");
            fgtStage.close();
        });
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(ok, cancel);
        hbox.setAlignment(Pos.CENTER);
        VBox box1 = new VBox(10);
        box1.setPadding(new Insets(30));
        box1.getChildren().addAll(exitButton(1),title, descrip, mailIn, hbox);
        box1.getStyleClass().add("allPane");
        box1.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
        });
        box1.setOnMouseDragged(e -> {
            fgtStage.setX(e.getScreenX() - mouse_x);
            fgtStage.setY(e.getScreenY() - mouse_y);
        });

        Scene forgScene = new Scene(box1, 450, 250);
        String stylrSheet2 = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        forgScene.getStylesheets().add(stylrSheet2); // CSS
        forgScene.setFill(Color.TRANSPARENT);
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

    private void readFileAccount() { //By Pop
        try {
            listUserAccount = new ReadWriteFile().readFile(user);
        } catch (IOException ex) {
            System.out.println("Login : IOExeption readfile in login constructor");
        } catch (ClassNotFoundException ex) {
            System.out.println("Login : ClassNotFoundExeption writefile in Login constructor");
        }
    }

}
