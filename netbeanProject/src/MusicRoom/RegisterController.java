
package MusicRoom;

import MusicRoom.entity.Band;
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
 * FXML's Register Controller.
 * @author SE-MUSICROOM
 */
public class RegisterController extends AnchorPane implements Initializable {

    @FXML    private Text message;
    @FXML    private TextField bandName;
    @FXML    private CheckBox subscribed;
    @FXML    private PasswordField password;
    @FXML    private TextField surname;
    @FXML    private TextField name;
    @FXML    private PasswordField confirmPassword;
    @FXML    private TextField email;
    @FXML    private TextField username;
    
    @FXML    private Text usernameWarningText;
    @FXML    private Text passwordWarningText;
    @FXML    private Text confirmWarningText;
    @FXML    private Text nameWarningText;
    @FXML    private Text surnameWarningText;
    @FXML    private Text bandNameWarningText;
    @FXML    private Text emailWarningText;

    /**
    *  Called by main
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message.setText("");
//        username.setPromptText("Enter your name");
//        password.setPromptText("Enter your password");
    }

    /**
    *  input validation
    */
    private static boolean validation(String password, String str) {
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
            return (hasLetter.find() || hasDigit.find()) && !hasSpecial.find();
        }
    }

    /**
    *  e-mail validation
    */
    private static boolean validateEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX
                = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
    *  Confirm button handler
    */
    public void onClickConfirm(ActionEvent event) {
        if (Main.getInstance() == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
        } else {
            boolean errorTrigger = false;
            message.setText("");
            usernameWarningText.setStyle("-fx-fill: #999999;");
            passwordWarningText.setStyle("-fx-fill: #999999;");
            confirmWarningText.setStyle("-fx-fill: #999999;");
            nameWarningText.setStyle("-fx-fill: #999999;");
            surnameWarningText.setStyle("-fx-fill: #999999;");
            emailWarningText.setStyle("-fx-fill: #999999;");

            if (username.getText().equals("")) {
                message.setText("Please fill in the blanks");
                usernameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if(DatabaseManager.getInstance().isExistUser(username.getText())) {
                Main.getInstance().showPopup("Too bad", "This username is already exist");
                errorTrigger = true;
            }
            
            if (password.getText().equals("")) {
                message.setText("Please fill in the blanks");
                passwordWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (confirmPassword.getText().equals("")) {
                message.setText("Please fill in the blanks");
                confirmWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (name.getText().equals("")) {
                message.setText("Please fill in the blanks");
                nameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (surname.getText().equals("")) {
                message.setText("Please fill in the blanks");
                surnameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;

            }

            if (bandName.getText().equals("")) {
                message.setText("Please fill in the blanks");
                bandNameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (username.getText().length() < 4 || !validation(username.getText(), "")) {
                usernameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (password.getText().length() < 8 || !validation(password.getText(), "password")) {
                passwordWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (!confirmPassword.getText().equals(password.getText())) {
                confirmWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (!validation(name.getText(), "")) {
                nameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            }

            if (!validation(surname.getText(), "")) {
                surnameWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
                /*} else if (email.getText()) {
                passwordWarningText.setStyle("-fx-fill: #ff0000;");*/
            }

            if (!validateEmail(email.getText())) {
                emailWarningText.setStyle("-fx-fill: #ff0000;");
                errorTrigger = true;
            } 
            
            
            if(!errorTrigger) {
                
                DatabaseManager.getInstance().addUser(
                        Main.getInstance().createUser(username.getText(), password.getText(),
                        name.getText(), surname.getText(),
                        email.getText(), Band.findBand(bandName.getText()),subscribed.isSelected()));
                Main.getInstance().updateUserDB();
                Main.getInstance().gotoRegisterConfirm();
            }

        }
    }

    /**
    *  Back button handler
    */
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
