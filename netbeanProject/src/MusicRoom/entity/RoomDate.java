/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;

/**
 *
 * @author YAY
 */
class RoomDate {
    private ArrayList<RoomTime> times = new ArrayList<RoomTime>();
    private boolean full;
    private boolean nearlyFull;

    public RoomDate() {
        this.full = false;
        this.nearlyFull = false;
    }

    public boolean isFull() {
        return full;
    }

    public boolean isNearlyFull() {
        return nearlyFull;
    }
    
    public ArrayList<RoomTime> getTimes() {
        return times;
    }
    
    
}
