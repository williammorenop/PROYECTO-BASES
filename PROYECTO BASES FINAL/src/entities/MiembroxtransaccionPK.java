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
public class MiembroxtransaccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "MIEMBRO_MIEMBRO_ID")
    private short miembroMiembroId;
    @Basic(optional = false)
    @Column(name = "TRANSACCION_TRANSACCION_ID")
    private int transaccionTransaccionId;

    public MiembroxtransaccionPK() {
    }

    public MiembroxtransaccionPK(short miembroMiembroId, int transaccionTransaccionId) {
        this.miembroMiembroId = miembroMiembroId;
        this.transaccionTransaccionId = transaccionTransaccionId;
    }

    public short getMiembroMiembroId() {
        return miembroMiembroId;
    }

    public void setMiembroMiembroId(short miembroMiembroId) {
        this.miembroMiembroId = miembroMiembroId;
    }

    public int getTransaccionTransaccionId() {
        return transaccionTransaccionId;
    }

    public void setTransaccionTransaccionId(int transaccionTransaccionId) {
        this.transaccionTransaccionId = transaccionTransaccionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) miembroMiembroId;
        hash += (int) transaccionTransaccionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroxtransaccionPK)) {
            return false;
        }
        MiembroxtransaccionPK other = (MiembroxtransaccionPK) object;
        if (this.miembroMiembroId != other.miembroMiembroId) {
            return false;
        }
        if (this.transaccionTransaccionId != other.transaccionTransaccionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MiembroxtransaccionPK[ miembroMiembroId=" + miembroMiembroId + ", transaccionTransaccionId=" + transaccionTransaccionId + " ]";
    }
    
}
