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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByLibeleQuestion", query = "SELECT q FROM Question q WHERE q.libeleQuestion = :libeleQuestion"),
    @NamedQuery(name = "Question.findMaxId", query = "select q from Question q WHERE q.id = ( select max(q2.id) from Question q2)")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libele_question")
    private String libeleQuestion;
    @ManyToMany(mappedBy = "questionList")
    private List<Reponse> reponseList;
    @OneToMany(mappedBy = "questionSuivantId")
    private List<Reponse> reponseList1;

    public Question() {
    }

    public Question(Integer id) {
        this.id = id;
    }

    public Question(Integer id, String libeleQuestion) {
        this.id = id;
        this.libeleQuestion = libeleQuestion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibeleQuestion() {
        return libeleQuestion;
    }

    public void setLibeleQuestion(String libeleQuestion) {
        this.libeleQuestion = libeleQuestion;
    }

    @XmlTransient
    public List<Reponse> getReponseList() {
        return reponseList;
    }

    public void setReponseList(List<Reponse> reponseList) {
        this.reponseList = reponseList;
    }

    @XmlTransient
    public List<Reponse> getReponseList1() {
        return reponseList1;
    }

    public void setReponseList1(List<Reponse> reponseList1) {
        this.reponseList1 = reponseList1;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString() + ":" + libeleQuestion;
    }

}
