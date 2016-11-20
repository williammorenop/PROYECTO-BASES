/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JAIRINHO
 */
@Embeddable
public class HistorialPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "MIEMBRO_MIEMBRO_ID")
    private short miembroMiembroId;
    @Basic(optional = false)
    @Column(name = "GRUPO_GRUPO_ID")
    private short grupoGrupoId;
    @Basic(optional = false)
    @Column(name = "ROL_ROL_ID")
    private short rolRolId;

    public HistorialPK() {
    }

    public HistorialPK(Date fecha, short miembroMiembroId, short grupoGrupoId, short rolRolId) {
        this.fecha = fecha;
        this.miembroMiembroId = miembroMiembroId;
        this.grupoGrupoId = grupoGrupoId;
        this.rolRolId = rolRolId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public short getMiembroMiembroId() {
        return miembroMiembroId;
    }

    public void setMiembroMiembroId(short miembroMiembroId) {
        this.miembroMiembroId = miembroMiembroId;
    }

    public short getGrupoGrupoId() {
        return grupoGrupoId;
    }

    public void setGrupoGrupoId(short grupoGrupoId) {
        this.grupoGrupoId = grupoGrupoId;
    }

    public short getRolRolId() {
        return rolRolId;
    }

    public void setRolRolId(short rolRolId) {
        this.rolRolId = rolRolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (int) miembroMiembroId;
        hash += (int) grupoGrupoId;
        hash += (int) rolRolId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialPK)) {
            return false;
        }
        HistorialPK other = (HistorialPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.miembroMiembroId != other.miembroMiembroId) {
            return false;
        }
        if (this.grupoGrupoId != other.grupoGrupoId) {
            return false;
        }
        if (this.rolRolId != other.rolRolId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.HistorialPK[ fecha=" + fecha + ", miembroMiembroId=" + miembroMiembroId + ", grupoGrupoId=" + grupoGrupoId + ", rolRolId=" + rolRolId + " ]";
    }
    
}
