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
public class SearchSystemAddSong implements ChangeListener<String>{
    
    FilteredList<AddSong> filterData;
    

    public SearchSystemAddSong(FilteredList<AddSong> filterData) {
        this.filterData = filterData;
    }

    public SearchSystemAddSong() {
    }
    
   

    public FilteredList<AddSong> getFilterData() {
        return filterData;
    }

    public void setFilterData(FilteredList<AddSong> filterData) {
        this.filterData = filterData;
    }
    

    @Override
    public void changed(ObservableValue<? extends String> ObservableValue, String oldValue, String newValue) {
       this.filterData.setPredicate((user) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    
                    String lowerCase = newValue.toLowerCase();
                    
                    
                    if (user.getSong().getNameSong().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    else if (user.getSong().getArtistSong().toLowerCase().contains(lowerCase)) {
                        return true;
                    }
                    else if (user.getSong().getDetailSong().toLowerCase().contains(lowerCase)){
                        return true;
                    }
                    return false;
                });
    }

    
        
    
}
