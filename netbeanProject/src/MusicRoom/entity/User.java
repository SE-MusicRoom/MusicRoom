/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    
    @OneToMany(mappedBy = "user",fetch=FetchType.EAGER)
    //@ElementCollection()
    private List<Booking> bookedTimes;
    
    private String name;
    private String surname;
    private String email;
    
    @ManyToOne
    private Band band;

    public User(String username, String password, String name, 
            String surname, String email, Band band) {
        this.username = username;
        this.password = password;
        bookedTimes = new ArrayList<Booking>();
        
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.band = band;
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

    public List<Booking> getBookings() {
        return bookedTimes;
    }

    public void addBookedTime(Booking time) {
        bookedTimes.add(time);
    }
    
    public void removeBookedTime(Booking time) {
        bookedTimes.remove(time);
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

    public Band getBandName() {
        return band;
    }
    
    @Override
    public String toString() {
        return name + " " + surname + "(" + username + ")" + " แห่งวง " + band.getName(); 
    }
    
    
}
