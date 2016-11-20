/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JAIRINHO
 */
@Entity
@Table(name = "TRANSACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByTransaccionId", query = "SELECT t FROM Transaccion t WHERE t.transaccionId = :transaccionId"),
    @NamedQuery(name = "Transaccion.findByFecha", query = "SELECT t FROM Transaccion t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Transaccion.findByHora", query = "SELECT t FROM Transaccion t WHERE t.hora = :hora"),
    @NamedQuery(name = "Transaccion.findByDescription", query = "SELECT t FROM Transaccion t WHERE t.description = :description"),
    @NamedQuery(name = "Transaccion.findByAprobado", query = "SELECT t FROM Transaccion t WHERE t.aprobado = :aprobado"),
    @NamedQuery(name = "Transaccion.findByTipo", query = "SELECT t FROM Transaccion t WHERE t.tipo = :tipo"),
    @NamedQuery(name = "Transaccion.findByMonto", query = "SELECT t FROM Transaccion t WHERE t.monto = :monto"),
    @NamedQuery(name = "Transaccion.findByAfectadoId", query = "SELECT t FROM Transaccion t WHERE t.afectadoId = :afectadoId")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TRANSACCION_ID")
    private Integer transaccionId;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "APROBADO")
    private short aprobado;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private short tipo;
    @Basic(optional = false)
    @Column(name = "MONTO")
    private long monto;
    @Basic(optional = false)
    @Column(name = "AFECTADO_ID")
    private short afectadoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaccion")
    private List<Miembroxtransaccion> miembroxtransaccionList;

    public Transaccion() {
    }

    public Transaccion(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Transaccion(Integer transaccionId, Date fecha, Date hora, short aprobado, short tipo, long monto, short afectadoId) {
        this.transaccionId = transaccionId;
        this.fecha = fecha;
        this.hora = hora;
        this.aprobado = aprobado;
        this.tipo = tipo;
        this.monto = monto;
        this.afectadoId = afectadoId;
    }

    public Integer getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Integer transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getAprobado() {
        return aprobado;
    }

    public void setAprobado(short aprobado) {
        this.aprobado = aprobado;
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

    @XmlTransient
    public List<Miembroxtransaccion> getMiembroxtransaccionList() {
        return miembroxtransaccionList;
    }

    public void setMiembroxtransaccionList(List<Miembroxtransaccion> miembroxtransaccionList) {
        this.miembroxtransaccionList = miembroxtransaccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionId != null ? transaccionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.transaccionId == null && other.transaccionId != null) || (this.transaccionId != null && !this.transaccionId.equals(other.transaccionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transaccion[ transaccionId=" + transaccionId + " ]";
    }
    
}
