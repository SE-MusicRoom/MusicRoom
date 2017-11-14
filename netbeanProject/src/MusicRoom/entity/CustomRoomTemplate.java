/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import MusicRoom.DatabaseManager;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;


/**
 *
 * @author YAY
 */
@Entity
public class CustomRoomTemplate extends RoomTemplate {
    
    //@OneToMany(mappedBy = "room",fetch=FetchType.EAGER)
    //@Transient
    private static List<Booking> bookings;
    
    public CustomRoomTemplate(String name, String detail, float price) {
        super(name, detail, price);
        //bookings = DatabaseManager.getInstance().fetchAllCustomBooking();
    }

    @Override
    public List<Booking> getBookings() {
        bookings = DatabaseManager.getInstance().fetchAllCustomBooking();
        return bookings;
    }
    
    
}
