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

    public ArrayList<Calendar> getReservedTimes() {
        return reservedTimes;
    }
    
    
}
