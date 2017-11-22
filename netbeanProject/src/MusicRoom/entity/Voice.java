/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

/**
 *
 * @author kyttpwned
 */
public class Voice extends Instrument{
    
    private String frequencyResponse;
    public Voice(String type, String name ,String model ,float price ,String img) {
        super(name ,model ,price ,img);
        frequencyResponse = type;
    }

    public String getFrequencyResponse() {
        return frequencyResponse;
    }
    
}


