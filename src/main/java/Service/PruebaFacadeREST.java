package Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author wtke9
 */
public class PruebaFacadeREST {

    @PersistenceContext(unitName = "com.mycompany_Sis_Gym_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

}
