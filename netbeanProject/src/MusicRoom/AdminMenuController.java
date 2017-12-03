/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author SE-MUSICROOM
 */
public class AdminMenuController  extends AnchorPane implements Initializable {
    
    @FXML
    private AnchorPane includePane;


    private AnchorPane adminUserPane;

    private AdminUserController adminUserController;
    
    public void setApp(){
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
    private Initializable showIncludePane(String fxml) throws IOException {
        
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
        
        includePane.getChildren().clear();
        includePane.getChildren().add(page);
        
        return (Initializable) loader.getController();
    }

    public void swapIncludePane(AnchorPane pane) {


        TranslateTransition tt = new TranslateTransition(Duration.ZERO, includePane);
        tt.setFromX( 0 );
        tt.setToX( 1000 );

        // If there is any pane open , play tt. if not, duration = 0 (not play)
        for(int i=0;i<includePane.getChildren().size();i++) {
            if(includePane.getChildren().get(i).isVisible()) {
                tt.setDuration(Duration.seconds(0.1));
                break;
            }
        }

        tt.setOnFinished((ActionEvent event) -> {
            hideIncludePane();
            pane.setVisible(true);
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.1), includePane);
            tt2.setFromX( 1000 );
            tt2.setToX( 0 );
            tt2.play();
        });



        tt.play();


    }

    public void hideIncludePane() {

        for(int i=0;i<includePane.getChildren().size();i++) {
            includePane.getChildren().get(i).setVisible(false);
        }



    }
    

    
    
    public void gotoLogOut() {
        Main.getInstance().gotoMainMenu();
    }

    public void gotoUser(){
        if(adminUserPane == null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-user.fxml"));
            try {
                adminUserPane = (AnchorPane) loader.load();
            } catch(IOException ex){
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            adminUserPane.setVisible(false);
            includePane.getChildren().add(adminUserPane);
            adminUserController = (AdminUserController) loader.getController();
        }
        adminUserController.setApp(this);
        swapIncludePane(adminUserPane);

    }

    public void onClickUsers(ActionEvent event) {
        gotoUser();
    }
    public void onClickBands(ActionEvent event) {
        
    }
    public void onClickNews(ActionEvent event) {
        
    }
    public void onClickTemplates(ActionEvent event) {
        
    }
    public void onClickInstruments(ActionEvent event) {
        
    }
    public void onClickPromotions(ActionEvent event) {
        
    }
    
     public void onClickLogOut(ActionEvent event) {
        Main.getInstance().gotoLogin();
    }
}
