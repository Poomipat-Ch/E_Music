/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main_music;

import UI_music.Login;
import javafx.application.Application;
import javafx.stage.Stage;
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
    }

    
    public static void main(String[] args) {launch(args);}

    
}
