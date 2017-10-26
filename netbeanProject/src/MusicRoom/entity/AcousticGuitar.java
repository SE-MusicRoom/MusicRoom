/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import javafx.scene.image.Image;

/**
 *
 * @author kyttpwned
 */
public class AcousticGuitar extends Guitar{
    private String name;
    private String model;
    private float price;
    private Image img;

    public AcousticGuitar(String name, String model, float price, Image img) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.img = img;
    }
    
}
