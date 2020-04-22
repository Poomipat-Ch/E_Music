/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.Admin_UI;
import UI_music.ReadWriteFile;
import UI_music.TileTagBar;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    File selectFileDelete;
    boolean changePhoto = false;
    boolean songUploadEmply = true;

    TextField fillNameSong;
    TextField fillNameArtist;
    TextField fillDetailSong;
    TextField fillSongPrice;
    TextField path;
    String checkExistFile = "";

    String nationality;

    ArrayList<ArrayList> list = new ArrayList<>();
    ArrayList<String> listStyleSong = new ArrayList<String>();
    TileTagBar tagBar;
    ScrollPane tagScrollPane = new ScrollPane();

    ArrayList<String> test = new ArrayList<>();

    int totalDownload = 0;

//    private CheckBox pop = createCheckBox("Pop");
//    private CheckBox jazz = createCheckBox("Jazz");
//    private CheckBox rock = createCheckBox("Rock");
//    private CheckBox rnb = createCheckBox("R&B");
//    private CheckBox hiphop = createCheckBox("Hip Hop");
    public UploadSongPopUp(String title, String nationality) { // For Upload
        this.title = new Label(title);
        this.fillNameSong = new TextField();
        this.fillNameArtist = new TextField();
        this.fillDetailSong = new TextField();
        this.fillSongPrice = new TextField();
        this.path = new TextField();
        image = new Image("/image/defaultmusic.png");
        photo = new ImageView(image);
        this.nationality = nationality;
        tagBar = new TileTagBar();
        runOnce();
    }

    public UploadSongPopUp(String title, Song editSong, String path) { //For Edit //Gut you can change this
        this.checkExistFile = (editSong.getNameSong() + editSong.getArtistSong().replaceAll("\\s", ""));
        this.title = new Label(title);
        this.fillNameSong = new TextField(editSong.getNameSong());
        this.fillNameArtist = new TextField(editSong.getArtistSong());
        this.fillDetailSong = new TextField(editSong.getDetailSong());
        this.fillSongPrice = new TextField(editSong.getPriceSong());
        this.path = new TextField(path);
        image = editSong.getPhoto();
        this.nationality = editSong.getNationality();
        photo = new ImageView(image);
        tagBar = new TileTagBar();
        file = new File("src/MusicFile/" + checkExistFile + ".mp3");

        this.totalDownload = editSong.getTotalDownload();

//        for (String list : editSong.getListStyleSong()) {
//
//            System.out.println(list);
//            tagBar.getTags().add(list);
//            listStyleSong.add(list);
//
//        }
        test = editSong.getListStyleSong();

        changePhoto = true;

        songUploadEmply = false;
        runOnce();

    }

    private void runOnce() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Upload New Song");
        stage.setResizable(false);
        System.out.println(checkExistFile);//test
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

    ArrayList<CheckBox> listCheckBox;

    private CheckBox createCheckBox(String name) {
        CheckBox chkbox = new CheckBox(name);
        chkbox.getStyleClass().add("checkbox");
        return chkbox;
    }

    private void DetailUploadSong() {

        title.getStyleClass().add("title"); //CSS

        totalDetail = new HBox(40);

//        listStyleSong = new ArrayList<>();
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
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream("src/data/stylemusiclist.dat"));
        } catch (IOException ex) {
            System.out.println("UploadSongPopUp : ERROR READ STYLE FILE");
        }

        try {
            list = (ArrayList<ArrayList>) file.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("UploadSongPopUp : ERROR READ STYLE FILE");
        }

        if (this.title.getText().equals("Upload Song") || nationality.equals("international")) {
            setupInterPane(list.get(0));
        } else {
            setupThaiPane(list.get(1));
        }
    }

    private void setupInterPane(ArrayList<String> list) {

        VBox detail = new VBox(30);
        VBox imageDetail = new VBox(5);

        AnchorPane profilePicture = new AnchorPane();
        profilePicture.getChildren().add(new ImageRectangle(30, 200, 200, image).getMyRectangle());

        Button imageBtn = new Button("Upload Picture");
        imageBtn.setOnAction(e -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

            //Set to user's directory or go to the default C drvie if cannot access
            String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
            File userDirectory = new File(userDirectoryString);

            if (!userDirectory.canRead()) {
                userDirectory = new File("user.home");
            }

            fileChooser.setInitialDirectory(userDirectory);

            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image
            if (filePath != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(filePath);
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    this.photo.setImage(image);
                    profilePicture.getChildren().remove(0);
                    profilePicture.getChildren().add(new ImageRectangle(30, 200, 200, image).getMyRectangle());

                } catch (IOException ex) {
                    System.out.println("UploadSongPopUp : IOExeption upload picture in DetailUpPopSong");
                }
                changePhoto = true;
            } else {
                System.out.println("upload cancel");
            }

        });

        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);
        imageBtn.getStyleClass().add("buybtn"); //CSS

