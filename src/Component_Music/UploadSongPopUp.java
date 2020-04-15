/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.Admin_UI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author Sirawit
 */
public class UploadSongPopUp { // Use for Upload And Edit Song

    private FileChooser fileChooser;
    private File filePath;
    private ImageView photo;
    private Image image;
    private HBox totalDetail;
    //private BorderPane totalDetail;
    private Stage stage;
    
    private Label title;

    //data song
    String nameForUpload;
    File file = new File("src/MusicFile/song.mp3");
    ArrayList<Song> songArrayList = new ArrayList<Song>();
    File musicFile = new File("src/data/music.dat");
    
    TextField fillNameSong;
    TextField fillNameArtist;
    TextField fillDetailSong;
    TextField fillSongPrice; 
    TextField path;


    public UploadSongPopUp(String title) { // For Upload
        this.title = new Label(title);
        this.fillNameSong = new TextField();
        this.fillNameArtist = new TextField();
        this.fillDetailSong = new TextField();
        this.fillSongPrice = new TextField();
        this.path = new TextField();
        photo = new ImageView(new Image("/image/defaultmusic.png"));
        runOnce();
    }
    
    public UploadSongPopUp(String title, String fillNameSong, String fillNameArtist, String fillDetailSong, String fillSongPrice, 
                            String path, Image img, CheckBox chkbox) { //For Edit //Gut you can change this
        this.title = new Label(title);
        this.fillNameSong = new TextField(fillNameSong);
        this.fillNameArtist = new TextField(fillNameArtist);
        this.fillDetailSong = new TextField(fillDetailSong);
        this.fillSongPrice = new TextField(fillSongPrice);
        this.path = new TextField(path);
        photo = new ImageView(img); 
        //= new Checkbox(chkbox); // Gut 
        runOnce();

    }
    
    private void runOnce(){
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Upload New Song");
        stage.setResizable(false);

        DetailUploadSong();
        Scene scene = new Scene(totalDetail);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        scene.getStylesheets().add(stylrSheet); // CSS

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }

    double mouse_x = 0, mouse_y = 0; // position mouse

    private CheckBox pop = createCheckBox("Pop");
    private CheckBox jazz = createCheckBox("Jazz");
    private CheckBox rock = createCheckBox("Rock");
    private CheckBox rnb = createCheckBox("R&B");
    private CheckBox hiphop = createCheckBox("Hip Hop");

    ArrayList<CheckBox> listCheckBox;
    ArrayList<String> listStyleSong;

    private CheckBox createCheckBox(String name) {
        CheckBox chkbox = new CheckBox(name);
        chkbox.getStyleClass().add("checkbox");
        return chkbox;
    }

