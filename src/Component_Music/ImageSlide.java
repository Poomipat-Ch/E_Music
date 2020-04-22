package Component_Music;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
 
public class ImageSlide {
 
    // Width and height of image in pixels
    private final double IMG_WIDTH = 1030;
    private final double IMG_HEIGHT = 300 * 1300 / 900;
 
    private final int NUM_OF_IMGS = 5;
    private final int SLIDE_FREQ = 4; // in secs
    
    private AnchorPane anchorpane;
 
    public ImageSlide() {
        
        anchorpane = new AnchorPane();
        anchorpane.setPrefWidth(1030);
              
        //root code
        StackPane root = new StackPane();
 
        Pane clipPane = new Pane();
        // To center the slide show incase maximized
        clipPane.setMaxWidth(IMG_WIDTH);
        clipPane.setClip(new Rectangle(IMG_WIDTH, IMG_HEIGHT));
 
        HBox imgContainer = addPicture();
        clipPane.getChildren().add(imgContainer);
        root.getChildren().add(clipPane);
 
        startAnimation(imgContainer);
        
        anchorpane.getChildren().addAll(root);
    }

    public AnchorPane getAnchorpane() {
        return anchorpane;
    }
    
    private HBox addPicture() {
        
        HBox imgContainer = new HBox();
        for(int i=1; i<= NUM_OF_IMGS; ++i) {
            ImageView image = new ImageView(new Image("/image/banner" + i +".png"));
            image.setFitWidth(1030);
            image.setPreserveRatio(true);
            
            imgContainer.getChildren().addAll(image);
        }
        
        return imgContainer;
    }
 
    //start animation
    private void startAnimation(final HBox hbox) {
        //error occured on (ActionEvent t) line
        //slide action
        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
            trans.setByX(-IMG_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
        //eventHandler
        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
            trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
 
        List<KeyFrame> keyFrames = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_IMGS; i++) {
            if (i == NUM_OF_IMGS) {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
            } else {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
            }
        }
//add timeLine
        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));
 
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.playFromStart();
    }
    
}
 