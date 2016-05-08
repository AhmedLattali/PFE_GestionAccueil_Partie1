/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import controller.util.JsfUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import model.Reponse;

/**
 *
 * @author Ahmed
 */
@Stateless
public class ReponseFacade extends AbstractFacade<Reponse> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReponseFacade() {
        super(Reponse.class);
    }
    

    public boolean create2(Reponse reponse) {
        int id = 1;
        try {
            Reponse q = getEntityManager().createNamedQuery("Reponse.findMaxId", Reponse.class).getSingleResult();
            id = q.getId() + 1;
            reponse.setId(id);
            getEntityManager().persist(reponse);
            try {
                getEntityManager().flush();
                return true;
            } catch (PersistenceException e) {
                JsfUtil.addErrorMessage("Un attribut dupliqué rompt la contrainte d'unicité");
                JsfUtil.validationFailed();
                return false;
            }

        } catch (Exception e) {
        } finally {
            reponse.setId(id);
            getEntityManager().persist(reponse);

        }
        return true;
    }

    public boolean remove2(Reponse reponse) {
        getEntityManager().remove(getEntityManager().merge(reponse));
        return true;
    }
    
        public Reponse getReponseByID(int reponse_id){
                return getEntityManager().createNamedQuery("Reponse.findById", Reponse.class).setParameter("id", reponse_id).getSingleResult();

        
    }

}
