/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author YAY
 */
public class TimeSelectController extends AnchorPane implements Initializable{
    
    @FXML
    private DatePicker datePicker;
    
    
    
    private MainMenuController mainmenu;
    private List<Calendar> selectedTimes;
    private LocalDate currentDate;
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.selectedTimes = new ArrayList<Calendar>();
        listTime(-1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       datePicker.setValue(LocalDate.now());
       currentDate = LocalDate.now();
    }
    
    
    public void onClickDatePicker(ActionEvent event) {
        //Date currentDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        for (int i = 0; i < selectedTimes.size(); i++) {
            LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(d.isEqual(datePicker.getValue())) {
                listTime(i);
                return;
            }
            
        }
        
        
        
    }
    
    private void listTime(int dayi) {
        if(dayi<0) {
            //List<Booking> 
        }
    }
    
    public void onClickTime(ActionEvent event) {
        Button timeSelectedBtn = (Button)event.getSource();
        String timeSelectedName = timeSelectedBtn.getId();
        int timeSelectedID = Integer.parseInt(timeSelectedName.split("_")[1]);
        System.out.println(timeSelectedID);
        timeSelectedBtn.getStyleClass().add("full");
        
        
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        newCalendar.set(Calendar.HOUR_OF_DAY, timeSelectedID);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        
        System.out.println(newCalendar);
        selectedTimes.add(newCalendar);
    }
    
    public void onClickConfirm(ActionEvent event) {
        Main.getInstance().setCurrentTimeTable(selectedTimes);
        
        Booking ssss = Main.getInstance().createBooking();
        System.out.println(ssss);
        DatabaseManager.getInstance().addBooking(ssss);
        System.out.println(DatabaseManager.getInstance().fetchAllBooking().get(0));
        
        
        mainmenu.hideIncludePane();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
}
