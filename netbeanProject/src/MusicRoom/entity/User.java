/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class User {
    @Id@GeneratedValue
    private long id;

    private String username = "";
    private String password = "";
    private List<Calendar> bookedTimes;
    

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        bookedTimes = new ArrayList<Calendar>();
    }


    public long getId() {
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

    public List<Calendar> getBookedTimes() {
        return bookedTimes;
    }

    public void addBookedTime(Calendar time) {
        bookedTimes.add(time);
    }
}
