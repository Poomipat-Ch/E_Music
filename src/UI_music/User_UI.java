/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI_music;

import Component_Music.SearchSystem;
import Component_Music.Song;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author poomi
 */
public class User_UI extends UI {

    // SearchSystem searchSystem = new SearchSystem();
    SearchSystem searchSystemMain = new SearchSystem();
    SearchSystem searchSystemMyLibrary = new SearchSystem();

    public User_UI(Stage stage) {
        super(stage);
        Scene scene = new Scene(allPane(), 1280, 960);
        String stylrSheet = getClass().getResource("/style_css/style.css").toExternalForm();
        scene.getStylesheets().add(stylrSheet);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public AnchorPane allSongPane() {
        AnchorPane pane = new AnchorPane();

        pane.setMinHeight(760);
        pane.setMaxHeight(Double.MAX_VALUE);
        pane.getStyleClass().add("bg-2");
        AnchorPane img = new AnchorPane();
        img.setPrefSize(300, 400);
        img.setLayoutX(1030 - 300 - 20);
        img.setLayoutY(20);
        Image imageAll = new Image("/image/Music_pic.jpg");
        ImageView imgAll = new ImageView(imageAll);
        img.getChildren().add(imgAll);

        Button priceButton = CreaButton("Buy");
        priceButton.setLayoutX(1030 - 250 - 20);
        priceButton.setLayoutY(420 + 20);

        pane.getChildren().addAll(img, priceButton, AllSong(), searchBoxAll());

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
        img.setLayoutX(1030 - 300 - 20);
        img.setLayoutY(20);

        Image imageMy = new Image("/image/Music_pic.jpg");
        ImageView imgMy = new ImageView(imageMy);
        img.getChildren().add(imgMy);

        Button downloadBtn = CreaButton("Download");
        downloadBtn.setLayoutX(1030 - 250 - 20);
        downloadBtn.setLayoutY(420 + 20);

        pane.getChildren().addAll(img, downloadBtn, tableMyMusic(), searchBoxMy());

        return pane;
    }

    private Button CreaButton(String text) {
        Button downLoadButton = new Button(text);
        downLoadButton.getStyleClass().add("detailbtn");
        downLoadButton.setMinSize(200, 50);

        return downLoadButton;

    }

    private AnchorPane tableMusic() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(1030 - 300 - 60, 700);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(100);

        TableView<Song> table = new TableView<>();
        table.setEditable(true);
        table.setMinSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

//      searchSystem.getTableView().setOnMouseClicked((event) -> {
//        if(event.getButton().equals(MouseButton.PRIMARY)){
//            System.out.println(table.getSelectionModel().getSelectedItem().getUserName());
//        }
//      });
        // Create column UserName (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("Name Song");
        NameCol.setMinWidth(250);

        // Create column Email (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        artistCol.setMinWidth(200);

        // Create column FullName (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("Detail");
        detailCol.setMinWidth(220);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));

        // Set Sort type for userName column
        NameCol.setSortType(TableColumn.SortType.DESCENDING);
        detailCol.setSortable(false);

        // Display row data
        ObservableList<Song> list = Song.getMusicList();
        FilteredList<Song> filterData = new FilteredList<>(list, b -> true);
        searchSystemMain.setFilterData(filterData);

        SortedList<Song> sortedList = new SortedList<>(searchSystemMain.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        table.getColumns().addAll(NameCol, artistCol, detailCol);

        anchorPane.getChildren().add(table);

        return anchorPane;
    }

    private AnchorPane tableMyMusic() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(1030 - 300 - 60, 700);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(100);

        TableView<Song> table = new TableView<>();
        table.setEditable(true);
        table.setMinSize(anchorPane.getMinWidth(), anchorPane.getMinHeight());

        table.setOnMouseClicked((event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                System.out.println(table.getSelectionModel().getSelectedItem().getNameSong());
            }
        });

        // Create column UserName (Data type of String).
        TableColumn<Song, String> NameCol = new TableColumn<>("Name Song");
        NameCol.setMinWidth(250);

        // Create column Email (Data type of String).
        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        artistCol.setMinWidth(200);

        // Create column FullName (Data type of String).
        TableColumn<Song, String> detailCol = new TableColumn<>("Detail");
        detailCol.setMinWidth(220);

        // Defines how to fill data for each cell.
        // Get value from property of UserAccount. .
        NameCol.setCellValueFactory(new PropertyValueFactory<>("nameSong"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artistSong"));
        detailCol.setCellValueFactory(new PropertyValueFactory<>("detailSong"));

        // Set Sort type for userName column
        NameCol.setSortType(TableColumn.SortType.DESCENDING);
        detailCol.setSortable(false);

        // Display row data
        ObservableList<Song> list = Song.getMyMusicList();
        FilteredList<Song> filterData = new FilteredList<>(list, b -> true);
        searchSystemMyLibrary.setFilterData(filterData);

        SortedList<Song> sortedList = new SortedList<>(searchSystemMyLibrary.getFilterData());
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        table.getColumns().addAll(NameCol, artistCol, detailCol);

        anchorPane.getChildren().addAll(table);

        return anchorPane;
    }

    @Override
    public HBox searchBoxAll() {
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMain);

        hBox.getChildren().addAll(searchTextField, searchButton);

        return hBox;
    }

    @Override
    public HBox searchBoxMy() {
        HBox hBox = new HBox();
        hBox.setMinSize(1030 - 300 - 60, 30);
        hBox.setLayoutX(20);
        hBox.setLayoutY(60);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search Music");
        searchTextField.setMinSize(1030 - 300 - 60 - 70, 30);

        Button searchButton = CreaButton("Search");
        searchButton.setStyle("-fx-font-size : 15px;");
        searchButton.setMinSize(50, 30);
        HBox.setMargin(searchButton, new Insets(0, 0, 0, 10));

        searchTextField.textProperty().addListener(searchSystemMyLibrary);

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
        scrollPane.setStyle("-fx-background-color: #e9967a");
        
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
                vbox.getChildren().addAll(imageView, new Text("Wahn Goey Dteun"), new Text("ARTIST : GUNGUN"));
                vbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(vbox);
            }
            hbox.setAlignment(Pos.CENTER);
            totalbox.getChildren().addAll(hbox);
        }
        
        totalbox.setAlignment(Pos.CENTER);
        scrollPane.setContent(totalbox);
        
        return scrollPane;
    }

}
