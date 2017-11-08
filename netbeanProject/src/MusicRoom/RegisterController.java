/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private Text message;

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

    public void setApp(Main application) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setText("");
//        username.setPromptText("Enter your name");
//        password.setPromptText("Enter your password");
    }

    public static boolean validation(String password, String str) {
        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        //Pattern eight = Pattern.compile (".{8}");

        Matcher hasLetter = letter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        if (str.equals("password")) {
            return hasLetter.find() || hasDigit.find() || hasSpecial.find();
        } else {
            return hasLetter.find() || hasDigit.find();
        }
    }

    public void onClickConfirm(ActionEvent event) {
        if (Main.getInstance() == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
        } else {
            if (username.getText().equals("")
                    || password.getText().equals("")
                    || confirmPassword.getText().equals("")
                    || name.getText().equals("")
                    || surname.getText().equals("")
                    || email.getText().equals("")
                    || bandName.getText().equals("")) {
                message.setText("Please fill in the blanks");
            } else {
                Main.getInstance().createUser(username.getText(), password.getText(),
                        name.getText(), surname.getText(),
                        email.getText(), bandName.getText());
            }

        }
    }

    public void onClickBack(ActionEvent event) {
        if (Main.getInstance() == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            message.setText("Hello " + username.getText());
        } else {
            Main.getInstance().gotoLogin();

        }
    }
}
