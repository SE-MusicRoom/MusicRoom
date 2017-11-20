/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import MusicRoom.entity.CustomRoomTemplate;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author SE-MUSICROOM
 */
public class TimeSelectController extends AnchorPane implements Initializable{
    
    @FXML
    private DatePicker datePicker;
    
    @FXML    private Button T_10_Btn;
    @FXML    private Button T_11_Btn;
    @FXML    private Button T_12_Btn;
    @FXML    private Button T_13_Btn;
    @FXML    private Button T_14_Btn;
    @FXML    private Button T_15_Btn;
    @FXML    private Button T_16_Btn;
    @FXML    private Button T_17_Btn;
    @FXML    private Button T_18_Btn;
    @FXML    private Button T_19_Btn;
    @FXML    private Button T_20_Btn;
    @FXML    private Button T_21_Btn;
    @FXML    private Button T_22_Btn;
    @FXML    private Button T_23_Btn;
    
    
        @FXML
    private ImageView SummaryImgView;
        
   @FXML
    private Text totalTxt;

    @FXML
    private VBox selectedBox;
    
    private MainMenuController mainmenu;
    private List<Calendar> notAvailableTimes;
    private List<Calendar> selectedTimes;
    private LocalDate currentDate;
    private Button[] TimeBtn;
    private float total;
    
