package controller;

import model.Solution;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import facade.SolutionFacade;

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

@Named("solutionController")
@SessionScoped
public class SolutionController implements Serializable {

    @EJB
    private facade.SolutionFacade ejbFacade;
    private List<Solution> items = null;
    private Solution selected;

    public SolutionController() {
    }

    public Solution getSelected() {
        return selected;
    }

    public void setSelected(Solution selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SolutionFacade getFacade() {
        return ejbFacade;
    }

    public Solution prepareCreate() {
        selected = new Solution();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle2").getString("SolutionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle2").getString("SolutionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle2").getString("SolutionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Solution> getItems() {
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
                        if(getFacade().create2(selected) == true){
                             JsfUtil.addSuccessMessage(successMessage);
                        }
                       

                        break;
                    case UPDATE:
                        getFacade().edit(selected);
                        JsfUtil.addSuccessMessage(successMessage);

                        break;
                    case DELETE:
                        if (getFacade().remove2(selected) == true) {
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
    }

    public Solution getSolution(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Solution> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Solution> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Solution.class)
    public static class SolutionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SolutionController controller = (SolutionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "solutionController");
            return controller.getSolution(getKey(value));
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
            if (object instanceof Solution) {
                Solution o = (Solution) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Solution.class.getName()});
                return null;
            }
        }

    }

}
