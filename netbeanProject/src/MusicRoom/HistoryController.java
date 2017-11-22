/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author SE-MUSICROOM
 */
public class HistoryController extends AnchorPane implements Initializable {
    
    @FXML
    private TextField searchBox;
    
    

    @FXML
    private VBox historyScroll;

    //For token
    @FXML    private Text showid;
    @FXML    private Text date;
    @FXML    private Text template;
    @FXML    private Text time;
    @FXML    private Text price;
    @FXML    private Text status;

    private MainMenuController mainmenu;
    private HistoryController parent;
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
         historyScroll.getChildren().clear();
        List<Booking> books = Main.getInstance().getCurrentUser().getBookings();
        System.out.println("booksize: "+books.size());
        for (int i = 0; i < books.size(); i++) {
            
            //error booking check
            if(books.get(i).getTimeTable().isEmpty()) {
                //System.out.println("remove: "+books.get(i).getID());
                //Main.getInstance().getCurrentUser().removeBookedTime(books.get(i));
                continue;
            }
            StackPane hist = copyHistoryToken(books.get(i));
            hist.setId(String.valueOf(historyScroll.getChildren().size()));
            historyScroll.getChildren().add(hist);
            
        }
    }
    
    public void setApp(HistoryController parent){
        this.parent = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public VBox getHistoryScroll() {
        return historyScroll;
    }
    
    
    
    private StackPane copyHistoryToken(Booking book) {
        
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("history.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StackPane newToken = (StackPane)loader.getNamespace().get("historyToken");

        
        HistoryController cus = (HistoryController)loader.getController();
        cus.setApp(this);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        cus.setTokenData(String.valueOf(book.getID()),
                        format1.format(book.getCreateTime().getTime()),
                         book.getRoom().getName(),
                         format2.format(book.getTimeTable().get(0).getTime())+"-"+book.getTimeTable().get(0).get(Calendar.HOUR_OF_DAY)+":59",
                         "฿ "+String.valueOf(book.getPrice()),
                         "OK");
        
        return newToken;
    }
    
    protected void setTokenData(String id,String date,String template,String time,String price,String status) {
        this.showid.setText(id);
        this.date.setText(date);
        this.template.setText(template);
        this.time.setText(time);
        this.price.setText(price);
        this.status.setText(status);
    }
    
    @FXML
    void onFind(ActionEvent event) {
        historyScroll.getChildren().clear();
        List<Booking> books = Main.getInstance().getCurrentUser().getBookings();
        for (int i = 0; i < books.size(); i++) {
            if(!(books.get(i).toString()).replaceAll("[\\s|\\u00A0]+", "").toLowerCase().contains(searchBox.getText().replaceAll("[\\s|\\u00A0]+", "").toLowerCase()) && !searchBox.getText().replaceAll("[\\s|\\u00A0]+", "").equals(""))
                continue;
            StackPane newToken = copyHistoryToken(books.get(i));
            historyScroll.getChildren().add(newToken);
        }
    }

    @FXML
    void onCancel(ActionEvent event) {
        
        int selectedId = Integer.parseInt( showid.getText() );
        Booking selectedB = Main.getInstance().getBookings().stream().filter(item -> item.getID() == selectedId).findFirst().get();
        // Remove from db
        DatabaseManager.getInstance().removeBooking(selectedB);
        // Remove from room
        //Main.getInstance().getCurrentUser().getBookings().get(selectedId).getRoom().removeBooking(Main.getInstance().getCurrentUser().getBookings().get(selectedId));
        // Remove from user
        Main.getInstance().getCurrentUser().removeBookedTime(selectedB);

        parent.getHistoryScroll().getChildren().remove(((Button)event.getSource()).getParent().getParent());
        
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }


    
}
