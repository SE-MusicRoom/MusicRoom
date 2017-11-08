/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
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
    
    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private RoomTemplate room;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @Temporal(TemporalType.TIMESTAMP)
    private List<Calendar> timeTable;
    
    @ManyToOne(cascade=CascadeType.ALL)
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createTime;

    public Booking(RoomTemplate room, List<Calendar> timeTable,User user) {
        this.room = room;
        this.timeTable = timeTable;
        this.user = user;
        this.createTime = Calendar.getInstance();
    }

    public RoomTemplate getRoom() {
        return room;
    }

    public List<Calendar> getTimeTable() {
        return timeTable;
    }

    public User getUser() {
        return user;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    
    
    @Override
    public String toString() {
        return "\nUser: " + this.user.getUsername() + "\n" +
               this.room.toString() +
               "\nTimeTable: " + this.timeTable;
    }
    
    
}
