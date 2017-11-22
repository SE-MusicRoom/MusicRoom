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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

   @FXML
    private Rectangle rect_1;

    @FXML
    private Rectangle rect_2;

    @FXML
    private Text txt_BEYOURSELF;

    @FXML
    private PasswordField password;

    @FXML
    private Button btn_reg;

    @FXML
    private Text txt_MUSIC;

    @FXML
    private Button btn_login;

    @FXML
    private Text txt_ROOM;
    
    @FXML
    private Text txt_USERNAME;
    
    @FXML
    private Text txt_PASSWORD;

    @FXML
    private TextField userId;

    @FXML
    private Rectangle rect_3;

    @FXML
    private Rectangle rect_4;

    
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    
    
    public void setApp() {
        TranslateTransition[] tt = new TranslateTransition[13];
        
        tt[0] = new TranslateTransition(Duration.millis(100), rect_1);
        tt[0].setFromY( -1000 );
        tt[0].setToY( 0 );
        
        tt[1] = new TranslateTransition(Duration.millis(100), rect_2);
        tt[1].setFromX( -1000 );
        tt[1].setToX( 0 );     
        
        tt[2] = new TranslateTransition(Duration.millis(100), rect_3);
        tt[2].setFromX( 1000 );
        tt[2].setToX( 0 );
        
        tt[3] = new TranslateTransition(Duration.millis(100), rect_4);
        tt[3].setFromY( 1000 );
        tt[3].setToY( 0 );
        
        tt[4] = new TranslateTransition(Duration.millis(100), txt_MUSIC);
        tt[4].setFromY( -1000 );
        tt[4].setToY( 0 );
        
        tt[5] = new TranslateTransition(Duration.millis(100), txt_ROOM);
        tt[5].setFromX( 1000 );
        tt[5].setToX( 0 );
        
        tt[6] = new TranslateTransition(Duration.millis(50), txt_BEYOURSELF);
        tt[6].setFromX( -1000 );
        tt[6].setToX( 0 );
        
        tt[7] = new TranslateTransition(Duration.millis(50), userId);
        tt[7].setFromX( -1000 );
        tt[7].setToX( 0 );
        
        tt[8] = new TranslateTransition(Duration.millis(50), password);
        tt[8].setFromX( 1000 );
        tt[8].setToX( 0 );
        
        tt[9] = new TranslateTransition(Duration.millis(50), txt_USERNAME);
        tt[9].setFromX( -1000 );
        tt[9].setToX( 0 );
        
        tt[10] = new TranslateTransition(Duration.millis(50), txt_PASSWORD);
        tt[10].setFromX( -1000 );
        tt[10].setToX( 0 );
        
        tt[11] = new TranslateTransition(Duration.millis(100), btn_login);
        tt[11].setFromX( 1000 );
        tt[11].setToX( 0 );
        
        tt[12] = new TranslateTransition(Duration.millis(100), btn_reg);
        tt[12].setFromX( 1000 );
        tt[12].setToX( 0 );
        
        SequentialTransition sequentialTransition = new SequentialTransition();
        for (int i = 0; i < tt.length; i++) {
           // tt[i].play();
            sequentialTransition.getChildren().add(tt[i]);
        }
        sequentialTransition.setDelay(Duration.millis(1));
        
        sequentialTransition.setOnFinished((ActionEvent event) -> {

            userId.setPromptText("username");
            password.setPromptText("password");
            userId.requestFocus();
        });
        
        sequentialTransition.play();
    }
    
    @FXML
    public void onClickLogin(ActionEvent event) {
        if (Main.getInstance() == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            //errorMessage.setText("Hello " + userId.getText());
        } else if (!Main.getInstance().isPopupOpen()){
            if ((userId.getText().equals("") || password.getText().equals("")))
                Main.getInstance().showPopup("Calm down!", "Please enter both username and password so that we can know who are you :O");
            else if (!Main.getInstance().userLogging(userId.getText(), password.getText())){
                
                Main.getInstance().showPopup("Error", "Username or Password is wrong :(");
            }
        }
    }
    @FXML
    public void onClickGoRegister(ActionEvent event) {
        Main.getInstance().gotoRegister();
    }
}
