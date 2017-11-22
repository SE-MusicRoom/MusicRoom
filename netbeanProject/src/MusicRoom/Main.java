/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Main instance;
    
    // Element
    private Stage stage;
    private boolean popupOpen;
    
    // Properties
    private final double MINIMUM_WINDOW_WIDTH = 1024;
    private final double MINIMUM_WINDOW_HEIGHT = 700;
    
    // DB
    private List<User> users;
    private List<Instrument> instruments;
    private List<RoomTemplate> roomTemplete;
    private List<Booking> bookings;
    
    // Reservation
    private User currentUser;
    private RoomTemplate currentRoom;
    private List<Calendar> currentTimeTable;
    private float currentPrice;
   
    

    
    public Main() {
        Main.instance = this;
        popupOpen = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(instance == null) {
            Application.launch(Main.class, (java.lang.String[])null);
            
        } else {
            // Error
        }
        
    }
    
 

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("MusicRoom");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            
            updateUserDB();
            
            gotoLogin();
            primaryStage.show();
            primaryStage.setResizable(false);
        
//            updateBookingDB();
//            updateInstrumentDB();
//            updateRoomTemplateDB();
        
        } catch (Exception ex) {
            
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    // Getters Setters    
    public static Main getInstance() {
        return instance;
    }
    public User getLoggedUser() {
        return currentUser;
    }
    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }
    public RoomTemplate getCurrentRoom() {
        return currentRoom;
    }

    public Instrument getInstrument(long id) {
        if(this.instruments == null)
            updateInstrumentDB();
        for (int i = 0; i < instruments.size(); i++) {
            Instrument o = instruments.get(i);
            if(o.getId() == id)
                return o;
        }
        return null;
    }
    public Instrument getInstrument(String name,String model) {
        if(this.instruments == null)
            updateInstrumentDB();
        for (int i = 0; i < instruments.size(); i++) {
            Instrument o = instruments.get(i);
            if(o.getName().equals(name) && o.getModel().equals(model))
                return o;
        }
        return null;
    }
    
    public List<Instrument> getInstruments() {
        if(this.instruments == null)
            updateInstrumentDB();
        return instruments;
    }
    public List<User> getUsers() {
        if(this.users == null)
            updateUserDB();
        return users;
    }
    public List<RoomTemplate> getRoomTemplete() {
        if(this.roomTemplete == null)
            updateRoomTemplateDB();
        return roomTemplete;
    }
    public List<Booking> getBookings() {
        if(this.bookings == null)
            updateBookingDB();
        return bookings;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentRoom(RoomTemplate currentRoom) {
        this.currentRoom = currentRoom;
    }
    public void setCurrentTimeTable(List<Calendar> currentTimeTable) {
        this.currentTimeTable = currentTimeTable;
    }


    public void updateUserDB() {
        this.users = (List<User>) DatabaseManager.getInstance().fetchAllUser();
        if(this.users == null) {
            showErrorPopup("Connection Error", "User fetching error :(\nClick confirm to try again","REFETCH_USER");
        }
        System.out.println("Fetch Main User:" + users.size());
        
    }   
    public void updateBookingDB() {
        this.bookings = (List<Booking>) DatabaseManager.getInstance().fetchAllBooking();
        if(this.bookings == null) {
            showErrorPopup("Connection Error", "Bookings fetching error :(\nClick confirm to try again","REFETCH_BOOKING");
        }
        System.out.println("Fetch Main Booking:" + bookings.size());
    }
    public void updateInstrumentDB() {
        this.instruments =  DatabaseManager.getInstance().fetchAllInstrument();
        if(this.instruments == null) {
            showErrorPopup("Connection Error", "Bookings fetching error :(\nClick confirm to try again","REFETCH_INSTRUMENT");
        }
        System.out.println("Fetch Main Instrument:" + instruments.size());
    }
    public void updateRoomTemplateDB() {
        this.roomTemplete =  DatabaseManager.getInstance().fetchAllRoomTemplate();
        if(this.roomTemplete == null) {
            showErrorPopup("Connection Error", "Bookings fetching error :(\nClick confirm to try again","REFETCH_ROOMTEMPLATE");
        }
        System.out.println("Fetch Main Template:" + roomTemplete.size());
    }
    public boolean isPopupOpen() {
        return popupOpen;
    }
    public void setPopupOpen(boolean isPopup) {
        this.popupOpen = isPopup;
    }

                
            
            

    



    public boolean userLogging(String userId, String password){
        for(int i=0;i<users.size();i++) {
            if(users.get(i).getUsername().equals(userId)) {
                if(users.get(i).getPassword().equals(password)) {
                    currentUser = users.get(i);

                    if(i!=0) // normal user
                        gotoMainMenu();
                    else    // admin
                        gotoAdmin();
                    
                    showPopup("Welcome "+currentUser.getName(),"Let's play some music !");
                    
                    return true;
                } else
                    return false;
            }
        }
        return false;

    }
    
    public void userLogout(){
        currentUser = null;
        gotoLogin();
    }
    

    public Booking createBooking() {
        Booking newBooking = new Booking(currentRoom, currentTimeTable, currentUser,currentPrice);
        return newBooking;
    } 
    
    public User createUser(String username ,String password ,String name ,String surname ,
                           String email ,Band band) {
        User newUser = new User(username ,password ,name, surname ,email ,band);
        
        currentUser = newUser;
        return newUser;
    }
    


    // Popups
    public PopupController showPopup(String title,String detail) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
        AnchorPane page = null;
        AnchorPane root = (AnchorPane)stage.getScene().getRoot();
        try {
            page = (AnchorPane) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        root.getChildren().add(page);
        PopupController pop = (PopupController)loader.getController();
        pop.setApp(title, detail, (AnchorPane)stage.getScene().getRoot(),page);
        popupOpen = true;
        return pop;
    }
    public void showErrorPopup(String title,String detail,String event) {
        PopupController pop = showPopup(title,detail);
        pop.addEventToButton(event);
    }

    // Scene Control
    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login.fxml");
            login.setApp();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void gotoMainMenu() {
        try {
            MainMenuController reg = (MainMenuController) replaceSceneContent("mainmenu.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void gotoAdmin() {
        try {
            AdminMenuController reg = (AdminMenuController) replaceSceneContent("controlPanel.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void gotoRegister() {
        try {
            RegisterController reg = (RegisterController) replaceSceneContent("register.fxml");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void gotoRegisterConfirm() {
        try {
            RegisterConfirmController reg = (RegisterConfirmController) replaceSceneContent("confirm_register.fxml");
            reg.setApp(currentUser);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
            //page.setStyle("base.css");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(page, 1024, 700);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public void sendEmail(String title,String msg) {
        
        final String username = "kmitlmusicroom@gmail.com";
	final String password = "kmitlmusicr00m";

	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
        
        // Recipient's email ID needs to be mentioned.
      String to = currentUser.getEmail();

      // Sender's email ID needs to be mentioned
      String from = username;

      Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
                        @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(title);
			message.setContent(msg.replaceAll("\n", "<br>"),"text/html; charset=UTF-8");

			Transport.send(message);

			System.out.println("Email sent...");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }


    
    
}
