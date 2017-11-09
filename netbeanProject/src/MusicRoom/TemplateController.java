/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Instrument;
import MusicRoom.entity.RoomTemplate;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author YAY
 */
public class TemplateController extends AnchorPane implements Initializable {
    
    private MainMenuController mainmenu;
    private TemplateController parent;
    
    @FXML
    private Text templateName;
    @FXML
    private AnchorPane templateToken;
    @FXML
    private GridPane templateGrid;
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        for (int i = 0; i < Main.getInstance().getRoomTemplete().size(); i++) {
            RoomTemplate r = Main.getInstance().getRoomTemplete().get(i);
            AnchorPane ac = copyTemplateToken(r.getName());

            if(i%3==0)
                templateGrid.addRow(i/3, ac);
            //else
                //templateGrid.add(ac,i%3,i/3);
            System.out.println(r);
        }
    }
    
    public void setApp(TemplateController parent){
        this.parent = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    private AnchorPane copyTemplateToken(String name) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("templateselect.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("templateToken");
        Text t0 = (Text) newToken.getChildren().get(4);
        t0.setText(name);
        
         // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        TemplateController cus = (TemplateController)loader.getController();
        cus.setApp(this);
        
        return newToken;
    }
    
    public void onClickCustomize(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
}
