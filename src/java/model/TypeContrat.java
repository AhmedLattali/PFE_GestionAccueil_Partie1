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
@Table(name = "type_contrat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeContrat.findAll", query = "SELECT t FROM TypeContrat t"),
    @NamedQuery(name = "TypeContrat.findByLibeleTypeContrat", query = "SELECT t FROM TypeContrat t WHERE t.libeleTypeContrat = :libeleTypeContrat")})
public class TypeContrat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libele_type_contrat")
    private String libeleTypeContrat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeContrat")
    private List<Affaire> affaireList;

    public TypeContrat() {
    }

    public TypeContrat(String libeleTypeContrat) {
        this.libeleTypeContrat = libeleTypeContrat;
    }

    public String getLibeleTypeContrat() {
        return libeleTypeContrat;
    }

    public void setLibeleTypeContrat(String libeleTypeContrat) {
        this.libeleTypeContrat = libeleTypeContrat;
    }

    @XmlTransient
    public List<Affaire> getAffaireList() {
        return affaireList;
    }

    public void setAffaireList(List<Affaire> affaireList) {
        this.affaireList = affaireList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (libeleTypeContrat != null ? libeleTypeContrat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeContrat)) {
            return false;
        }
        TypeContrat other = (TypeContrat) object;
        if ((this.libeleTypeContrat == null && other.libeleTypeContrat != null) || (this.libeleTypeContrat != null && !this.libeleTypeContrat.equals(other.libeleTypeContrat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libeleTypeContrat;
    }

}
