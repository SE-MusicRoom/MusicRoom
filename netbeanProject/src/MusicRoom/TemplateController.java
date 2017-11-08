/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.RoomTemplate;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author YAY
 */
public class TemplateController extends AnchorPane implements Initializable {
    
    private MainMenuController mainmenu;
    
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < Main.getInstance().getRoomTemplete().size(); i++) {
            RoomTemplate r = Main.getInstance().getRoomTemplete().get(i);
            System.out.println(r);
        }
    }
    
    public void onClickCustomize(ActionEvent event) {
        mainmenu.gotoReservation();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
}
