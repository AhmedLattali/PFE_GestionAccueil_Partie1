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
import model.TypeContrat;

/**
 *
 * @author Ahmed
 */
@Stateless
public class TypeContratFacade extends AbstractFacade<TypeContrat> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeContratFacade() {
        super(TypeContrat.class);
    }

    public boolean create2(TypeContrat t) {
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

    public boolean remove2(TypeContrat t) {
        List<Affaire> list;
        list = getEntityManager().createNamedQuery("Affaire.findByTypeContrat", Affaire.class).
                setParameter("type_contrat", t.getLibeleTypeContrat()).getResultList();
        if (!list.isEmpty()) {
            //  System.out.println("koko" + list.get(0).getId());
            JsfUtil.addErrorMessage("Ce type contrat ne peut pas étre supprimé car il est toujours réferencé par une affaire.");
            return false;
        } else {
            System.out.println("list == null remove ");
            getEntityManager().remove(getEntityManager().merge(t));
            return true;
        }
    }

}
