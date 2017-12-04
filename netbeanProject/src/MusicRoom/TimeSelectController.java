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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML's Booking Time Selection Controller.
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
      
    @FXML    private ImageView SummaryImgView;
    @FXML    private Text totalTxt;
    @FXML    private VBox selectedBox;
    
    private MainMenuController mainmenu;
    private List<Calendar> notAvailableTimes;
    private List<Calendar> selectedTimes;
    private LocalDate currentDate;
    private Button[] TimeBtn;
    private float total;
    
    // Modify cells of calendar
    final Callback<DatePicker, DateCell> dayCellFactory = 
            new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            this.setDisable(empty || item.isBefore(LocalDate.now()));
                            
                            // Check selected time
                            int na = 0;
                            for (int i = 0; i < selectedTimes.size(); i++) {
                                LocalDate d = selectedTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                if(d.isEqual(item)) {
                                    this.getStyleClass().add("selected");
                                    return;
                                }
                            }
                            // Check unavailable (booked) hour
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
    
    
    /**
    * called by MainMenuController
    */
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.selectedTimes = new ArrayList<Calendar>();
        this.notAvailableTimes = new ArrayList<Calendar>();
        TimeBtn = new Button[]{null,null,null,null,null,null,null,null,null,null,T_10_Btn,T_11_Btn,T_12_Btn,T_13_Btn,T_14_Btn,T_15_Btn,T_16_Btn,T_17_Btn,T_18_Btn,T_19_Btn,T_20_Btn,T_21_Btn,T_22_Btn,T_23_Btn}; 
        SummaryImgView.setImage(Main.getInstance().getCurrentRoom().getImg()); //new Image("MusicRoom/img/RoomTemplate/"+r.getName()+".jpg",imgV.getFitWidth(), imgV.getFitHeight(), false,true)
        SummaryImgView.setFitHeight(SummaryImgView.getFitHeight());
        SummaryImgView.setFitWidth(SummaryImgView.getFitWidth());
        SummaryImgView.setPreserveRatio(false);
        total = 0;
        totalTxt.setText(total+"฿");
        
        
        List<Booking> allBookings = Main.getInstance().getCurrentRoom().getBookings();
        if(allBookings==null) {
            System.out.println("load book fail");
            mainmenu.hideIncludePane();
            return;
        }
        for (int i = 0; i < allBookings.size(); i++) {
            Booking booking = allBookings.get(i);
            for (int j = 0; j < booking.getTimeTable().size(); j++) {
                Calendar calendar = booking.getTimeTable().get(j);
                notAvailableTimes.add(calendar);
            }
        }
        listTime();
    }

    /**
    * called by init
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       datePicker.setShowWeekNumbers(false);
       datePicker.setEditable(false);
       datePicker.setValue(LocalDate.now());
       datePicker.setDayCellFactory(dayCellFactory);
       currentDate = LocalDate.now();
       selectedBox.getChildren().clear();
       
    }
    
    /**
    * Date picker click handler
    */
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
    
    /**
    * Set each TimeBtn
    */
    private void listTime() {
        for (int i = 10; i < TimeBtn.length; i++) {
            //System.out.println(i+"set true");
            TimeBtn[i].setDisable(false);
            TimeBtn[i].getStyleClass().remove("full");
            TimeBtn[i].getStyleClass().remove("selected");
            TimeBtn[i].getStyleClass().add("available");
        }
       
        // Set unavailable (booked) hour button
        for (int i = 0; i < notAvailableTimes.size(); i++) {
            LocalDate d = notAvailableTimes.get(i).getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(d.isEqual(currentDate)) {
                
                int hour = notAvailableTimes.get(i).get(Calendar.HOUR_OF_DAY);
                TimeBtn[hour].setDisable(true);

                TimeBtn[hour].getStyleClass().remove("available");
                TimeBtn[hour].getStyleClass().remove("selected");
                TimeBtn[hour].getStyleClass().add("full");
            } 
        }
        
        // Set unavailable (passed) hour button
        int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        for (int hour = 10; hour <= nowHour && currentDate.isEqual(LocalDate.now()); hour++) {
            TimeBtn[hour].setDisable(true);
        }
        
    }
    
    /**
    * Generate & Update SelectedTime text
    */
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
                        Text text = new Text(toAdd);
                        text.setStyle("-fx-font-size: 30px;");
                        selectedBox.getChildren().add(text);
                        toAdd = format1.format(selectedTimes.get(i).getTime()) + thisHour + ":59)";
                    }
                else if(selectedTimes.size()==1) { // first and only one
                    System.out.println("First only");
                    toAdd = format1.format(selectedTimes.get(i).getTime()) + thisHour + ":59)";
                }
                if(!toAdd.equals("")) {
                    Text text = new Text(toAdd);
                    text.setStyle("-fx-font-size: 30px;");
                    selectedBox.getChildren().add(text);
                }
                toAdd = format1.format(selectedTimes.get(i).getTime());
                
            }
            
            lastDay = thisDay;
            lastHour = thisHour;
        }
    }
    
    /**
    * Every time button handler
    */
    public void onClickTime(ActionEvent event) {
        Button timeSelectedBtn = (Button)event.getSource();
        String timeSelectedName = timeSelectedBtn.getId();
        int timeSelectedID = Integer.parseInt(timeSelectedName.split("_")[1]);
        

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

        Collections.sort(selectedTimes, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar o1, Calendar o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });

        updateSelectedTimeText();
        
        System.out.println(selectedTimes);
        
        total = selectedTimes.size()*Main.getInstance().getCurrentRoom().getPrice();
        Main.getInstance().setCurrentPrice(total);
        totalTxt.setText(total+"฿");
    }
    
    /**
    * Confirm button handler
    */
    public void onClickConfirm(ActionEvent event) {
        
        if(selectedTimes.isEmpty()) {
            System.out.println("Error: Please add some times first");
            Main.getInstance().showPopup("Wait Wait!","Please choose your preferred time");
            return;
        }
        
        Main.getInstance().setCurrentTimeTable(selectedTimes);
        
        Main.getInstance().showErrorPopup("Confirmation", "Confirm your reservation ?", "SUCCESS_CONFIRM");
    }
    
    /**
    * Back button handler
    */
    public void onClickBack(ActionEvent event) {
        if(Main.getInstance().getCurrentRoom() instanceof CustomRoomTemplate)
            mainmenu.gotoCustomize();
        else
            mainmenu.gotoTemplate();
    }
}
