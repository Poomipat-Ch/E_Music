/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component_Music;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author Sirawit
 */
public class SearchSystemAccount implements ChangeListener<String>{
    
    FilteredList<Account> filterData;

    public SearchSystemAccount(FilteredList<Account> filterData) {
        this.filterData = filterData;
    }

    public SearchSystemAccount() {
    }
    
   

    public FilteredList<Account> getFilterData() {
        return filterData;
    }

    public void setFilterData(FilteredList<Account> filterData) {
        this.filterData = filterData;
    }
    

    @Override
    public void changed(ObservableValue<? extends String> ObservableValue, String oldValue, String newValue) {
       this.filterData.setPredicate((user) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    String lowerCase = newValue.toLowerCase();
                    
                    
                    if (user.getName().contains(newValue)) {
                        return true;
                    }
                    else if (user.getUsername().toLowerCase().contains(lowerCase)){
                        return true;
                    }
                    else if (user.getSurname().toLowerCase().contains(lowerCase)){
                        return true;
                    }
                    else if (user.getEmail().toLowerCase().contains(lowerCase)){
                        return true;
                    }
              
                    return false;
                });
    }


        
    
}
