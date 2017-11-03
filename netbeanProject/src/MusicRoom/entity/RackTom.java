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
public class RackTom extends Drums{

    public RackTom(String name ,String model ,float price ,String img) {
        super(name ,model ,price ,img);
    }
    
}
