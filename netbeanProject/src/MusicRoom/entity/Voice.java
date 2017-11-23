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
public class Voice extends Instrument{
    
    //condenser or dynamic
    private String frequencyResponse;
    
    public Voice(String type, String name ,String model ,float price ,String img) {
        super(name ,model ,price ,img);
        frequencyResponse = type;
    }

    public String getFrequencyResponse() {
        return frequencyResponse;
    }

    public void setFrequencyResponse(String frequencyResponse) {
        this.frequencyResponse = frequencyResponse;
    }
    
    @Override
    public String getClassPath() {
        return "Microphone";
    }
    
}


