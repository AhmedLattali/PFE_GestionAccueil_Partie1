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
import model.Affaire;
import model.Solution;

/**
 *
 * @author Ahmed
 */
@Stateless
public class AffaireFacade extends AbstractFacade<Affaire> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AffaireFacade() {
        super(Affaire.class);
    }

    public boolean create2(Affaire affaire) {
        int id = 1;
        try {
            Affaire u = getEntityManager().createNamedQuery("Affaire.findMaxId", Affaire.class).getSingleResult();
            id = u.getId() + 1;
            affaire.setId(id);
            getEntityManager().persist(affaire);
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
            affaire.setId(id);
            getEntityManager().persist(affaire);

        }
        return true;
    }

    public boolean edit2(Affaire affaire) {
        getEntityManager().merge(affaire);
        try {
            getEntityManager().flush();
            return true;
        } catch (PersistenceException e) {
            JsfUtil.addErrorMessage("Un attribut dupliqué rompt la contrainte d'unicité");
            JsfUtil.validationFailed();
            return false;
        }

    }

    public boolean remove2(Affaire affaire) {
        // getEntityManager().remove(getEntityManager().merge(affaire));
        //return true ;
        List<Solution> list;
        list = getEntityManager().createNamedQuery("Solution.findByAffaireID", Solution.class).
                setParameter("affaireID", affaire.getId()).getResultList();
        if (!list.isEmpty()) {
            //  System.out.println("koko" + list.get(0).getId());
            JsfUtil.addErrorMessage("Cette affaire  ne peut pas étre suppriméé car elle est toujours réferencée par une solution.");
            return false;
        } else {
            System.out.println("list == null remove ");
            getEntityManager().remove(getEntityManager().merge(affaire));
            return true;
        }
    }

}
