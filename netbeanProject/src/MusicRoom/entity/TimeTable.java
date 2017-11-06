/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author YAY
 */
public class TimeTable {
    private List<Calendar> reservedTimes;

    public TimeTable() {
        this.reservedTimes = new ArrayList<Calendar>();
    }
    
    public TimeTable(List<Calendar> reservedTimes) {
        this.reservedTimes = reservedTimes;
    }

    public List<Calendar> getReservedTimes() {
        return reservedTimes;
    }
    
    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < reservedTimes.size(); i++) {
            string += reservedTimes.get(i).getTime().toString() + "||";
        }
        return string;
    }
    
    
}
