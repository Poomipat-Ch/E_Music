/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author HCARACH
 */
public class SearchPage {
    
    AnchorPane anchorPane;

    public SearchPage() {
        anchorPane = new AnchorPane();
        anchorPane.setMinSize(990, 900);
        anchorPane.setLayoutX(-3);
        anchorPane.setLayoutY(-3);
        anchorPane.getStyleClass().add("mainBox");
        
        AnchorPane background = new AnchorPane();
        background.getStyleClass().add("backgroundsearch");
        background.setPrefSize(1030, 901);
        background.setPadding(Insets.EMPTY);
        background.setLayoutX(-1);
        background.setLayoutY(1);
        
        HBox hBox = new HBox();
        hBox.setPrefSize(1030, 30);
        hBox.setAlignment(Pos.CENTER);
        hBox.setLayoutY(100);

        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Search");
        searchTextField.setStyle("-fx-font-size: 18px;");
        searchTextField.setPrefSize(1030-300-60-70, 30);
        
        hBox.getChildren().addAll(searchTextField);
        
        anchorPane.getChildren().addAll(background,hBox, FoundListPane());
        
    }
    
    public AnchorPane getSearchPane() {
        return anchorPane;
    }
    
    private BorderPane FoundListPane() {
        BorderPane borderpane = new BorderPane();
        borderpane.getStyleClass().add("backgroundsearch");
        
        borderpane.setPrefWidth(970);
        borderpane.setMinHeight(602);
        borderpane.setLayoutX(30);
        borderpane.setLayoutY(300);
        
        borderpane.setLeft(PlaylistPane("Songs","music.dat"));
        
        return borderpane;
    }
    
    private BorderPane PlaylistPane(String string,String filename) {
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(10,20,0,20));
        
        borderpane.setPrefWidth(485);
        borderpane.setMinHeight(602);
        borderpane.setLayoutX(30);
        borderpane.setLayoutY(300);
        
        borderpane.setTop(HeadBox(string, filename));
        
        return borderpane;
    }
    
    private HBox HeadBox(String string, String filename) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("hbox");
        Label seeall = CreateSeeAll(filename);
        HBox.setHgrow(seeall,Priority.ALWAYS);
        
        hbox.getChildren().addAll(CreateLabel(string), seeall);
        
        return hbox;
    }
    
    private Label CreateLabel(String string) {
        Label label = new Label(string);
        label.getStyleClass().add("headlabel");
        label.setAlignment(Pos.CENTER_LEFT);
        
        return label;
    }
    
    private Label CreateSeeAll(String string) {
        Label label = new Label("SEE ALL");
        label.setAlignment(Pos.CENTER_RIGHT);
        label.getStyleClass().add("seealllabel");
        
        label.setOnMouseClicked(event -> {
                
            });
        
        return label;
    }
    
    
}
