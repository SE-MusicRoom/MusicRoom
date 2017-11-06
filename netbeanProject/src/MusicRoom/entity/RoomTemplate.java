/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;

/**
 *
 * @author YAY
 */
public class RoomTemplate {
    private int id;
    private String name;
    private String detail;
    private float price;
    private ArrayList<Instrument> instruments;

    public RoomTemplate(String name, String detail, float price) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        instruments = new ArrayList<Instrument>();
    }
    
    public void addInstrument(Instrument newInstrument) {
        instruments.add(newInstrument);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public float getPrice() {
        return price;
    }

    public ArrayList<Instrument> getInstruments() {
        return instruments;
    }
    
    
}
