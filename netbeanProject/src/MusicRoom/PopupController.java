/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author SE-MUSICROOM
 */
public class PopupController extends AnchorPane implements Initializable {
    
    @FXML
    private Button button;
    
    @FXML
    private Text titleTxt;

    @FXML
    private Text detailTxt;
        
    private AnchorPane parent;
    
    
    
    
    public void setApp(String title,String detail,AnchorPane parent){
        titleTxt.setText(title);
        detailTxt.setText(detail);
        this.parent = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void onClickOK(ActionEvent event) {
        parent.getChildren().remove(parent.getChildren().size()-1);
    }

    public void addEventToButton(EventHandler<ActionEvent> eh) {
        button.setOnAction(eh);
    }
    

    
}