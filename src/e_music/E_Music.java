/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author poomi
 */
public class E_Music extends Application{

    /**
     * @param stage
     * @throws java.lang.Exception
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        Login login = new Login(stage);
        
        
        
        
//        User_UI user = new User_UI(stage);
//        Scene scene = new Scene(user.allPane(), 1280, 960);
//        String stylrSheet = getClass().getResource("/Style/style.css").toExternalForm();
//        scene.getStylesheets().add(stylrSheet);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
    }

  
    
    
    public static void main(String[] args) {launch(args);}

    
    
}
