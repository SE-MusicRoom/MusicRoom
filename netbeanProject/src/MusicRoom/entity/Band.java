/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import MusicRoom.DatabaseManager;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author SE-MUSICROOM
 */

@Entity
public class Band {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    private String name;
    
    @OneToMany(mappedBy = "band",fetch=FetchType.EAGER)
    private List<User> members;

    public Band(String name) {
        this.name = name;
        members = new ArrayList<User>();
    }
    
    public void addMember(User m) {
        members.add(m);
    }

    public String getName() {
        return name;
    }
    
    
    
    public static Band findBand(String name) {
        Band b = DatabaseManager.getInstance().fetchBand(name);
        if(b == null) {
            b =  new Band(name);
            DatabaseManager.getInstance().addBand(b);
        }
        return b;
            
    }
}
