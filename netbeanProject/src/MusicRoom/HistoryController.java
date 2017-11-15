/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import MusicRoom.entity.RoomTemplate;
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
        List<Booking> books = Main.getInstance().getCurrentUser().getBookedTimes();
        for (int i = 0; i < books.size(); i++) {
            historyScroll.getChildren().add(copyHistoryToken(books.get(i)));
        }
    }
    
    public void setApp(HistoryController parent){
        this.parent = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    private StackPane copyHistoryToken(Booking book) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("history.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StackPane newToken = (StackPane)loader.getNamespace().get("historyToken");
        for (int i = 0; i < newToken.getChildren().size(); i++) {
            System.out.println(newToken.getChildren().get(i).getId());
        }
        
        
        HistoryController cus = (HistoryController)loader.getController();
        cus.setApp(this);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
        cus.setTokenData(format1.format(book.getCreateTime().getTime()),
                         book.getRoom().getName(),
                         format2.format(book.getTimeTable().get(0).getTime())+"-"+book.getTimeTable().get(0).get(Calendar.HOUR_OF_DAY)+":59",
                         "THB "+String.valueOf(book.getPrice()),
                         "OK");
        
        return newToken;
    }
    
    protected void setTokenData(String date,String template,String time,String price,String status) {
        this.date.setText(date);
        this.template.setText(template);
        this.time.setText(time);
        this.price.setText(price);
        this.status.setText(status);
    }
    
    @FXML
    void onFind(ActionEvent event) {

    }

    @FXML
    void onCancel(ActionEvent event) {

    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }


    
}
