package MusicRoom;

import MusicRoom.entity.AcousticGuitar;
import MusicRoom.entity.Band;
import MusicRoom.entity.Booking;
import MusicRoom.entity.CustomRoomTemplate;
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
    
    private final String ip;

    private DatabaseManager(){
        ip = "objectdb://161.246.6.16:80";
    };

    public static DatabaseManager getInstance() {
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }
    private boolean createEMF(String DbName){
        try {
            this.emf = Persistence.createEntityManagerFactory(DbName);
            this.em = this.emf.createEntityManager();
        } catch (Exception ex) {
            //System.out.println("ex");
            //Main.getInstance().showErrorPopup("Error Connecting db", "\nClick confirm to try again");
            return false;
        }
        
        return true;
        
        
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
    
    public boolean createDummyUser() {
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        User l = new User("1234", "1234", "1234", "1234", "1234", null);
        User m = new User("123", "123", "123", "123", "123", null);
        
        em.getTransaction().begin();
        em.persist(l);
        em.persist(m);

        em.getTransaction().commit();
        
        closeEMF();
        return true;
    }

    public boolean createInstrumentDB (){

        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        ArrayList<Instrument> l = new ArrayList<Instrument>();
        // Violin
        l.add(new Violin("Andrea Schudtz","2800",50000,"MusicRoom/img/Instruments/Violin/Violin.png"));
        l.add(new Violin("Dario II Vettori","2006",600000,"MusicRoom/img/Instruments/Violin/Violin.png"));
        l.add(new Violin("Robert Lee","804",45000,"MusicRoom/img/Instruments/Violin/Violin.png"));

        //AcousticGuitar
        l.add(new AcousticGuitar("Takamine","CP3DC",47500,"MusicRoom/img/Instruments/Guitar/AcousticGuitar/AcousticGuitar.png"));
        l.add(new AcousticGuitar("Taylor","310CE",67900,"MusicRoom/img/Instruments/Guitar/AcousticGuitar/AcousticGuitar.png"));
        l.add(new AcousticGuitar("Guild","M-240E",15750,"MusicRoom/img/Instruments/Guitar/AcousticGuitar/AcousticGuitar.png"));

        // 
        
        
        //
        
        
        //

        for(Instrument i: l){
            em.persist(i);
        }

        em.getTransaction().commit();
        closeEMF();
        return true;
    }
    
    public boolean createDummyRoomTemplate (){

        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;
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
        return true;
    }



    public boolean addUser(User user){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }
    
    public boolean addBand(Band band){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        em.persist(band);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }
    
    public boolean addRoom(RoomTemplate roomTemplate){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        em.persist(roomTemplate);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }
    
    public boolean addCustomRoom(CustomRoomTemplate roomTemplate){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        em.persist(roomTemplate);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }
    
    public boolean addBooking(Booking booking){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        em.persist(booking);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }
    
    public boolean removeBooking(Booking booking){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return false;

        em.getTransaction().begin();
        em.remove(em.contains(booking) ? booking : em.merge(booking));
        em.getTransaction().commit();

        closeEMF();
        return true;
    }
    
    public List<User> fetchAllUser(){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return null;

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u ", User.class);
        List<User> results = query.getResultList();

        closeEMF();
        return results;
    }
    
    public List<Booking> fetchAllBooking(){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))            return null;

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b ", Booking.class);
        List<Booking> results = query.getResultList();

        closeEMF();
        return results;
    }
    
    public List<Instrument> fetchAllInstrument (){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return null;
        
        TypedQuery<Instrument> query = em.createQuery("SELECT l FROM Instrument l", Instrument.class);
        List<Instrument> results = query.getResultList();

        closeEMF();
        return results;
    }
    
    public List<RoomTemplate> fetchAllRoomTemplate (){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))
            return null;

        TypedQuery<RoomTemplate> query = em.createQuery("SELECT l FROM RoomTemplate l", RoomTemplate.class);
        List<RoomTemplate> results = query.getResultList();

        closeEMF();
        return results;
    }


    
    public List<Booking> fetchAllCustomBooking(){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))            return null;

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b WHERE b.room.name = \"Custom\"", Booking.class);
        List<Booking> results = query.getResultList();
        closeEMF();
        return results;
    }
    
    public List<Booking> fetchBookingByRoomID(int id) {
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))            return null;

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b WHERE b.room.id = "+id, Booking.class);
        List<Booking> results = query.getResultList();
        closeEMF();
        return results;
    }

    public User fetchUser(long id){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))            return null;

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =" + String.valueOf(id), User.class);
        User result = query.getSingleResult();

        closeEMF();
        return result;
    }

    public Band fetchBand(String name){
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))            return null;

        TypedQuery<Band> query = em.createQuery("SELECT b FROM Band b WHERE b.name = \"" + name + "\"", Band.class);
        Band result;
        if(!query.getResultList().isEmpty()) {
            result = query.getSingleResult();
        } else {
            result = null;
        }
            

        closeEMF();
        return result;
    }
    public void  updateInstrument(Instrument instrument){
        
        if(!createEMF(ip+"/MusicRoom.odb;user=admin;password=admin"))            
            return ;

        TypedQuery<Instrument> query = em.createQuery("SELECT u FROM Instrument u WHERE u.id =" + String.valueOf(instrument.getId()), Instrument.class);
        Instrument result = query.getSingleResult();

        em.getTransaction().begin();
        result.setName(instrument.getName());
        result.setModel(instrument.getModel());
        result.setPrice(instrument.getPrice());
        result.setImgPath(instrument.getImgPath());
        em.getTransaction().commit();
        closeEMF();
    }
    
        public void updateUser (User user){
        User DbUser = fetchUser(user.getId());
        createEMF("objectdb://"+ip+"/MusicRoom.odb;user=admin;password=admin");
        em.getTransaction().begin();
        
        Query query = em.createQuery(
            "UPDATE User u SET name =\":name\", surname=\":surname\", bandname=\":bandname\", email=\":email\", username=\":username\", password=\":password\", bookedTimes=\":bookedTimes\" " +
            "WHERE u.id < :id");
        query = query.setParameter("name", user.getName());
        query = query.setParameter("surname", user.getSurname());
        query = query.setParameter("bandname", user.getBandName());
        query = query.setParameter("email", user.getEmail());
        query = query.setParameter("username", user.getUsername());
        query = query.setParameter("password", user.getPassword());
        query = query.setParameter("bookedTimes", user.getBookings());
        query.executeUpdate();
        
        em.getTransaction().commit();
        closeEMF();
    }







}