/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;
import javax.persistence.*;
/**
 *
 * @author kyttpwned
 */
@Entity
public class ElectricBass extends BassGuitar{

    public ElectricBass(String name ,String model ,float price ,String img) {
        super(name ,model ,price ,img);
    }
    
}
