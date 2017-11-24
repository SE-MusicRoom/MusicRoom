package MusicRoom;

import MusicRoom.entity.Booking;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Login Controller.
 */
public class MainMenuController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane includePane;
    @FXML
    private Button reservationBtn;

    
    private AnchorPane customizePane;
    private AnchorPane timeselectorPane;
    private AnchorPane templateselectPane;
    private AnchorPane historyPane;
    private AnchorPane successReservationPane;
    
    private CustomizeController customizeController;
    private TimeSelectController timeselectorController;
    private TemplateSelectController templateselectController;
    private HistoryController historyController;
    private SuccessReservationController successReservationController;
    
    public void setApp(){
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    
    private Initializable showIncludePane(String fxml) throws IOException {
        hideIncludePane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        includePane.getChildren().clear();
        includePane.getChildren().add(page);
        

        
        return (Initializable) loader.getController();
    }
    
    public void swapIncludePane(AnchorPane pane) {
        
        
        TranslateTransition tt = new TranslateTransition(Duration.ZERO, includePane);
        tt.setFromX( 0 );
        tt.setToX( 1000 );
        
        // If there is any pane open , play tt. if not, duration = 0 (not play)
        for(int i=0;i<includePane.getChildren().size();i++) {
            if(includePane.getChildren().get(i).isVisible()) {
                tt.setDuration(Duration.seconds(0.1));
                break;
            }
        }
        
        tt.setOnFinished((ActionEvent event) -> { 
            hideIncludePane();
            pane.setVisible(true);
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.1), includePane);
            tt2.setFromX( 1000 );
            tt2.setToX( 0 );
            tt2.play();
        });
        

            
        tt.play();
            
        
    }
    
    public void hideIncludePane() {

            for(int i=0;i<includePane.getChildren().size();i++) {
                includePane.getChildren().get(i).setVisible(false);
            }

        
        
    }
    
    public void gotoCustomize() {
        if(customizePane==null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customize.fxml"));
            try {
                customizePane = (AnchorPane) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            customizePane.setVisible(false);
            includePane.getChildren().add(customizePane);
            customizeController = (CustomizeController) loader.getController();
            customizeController.setApp(this);
        }
        swapIncludePane(customizePane);
        
    }
    
     public void gotoTimeSelect() {
        if(timeselectorPane==null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timeselector.fxml"));
            try {
                timeselectorPane = (AnchorPane) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            timeselectorPane.setVisible(false);
            includePane.getChildren().add(timeselectorPane);
            timeselectorController = (TimeSelectController) loader.getController();
            
        }
        Main.getInstance().updateBookingDB();
        timeselectorController.setApp(this);
        swapIncludePane(timeselectorPane);
    }
    
    public void gotoTemplate() {
        if(templateselectPane==null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("templateselect.fxml"));
            try {
                templateselectPane = (AnchorPane) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            templateselectPane.setVisible(false);
            includePane.getChildren().add(templateselectPane);
            templateselectController = (TemplateSelectController) loader.getController();
            
        }
        templateselectController.setApp(this);
        swapIncludePane(templateselectPane);
    }
    
    public void gotoHistory() {
        if(historyPane==null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("history.fxml"));
            try {
                historyPane = (AnchorPane) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            historyPane.setVisible(false);
            includePane.getChildren().add(historyPane);
            historyController = (HistoryController) loader.getController();
            
        }
        historyController.setApp(this);
        swapIncludePane(historyPane);
    }
    
    public void gotoSuccess(Booking newBooking) {
        if(successReservationPane==null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("success_reservation.fxml"));
            try {
                successReservationPane = (AnchorPane) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            successReservationPane.setVisible(false);
            includePane.getChildren().add(successReservationPane);
            successReservationController = (SuccessReservationController) loader.getController();
            
        }
        successReservationController.setApp(this, newBooking);
        swapIncludePane(successReservationPane);
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

    public void clearAllState() {
        customizePane = null;
        timeselectorPane = null;
        templateselectPane = null;
        historyPane = null;
        successReservationPane = null;
        
    }

}
