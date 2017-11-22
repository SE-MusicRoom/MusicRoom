package MusicRoom;

import MusicRoom.entity.CustomRoomTemplate;
import MusicRoom.entity.Instrument;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Login Controller.
 */
public class CustomizeController extends AnchorPane implements Initializable {

    @FXML
    private Text listToken_price;

    @FXML
    private TextField searchTxtField;

    @FXML
    private Button GoBtn;

    @FXML
    private ImageView listToken_pic;

    @FXML
    private AnchorPane addedToken;

    @FXML
    private Text addedToken_name;

    @FXML
    private Button reservationBtn;

    @FXML
    private VBox addedScroll;

    @FXML
    private Text listToken_path;

    @FXML
    private Text total;

    @FXML
    private Text addedToken_price;

    @FXML
    private AnchorPane listToken;

    @FXML
    private Text listToken_name;

    @FXML
    private Button reservationBtn1;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private VBox listScroll;
    
    private MainMenuController mainmenu;
    private CustomizeController parent;
    public static CustomizeController instance;
    
    private List<Instrument> addedInstruments;
    
    public void setApp(MainMenuController mainmenu){
        this.addedInstruments = new ArrayList<Instrument>();
        this.mainmenu = mainmenu;
        
        choiceBox.getItems().add("All");
        choiceBox.getSelectionModel().selectFirst();
        
        List<Instrument> listedInstruments = Main.getInstance().getInstruments();
        for (int i = 0; i < listedInstruments.size(); i++) {
            if(!choiceBox.getItems().contains(listedInstruments.get(i).getClass().getSimpleName()))
                choiceBox.getItems().add(listedInstruments.get(i).getClass().getSimpleName());
        }
        
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                System.out.println(newValue);
            }
            });
    }
    
    public void setApp(CustomizeController parent){
        this.parent = parent;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addedScroll.getChildren().clear();
        listScroll.getChildren().clear();
        setTotal(0);
        
    }

    public VBox getAddedScroll() {
        return addedScroll;
    }

    public void setTotal(float total) {
        this.total.setText("฿"+total+"/hr");
    }
    
    public List<Instrument> getAddedInstruments() {
        return addedInstruments;
    }
    
    public void addInstrument(Instrument instrument) {
        addedInstruments.add(instrument);
    }
    
    public void removeInstrument(Instrument instrument) {
        addedInstruments.remove(instrument);
    }
    
    public void removeInstrument(int i) {
        addedInstruments.remove(i);
    }
    
    public void onClickGo(ActionEvent event) {
        listScroll.getChildren().clear();
        List<Instrument> listedInstruments = Main.getInstance().getInstruments();
        for (int i = 0; i < listedInstruments.size(); i++) {
            if(!listedInstruments.get(i).getClass().getSimpleName().equals(choiceBox.getSelectionModel().getSelectedItem()) && !choiceBox.getSelectionModel().getSelectedItem().equals("All"))
                continue;
            if(!(listedInstruments.get(i).getName()+listedInstruments.get(i).getModel()).replaceAll("[\\s|\\u00A0]+", "").toLowerCase().contains(searchTxtField.getText().replaceAll("[\\s|\\u00A0]+", "").toLowerCase()) && !searchTxtField.getText().replaceAll("[\\s|\\u00A0]+", "").equals(""))
                continue;
            AnchorPane newToken = copyListToken(listedInstruments.get(i));
            newToken.setId(String.valueOf(listedInstruments.get(i).getId()));
            listScroll.getChildren().add(newToken);
        }
    }
    
    
    public void onAddInstrument(MouseEvent event) {
        
        int selectedId = Integer.parseInt( ((AnchorPane)event.getSource()).getId() );
        Instrument selectedInstrument = Main.getInstance().getInstrument(selectedId);
        parent.addInstrument(selectedInstrument);
        System.out.println("Added " +  selectedInstrument.getName() +  selectedInstrument.getModel());
        
        
        AnchorPane newToken = copyAddedToken(selectedInstrument);
        newToken.setId(String.valueOf(parent.getAddedScroll().getChildren().size()));
        parent.getAddedScroll().getChildren().add(newToken);

        // Total
        parent.setTotal(parent.calculateTotal());
        
    }
    
    public void onRemoveInstrument(MouseEvent event) {
        int selectedId = Integer.parseInt( ((AnchorPane)event.getSource()).getId() );
        Instrument selectedInstrument = parent.getAddedInstruments().get(selectedId);
        System.out.println("Removed " +  selectedInstrument.getName() +  selectedInstrument.getModel());
        parent.removeInstrument(selectedId);

        parent.getAddedScroll().getChildren().remove(selectedId);
        // Reassign ID
        for (int i = 0; i < parent.getAddedScroll().getChildren().size(); i++) {
            parent.getAddedScroll().getChildren().get(i).setId(String.valueOf(i));
                
        }
        
        // Total
        parent.setTotal(parent.calculateTotal());
    }
    
    
    public void onClickBack(ActionEvent event) {
        mainmenu.gotoTemplate();
    }
    public void onClickConfirm(ActionEvent event) {
        
        if(addedInstruments.isEmpty()) {
            System.out.println("Error: Please add some instruments first");
            Main.getInstance().showPopup("Wait Wait!","Please add some instruments first");
            return;
        }
        
        System.out.println("SET!");
        
        CustomRoomTemplate newRoom = new CustomRoomTemplate("Custom","A new custom room", calculateTotal());
        for (int i = 0; i < addedInstruments.size(); i++) {
            newRoom.addInstrument(addedInstruments.get(i));
        }
        
        Main.getInstance().setCurrentRoom(newRoom);
        mainmenu.gotoTimeSelect();
        
        
        
    }
    
    private float calculateTotal() {
        float price = 0f;
        for (int i = 0; i < addedInstruments.size(); i++) {
            price += addedInstruments.get(i).getRentPrice();
            
        }
        return (float) Math.ceil(price);
    }
    
    private AnchorPane copyListToken(Instrument instrument) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("listToken");
        
         // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController)loader.getController();
        cus.setApp(this);
        cus.setListToken(instrument.getName()+" "+instrument.getModel(),
                        instrument.getClassPath(),
                        Float.toString(instrument.getRentPrice()),
                        instrument.getImg());
        
        
        return newToken;
    }
    
    private AnchorPane copyAddedToken(Instrument selectedInstrument) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("addedToken");
        
        // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController)loader.getController();
        cus.setApp(this.parent);
        cus.setAddedToken(selectedInstrument.getClass().getSimpleName()+": "+selectedInstrument.getName()+" "+selectedInstrument.getModel(),
                            Float.toString(selectedInstrument.getRentPrice()));
        
        return newToken;
    }
    
    protected void setListToken(String name,String path,String price,Image img) {
        listToken_name.setText(name);
        listToken_path.setText(path);
        listToken_price.setText(price+"฿/hr");
        listToken_pic.setImage(img);
        
    }
    
    protected void setAddedToken(String name,String price) {
        addedToken_name.setText(name);
        addedToken_price.setText(price+"฿/hr");
    }
}
