/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import MusicRoom.*;
import javax.persistence.MappedSuperclass;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import javafx.scene.image.Image;
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
    
    transient private Image imgLoaded;

    public Instrument(String name, String model, float price, String img) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.img = img;
    }
    

    
    public String getClassPath() {
        String str = "";
        Class C = getClass();
        int round = 2;
        while (C != Instrument.class && round>0) {
            str = C.getSimpleName() + "/" + str;
            C = C.getSuperclass();
            round--;
        }
        return str.substring(0, str.length() - 1);
    }

    public long getId() {
        return id;
    }
    public String getImgPath() {
        return img;
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

    public void setImgPath(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    public Image getImg() {
        if(imgLoaded==null)
            try {
                imgLoaded = new Image(getClass().getClassLoader().getResource(img).toString());
                //System.out.println(name+"::"+imgLoaded.isError()+"::"+img);
            } catch (Exception e) {
                System.out.println("Image not found: "+e.toString()+"\n"+img);
            }
            
        
        return imgLoaded;
    }
    
    
}
