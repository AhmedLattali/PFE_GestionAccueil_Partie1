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
import model.TypeUtilisateur;
import model.Utilisateur;

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

    public boolean create2(TypeUtilisateur t) {
        try {
            getEntityManager().persist(t);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ce type utilisateur existe déja");
            JsfUtil.validationFailed();
            return false;
        }
    }

    public boolean remove2(TypeUtilisateur t) {
        List<Utilisateur> list;
        list = getEntityManager().createNamedQuery("Utilisateur.findByTypeUtilisateur", Utilisateur.class).
                setParameter("type_utilisateur", t.getLibeleTypeUtilisateur()).getResultList();
        if (!list.isEmpty()) {
            if ((list.get(0).getTypeUtilisateur().getLibeleTypeUtilisateur()).equals("Administrateur")) {
                JsfUtil.addErrorMessage("le type utlisateur Administrateur est protégé et ne peut étre supprimé");
                return false;
            }
            //  System.out.println("koko" + list.get(0).getId());
            JsfUtil.addErrorMessage("Ce type utilisateur ne peut pas étre supprimé car il est toujours réferencé par un utilisateur.");
            return false;
        } else {
            System.out.println("list == null remove ");
            getEntityManager().remove(getEntityManager().merge(t));
            return true;
        }

        /* getEntityManager().remove(getEntityManager().merge(t));
        
        try {
            getEntityManager().flush();
            return true ;
        } catch (PersistenceException e) {
             /* Throwable cause = e.getCause();
            String msg = cause.getLocalizedMessage();
            String a = (msg.split("Détail :"))[1];
            String detail = a.split("Error")[0];

            System.out.println("catchtiha f remove" + a);
            System.out.println("catchtiha f remove" + detail);
            JsfUtil.addErrorMessage(detail);*/
 /*JsfUtil.addErrorMessage("Ce type utilisateur ne peut pas étre supprimé car il est toujours réferencé par un utilisateur.");
            return false ;

        }*/
    }

}
