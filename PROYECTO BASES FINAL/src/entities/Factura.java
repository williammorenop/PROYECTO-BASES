/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JAIRINHO
 */
@Entity
@Table(name = "FACTURA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByFacturaId", query = "SELECT f FROM Factura f WHERE f.facturaId = :facturaId"),
    @NamedQuery(name = "Factura.findByNombre", query = "SELECT f FROM Factura f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Factura.findByCosto", query = "SELECT f FROM Factura f WHERE f.costo = :costo"),
    @NamedQuery(name = "Factura.findByComprobante", query = "SELECT f FROM Factura f WHERE f.comprobante = :comprobante"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByHora", query = "SELECT f FROM Factura f WHERE f.hora = :hora"),
    @NamedQuery(name = "Factura.findByUbicacion", query = "SELECT f FROM Factura f WHERE f.ubicacion = :ubicacion"),
    @NamedQuery(name = "Factura.findByCreadorMiembro", query = "SELECT f FROM Factura f WHERE f.creadorMiembro = :creadorMiembro"),
    @NamedQuery(name = "Factura.findByComentarios", query = "SELECT f FROM Factura f WHERE f.comentarios = :comentarios"),
    @NamedQuery(name = "Factura.findByMonto", query = "SELECT f FROM Factura f WHERE f.monto = :monto")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FACTURA_ID")
    private Integer facturaId;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "COSTO")
    private long costo;
    @Column(name = "COMPROBANTE")
    private Short comprobante;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "HORA")
    private String hora;
    @Column(name = "UBICACION")
    private String ubicacion;
    @Basic(optional = false)
    @Column(name = "CREADOR_MIEMBRO")
    private Character creadorMiembro;
    @Column(name = "COMENTARIOS")
    private String comentarios;
    @Lob
    @Column(name = "IMAGEN")
    private Serializable imagen;
    @Column(name = "MONTO")
    private BigInteger monto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    private List<Miembroxfactura> miembroxfacturaList;

    public Factura() {
    }

    public Factura(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public Factura(Integer facturaId, String nombre, long costo, Date fecha, String hora, Character creadorMiembro) {
        this.facturaId = facturaId;
        this.nombre = nombre;
        this.costo = costo;
        this.fecha = fecha;
        this.hora = hora;
        this.creadorMiembro = creadorMiembro;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCosto() {
        return costo;
    }

    public void setCosto(long costo) {
        this.costo = costo;
    }

    public Short getComprobante() {
        return comprobante;
    }

    public void setComprobante(Short comprobante) {
        this.comprobante = comprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Character getCreadorMiembro() {
        return creadorMiembro;
    }

    public void setCreadorMiembro(Character creadorMiembro) {
        this.creadorMiembro = creadorMiembro;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Serializable getImagen() {
        return imagen;
    }

    public void setImagen(Serializable imagen) {
        this.imagen = imagen;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    @XmlTransient
    public List<Miembroxfactura> getMiembroxfacturaList() {
        return miembroxfacturaList;
    }

    public void setMiembroxfacturaList(List<Miembroxfactura> miembroxfacturaList) {
        this.miembroxfacturaList = miembroxfacturaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facturaId != null ? facturaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.facturaId == null && other.facturaId != null) || (this.facturaId != null && !this.facturaId.equals(other.facturaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Factura[ facturaId=" + facturaId + " ]";
    }
    
}
