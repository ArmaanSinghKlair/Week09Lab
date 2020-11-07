package database;

import models.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
public class UserDB {

    public int insert(Users user) throws NotesDBException {
        
          EntityManagerFactory emf = DBUtil.getEmFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
            return 1;
          
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot create " + user.toString(), ex);
            throw new NotesDBException("Error inserting user");
        } finally {
           em.close();
        }
        
    }

    public int update(Users user) throws NotesDBException {
        EntityManagerFactory emf = DBUtil.getEmFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
           trans.begin();
           em.merge(user);
           trans.commit();
           return 1;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot update " + user.toString(), ex);
            throw new NotesDBException("Error updating user");
        } finally {
           em.close();
        }
    }

    public List<Users> getAll() throws NotesDBException {
        EntityManagerFactory emf = DBUtil.getEmFactory();
        EntityManager em = emf.createEntityManager();
        
        try {
           TypedQuery<Users> q =  em.createNamedQuery("Users.findAll",Users.class);
           return q.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot read users", ex);
            throw new NotesDBException("Error getting Users"+ex.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Get a single user by their username.
     *
     * @param username The unique username.
     * @return A User object if found, null otherwise.
     * @throws NotesDBException
     */
    public Users getUser(String username) throws NotesDBException {
        EntityManagerFactory emf = DBUtil.getEmFactory();
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Users.class, username);
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot read users", ex);
            throw new NotesDBException("Error getting Users");
        } finally {
           em.close();
        }
    }

    public int delete(Users user) throws NotesDBException {
        EntityManagerFactory emf = DBUtil.getEmFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
           return 1;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, "Cannot delete " + user.toString(), ex);
            throw new NotesDBException("Error deleting User");
        } finally {
            em.close();
        }
    }
}
