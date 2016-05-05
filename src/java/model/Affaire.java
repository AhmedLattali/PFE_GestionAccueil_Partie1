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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "affaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Affaire.findAll", query = "SELECT a FROM Affaire a"),
    @NamedQuery(name = "Affaire.findById", query = "SELECT a FROM Affaire a WHERE a.id = :id"),
    @NamedQuery(name = "Affaire.findByCode", query = "SELECT a FROM Affaire a WHERE a.code = :code"),
    @NamedQuery(name = "Affaire.findByLibeleAffaire", query = "SELECT a FROM Affaire a WHERE a.libeleAffaire = :libeleAffaire"),
    @NamedQuery(name = "Affaire.findByPresentation", query = "SELECT a FROM Affaire a WHERE a.presentation = :presentation"),
    @NamedQuery(name = "Affaire.findMaxId", query = "select a from Affaire a WHERE a.id = ( select max(a2.id) from Affaire a2)"),
    @NamedQuery(name = "Affaire.findByTypeContrat", query = "SELECT a FROM Affaire a WHERE a.typeContrat.libeleTypeContrat = :type_contrat"),
    @NamedQuery(name = "Affaire.findByEnergie", query = "SELECT a FROM Affaire a WHERE a.typeEnergie.libeleTypeEnergie = :type_energie")
})
public class Affaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "libele_affaire")
    private String libeleAffaire;
    @Size(max = 2500)
    @Column(name = "presentation")
    private String presentation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "affaireid")
    private List<Solution> solutionList;
    @JoinColumn(name = "type_contrat", referencedColumnName = "libele_type_contrat")
    @ManyToOne(optional = false)
    private TypeContrat typeContrat;
    @JoinColumn(name = "type_energie", referencedColumnName = "libele_type_energie")
    @ManyToOne(optional = false)
    private TypeEnergie typeEnergie;

    public Affaire() {
    }

    public Affaire(Integer id) {
        this.id = id;
    }

    public Affaire(Integer id, String code, String libeleAffaire) {
        this.id = id;
        this.code = code;
        this.libeleAffaire = libeleAffaire;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibeleAffaire() {
        return libeleAffaire;
    }

    public void setLibeleAffaire(String libeleAffaire) {
        this.libeleAffaire = libeleAffaire;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    @XmlTransient
    public List<Solution> getSolutionList() {
        return solutionList;
    }

    public void setSolutionList(List<Solution> solutionList) {
        this.solutionList = solutionList;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public TypeEnergie getTypeEnergie() {
        return typeEnergie;
    }

    public void setTypeEnergie(TypeEnergie typeEnergie) {
        this.typeEnergie = typeEnergie;
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
        if (!(object instanceof Affaire)) {
            return false;
        }
        Affaire other = (Affaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return code + ":" + libeleAffaire;
    }
    
}
