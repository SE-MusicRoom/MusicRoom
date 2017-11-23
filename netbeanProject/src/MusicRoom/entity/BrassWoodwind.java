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
public class BrassWoodwind extends Instrument{
    
    //brass or woodwind
    private String type;
    private String subtype;
    
    public BrassWoodwind(String type,String suptype, String name ,String model ,float price ,String img) {
        super(name ,model ,price ,img);
        this.type = type;
        this.subtype = suptype;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
    
    @Override
    public String getClassPath() {
        return subtype;
    }
}


