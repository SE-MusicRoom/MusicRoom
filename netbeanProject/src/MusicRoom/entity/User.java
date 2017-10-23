/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.Calendar;

public class User {

    private String username = "";
    private String password = "";
    private ArrayList<Calendar> bookedTimes;

    private String id;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        bookedTimes = new ArrayList<Calendar>();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Calendar> getBookedTimes() {
        return bookedTimes;
    }

    public void addBookedTime(Calendar time) {
        bookedTimes.add(time);
    }
}
