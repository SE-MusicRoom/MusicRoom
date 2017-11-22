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
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private ScrollPane detailScroll;
    
    @FXML
    private Rectangle bgrect;

    @FXML
    private Text detailTxt;

    @FXML
    private AnchorPane titleBox;
        
    private AnchorPane parent;
    private AnchorPane me;
    
    // Handlers
    private final EventHandler<ActionEvent> REFETCH_USER = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            parent.getChildren().remove(me);
            Main.getInstance().setPopupOpen(false);
            Main.getInstance().updateUserDB();
        }
    };
    private final EventHandler<ActionEvent> REFETCH_BOOKING = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            parent.getChildren().remove(me);
            Main.getInstance().setPopupOpen(false);
            Main.getInstance().updateBookingDB();
        }
    };
    private final EventHandler<ActionEvent> REFETCH_INSTRUMENT = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            parent.getChildren().remove(me);
            Main.getInstance().setPopupOpen(false);
            Main.getInstance().updateInstrumentDB();
        }
    };
    private final EventHandler<ActionEvent> REFETCH_ROOMTEMPLATE = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            parent.getChildren().remove(me);
            Main.getInstance().setPopupOpen(false);
            Main.getInstance().updateRoomTemplateDB();
        }
    };    
    
    public void setApp(String title,String detail,AnchorPane parent,AnchorPane me){
        titleTxt.setText(title);
        detailTxt.setText(detail);
        this.parent = parent;
        this.me = me;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), bgrect);
        ft1.setFromValue(0.1);
        ft1.setToValue(1.0);
        ft1.play();
        
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(200), titleBox);
        tt1.setFromX( -1000 );
        tt1.setToX( 0 );
        tt1.play();
        
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(200), detailScroll);
        tt2.setFromX( 1000 );
        tt2.setToX( 0 );
        tt2.play();
        
        button.requestFocus();
    }
    

    
    public void onClickOK(ActionEvent event) {
        parent.getChildren().remove(me);
        Main.getInstance().setPopupOpen(false);
    }

    public void addEventToButton(String event) {
        if(event.equals("REFETCH_USER"))
            button.setOnAction(REFETCH_USER);
        else if(event.equals("REFETCH_BOOKING"))
            button.setOnAction(REFETCH_BOOKING);
        else if(event.equals("REFETCH_INSTRUMENT"))
            button.setOnAction(REFETCH_INSTRUMENT);
        else if(event.equals("REFETCH_ROOMTEMPLATE"))
            button.setOnAction(REFETCH_ROOMTEMPLATE);
    }
    
    
    
    
    
}