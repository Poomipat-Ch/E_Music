/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.Account;
import Component_Music.AlertBox;
import Component_Music.SearchSystem;
import Component_Music.SearchSystemAccount;
import Component_Music.Song;
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
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Sirawit
 */
public class Admin_UI extends UI{
    
    LocalDate dOB;
    File user = new File("src/data/user.dat");
    boolean dateSet = false;   
    ArrayList<Account> listAccount = new ArrayList<>(); 
    ArrayList<Account> addAccount = new ArrayList<>();
    
    ObservableList<Account> list = null; 
    
    SearchSystem searchSystemMain = new SearchSystem();
    SearchSystemAccount searchAccount = new SearchSystemAccount();

    TableView<Account> table;
    

    public Admin_UI(Stage stage) {
        super(stage);
        Scene scene = new Scene(allPane2(), 1280, 960);
        String stylrSheet = getClass().getResource("/style_css/style.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    private Button CreaButton(String text) {
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setMinSize(200, 50);

        return downLoadButton;

    }
    
    @Override
    public AnchorPane allSongPane() {
        AnchorPane pane = new AnchorPane();
        
        Label title1 = new Label("Welcome to Administrative Page!");
        title1.getStyleClass().add("titleAdmin");
        title1.setLayoutX(50);
        title1.setLayoutY(5);

        //        pane.setMinHeight(760);
        //        pane.setMaxHeight(Double.MAX_VALUE);
        //        pane.getStyleClass().add("bg-2");
        //        AnchorPane img = new AnchorPane();
        //        img.setPrefSize(300, 400);
        //        img.setLayoutX(1030 - 300 - 20);
        //        img.setLayoutY(20);
        //        Image imageAll = new Image("/image/Music_pic.jpg");
        //        ImageView imgAll = new ImageView(imageAll);
        //        img.getChildren().add(imgAll);
        //
        //        Button priceButton = CreaButton("Buy");
        //        priceButton.setLayoutX(1030 - 250 - 20);
        //        priceButton.setLayoutY(420 + 20);

        pane.getChildren().addAll(AllSong(),title1);

        return pane;   
    }

    @Override
    public AnchorPane mySongPane() {    //Accounts Page 2
        AnchorPane pane = new AnchorPane();
        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);
        pane.getStyleClass().add("bg-2");
        
        Label title2 = new Label("Account Management System");
        title2.getStyleClass().add("titleAdmin");
        title2.setLayoutX(50);
        title2.setLayoutY(5);
        
        Button addAccountBtn = CreaButton("Add Account");
        addAccountBtn.setLayoutX(520);
        addAccountBtn.setLayoutY(675); 
        addAccountBtn.setOnAction(e -> {
            register(null);
            refreshTable();
        });

        Button deleteAccountBtn = CreaButton("Delete Account");
        deleteAccountBtn.setLayoutX(750);
        deleteAccountBtn.setLayoutY(675);
        deleteAccountBtn.setOnAction(e -> {
            
            try {
                deleteAccountClicked();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Admin_UI.class.getName()).log(Level.SEVERE, null, ex);
            }     
            refreshTable();
        });
        
        pane.getChildren().addAll(addAccountBtn, deleteAccountBtn, tableAccount(), searchBoxMy(),title2);