//        listCheckBox.add(pop);
//        listCheckBox.add(jazz);
//        listCheckBox.add(rock);
//        listCheckBox.add(rnb);
//        listCheckBox.add(hiphop);
//        for (CheckBox checkbox : listCheckBox) {
//            styleSelect.getChildren().add(checkbox);
//        }
        //Style Tag !!!! By Pop  /////////////////////////////////////////////////////////////////////
//        tagBar = new TileTagBar(); // Own Class see in TileTagBar.java
//        ScrollPane tagScrollPane = new ScrollPane();
        //setPrefSize(300, 400);
        //pannableProperty().set(true);
        tagScrollPane.fitToWidthProperty().set(true);
        tagScrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        tagScrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tagScrollPane.setPrefWidth(300);
        tagScrollPane.setMinHeight(60);
        tagScrollPane.setMaxHeight(100);
        tagScrollPane.getStyleClass().add("scroll-bar");

        tagScrollPane.setContent(tagBar);
        this.setupStyleList();

        Button selectBtn = new Button("Select");
        selectBtn.getStyleClass().add("buybtn");
        selectBtn.setOnAction((ActionEvent e) -> { // Select and open new Stage(New Window)
            selectStage = new Stage();
            selectStage.initModality(Modality.APPLICATION_MODAL);
            selectStage.setResizable(false);
            selectStage.initStyle(StageStyle.TRANSPARENT);

            listView = new ListView<>();

            list.forEach((string) -> {
                listView.getItems().addAll(string);
            });
//            listView.getItems().addAll("Gud", "I", "Find", "Naa", "Hee", "Len", "tae", "Game", "-3-"); //Pull DATA
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listView.getStyleClass().add("detailTag");

            Button summitBtn = new Button("Submit");
            summitBtn.getStyleClass().add("savebtn");
            summitBtn.setOnAction(evt -> {

                summitClicked();
                //When Everything is Done! 
                selectStage.close();
            });

            Button cancelBtn = new Button("Cancel");
            cancelBtn.getStyleClass().add("cancelbtn");
            cancelBtn.setOnAction(evt -> selectStage.close()); // Force Close!
            //HBox
            HBox summitAndCancelBox = new HBox(30);
            summitAndCancelBox.setAlignment(Pos.CENTER);
            summitAndCancelBox.getChildren().addAll(summitBtn, cancelBtn); //Botton of Window

            Label selectStyleLabel = new Label("Select Song Style");
            selectStyleLabel.getStyleClass().add("title");

            //VBox 
            VBox totalVBox = new VBox(15);
            totalVBox.getChildren().addAll(exitButtonForStyleSelection(), selectStyleLabel, listView, summitAndCancelBox);
            totalVBox.getStyleClass().add("allPanePremium");
            totalVBox.setAlignment(Pos.TOP_CENTER);
            totalVBox.setPadding(new Insets(30));
            totalVBox.setOnMousePressed(evt -> {
                mouse_x = evt.getSceneX();
                mouse_y = evt.getSceneY();
                //System.out.println(mouse_x + " " + mouse_y);
            });
            totalVBox.setOnMouseDragged(evt -> {
                selectStage.setX(evt.getScreenX() - mouse_x);
                selectStage.setY(evt.getScreenY() - mouse_y);
            });

            Scene selectScene = new Scene(totalVBox);
            String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
            selectScene.getStylesheets().add(stylrSheet); // CSS
            selectScene.setFill(Color.TRANSPARENT);
            selectStage.setScene(selectScene);
            selectStage.showAndWait();
        });

        Button sortBtn = new Button("Sort");
        sortBtn.getStyleClass().add("buybtn");
        sortBtn.setOnAction(e -> {
            FXCollections.sort(tagBar.getTags()); //Sort from A-Z a->z
        });

        HBox styleSelect = new HBox(10);
        styleSelect.setAlignment(Pos.CENTER_LEFT);
        styleSelect.getChildren().addAll(tagScrollPane, selectBtn, sortBtn);

        /////////////////////////////////////////////////////////////////////
        imageDetail.setAlignment(Pos.TOP_CENTER);
        imageDetail.setSpacing(20);
        imageDetail.getChildren().addAll(title, profilePicture, imageBtn);
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
        fillSongPrice.setPromptText("e.g. 59 Bahts");
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

