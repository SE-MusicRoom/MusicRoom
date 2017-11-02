package MusicRoom;

import MusicRoom.entity.Instrument;

import javax.persistence.*;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseManager(){};

    public static DatabaseManager getInstance() {
        if(instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    public List<Instrument> fetchAllInstrument (String path){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(path + "instruments.odb");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Instrument> query = em.createQuery("SELECT l FROM Instrument l", Instrument.class);
        List<Instrument> results = query.getResultList();
        em.close();
        emf.close();
        return results;
    }

    

}