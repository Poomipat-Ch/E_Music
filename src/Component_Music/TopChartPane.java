/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author HCARACH
 */
public class TopChartPane {
    
    private BorderPane topchartpane;
    private AnchorPane pane;
    
    public TopChartPane() {
        
        AnchorPane backgroundtopchart = new AnchorPane();
        backgroundtopchart.setStyle("-fx-background-color: transparent;");
        
        pane = new AnchorPane();
        topchartpane = new BorderPane();
        AnchorPane topChartBtn;
        
        ArrayList<String> topchartlist = new ArrayList<>();

        topchartlist.add("THAILAND TOP 50");
        topchartlist.add("INTERNATIONAL TOP 50");

        topchartpane.setLayoutX(20);
        topchartpane.setLayoutY(375);
        topchartpane.setStyle("-fx-background-color: transparent;");
        topchartpane.setMinSize(1030 - 50, 500);
        
        AnchorPane background = new AnchorPane();
        background.getStyleClass().add("topchart");
        background.setMinSize(780, 500);

        VBox topChartVbox = new VBox(10);
        topChartVbox.setMinWidth(200);
        topChartVbox.getStyleClass().add("topchartlabel");
        topChartVbox.setAlignment(Pos.CENTER);
        topChartVbox.setLayoutY(20);

        topChartVbox.getChildren().addAll(createLabel(" ", 2), createLabel("T", 1), createLabel("O", 1), createLabel("P", 1), createLabel(" ", 2),
                createLabel("C", 1), createLabel("H", 1), createLabel("A", 1), createLabel("R", 1), createLabel("T", 1), createLabel(" ", 2));

        topchartpane.setLeft(topChartVbox);

        VBox topChartList = new VBox(5);
        topChartList.setPadding(new Insets(20, 0, 0, 100));
        
        for (String string : topchartlist) {
            topChartBtn = new AnchorPane();
            topChartBtn.getStyleClass().add("topchartbtn");
            topChartBtn.setPrefSize(600, 160);         
            
            ImageView image = new ImageView(new Image("/image/"+string+".png"));
            image.setFitWidth(600);
            image.setPreserveRatio(true);
            
            topChartBtn.getChildren().add(image);
            
            topChartList.getChildren().addAll(createLabel("\n", 2), createLabel(string, 2), topChartBtn);

            topChartBtn.setOnMouseClicked(event -> {
                System.out.println(string);
                    new ShowMusicPage(string, "", new Image("/UI_music/defaultprofile.png"));
            });

        }
        
        AnchorPane centerTopChart = new AnchorPane();
        centerTopChart.getChildren().addAll(background, topChartList,createSeeAll(topchartlist.get(0),650,215, new Image("/UI_music/defaultprofile.png")),createSeeAll(topchartlist.get(1),650,435, new Image("/UI_music/defaultprofile.png")));

        topchartpane.setCenter(centerTopChart);
        
        backgroundtopchart.getChildren().addAll( topchartpane);
        
        pane.getChildren().addAll(new ImageSlide().getAnchorpane(), backgroundtopchart);


    }

    public AnchorPane getTopchartpane() {
        return pane;
    }

    private Label createLabel(String alphabet, int style) {
        Label label = new Label(alphabet);
        if (style == 1) {
            label.getStyleClass().add("labeldetail");
        } else if (style == 2){
            label.getStyleClass().add("listlabeldetail");
        }

        return label;
    }
    
    private Label createSeeAll(String string, double x, double y, Image image) {
        Label label = new Label("SEE ALL");
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.getStyleClass().add("seealllabel");
        
        label.setOnMouseClicked(event -> {
                System.out.println(string);
                    new ShowMusicPage(string, "", image);
            });
        
        return label;
    }
    
}
