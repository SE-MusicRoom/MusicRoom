/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.text.SimpleDateFormat;
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

    public String getTimeTableString() {
        String str = "";
        int lastDay = -1;
        int lastHour = -1;
        //   13/02/60 (13:00 - 16:59)
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yy (HH:mm - ");
        String toAdd = "";
        for(int i=0;i<timeTable.size();i++) {
            int thisHour = timeTable.get(i).get(Calendar.HOUR_OF_DAY);
            int thisDay = timeTable.get(i).get(Calendar.DATE);
            if((thisDay!=lastDay && lastDay !=-1) || (thisHour!=lastHour+1 && lastHour !=-1) || i==0 || i==timeTable.size()-1) {
                if(i!=0 && i!=timeTable.size()-1) { // if not first one
                    toAdd += lastHour + ":59)";
                }    
                else if(i==timeTable.size()-1 && i!=0) 
                    if(thisHour==lastHour+1 && thisDay==lastDay) { // if the last is continuous
                        toAdd += thisHour + ":59)";
                    } else {// if the last is new
                        toAdd += lastHour + ":59)";
                        str += toAdd + "\n";
                        toAdd = format1.format(timeTable.get(i).getTime()) + thisHour + ":59)";
                    }
                str += toAdd + "\n";
                toAdd = format1.format(timeTable.get(i).getTime());
            }
            
            lastDay = thisDay;
            lastHour = thisHour;
        }
        return str;
    }
    
    @Override
    public String toString() {
        return "Booking ID:" + this.id + 
                "\nUser: " + this.user.getUsername() + "\n" +
               this.room.toString() +
               "\nTime Table("+timeTable.size()+"): \n" + getTimeTableString();
    }
    
    
}
