/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HCARACH
 */
public class Account implements Serializable {
    private String name;
    private String email;
    private String password;
    private Date dOb;
    private String question;
    private String answer;
    
    public Account() {
    }

    public Account(String name, String email, String password, String question, String answer) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }
    
    public String name(){
        return name;
    }

    public String geteMail() {
        return email;
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
        return "Account{" + "username=" + email + ", password=" + password + ", answer=" + answer + '}';
    }
    
    
    
    
}
