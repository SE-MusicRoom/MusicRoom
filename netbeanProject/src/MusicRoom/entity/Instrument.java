/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

/**
 *
 * @author Kittinan
 */
abstract public class Instrument {
    private String name;
    private String model;
    private float price;

    public Instrument() {
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
    
    
}
