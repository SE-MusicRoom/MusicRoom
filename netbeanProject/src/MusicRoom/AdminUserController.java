package MusicRoom;

import MusicRoom.entity.User;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;



public class AdminUserController extends AnchorPane implements Initializable {

    @FXML
    private VBox userScroll;


    private AdminMenuController mainmenu;
    private AdminUserController parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApp(AdminMenuController mainmenu){
        this.mainmenu = mainmenu;
        userScroll.getChildren().clear();

        List<User> users = Main.getInstance().getUsers();

        for(int i = 0; i < users.size(); i++){

        }
    }

    private StackPane creatUsersToken(User user){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("admin-user.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StackPane newtoken = (StackPane)loader.getNamespace().get("userToken");
        return newtoken;
    }



    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }



}