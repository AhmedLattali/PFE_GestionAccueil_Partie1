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
@Table(name = "type_energie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeEnergie.findAll", query = "SELECT t FROM TypeEnergie t"),
    @NamedQuery(name = "TypeEnergie.findByLibeleTypeEnergie", query = "SELECT t FROM TypeEnergie t WHERE t.libeleTypeEnergie = :libeleTypeEnergie")})
public class TypeEnergie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libele_type_energie")
    private String libeleTypeEnergie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeEnergie")
    private List<Affaire> affaireList;

    public TypeEnergie() {
    }

    public TypeEnergie(String libeleTypeEnergie) {
        this.libeleTypeEnergie = libeleTypeEnergie;
    }

    public String getLibeleTypeEnergie() {
        return libeleTypeEnergie;
    }

    public void setLibeleTypeEnergie(String libeleTypeEnergie) {
        this.libeleTypeEnergie = libeleTypeEnergie;
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
        hash += (libeleTypeEnergie != null ? libeleTypeEnergie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeEnergie)) {
            return false;
        }
        TypeEnergie other = (TypeEnergie) object;
        if ((this.libeleTypeEnergie == null && other.libeleTypeEnergie != null) || (this.libeleTypeEnergie != null && !this.libeleTypeEnergie.equals(other.libeleTypeEnergie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return libeleTypeEnergie;
    }
    
}
