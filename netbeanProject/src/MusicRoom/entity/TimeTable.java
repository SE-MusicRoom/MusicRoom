/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author YAY
 */
public class TimeTable {
    private ArrayList<Calendar> reservedTimes;

    public TimeTable() {
        this.reservedTimes = new ArrayList<Calendar>();
    }
    
    public TimeTable(ArrayList<Calendar> reservedTimes) {
        this.reservedTimes = reservedTimes;
    }

    public ArrayList<Calendar> getReservedTimes() {
        return reservedTimes;
    }
    
    public String ToString() {
        String string = "";
        for (int i = 0; i < reservedTimes.size(); i++) {
            string += reservedTimes.get(i).toString() + "||";
        }
        return string;
    }
    
    
}
