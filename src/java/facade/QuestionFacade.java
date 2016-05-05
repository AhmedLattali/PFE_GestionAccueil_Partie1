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
import model.Question;

/**
 *
 * @author Ahmed
 */
@Stateless
public class QuestionFacade extends AbstractFacade<Question> {

    @PersistenceContext(unitName = "PFE_GestionAccueil_Partie1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionFacade() {
        super(Question.class);
    }

        public boolean create2(Question question) {
        int id = 1;
        try {
            Question q = getEntityManager().createNamedQuery("Question.findMaxId", Question.class).getSingleResult();
            id = q.getId() + 1;
            question.setId(id);
            getEntityManager().persist(question);
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
            question.setId(id);
            getEntityManager().persist(question);

        }
        return true;
    }

    public boolean remove2(Question question) {
         getEntityManager().remove(getEntityManager().merge(question));
         return true;
    }

}
