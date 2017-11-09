package MusicRoom;

import MusicRoom.entity.AcousticGuitar;
import MusicRoom.entity.Booking;
import MusicRoom.entity.Instrument;
import MusicRoom.entity.RoomTemplate;
import MusicRoom.entity.User;
import MusicRoom.entity.Violin;
import java.util.ArrayList;

import javax.persistence.*;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private String ip;

    private DatabaseManager(){
        ip = "objectdb://161.246.6.16:80";
    };

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
    
    public void createDummyAll() {
        createInstrumentDB();
        createDummyRoomTemplate();
        createDummyUser();
    }
    
    public void createDummyUser() {
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        em.getTransaction().begin();
        User l = new User("1234", "1234", "1234", "1234", "1234", "1234");
        User m = new User("123", "123", "123", "123", "123", "123");
        
        em.getTransaction().begin();
        em.persist(l);
        em.persist(m);

        em.getTransaction().commit();
        
        closeEMF();
    }

    public void createInstrumentDB (){
        String violinPath = new String("src\\MusicRoom\\img\\Instrument\\Violin\\");
        String AcousticGuitarPath = new String("src\\MusicRoom\\img\\Instrument\\AcousticGuitar\\");

        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

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
        closeEMF();
    }
    
    public void createDummyRoomTemplate (){

        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");
        em.getTransaction().begin();
        
        RoomTemplate l = new RoomTemplate("Violin Madness","Full of Violin",6000);
        
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        l.addInstrument(Main.getInstance().getInstrument("Dario II Vettori","2006"));
        em.persist(l);
       
        /*
        RoomTemplate m = new RoomTemplate("Guitar Madness","Full of Guitar",5000);
        
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        m.addInstrument(Main.getInstance().getInstrument("Takamine","CP3DC"));
        
        
        
        em.persist(m);*/

        em.getTransaction().commit();
        
        closeEMF();
    }

    public List<Instrument> fetchAllInstrument (){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        TypedQuery<Instrument> query = em.createQuery("SELECT l FROM Instrument l", Instrument.class);
        List<Instrument> results = query.getResultList();

        closeEMF();
        return results;
    }
    
    public List<RoomTemplate> fetchAllRoomTemplate (){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        TypedQuery<RoomTemplate> query = em.createQuery("SELECT l FROM RoomTemplate l", RoomTemplate.class);
        List<RoomTemplate> results = query.getResultList();

        closeEMF();
        return results;
    }

    public void addUser(User user){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        closeEMF();
    }
    
    
    
    public void addBooking(Booking booking){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        em.getTransaction().begin();
        em.persist(booking);
        em.getTransaction().commit();

        closeEMF();
    }

    public List<User> fetchAllUser(){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u ", User.class);
        List<User> results = query.getResultList();

        closeEMF();
        return results;
    }
    
    public List<Booking> fetchAllBooking(){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b ", Booking.class);
        List<Booking> results = query.getResultList();

        closeEMF();
        return results;
    }

    public User fetchUser(int id){
        createEMF(ip+"/MusicRoom.odb;user=admin;password=admin");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =" + String.valueOf(id), User.class);
        User result = query.getSingleResult();

        closeEMF();
        return result;
    }

    public void updateUser (User user, int id){
        User DbUser = fetchUser(id);
        createEMF("objectdb://"+ip+"/MusicRoom.odb;user=admin;password=admin");
        em.getTransaction().begin();
        if(user.getName() != DbUser.getName()){
            Query query = em.createQuery("UPDATE User u SET u.name = \"" + user.getName() + "\" WHERE u.id = " + String.valueOf(id) );
            query.executeUpdate();
        }
        if(user.getSurname() != DbUser.getSurname()){
            Query query = em.createQuery("UPDATE User u SET u.surname = \"" + user.getSurname() + "\" WHERE u.id = " + String.valueOf(id) );
            query.executeUpdate();
        }
        if(user.getBandName() != DbUser.getBandName()){
            Query query = em.createQuery("UPDATE User u SET u.bandname = \"" + user.getBandName() + "\" WHERE u.id = " + String.valueOf(id) );
            query.executeUpdate();
        }
        if(user.getEmail() != DbUser.getEmail()){
            Query query = em.createQuery("UPDATE User u SET u.email = \"" + user.getEmail() + "\" WHERE u.id = " + String.valueOf(id) );
            query.executeUpdate();
        }
        if(user.getUsername() != DbUser.getUsername()){
            Query query = em.createQuery("UPDATE User u SET u.username = \"" + user.getUsername() + "\" WHERE u.id = " + String.valueOf(id) );
            query.executeUpdate();
        }
        if(user.getPassword() != DbUser.getPassword()){
            Query query = em.createQuery("UPDATE User u SET u.password = \"" + user.getPassword() + "\" WHERE u.id = " + String.valueOf(id) );
            query.executeUpdate();
        }
        em.getTransaction().commit();
        closeEMF();
    }





}