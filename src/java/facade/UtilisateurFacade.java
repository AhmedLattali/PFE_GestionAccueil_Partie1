/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Override
    public void create(Utilisateur u) {
        System.out.println("aaaa");
        u.setId(count() + 1);
        getEntityManager().persist(u);
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
