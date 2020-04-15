/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Account implements Serializable {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String gender;
    private String question;
    private String answer;
    private LocalDate dateOfBirth;
    private String userRole;

    private int width, height;
    private int[][] data;

    private static File user = new File("src/data/user.dat");
    
    private ArrayList<Song> listSong = new ArrayList<>();
    
    private boolean firstSong = true;

    public Account() {
    }

    public Account(String name, String surname, String username, String email, String password, String gender, LocalDate dateOfBirth, String question, String answer, String userRole, Image image) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.question = question;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.question = question;
        this.answer = answer;
        this.userRole = userRole;
        this.setPhoto(image);
        
        listSong.add(new Song());
    }

    

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserRole() {
        return userRole;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String name() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Account{" + "username=" + username + ", password=" + password + ", answer=" + answer + '}';
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public void setPhoto(Image image) {
        width = ((int) image.getWidth());
        height = ((int) image.getHeight());
        data = new int[width][height];

        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = r.getArgb(i, j);
            }
        }
    }

    public Image getPhoto() {
        WritableImage img = new WritableImage(width, height);

        PixelWriter w = img.getPixelWriter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                w.setArgb(i, j, data[i][j]);
            }
        }

        return img;
    }

    /**
     *
     * @return @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<Account> getAccountList() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(user));

        ObservableList list = FXCollections.observableArrayList();

        for (Account account : (ArrayList<Account>) in.readObject()) {
            list.add(account);
        }

        return list;
    }
    
    public ObservableList<Song> getMyListSong() {
        ObservableList list = FXCollections.observableArrayList();

        for (Song song : listSong) {
            list.add(song);
        }

        return list;
    }
    
    public void addSong(Song song) {
        if(firstSong)
            listSong.remove(0);
        listSong.add(song);
        
        firstSong = false;
    }

    public boolean isFirstSong() {
        return firstSong;
    }
    
    

}
