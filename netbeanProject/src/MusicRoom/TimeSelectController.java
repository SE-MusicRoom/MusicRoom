/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
    private List<Calendar> selectedTime;
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.selectedTime = new ArrayList<Calendar>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    public void calandarCheck() {
        List<Calendar> test = new ArrayList<Calendar>();
        test.add(Calendar.getInstance());
        test.add(Calendar.getInstance());
        Main.getInstance().setCurrentTimeTable(test);
        
        Booking ssss = Main.getInstance().createBooking();
        System.out.println(ssss);
        DatabaseManager.getInstance().addBooking(ssss);
        System.out.println(DatabaseManager.getInstance().fetchAllBooking().get(0));
    }
    
    public void onClickDatePicker(ActionEvent event) {
        System.out.println(datePicker.getValue());
    }
    
    public void onClickTime(ActionEvent event) {
        Button timeSelectedBtn = (Button)event.getSource();
        String timeSelectedName = timeSelectedBtn.getId();
        int timeSelectedID = Integer.parseInt(timeSelectedName.split("_")[1]);
        System.out.println(timeSelectedID);
        timeSelectedBtn.getStyleClass().add("full");
        System.out.println(timeSelectedBtn.getStyleClass());
    }
    
    public void onClickConfirm(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
}
