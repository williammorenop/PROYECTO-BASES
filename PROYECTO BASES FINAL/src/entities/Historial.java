/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JAIRINHO
 */
@Entity
@Table(name = "HISTORIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historial.findAll", query = "SELECT h FROM Historial h"),
    @NamedQuery(name = "Historial.findByFecha", query = "SELECT h FROM Historial h WHERE h.historialPK.fecha = :fecha"),
    @NamedQuery(name = "Historial.findByMiembroMiembroId", query = "SELECT h FROM Historial h WHERE h.historialPK.miembroMiembroId = :miembroMiembroId"),
    @NamedQuery(name = "Historial.findByGrupoGrupoId", query = "SELECT h FROM Historial h WHERE h.historialPK.grupoGrupoId = :grupoGrupoId"),
    @NamedQuery(name = "Historial.findByRolRolId", query = "SELECT h FROM Historial h WHERE h.historialPK.rolRolId = :rolRolId")})
public class Historial implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistorialPK historialPK;
    @JoinColumn(name = "GRUPO_GRUPO_ID", referencedColumnName = "GRUPO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "MIEMBRO_MIEMBRO_ID", referencedColumnName = "MIEMBRO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Miembro miembro;
    @JoinColumn(name = "ROL_ROL_ID", referencedColumnName = "ROL_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol;

    public Historial() {
    }

    public Historial(HistorialPK historialPK) {
        this.historialPK = historialPK;
    }

    public Historial(Date fecha, short miembroMiembroId, short grupoGrupoId, short rolRolId) {
        this.historialPK = new HistorialPK(fecha, miembroMiembroId, grupoGrupoId, rolRolId);
    }

    public HistorialPK getHistorialPK() {
        return historialPK;
    }

    public void setHistorialPK(HistorialPK historialPK) {
        this.historialPK = historialPK;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historialPK != null ? historialPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historial)) {
            return false;
        }
        Historial other = (Historial) object;
        if ((this.historialPK == null && other.historialPK != null) || (this.historialPK != null && !this.historialPK.equals(other.historialPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.Historial[ historialPK=" + historialPK + " ]";
    }
    
}
