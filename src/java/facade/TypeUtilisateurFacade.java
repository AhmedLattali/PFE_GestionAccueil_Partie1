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
import model.Fonction;
import model.TypeUtilisateur;

/**
 *
 * @author Ahmed
 */
@Stateless
public class TypeUtilisateurFacade extends AbstractFacade<TypeUtilisateur> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeUtilisateurFacade() {
        super(TypeUtilisateur.class);
    }

  
    public boolean remove2(TypeUtilisateur t) {
        getEntityManager().remove(getEntityManager().merge(t));
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
            JsfUtil.addErrorMessage("Ce type utilisateur ne peut pas étre supprimé car il est toujours réferencé par un utilisateur.");
            return false ;

        }
        

    }

}
