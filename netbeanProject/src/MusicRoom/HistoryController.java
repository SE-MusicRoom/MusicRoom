/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import MusicRoom.entity.RoomTemplate;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author YAY
 */
public class HistoryController extends AnchorPane implements Initializable {
    
        @FXML
    private TextField searchBox;
        
    private MainMenuController mainmenu;
    
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Booking> books = Main.getInstance().getCurrentUser().getBookedTimes();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i));
        }
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
    public void onTypeDone(ActionEvent event) {
        System.out.println(searchBox.getText());
    }
    
}
