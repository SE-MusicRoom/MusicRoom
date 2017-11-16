/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javax.persistence.GenerationType;

/**
 *
 * @author Kittinan
 */
@MappedSuperclass 
abstract public class Instrument implements Serializable{
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
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
    
    public String getClassPath() {
        String str = "";
        Class C = getClass();
        while (C != Instrument.class) {
            str = C.getSimpleName() + "/" + str;
            C = C.getSuperclass();
        }
        return str.substring(0, str.length() - 1);
    }

    public long getId() {
        return id;
    }
    
    public String toString() {
        return name + " " + model + " (" + getClassPath() + ")";
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
    
    public float getRentPrice() {
        return price/100;
    }
    
    public String getImage() {
        return img;
    }
    
}