        return pane;    
    }
     
    private AnchorPane tableAccount() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(925, 500); //1030 - 300 - 60, 700
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(150);
        
        table = new TableView<>();
        
        table.setEditable(true);
        table.setPrefSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(table.getSelectionModel().getSelectedItem().getName()); //.getNameSong()
                
            }
        });

        // Create column Name (Data type of String).
        TableColumn<Account, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(150);

        // Create column Surname (Data type of String).
        TableColumn<Account, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setMinWidth(150);

        // Create column Username (Data type of String).
        TableColumn<Account, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setMinWidth(150);
        
        // Create column Email (Data type of String).
        TableColumn<Account, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(280);
        
        // Create column Gender (Data type of String).
        TableColumn<Account, String> genderCol = new TableColumn<>("Gender");
        genderCol.setMinWidth(30);
        genderCol.setMaxWidth(60);
        
        // Create column DoB (Data type of LocalDate).
        TableColumn<Account, LocalDate> dobCol = new TableColumn<>("DoB");
        dobCol.setMinWidth(60);
        
        // Create column isAdmin (Data type of Boolean).
        TableColumn<Account, Boolean> adminCol = new TableColumn<>("Admin");
        adminCol.setMinWidth(50);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        adminCol.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
       
        
        // Set Sort type for userName column
        nameCol.setSortType(TableColumn.SortType.DESCENDING);

        // Display row data       
        refreshTable(); // get.list account-> sorted ** see function below
        
        table.getColumns().addAll(nameCol, surnameCol, usernameCol, emailCol, genderCol, dobCol, adminCol);
        anchorPane.getChildren().addAll(table);

        return anchorPane;
    }
    

    @Override
    public HBox searchBoxAll() { // All Song first page
       
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(600, 30); //1030 - 300 - 60 - 70

        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMain);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
        
    }

    @Override
    public HBox searchBoxMy() {  // All Song Second page
        HBox hBox = new HBox();
        hBox.setMinSize(670, 30); //1030 - 300 - 60
        hBox.setLayoutX(20);
        hBox.setLayoutY(100);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Account");
        searchTextField.setMinSize(850, 32); //1030 - 300 - 60 - 70

        Button searchButton = CreaButton("Refresh");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 32);
        searchButton.setOnAction(e->{
            searchTextField.clear();
            list.removeAll();
            refreshTable();
            System.out.println("reFresh Table");
        });
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchAccount);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }
    
    ScrollPane scrollPane;
    VBox vbox;
    HBox hbox;
    
    private ScrollPane  AllSong(){
        
        vbox = new VBox(10);
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(1030 - 300 - 60, 700);
        //scrollPane.setMinSize(1030-300-60, 700);
        scrollPane.setLayoutX(20);
        scrollPane.setLayoutY(100);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(false);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        scrollPane.setPadding(new Insets(20, 20, 20, 20));
        scrollPane.setBackground(Background.EMPTY);
        
        ImageView imageView;
        
        VBox totalbox = new VBox(30);
        //totalbox.setPadding(new Insets(30, 30, 30, 30));
        
        for(int i = 0; i< 10; ++i) {
            hbox = new HBox(20);
            hbox.setPadding(new Insets(0, 30, 0, 30));
            
            for(int k  = 1 ; k < 4 ; ++k) {
                vbox = new VBox(30);
                vbox.setPadding(new Insets(20, 20, 20, 20));
                
                imageView = new ImageView(new Image("/image/" + k +".jpg"));
                imageView.setFitHeight(160); 
                imageView.setFitWidth(120); 
                
                Button buyButton = new Button("Buy");
                buyButton.setOnMouseClicked(e ->{
                    // Buy fuction wait ->  gut nehee
                });
                
                vbox.getChildren().addAll(imageView, new Text("Love"), new Text("ARTIST : Bodyslam"));
                vbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(vbox);
            }
            hbox.setAlignment(Pos.CENTER);
            totalbox.getChildren().addAll(hbox);
        }
        
        totalbox.setAlignment(Pos.CENTER);
        VBox totalPane = new VBox();
        totalPane.getChildren().addAll(searchBoxAll(),totalbox);
        
        
        scrollPane.setContent(totalPane);
        
        return scrollPane;
    }
    
    public void register(String email) {
        //StringProperty name, surname, mail, password, sex;
        Stage regisStage = new Stage();
        regisStage.initModality(Modality.APPLICATION_MODAL);

        Label title = new Label("New Account");

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
        
        Label adminText = new Label("Administrive");
        ToggleGroup adminToggle = new ToggleGroup(); //create radio button group
        RadioButton userRadio = new RadioButton("User"); //create radio button
        RadioButton adminRadio = new RadioButton("Admin");
        userRadio.setToggleGroup(adminToggle); //Set radio button group
        adminRadio.setToggleGroup(adminToggle);
        adminToggle.selectToggle(userRadio); // Set default = user

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
            
            boolean isAdmin = false;

            //Check admin radioButton which is selected
            if (adminRadio.isSelected()) {
                isAdmin = true;
            } else if (userRadio.isSelected()) {
                isAdmin = false;
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
                                passIn.getText(), userGender, dOB, question.getValue(), answer.getText(), isAdmin));

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
        
        HBox adminRow = new HBox(20);
        adminRow.getChildren().addAll(userRadio, adminRadio);
        adminRow.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20); //Button Row
        row2.getChildren().addAll(ok, cancel);
        row2.setAlignment(Pos.CENTER);

        VBox column1 = new VBox(20);
        column1.setPadding(new Insets(10)); //add gap 10px
        column1.getChildren().addAll(title, adminRow, row1, usernameIn, mailIn, row3, title2, date, sexText, sexRow, qText, question, answer, row2);
        column1.setAlignment(Pos.CENTER);
        Scene regScene = new Scene(column1, 360, 600);
        regisStage.setScene(regScene);
        regisStage.setResizable(false);
        regisStage.setTitle("Registeration.");
        regisStage.showAndWait();

    }
    
    //ReadFile Function from Registor
    private ArrayList<Account> readFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Account>) in.readObject();
    }
    
    //WriteFile Function from Registor
    private void writeFile(File file, ArrayList<Account> listAccount) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(listAccount);
        out.close();
    }
    
    private void refreshTable(){ //get.list -> sorted
        //TRY -CATCH FOR EXCEPTION ... NOTHING TO DO WITH IT
        try {
            list = Account.getAccountList();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Admin_UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Filter for Search and Sorted
        FilteredList<Account> filterData = new FilteredList<>(list, b -> true);
        searchAccount.setFilterData(filterData);
                                                       
        SortedList<Account> sortedList = new SortedList<>(searchAccount.getFilterData()); 
        sortedList.comparatorProperty().bind(table.comparatorProperty()); 
        table.setItems(filterData);  
    }
    
    private void deleteAccountClicked() throws IOException, FileNotFoundException, ClassNotFoundException {
        
        String selectUsername = table.getSelectionModel().getSelectedItem().getUsername();
        String selectEmail = table.getSelectionModel().getSelectedItem().getEmail();
        
        ArrayList<Account> oldAccounts = new ArrayList<>();
        ArrayList<Account> presentAccounts = new ArrayList<>();
        oldAccounts = readFile(user);   
        
        for (Account account : oldAccounts) {
            String chkUser = account.getUsername();
            String chkEmail = account.getEmail();
            if (!(selectEmail.equals(chkEmail) && selectEmail.equals(chkEmail))) {  
                presentAccounts.add(account);
            }
            else{
                System.out.println("delete " + account);
            }
        }
        
        writeFile(user, presentAccounts);

    }
    
    //Verify email address bab easy
    //.contains = have that string in email
    //catch case dai pra marn nee
    private boolean isEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.contains(" ") || email.contains(";") || email.contains(",") || email.contains("..")
                || email.length() <= 12 || email.length() > 64);
    }
    
}
