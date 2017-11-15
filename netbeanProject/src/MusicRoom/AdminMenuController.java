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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author SE-MUSICROOM
 */
public class AdminMenuController  extends AnchorPane implements Initializable {
    
    @FXML
    private AnchorPane includePane;
    
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
    
    public void hideIncludePane() {
        includePane.getChildren().clear();
    }
    

    
    
    public void gotoLogOut() {
        Main.getInstance().gotoMainMenu();
    }
    
    
    public void onClickUsers(ActionEvent event) {
        
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