//            for (CheckBox checkbox : listCheckBox) {
//                if (checkbox.isSelected()) {
//                    listStyleSong.add(checkbox.getText());
//                }
//            }
            this.saveStyleList();
            if (!listStyleSong.isEmpty() && !fillDetailSong.getText().isEmpty() && !fillNameArtist.getText().isEmpty() && !fillDetailSong.getText().isEmpty() && !fillSongPrice.getText().isEmpty() && !songUploadEmply) {
                if (!checkExistFile.equals("")) {
                    ArrayList<Song> oldSongList = new ArrayList<Song>();
                    ArrayList<Song> newSongList = new ArrayList<Song>();
                    selectFileDelete = new File("src/MusicFile/" + checkExistFile + ".mp3");
                    try {
                        oldSongList = ReadWriteFile.readFileSong(musicFile);
                    } catch (IOException ex) {
                        System.out.println("UploadSongPopUp : IOExeption read file in DetailUpPopSong");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("UploadSongPopUp : ClassNotFoundExeption read file in DetailUpPopSong");
                    }
                    for (Song song : oldSongList) {
                        if (checkExistFile.equals((song.getNameSong() + song.getArtistSong()).replaceAll("\\s", ""))) {

                        } else {
                            newSongList.add(song);
                        }
                    }
                    try {
                        ReadWriteFile.writeFileSong(musicFile, newSongList);
                    } catch (IOException ex) {
                        Logger.getLogger(UploadSongPopUp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    changePhoto = true;
                }
                try {
                    songArrayList = ReadWriteFile.readFileSong(musicFile);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("UploadSongPopUp : IOExeption read file in DetailUpPopSong");
                }
                if (checkSong(songArrayList, fillNameSong.getText(), fillNameArtist.getText()) == 0) {
                    if (changePhoto) {
                        this.saveSong();
                    } else {
                        if (changePhoto = AlertBox.display("missing photo?", "Are you sure to save without upload photo")) {
                            this.saveSong();
                        } else {
                            System.out.println("save cancel");
                        }
                    }
                } else {
                    AlertBox.displayAlert("Upload Fail!", "This song already exist");
                }

            } else if (songUploadEmply) {
                AlertBox.displayAlert("Upload Fail!", "Plese upload song.");
            } else {
                AlertBox.displayAlert("Upload Fail!", "Plese complete the form.");
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
        gridPane.getChildren().addAll(nameSong, nameArtist, detailSong, songStyle, songPrice, uploadBtn,
                colon1, colon2, colon3, colon4, colon5, colon6,
                fillNameSong, fillNameArtist, fillDetailSong, styleSelect, fillSongPrice, path);

        detail.getChildren().addAll(gridPane, hbox6);
        totalDetail.getChildren().addAll(imageDetail, detail, exitButton());
    }

    ListView<String> listView;

    Stage selectStage;

//    TileTagBar tagBar;
    private void setupThaiPane(ArrayList<String> list) {

        VBox detail = new VBox(30);
        VBox imageDetail = new VBox(5);

        AnchorPane profilePicture = new AnchorPane();
        profilePicture.getChildren().add(new ImageRectangle(30,200, 200, image).getMyRectangle());

        Button imageBtn = new Button("เพิ่มรูปภาพ");
        imageBtn.setOnAction(e -> {
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            
            //Set to user's directory or go to the default C drvie if cannot access
            String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
            File userDirectory = new File(userDirectoryString);

            if (!userDirectory.canRead()) {
                userDirectory = new File("user.home");
            }

            fileChooser.setInitialDirectory(userDirectory);

            this.filePath = fileChooser.showOpenDialog(stage);

            //Try to update the image by loading the new image
            if (filePath != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(filePath);
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    this.photo.setImage(image);
                    profilePicture.getChildren().remove(0);
                    profilePicture.getChildren().add(new ImageRectangle(30, 200, 200, image).getMyRectangle());

                } catch (IOException ex) {
                    System.out.println("UploadSongPopUp : IOExeption upload picture in DetailUpPopSong");
                }
                changePhoto = true;
            } else {
                System.out.println("upload cancel");
            }

        });

        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);
        imageBtn.getStyleClass().add("buybtn"); //CSS

//        listCheckBox.add(pop);
//        listCheckBox.add(jazz);
//        listCheckBox.add(rock);
//        listCheckBox.add(rnb);
//        listCheckBox.add(hiphop);
//        for (CheckBox checkbox : listCheckBox) {
//            styleSelect.getChildren().add(checkbox);
//        }
        //Style Tag !!!! By Pop  /////////////////////////////////////////////////////////////////////
        tagBar = new TileTagBar(); // Own Class see in TileTagBar.java

//        ScrollPane tagScrollPane = new ScrollPane();
        //setPrefSize(300, 400);
        //pannableProperty().set(true);
        tagScrollPane.fitToWidthProperty().set(true);
        tagScrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        tagScrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        tagScrollPane.setPrefWidth(300);
        tagScrollPane.setMinHeight(60);
        tagScrollPane.setMaxHeight(100);
        tagScrollPane.getStyleClass().add("scroll-bar");

        tagScrollPane.setContent(tagBar);
        this.setupStyleList();

        Button selectBtn = new Button("เลือก");
        selectBtn.getStyleClass().add("buybtn");
        selectBtn.setOnAction(e -> { // Select and open new Stage(New Window)
            selectStage = new Stage();
            selectStage.initModality(Modality.APPLICATION_MODAL);
            selectStage.setResizable(false);
            selectStage.initStyle(StageStyle.TRANSPARENT);

            listView = new ListView<>();

            list.forEach((string) -> {
                listView.getItems().addAll(string);
            });
//            listView.getItems().addAll("Gud", "I", "Find", "Naa", "Hee", "Len", "tae", "Game", "-3-"); //Pull DATA
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listView.getStyleClass().add("detailTag");

            Button summitBtn = new Button("ตกลง");
            summitBtn.getStyleClass().add("savebtn");
            summitBtn.setOnAction(evt -> {

                summitClicked();
                //When Everything is Done! 
                selectStage.close();
            });

            Button cancelBtn = new Button("ยกเลิก");
            cancelBtn.getStyleClass().add("cancelbtn");
            cancelBtn.setOnAction(evt -> selectStage.close()); // Force Close!
            //HBox
            HBox summitAndCancelBox = new HBox(30);
            summitAndCancelBox.setAlignment(Pos.CENTER);
            summitAndCancelBox.getChildren().addAll(summitBtn, cancelBtn); //Botton of Window

            Label selectStyleLabel = new Label("เลือกแนวเพลง");
            selectStyleLabel.getStyleClass().add("title");

            //VBox 
            VBox totalVBox = new VBox(15);
            totalVBox.getChildren().addAll(exitButtonForStyleSelection(), selectStyleLabel, listView, summitAndCancelBox);
            totalVBox.getStyleClass().add("allPanePremium");
            totalVBox.setAlignment(Pos.TOP_CENTER);
            totalVBox.setPadding(new Insets(30));
            totalVBox.setOnMousePressed(evt -> {
                mouse_x = evt.getSceneX();
                mouse_y = evt.getSceneY();
                //System.out.println(mouse_x + " " + mouse_y);
            });
            totalVBox.setOnMouseDragged(evt -> {
                selectStage.setX(evt.getScreenX() - mouse_x);
                selectStage.setY(evt.getScreenY() - mouse_y);
            });

            Scene selectScene = new Scene(totalVBox);
            String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
            selectScene.getStylesheets().add(stylrSheet); // CSS
            selectScene.setFill(Color.TRANSPARENT);
            selectStage.setScene(selectScene);
            selectStage.showAndWait();
        });

        Button sortBtn = new Button("Sort");
        sortBtn.getStyleClass().add("buybtn");
        sortBtn.setOnAction(e -> {
            FXCollections.sort(tagBar.getTags()); //Sort from A-Z a->z
        });

        HBox styleSelect = new HBox(10);
        styleSelect.setAlignment(Pos.CENTER_LEFT);
        styleSelect.getChildren().addAll(tagScrollPane, selectBtn, sortBtn);

        /////////////////////////////////////////////////////////////////////
        imageDetail.setAlignment(Pos.TOP_CENTER);
        imageDetail.setSpacing(20);
        imageDetail.getChildren().addAll(title, profilePicture, imageBtn);
        imageDetail.setMinHeight(280);

        //Detail Song
        //Upload detail song
        //Song
        Label nameSong = new Label("เพลง");
        fillNameSong.setPromptText("ชื่อเพลง");
        fillNameSong.setPrefWidth(300);

        //Artist
        Label nameArtist = new Label("ศิลปิน");

        fillNameArtist.setPromptText("ชื่อ นักร้อง / วงดนตรี");
        fillNameArtist.setPrefWidth(300);

        //Detail
        Label detailSong = new Label("รายละเอียด");
        fillDetailSong.setPromptText("ความยาวเพลง นาที : วินาที / อัลบั้ม");
        fillDetailSong.setPrefWidth(293);

        //Song Style
        Label songStyle = new Label("แนวเพลง");

        //Price
        Label songPrice = new Label("ราคา");
        fillSongPrice.setPromptText("ตัวอย่าง 59 บาท");
        fillSongPrice.setPrefWidth(306);

        fillNameSong.getStyleClass().add("detailUploadTextFill"); //CSS
        fillNameArtist.getStyleClass().add("detailUploadTextFill"); //CSS
        fillDetailSong.getStyleClass().add("detailUploadTextFill"); //CSS
        fillSongPrice.getStyleClass().add("detailUploadTextFill"); //CSS

        //Button Buy
        Button uploadBtn = new Button("เพิ่มไฟล์เพลง");
        uploadBtn.getStyleClass().add("uploadbtn"); // borrow...
        uploadBtn.setOnMouseClicked(e -> {
            this.uploadSong();
        });

        path.setPrefWidth(300);
        path.setPromptText(".MP3");
        path.getStyleClass().add("detailUploadTextFillPath");

        Button saveBtn = new Button("บันทึก");
        saveBtn.getStyleClass().add("savebtn"); // borrow...
        saveBtn.setOnMouseClicked(e -> {

//            for (CheckBox checkbox : listCheckBox) {
//                if (checkbox.isSelected()) {
//                    listStyleSong.add(checkbox.getText());
//                }
//            }
            this.saveStyleList();
            if (!listStyleSong.isEmpty() && !fillDetailSong.getText().isEmpty() && !fillNameArtist.getText().isEmpty() && !fillDetailSong.getText().isEmpty() && !fillSongPrice.getText().isEmpty() && !songUploadEmply) {
                if (!checkExistFile.equals("")) {
                    ArrayList<Song> oldSongList = new ArrayList<Song>();
                    ArrayList<Song> newSongList = new ArrayList<Song>();
                    selectFileDelete = new File("src/MusicFile/" + checkExistFile + ".mp3");
                    try {
                        oldSongList = ReadWriteFile.readFileSong(musicFile);
                    } catch (IOException ex) {
                        System.out.println("UploadSongPopUp : IOExeption read file in DetailUpPopSong");
                    } catch (ClassNotFoundException ex) {
                        System.out.println("UploadSongPopUp : ClassNotFoundExeption read file in DetailUpPopSong");
                    }
                    for (Song song : oldSongList) {
                        if (checkExistFile.equals((song.getNameSong() + song.getArtistSong()).replaceAll("\\s", ""))) {

                        } else {
                            newSongList.add(song);
                        }
                    }
                    try {
                        ReadWriteFile.writeFileSong(musicFile, newSongList);
                    } catch (IOException ex) {
                        Logger.getLogger(UploadSongPopUp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    changePhoto = true;
                }
                try {
                    songArrayList = ReadWriteFile.readFileSong(musicFile);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("UploadSongPopUp : IOExeption read file in DetailUpPopSong");
                }
                if (checkSong(songArrayList, fillNameSong.getText(), fillNameArtist.getText()) == 0) {
                    if (changePhoto) {
                        this.saveSong();
                    } else {
                        if (changePhoto = AlertBox.display("ไม่มีรูปภาพ?", "คุณแน่ใจหรือไม่? ที่จะทำการ\nบันทึกโดยการไม่มีรูปภาพ")) {
                            this.saveSong();
                        } else {
                            System.out.println("save cancel");
                        }
                    }
                } else {
                    AlertBox.displayAlert("Upload Fail!", "This song already exist");
                }

            } else if (songUploadEmply) {
                AlertBox.displayAlert("Upload Fail!", "Plese upload song.");
            } else {
                AlertBox.displayAlert("Upload Fail!", "Plese complete the form.");
            }
        });

        Button cancelBtn = new Button("ยกเลิก");
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
        gridPane.getChildren().addAll(nameSong, nameArtist, detailSong, songStyle, songPrice, uploadBtn,
                colon1, colon2, colon3, colon4, colon5, colon6,
                fillNameSong, fillNameArtist, fillDetailSong, styleSelect, fillSongPrice, path);

        detail.getChildren().addAll(gridPane, hbox6);
        totalDetail.getChildren().addAll(imageDetail, detail, exitButton());

    }

    private void summitClicked() { // For Tag Style
        String testMessage = "";
        ObservableList<String> styleList;
        styleList = listView.getSelectionModel().getSelectedItems();

        for (String list : styleList) {

            if (!tagBar.getTags().contains(list)) {
                tagBar.getTags().add(list);
            }

            testMessage += list + ", ";
        }

        System.out.println("You have been selected style(s) :\n" + testMessage);

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

    private Button exitButtonForStyleSelection() {

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
            selectStage.close();
        });
        exit.setStyle("-fx-background-color : transparent;"); //CSS
        //exit.setPadding(Insets.EMPTY);
        exit.setTranslateX(110);

        return exit;
    }

    public void uploadSong() {
        // create a File chooser 
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            path.setText(file.getAbsolutePath());
            songUploadEmply = false;
        } else {
            System.out.println("upload cancel");
        }
    }

    private int checkSong(ArrayList<Song> arrayList, String name, String artist) {
        int i = 0;
        for (Song song : arrayList) {
            if (song.getNameSong().equals(name) && song.getArtistSong().equals(artist)) {
                i = 1;
            } else {
                i = 0;
            }
        }
        return i;
    }

    private void saveSong() {
        int priceCorrect = 0;
        Boolean priceCorrectBoolean = false;
        try {
            priceCorrect = Integer.parseInt(fillSongPrice.getText());
            priceCorrectBoolean = true;
        } catch (Exception e) {
            System.out.println(e);
            priceCorrectBoolean = false;
        }
        if (priceCorrect >= 0 && priceCorrectBoolean) {
            songArrayList.add(new Song(fillNameSong.getText(), fillDetailSong.getText(), fillNameArtist.getText(), fillSongPrice.getText(), listStyleSong, image, nationality, totalDownload));
            try {
                ReadWriteFile.writeFileSong(musicFile, songArrayList);
            } catch (IOException ex) {
                System.out.println("UploadSongPopUp : IOExeption write file in DetailUpPopSong");
            }
            //Upload file .mp3
//                file = new File(nameForUpload);
            nameForUpload = (fillNameSong.getText() + fillNameArtist.getText()).replaceAll("\\s", "");
            File newSong = new File("src/MusicFile/" + nameForUpload + ".mp3");
            System.out.println(newSong.getName());
            try {
                Files.copy(file.toPath(), newSong.toPath());
            } catch (IOException ex) {
                System.out.println("UploadSongPopUp : IOExeption upload file song in DetailUpPopSong");
            }
            if (!checkExistFile.equals("")) {
                selectFileDelete.delete();
            }
            Admin_UI.totalPane.getChildren().clear();
            Admin_UI.totalPane.getChildren().add(Admin_UI.updateScrollPane(""));

            if (this.title.getText().equals("Upload Song")) {
                AlertBox.displayAlert("Upload Success!", "Upload Success!");
            } else {
                AlertBox.displayAlert("เพิ่มเพลงสำเร็จ!", "เพิ่มเพลงสำเร็จ!");
            }
            stage.close();
        } else {
            AlertBox.displayAlert("Fail!", "Please check your price.");
        }
    }

    public void setupStyleList() {
        for (String list : test) {

            if (!tagBar.getTags().contains(list)) {
                System.out.println(list);
                tagBar.getTags().add(list);
            }
        }
    }

    public void saveStyleList() {
        for (String list : tagBar.getTags()) {
            System.out.println("check");
            System.out.println(list);
            listStyleSong.add(list);
        }

    }
}
