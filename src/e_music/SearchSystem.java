/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package e_music;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;

/**
 *
 * @author poomi
 */
public class SearchSystem implements ChangeListener<String>{
    
    FilteredList<UserAccount> filterData;

    public SearchSystem(FilteredList<UserAccount> filterData) {
        this.filterData = filterData;
    }

    public SearchSystem() {
    }
    
   

    public FilteredList<UserAccount> getFilterData() {
        return filterData;
    }

    public void setFilterData(FilteredList<UserAccount> filterData) {
        this.filterData = filterData;
    }
    

    @Override
    public void changed(ObservableValue<? extends String> ObservableValue, String oldValue, String newValue) {
       this.filterData.setPredicate((user) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    String lowerCase = newValue.toLowerCase();
                    
                    
                    if (user.getUserName().contains(newValue)) {
                        return true;
                    }
                    else if (user.getFirstName().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    else if (user.getLastName().toLowerCase().contains(lowerCase)){
                        return true;
                    }
                    return false;
                });
    }


        
    
}
