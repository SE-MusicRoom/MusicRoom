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
    private List<Integer> selectedHours;
    private int selectedHoursSize;
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.selectedTimes = new ArrayList<Calendar>();
        this.selectedHours = new ArrayList<Integer>();
        listTime(-1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       datePicker.setValue(LocalDate.now());
       currentDate = LocalDate.now();
    }
    
    
    public void onClickDatePicker(ActionEvent event) {
        commitDay();
        currentDate = datePicker.getValue();
        //Date currentDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        for (int i = 0; i < selectedTimes.size(); i++) {
            LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(d.isEqual(currentDate)) {
                selectedHours.add(selectedTimes.get(i).get(Calendar.HOUR_OF_DAY));
            }
            
        }
        
        System.out.println(selectedHours);
        
    }
    
    private void listTime(int dayi) {
        if(dayi<0) {
            //List<Booking> 
        }
        
        List<Booking> allBookings = DatabaseManager.getInstance().fetchAllBooking();
        for (int i = 0; i < allBookings.size(); i++) {
            Booking booking = allBookings.get(i);
            for (int j = 0; j < booking.getTimeTable().size(); j++) {
                Calendar calendar = booking.getTimeTable().get(i);
                
            }
        }
        
        
    }
    
    private int findDuplicateHour(int hour) {
        for (int j = 0; j < selectedTimes.size(); j++) { // check if already in selectedTimes
            LocalDate d = selectedTimes.get(j).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(d.isEqual(currentDate) && selectedTimes.get(j).get(Calendar.HOUR_OF_DAY) == hour)
                return j;
        }
        return -1;
    }
    
    private void commitDay() {
        for (int i = 0; i < selectedHours.size(); i++) {
            
            if(findDuplicateHour(selectedHours.get(i))>-1) // check if already in selectedTimes
                continue;
            
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.setTime(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newCalendar.set(Calendar.HOUR_OF_DAY, selectedHours.get(i));
            newCalendar.set(Calendar.MINUTE, 0);
            newCalendar.set(Calendar.SECOND, 0);

            
            selectedTimes.add(newCalendar);
        }
        selectedHours.clear();
        for (int i = 0; i < selectedTimes.size(); i++) {
            System.out.println(i + ": "+ selectedTimes.get(i).get(Calendar.HOUR_OF_DAY) + " " + selectedTimes.get(i).get(Calendar.DAY_OF_YEAR));
        }
    }
    
    public void onClickTime(ActionEvent event) {
        Button timeSelectedBtn = (Button)event.getSource();
        String timeSelectedName = timeSelectedBtn.getId();
        int timeSelectedID = Integer.parseInt(timeSelectedName.split("_")[1]);
        
        if(selectedHours.indexOf(timeSelectedID)<0) { //add
            timeSelectedBtn.getStyleClass().add("full");

            selectedHours.add(timeSelectedID);
        } else { // remove
            int j = findDuplicateHour(timeSelectedID);
            if(j>-1)
                selectedTimes.remove(j);
            
            for (int i = 0; i < selectedTimes.size(); i++) { // remove in selectedTimes
                LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(d.isEqual(currentDate) && selectedTimes.get(i).get(Calendar.HOUR_OF_DAY) == timeSelectedID) 
                    selectedTimes.remove(i);
            }
            selectedHours.remove(Integer.valueOf(timeSelectedID)); // remove in selectedHours
            
        }
        System.out.println(selectedHours);
    }
    
    public void onClickConfirm(ActionEvent event) {
        commitDay();
        Main.getInstance().setCurrentTimeTable(selectedTimes);
        
        Booking ssss = Main.getInstance().createBooking();
        System.out.println(ssss);
        DatabaseManager.getInstance().addBooking(ssss);
        
        
        mainmenu.hideIncludePane();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
}
