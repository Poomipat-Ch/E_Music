/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import UI_music.Admin_UI;
import UI_music.ReadWriteFile;
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
import javafx.scene.layout.AnchorPane;
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
public class UploadArtistPopUp { // Use for Upload And Edit Song

    private FileChooser fileChooser;
    private File filePath;
    private ImageView photo;
    private Image image;
    private HBox totalDetail;
    //private BorderPane totalDetail;
    private Stage stage;

    private Label title;

    TextField fillNameArtist;
    TextField fillNameArtist2;
    TextField fillDetailArtist;

    File artistFile = new File("src/data/artist.dat");
    ArrayList<Artist> artistArrayList = new ArrayList<Artist>();
    Artist editArtist;
    Boolean changePhoto = false;

    public UploadArtistPopUp(String title) { // For Upload
        this.title = new Label(title);
        this.fillNameArtist = new TextField();
        this.fillNameArtist2 = new TextField();
        this.fillDetailArtist = new TextField();
        image = new Image("/image/defaultprofile.png");
        photo = new ImageView(image);
        runOnce();
    }

    public UploadArtistPopUp(String title, Artist editArtist) { //For Edit //Gut you can change this
        this.title = new Label(title);
        this.fillNameArtist = new TextField(editArtist.getName1());
        this.fillNameArtist2 = new TextField(editArtist.getName2());
        this.fillDetailArtist = new TextField(editArtist.getInfomation());
        image = editArtist.getPhoto();
        photo = new ImageView(image);
        this.editArtist = editArtist;
        changePhoto = true;
        runOnce();

    }

    private void runOnce() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Upload New Song");
        stage.setResizable(false);

