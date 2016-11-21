/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author JAIRINHO
 */
@Embeddable
public class MiembroxfacturaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "MIEMBRO_MIEMBRO_ID")
    private short miembroMiembroId;
    @Basic(optional = false)
    @Column(name = "FACTURA_FACTURA_ID")
    private int facturaFacturaId;

    public MiembroxfacturaPK() {
    }

    public MiembroxfacturaPK(short miembroMiembroId, int facturaFacturaId) {
        this.miembroMiembroId = miembroMiembroId;
        this.facturaFacturaId = facturaFacturaId;
    }

    public short getMiembroMiembroId() {
        return miembroMiembroId;
    }

    public void setMiembroMiembroId(short miembroMiembroId) {
        this.miembroMiembroId = miembroMiembroId;
    }

    public int getFacturaFacturaId() {
        return facturaFacturaId;
    }

    public void setFacturaFacturaId(int facturaFacturaId) {
        this.facturaFacturaId = facturaFacturaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) miembroMiembroId;
        hash += (int) facturaFacturaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroxfacturaPK)) {
            return false;
        }
        MiembroxfacturaPK other = (MiembroxfacturaPK) object;
        if (this.miembroMiembroId != other.miembroMiembroId) {
            return false;
        }
        if (this.facturaFacturaId != other.facturaFacturaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GUI.MiembroxfacturaPK[ miembroMiembroId=" + miembroMiembroId + ", facturaFacturaId=" + facturaFacturaId + " ]";
    }
    
}
