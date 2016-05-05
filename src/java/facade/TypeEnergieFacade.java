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
import model.Affaire;
import model.TypeEnergie;

/**
 *
 * @author Ahmed
 */
@Stateless
public class TypeEnergieFacade extends AbstractFacade<TypeEnergie> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeEnergieFacade() {
        super(TypeEnergie.class);
    }

    public boolean create2(TypeEnergie t) {
        try {
            getEntityManager().persist(t);
            getEntityManager().flush();
            return true;
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ce type Energie existe déja");
            JsfUtil.validationFailed();
            return false;
        }

    }

    public boolean remove2(TypeEnergie t) {
        List<Affaire> list;
        list = getEntityManager().createNamedQuery("Affaire.findByEnergie", Affaire.class).
                setParameter("type_energie", t.getLibeleTypeEnergie()).getResultList();
        if (!list.isEmpty()) {
            //  System.out.println("koko" + list.get(0).getId());
            JsfUtil.addErrorMessage("Ce type Energie ne peut pas étre supprimé car il est toujours réferencé par une affaire.");
            return false;
        } else {
            System.out.println("list == null remove ");
            getEntityManager().remove(getEntityManager().merge(t));
            return true;
        }

    }

}
