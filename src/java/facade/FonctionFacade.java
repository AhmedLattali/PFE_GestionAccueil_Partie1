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
import model.Utilisateur;

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

    public boolean create2(Fonction t) {
        try {
            getEntityManager().persist(t);
            getEntityManager().flush();
            return true;
        } catch (PersistenceException e) {
            JsfUtil.addErrorMessage("Cette fonction existe déja");
            JsfUtil.validationFailed();
            return false;
        }
    }

    public boolean remove2(Fonction f) {
        List<Utilisateur> list;
        list = getEntityManager().createNamedQuery("Utilisateur.findByFonction", Utilisateur.class).
                setParameter("libele_fonction", f.getLibeleFonction()).getResultList();
        if (!list.isEmpty()) {
            //  System.out.println("koko" + list.get(0).getId());
            JsfUtil.addErrorMessage("Cette fonction ne peut pas étre supprimée car elle est toujours réferencée par un utilisateur.");
            return false;
        } else {
            System.out.println("list == null remove ");
            getEntityManager().remove(getEntityManager().merge(f));
            return true;
        }
    }

}
