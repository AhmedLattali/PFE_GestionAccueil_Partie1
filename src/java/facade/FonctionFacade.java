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
import model.Fonction;

/**
 *
 * @author Ahmed
 */
@Stateless
public class FonctionFacade extends AbstractFacade<Fonction> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FonctionFacade() {
        super(Fonction.class);
    }

   
  
    public boolean remove2(Fonction f) {
        getEntityManager().remove(getEntityManager().merge(f));
        try {
            getEntityManager().flush();
            return true ;
        } catch (PersistenceException e) {
            /*  Throwable cause = e.getCause();
            String msg = cause.getLocalizedMessage();
            String a = (msg.split("Détail :"))[1];
            String detail = a.split("Error")[0];

            System.out.println("catchtiha f remove" + a);
            System.out.println("catchtiha f remove" + detail);
            JsfUtil.addErrorMessage(detail);*/
            JsfUtil.addErrorMessage("Cette fonction ne peut pas étre supprimée car elle est toujours réferencée par un utilisateur.");
            return false ;

        }
    }

}
