/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author YAY
 */
public class RegisterController extends AnchorPane implements Initializable {

    @FXML
    private Text errorMessage;
        
    @FXML
    private Button back;

    @FXML
    private TextField bandName;

    @FXML
    private CheckBox subscribed;

    @FXML
    private PasswordField password;

    @FXML
    private TextField phone;

    @FXML
    private TextField surname;

    @FXML
    private TextField name;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button comfirm;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    private Main application;
    
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        username.setPromptText("Enter your name");
        password.setPromptText("Enter your password");
        
    }
    
    
    public void onClickRegister(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            errorMessage.setText("Hello " + username.getText());
        } else {
            if(username.getText()!="" && password.getText()!="") {
                errorMessage.setText("Registered");
                application.createUser(username.getText(), password.getText());
            }
            
            
        }
    }    
    
    public void onClickBack(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            errorMessage.setText("Hello " + username.getText());
        } else {
            application.gotoLogin();
            
            
        }
    }    
}
