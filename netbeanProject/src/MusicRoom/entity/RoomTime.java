/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

/**
 *
 * @author YAY
 */
class RoomTime {
    private RoomDate date;
    private boolean available;

    public RoomTime(RoomDate date) {
        this.date = date;
        this.available = true;
    }

    public RoomDate getDate() {
        return date;
    }

    public void setDate(RoomDate date) {
        this.date = date;
    }

    public boolean isOwned() {
        return available;
    }

    public void setOwned(boolean available) {
        this.available = available;
    }
    
    
}
