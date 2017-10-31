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

import MusicRoom.entity.Instrument;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Login Controller.
 */
public class CustomizeController extends AnchorPane implements Initializable {

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
    
    private Main application;
    private MainMenuController mainmenu;
    
    
    public void setApp(Main application,MainMenuController mainmenu){
        this.application = application;
        this.mainmenu = mainmenu;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
    }
    
    public void onClickGo(ActionEvent event) {
        ArrayList<Instrument> instruments = application.getInstruments();
        System.out.println("Start");
        for (int i = 0; i < instruments.size(); i++) {
            System.out.println(instruments.get(i).getName());
            AnchorPane newToken = copyListToken();
            listScroll.getChildren().add(newToken);
            
        }
    }
    public void onClickBack(ActionEvent event) {
        mainmenu.hideIncludePane();
    }
    public void onClickConfirm(ActionEvent event) {
        
    }
    
    private AnchorPane copyListToken() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("customize.fxml"));
        
        try {
            
            loader.load();
            
        } catch (IOException ex) {
            Logger.getLogger(CustomizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return (AnchorPane)loader.getNamespace().get("listToken");
    }
}
