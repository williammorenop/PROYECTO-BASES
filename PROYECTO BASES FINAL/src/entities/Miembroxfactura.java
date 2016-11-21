/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @author johan
 */
@Entity
@Table(name = "MIEMBROXFACTURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miembroxfactura.findAll", query = "SELECT m FROM Miembroxfactura m"),
    @NamedQuery(name = "Miembroxfactura.findByMiembroMiembroId", query = "SELECT m FROM Miembroxfactura m WHERE m.miembroxfacturaPK.miembroMiembroId = :miembroMiembroId"),
    @NamedQuery(name = "Miembroxfactura.findByFacturaFacturaId", query = "SELECT m FROM Miembroxfactura m WHERE m.miembroxfacturaPK.facturaFacturaId = :facturaFacturaId"),
    @NamedQuery(name = "Miembroxfactura.findByMonto", query = "SELECT m FROM Miembroxfactura m WHERE m.monto = :monto")})
public class Miembroxfactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MiembroxfacturaPK miembroxfacturaPK;
    @Column(name = "MONTO")
    private BigInteger monto;
    @JoinColumn(name = "FACTURA_FACTURA_ID", referencedColumnName = "FACTURA_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Factura factura;
    @JoinColumn(name = "MIEMBRO_MIEMBRO_ID", referencedColumnName = "MIEMBRO_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Miembro miembro;

    public Miembroxfactura() {
    }

    public Miembroxfactura(MiembroxfacturaPK miembroxfacturaPK) {
        this.miembroxfacturaPK = miembroxfacturaPK;
    }

    public Miembroxfactura(short miembroMiembroId, int facturaFacturaId) {
        this.miembroxfacturaPK = new MiembroxfacturaPK(miembroMiembroId, facturaFacturaId);
    }

    public MiembroxfacturaPK getMiembroxfacturaPK() {
        return miembroxfacturaPK;
    }

    public void setMiembroxfacturaPK(MiembroxfacturaPK miembroxfacturaPK) {
        this.miembroxfacturaPK = miembroxfacturaPK;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miembroxfacturaPK != null ? miembroxfacturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembroxfactura)) {
            return false;
        }
        Miembroxfactura other = (Miembroxfactura) object;
        if ((this.miembroxfacturaPK == null && other.miembroxfacturaPK != null) || (this.miembroxfacturaPK != null && !this.miembroxfacturaPK.equals(other.miembroxfacturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Miembroxfactura[ miembroxfacturaPK=" + miembroxfacturaPK + " ]";
    }
    
}
