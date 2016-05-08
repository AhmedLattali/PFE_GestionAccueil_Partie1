/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ahmed
 */
@Embeddable
public class QuestionReponseProposeePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "questionid")
    private int questionid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reponseid")
    private int reponseid;

    public QuestionReponseProposeePK() {
    }

    public QuestionReponseProposeePK(int questionid, int reponseid) {
        this.questionid = questionid;
        this.reponseid = reponseid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public int getReponseid() {
        return reponseid;
    }

    public void setReponseid(int reponseid) {
        this.reponseid = reponseid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) questionid;
        hash += (int) reponseid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionReponseProposeePK)) {
            return false;
        }
        QuestionReponseProposeePK other = (QuestionReponseProposeePK) object;
        if (this.questionid != other.questionid) {
            return false;
        }
        if (this.reponseid != other.reponseid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.QuestionReponseProposeePK[ questionid=" + questionid + ", reponseid=" + reponseid + " ]";
    }
    
}
