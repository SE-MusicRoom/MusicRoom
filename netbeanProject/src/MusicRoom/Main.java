/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.AcousticGuitar;
import MusicRoom.entity.Instrument;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import MusicRoom.entity.User;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Main instance;
    
    private Stage stage;
    private User currentUser;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Instrument> instruments = new ArrayList<Instrument>();
    
    //Customize
    private ArrayList<Instrument> addedInstruments = new ArrayList<Instrument>();
    
    private final double MINIMUM_WINDOW_WIDTH = 1366;
    private final double MINIMUM_WINDOW_HEIGHT = 768;
    
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
            
            // Test
            
            instruments.add(new AcousticGuitar("Guitar","Gibson V Flyer",120,null));
            instruments.add(new AcousticGuitar("Guitar","Something else",150,null));
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

    public ArrayList<Instrument> getInstruments() {
        return instruments;
    }
    
    public Instrument addSelectedInstruments(int i) {
        addedInstruments.add(instruments.get(i));
        
        return instruments.get(i);
    }
    
    public User createUser(String username,String password) {
        String id = Integer.toString(users.size());
        User newUser = new User(id,username,password);
        users.add(newUser);
        return newUser;
    }
        
    public boolean userLogging(String userId, String password){
        gotoMainMenu();
            return true;
//        for(int i=0;i<users.size();i++) {
//            //System.out.println(users.get(i).getUsername()+" "+userId);
//            //System.out.println(users.get(i).getPassword()+" "+password);
//            if(users.get(i).getUsername().equals(userId)) {
//                System.out.println("1");
//                if(users.get(i).getPassword().equals(password)) {
//                    currentUser = users.get(i);
//                    gotoProfile();
//                    return true;
//                } else
//                    return false;
//            }
//        }
//        return false;

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
        Scene scene = new Scene(page, 1366, 768);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
    
}
