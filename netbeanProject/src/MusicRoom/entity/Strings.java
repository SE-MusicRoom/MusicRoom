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
public class Strings extends Instrument{
    
    private String number_of_strings;
    private String type;
    
    public Strings(String type, String number_of_strings, String name ,String model ,float price ,String img) {
        super(name ,model ,price ,img);
        this.number_of_strings = number_of_strings;
        this.type = type;
    }

    public String getNumber_of_strings() {
        return number_of_strings;
    }
    
}


