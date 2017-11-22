package MusicRoom;

import MusicRoom.entity.Band;
import MusicRoom.entity.Booking;
import MusicRoom.entity.BrassWoodwind;
import MusicRoom.entity.CustomRoomTemplate;
import MusicRoom.entity.Instrument;
import MusicRoom.entity.RoomTemplate;
import MusicRoom.entity.User;
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
       
        ArrayList<Instrument> list = new ArrayList<Instrument>();
        
        //Trumpet
        list.add(new BrassWoodwind("Brass","Midway","Bb MTR-H3",9800,"MusicRoom/img/Instruments/BrassWoodwind/Trumpet/trumpet1.png"));
        list.add(new BrassWoodwind("Brass","Jinboa","YBTR-450N",14000,"MusicRoom/img/Instruments/BrassWoodwind/Trumpet/trumpet2.png"));
        list.add(new BrassWoodwind("Brass","Yamaha","YTR-2330S",28000,"MusicRoom/img/Instruments/BrassWoodwind/Trumpet/trumpet3.png"));
        
        /*
        //Paino
        list.add(new Piano("Korg","SP-250",33000,"MusicRoom/img/Instruments/Keyboard/Piano/piano1.png"));
        list.add(new Piano("Roland","V-Piano",250000,"MusicRoom/img/Instruments/Keyboard/Piano/piano2.png"));
        list.add(new Piano("Pastel Piano","P-9",19900,"MusicRoom/img/Instruments/Keyboard/Piano/piano3.png"));
        
        //BassDrum
        list.add(new BassDrum("Pearl","Pearl Roadshow",6500,"MusicRoom/img/Instruments/Percussion/Drums/BassDrum/bassdrum1.png"));
        list.add(new BassDrum("Tama","Starclassic",5000,"MusicRoom/img/Instruments/Percussion/Drums/BassDrum/bassdrum1.png"));
        list.add(new BassDrum("Ludwig","Classic Maple",7000,"MusicRoom/img/Instruments/Percussion/Drums/BassDrum/bassdrum1.png"));
        
        //ChinaCymbal
        list.add(new ChinaCymbal("Zildjian","18″ Z3 CHINA",9500,"MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/chinacymbal1.png")); 
        list.add(new ChinaCymbal("Zildjian","17″ K CHINA",10200,"MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/chinacymbal2.png"));
        list.add(new ChinaCymbal("Sabian ","AA 17 Holy China",8000,"MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/chinacymbal3.png"));
        
        //Cowbell
        list.add(new Cowbell("Rock Cowbell","CB-L",360,"MusicRoom/img/Instruments/Percussion/Drums/Cowbell/cowbell1.png")); 
        
        //CrashCymbal
        list.add(new CrashCymbal("Zildjian ","20″ Z3 MEDIUM CRASH",10200,"MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/crashcymbal1.png"));
        list.add(new CrashCymbal("Sabian  ","XSR 14 Fast Crash",4000,"MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/crashcymbal2.png"));
        list.add(new CrashCymbal("Istanbul ","Cymbals Agop 16\" MEDIUM CRASH",10200,"MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/crashcymbal3.png"));
        
        //FloorTom
        list.add(new FloorTom("Pearl","Vision",3500,"MusicRoom/img/Instruments/Percussion/Drums/FloorTom/floortom1.png")); 
        list.add(new FloorTom("Tama","Starclassic",4000,"MusicRoom/img/Instruments/Percussion/Drums/FloorTom/floortom2.png")); 
        list.add(new FloorTom("Ludwig","DW",3900,"MusicRoom/img/Instruments/Percussion/Drums/FloorTom/floortom3.png")); 
        
        //HiHat
        list.add(new HiHat("Zildjian","13″ K CUSTOM DARK HIHATS",14000,"MusicRoom/img/Instruments/Percussion/Drums/HiHat/hihat1.png")); 
        list.add(new HiHat("Zildjian","14″ K CUSTOM DARK HIHATS",15800,"MusicRoom/img/Instruments/Percussion/Drums/HiHat/hihat2.png")); 
        list.add(new HiHat("Istanbul","Cymbals 14\" AGOP HIHAT",13900,"MusicRoom/img/Instruments/Percussion/Drums/HiHat/hihat3.png")); 
        
        //RackTom
        list.add(new RackTom("Pearl","EXL",3400,"MusicRoom/img/Instruments/Percussion/Drums/RackTom/racktom1.png")); 
        list.add(new RackTom("Tama","Swingstar",3000,"MusicRoom/img/Instruments/Percussion/Drums/RackTom/racktom2.png")); 
        list.add(new RackTom("Ludwig","Acent CS",5400,"MusicRoom/img/Instruments/Percussion/Drums/RackTom/racktom3.png")); 
        
        //RideCymbal
        list.add(new RideCymbal("Zildjian ","20″ K CUSTOM HYBRID RIDE",13000,"MusicRoom/img/Instruments/Percussion/Drums/RideCymbal/ridecymbal1.png"));
        list.add(new RideCymbal("Sabian","AAX 22 Omni Ride",12000,"MusicRoom/img/Instruments/Percussion/Drums/RideCymbal/ridecymbal2.png")); 
        list.add(new RideCymbal("Istanbul","Cymbals Origin Dark Ride 22″",14200,"MusicRoom/img/Instruments/Percussion/Drums/RideCymbal/ridecymbal3.png"));
        
        //SnareDrum
        list.add(new SnareDrum("Pearl","J-1355 Joey Jordison",13175,"MusicRoom/img/Instruments/Percussion/Drums/SnareDum/snaredrum1.png")); 
        list.add(new SnareDrum("Ludwig","Centennial Snare Drum Lacquer Finish",5200,"MusicRoom/img/Instruments/Percussion/Drums/SnareDum/snaredrum2.png")); 
        list.add(new SnareDrum("DW","Design",11700,"MusicRoom/img/Instruments/Percussion/Drums/SnareDum/snaredrum3.png")); 
        
        //SplashCymbal
        list.add(new SplashCymbal("Zildjian ","12″ Z3 SPLASH",4700,"MusicRoom/img/Instruments/Percussion/Drums/SplashCymbal/splashcymbal1.png")); 
        list.add(new SplashCymbal("Sabian","AAX 8 Splash",4000,"MusicRoom/img/Instruments/Percussion/Drums/SplashCymbal/splashcymbal2.png")); 
        list.add(new SplashCymbal("Istanbul","Cymbals Agop 12″ Traditional Series Splash Cymbal",5200,"MusicRoom/img/Instruments/Percussion/Drums/SplashCymbal/splashcymbal3.png")); 
        
        //ElectricBass
        list.add(new ElectricBass("Squier","Affinity Jazz Bass V",8550,"MusicRoom/img/Instruments/Strings/BassGuitar/ElectricBass/ebass1.png"));
        list.add(new ElectricBass("Fender","American Elite Jazz Bass",71100,"MusicRoom/img/Instruments/Strings/BassGuitar/ElectricBass/ebass2.png"));
        list.add(new ElectricBass("Peavey","Peavey Millennium® Series Bass 5 String",9900,"MusicRoom/img/Instruments/Strings/BassGuitar/ElectricBass/ebass3.png"));
        
        //AcousticGuitar
        list.add(new AcousticGuitar("Takamine","CP3DC",47500,"MusicRoom/img/Instruments/Strings/Guitar/AcousticGuitar/aguitar1.png"));
        list.add(new AcousticGuitar("Taylor","310CE",67900,"MusicRoom/img/Instruments/Strings/Guitar/AcousticGuitar/aguitar2.png"));
        list.add(new AcousticGuitar("Guild","M-240E",15750,"MusicRoom/img/Instruments/Strings/Guitar/AcousticGuitar/aguitar3.png"));
        
        //ElectricGuitar
        list.add(new ElectricGuitar("Fender","Standard Stratocaster RW FR",26100,"MusicRoom/img/Instruments/Strings/Guitar/ElecticGuitar/eguitar1.png"));
        list.add(new ElectricGuitar("Squier","Classic Vibe Tele 50 S",155000,"MusicRoom/img/Instruments/Strings/Guitar/ElecticGuitar/eguitar2.png"));
        list.add(new ElectricGuitar("Gibson","Les Paul Custom Alphine White",153000,"MusicRoom/img/Instruments/Strings/Guitar/ElecticGuitar/eguitar3.png"));
        
        // Violin
        list.add(new Violin("Andrea Schudtz","2800",50000,"MusicRoom/img/Instruments/Strings/Violin/vilon1.png"));
        list.add(new Violin("Dario II Vettori","2006",600000,"MusicRoom/img/Instruments/Strings/Violin/vilon2.png"));
        list.add(new Violin("Robert Lee","804",45000,"MusicRoom/img/Instruments/Violin/Strings/vilon3.png"));

        //Microphone
        list.add(new Microphone("Shure","PG58-LCV",2520,"MusicRoom/img/Instruments/Voice/mic1.png"));
        list.add(new Microphone("Rode","M1-S",5300,"MusicRoom/img/Instruments/Voice/mic2.png"));
        list.add(new Microphone("EV","7000",2520,"MusicRoom/img/Instruments/Voice/mic3.png"));
        
        //AltoSaxophone
        list.add(new AltoSaxophone("Selmer","Super Action 80 Series II E-flat",198500,"MusicRoom/img/Instruments/Woodwind/Saxophone/AltoSaxophone/altosax1.png"));
        list.add(new AltoSaxophone("Buffet","100 Series Gold Lacquer",48000,"MusicRoom/img/Instruments/Woodwind/Saxophone/AltoSaxophone/altosax2.png"));
        list.add(new AltoSaxophone("Yanagisawa","A9937",550000,"MusicRoom/img/Instruments/Woodwind/Saxophone/AltoSaxophone/altosax3.png"));
        
        //TenorSaxophone
        list.add(new TenorSaxophone("Selmer","Super Action 80 Series II B-flat",217000,"MusicRoom/img/Instruments/Woodwind/Saxophone/TenorSaxophone/tenorsax1.png"));
        list.add(new TenorSaxophone("Yanagisawa","T901S",165000,"MusicRoom/img/Instruments/Woodwind/Saxophone/TenorSaxophone/tenorsax1.png"));
        list.add(new TenorSaxophone("Julius Keilwerth","ST90",45000,"MusicRoom/img/Instruments/Woodwind/Saxophone/TenorSaxophone/tenorsax1.png"));
        */
        
        for(Instrument i: list){
            em.persist(i);
        }
        
        em.getTransaction().commit();
        closeEMF();
        return true;
    }
    
    public boolean createDummyRoomTemplate (){
/*
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
        
        
        
        em.persist(m);

        em.getTransaction().commit();
       
        closeEMF();*/
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