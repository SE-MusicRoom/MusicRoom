package MusicRoom;

import MusicRoom.entity.Instrument;
import MusicRoom.entity.User;

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

    public List<Instrument> fetchAllInstrument (String path){
        createEMF(path + "instruments.odb");

        TypedQuery<Instrument> query = em.createQuery("SELECT l FROM Instrument l", Instrument.class);
        List<Instrument> results = query.getResultList();

        closeEMF();
        return results;
    }

    public void addUser(User user, String path){
        createEMF(path + "user.odb");

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        closeEMF();
    }

    public List<User> fetchAllUser(String path, int id){
        createEMF(path + "user.odb");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u ", User.class);
        List<User> results = query.getResultList();

        closeEMF();
        return results;
    }

    public User fetchUser(String path, int id){
        createEMF(path + "user.odb");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id =" + String.valueOf(id), User.class);
        User result = query.getSingleResult();

        closeEMF();
        return result;
    }




}