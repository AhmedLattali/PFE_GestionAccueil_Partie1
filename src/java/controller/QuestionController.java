package controller;

import model.Question;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import facade.QuestionFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.QuestionReponseProposee;
import model.QuestionReponseProposeePK;
import utilitaire.QuestionReponses;
import model.Reponse;

@Named("questionController")
@SessionScoped
public class QuestionController implements Serializable {

    @EJB
    private facade.QuestionFacade ejbFacade;
    @EJB
    private facade.QuestionReponseProposeeFacade ejbQuestionReponseProposeeFacade;
    @EJB
    private facade.ReponseFacade ejbReponseFacade;
    private List<Question> items = null;
    private List<QuestionReponses> items2 = null;
    private Question selected;
    private QuestionReponses selected2;
    private List<String> reponsesSelected;
    private List<Reponse> reponsesSelected2;
    private final QuestionReponseProposeeController questionReponseProposeeController;
    private final ReponseController reponseController;
    private QuestionReponseProposee questionreponse;

    public QuestionController() {
        questionReponseProposeeController = new QuestionReponseProposeeController();
        questionReponseProposeeController.setFacade(ejbQuestionReponseProposeeFacade);
        reponseController = new ReponseController();
        reponseController.setFacade(ejbReponseFacade);

    }

    public Question getSelected() {
        return selected;
    }

    public void setSelected(Question selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuestionFacade getFacade() {
        return ejbFacade;
    }

    public Question prepareCreate() {
        selected = new Question();
        selected2 = new QuestionReponses();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareUpdate() {
        selected = new Question();
        selected.setId(selected2.getId());
        selected.setLibeleQuestion(selected2.getLibeleQuestion());
    }

    public void convertReponses() {
        reponsesSelected2 = new ArrayList<>();
        for (String s : reponsesSelected) {
            reponsesSelected2.add(new Reponse(Integer.parseInt(s.split(":")[0])));
            //System.out.println(reponsesSelected2.toString());

        }
    }

    public void create() {
        // selected.setId(selected2.getId());
        //selected.setLibeleQuestion(selected2.getLibeleQuestion());
        convertReponses();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle2").getString("QuestionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }

    }

    public void update() {

        convertReponses();
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle2").getString("QuestionUpdated"));
    }

    public void destroy() {
        selected.setId(selected2.getId());
        selected.setLibeleQuestion(selected2.getLibeleQuestion());
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle2").getString("QuestionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;
            selected2 = null;// Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Question> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<QuestionReponses> getItems2() {
        items2 = new ArrayList<>();
        questionReponseProposeeController.setFacade(ejbQuestionReponseProposeeFacade);
        reponseController.setFacade(ejbReponseFacade);
        List<QuestionReponseProposee> listqr = new ArrayList<>();
        List<String> listLibelReponses = new ArrayList<>();
        QuestionReponses qr = new QuestionReponses();
        Reponse reponse = new Reponse();
        items = getFacade().findAll();
        int i = 0;
        for (Question q : items) {
            qr.setId(q.getId());
            qr.setLibeleQuestion(q.getLibeleQuestion());
            listqr = questionReponseProposeeController.getReponsesByQuestionID(q.getId());

            for (QuestionReponseProposee qrp : listqr) {
                reponse = reponseController.getReponseByID(qrp.getQuestionReponseProposeePK().getReponseid());
                listLibelReponses.add(reponse.getLibeleReponse());

            }
            qr.setListLibeleReponses(listLibelReponses);
            //System.out.println(qr.getLibeleQuestion());
            items2.add(i, new QuestionReponses(q.getId(), q.getLibeleQuestion(), new ArrayList<>(listLibelReponses)));
            listLibelReponses.clear();
            i++;

        }

        return items2;
    }

    public void createReponses() {
        questionReponseProposeeController.setFacade(ejbQuestionReponseProposeeFacade);
        // getEjbQuestionReponseProposeeFacade().create(entity);
        for (Reponse r : reponsesSelected2) {
            questionreponse = questionReponseProposeeController.prepareCreate();
            questionreponse.setQuestionReponseProposeePK(new QuestionReponseProposeePK(selected.getId(), r.getId()));
            questionReponseProposeeController.setSelected(questionreponse);
            questionReponseProposeeController.create();
        }
    }

    public void updateReponses() {
        questionReponseProposeeController.setFacade(ejbQuestionReponseProposeeFacade);
        questionReponseProposeeController.deletByQuestionId(selected.getId());
        for (Reponse r : reponsesSelected2) {
            questionreponse = questionReponseProposeeController.prepareCreate();
            questionreponse.setQuestionReponseProposeePK(new QuestionReponseProposeePK(selected.getId(), r.getId()));
            questionReponseProposeeController.setSelected(questionreponse);

            questionReponseProposeeController.create();
        }

    }

    public void deleteReponses() {
        System.out.println("dkhalna f delete reponses");
        questionReponseProposeeController.setFacade(ejbQuestionReponseProposeeFacade);
        questionReponseProposeeController.deletByQuestionId(selected.getId());
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case CREATE:
                        if (getFacade().create2(selected) == true) {
                            createReponses();
                            JsfUtil.addSuccessMessage(successMessage);

                        }

                        break;
                    case UPDATE:
                        getFacade().edit(selected);
                        updateReponses();
                        JsfUtil.addSuccessMessage(successMessage);

                        break;
                    case DELETE:
                        if (getFacade().remove2(selected) == true) {
                            System.out.println("dkhalna f remove");
                            deleteReponses();
                            JsfUtil.addSuccessMessage(successMessage);
                        }
                        break;

                }

            } catch (EJBException ex) {
                String msg = "";

                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    msg = "la valeur d'un attribut dupliqué rompt la contrainte d'unicité";
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
                System.out.println(msg);
            } catch (Exception ex) {

                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }

        /*if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle2").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle2").getString("PersistenceErrorOccured"));
            }
        }*/
    }

    public Question getQuestion(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Question> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Question> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * @return the ejbQuestionReponseProposeeFacade
     */
    public facade.QuestionReponseProposeeFacade getEjbQuestionReponseProposeeFacade() {
        return ejbQuestionReponseProposeeFacade;
    }

    /**
     * @param ejbQuestionReponseProposeeFacade the
     * ejbQuestionReponseProposeeFacade to set
     */
    public void setEjbQuestionReponseProposeeFacade(facade.QuestionReponseProposeeFacade ejbQuestionReponseProposeeFacade) {
        this.ejbQuestionReponseProposeeFacade = ejbQuestionReponseProposeeFacade;
    }

    /**
     * @return the reponsesSelected
     */
    public List<String> getReponsesSelected() {
        return reponsesSelected;
    }

    /**
     * @param reponsesSelected the reponsesSelected to set
     */
    public void setReponsesSelected(List<String> reponsesSelected) {
        this.reponsesSelected = reponsesSelected;
    }

    /**
     * @return the selected2
     */
    public QuestionReponses getSelected2() {
        return selected2;
    }

    /**
     * @param selected2 the selected2 to set
     */
    public void setSelected2(QuestionReponses selected2) {
        this.selected2 = selected2;
    }

    @FacesConverter(forClass = Question.class)
    public static class QuestionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionController controller = (QuestionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionController");
            return controller.getQuestion(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Question) {
                Question o = (Question) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Question.class.getName()});
                return null;
            }
        }

    }

}
