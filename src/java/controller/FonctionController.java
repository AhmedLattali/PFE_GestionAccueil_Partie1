package controller;

import model.Fonction;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import facade.FonctionFacade;

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

@Named("fonctionController")
@SessionScoped
public class FonctionController implements Serializable {

    @EJB
    private facade.FonctionFacade ejbFacade;
    private List<Fonction> items = null;
    private Fonction selected;

    public FonctionController() {
    }

    public Fonction getSelected() {
        return selected;
    }

    public void setSelected(Fonction selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FonctionFacade getFacade() {
        return ejbFacade;
    }

    public Fonction prepareCreate() {
        selected = new Fonction();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FonctionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FonctionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FonctionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Fonction> getItems() {
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
                        if (getFacade().create2(selected) == true) {
                            JsfUtil.addSuccessMessage(successMessage);
                        }

                        break;
                    case DELETE:
                        if (getFacade().remove2(selected) == true) {
                            JsfUtil.addSuccessMessage(successMessage);
                        }
                        break;

                }
                /* if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                    JsfUtil.addSuccessMessage(successMessage);
                } else {

                    if(getFacade().remove2(selected)==true){
                        JsfUtil.addSuccessMessage(successMessage);
                        
                    }
                }*/

            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (!(msg.length() > 0)) {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Fonction getFonction(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Fonction> getItemsAvailableSelectMany() {

        return getFacade().findAll();

    }

    public List<Fonction> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Fonction.class)
    public static class FonctionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FonctionController controller = (FonctionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fonctionController");
            return controller.getFonction(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Fonction) {
                Fonction o = (Fonction) object;
                return getStringKey(o.getLibeleFonction());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Fonction.class.getName()});
                return null;
            }
        }

    }

}
