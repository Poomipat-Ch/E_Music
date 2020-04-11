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

/**
 *
 * @author HCARACH Thanks a lot!!
 */
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
    private boolean isAdmin;
    
    private static File user = new File("src/data/user.dat");
    
    public Account() {
    }

    public Account(String name, String surname, String username, String email,
            String password,String gender, LocalDate dateOfBirth, String question, String answer, boolean isAdmin) {
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
        this.isAdmin = isAdmin;
    }
    
    public boolean getIsAdmin(){
        return isAdmin;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }
    public String name(){
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
    
    /**
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ObservableList<Account> getAccountList() throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(user));
 
        ObservableList list = FXCollections.observableArrayList();
        
        for (Account account : (ArrayList<Account>)in.readObject()) {
            list.add(account);
        }
    
  
        return list;
    }
    
    
}