/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author YAY
 */
@Entity
public class Booking {
    @Id@GeneratedValue
    private long id;
    
    @OneToOne
    private RoomTemplate room;
    
    @OneToMany(mappedBy="timeTable")
    private List<Calendar> timeTable;
    
    @OneToOne @MapsId
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createTime;

    public Booking(RoomTemplate room, List<Calendar> timeTable,User user) {
        this.room = room;
        this.timeTable = timeTable;
        this.user = user;
        this.createTime = Calendar.getInstance();
    }
    
    
    @Override
    public String toString() {
        return "Name:" + this.room.getName() +
               "\nTHB" + this.room.getPrice() +
               "\nInstruments:" + this.room.getInstruments().size()  +
               "\nUser: " + this.user.getUsername() +
               "\nTimeTable: " + this.timeTable.toString() ;
    }
    
    
}
