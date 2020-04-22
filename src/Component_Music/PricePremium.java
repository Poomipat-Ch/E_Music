/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sirawit
 */
public class PricePremium implements Serializable {
    
    
    private int discountPercent;
    private static File price = new File("src/data/price.dat");
    
        public PricePremium() {
            discountPercent = 25; //Default
        }

        public PricePremium(int discountPercent) {
            this.discountPercent = discountPercent;
        }

        public int getDiscountPercentInt() {
            return discountPercent;
        }

        public void setDiscountPercentInt(int discountPercent) {
            this.discountPercent = discountPercent;
        }

    
        public static ObservableList<PricePremium> getPriceList() throws IOException, ClassNotFoundException  {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(price));

        ObservableList list = FXCollections.observableArrayList();
        
            for (PricePremium prices : (ArrayList<PricePremium>) in.readObject()) {
                list.add(prices);
            }

            return list;
        }
}
