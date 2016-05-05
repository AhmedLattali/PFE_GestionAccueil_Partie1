/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import controller.util.JsfUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import model.Solution;

/**
 *
 * @author Ahmed
 */
@Stateless
public class SolutionFacade extends AbstractFacade<Solution> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SolutionFacade() {
        super(Solution.class);
    }

    public boolean create2(Solution solution) {
              int id = 1;
        try {
            Solution q = getEntityManager().createNamedQuery("Solution.findMaxId", Solution.class).getSingleResult();
            id = q.getId() + 1;
            solution.setId(id);
            getEntityManager().persist(solution);
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
            solution.setId(id);
            getEntityManager().persist(solution);

        }
        return true;
    }

    public boolean remove2(Solution solution) {
           getEntityManager().remove(getEntityManager().merge(solution));
         return true;
    }
    
}
