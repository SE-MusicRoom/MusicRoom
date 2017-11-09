/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author YAY
 */
@Entity
public class RoomTemplate {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String detail;
    private float price;
    
    //@ElementCollection
    @ManyToMany
    private List<Instrument> instruments;

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

    public List<Instrument> getInstruments() {
        return instruments;
    }
    
    @Override
    public String toString() {
        return "Name:" + this.name +
               "\nTHB" + this.price +
               "\nInstruments:" + this.instruments /*+
               "\nTimeTable: " + this.timeTable.toString() */;
    }
    
}
