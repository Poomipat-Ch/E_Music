/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;



import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



/**
 *
 * @author poomi
 */
public class User_UI extends UI{
    
   // SearchSystem searchSystem = new SearchSystem();
    SearchSystem searchSystemMain = new SearchSystem();
    SearchSystem searchSystemMyLibrary = new SearchSystem();
    public User_UI(Stage stage) {
        super(stage);
    }

    @Override
    public AnchorPane allSongPane() {
       AnchorPane pane = new AnchorPane();
       pane.setMinHeight(760);
       pane.setMaxHeight(Double.MAX_VALUE);
       pane.getStyleClass().add("bg-2");
       AnchorPane img = new AnchorPane();
            img.setPrefSize(300, 400);
            img.setLayoutX(1030-300-20);
            img.setLayoutY(20);
            Image imageAll = new Image("/image/Music_pic.jpg");
            ImageView imgAll = new ImageView(imageAll);
            img.getChildren().add(imgAll);

            Button priceButton = CreaButton("Buy");
            priceButton.setLayoutX(1030-250-20);
            priceButton.setLayoutY(420+20);

            pane.getChildren().addAll(img,priceButton,tableMusic(),searchBoxAll());

        return pane;
    }
    
        @Override
    public AnchorPane mySongPane() {
       AnchorPane pane = new AnchorPane();
       pane.setMinHeight(760);
       pane.setMaxHeight(Double.MAX_VALUE);
       pane.getStyleClass().add("bg-2");
       AnchorPane img = new AnchorPane();
            img.setPrefSize(300, 400);
            img.setLayoutX(1030-300-20);
            img.setLayoutY(20);
     
            Image imageMy = new Image("/image/Music_pic.jpg");
            ImageView imgMy = new ImageView(imageMy);
            img.getChildren().add(imgMy);

            Button downloadBtn = CreaButton("Download");
            downloadBtn.setLayoutX(1030-250-20);
            downloadBtn.setLayoutY(420+20);

            pane.getChildren().addAll(img,downloadBtn,tableMyMusic(),searchBoxMy());

        return pane;
    }
    
    
    private Button CreaButton(String text){
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setMinSize(200, 50);
        
        
        return downLoadButton;
        
    }
    
    
    private AnchorPane tableMusic(){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(1030-300-60, 700);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(100);
        

      TableView<UserAccount> table = new TableView<>();
      table.setEditable(true);
      table.setMinSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());
 
//      searchSystem.getTableView().setOnMouseClicked((event) -> {
//        if(event.getButton().equals(MouseButton.PRIMARY)){
//            System.out.println(table.getSelectionModel().getSelectedItem().getUserName());
//        }
//      });
      
      
       // Create column UserName (Data type of String).
      TableColumn<UserAccount, String> userNameCol = new TableColumn<>("Name Song");
      userNameCol.setMinWidth(250);

 
      // Create column Email (Data type of String).
      TableColumn<UserAccount, String> emailCol = new TableColumn<>("Email");
      emailCol.setMinWidth(200);
 
      // Create column FullName (Data type of String).
      TableColumn<UserAccount, String> fullNameCol = new TableColumn<>("Full Name");
      
      // Create 2 sub column for FullName.
      TableColumn<UserAccount, String> firstNameCol = new TableColumn<>("First Name");
      firstNameCol.setMinWidth(110);
 
      TableColumn<UserAccount, String> lastNameCol = new TableColumn<>("Last Name");
      lastNameCol.setMinWidth(110);
 
      // Add sub columns to the FullName
      fullNameCol.getColumns().addAll(firstNameCol, lastNameCol);
 
 
      // Defines how to fill data for each cell.
      // Get value from property of UserAccount. .
      userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
      emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
      firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
      lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    
      // Set Sort type for userName column
      userNameCol.setSortType(TableColumn.SortType.DESCENDING);
      lastNameCol.setSortable(false);
 
      // Display row data
      ObservableList<UserAccount> list = UserAccount.getMusicList();
      FilteredList<UserAccount> filterData = new FilteredList<>(list, b -> true);
      searchSystemMain.setFilterData(filterData);
        
      SortedList<UserAccount> sortedList = new SortedList<>(searchSystemMain.getFilterData());
      sortedList.comparatorProperty().bind(table.comparatorProperty());
      table.setItems(sortedList);
 
      table.getColumns().addAll(userNameCol, emailCol, fullNameCol);
        
        
        anchorPane.getChildren().add(table);
        
        return anchorPane;
    }
    
    private AnchorPane tableMyMusic(){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(1030-300-60, 700);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(100);
        
        

      TableView<UserAccount> table = new TableView<>();
      table.setEditable(true);
      table.setMinSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());
 
      table.setOnMouseClicked((event) -> {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            System.out.println(table.getSelectionModel().getSelectedItem().getUserName());
        }
      });
      
      
      // Create column UserName (Data type of String).
      TableColumn<UserAccount, String> userNameCol = new TableColumn<>("Name Song");
      userNameCol.setMinWidth(250);

 
      // Create column Email (Data type of String).
      TableColumn<UserAccount, String> emailCol = new TableColumn<>("Email");
      emailCol.setMinWidth(200);
 
      // Create column FullName (Data type of String).
      TableColumn<UserAccount, String> fullNameCol = new TableColumn<>("Full Name");
      
      // Create 2 sub column for FullName.
      TableColumn<UserAccount, String> firstNameCol = new TableColumn<>("First Name");
      firstNameCol.setMinWidth(110);
 
      TableColumn<UserAccount, String> lastNameCol = new TableColumn<>("Last Name");
      lastNameCol.setMinWidth(110);
 
      // Add sub columns to the FullName
      fullNameCol.getColumns().addAll(firstNameCol, lastNameCol);
 
 
      // Defines how to fill data for each cell.
      // Get value from property of UserAccount. .
      userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
      emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
      firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
      lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    
      // Set Sort type for userName column
      userNameCol.setSortType(TableColumn.SortType.DESCENDING);
      lastNameCol.setSortable(false);
      
 
      // Display row data
      ObservableList<UserAccount> list = UserAccount.getMyMusicList();
      FilteredList<UserAccount> filterData = new FilteredList<>(list, b -> true);
      searchSystemMyLibrary.setFilterData(filterData);
        
      SortedList<UserAccount> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
      sortedList.comparatorProperty().bind(table.comparatorProperty());
      table.setItems(sortedList);
      
 
      table.getColumns().addAll(userNameCol, emailCol, fullNameCol);
        
        
        
        anchorPane.getChildren().addAll(table);
        
        return anchorPane;
    }
    
    @Override
    public HBox searchBoxAll(){
        HBox hBox = new HBox();
        hBox.setMinSize(1030-300-60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);
        
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030-300-60-70, 30);
        
        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0,0,0,10));
        
        searchTextField.textProperty().addListener(searchSystemMain);
        
        hBox.getChildren().addAll(searchTextField,searchButton);
        
        return hBox;
    }

    @Override
    public HBox searchBoxMy(){
        HBox hBox = new HBox();
        hBox.setMinSize(1030-300-60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);
        
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030-300-60-70, 30);
        
        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0,0,0,10));
        
        searchTextField.textProperty().addListener(searchSystemMyLibrary);

        hBox.getChildren().addAll(searchTextField,searchButton);
        
        return hBox;
    }
    

   
    
 }