    final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            int na = 0;
                            for (int i = 0; i < selectedTimes.size(); i++) {
                                LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                if(d.isEqual(item)) {
                                    this.getStyleClass().add("selected");
                                    return;
                                }
                            }
                            this.getStyleClass().remove("selected");
                            for (int i = 0; i < notAvailableTimes.size(); i++) {
                                LocalDate d = notAvailableTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                if(d.isEqual(item)) {
                                    na++;
                                    if(na==14) {
                                        this.getStyleClass().add("danger");
                                        break;
                                    }
                                }
                                if(na>7)
                                    this.getStyleClass().add("warning");

                            }
                     }
              };
        }
    };
    
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.selectedTimes = new ArrayList<Calendar>();
        this.notAvailableTimes = new ArrayList<Calendar>();
        TimeBtn = new Button[]{null,null,null,null,null,null,null,null,null,null,T_10_Btn,T_11_Btn,T_12_Btn,T_13_Btn,T_14_Btn,T_15_Btn,T_16_Btn,T_17_Btn,T_18_Btn,T_19_Btn,T_20_Btn,T_21_Btn,T_22_Btn,T_23_Btn}; 
        SummaryImgView.setImage(new Image("MusicRoom/img/RoomTemplate/"+Main.getInstance().getCurrentRoom().getName()+".jpg",SummaryImgView.getFitWidth(), SummaryImgView.getFitHeight(), false,true));
        total = 0;
        totalTxt.setText("THB"+total);
        
        
        List<Booking> allBookings = Main.getInstance().getCurrentRoom().getBookings();
        for (int i = 0; i < allBookings.size(); i++) {
            Booking booking = allBookings.get(i);
            for (int j = 0; j < booking.getTimeTable().size(); j++) {
                Calendar calendar = booking.getTimeTable().get(j);
                notAvailableTimes.add(calendar);
            }
        }
        listTime();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       datePicker.setValue(LocalDate.now());
       datePicker.setDayCellFactory(dayCellFactory);
       currentDate = LocalDate.now();
       selectedBox.getChildren().clear();
       
    }
    
    
    public void onClickDatePicker(ActionEvent event) {
        currentDate = datePicker.getValue();
        listTime();
        //Date currentDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        for (int i = 0; i < selectedTimes.size(); i++) {
            LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(d.isEqual(currentDate)) {
                int hour = selectedTimes.get(i).get(Calendar.HOUR_OF_DAY);
                TimeBtn[hour].getStyleClass().add("selected");
            }
        }
        
        
    }
    
    private void listTime() {
        for (int i = 10; i < TimeBtn.length; i++) {
            //System.out.println(i+"set true");
            TimeBtn[i].setDisable(false);
            TimeBtn[i].getStyleClass().remove("full");
            TimeBtn[i].getStyleClass().remove("selected");
            TimeBtn[i].getStyleClass().add("available");
        }
        int counter = 0;
        for (int i = 0; i < notAvailableTimes.size(); i++) {
            LocalDate d = notAvailableTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(d.isEqual(currentDate)) {
                
                int hour = notAvailableTimes.get(i).get(Calendar.HOUR_OF_DAY);
                TimeBtn[hour].setDisable(true);

                TimeBtn[hour].getStyleClass().remove("available");
                TimeBtn[hour].getStyleClass().remove("selected");
                TimeBtn[hour].getStyleClass().add("full");
                counter++;
            }
                
                //selectedTimes.add(selectedTimes.get(j).get(Calendar.HOUR_OF_DAY));
        }
        System.out.println("Booked: "+counter);
        
    }
    

    
    private void updateSelectedTimeText() {
        selectedBox.getChildren().clear();
        int lastDay = -1;
        int lastHour = -1;
        //   13/02/60 (13:00 - 16:59)
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy (HH:mm - ");
        String toAdd = "";
        for(int i=0;i<selectedTimes.size();i++) {
            int thisHour = selectedTimes.get(i).get(Calendar.HOUR_OF_DAY);
            int thisDay = selectedTimes.get(i).get(Calendar.DATE);
            if((thisDay!=lastDay && lastDay !=-1) || (thisHour!=lastHour+1 && lastHour !=-1) || i==0 || i==selectedTimes.size()-1) {
                System.out.println(i);
                if(i!=0 && i!=selectedTimes.size()-1) { // if not first one
                    toAdd += lastHour + ":59)";
                    System.out.println("not first");
                }    
                else if(i==selectedTimes.size()-1 && i!=0) 
                    if(thisHour==lastHour+1 && thisDay==lastDay) { // if the last is continuous
                        toAdd += thisHour + ":59)";
                        System.out.println("conti");
                    } else {// if the last is new
                        System.out.println("last is new");
                        toAdd += lastHour + ":59)";
                        selectedBox.getChildren().add(new Text(toAdd));
                        toAdd = format1.format(selectedTimes.get(i).getTime()) + thisHour + ":59)";
                    }
                selectedBox.getChildren().add(new Text(toAdd));
                toAdd = format1.format(selectedTimes.get(i).getTime());
            }
            
            lastDay = thisDay;
            lastHour = thisHour;
        }
    }
    
    public void onClickTime(ActionEvent event) {
        Button timeSelectedBtn = (Button)event.getSource();
        String timeSelectedName = timeSelectedBtn.getId();
        int timeSelectedID = Integer.parseInt(timeSelectedName.split("_")[1]);
        
        //if(selectedHours.indexOf(timeSelectedID)<0) { //add
        //    
            
        //} else { // remove
            

            boolean isNew = true;
            for (int i = 0; i < selectedTimes.size(); i++) { // remove in selectedTimes
                LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(d.isEqual(currentDate) && selectedTimes.get(i).get(Calendar.HOUR_OF_DAY) == timeSelectedID) {
                    timeSelectedBtn.getStyleClass().remove("selected");
                    selectedTimes.remove(i);
                    isNew = false;
                    System.out.println("REMOVING");
                }
            }
            if(isNew) {
                timeSelectedBtn.getStyleClass().add("selected");
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.setTime(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                newCalendar.set(Calendar.HOUR_OF_DAY, timeSelectedID);
                newCalendar.set(Calendar.MINUTE, 0);
                newCalendar.set(Calendar.SECOND, 0);

                selectedTimes.add(newCalendar);
                System.out.println("ADDING");
            }
            //selectedHours.remove(Integer.valueOf(timeSelectedID)); // remove in selectedHours
            
        //}
        
        
            

        Collections.sort(selectedTimes, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar o1, Calendar o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        updateSelectedTimeText();
        
        System.out.println(selectedTimes);
        
        total = selectedTimes.size()*Main.getInstance().getCurrentRoom().getPrice();
        totalTxt.setText("THB"+total);
    }
    
    public void onClickConfirm(ActionEvent event) {
        
        if(selectedTimes.isEmpty()) {
            System.out.println("Error: Please add some times first");
            Main.getInstance().showPopup("Wait Wait!","Please choose your preferred time");
            return;
        }
        
        Main.getInstance().setCurrentTimeTable(selectedTimes);
        
        if(Main.getInstance().getCurrentRoom() instanceof CustomRoomTemplate)
            DatabaseManager.getInstance().addCustomRoom((CustomRoomTemplate) Main.getInstance().getCurrentRoom());
                    
        Booking newBook = Main.getInstance().createBooking();
        System.out.println(newBook);
        Main.getInstance().getCurrentUser().addBookedTime(newBook);
        DatabaseManager.getInstance().addBooking(newBook);
        Main.getInstance().getCurrentRoom().addBooking(newBook);
        
        mainmenu.gotoSuccess(newBook);
    }
    
    public void onClickBack(ActionEvent event) {
        if(Main.getInstance().getCurrentRoom() instanceof CustomRoomTemplate)
            mainmenu.gotoCustomize();
        else
            mainmenu.gotoTemplate();
    }
}
