/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.Calendar;

/**
 *
 * @author YAY
 */
public class Booking {
    private RoomTemplate room;
    private TimeTable timeTable;
    private User user;
    private Calendar createTime;

    public Booking(RoomTemplate room, TimeTable timeTable,User user) {
        this.room = room;
        this.timeTable = timeTable;
        this.user = user;
        this.createTime = Calendar.getInstance();
    }
    
    
    public String ToString() {
        return "Name:" + this.room.getName() +
               "\nTHB" + this.room.getPrice() +
               "\nInstruments:" + this.room.getInstruments().size()  +
               "\nUser: " + this.user.getUsername() +
               "\nTimeTable: " + this.timeTable.toString() ;
    }
    
    
}
