/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.faces.component.visit.FullVisitContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.context.RequestContext;
import utilitaire.QuestionReponses;

/**
 *
 * @author Ahmed
 */
@ManagedBean(name = "InsererAffaireController")
@ViewScoped
public class InsererAffaireController implements Serializable {

    private QuestionReponses firstQuestion;
    private QuestionReponses nextQuestion;
    private String firstQuestionAnswer = "a";
    private List<QuestionReponses> listqr;

    public InsererAffaireController() {

    }

    @PostConstruct
    public void init() {
        setListqr(new ArrayList<>());
        QuestionReponses qr = getFirstQuestion();
        listqr.add(qr);
    }

    public void add(QuestionReponses qr) {
        QuestionReponses qrnext = getNextQuestion();
        int index = getIndexOf(qr);
        System.out.println("index" + index);
        if (listqr.size() == index + 1) {
            listqr.add(qrnext);
            //System.out.println(qr.getLibeleQuestion());
        } else {
            List<QuestionReponses> listtemp = new ArrayList<>() ;
        //    int size = listqr.size() ;
           // System.out.println("size "+size);
           // System.out.println("index "+index);
            for (int i = index+1; i < listqr.size(); i++) {
                listtemp.add(listqr.get(i)) ;
            }
            listqr.removeAll(listtemp);
            listqr.add(qrnext);

        }
    }

    public QuestionReponses getFirstQuestion() {

        setListqr(new ArrayList<>());

        List<String> listReponses = new ArrayList<>();
        listReponses.add("BT-BP");
        listReponses.add("MT-MP");
        listReponses.add("HT-HP");
        //  listqr.add( new QuestionReponses(1, "Indiquer type contrat :", listReponses)) ;
        return new QuestionReponses(1, "Indiquer type contrat :", listReponses);
    }

    public QuestionReponses getNextQuestion() {
        List<String> listReponses = new ArrayList<>();
        listReponses.add("option 1");
        listReponses.add("option 2");
        return new QuestionReponses(1, "question suivante :", listReponses);
    }

    public void displayNextQuestion() {
        nextQuestion = getNextQuestion();
        OutputLabel nextQuestionLabelUI = new OutputLabel();
        nextQuestionLabelUI.setValue(nextQuestion.getLibeleQuestion());

        SelectOneMenu nextQuestionDropDown = new SelectOneMenu();

        List<SelectItem> selectItems = new ArrayList<>();
        for (String s : nextQuestion.getListLibeleReponses()) {

            selectItems.add(new SelectItem(s, s));
        }
        UISelectItems selectItemsComponent = new UISelectItems();
        selectItemsComponent.setValue(selectItems);
        nextQuestionDropDown.getChildren().add(selectItemsComponent);

        int i = getLastID();

        UIComponent panelGrid = findComponent("panel_grid");
        panelGrid.getChildren().add(nextQuestionLabelUI);
        panelGrid.getChildren().add(nextQuestionDropDown);
        RequestContext.getCurrentInstance().update("panel_grid");
        i = getLastID();

    }

    public int getLastID() {

        UIComponent panelGrid = findComponent("panel_grid");
        String s_id = panelGrid.getChildren().get(panelGrid.getChildCount() - 1).getId();
        System.out.println("id " + s_id);
        String[] s_idd = s_id.split("id");
        System.out.println("id " + s_idd[1]);

        return Integer.parseInt(s_idd[1]);
    }

    public UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }

    /**
     * @return the firstQuestionAnswer
     */
    public String getFirstQuestionAnswer() {
        return firstQuestionAnswer;
    }

    /**
     * @param firstQuestionAnswer the firstQuestionAnswer to set
     */
    public void setFirstQuestionAnswer(String firstQuestionAnswer) {
        this.firstQuestionAnswer = firstQuestionAnswer;
    }

    /**
     * @return the listqr
     */
    public List<QuestionReponses> getListqr() {
        return listqr;
    }

    /**
     * @param listqr the listqr to set
     */
    public void setListqr(List<QuestionReponses> listqr) {
        this.listqr = listqr;
    }

    /**
     * @param firstQuestion the firstQuestion to set
     */
    public void setFirstQuestion(QuestionReponses firstQuestion) {
        this.firstQuestion = firstQuestion;
    }

    private int getIndexOf(QuestionReponses qr) {
        int i = 0;
        for (QuestionReponses a : listqr) {
            if (a.equals(qr)) {
                return i;
            }
            i++;
        }
        return -1;
    }

}
