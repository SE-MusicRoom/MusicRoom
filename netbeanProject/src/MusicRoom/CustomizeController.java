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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * FXML's Room Customization Controller.
 * @author SE-MUSICROOM
 */
public class CustomizeController extends AnchorPane implements Initializable {

    @FXML    private ImageView iconToken;
    @FXML    private GridPane iconGrid;
    @FXML    private Text listToken_price;
    @FXML    private TextField searchTxtField;
    @FXML    private ImageView listToken_pic;
    @FXML    private Text addedToken_name;
    @FXML    private VBox addedScroll;
    @FXML    private Text listToken_path;
    @FXML    private Text total;
    @FXML    private Text addedToken_price;
    @FXML    private AnchorPane listToken;
    @FXML    private Text listToken_name;
    @FXML    private ChoiceBox<String> choiceBox;
    @FXML    private VBox listScroll;

    // Controllers
    private MainMenuController mainmenu;
    private CustomizeController parent;
    public static CustomizeController instance;

    // User's selected/added Instruments 
    private List<Instrument> addedInstruments;

    /**
    * called by main
    */
    public void setApp(MainMenuController mainmenu) {
        // init
        this.mainmenu = mainmenu;

        // add 'All' choice to Choice Box and select it as default
        choiceBox.getItems().add("All");
        choiceBox.getSelectionModel().selectFirst();

        // Get instrument from main. Add all class names as choice box's categories 
        List<Instrument> listedInstruments = Main.getInstance().getInstruments();
        if(listedInstruments==null) {
            mainmenu.hideIncludePane();
            return;
        }
        for (int i = 0; i < listedInstruments.size(); i++) {
            if (!choiceBox.getItems().contains(listedInstruments.get(i).getClass().getSimpleName())) {
                choiceBox.getItems().add(listedInstruments.get(i).getClass().getSimpleName());
            }
        }
    }

    /**
    * (for 'addedToken' and 'listToken' only, they use different CustomizeController). 
    * called by main CustomizeController. 
    */
    public void setApp(CustomizeController parent) {
        this.addedInstruments = new ArrayList<Instrument>();
        this.parent = parent;
    }
    
    /**
    * Clear all to start
    */
    public void clearAll() {
        addedInstruments = new ArrayList<Instrument>();
        addedScroll.getChildren().clear();
        listScroll.getChildren().clear();
        iconGrid.getChildren().clear();
        setTotal(0);
    }

