/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "MIEMBROXTRANSACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miembroxtransaccion.findAll", query = "SELECT m FROM Miembroxtransaccion m"),
    @NamedQuery(name = "Miembroxtransaccion.findByMiembroMiembroId", query = "SELECT m FROM Miembroxtransaccion m WHERE m.miembroxtransaccionPK.miembroMiembroId = :miembroMiembroId"),
    @NamedQuery(name = "Miembroxtransaccion.findByTransaccionTransaccionId", query = "SELECT m FROM Miembroxtransaccion m WHERE m.miembroxtransaccionPK.transaccionTransaccionId = :transaccionTransaccionId"),
    @NamedQuery(name = "Miembroxtransaccion.findByTipo", query = "SELECT m FROM Miembroxtransaccion m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Miembroxtransaccion.findByMonto", query = "SELECT m FROM Miembroxtransaccion m WHERE m.monto = :monto"),
    @NamedQuery(name = "Miembroxtransaccion.findByAfectadoId", query = "SELECT m FROM Miembroxtransaccion m WHERE m.afectadoId = :afectadoId")})
public class Miembroxtransaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MiembroxtransaccionPK miembroxtransaccionPK;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private short tipo;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private long monto;
    @Basic(optional = false)
    @Column(name = "AFECTADO_ID")
    private short afectadoId;
    @JoinColumn(name = "MIEMBRO_MIEMBRO_ID", referencedColumnName = "MIEMBRO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Miembro miembro;
    @JoinColumn(name = "TRANSACCION_TRANSACCION_ID", referencedColumnName = "TRANSACCION_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Transaccion transaccion;

    public Miembroxtransaccion() {
    }

    public Miembroxtransaccion(MiembroxtransaccionPK miembroxtransaccionPK) {
        this.miembroxtransaccionPK = miembroxtransaccionPK;
    }

    public Miembroxtransaccion(MiembroxtransaccionPK miembroxtransaccionPK, short tipo, long monto, short afectadoId) {
        this.miembroxtransaccionPK = miembroxtransaccionPK;
        this.tipo = tipo;
        this.monto = monto;
        this.afectadoId = afectadoId;
    }

    public Miembroxtransaccion(short miembroMiembroId, int transaccionTransaccionId) {
        this.miembroxtransaccionPK = new MiembroxtransaccionPK(miembroMiembroId, transaccionTransaccionId);
    }

    public MiembroxtransaccionPK getMiembroxtransaccionPK() {
        return miembroxtransaccionPK;
    }

    public void setMiembroxtransaccionPK(MiembroxtransaccionPK miembroxtransaccionPK) {
        this.miembroxtransaccionPK = miembroxtransaccionPK;
    }

    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public short getAfectadoId() {
        return afectadoId;
    }

    public void setAfectadoId(short afectadoId) {
        this.afectadoId = afectadoId;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miembroxtransaccionPK != null ? miembroxtransaccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembroxtransaccion)) {
            return false;
        }
        Miembroxtransaccion other = (Miembroxtransaccion) object;
        if ((this.miembroxtransaccionPK == null && other.miembroxtransaccionPK != null) || (this.miembroxtransaccionPK != null && !this.miembroxtransaccionPK.equals(other.miembroxtransaccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Miembroxtransaccion[ miembroxtransaccionPK=" + miembroxtransaccionPK + " ]";
    }
    
}
