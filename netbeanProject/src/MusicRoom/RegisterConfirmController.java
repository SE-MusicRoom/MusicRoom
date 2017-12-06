package MusicRoom;

import MusicRoom.entity.User;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML's Register Confirmation Controller.
 * @author SE-MUSICROOM
 */
public class RegisterConfirmController extends AnchorPane implements Initializable{


    @FXML    private PasswordField activateCode;
    @FXML    private Text emailTxt;
    
    private User user;
    private String activition;
    
    /**
    *  init
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    /**
    *  Called by main
    */
    public void setApp(User user){
        this.user = user;
        activition = encrypt(String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        
        emailTxt.setText(user.getEmail());
        onClickResend(null); // Send Email
    }
    
    /**
    *  Simple activation code encryption (nothing)
    */
    private String encrypt(String src) {
        return src;
    }
    
    /**
    *  Back button handler
    */
    public void onClickBack(ActionEvent event) {
        Main.getInstance().gotoLogin();
    }
    
    /**
    *  Confirm button handler
    */
    public void onClickConfirm(ActionEvent event) {
        if(activateCode.getText().equals( activition ) ) {
            DatabaseManager.getInstance().updateActivateUser(user);
            Main.getInstance().updateUserDB();
            Main.getInstance().gotoLogin();
        } else {
            System.out.println(activition);
            System.out.println(activateCode.getText());
            System.out.println("PW Wrong");
        }
            
    }
    
    /**
    *  Resend button handler
    */
    public void onClickResend(ActionEvent event) {
       Main.getInstance().sendEmail("Music Room Register Comfirmation",
                                "<b>ขอบคุณที่สมัคร,<br>"
				+ "<br><br> โชคดี"
                                + "<br> รหัสยืนยันคือ " + activition);
    }
    
}