    /**
    *  init 
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    
    /**
    *  set total text
     * @param total total money to be set 
    */
    public void setTotal(float total) {
        this.total.setText("฿" + total + "/hr");
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
    
    /**
    *  getAddedScroll of main CustomizeController, for addedToken,listToken to call and add/count child
    */
    protected VBox getAddedScroll() {
        return addedScroll;
    }
 
    /**
    *  add icon to top-left grid
    */
    public void addIconGrid(ImageView img) {
        int row = iconGrid.getChildren().size() / (iconGrid.getColumnConstraints().size());
        int col = iconGrid.getChildren().size() % iconGrid.getColumnConstraints().size();
        System.out.println(row+".."+col);
        iconGrid.setPrefHeight(img.getFitHeight()*(row+1));
        System.out.println("Pref:"+iconGrid.getPrefHeight());
        
        if(col == 0 && row+1>iconGrid.getRowConstraints().size())
            iconGrid.getRowConstraints().add(new RowConstraints());

        iconGrid.add(img, col, row);
        iconGrid.getRowConstraints().get(row).setMinHeight(img.getFitHeight());
        iconGrid.getRowConstraints().get(row).setPrefHeight(img.getFitHeight());
        iconGrid.getRowConstraints().get(row).setMaxHeight(img.getFitHeight());
        
    }

    /**
    *  remove icon from top-left grid
    */
    public void removeIconGrid(int id) {
        GridPane.clearConstraints(iconGrid);
        iconGrid.getChildren().remove(id);
        int row = iconGrid.getChildren().size() / (iconGrid.getColumnConstraints().size());
        iconGrid.setPrefHeight(150*(row+1));
        for (int i = id; i < iconGrid.getChildren().size(); i++) {
            GridPane.setConstraints(iconGrid.getChildren().get(i), i % iconGrid.getColumnConstraints().size(), i / iconGrid.getColumnConstraints().size());
        }
    }
    
    /**
    *  go button handler
    */
    public void onClickGo(ActionEvent event) {
        listScroll.getChildren().clear();
        List<Instrument> listedInstruments = Main.getInstance().getInstruments();
        for (int i = 0; i < listedInstruments.size(); i++) {
            if (!listedInstruments.get(i).getClass().getSimpleName().equals(choiceBox.getSelectionModel().getSelectedItem()) && !choiceBox.getSelectionModel().getSelectedItem().equals("All")) {
                continue;
            }
            if (!(listedInstruments.get(i).getName() + listedInstruments.get(i).getModel()).replaceAll("[\\s|\\u00A0]+", "").toLowerCase().contains(searchTxtField.getText().replaceAll("[\\s|\\u00A0]+", "").toLowerCase()) && !searchTxtField.getText().replaceAll("[\\s|\\u00A0]+", "").equals("")) {
                continue;
            }
            AnchorPane newToken = copyListToken(listedInstruments.get(i));
            newToken.setId(String.valueOf(listedInstruments.get(i).getId()));
            listScroll.getChildren().add(newToken);
        }
    }
    
    /**
    *  Create tooltip for each listToken when hover
    */
    public void onMouseEntered(MouseEvent event) {
        int selectedId = Integer.parseInt(((AnchorPane) event.getSource()).getId());
        Instrument instrument = Main.getInstance().getInstrument(selectedId);
        Tooltip tooltip = new Tooltip(instrument.toDetailString());
        ImageView imgView = new ImageView(instrument.getImg());
        imgView.setFitWidth(200);
        imgView.setFitHeight(200);
        imgView.setPreserveRatio(true);
        
        tooltip.setFont(Font.font("Courier New", 20));
        tooltip.setGraphic(imgView);
        Tooltip.install(listToken, tooltip);
    }

    /**
    *  add instrument handler
    */
    public void onAddInstrument(MouseEvent event) {
        // Get instrument from clicked listToken. add it to list
        int selectedId = Integer.parseInt(((AnchorPane) event.getSource()).getId());
        Instrument selectedInstrument = Main.getInstance().getInstrument(selectedId);
        parent.addInstrument(selectedInstrument);

        // copy new addToken. add it to addedScroll
        AnchorPane newToken = copyAddedToken(selectedInstrument);
        newToken.setId(String.valueOf(parent.getAddedScroll().getChildren().size()));
        parent.getAddedScroll().getChildren().add(newToken);

        // copy new copyIconToken. add it to iconGrid
        ImageView newIcon = copyIconToken(selectedInstrument);
        parent.addIconGrid(newIcon);

        // Total
        parent.setTotal(parent.calculateTotal());

    }

    /**
    *  remove instrument handler
    */
    public void onRemoveInstrument(MouseEvent event) {
        // Get instrument from clicked addedToken. remove it from list/addedScroll/iconGrid
        int selectedId = Integer.parseInt(((AnchorPane) event.getSource()).getId());
        parent.removeInstrument(selectedId);
        parent.getAddedScroll().getChildren().remove(selectedId);
        parent.removeIconGrid(selectedId);
        
        // Reassign addedToken(s) ID
        for (int i = 0; i < parent.getAddedScroll().getChildren().size(); i++) {
            parent.getAddedScroll().getChildren().get(i).setId(String.valueOf(i));
        }

        // Total
        parent.setTotal(parent.calculateTotal());
    }

    /**
    *  back button handler
    */
    public void onClickBack(ActionEvent event) {
        mainmenu.gotoTemplate();
    }

    /**
    *  confirm button handler
    */
    public void onClickConfirm(ActionEvent event) {
        // if not select any instrument
        if (addedInstruments.isEmpty()) {
            System.out.println("Error: Please add some instruments first");
            Main.getInstance().showPopup("Wait Wait!", "Please add some instruments first");
            return;
        }

        // create new CustomRoomTemplate from Instrument selected
        CustomRoomTemplate newRoom = new CustomRoomTemplate("Custom", "A new custom room", calculateTotal());
        for (int i = 0; i < addedInstruments.size(); i++) {
            newRoom.addInstrument(addedInstruments.get(i));
        }

        // set to main. continue to time selection
        Main.getInstance().setCurrentRoom(newRoom);
        mainmenu.gotoTimeSelect();

    }

   
    /**
    *  calculate total price per hour of all instruments.
    */
    private float calculateTotal() {
        float price = 0f;
        for (int i = 0; i < addedInstruments.size(); i++) {
            price += addedInstruments.get(i).getRentPrice();
        }
        return (float) Math.ceil(price);
    }

    /**
    * copy listToken by loading it and set data
    */
    private AnchorPane copyListToken(Instrument instrument) {
        // load only fxid='listToken' from fxml
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane) loader.getNamespace().get("listToken");

        // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController) loader.getController();
        cus.setApp(this);
        cus.setListToken(instrument.getName() + " " + instrument.getModel(),
                instrument.getClassPath(),
                Float.toString(instrument.getRentPrice()),
                instrument.getIcon());

        return newToken;
    }
    
    /**
    * copy addedToken by loading it and set data
    */
    private AnchorPane copyAddedToken(Instrument selectedInstrument) {
        // load only fxid='addedToken' from fxml
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane) loader.getNamespace().get("addedToken");

        // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController) loader.getController();
        cus.setApp(this.parent);
        cus.setAddedToken(selectedInstrument.getClassPath() + ": " + selectedInstrument.getName() + " " + selectedInstrument.getModel(),
                Float.toString(selectedInstrument.getRentPrice()));

        return newToken;
    }

    /**
    * copy iconToken by loading it and set data
    */
    private ImageView copyIconToken(Instrument selectedInstrument) {
        // load only fxid='iconToken' from fxml
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView newToken = (ImageView) loader.getNamespace().get("iconToken");

        // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController) loader.getController();
        cus.setApp(this.parent);
        cus.setIconToken(selectedInstrument.getIcon());

        return newToken;
    }

    /**
    * (called by copyListToken of main CustomizeController) set image/text of listToken
    */
    protected void setListToken(String name, String path, String price, Image img) {
        listToken_name.setText(name);
        listToken_path.setText(path);
        listToken_price.setText(price + "฿/hr");
        if (img == null) {
            return;
        }
        listToken_pic.setImage(img);
        listToken_pic.setPreserveRatio(false);

    }

    /**
    * (called by copyAddedToken of main CustomizeController) set image/text of addedToken
    */
    protected void setAddedToken(String name, String price) {
        addedToken_name.setText(name);
        addedToken_price.setText(price + "฿/hr");
    }

    /**
    * (called by copyIconToken of main CustomizeController) set image/text of iconToken
    * @param img  image of an icon
    */
    protected void setIconToken(Image img) {
        iconToken.setImage(img);
        iconToken.setPreserveRatio(false);
    }
}
