/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "question_reponse_proposee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionReponseProposee.findAll", query = "SELECT q FROM QuestionReponseProposee q"),
    @NamedQuery(name = "QuestionReponseProposee.findByQuestionid", query = "SELECT q FROM QuestionReponseProposee q WHERE q.questionReponseProposeePK.questionid = :questionid"),
    @NamedQuery(name = "QuestionReponseProposee.findByReponseid", query = "SELECT q FROM QuestionReponseProposee q WHERE q.questionReponseProposeePK.reponseid = :reponseid"),
    @NamedQuery(name = "QuestionReponseProposee.findByLibele", query = "SELECT q FROM QuestionReponseProposee q WHERE q.libele = :libele")})
public class QuestionReponseProposee implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QuestionReponseProposeePK questionReponseProposeePK;
    @Size(max = 2147483647)
    @Column(name = "libele")
    private String libele;

    public QuestionReponseProposee() {
    }

    public QuestionReponseProposee(QuestionReponseProposeePK questionReponseProposeePK) {
        this.questionReponseProposeePK = questionReponseProposeePK;
    }

    public QuestionReponseProposee(int questionid, int reponseid) {
        this.questionReponseProposeePK = new QuestionReponseProposeePK(questionid, reponseid);
    }

    public QuestionReponseProposeePK getQuestionReponseProposeePK() {
        return questionReponseProposeePK;
    }

    public void setQuestionReponseProposeePK(QuestionReponseProposeePK questionReponseProposeePK) {
        this.questionReponseProposeePK = questionReponseProposeePK;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionReponseProposeePK != null ? questionReponseProposeePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionReponseProposee)) {
            return false;
        }
        QuestionReponseProposee other = (QuestionReponseProposee) object;
        if ((this.questionReponseProposeePK == null && other.questionReponseProposeePK != null) || (this.questionReponseProposeePK != null && !this.questionReponseProposeePK.equals(other.questionReponseProposeePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.QuestionReponseProposee[ questionReponseProposeePK=" + questionReponseProposeePK + " ]";
    }
    
}
