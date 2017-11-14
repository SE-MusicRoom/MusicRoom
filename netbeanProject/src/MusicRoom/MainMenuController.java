package MusicRoom;

import MusicRoom.entity.Booking;
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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Login Controller.
 */
public class MainMenuController extends AnchorPane implements Initializable {

    @FXML
    AnchorPane includePane;
    @FXML
    Button reservationBtn;

    
    
    
    public void setApp(){
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
    private Initializable showIncludePane(String fxml) throws IOException {
        hideIncludePane();
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
    
    public void gotoCustomize() {
        try {
            CustomizeController con = (CustomizeController) showIncludePane("customize.fxml");
            con.setApp(this);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void gotoTimeSelect() {
        try {
            TimeSelectController con = (TimeSelectController) showIncludePane("timeselector.fxml");
            con.setApp(this);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoTemplate() {
        try {
            TemplateController con = (TemplateController) showIncludePane("templateselect.fxml");
            con.setApp(this);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoHistory() {
        try {
            HistoryController con = (HistoryController) showIncludePane("history.fxml");
            con.setApp(this);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoSuccess(Booking newBooking) {
        try {
            SuccessReservationController con = (SuccessReservationController) showIncludePane("success_reservation.fxml");
            con.setApp(this,newBooking);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onClickReservation(ActionEvent event) {
        gotoTemplate();
    }
    
    public void onClickHistory(ActionEvent event) {
       gotoHistory();
    }
    
     public void onClickLogOut(ActionEvent event) {
        Main.getInstance().gotoLogin();
    }


}
