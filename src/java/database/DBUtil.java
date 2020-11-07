
package database;
import javax.persistence.*;

public class DBUtil {
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Week09LabPU");
    
    public static EntityManagerFactory getEmFactory(){
        return emf;
    }
}
