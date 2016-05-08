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
import model.Question;
import model.Reponse;

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
        List<Reponse> list;
        list = getEntityManager().createNamedQuery("Reponse.findByQuestionID", Reponse.class).
                setParameter("question_id", question.getId()).getResultList();
        if (!list.isEmpty()) {
            //  System.out.println("koko" + list.get(0).getId());
            JsfUtil.addErrorMessage("Cette solution ne peut pas étre supprimée car elle est toujours réferencée par une réponse.");
            return false;
        } else {
            System.out.println("list == null remove ");
            getEntityManager().remove(getEntityManager().merge(question));
            return true;
        }
    }

}
