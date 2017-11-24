package MusicRoom;

import MusicRoom.entity.Band;
import MusicRoom.entity.Booking;
import MusicRoom.entity.BrassWoodwind;
import MusicRoom.entity.CustomRoomTemplate;
import MusicRoom.entity.Instrument;
import MusicRoom.entity.Percussion;
import MusicRoom.entity.RoomTemplate;
import MusicRoom.entity.Strings;
import MusicRoom.entity.User;
import MusicRoom.entity.Voice;
import java.util.ArrayList;

import javax.persistence.*;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;
    private EntityManagerFactory emf;
    private EntityManager em;

    private final String ip;

    private DatabaseManager() {
        ip = "objectdb://161.246.6.16:80";
    }

    ;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private boolean createEMF(String DbName) {
        try {
            this.emf = Persistence.createEntityManagerFactory(DbName);
            this.em = this.emf.createEntityManager();
        } catch (Exception ex) {
            System.out.println("Error creating EMF");
            //Main.getInstance().showErrorPopup("Error Connecting db", "\nClick confirm to try again");
            return false;
        }

        return true;

    }

    private void closeEMF() {
        this.em.close();
        this.emf.close();
    }

    public void createDummyAll() {
        createInstrumentDB();
        createDummyRoomTemplate();
        createDummyUser();
    }

    public boolean createDummyUser() {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        
        User l = new User("admin","1234","ADMIN","MUSICROOM","kmitlmusicroom@gmail.com",fetchBand("MasterBand"),true);
        User m = new User("1234", "12345678", "Tester", "man", "p.i.t.a.wat@hotmail.com", fetchBand("MasterBand"),true);
        l.setActivated(true);
        em.getTransaction().begin();
        em.persist(l);
        em.persist(m);

        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean createInstrumentDB() {

        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();

        ArrayList<Instrument> list = new ArrayList<Instrument>();

        //Trumpet
        list.add(new BrassWoodwind("Brass", "Trumpet", "Midway", "Bb MTR-H3", 9800, "MusicRoom/img/Instruments/BrassWoodwind/Trumpet/trumpet1.png"));
        list.add(new BrassWoodwind("Brass", "Trumpet", "Jinboa", "YBTR-450N", 14000, "MusicRoom/img/Instruments/BrassWoodwind/Trumpet/trumpet2.png"));
        list.add(new BrassWoodwind("Brass", "Trumpet", "Yamaha", "YTR-2330S", 28000, "MusicRoom/img/Instruments/BrassWoodwind/Trumpet/trumpet3.png"));

        //Piano
        list.add(new Percussion("Piano", "Korg", "SP-250", 33000, "MusicRoom/img/Instruments/Percussion/Keyboard/Piano/piano1.png"));
        list.add(new Percussion("Piano", "Roland", "V-Piano", 250000, "MusicRoom/img/Instruments/Percussion/Keyboard/Piano/piano2.png"));
        list.add(new Percussion("Piano", "Pastel Piano", "P-9", 19900, "MusicRoom/img/Instruments/Percussion/Keyboard/Piano/piano3.png"));

        //BassDrum
        list.add(new Percussion("BassDrum", "Pearl", "Pearl Roadshow", 6500, "MusicRoom/img/Instruments/Percussion/Drums/BassDrum/bassdrum1.png"));
        list.add(new Percussion("BassDrum", "Tama", "Starclassic", 5000, "MusicRoom/img/Instruments/Percussion/Drums/BassDrum/bassdrum2.png"));
        list.add(new Percussion("BassDrum", "Ludwig", "Classic Maple", 7000, "MusicRoom/img/Instruments/Percussion/Drums/BassDrum/bassdrum3.png"));

        //ChinaCymbal
        list.add(new Percussion("ChinaCymbal", "Zildjian", "18″ Z3 China", 9500, "MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/chinacymbal1.png"));
        list.add(new Percussion("ChinaCymbal", "Zildjian", "17″ K China", 10200, "MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/chinacymbal2.png"));
        list.add(new Percussion("ChinaCymbal", "Sabian ", "AA 17 Holy China", 8000, "MusicRoom/img/Instruments/Percussion/Drums/ChinaCymbal/chinacymbal3.png"));

        //Cowbell
        list.add(new Percussion("Cowbell", "Rock Cowbell", "CB-L", 360, "MusicRoom/img/Instruments/Percussion/Drums/Cowbell/cowbell1.png"));

        //CrashCymbal
        list.add(new Percussion("CrashCymbal", "Zildjian", "20″ Z3 Medium Crash", 10200, "MusicRoom/img/Instruments/Percussion/Drums/CrashCymbal/crashcymbal1.png"));
        list.add(new Percussion("CrashCymbal", "Sabian", "XSR 14 Fast Crash", 4000, "MusicRoom/img/Instruments/Percussion/Drums/CrashCymbal/crashcymball2.png"));
        list.add(new Percussion("CrashCymbal", "Istanbul", "Agop 16″ Medium Crash", 10200, "MusicRoom/img/Instruments/Percussion/Drums/CrashCymbal/crashcymbal3.png"));

        //FloorTom
        list.add(new Percussion("FloorTom", "Pearl", "Vision", 3500, "MusicRoom/img/Instruments/Percussion/Drums/FloorTom/floortom1.png"));
        list.add(new Percussion("FloorTom", "Tama", "Starclassic", 4000, "MusicRoom/img/Instruments/Percussion/Drums/FloorTom/floortom2.png"));
        list.add(new Percussion("FloorTom", "Ludwig", "DW", 3900, "MusicRoom/img/Instruments/Percussion/Drums/FloorTom/floortom3.png"));

        //HiHat
        list.add(new Percussion("HiHat", "Zildjian", "13″ K Custom Dark", 14000, "MusicRoom/img/Instruments/Percussion/Drums/HiHat/hihat1.png"));
        list.add(new Percussion("HiHat", "Zildjian", "14″ K Custom Dark", 15800, "MusicRoom/img/Instruments/Percussion/Drums/HiHat/hihat2.png"));
        list.add(new Percussion("HiHat", "Istanbul", "14″ AGOP", 13900, "MusicRoom/img/Instruments/Percussion/Drums/HiHat/hihat3.png"));

        //RackTom
        list.add(new Percussion("RackTom", "Pearl", "EXL", 3400, "MusicRoom/img/Instruments/Percussion/Drums/RackTom/racktom1.png"));
        list.add(new Percussion("RackTom", "Tama", "Swingstar", 3000, "MusicRoom/img/Instruments/Percussion/Drums/RackTom/racktom2.png"));
        list.add(new Percussion("RackTom", "Ludwig", "Acent CS", 5400, "MusicRoom/img/Instruments/Percussion/Drums/RackTom/racktom3.png"));

        //RideCymbal
        list.add(new Percussion("RideCymbal", "Zildjian", "20″ K Custom Hybrid Ride", 13000, "MusicRoom/img/Instruments/Percussion/Drums/RideCymbal/ridecymbal1.png"));
        list.add(new Percussion("RideCymbal", "Sabian", "AAX 22 Omni Ride", 12000, "MusicRoom/img/Instruments/Percussion/Drums/RideCymbal/ridecymbal2.png"));
        list.add(new Percussion("RideCymbal", "Istanbul", "Origin Dark Ride 22″", 14200, "MusicRoom/img/Instruments/Percussion/Drums/RideCymbal/ridecymbal3.png"));

        //SnareDrum
        list.add(new Percussion("SnareDrum", "Pearl", "J-1355 Joey Jordison", 13175, "MusicRoom/img/Instruments/Percussion/Drums/SnareDrum/snaredrum1.png"));
        list.add(new Percussion("SnareDrum", "Ludwig", "Centennial Lacquer Finish", 5200, "MusicRoom/img/Instruments/Percussion/Drums/SnareDrum/snaredrum2.png"));
        list.add(new Percussion("SnareDrum", "DW", "Design", 11700, "MusicRoom/img/Instruments/Percussion/Drums/SnareDrum/snaredrum3.png"));

        //SplashCymbal
        list.add(new Percussion("SplashCymbal", "Zildjian ", "12″ Z3 Splash", 4700, "MusicRoom/img/Instruments/Percussion/Drums/SplashCymbal/splashcymbal1.png"));
        list.add(new Percussion("SplashCymbal", "Sabian", "AAX 8 Splash", 4000, "MusicRoom/img/Instruments/Percussion/Drums/SplashCymbal/splashcymbal2.png"));
        list.add(new Percussion("SplashCymbal", "Istanbul", "Agop 12″ Traditional Series", 5200, "MusicRoom/img/Instruments/Percussion/Drums/SplashCymbal/splashcymbal3.png"));

        //ElectricBass
        list.add(new Strings("ElectricBass", "5", "Squier", "Affinity Jazz Bass V", 8550, "MusicRoom/img/Instruments/Strings/BassGuitar/ElectricBass/ebass1.png"));
        list.add(new Strings("ElectricBass", "5", "Fender", "American Elite Jazz Bass", 71100, "MusicRoom/img/Instruments/Strings/BassGuitar/ElectricBass/ebass2.png"));
        list.add(new Strings("ElectricBass", "5", "Peavey", "Peavey Millennium® Series", 9900, "MusicRoom/img/Instruments/Strings/BassGuitar/ElectricBass/ebass3.png"));

        //AcousticGuitar
        list.add(new Strings("AcousticGuitar", "6", "Takamine", "CP3DC", 47500, "MusicRoom/img/Instruments/Strings/Guitar/AcousticGuitar/aguitar1.png"));
        list.add(new Strings("AcousticGuitar", "6", "Taylor", "310CE", 67900, "MusicRoom/img/Instruments/Strings/Guitar/AcousticGuitar/aguitar2.png"));
        list.add(new Strings("AcousticGuitar", "6", "Guild", "M-240E", 15750, "MusicRoom/img/Instruments/Strings/Guitar/AcousticGuitar/aguitar3.png"));

        //ElectricGuitar
        list.add(new Strings("ElectricGuitar", "6", "Fender", "Standard Stratocaster RW FR", 26100, "MusicRoom/img/Instruments/Strings/Guitar/ElectricGuitar/eguitar1.png"));
        list.add(new Strings("ElectricGuitar", "6", "Squier", "Classic Vibe Tele 50 S", 155000, "MusicRoom/img/Instruments/Strings/Guitar/ElectricGuitar/eguitar2.png"));
        list.add(new Strings("ElectricGuitar", "6", "Gibson", "Les Paul Custom Alphine White", 153000, "MusicRoom/img/Instruments/Strings/Guitar/ElectricGuitar/eguitar3.png"));

        //Violin
        list.add(new Strings("Violin", "4", "Andrea Schudtz", "2800", 50000, "MusicRoom/img/Instruments/Strings/Violin/violin1.png"));
        list.add(new Strings("Violin", "4", "Dario II Vettori", "2006", 600000, "MusicRoom/img/Instruments/Strings/Violin/violin2.png"));
        list.add(new Strings("Violin", "4", "Robert Lee", "804", 45000, "MusicRoom/img/Instruments/Strings/Violin/violin3.png"));

        //Microphone
        list.add(new Voice("Dynamic", "Shure", "PG58-LCV", 2520, "MusicRoom/img/Instruments/Voice/Microphone/mic1.png"));
        list.add(new Voice("Condenser", "Rode", "M1-S", 5300, "MusicRoom/img/Instruments/Voice/Microphone/mic2.png"));
        list.add(new Voice("Dynamic", "EV", "7000", 2520, "MusicRoom/img/Instruments/Voice/Microphone/mic3.png"));

        //AltoSaxophone
        list.add(new BrassWoodwind("Woodwind", "AltoSaxophone", "Selmer", "80 Series II E-flat", 198500, "MusicRoom/img/Instruments/BrassWoodwind/Woodwind/Saxophone/AltoSaxophone/altosax1.png"));
        list.add(new BrassWoodwind("Woodwind", "AltoSaxophone", "Buffet", "100 Series Gold Lacquer", 48000, "MusicRoom/img/Instruments/BrassWoodwind/Woodwind/Saxophone/AltoSaxophone/altosax2.png"));
        list.add(new BrassWoodwind("Woodwind", "AltoSaxophone", "Yanagisawa", "A9937", 550000, "MusicRoom/img/Instruments/BrassWoodwind/Woodwind/Saxophone/AltoSaxophone/altosax3.png"));

        //TenorSaxophone
        list.add(new BrassWoodwind("Woodwind", "TenorSaxophone", "Selmer", "80 Series II B-flat", 217000, "MusicRoom/img/Instruments/BrassWoodwind/Woodwind/Saxophone/TenorSaxophone/tenorsax1.png"));
        list.add(new BrassWoodwind("Woodwind", "TenorSaxophone", "Yanagisawa", "T901S", 165000, "MusicRoom/img/Instruments/BrassWoodwind/Woodwind/Saxophone/TenorSaxophone/tenorsax2.png"));
        list.add(new BrassWoodwind("Woodwind", "TenorSaxophone", "Julius Keilwerth", "ST90", 45000, "MusicRoom/img/Instruments/BrassWoodwind/Woodwind/Saxophone/TenorSaxophone/tenorsax3.png"));

        list.forEach((i) -> {
            em.persist(i);
        });

        em.getTransaction().commit();
        closeEMF();
        return true;
    }

    public boolean createDummyRoomTemplate() {

        RoomTemplate reggae = new RoomTemplate("Reggae", "For the love of Bob", 6000);

        //Drums
        reggae.addInstrument(Main.getInstance().getInstrument("Korg", "SP-250"));
        reggae.addInstrument(Main.getInstance().getInstrument("Ludwig", "Classic Maple"));
        //reggae.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        reggae.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        reggae.addInstrument(Main.getInstance().getInstrument("Rock Cowbell", "CB-L"));
        reggae.addInstrument(Main.getInstance().getInstrument("Ludwig", "DW"));
        reggae.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        reggae.addInstrument(Main.getInstance().getInstrument("Ludwig", "Acent CS"));
        reggae.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        reggae.addInstrument(Main.getInstance().getInstrument("Ludwig", "Centennial Lacquer Finish"));
        reggae.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        reggae.addInstrument(Main.getInstance().getInstrument("Fender", "Standard Stratocaster RW FR"));
        reggae.addInstrument(Main.getInstance().getInstrument("Gibson", "Les Paul Custom Alphine White"));

        //BassGuitar
        reggae.addInstrument(Main.getInstance().getInstrument("Fender", "American Elite Jazz Bass"));

        //Voice
        reggae.addInstrument(Main.getInstance().getInstrument("EV", "7000"));

        
        RoomTemplate rock = new RoomTemplate("Rock", "For Rock band and General", 5000);

        //Drums
        rock.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        rock.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        rock.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        rock.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        rock.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        rock.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        rock.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        rock.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        rock.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        rock.addInstrument(Main.getInstance().getInstrument("Fender", "Standard Stratocaster RW FR"));
        rock.addInstrument(Main.getInstance().getInstrument("Gibson", "Les Paul Custom Alphine White"));

        //BassGuitar
        rock.addInstrument(Main.getInstance().getInstrument("Fender", "American Elite Jazz Bass"));

        //Voice
        rock.addInstrument(Main.getInstance().getInstrument("EV", "7000"));
        
        RoomTemplate stage = new RoomTemplate("Stage", "Stage", 4000);

        //Drums
        stage.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        stage.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        stage.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        stage.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        stage.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        stage.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        stage.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        stage.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        stage.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        stage.addInstrument(Main.getInstance().getInstrument("Squier", "Classic Vibe Tele 50 S"));
        stage.addInstrument(Main.getInstance().getInstrument("Gibson", "Les Paul Custom Alphine White"));

        //BassGuitar
        stage.addInstrument(Main.getInstance().getInstrument("Squier", "Affinity Jazz Bass V"));

        //Voice
        stage.addInstrument(Main.getInstance().getInstrument("EV", "7000"));
        
        
        RoomTemplate acoustic = new RoomTemplate("Acoustic", "Acoustic", 3500);

        //Drums
        acoustic.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        acoustic.addInstrument(Main.getInstance().getInstrument("Takamine", "CP3DC"));
        acoustic.addInstrument(Main.getInstance().getInstrument("Taylor", "310CE"));

        //BassGuitar
        acoustic.addInstrument(Main.getInstance().getInstrument("Fender", "American Elite Jazz Bass"));

        //Voice
        acoustic.addInstrument(Main.getInstance().getInstrument("Shure", "PG58-LCV"));
        
        RoomTemplate indie = new RoomTemplate("Indie", "Indie", 4700);

        //Drums
        indie.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        indie.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        indie.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        indie.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        indie.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        indie.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        indie.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        indie.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        indie.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        indie.addInstrument(Main.getInstance().getInstrument("Squier", "Classic Vibe Tele 50 S"));
        indie.addInstrument(Main.getInstance().getInstrument("Squier", "Classic Vibe Tele 50 S"));

        //BassGuitar
        indie.addInstrument(Main.getInstance().getInstrument("Squier", "Affinity Jazz Bass V"));

        //Voice
        indie.addInstrument(Main.getInstance().getInstrument("EV", "7000"));
        
        RoomTemplate jazz = new RoomTemplate("Jazz", "Jazz", 8000);

        //Drums
        jazz.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        jazz.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        jazz.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        jazz.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        jazz.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        jazz.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        jazz.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        jazz.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        jazz.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        jazz.addInstrument(Main.getInstance().getInstrument("Fender", "Standard Stratocaster RW FR"));
        jazz.addInstrument(Main.getInstance().getInstrument("Gibson", "Les Paul Custom Alphine White"));

        //BassGuitar
        jazz.addInstrument(Main.getInstance().getInstrument("Fender", "American Elite Jazz Bass"));
        
        //AltoSaxophone
        jazz.addInstrument(Main.getInstance().getInstrument("Selmer", "80 Series II E-flat"));
        
        //Piano
        jazz.addInstrument(Main.getInstance().getInstrument("Korg", "SP-250"));

        //Voice
        jazz.addInstrument(Main.getInstance().getInstrument("EV", "7000"));
        
        RoomTemplate pop = new RoomTemplate("Pop", "Pop", 5500);

        //Drums
        pop.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        pop.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        pop.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        pop.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        pop.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        pop.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        pop.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        pop.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        pop.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        pop.addInstrument(Main.getInstance().getInstrument("Fender", "Standard Stratocaster RW FR"));
        pop.addInstrument(Main.getInstance().getInstrument("Gibson", "Les Paul Custom Alphine White"));

        //BassGuitar
        pop.addInstrument(Main.getInstance().getInstrument("Fender", "American Elite Jazz Bass"));
        
        //Piano
        pop.addInstrument(Main.getInstance().getInstrument("Roland", "V-Piano"));
        
        //Voice
        pop.addInstrument(Main.getInstance().getInstrument("EV", "7000"));
        
        RoomTemplate rnb = new RoomTemplate("R&B", "R&B", 5800);

        //Drums
        rnb.addInstrument(Main.getInstance().getInstrument("Pearl", "Pearl Roadshow"));
        rnb.addInstrument(Main.getInstance().getInstrument("Zildjian", "18″ Z3 China"));
        rnb.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ Z3 Medium Crash"));
        rnb.addInstrument(Main.getInstance().getInstrument("Pearl", "Vision"));
        rnb.addInstrument(Main.getInstance().getInstrument("Zildjian", "13″ K Custom Dark"));
        rnb.addInstrument(Main.getInstance().getInstrument("Pearl", "EXL"));
        rnb.addInstrument(Main.getInstance().getInstrument("Zildjian", "20″ K Custom Hybrid Ride"));
        rnb.addInstrument(Main.getInstance().getInstrument("Pearl", "J-1355 Joey Jordison"));
        rnb.addInstrument(Main.getInstance().getInstrument("Zildjian ", "12″ Z3 Splash"));

        //Guitar
        rnb.addInstrument(Main.getInstance().getInstrument("Fender", "Standard Stratocaster RW FR"));
        rnb.addInstrument(Main.getInstance().getInstrument("Gibson", "Les Paul Custom Alphine White"));

        //BassGuitar
        rnb.addInstrument(Main.getInstance().getInstrument("Fender", "American Elite Jazz Bass"));
        
        //AltoSaxophone
        rnb.addInstrument(Main.getInstance().getInstrument("Selmer", "80 Series II B-flat"));
        
        //Voice
        rnb.addInstrument(Main.getInstance().getInstrument("EV", "7000"));
        
        
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.persist(reggae);
        em.persist(rock);
        em.persist(stage);
        em.persist(acoustic);
        em.persist(indie);
        em.persist(jazz);
        em.persist(pop);
        em.persist(rnb);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean addUser(User user) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean addBand(Band band) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.persist(band);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean addRoom(RoomTemplate roomTemplate) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.persist(roomTemplate);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean addCustomRoom(CustomRoomTemplate roomTemplate) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.persist(roomTemplate);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean addBooking(Booking booking) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.persist(booking);
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public boolean removeBooking(Booking booking) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        em.getTransaction().begin();
        em.remove(em.contains(booking) ? booking : em.merge(booking));
        em.getTransaction().commit();

        closeEMF();
        return true;
    }

    public List<User> fetchAllUser() {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u ", User.class);
        List<User> results = query.getResultList();
        
        if(results.isEmpty()) 
            createDummyUser();
        
        closeEMF();
        return results;
    }

    public List<Booking> fetchAllBooking() {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b ", Booking.class);
        List<Booking> results = query.getResultList();

        closeEMF();
        return results;
    }

    public List<Instrument> fetchAllInstrument() {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<Instrument> query = em.createQuery("SELECT l FROM Instrument l", Instrument.class);
        List<Instrument> results = query.getResultList();

        closeEMF();
        return results;
    }

    public List<RoomTemplate> fetchAllRoomTemplate() {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<RoomTemplate> query = em.createQuery("SELECT l FROM RoomTemplate l", RoomTemplate.class);
        List<RoomTemplate> results = query.getResultList();

        closeEMF();
        return results;
    }

    public List<Booking> fetchAllCustomBooking() {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b WHERE b.room.name = \"Custom\"", Booking.class);
        List<Booking> results = query.getResultList();
        closeEMF();
        return results;
    }

    public List<Booking> fetchBookingByRoomID(int id) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b WHERE b.room.id = " + id, Booking.class);
        List<Booking> results = query.getResultList();
        closeEMF();
        return results;
    }

    public User fetchUser(long id) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =" + String.valueOf(id), User.class);
        User result = query.getSingleResult();

        closeEMF();
        return result;
    }

    public boolean isExistUser(String username) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return false;
        }

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username =\"" + username + "\"", User.class);
        int count = query.getResultList().size();

        closeEMF();
        return count > 0;
    }

    public Band fetchBand(String name) {
        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return null;
        }

        TypedQuery<Band> query = em.createQuery("SELECT b FROM Band b WHERE b.name = \"" + name + "\"", Band.class);
        Band result;
        if (!query.getResultList().isEmpty()) {
            result = query.getSingleResult();
        } else {
            result = null;
        }

        closeEMF();
        return result;
    }

    public void updateInstrument(Instrument instrument) {

        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return;
        }

        TypedQuery<Instrument> query = em.createQuery("SELECT u FROM Instrument u WHERE u.id =" + String.valueOf(instrument.getId()), Instrument.class);
        Instrument result = query.getSingleResult();

        em.getTransaction().begin();
        result.setName(instrument.getName());
        result.setModel(instrument.getModel());
        result.setPrice(instrument.getPrice());
        result.setImgPath(instrument.getImgPath());

        if (instrument instanceof Strings) {
            ((Strings) result).setType(((Strings) instrument).getType());
            ((Strings) result).setNumber_of_strings(((Strings) instrument).getNumber_of_strings());
        }

        if (instrument instanceof BrassWoodwind) {
            ((BrassWoodwind) result).setType(((BrassWoodwind) instrument).getType());
            ((BrassWoodwind) result).setSubtype(((BrassWoodwind) instrument).getSubtype());
        }

        if (instrument instanceof Percussion) {
            ((Percussion) result).setPartName(((Percussion) instrument).getPartName());
        }

        if (instrument instanceof Voice) {
            ((Voice) result).setFrequencyResponse(((Voice) instrument).getFrequencyResponse());
        }

        em.getTransaction().commit();
        closeEMF();
    }

    public void updateUser(User user) {
        User DbUser = fetchUser(user.getId());
        createEMF("objectdb://" + ip + "/MusicRoom.odb;user=admin;password=admin");
        em.getTransaction().begin();

        Query query = em.createQuery(
                "UPDATE User u SET name =\":name\", surname=\":surname\", bandname=\":bandname\", email=\":email\", username=\":username\", password=\":password\", bookedTimes=\":bookedTimes\" "
                + "WHERE u.id < :id");
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
    
    public void updateActivateUser(User user) {

        if (!createEMF(ip + "/MusicRoom.odb;user=admin;password=admin")) {
            return;
        }

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =" + String.valueOf(user.getId()), User.class);
        User result = query.getSingleResult();

        em.getTransaction().begin();
        result.setActivated(true);
        em.getTransaction().commit();
        closeEMF();
    }

}
