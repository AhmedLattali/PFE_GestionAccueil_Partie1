/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.util.List;

/**
 *
 * @author Ahmed
 */
public class QuestionReponses {

    private int id;
    private String libeleQuestion;
    private List<String> listLibeleReponses;
    
    public QuestionReponses(){
        
    }

    public QuestionReponses(int id, String libeleQuestion, List<String> listLibeleReponses) {
        this.id = id;
        this.libeleQuestion = libeleQuestion;
        this.listLibeleReponses = listLibeleReponses;

    }

    /**
     * @return the listLibeleReponses
     */
    public List<String> getListLibeleReponses() {
        return listLibeleReponses;
    }

    /**
     * @param listLibeleReponses the listLibeleReponses to set
     */
    public void setListLibeleReponses(List<String> listLibeleReponses) {
        this.listLibeleReponses = listLibeleReponses;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the libeleQuestion
     */
    public String getLibeleQuestion() {
        return libeleQuestion;
    }

    /**
     * @param libeleQuestion the libeleQuestion to set
     */
    public void setLibeleQuestion(String libeleQuestion) {
        this.libeleQuestion = libeleQuestion;
    }
    
    public void setLibeleReponse(String libeleReponse){
        this.listLibeleReponses.add(libeleReponse) ;
    }
   

}