        DetailUploadArtist();
        Scene scene = new Scene(totalDetail);
        String stylrSheet = getClass().getResource("/style_css/stylePopupDetail.css").toExternalForm(); // From PopUpdetail CSS
        scene.getStylesheets().add(stylrSheet); // CSS

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();
    }

    double mouse_x = 0, mouse_y = 0; // position mouse

    private void DetailUploadArtist() {

        title.getStyleClass().add("title"); //CSS

        totalDetail = new HBox(0);

        totalDetail.getStyleClass().add("allPane"); //CSS
        totalDetail.setPadding(new Insets(40));
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
        if(this.title.getText().equals("New Artist"))
            setupInterPane();
        else
            setupThaiPane();

    }
    
    private void setupInterPane() {
        
        VBox detail = new VBox(30);
        detail.setAlignment(Pos.CENTER);
        
        Button imageBtn = new Button("Upload Picture");
        imageBtn.getStyleClass().add("buybtn"); //CSS
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
            if (filePath != null) {
                //Try to update the image by loading the new image
                try {
                    BufferedImage bufferedImage = ImageIO.read(filePath);
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    this.photo.setImage(image);

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

        //Upload Detail Artist
        //Artist
        Label nameArtist = new Label("Name");
        fillNameArtist.setPromptText("(Showing Artist Name)");
        fillNameArtist.setPrefWidth(300);

        //Artist (Optional) 
        Label nameArtist2 = new Label("Name2");
        fillNameArtist2.setPromptText("(Optional)");
        fillNameArtist2.setPrefWidth(300);

        //Detail
        Label detailSong = new Label("Detail");
        fillDetailArtist.setPromptText("detail of Artist");
        fillDetailArtist.setPrefWidth(200);

        fillNameArtist.getStyleClass().add("detailUploadTextFill"); //CSS
        fillNameArtist2.getStyleClass().add("detailUploadTextFill"); //CSS
        fillDetailArtist.getStyleClass().add("detailUploadTextFill"); //CSS

        Label colon1 = new Label(":");
        Label colon2 = new Label(":");
        Label colon3 = new Label(":");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.getStyleClass().add("detailUpload"); //CSS
        GridPane.setHalignment(nameArtist, HPos.RIGHT);
        GridPane.setHalignment(nameArtist2, HPos.RIGHT);
        GridPane.setHalignment(detailSong, HPos.RIGHT);
        GridPane.setConstraints(nameArtist, 0, 0);
        GridPane.setConstraints(nameArtist2, 0, 1);
        GridPane.setConstraints(detailSong, 0, 2);
        GridPane.setConstraints(colon1, 1, 0);
        GridPane.setConstraints(colon2, 1, 1);
        GridPane.setConstraints(colon3, 1, 2);
        GridPane.setConstraints(fillNameArtist, 2, 0);
        GridPane.setConstraints(fillNameArtist2, 2, 1);
        GridPane.setConstraints(fillDetailArtist, 2, 2);
        gridPane.getChildren().addAll(nameArtist, nameArtist2, detailSong,
                colon1, colon2, colon3,
                fillNameArtist, fillNameArtist2, fillDetailArtist);

        Button saveBtn = new Button("Save");
        saveBtn.getStyleClass().add("savebtn"); // borrow...
        saveBtn.setOnMouseClicked(e -> {
            if(!fillNameArtist.getText().isEmpty() && !fillDetailArtist.getText().isEmpty())
            {
            try {
                artistArrayList = ReadWriteFile.readFileArist(artistFile);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("UploadArtistPopUp : IOExeption read file in DetailUpPopArtist");
            }
            if (!checkArtistExist(artistArrayList, fillNameArtist.getText())) {
                if (editArtist != null) {
                    ArrayList<Artist> oldArrayList = new ArrayList<>();
                    ArrayList<Artist> newArrayList = new ArrayList<>();
                    oldArrayList = artistArrayList;
                    for (Artist artist : oldArrayList) {
                        if (artist.getName1().equals(editArtist.getName1()) && artist.getName2().equals(editArtist.getName2()) && artist.getInfomation().equals(editArtist.getInfomation())) {
                            System.out.println("delete old edit");
                        } else {
                            newArrayList.add(artist);
                        }
                    }
                    artistArrayList = newArrayList;
                }
                if (changePhoto) {
                    this.saveArtist();
                } else {
                    if (changePhoto = AlertBox.display("missing photo?", "Saving without upload photo?")) {
                        this.saveArtist();
                    } else {
                        System.out.println("save cancel");
                    }
                }
            } else {
                AlertBox.displayAlert("Upload Fail!", "This artist already exist");
            }
            }
            else {
                AlertBox.displayAlert("Upload Fail!", "Plese complete the form.");
            }
        });

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(e -> {
            stage.close();
        });

        HBox hboxSaveCancel = new HBox(20);
        hboxSaveCancel.setAlignment(Pos.BOTTOM_RIGHT);
        hboxSaveCancel.getChildren().addAll(saveBtn, cancelBtn);

        detail.getChildren().addAll(title, photo, imageBtn, gridPane, hboxSaveCancel);
        totalDetail.getChildren().addAll(detail, exitButton());
    }
    
    private void setupThaiPane() {
        
        VBox detail = new VBox(30);
        detail.setAlignment(Pos.CENTER);
        
        Button imageBtn = new Button("เพิ่มรูปภาพ");
        imageBtn.getStyleClass().add("buybtn"); //CSS
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
            if (filePath != null) {
                //Try to update the image by loading the new image
                try {
                    BufferedImage bufferedImage = ImageIO.read(filePath);
                    image = SwingFXUtils.toFXImage(bufferedImage, null);
                    this.photo.setImage(image);

                } catch (IOException ex) {
                    System.out.println("UploadArtistPopUp : IOExeption upload picture in DetailUpPopSong");
                }
                changePhoto = true;
            } else {
                System.out.println("upload cancel");
            }
        });

        photo.setFitHeight(200);
        photo.setFitWidth(200);
        photo.setPreserveRatio(true);

        //Upload Detail Artist
        //Artist
        Label nameArtist = new Label("ชื่อ");
        fillNameArtist.setPromptText("(สำหรับการแสดง)");
        fillNameArtist.setPrefWidth(300);

        //Artist (Optional) 
        Label nameArtist2 = new Label("ชื่อสำรอง");
        fillNameArtist2.setPromptText("(สำหรับการค้นหาเพิ่มเติม)");
        fillNameArtist2.setPrefWidth(300);

        //Detail
        Label detailSong = new Label("รายละเอียด");
        fillDetailArtist.setPromptText("อธิบายเกี่ยวกับศิลปิน");
        fillDetailArtist.setPrefWidth(200);

        fillNameArtist.getStyleClass().add("detailUploadTextFill"); //CSS
        fillNameArtist2.getStyleClass().add("detailUploadTextFill"); //CSS
        fillDetailArtist.getStyleClass().add("detailUploadTextFill"); //CSS

        Label colon1 = new Label(":");
        Label colon2 = new Label(":");
        Label colon3 = new Label(":");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.getStyleClass().add("detailUpload"); //CSS
        GridPane.setHalignment(nameArtist, HPos.RIGHT);
        GridPane.setHalignment(nameArtist2, HPos.RIGHT);
        GridPane.setHalignment(detailSong, HPos.RIGHT);
        GridPane.setConstraints(nameArtist, 0, 0);
        GridPane.setConstraints(nameArtist2, 0, 1);
        GridPane.setConstraints(detailSong, 0, 2);
        GridPane.setConstraints(colon1, 1, 0);
        GridPane.setConstraints(colon2, 1, 1);
        GridPane.setConstraints(colon3, 1, 2);
        GridPane.setConstraints(fillNameArtist, 2, 0);
        GridPane.setConstraints(fillNameArtist2, 2, 1);
        GridPane.setConstraints(fillDetailArtist, 2, 2);
        gridPane.getChildren().addAll(nameArtist, nameArtist2, detailSong,
                colon1, colon2, colon3,
                fillNameArtist, fillNameArtist2, fillDetailArtist);

        Button saveBtn = new Button("บันทึก");
        saveBtn.getStyleClass().add("savebtn"); // borrow...
        saveBtn.setOnMouseClicked(e -> {
            if(!fillNameArtist.getText().isEmpty() && !fillDetailArtist.getText().isEmpty())
            {
            try {
                artistArrayList = ReadWriteFile.readFileArist(artistFile);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("UploadArtistPopUp : IOExeption read file in DetailUpPopArtist");
            }
            if (!checkArtistExist(artistArrayList, fillNameArtist.getText())) {
                if (editArtist != null) {
                    ArrayList<Artist> oldArrayList = new ArrayList<>();
                    ArrayList<Artist> newArrayList = new ArrayList<>();
                    oldArrayList = artistArrayList;
                    for (Artist artist : oldArrayList) {
                        if (artist.getName1().equals(editArtist.getName1()) && artist.getName2().equals(editArtist.getName2()) && artist.getInfomation().equals(editArtist.getInfomation())) {
                            System.out.println("delete old edit");
                        } else {
                            newArrayList.add(artist);
                        }
                    }
                    artistArrayList = newArrayList;
                }
                if (changePhoto) {
                    this.saveArtist();
                } else {
                    if (changePhoto = AlertBox.display("missing photo?", "Saving without upload photo?")) {
                        this.saveArtist();
                    } else {
                        System.out.println("save cancel");
                    }
                }
            } else {
                AlertBox.displayAlert("Upload Fail!", "This artist already exist");
            }
            }
            else {
                AlertBox.displayAlert("Upload Fail!", "Plese complete the form.");
            }
        });

        Button cancelBtn = new Button("ยกเลิก");
        cancelBtn.getStyleClass().add("cancelbtn");
        cancelBtn.setOnMouseClicked(e -> {
            stage.close();
        });

        HBox hboxSaveCancel = new HBox(20);
        hboxSaveCancel.setAlignment(Pos.BOTTOM_RIGHT);
        hboxSaveCancel.getChildren().addAll(saveBtn, cancelBtn);

        detail.getChildren().addAll(title, photo, imageBtn, gridPane, hboxSaveCancel);
        totalDetail.getChildren().addAll(detail, exitButton());
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

    private boolean checkArtistExist(ArrayList<Artist> arrayList, String name1) {
        boolean i = false;
        for (Artist artist : arrayList) {
            if (artist.getName1().equals(name1)) {
                i = true;
            } else {
                i = false;
            }
        }
        return i;
    }

    private void saveArtist() {
        artistArrayList.add(new Artist(fillNameArtist.getText(), fillNameArtist2.getText(), fillDetailArtist.getText(), image));
        try {
            ReadWriteFile.writeFileArtist(artistFile, artistArrayList);
        } catch (IOException ex) {
            System.out.println("UploadArtistPopUp : IOExeption write file in DetailUpPopArtist");
        }
        Admin_UI.totalArtistPane.getChildren().remove(0);
        Admin_UI.totalArtistPane.getChildren().add(Admin_UI.updateScrollArtistPane(""));
        stage.close();
    }
}
