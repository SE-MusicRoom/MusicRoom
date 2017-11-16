/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.Calendar;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author SE-MUSICROOM
 */
@Entity
public class Booking {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private float price;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private RoomTemplate room;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @Temporal(TemporalType.TIMESTAMP)
    private List<Calendar> timeTable;
    
    @ManyToOne
    private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createTime;

    public Booking(RoomTemplate room, List<Calendar> timeTable,User user,float price) {
        this.room = room;
        this.timeTable = timeTable;
        this.user = user;
        this.createTime = Calendar.getInstance();
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getID() {
        return id;
    }

    
    
    @Override
    public String toString() {
        return "\nUser: " + this.user.getUsername() + "\n" +
               this.room.toString() +
               "\nTimeTable: " + this.timeTable;
    }
    
    
}
