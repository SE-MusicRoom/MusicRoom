package MusicRoom;

import MusicRoom.entity.Band;
import MusicRoom.entity.User;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javax.persistence.criteria.CriteriaBuilder;


public class AdminUserController extends AnchorPane implements Initializable {

    @FXML
    private VBox userScroll;


    private AdminMenuController mainmenu;
    private AdminUserController parent;
    private List<AdminUserController> scrollCon = new ArrayList<AdminUserController>();
    private int state;

    // for token
    @FXML private Text userID;
    @FXML private TextField name;
    @FXML private TextField bandName;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApp(AdminUserController parent){
        this.parent = parent;
    }

    public void setApp(AdminMenuController mainmenu){
        this.mainmenu = mainmenu;
        userScroll.getChildren().clear();
        state = 0;

        List<User> users = Main.getInstance().getUsers();

        for(int i = 0; i < users.size(); i++){
            userScroll.getChildren().add(copyUsersToken(users.get(i)));
        }
    }

    private StackPane copyUsersToken(User user){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("admin-user.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StackPane newtoken = (StackPane)loader.getNamespace().get("userToken");
        AdminUserController cus = (AdminUserController) loader.getController();
        cus.setApp(this);
        cus.setTokenData(String.valueOf(user.getId()),user.getName(),user.getBandName().getName());
        scrollCon.add(cus);
        return newtoken;
    }

    private void setTokenData(String id, String name, String bandName){
        this.userID.setText(id);
        this.name.clear();
        this.name.setPromptText(name);
        this.bandName.clear();
        this.bandName.setPromptText(bandName);
    }

    public VBox getUserScroll(){
        return userScroll;
    }

    public TextField getName(){
        return name;
    }

    public TextField getBandName(){
        return bandName;
    }

    public Text getUserID(){
        return userID;
    }

    public void onClickAdd(ActionEvent event){

    }

    public void onClickRemove(ActionEvent event){
        int selectID = Integer.parseInt(userID.getText());

        User user = Main.getInstance().getUsers().stream().filter(u -> u.getId() == selectID).findFirst().get();

        DatabaseManager.getInstance().removeUser(user);

        parent.getUserScroll().getChildren().remove(((Button)event.getSource()).getParent().getParent());
    }

    public void onClickUpdate(ActionEvent event){
        for(int i = 0; i < scrollCon.size(); i++){
            AdminUserController cus = scrollCon.get(i);
            if(!cus.getName().getText().equals("")){
                String updateName = cus.getName().getText();
                int selectID = Integer.parseInt(cus.getUserID().getText());
                User user = Main.getInstance().getUsers().stream().filter(u -> u.getId() == selectID).findFirst().get();

                user.setName(updateName);
                DatabaseManager.getInstance().updateUser(user);
                state = 1;
            }
            if(!cus.getBandName().getText().equals("")){
                String updateName = cus.getBandName().getText();
                int selectID = Integer.parseInt(cus.getUserID().getText());
                User user = Main.getInstance().getUsers().stream().filter(u -> u.getId() == selectID).findFirst().get();

                Band b = Band.findBand(updateName);
                user.setBandName(b);
                DatabaseManager.getInstance().updateUserBand(user,b);
                state = 1;
            }
        }
        if(state == 1) {
            mainmenu.hideIncludePane();
            mainmenu.gotoUser();
        }
    }

    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }



}