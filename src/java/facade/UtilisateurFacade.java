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
import javax.servlet.http.HttpSession;
import model.Utilisateur;
import utilitaire.Util;

/**
 *
 * @author Ahmed
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    public boolean edit2(Utilisateur utilisateur) {
        getEntityManager().merge(utilisateur);
        try {
            getEntityManager().flush();
            return true;
        } catch (PersistenceException e) {
            JsfUtil.addErrorMessage("Un attribut dupliqué rompt la contrainte d'unicité");
            JsfUtil.validationFailed();
            return false;
        }

    }

    public boolean create2(Utilisateur utilisateur) {
        int id = 1;
        try {
            Utilisateur u = getEntityManager().createNamedQuery("Utilisateur.findMaxId", Utilisateur.class).getSingleResult();
            id = u.getId() + 1;
            utilisateur.setId(id);
            getEntityManager().persist(utilisateur);
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
            utilisateur.setId(id);
            getEntityManager().persist(utilisateur);

        }
        return true;
    }

    public String getUserByPseudoEtMotDePasse(String pseudo, String motdepasse) {
        try {
            Utilisateur user
                    = getEntityManager().createNamedQuery("Utilisateur.loginControl", Utilisateur.class).setParameter("pseudo", pseudo).setParameter("motdepasse", motdepasse).getSingleResult();
            if (user != null) {
                // get Http Session and store username
                HttpSession session = Util.getSession();
                session.setAttribute("usersession", user);
                System.out.println(user.getTypeUtilisateur().toString());

                return user.getTypeUtilisateur().getLibeleTypeUtilisateur();

            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
