/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class User {
    @Id@GeneratedValue
    private long id;

    private String username;
    private String password;
    
    @ElementCollection(fetch=FetchType.EAGER)
    private List<Booking> bookedTimes;
    
    private String name;
    private String surname;
    private String email;
    private String bandName;

    public User(String username, String password, String name, 
            String surname, String email, String bandName) {
        this.username = username;
        this.password = password;
        bookedTimes = new ArrayList<Booking>();
        
        this.name = name;
        this.name = surname;
        this.name = email;
        this.name = bandName;
    }


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Booking> getBookedTimes() {
        return bookedTimes;
    }

    public void addBookedTime(Booking time) {
        bookedTimes.add(time);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getBandName() {
        return bandName;
    }
    
    
}
