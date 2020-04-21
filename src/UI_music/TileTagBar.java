package UI_music;


import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sirawit
 */


public class TileTagBar extends TilePane{

    public static void getTag() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private final ObservableList<String> observableTags;
                                       //private final TextField inputTextField;

    public ObservableList<String> getTags() {
        return observableTags;
    }
    
//  TilePane inputTilePane;

    public TileTagBar() {

        
        observableTags = FXCollections.observableArrayList();

        setPadding(new Insets(2));
        setVgap(5);
        setHgap(5);
        setBackground(null);
        
        //FOR TEXTFIELD FILL IN THAT WE DO NOT NEED IT ANY MORE BUT KEEP IT IS OK...
                                        //        inputTextField = new TextField();
                                        //        inputTextField.setOnAction(evt -> {
                                        //            String text = inputTextField.getText();
                                        //            
                                        //            if (!text.isEmpty() && !observableTags.contains(text)) {
                                        //                observableTags.add(text);
                                        //                inputTextField.clear();
                                        //            }
                                        //        });

                                        //inputTextField.prefHeightProperty().bind(this.heightProperty());


                                        //HBox.setHgrow(inputTextField, Priority.ALWAYS);
                                        //inputTextField.setBackground(null);
       

        //This is Listener... from Observable String. 
        observableTags.addListener((ListChangeListener.Change<? extends String> change) -> {
            while (change.next()) {
                if (change.wasPermutated()) {
                    ArrayList<Node> newSublist = new ArrayList<>(change.getTo() - change.getFrom());
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.add(null);
                    }
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.set(change.getPermutation(i), getChildren().get(i));
                    }
                    getChildren().subList(change.getFrom(), change.getTo()).clear();
                    getChildren().addAll(change.getFrom(), newSublist);
                } else {
                    if (change.wasRemoved()) {
                        getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                    }
                    if (change.wasAdded()) {
                        getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).collect(Collectors.toList()));
                    }
                }
            }
        });
                                         //getChildren().add(inputTextField);
        //setContent(inputTilePane);
    }

    private class Tag extends Button {

        public Tag(String stringTag) {
            getStyleClass().add("tagbtn");
            Label text = new Label(stringTag);
            text.getStyleClass().add("detailTag");
            text.setMaxHeight(5);
            
            HBox innerTag = new HBox(5);
            //innerTag.setMargin(text, new Insets(0, 0, 0, 5));
            //innerTag.setAlignment(Pos.TOP_CENTER);
            innerTag.getChildren().addAll(text, exitButton(stringTag));     //<<- TAG
            setGraphic(innerTag);
            setMaxHeight(10); // By Pop
            setMinWidth(60); // By Pop
            //setPadding(new Insets(5));
            //setMinWidth(200);
        }
        private Button exitButton(String stringTag) {

            ImageView exit_icon = new ImageView(new Image("/icon/close-512-detail.png"));
            ImageView exit_hover_icon = new ImageView(new Image("/icon/close-512_hover.png"));
            exit_icon.setFitWidth(14);
            exit_icon.setFitHeight(14);
            exit_hover_icon.setFitWidth(14);
            exit_hover_icon.setFitHeight(14);

            Button exit = new Button();
            exit.setGraphic(exit_icon);
            exit.setOnMouseEntered(e -> {
                exit.setGraphic(exit_hover_icon);
            });
            exit.setOnMouseExited(e -> {
                exit.setGraphic(exit_icon);
            });
            exit.setOnMouseClicked(e -> {
                observableTags.remove(stringTag); // REMOVE TAG!!!
            });
            exit.setStyle("-fx-background-color : transparent;");
            exit.setPadding(new Insets(0));
            exit.setTranslateX(0);
            exit.setTranslateY(2);

            return exit;
        }
    }
        

}
