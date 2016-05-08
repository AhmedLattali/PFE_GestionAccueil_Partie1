/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.QuestionReponseProposee;

/**
 *
 * @author Ahmed
 */
@Stateless
public class QuestionReponseProposeeFacade extends AbstractFacade<QuestionReponseProposee> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionReponseProposeeFacade() {
        super(QuestionReponseProposee.class);
    }

    public void deleteByQuestionID(int questionId) {
        System.out.println("dkhalna f deletequestionbyid ta3 facade");
        List<QuestionReponseProposee> lqrp = getEntityManager().createNamedQuery("QuestionReponseProposee.findByQuestionid", QuestionReponseProposee.class).setParameter("questionid", questionId).getResultList();
        for (QuestionReponseProposee qrp : lqrp) {
            System.out.println("dkhalna f lboucle");
            System.out.println("question reponse a supprimé " + qrp.toString());
            getEntityManager().remove(getEntityManager().merge(qrp));
            // System.out.println(qrp.getQuestionReponseProposeePK().toString()+"supprimé avec succes");
        }

    }

    public List<QuestionReponseProposee> getReponsesByQuestionID(int questionId) {
        return getEntityManager().createNamedQuery("QuestionReponseProposee.findByQuestionid", QuestionReponseProposee.class).setParameter("questionid", questionId).getResultList();

    }

}
