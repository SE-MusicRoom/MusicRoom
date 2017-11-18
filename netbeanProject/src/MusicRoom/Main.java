/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.*;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Main instance;
    
    private Stage stage;
    private List<User> users;
    private List<Instrument> instruments;
    private List<RoomTemplate> roomTemplete;
    private List<Booking> bookedTimes;
    
    // Reservation
    private User currentUser;
    private Booking currentBooking;
    private RoomTemplate currentRoom;
    private List<Calendar> currentTimeTable;
    private float currentPrice;
   
    
    private final double MINIMUM_WINDOW_WIDTH = 1024;
    private final double MINIMUM_WINDOW_HEIGHT = 700;
    
    public Main() {
        Main.instance = this;
        

        
        
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
            gotoLogin();
            primaryStage.show();
            primaryStage.setResizable(false);
        
            
            updateUserDB();
            updateBookingDB();
            updateInstrumentDB();
            updateBookingDB();
        
        } catch (Exception ex) {
            
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    
    
    
    public List<Instrument> getInstruments() {
        return instruments;
    }
    
    public Instrument getInstrument(long id) {
        for (int i = 0; i < instruments.size(); i++) {
            Instrument o = instruments.get(i);
            if(o.getId() == id)
                return o;
        }
        return null;
    }
    
    public Instrument getInstrument(String name,String model) {
        for (int i = 0; i < instruments.size(); i++) {
            Instrument o = instruments.get(i);
            if(o.getName().equals(name) && o.getModel().equals(model))
                return o;
        }
        return null;
    }

    public List<RoomTemplate> getRoomTemplete() {
        return roomTemplete;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCurrentBooking(Booking currentBooking) {
        this.currentBooking = currentBooking;
    }

    public void setCurrentRoom(RoomTemplate currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void setCurrentTimeTable(List<Calendar> currentTimeTable) {
        this.currentTimeTable = currentTimeTable;
    }

    public List<Booking> getBookedTimes() {
        return bookedTimes;
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
    
    public void updateUserDB() {
        this.users = (List<User>) DatabaseManager.getInstance().fetchAllUser();
        if(users == null) {
            showErrorPopup("Connection Error", "User fetching error :(\nClick confirm to try again",new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //getChildren().remove(parent.getChildren().size()-1);
                updateUserDB();
            }
        });
        }
        
    }
    
    public void updateBookingDB() {
        this.bookedTimes = (List<Booking>) DatabaseManager.getInstance().fetchAllBooking();
    }
    
    public void updateInstrumentDB() {
        this.instruments =  DatabaseManager.getInstance().fetchAllInstrument();
    }
    
    public void updateRoomTemplateDB() {
        this.roomTemplete =  DatabaseManager.getInstance().fetchAllRoomTemplate();
    }
        
    public boolean userLogging(String userId, String password){
//        gotoMainMenu();
//          return true;

        //gotoMainMenu();
        //  return true;

        for(int i=0;i<users.size();i++) {
            //System.out.println(users.get(i).getUsername()+" "+userId);
            //System.out.println(users.get(i).getPassword()+" "+password);
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
    
    void userLogout(){
        currentUser = null;
        gotoLogin();
    }
    
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
        pop.setApp(title, detail, (AnchorPane)stage.getScene().getRoot());
        return pop;
    }
    
    public void showErrorPopup(String title,String detail,EventHandler<ActionEvent> eh) {
        PopupController pop = showPopup(title,detail);
        pop.addEventToButton(eh);
    }

    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login.fxml");
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


    
    
}
