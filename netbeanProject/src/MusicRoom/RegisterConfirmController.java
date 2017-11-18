/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.User;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author SE-MUSICROOM
 */
public class RegisterConfirmController extends AnchorPane implements Initializable{


    @FXML
    private PasswordField activateCode;
    
    @FXML
    private Text emailTxt;
    
    private User user;
    
    private String activition;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setApp(User user){
        this.user = user;
        activition = encrypt(String.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
        
        emailTxt.setText(user.getEmail());
        sendEmail();
    }
    
    
    private void sendEmail() {
        
        final String username = "kmitlmusicroom@gmail.com";
	final String password = "kmitlmusicr00m";

	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
        
        // Recipient's email ID needs to be mentioned.
      String to = user.getEmail();

      // Sender's email ID needs to be mentioned
      String from = user.getEmail();

      Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
                        @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("se_musicroom@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject("Music Room Register Comfirmation");
			message.setText("ขอบคุณที่สมัคร,"
				+ "\n\n โชคดี"
                                + "\n รหัสยืนยันคือ " + activition);

			Transport.send(message);

			System.out.println("Email sent...");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
    private String encrypt(String src) {
        return src;
    }
    
    public void onClickBack(ActionEvent event) {
        Main.getInstance().gotoLogin();
    }
    
    public void onClickConfirm(ActionEvent event) {
        if(activateCode.getText().equals( activition ) ) {
            DatabaseManager.getInstance().addUser(user);
            Main.getInstance().updateUserDB();
            Main.getInstance().gotoLogin();
        } else {
            System.out.println(activition);
            System.out.println(activateCode.getText());
            System.out.println("PW Wrong");
        }
            
    }
    
    public void onClickResend(ActionEvent event) {
       sendEmail();
    }
    
}
