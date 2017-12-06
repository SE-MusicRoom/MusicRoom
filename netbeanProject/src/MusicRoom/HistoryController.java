package MusicRoom;

import MusicRoom.entity.Booking;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML's User's History Controller.
 * @author SE-MUSICROOM
 */
public class HistoryController extends AnchorPane implements Initializable {
    
    @FXML    private TextField searchBox;
    
    @FXML    private VBox historyScroll;

    //For token
    @FXML    private Text showid;
    @FXML    private Text date;
    @FXML    private Text template;
    @FXML    private Text time;
    @FXML    private Text price;
    @FXML    private Text status;
    @FXML    private Button cancelBtn;

    private MainMenuController mainmenu;
    private HistoryController parent;
    
    /**
    * called by MainMenuController
    */
    public void setApp(MainMenuController mainmenu){
        this.mainmenu = mainmenu;
         historyScroll.getChildren().clear();
        List<Booking> books = Main.getInstance().getCurrentUser().getBookings();
        System.out.println("booksize: "+books.size());
        for (int i = 0; i < books.size(); i++) {
            
            //error booking check
            if(books.get(i).getTimeTable().isEmpty()) {
                //System.out.println("remove: "+books.get(i).getID());
                //Main.getInstance().getCurrentUser().removeBookedTime(books.get(i));
                continue;
            }
            StackPane hist = copyHistoryToken(books.get(i));
            historyScroll.getChildren().add(hist);
            
        }
    }
    
    /**
    * (for 'historyToken' only, they use different HistoryController). 
    * called by main HistoryController. 
    */
    public void setApp(HistoryController parent){
        this.parent = parent;
    }

    /**
    *  init 
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
    *  getHistoryScroll of main HistoryController, for historyToken to call and add/count child
    */
    public VBox getHistoryScroll() {
        return historyScroll;
    }
    
    
    /**
    * copy historyToken by loading it and set data
    */
    private StackPane copyHistoryToken(Booking book) {
        
        
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("history.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StackPane newToken = (StackPane)loader.getNamespace().get("historyToken");

        
        HistoryController cus = (HistoryController)loader.getController();
        cus.setApp(this);
        
        cus.setTokenData(book);
        
        return newToken;
    }
    
    /**
    * (called by copyHistoryToken of main HistoryController) set text of historyToken
    */
    protected void setTokenData(Booking book) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy");
        this.showid.setText(String.valueOf(book.getID()));
        this.date.setText(format1.format(book.getCreateTime().getTime()));
        this.template.setText(book.getRoom().getName());
        this.time.setText(book.getTimeTableString());
        this.price.setText("฿ "+String.valueOf(book.getPrice()));
        
        // Check if first one is already passed
        if(book.getTimeTable().get(0).before(Calendar.getInstance())) {
            this.cancelBtn.setDisable(true);
            this.status.setText("PASSED");
        } else {
            this.status.setText("OK");
        }
        
    }
    
    /**
    *  find button handler
    */
    @FXML
    void onFind(Event event) {
        historyScroll.getChildren().clear();
        List<Booking> books = Main.getInstance().getCurrentUser().getBookings();
        for (int i = 0; i < books.size(); i++) {
            
            if(!(books.get(i).toString()).replaceAll("[\\s|\\u00A0]+", "").toLowerCase().contains(searchBox.getText().replaceAll("[\\s|\\u00A0]+", "").toLowerCase()) && !searchBox.getText().replaceAll("[\\s|\\u00A0]+", "").equals(""))
                continue;
            System.out.println(books.get(i).getID());
            StackPane newToken = copyHistoryToken(books.get(i));
            historyScroll.getChildren().add(newToken);
        }
    }

    /**
    *  cancel button handler
    */
    @FXML
    void onCancel(ActionEvent event) {
        
        int selectedId = Integer.parseInt( showid.getText() );
        Booking selectedB = Main.getInstance().getBookings().stream().filter(item -> item.getID() == selectedId).findFirst().get();
        // Remove from db
        DatabaseManager.getInstance().removeBooking(selectedB);
        // Remove from user
        Main.getInstance().getCurrentUser().removeBookedTime(selectedB);
        // Remove from Scroll
        parent.getHistoryScroll().getChildren().remove(((Button)event.getSource()).getParent().getParent());
        
        // Send cancel reservation e-mail
        Main.getInstance().sendEmail("Music Room Cancel Reservation",
                                "<h1><span style=\"color: #FF0000;\"> ยกเลิกการจองสำเร็จ </span> </h1>"
                                + "<br> <b>รหัสจอง:</b> " + selectedB.getID()
                                + "<br> <b>ผู้จอง:</b> คุณ " + selectedB.getUser().toString()
                                + "<br> <b>รายละเอียดห้อง:</b> <pre>" + selectedB.getRoom().toString()+ "</pre>"
                                + "<br> <b>เวลา:</b> " + selectedB.getTimeTableString()
                                + "<br> ราคารวมทั้งหมด: <i>" + selectedB.getPrice() + "(ยกเลิกแล้ว ไม่จำเป็นต้องจ่ายเงินอีก) <i>"
                                + "<br><br> <b> ขออภัยหากทำให้ท่านไม่พอใจในการใช้บริการ หวังว่าพวกเราจะได้บริการท่านอีกในอนาคต"
                                + "<br> ขอบคุณที่ใช้บริการกับเรา, หากมีปัญหาสามารถตอบกลับอีเมลฉบับนี้ได้"
				+ "<br> Be yourself"
                                + "<br> SE-MusicRoom</b>");
        
    }
    
    /**
    *  back button handler
    */
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }


    
}
