/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author poomi
 */
public class UserAccount {
 
   private LongProperty id;
   private StringProperty userName;
   private StringProperty email;
   private StringProperty firstName;
   private StringProperty lastName;
   private BooleanProperty active;
 
   public UserAccount(Long id, String userName, String email, //
           String firstName, String lastName, boolean active) {
       this.id = new SimpleLongProperty(id);
       this.userName = new SimpleStringProperty(userName);
       this.email = new SimpleStringProperty(email);
       this.firstName = new SimpleStringProperty(firstName);
       this.lastName = new SimpleStringProperty(lastName);
       this.active = new SimpleBooleanProperty(active);
   }
 
   public Long getId() {
       return id.getValue();
   }
 
   public void setId(Long id) {
       this.id.setValue(id);
   }
 
   public String getUserName() {
       return userName.getValue();
   }
 
   public void setUserName(String userName) {
       this.userName.setValue(userName);
   }
 
   public String getEmail() {
       return email.getValue();
   }
 
   public void setEmail(String email) {
       this.email.setValue(email);
   }
 
   public String getFirstName() {
       return firstName.getValue();
   }
 
   public void setFirstName(String firstName) {
       this.firstName.setValue(firstName);
   }
 
   public String getLastName() {
       return lastName.getValue();
   }
 
   public void setLastName(String lastName) {
       this.lastName.setValue(lastName);
   }
 
   public boolean isActive() {
       return active.getValue();
   }
 
   public void setActive(boolean active) {
       this.active.setValue(active);
   }
   
   public static ObservableList<UserAccount> getMusicList() {
 
      UserAccount user1 = new UserAccount(1L, "smith", "smith@gmail.com", //
              "Susan", "Smith", true);
      UserAccount user2 = new UserAccount(2L, "mcneil", "mcneil@gmail.com", //
              "Anne", "McNeil", true);
      UserAccount user3 = new UserAccount(3L, "white", "white@gmail.com", //
              "Kenvin", "White", false);
 
      ObservableList<UserAccount> list = FXCollections.observableArrayList(user1, user2, user3);
    
      return list;
    }
   
      public static ObservableList<UserAccount> getMyMusicList() {

      ObservableList<UserAccount> list = FXCollections.observableArrayList(
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false),
              new UserAccount(1L, "asd", "smith@gmail.com", //
              "Susssssan", "Smidddth", true),
              new UserAccount(2L, "mcnasdeil", "mcneil@gmail.com", //
              "Anne", "McNeil", true),
              new UserAccount(3L, "whidsaaate", "white@gmail.com", //
              "Kedddnvin", "White", false)
              
              );
      return list;
    }
   
}
