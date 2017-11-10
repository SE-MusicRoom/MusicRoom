/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package MusicRoom;

import MusicRoom.entity.CustomRoomTemplate;
import MusicRoom.entity.Instrument;
import MusicRoom.entity.RoomTemplate;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Login Controller.
 */
public class CustomizeController extends AnchorPane implements Initializable {

    @FXML
    private Text total;
    
    @FXML
    private AnchorPane listToken;

    @FXML
    private Button backBtn;

    @FXML
    private TextField searchTxtField;

    @FXML
    private Button GoBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private VBox listScroll;

    @FXML
    private AnchorPane addedToken;

    @FXML
    private VBox addedScroll;
    
    private MainMenuController mainmenu;
    private CustomizeController parent;
    public static CustomizeController instance;
    
    private List<Instrument> addedInstruments;
    
    public void setApp(MainMenuController mainmenu){
        this.addedInstruments = new ArrayList<Instrument>();
        this.mainmenu = mainmenu;
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
        this.total.setText("THB"+total+"/hr");
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
        List<Instrument> listedInstruments = Main.getInstance().getInstruments();
        System.out.println("Start");
        for (int i = 0; i < listedInstruments.size(); i++) {
            System.out.println(listedInstruments.get(i).getName());
            AnchorPane newToken = copyListToken(listedInstruments.get(i).getName()+" "+listedInstruments.get(i).getModel(),listedInstruments.get(i).getClassPath(),Float.toString(listedInstruments.get(i).getRentPrice()));
            newToken.setId(String.valueOf(i));
            listScroll.getChildren().add(newToken);
        }
    }
    
    
    public void onAddInstrument(MouseEvent event) {
        
        int selectedId = Integer.parseInt( ((AnchorPane)event.getSource()).getId() );
        Instrument selectedInstrument = Main.getInstance().getInstrument(selectedId);
        parent.addInstrument(selectedInstrument);
        System.out.println("Added " +  selectedInstrument.getName() +  selectedInstrument.getModel());
        
        
        AnchorPane newToken = copyAddedToken(selectedInstrument.getClass().getSimpleName()+": "+selectedInstrument.getName()+" "+selectedInstrument.getModel(),Float.toString(selectedInstrument.getRentPrice()));
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
        return price;
    }
    
    private AnchorPane copyListToken(String name,String path,String price) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        
        try {   
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("listToken");
        Text t0 = (Text) newToken.getChildren().get(0);
        Text t1 = (Text) newToken.getChildren().get(1);
        Text t2 = (Text) newToken.getChildren().get(2);
        t0.setText(name);
        t1.setText(path);
        t2.setText("THB"+price+"/hr");
        
         // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController)loader.getController();
        cus.setApp(this);
        
        return newToken;
    }
    
    private AnchorPane copyAddedToken(String name,String price) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AnchorPane newToken = (AnchorPane)loader.getNamespace().get("addedToken");
        Text t0 = (Text) newToken.getChildren().get(0);
        Text t1 = (Text) newToken.getChildren().get(1);
        t0.setText(name);
        t1.setText("THB"+price+"/hr");
        
        // Set parent = CustomizeController for new cloned button (It has different CustomizeController)
        CustomizeController cus = (CustomizeController)loader.getController();
        cus.setApp(this.parent);
        
        return newToken;
    }
}
