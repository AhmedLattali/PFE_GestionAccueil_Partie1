/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "type_utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeUtilisateur.findAll", query = "SELECT t FROM TypeUtilisateur t"),
    @NamedQuery(name = "TypeUtilisateur.findByLibeleTypeUtilisateur", query = "SELECT t FROM TypeUtilisateur t WHERE t.libeleTypeUtilisateur = :libeleTypeUtilisateur")})
public class TypeUtilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "libele_type_utilisateur")
    private String libeleTypeUtilisateur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeUtilisateur")
    private List<Utilisateur> utilisateurList;

    public TypeUtilisateur() {
    }

    public TypeUtilisateur(String libeleTypeUtilisateur) {
        this.libeleTypeUtilisateur = libeleTypeUtilisateur;
    }

    public String getLibeleTypeUtilisateur() {
        return libeleTypeUtilisateur;
    }

    public void setLibeleTypeUtilisateur(String libeleTypeUtilisateur) {
        this.libeleTypeUtilisateur = libeleTypeUtilisateur;
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
        hash += (libeleTypeUtilisateur != null ? libeleTypeUtilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeUtilisateur)) {
            return false;
        }
        TypeUtilisateur other = (TypeUtilisateur) object;
        if ((this.libeleTypeUtilisateur == null && other.libeleTypeUtilisateur != null) || (this.libeleTypeUtilisateur != null && !this.libeleTypeUtilisateur.equals(other.libeleTypeUtilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libeleTypeUtilisateur;
    }

}
