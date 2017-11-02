/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Kittinan
 */
@MappedSuperclass
abstract public class Instrument implements Serializable{
    @Id@GeneratedValue
    private long id;

    private String name;
    private String model;
    private float price;
    private String img;

    public Instrument(String name, String model, float price, String img) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.img = img;
    }
    
    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public float getPrice() {
        return price;
    }
    
    public String getImage() {
        return img;
    }
}
