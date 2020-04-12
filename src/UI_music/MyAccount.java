/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author HCARACH
 */
public class MyAccount {

    BorderPane editPane = new BorderPane();
    BorderPane changePane = new BorderPane();
    
    File user = new File("src/data/user.dat");
    
    Account myAccount;
    ArrayList<Account> listAccount = new ArrayList<>();
    
    LocalDate dOB ;
    
    VBox editBox = new VBox(10);
    VBox changeBox = new VBox(10);
    VBox leftBox = new VBox(10);
    HBox bottomBox = new HBox();
    
    //TextFiled
    TextField username = new TextField();
    TextField firstname = new TextField();
    TextField lastname = new TextField();
    TextField email = new TextField();
    TextField currentPassword = new PasswordField();
    TextField password = new PasswordField();
    TextField passwordConfirm = new PasswordField();
    
    ToggleGroup sexToggle = new ToggleGroup(); //create radio button group
    RadioButton male;
    RadioButton female;
    RadioButton otherRadio;
    
    boolean changeStatus = false;
    
    public MyAccount(Account myAccount) {
        this.myAccount = myAccount;
                
            VBox row = new VBox(10);
            row.getChildren().addAll(new Text("Username : \t"), new Label(myAccount.getUsername()));
            
        
            HBox row2 = new HBox(10);
            row2.getChildren().addAll(new Text("First Name : \t\t\t    "), new Text("Last Name : \t"));
        
            HBox row3 = new HBox(5);
            row3.getChildren().addAll(new Label(myAccount.getName()),new Label(myAccount.getSurname()));
        
            VBox row5 = new VBox(10);
            row5.getChildren().addAll(new Text("Email : \t\t"), new Label(myAccount.getEmail()));
            
            HBox row7 = new HBox(10);
            row7.getChildren().addAll(new Text("Date of Birth : "), new Label());
        
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
            row12.getChildren().add(new Text(" "));
            HBox row14 = new HBox(10);
            row14.getChildren().add(new Text(" "));            
        
        editBox.getChildren().addAll(row, row1, row2, row3, row4, row5, row6, row7, row8,row9, row10);        
        editBox.setPadding(new Insets(50, 50, 50, 50));
        editBox.setStyle("-fx-background-color: #f5deb3");
    }

    public VBox getEditBox() {
        return editBox;
    }
    
    private void editAccount() {
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
            row3.getChildren().addAll(firstname , lastname);
        
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
        
        if(myAccount.getGender( ).equals("Male"))
            sexToggle.selectToggle(male);
        else if(myAccount.getGender( ).equals("Female"))
            sexToggle.selectToggle(female);
        else
            sexToggle.selectToggle(otherRadio);
        
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
            
        
        editBox.getChildren().addAll(row, row1, row2, row3, row4, row5, row6, row7, row8,row9, row10,row11, row12, changePassword);        
        editBox.setPadding(new Insets(50, 50, 50, 50));
        editBox.setStyle("-fx-background-color: #f5deb3");
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
            ArrayList<Account> addAccount = new ArrayList<>();
        
        try {
            listAccount = readFile(user);
        } catch (Exception e) {
            System.out.println("Read File error: " + e);
        }
        
        boolean uniqueID = true;
            //Check already username / email
            for (Account account : listAccount) {
                String userId = username.getText(), emailID = email.getText();
                String chkUser = account.getUsername(), chkEmail = account.getEmail();
                if (( (userId.equals(chkUser) && !userId.equals(myAccount.getUsername())) || ( emailID.equals(chkEmail) && !emailID.equals(myAccount.getEmail())) )) {
                    uniqueID = false;
                    break;
                }
            }
            if (uniqueID == false) {
                AlertBox.displayAlert("Something went wrong!", "Email / username is already exists.");
            } //Check comfirm password is the same as password will call error password
            else if (currentPassword.getText().equals(myAccount.getPassword()) && password.getText().equals(passwordConfirm.getText())) {
                //Check if it has Blank Field will call error blank field
                if (firstname.getText().isBlank() || lastname.getText().isBlank() || username.getText().isBlank()
                        || email.getText().isBlank() ) {
                    AlertBox.displayAlert("Something went wrong!", "Please check all the form.\nAnd make sure it was filled.");
                } else {
                    //Check Date of Birth
                    if (dOB.isAfter(LocalDate.now())) { // checkdate if user born after present
                        System.out.println("User is come from the future.");
                        AlertBox.displayAlert("Something went wrong!", "Please check a date field again.\n"
                                + "Make sure that's your date of birth.");
                    }
                    //Check email if it's not will call error email [ see function isEmail for more information]
                    else if (!isEmail(email.getText())) {
                        AlertBox.displayAlert("Something went wrong!", "Please use another email.");

                    } else {
                        
                        ArrayList<Account> changeAccount = new ArrayList<>();
                        
                        if(changeStatus)
                                myAccount.setPassword(this.password.getText());

                        for (Account account : listAccount) {
                            
                            if(account.getUsername().equals(myAccount.getUsername())) {
                                Account newAccount = new Account(firstname.getText(), lastname.getText(), username.getText(), email.getText(),
                                        myAccount.getPassword(), userGender, dOB, myAccount.getQuestion(), myAccount.getAnswer(), myAccount.getIsAdmin());
                                changeAccount.add(newAccount);
                                myAccount = newAccount;
                            } else {
                                changeAccount.add(account);
                            }
                        }

                        try {
                            writeFile(user, changeAccount);
                            System.out.println("Saving account.");
                        } catch (IOException ex) {
                            System.out.println("Register writeFile " + ex);
                        }
                        try {
                            listAccount = readFile(user);
                        } catch (Exception ex) {
                            System.out.println("Error: " + ex);
                        }

                        System.out.println("Edit Profile Complete!\n");
                        
                        editSave = true;
                    }
                }
            } else if(!currentPassword.getText().equals(myAccount.getPassword())){
                AlertBox.displayAlert("Something went wrong!", "Confirm password is not correct.");
            } else {
                AlertBox.displayAlert("Something went wrong!", "Confirm password is not as same as password.");
            }
        
            changeStatus = false;
        return editSave;
    }

    public Account getMyAccount() {
        return myAccount;
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
    
    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.contains(" ") || email.contains(";") || email.contains(",") || email.contains("..")
                || email.length() <= 12 || email.length() > 64);
    }
    
}