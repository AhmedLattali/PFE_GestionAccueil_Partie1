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
@Table(name = "fonction")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fonction.findAll", query = "SELECT f FROM Fonction f"),
    @NamedQuery(name = "Fonction.findByLibeleFonction", query = "SELECT f FROM Fonction f WHERE f.libeleFonction = :libeleFonction")})

public class Fonction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "libele_fonction")
    private String libeleFonction;
    @OneToMany(mappedBy = "fonction")
    private List<Utilisateur> utilisateurList;

    public Fonction() {
    }

    public Fonction(String libeleFonction) {
        this.libeleFonction = libeleFonction;
    }

    public String getLibeleFonction() {
        return libeleFonction;
    }

    public void setLibeleFonction(String libeleFonction) {
        this.libeleFonction = libeleFonction;
    }

    @XmlTransient
    public List<Utilisateur> getUtilisateurList() {
        return utilisateurList;
    }

    public void setUtilisateurList(List<Utilisateur> utilisateurList) {
        this.utilisateurList = utilisateurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (libeleFonction != null ? libeleFonction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fonction)) {
            return false;
        }
        Fonction other = (Fonction) object;
        if ((this.libeleFonction == null && other.libeleFonction != null) || (this.libeleFonction != null && !this.libeleFonction.equals(other.libeleFonction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libeleFonction;
    }

}
