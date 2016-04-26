/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
