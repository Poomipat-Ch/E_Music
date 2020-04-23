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
 * @author poomi
 */
public class SearchSystem implements ChangeListener<String>{
    
    private FilteredList<Song> filterData;
    

    public SearchSystem(FilteredList<Song> filterData) {
        this.filterData = filterData;
    }

    public SearchSystem() {
    }
    
   

    public FilteredList<Song> getFilterData() {
        return filterData;
    }

    public void setFilterData(FilteredList<Song> filterData) {
        this.filterData = filterData;
    }
    

    @Override
    public void changed(ObservableValue<? extends String> ObservableValue, String oldValue, String newValue) {
       this.filterData.setPredicate((user) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    String lowerCase = newValue.toLowerCase();
                    
                    
                    if (user.getNameSong().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    else if (user.getArtistSong().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    else if (user.getDetailSong().toLowerCase().contains(lowerCase)){
                        return true;
                    }
                    return false;
                });
    }

    
        
    
}
