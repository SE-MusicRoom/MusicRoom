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
public class TemplateController extends AnchorPane implements Initializable {
    
    private MainMenuController mainmenu;
    private TemplateController parent;
    
    @FXML
    private Text templateName;
    @FXML
    private ImageView templatePic;
    @FXML
    private AnchorPane templateToken;
    @FXML
    private GridPane templateGrid;
    
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.templateGrid.getChildren().clear();
        templateGrid.setPrefHeight(200*Math.ceil(Main.getInstance().getRoomTemplete().size()/3));
        for (int i = 0; i < Main.getInstance().getRoomTemplete().size(); i++) {
            RoomTemplate r = Main.getInstance().getRoomTemplete().get(i);
            if(r instanceof CustomRoomTemplate)
                continue;
            
            AnchorPane ac = copyTemplateToken(r);
            ac.setId(String.valueOf(i));
            templateGrid.add(ac,(i)%3,i/3);
        }
        
    }
    
    public void setApp(TemplateController parent,MainMenuController mainmenu){
        this.parent = parent;
        this.mainmenu = mainmenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    private AnchorPane copyTemplateToken(RoomTemplate r) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("templateselect.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("templateToken");
        setTemplateToken(newToken,r);
        
         // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        TemplateController cus = (TemplateController)loader.getController();
        cus.setApp(this,mainmenu);
        
        return newToken;
    }
    
    private void setTemplateToken(AnchorPane newToken,RoomTemplate r) {
        templateName.setText(r.getName());
        templatePic.setImage(r.getImg()); //new Image("MusicRoom/img/RoomTemplate/"+r.getName()+".jpg",imgV.getFitWidth(), imgV.getFitHeight(), false,true)
       // templatePic.setFitHeight(templatePic.getFitHeight());
        //templatePic.setFitWidth(templatePic.getFitWidth());
        templatePic.setPreserveRatio(false);
    }
    
    public void onClickCustomize(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
    
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
    public void onSelectTemplate(ActionEvent event) {
        int selectedId = Integer.parseInt( ((Button)event.getSource()).getParent().getId() );
        Main.getInstance().setCurrentRoom(Main.getInstance().getRoomTemplete().get(selectedId));
        mainmenu.gotoTimeSelect();
        Main.getInstance().setCurrentPrice(Main.getInstance().getRoomTemplete().get(selectedId).getPrice());
        
    }
    
    public void onSelectDetail(ActionEvent event) {
        int selectedId = Integer.parseInt( ((Button)event.getSource()).getParent().getId() );
        RoomTemplate room = Main.getInstance().getRoomTemplete().get(selectedId);
        Main.getInstance().showPopup(room.getName(), room.toString());
        System.out.println(Main.getInstance().getRoomTemplete().get(selectedId));
        
    }
}