    private void DetailUploadSong() {
        
        title.getStyleClass().add("title"); //CSS
        
        totalDetail = new HBox(40);

        VBox detail = new VBox(30);
        VBox imageDetail = new VBox(5);
        
        listStyleSong = new ArrayList<>();
        listCheckBox = new ArrayList<>();

        totalDetail.getStyleClass().add("allPane"); //CSS
        totalDetail.setAlignment(Pos.TOP_CENTER);
        totalDetail.setPadding(new Insets(50));
        totalDetail.setOnMousePressed(e -> {
            mouse_x = e.getSceneX();
            mouse_y = e.getSceneY();
            //System.out.println(mouse_x + " " + mouse_y);
       });
       totalDetail.setOnMouseDragged(e -> {
           stage.setX(e.getScreenX() - mouse_x);
           stage.setY(e.getScreenY() - mouse_y);
       });

//        ImageView imageSong = new ImageView(img);      //commend by gut
//        imageSong.setFitWidth(250);
//        imageSong.setFitHeight(300);

        //Upload Picture
        Button imageBtn = new Button("Upload Picture");
        imageBtn.setOnAction(e -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Files", "*.png"), new FileChooser.ExtensionFilter("JPEG", "*.jpeg"));

            //Set to user's directory or go to the default C drvie if cannot access
            String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
            File userDirectory = new File(userDirectoryString);

            if (!userDirectory.canRead()) {
                userDirectory = new File("user.home");
            }

            fileChooser.setInitialDirectory(userDirectory);

            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image
            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                this.photo.setImage(image);

            } catch (IOException ex) {
                System.out.println("UploadSongPopUp : IOExeption upload picture in DetailUpPopSong");
            }
        });
       
        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);
        imageBtn.getStyleClass().add("buybtn"); //CSS
       
        HBox styleSelect = new HBox(10);
        styleSelect.setAlignment(Pos.CENTER);
        
        listCheckBox.add(pop);
        listCheckBox.add(jazz);
        listCheckBox.add(rock);
        listCheckBox.add(rnb);
        listCheckBox.add(hiphop);
        for (CheckBox checkbox : listCheckBox) {
            styleSelect.getChildren().add(checkbox);
        }
        
        imageDetail.setAlignment(Pos.TOP_CENTER);
        imageDetail.setSpacing(20);
        imageDetail.getChildren().addAll(title, photo, imageBtn);
        imageDetail.setMinHeight(280);

        //Detail Song
        //Upload detail song
        //Song
        Label nameSong = new Label("Song");
        fillNameSong.setPromptText("Name");
        fillNameSong.setPrefWidth(300);

        //Artist
        Label nameArtist = new Label("Artist");

        fillNameArtist.setPromptText("Singer / Band");
        fillNameArtist.setPrefWidth(300);

        //Detail
        Label detailSong = new Label("Detail");
        fillDetailSong.setPromptText("min:sec / date of song / albums");
        fillDetailSong.setPrefWidth(293);

        //Song Style
        Label songStyle = new Label("Style"); 
        
        //Price
        Label songPrice = new Label("Price");
        fillSongPrice.setPromptText("e.g. 100 Bahts");
        fillSongPrice.setPrefWidth(306);

        fillNameSong.getStyleClass().add("detailUploadTextFill"); //CSS
        fillNameArtist.getStyleClass().add("detailUploadTextFill"); //CSS
        fillDetailSong.getStyleClass().add("detailUploadTextFill"); //CSS
        fillSongPrice.getStyleClass().add("detailUploadTextFill"); //CSS
        

        //Button Buy
        Button uploadBtn = new Button("Upload File");
        uploadBtn.getStyleClass().add("uploadbtn"); // borrow...
        uploadBtn.setOnMouseClicked(e -> {
            this.uploadSong();
        });

        path.setPrefWidth(300);
        path.setPromptText(".MP3");
        path.getStyleClass().add("detailUploadTextFillPath");

        
        Button saveBtn = new Button("Save");
        saveBtn.getStyleClass().add("savebtn"); // borrow...
        saveBtn.setOnMouseClicked(e -> {

            for (CheckBox checkbox : listCheckBox) {
                if (checkbox.isSelected()) {
                    listStyleSong.add(checkbox.getText());
                }
            }

            if (!listStyleSong.isEmpty()) {
                try {
                    songArrayList = readFileSong(musicFile);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("UploadSongPopUp : IOExeption read file in DetailUpPopSong");
                }
                songArrayList.add(new Song(fillNameSong.getText(), fillDetailSong.getText(), fillNameArtist.getText(), fillSongPrice.getText(), listStyleSong, image));
                try {
                    writeFileSong(musicFile, songArrayList);
                } catch (IOException ex) {
                    System.out.println("UploadSongPopUp : IOExeption write file in DetailUpPopSong");
                }
                //Upload file .mp3
                nameForUpload = fillNameSong.getText() + fillNameArtist.getText() + fillDetailSong.getText();
                File newSong = new File("src/MusicFile/" + nameForUpload + ".mp3");
                System.out.println(newSong.getName());
                try {
                    Files.copy(file.toPath(), newSong.toPath());
                } catch (IOException ex) {
                    System.out.println("UploadSongPopUp : IOExeption upload file song in DetailUpPopSong");
                }
                Admin_UI.totalPane.getChildren().remove(0);
                Admin_UI.totalPane.getChildren().add(Admin_UI.updateScrollPane(""));
                stage.close();
            } else {
                AlertBox.displayAlert("Upload Faile!", "Plese select your song' style.");
            }
        });
        
        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(e -> {
            stage.close();
        });
        
        HBox hbox6 = new HBox(20);
        hbox6.setAlignment(Pos.BOTTOM_RIGHT);
        hbox6.getChildren().addAll(saveBtn, cancelBtn);

        Label colon1 = new Label(":");
        Label colon2 = new Label(":");
        Label colon3 = new Label(":");
        Label colon4 = new Label(":");
        Label colon5 = new Label(":");
        Label colon6 = new Label(":");
        
        GridPane gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(10);
        gridPane.getStyleClass().add("detailUpload"); //CSS
        GridPane.setHalignment(nameSong, HPos.RIGHT);
        GridPane.setHalignment(nameArtist, HPos.RIGHT);
        GridPane.setHalignment(detailSong, HPos.RIGHT);
        GridPane.setHalignment(songStyle, HPos.RIGHT);
        GridPane.setHalignment(songPrice, HPos.RIGHT);
        GridPane.setHalignment(uploadBtn, HPos.RIGHT);
        GridPane.setConstraints(nameSong, 0, 0);
        GridPane.setConstraints(nameArtist, 0, 1);
        GridPane.setConstraints(detailSong, 0, 2);
        GridPane.setConstraints(songStyle, 0, 3);
        GridPane.setConstraints(songPrice, 0, 4);
        GridPane.setConstraints(uploadBtn, 0, 5);
        GridPane.setConstraints(colon1, 1, 0);
        GridPane.setConstraints(colon2, 1, 1);
        GridPane.setConstraints(colon3, 1, 2);
        GridPane.setConstraints(colon4, 1, 3);
        GridPane.setConstraints(colon5, 1, 4);
        GridPane.setConstraints(colon6, 1, 5);
        GridPane.setConstraints(fillNameSong, 2, 0);
        GridPane.setConstraints(fillNameArtist, 2, 1);
        GridPane.setConstraints(fillDetailSong, 2, 2);
        GridPane.setConstraints(styleSelect, 2, 3);
        GridPane.setConstraints(fillSongPrice, 2, 4);
        GridPane.setConstraints(path, 2, 5);
        gridPane.getChildren().addAll(nameSong,nameArtist,detailSong,songStyle,songPrice,uploadBtn,
                colon1,colon2,colon3,colon4,colon5,colon6,
                fillNameSong,fillNameArtist,fillDetailSong,styleSelect,fillSongPrice,path);
                
        detail.getChildren().addAll(gridPane,hbox6); 
        totalDetail.getChildren().addAll(imageDetail, detail,exitButton());

    }

    private Button exitButton() {

        //Exit with Decoration
        Image exit_icon = new Image("/icon/close-512-detail.png");
        Image exit_hover_icon = new Image("/icon/close-512_hover.png");

        ImageView imageView = new ImageView(exit_icon);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        ImageView imageView_hover = new ImageView(exit_hover_icon);
        imageView_hover.setFitHeight(20);
        imageView_hover.setFitWidth(20);

        Button exit = new Button("", imageView);
        exit.setOnMouseEntered(e -> {
            exit.setGraphic(imageView_hover);
        });
        exit.setOnMouseExited(e -> {
            exit.setGraphic(imageView);
        });
        exit.setOnMouseClicked(e -> {
            stage.close();
        });
        exit.setStyle("-fx-background-color : transparent;"); //CSS
        //exit.setPadding(Insets.EMPTY);

        return exit;
    }

    public void uploadSong() {
        // create a File chooser 
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            path.setText(file.getAbsolutePath());
        } else {
            System.out.println("upload cancel");
        }
    }

    private ArrayList<Song> readFileSong(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        return (ArrayList<Song>) in.readObject();
    }

    private void writeFileSong(File file, ArrayList<Song> listSong) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(listSong);
        out.close();
    }

}
