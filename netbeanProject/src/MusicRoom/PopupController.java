/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML's Popup Box Controller.
 * @author SE-MUSICROOM
 */
public class PopupController extends AnchorPane implements Initializable {
    
    @FXML    private Button button1;
    @FXML    private Button button2;

    @FXML    private Text titleTxt;
    @FXML    private Text detailTxt;
    @FXML    private ScrollPane detailScroll;
    
    @FXML    private Rectangle bgrect;
    @FXML    private AnchorPane titleBox;
        
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
    private final EventHandler<ActionEvent> SUCCESS_CONFIRM = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            parent.getChildren().remove(me);
            Main.getInstance().setPopupOpen(false);
            Main.getInstance().createConfirmedBooking();
        }
    }; 
    
    /**
    *  Called by main
    */
    public void setApp(String title,String detail,AnchorPane parent,AnchorPane me){
        titleTxt.setText(title);
        detailTxt.setText(detail);
        this.parent = parent;
        this.me = me;
    }

    /**
    *  init
    */
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
        
        button1.requestFocus();
    }
    
    /**
    *  OK button handler
    */
    public void onClickOK(ActionEvent event) {
        parent.getChildren().remove(me);
        Main.getInstance().setPopupOpen(false);
    }
    /**
    *  Cancel button handler
    */
    public void onClickCANCEL(ActionEvent event) {
        parent.getChildren().remove(me);
        Main.getInstance().setPopupOpen(false);
    }
    
    /**
    *  Add specified handler to OK button of Error popup
    */
    public void addEventToButton(String event) {
        switch (event) {
            case "REFETCH_USER":
                button1.setOnAction(REFETCH_USER);
                break;
            case "REFETCH_BOOKING":
                button1.setOnAction(REFETCH_BOOKING);
                break;
            case "REFETCH_INSTRUMENT":
                button1.setOnAction(REFETCH_INSTRUMENT);
                break;
            case "REFETCH_ROOMTEMPLATE":
                button1.setOnAction(REFETCH_ROOMTEMPLATE);
                break;
            case "SUCCESS_CONFIRM":
                button1.setOnAction(SUCCESS_CONFIRM);
                button2.setVisible(true);
                break;
            default:
                break;
        }
    } 
}