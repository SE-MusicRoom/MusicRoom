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

    private Stage stage;
    private User currentUser;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Instrument> instruments = new ArrayList<Instrument>();
    
    private final double MINIMUM_WINDOW_WIDTH = 1366;
    private final double MINIMUM_WINDOW_HEIGHT = 768;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
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

    public User getLoggedUser() {
        return currentUser;
    }

    public ArrayList<Instrument> getInstruments() {
        return instruments;
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
    
    private void gotoProfile() {
        try {
            ProfileController profile = (ProfileController) replaceSceneContent("profile.fxml");
            profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoMainMenu() {
        try {
            MainMenuController reg = (MainMenuController) replaceSceneContent("base.fxml");
            reg.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoRegister() {
        try {
            RegisterController reg = (RegisterController) replaceSceneContent("register.fxml");
            reg.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 800, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
