/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.Booking;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML's Room Customization Controller.
 * @author SE-MUSICROOM
 */
public class SuccessReservationController extends AnchorPane implements Initializable {

    
    @FXML    private Text idtxt;
    @FXML    private Text emailtxt;
    
    private MainMenuController mainmenu;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    /**
    *  Called by MainMenuController
    */
    public void setApp(MainMenuController mainmenu, Booking newBooking){
        this.mainmenu = mainmenu;
        idtxt.setText(String.valueOf(newBooking.getID()));
        emailtxt.setText(Main.getInstance().getCurrentUser().getEmail());
        
        // Send activition e-mail
        Main.getInstance().sendEmail("Music Room Reservation Detail",
                                "<h1><span style=\"color: #00AA00;\"> เพิ่มการจองสำเร็จ ! </span></h1>"
                                + "<br> <b>รหัสจอง:</b> " + newBooking.getID()
                                + "<br> <b>ผู้จอง:</b> คุณ " + newBooking.getUser().toString()
                                + "<br> <b>รายละเอียดห้อง:</b> <pre>" + newBooking.getRoom().toString()+ "</pre>"
                                + "<br> <b>เวลา:</b> " + newBooking.getTimeTableString()
                                + "<br> <h1>ราคารวมทั้งหมด: <i>" + newBooking.getPrice() + "<i></h1>"
                                + "<br><br> <b>ขอบคุณที่ใช้บริการกับเรา, หากมีปัญหาสามารถตอบกลับอีเมลฉบับนี้ได้"
				+ "<br> Be yourself"
                                + "<br> SE-MusicRoom</b>");
    }
    
    /**
    *  Back button handler
    */
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
        mainmenu.clearAllState();
    }
    
}
