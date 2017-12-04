package MusicRoom;

import MusicRoom.entity.CustomRoomTemplate;
import MusicRoom.entity.RoomTemplate;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

/**
 * FXML's Room Template (Preset) Selection Controller.
 * @author SE-MUSICROOM
 */
public class TemplateSelectController extends AnchorPane implements Initializable {
    
    private MainMenuController mainmenu;
    private TemplateSelectController parent;
    private RoomTemplate myTemplate;
    
    @FXML    private Text templateName;
    @FXML    private ImageView templatePic;
    @FXML    private GridPane templateGrid;
    @FXML    private Button selectBtn;
    
    /**
    *  Called by MainMenuController
    */
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
        this.templateGrid.getChildren().clear();
        templateGrid.setPrefHeight(400*templateGrid.getRowConstraints().size());
        int i=0;
        List<RoomTemplate> rlist = Main.getInstance().getRoomTemplete();
        if(rlist==null) {
            mainmenu.hideIncludePane();
            return;
        }
        for (RoomTemplate r : rlist) {
            if(r instanceof CustomRoomTemplate)
                continue;
            
            AnchorPane ac = copyTemplateToken(r);
            
            if(i%3 == 0 && i/3-1 > templateGrid.getRowConstraints().size())
                templateGrid.getRowConstraints().add(new RowConstraints());
            
            templateGrid.add(ac,i%3,i/3);
            templateGrid.getRowConstraints().get(i/3).setMinHeight(ac.getPrefHeight());
            templateGrid.getRowConstraints().get(i/3).setPrefHeight(ac.getPrefHeight());
            templateGrid.getRowConstraints().get(i/3).setMaxHeight(ac.getPrefHeight());
            i++;
        }
        
        AnchorPane ac = copyCustomTemplateToken();
        templateGrid.add(ac,(i)%3,i/3);
        
    }
    
    /**
    * (for 'templateToken' only, they use different TemplateSelectController). 
    * called by main TemplateSelectController. 
    */
    public void setApp(TemplateSelectController parent,MainMenuController mainmenu){
        this.parent = parent;
        this.mainmenu = mainmenu;
    }

    /**
    * init
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    /**
    * copy templateToken by loading it and set data
    */
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
    
    /**
    * add customize button as a new copy of templateToken by loading it and set data
    */
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
    
    /**
    * (called by copyTemplateToken of main TemplateSelectController) set image/text of templateToken
    */
    private void setTemplateToken(RoomTemplate r) {
        myTemplate = r;
        templateName.setText(r.getName());
        templatePic.setImage(r.getImg());
        templatePic.setPreserveRatio(false);
        if(r instanceof CustomRoomTemplate)
            selectBtn.setOnAction((ActionEvent event) -> {onClickCustomize(null);});
    }
    
    /**
    *  Customize button handler
    */
    public void onClickCustomize(ActionEvent event) {
        mainmenu.gotoCustomize();
    }
    
    /**
    *  Back button handler
    */
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    
    /**
    *  template click handler
    */
    public void onSelectTemplate(ActionEvent event) {
        Main.getInstance().setCurrentRoom(myTemplate);
        mainmenu.gotoTimeSelect();
        Main.getInstance().setCurrentPrice(myTemplate.getPrice());
        
    }
    
    /**
    *  detail button handler
    */
    public void onSelectDetail(ActionEvent event) {
        Main.getInstance().showPopup(myTemplate.getName(), myTemplate.toString());
    }
}
