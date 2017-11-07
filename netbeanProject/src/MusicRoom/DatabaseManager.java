package MusicRoom;

import MusicRoom.entity.AcousticGuitar;
import MusicRoom.entity.Booking;
import MusicRoom.entity.Instrument;
import MusicRoom.entity.User;
import MusicRoom.entity.Violin;
import java.util.ArrayList;

import javax.persistence.*;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private EntityManagerFactory emf;
    private EntityManager em;

    private DatabaseManager(){};

    public static DatabaseManager getInstance() {
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }
    private void createEMF(String DbName){
        this.emf = Persistence.createEntityManagerFactory(DbName);
        this.em = this.emf.createEntityManager();
    }
    private void closeEMF(){
        this.em.close();
        this.emf.close();
    }

    public void createInstrumentDB (){
        String violinPath = new String("src\\MusicRoom\\img\\Instrument\\Violin\\");
        String AcousticGuitarPath = new String("src\\MusicRoom\\img\\Instrument\\AcousticGuitar\\");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("src\\MusicRoom\\database\\Instruments.odb");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        ArrayList<Instrument> l = new ArrayList<Instrument>();
        // Violin
        l.add(new Violin("Andrea Schudtz","2800",50000,(violinPath + "Andrea Schudtz.jpg")));
        l.add(new Violin("Dario II Vettori","2006",600000,(violinPath + "Dario II Vettori.jpg")));
        l.add(new Violin("Robert Lee","804",45000,(violinPath + "Robert Lee.jpg")));

        //AcousticGuitar
        l.add(new AcousticGuitar("Takamine","CP3DC",47500,(AcousticGuitarPath + "Takamine.jpg")));
        l.add(new AcousticGuitar("Taylor","310CE",67900,AcousticGuitarPath + "Taylor.jpg"));
        l.add(new AcousticGuitar("Guild","M-240E",15750,(AcousticGuitarPath + "Guild.jpg")));



        for(Instrument i: l){
            em.persist(i);
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public List<Instrument> fetchAllInstrument (){
        createEMF("objectdb://10.66.15.12/instrument.odb;user=admin;password=admin");

        TypedQuery<Instrument> query = em.createQuery("SELECT l FROM Instrument l", Instrument.class);
        List<Instrument> results = query.getResultList();

        closeEMF();
        return results;
    }

    public void addUser(User user){
        createEMF("objectdb://local/user.odb;user=admin;password=admin");

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        closeEMF();
    }
    
    public void addBooking(Booking booking){
        createEMF("objectdb://10.66.15.12/booking.odb;user=admin;password=admin");

        em.getTransaction().begin();
        em.persist(booking);
        em.getTransaction().commit();

        closeEMF();
    }

    public List<User> fetchAllUser(){
        createEMF("objectdb://10.66.15.12/user.odb;user=admin;password=admin");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u ", User.class);
        List<User> results = query.getResultList();

        closeEMF();
        return results;
    }
    
    public List<Booking> fetchAllBooking(){
        createEMF("objectdb://10.66.15.12/booking.odb;user=admin;password=admin");

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b ", Booking.class);
        List<Booking> results = query.getResultList();

        closeEMF();
        return results;
    }

    public User fetchUser(int id){
        createEMF("objectdb://10.66.15.12/user.odb;user=admin;password=admin");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =" + String.valueOf(id), User.class);
        User result = query.getSingleResult();

        closeEMF();
        return result;
    }




}