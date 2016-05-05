/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "reponse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reponse.findAll", query = "SELECT r FROM Reponse r"),
    @NamedQuery(name = "Reponse.findById", query = "SELECT r FROM Reponse r WHERE r.id = :id"),
     @NamedQuery(name = "Reponse.findMaxId", query = "select r from Reponse r WHERE r.id = ( select max(r2.id) from Reponse r2)"),
    @NamedQuery(name = "Reponse.findByLibeleReponse", query = "SELECT r FROM Reponse r WHERE r.libeleReponse = :libeleReponse")})
public class Reponse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libele_reponse")
    private String libeleReponse;
    @JoinTable(name = "question_reponse_proposee", joinColumns = {
        @JoinColumn(name = "reponseid", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "questionid", referencedColumnName = "id")})
    @ManyToMany
    private List<Question> questionList;
    @JoinColumn(name = "question_suivant_id", referencedColumnName = "id")
    @ManyToOne
    private Question questionSuivantId;
    @JoinColumn(name = "solutionid", referencedColumnName = "id")
    @ManyToOne
    private Solution solutionid;

    public Reponse() {
    }

    public Reponse(Integer id) {
        this.id = id;
    }

    public Reponse(Integer id, String libeleReponse) {
        this.id = id;
        this.libeleReponse = libeleReponse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibeleReponse() {
        return libeleReponse;
    }

    public void setLibeleReponse(String libeleReponse) {
        this.libeleReponse = libeleReponse;
    }

    @XmlTransient
    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public Question getQuestionSuivantId() {
        return questionSuivantId;
    }

    public void setQuestionSuivantId(Question questionSuivantId) {
        this.questionSuivantId = questionSuivantId;
    }

    public Solution getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(Solution solutionid) {
        this.solutionid = solutionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reponse)) {
            return false;
        }
        Reponse other = (Reponse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
         return id.toString()+":"+libeleReponse;
    }
    
}
