/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MusicRoom.entity;

import MusicRoom.DatabaseManager;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author SE-MUSICROOM
 */
@Entity
public class RoomTemplate {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String detail;
    private float price;
    
    //@ElementCollection
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Instrument> instruments;
    
    @OneToMany(mappedBy = "room",fetch=FetchType.EAGER)
    private List<Booking> bookings;
    
    transient private Image img;

    public RoomTemplate(String name, String detail, float price) {
        this.name = name;
        this.detail = detail;
        this.price = (float) Math.ceil(price);
        this.instruments = new ArrayList<Instrument>();
        this.bookings = new ArrayList<Booking>();
    }
    
    public void addInstrument(Instrument newInstrument) {
        instruments.add(newInstrument);
    }
    
    public void addBooking(Booking newBooking) {
        bookings.add(newBooking);
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }
    
    public List<Booking> getBookings() {
        return updateBookings();
    }
    
    public List<Booking> updateBookings() {
        this.bookings = DatabaseManager.getInstance().fetchBookingByRoomID(this.id);
        System.out.println(bookings.size());
        return bookings;
    }
    

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public float getPrice() {
        return price;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }
    
    
    public Image getImg() {
        if(img==null)
            try {
                img = new Image("MusicRoom/img/RoomTemplate/"+name+".jpg");
                //System.out.println(name+"::"+imgLoaded.isError()+"::"+img);
            } catch (Exception e) {
                System.out.println("Image not found: "+e.toString());
            }
            
        return img;
    }
    
    @Override
    public String toString() {
        String detail = 
               "Name: " + this.name +
               "\nDetail: " + this.detail +
               "\n฿/Hr.: " + this.price +
               "\nInstruments:" + this.instruments.size();
                for (int i = 0; i < this.instruments.size(); i++) {
                    if(this.instruments.get(i) != null)
                        detail += "\n - " + this.instruments.get(i).toString();
                }
        return detail;
    }
    
}
