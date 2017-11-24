/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import MusicRoom.entity.CustomRoomTemplate;
import MusicRoom.entity.RoomTemplate;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author SE-MUSICROOM
 */
public class TemplateSelectController extends AnchorPane implements Initializable {
    
    private MainMenuController mainmenu;
    private TemplateSelectController parent;
    private RoomTemplate myTemplate;
    
    @FXML
    private Text templateName;
    @FXML
    private ImageView templatePic;
    @FXML
    private AnchorPane templateToken;
    @FXML
    private GridPane templateGrid;
    @FXML
    private Button selectBtn;
    
    
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.templateGrid.getChildren().clear();
        templateGrid.setPrefHeight(200*Math.ceil(Main.getInstance().getRoomTemplete().size()/3));
        int i=0;
        for (RoomTemplate r : Main.getInstance().getRoomTemplete()) {
            if(r instanceof CustomRoomTemplate)
                continue;
            
            AnchorPane ac = copyTemplateToken(r);
            templateGrid.add(ac,(i)%3,i/3);
            i++;
        }
        
        AnchorPane ac = copyCustomTemplateToken();
        templateGrid.add(ac,(i)%3,i/3);
        
    }
    
    public void setApp(TemplateSelectController parent,MainMenuController mainmenu){
        this.parent = parent;
        this.mainmenu = mainmenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    private AnchorPane copyCustomTemplateToken() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("templateselect.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("templateToken");
        
         // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        TemplateSelectController cus = (TemplateSelectController)loader.getController();
        cus.setApp(this,mainmenu);
        cus.setTemplateToken(new CustomRoomTemplate("Custom", "Custom your room as you wish", 0));
        
        return newToken;
    }
    
    private AnchorPane copyTemplateToken(RoomTemplate r) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("templateselect.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("templateToken");
        
        
         // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        TemplateSelectController cus = (TemplateSelectController)loader.getController();
        cus.setApp(this,mainmenu);
        cus.setTemplateToken(r);
        
        return newToken;
    }
    
    private void setTemplateToken(RoomTemplate r) {
        myTemplate = r;
        templateName.setText(r.getName());
        templatePic.setImage(r.getImg()); //new Image("MusicRoom/img/RoomTemplate/"+r.getName()+".jpg",imgV.getFitWidth(), imgV.getFitHeight(), false,true)
       // templatePic.setFitHeight(templatePic.getFitHeight());
        //templatePic.setFitWidth(templatePic.getFitWidth());
        templatePic.setPreserveRatio(false);
        if(r instanceof CustomRoomTemplate)
            selectBtn.setOnAction((event) -> {onClickCustomize(null);});
    }
    
    public void onClickCustomize(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
    public void onSelectTemplate(ActionEvent event) {
        Main.getInstance().setCurrentRoom(myTemplate);
        mainmenu.gotoTimeSelect();
        Main.getInstance().setCurrentPrice(myTemplate.getPrice());
        
    }
    
    public void onSelectDetail(ActionEvent event) {
        Main.getInstance().showPopup(myTemplate.getName(), myTemplate.toString());
    }
}
