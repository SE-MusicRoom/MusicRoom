/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.*;
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

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Main instance;
    
    private Stage stage;
    private List<User> users;
    private List<Instrument> instruments;
    private List<RoomTemplate> roomTemplete;
    private List<RoomTemplate> customTemplete;
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
        this.instruments =  DatabaseManager.getInstance().fetchAllInstrument();
        this.roomTemplete =  DatabaseManager.getInstance().fetchAllRoomTemplate();
        updateUserDB();
        this.customTemplete = new ArrayList<RoomTemplate>();

        this.users = DatabaseManager.getInstance().fetchAllUser();
        this.bookedTimes = DatabaseManager.getInstance().fetchAllBooking();
        
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

            Voice v = new Voice("", "", 10, "");
            // Test
            //DatabaseManager.getInstance().createDummyRoomTemplate ();
            /*
            DatabaseManager DbManager = DatabaseManager.getInstance();
            List<Instrument> instruments = DbManager.fetchAllInstrument(insert path of file.odb);
            */

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

    

    public void addCustomTemplete(RoomTemplate customTemplete) {
        this.customTemplete.add(customTemplete);
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
                           String email ,String bandName) {
        User newUser = new User(username ,password ,name, surname ,email ,bandName);
        DatabaseManager.getInstance().addUser(newUser);
        updateUserDB();
        return newUser;
    }
    
    public void updateUserDB() {
        this.users = (List<User>) DatabaseManager.getInstance().fetchAllUser();
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
            AdminMenuController reg = (AdminMenuController) replaceSceneContent("user-admin.fxml");
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

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
            //page.setStyle("base.css");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(page, 1024, 700);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
    
}
