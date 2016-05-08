package controller;

import model.QuestionReponseProposee;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import facade.QuestionReponseProposeeFacade;

import java.io.Serializable;
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

@Named("questionReponseProposeeController")
@SessionScoped
public class QuestionReponseProposeeController implements Serializable {

    @EJB
    private facade.QuestionReponseProposeeFacade ejbFacade;
    private List<QuestionReponseProposee> items = null;
    private QuestionReponseProposee selected;

    public QuestionReponseProposeeController() {
    }

    public QuestionReponseProposee getSelected() {
        return selected;
    }

    public void setSelected(QuestionReponseProposee selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
        selected.setQuestionReponseProposeePK(new model.QuestionReponseProposeePK());
    }

    private QuestionReponseProposeeFacade getFacade() {
        return ejbFacade;
    }

    public void setFacade(QuestionReponseProposeeFacade ejFacade) {
        this.ejbFacade = ejFacade;
    }

    public QuestionReponseProposee prepareCreate() {
        selected = new QuestionReponseProposee();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle3").getString("QuestionReponseProposeeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle3").getString("QuestionReponseProposeeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle3").getString("QuestionReponseProposeeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QuestionReponseProposee> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case CREATE:
                           getFacade().create(selected);

                        break;
                    case UPDATE:
                        getFacade().edit(selected);

                        break;
                    case DELETE:
                       getFacade().remove(selected) ;
                        break;

                }
                /*
                if (persistAction != PersistAction.DELETE) {
                    System.out.println("question reponse a ajouter "+selected.toString());
                    getFacade().edit(selected);
                } else {
                   
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);*/
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle3").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle3").getString("PersistenceErrorOccured"));
            }
        }
    }

    public QuestionReponseProposee getQuestionReponseProposee(model.QuestionReponseProposeePK id) {
        return getFacade().find(id);
    }

    public List<QuestionReponseProposee> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<QuestionReponseProposee> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void deletByQuestionId(int questionId) {
        System.out.println("dkhalna f deletequestionbyid ta3 questionreponse controller");
        getFacade().deleteByQuestionID(questionId);
    }

    @FacesConverter(forClass = QuestionReponseProposee.class)
    public static class QuestionReponseProposeeControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuestionReponseProposeeController controller = (QuestionReponseProposeeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "questionReponseProposeeController");
            return controller.getQuestionReponseProposee(getKey(value));
        }

        model.QuestionReponseProposeePK getKey(String value) {
            model.QuestionReponseProposeePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.QuestionReponseProposeePK();
            key.setQuestionid(Integer.parseInt(values[0]));
            key.setReponseid(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.QuestionReponseProposeePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getQuestionid());
            sb.append(SEPARATOR);
            sb.append(value.getReponseid());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof QuestionReponseProposee) {
                QuestionReponseProposee o = (QuestionReponseProposee) object;
                return getStringKey(o.getQuestionReponseProposeePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuestionReponseProposee.class.getName()});
                return null;
            }
        }

    }
    public List<QuestionReponseProposee> getReponsesByQuestionID(int question_id){
        return getFacade().getReponsesByQuestionID(question_id) ;
    }

}
