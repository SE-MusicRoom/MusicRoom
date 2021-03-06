/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import javax.persistence.Entity;

/**
 *
 * @author kyttpwned
 */
@Entity
public class Percussion extends Instrument{

    private String partName;
    
    public Percussion(String partName, String name, String model, float price, String img) {
        super(name ,model ,price ,img);
        this.partName = partName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    @Override
    public String getClassPath() {
        return partName;
    }
       
    @Override
    public String toDetailString() {
        return super.toDetailString() + "\n" +
               partName;
    }
}


