/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author 62010710
 */
public class MusicFunc {
    
    public Stage frame = new Stage();
    private String nameForUpload;
    File musicFile = new File("Song.dat");
    File file = new File("music.mp3");
    ArrayList<Song> songArrayList = new ArrayList<Song>();
    Song song = new Song();
//    Text songListText = new Text();
       
    public void uploadSongBTN() {

            // set title for the stage 
            frame.setTitle("File Select");

            // create a File chooser 
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));

            // create a Label 
            Label label = new Label("no files selected");

            // create a Button 
            Button selectFilebtn = new Button("Select file");
            Button uploadFilebtn = new Button("Upload");

            // create an Event Handler 
            selectFilebtn.setOnAction((ActionEvent t) -> {
                file = fileChooser.showOpenDialog(null);

                if (file != null) {

                    label.setText(file.getAbsolutePath() + "  selected");
                }
                else{
                    System.out.println("upload cancel");
                }
                
            });
            
            
            uploadFilebtn.setOnAction((ActionEvent t) -> {
                
                File newSong = new File(nameForUpload+".mp3");

                try {
                    Files.copy(file.toPath(), newSong.toPath());
                } catch (IOException ex) {
                    System.out.println("MusicFunc : IOExeption upload file picture in uploadSongBTN");
                }
            

            System.out.println("added");
            frame.close();
            });

            // create a VBox 
            VBox vbox = new VBox(30, label, selectFilebtn, uploadFilebtn);

            // set Alignment 
            vbox.setAlignment(Pos.CENTER);

            // create a scene 
            Scene scene = new Scene(vbox, 800, 500);

            // set the scene 
            frame.setScene(scene);

            frame.show();
            
    }
    
    public void listSongBTN(){
        
        for (Song song : songArrayList) {
            
            Label labelSong = new Label(song.getNameSong() + "  Artist : " + song.getArtistSong() + "  Detail" + song.getDetailSong());         
                      
        } 
    }
    

    
    public void deleteBTN(){
                 
            System.out.println("DeleteSong");
            songArrayList.remove(song);
                try {
                    writeFile(musicFile, songArrayList);
                } catch (IOException ex) {
                    System.out.println("MusicFunc : IOExeption write file music in listSongBTN");
                }

        this.listSongBTN();
        
    }
    
    public void downloader() {

        System.out.println("Download");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        File downloadFile = fileChooser.showSaveDialog(null);
        
        if (downloadFile != null) {
            try {
                Files.copy(file.toPath(), downloadFile.toPath());
            } catch (IOException ex) {
                System.out.println("MusicFunc : IOExeption download file music in downloader");
            }
        }

    }
    
    
    private ArrayList<Song> readFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Song>) in.readObject();
    }

    private void writeFile(File file, ArrayList<Song> listSong) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(listSong);
        out.close();
    }
}
