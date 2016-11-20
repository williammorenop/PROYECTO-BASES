/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JAIRINHO
 */
@Entity
@Table(name = "MIEMBRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miembro.findAll", query = "SELECT m FROM Miembro m"),
    @NamedQuery(name = "Miembro.findByEstado", query = "SELECT m FROM Miembro m WHERE m.estado = :estado"),
    @NamedQuery(name = "Miembro.findByMiembroId", query = "SELECT m FROM Miembro m WHERE m.miembroId = :miembroId"),
    @NamedQuery(name = "Miembro.findByMonto", query = "SELECT m FROM Miembro m WHERE m.monto = :monto"),
    @NamedQuery(name = "Miembro.findByTipo", query = "SELECT m FROM Miembro m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Miembro.findByMonto1", query = "SELECT m FROM Miembro m WHERE m.monto1 = :monto1"),
    @NamedQuery(name = "Miembro.findByAfectadoId", query = "SELECT m FROM Miembro m WHERE m.afectadoId = :afectadoId")})
public class Miembro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private short estado;
    @Id
    @Basic(optional = false)
    @Column(name = "MIEMBRO_ID")
    private Short miembroId;
    @Column(name = "MONTO")
    private BigInteger monto;
    @Column(name = "TIPO")
    private Short tipo;
    @Column(name = "MONTO1")
    private Long monto1;
    @Column(name = "AFECTADO_ID")
    private Short afectadoId;
    @JoinColumn(name = "USUARIO_NICK_NAME", referencedColumnName = "NICK_NAME")
    @ManyToOne(optional = false)
    private Usuario usuarioNickName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "miembro")
    private List<Miembroxtransaccion> miembroxtransaccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "miembro")
    private List<Miembroxfactura> miembroxfacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "miembro")
    private List<Historial> historialList;

    public Miembro() {
    }

    public Miembro(Short miembroId) {
        this.miembroId = miembroId;
    }

    public Miembro(Short miembroId, short estado) {
        this.miembroId = miembroId;
        this.estado = estado;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public Short getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Short miembroId) {
        this.miembroId = miembroId;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
    }

    public Short getTipo() {
        return tipo;
    }

    public void setTipo(Short tipo) {
        this.tipo = tipo;
    }

    public Long getMonto1() {
        return monto1;
    }

    public void setMonto1(Long monto1) {
        this.monto1 = monto1;
    }

    public Short getAfectadoId() {
        return afectadoId;
    }

    public void setAfectadoId(Short afectadoId) {
        this.afectadoId = afectadoId;
    }

    public Usuario getUsuarioNickName() {
        return usuarioNickName;
    }

    public void setUsuarioNickName(Usuario usuarioNickName) {
        this.usuarioNickName = usuarioNickName;
    }

    @XmlTransient
    public List<Miembroxtransaccion> getMiembroxtransaccionList() {
        return miembroxtransaccionList;
    }

    public void setMiembroxtransaccionList(List<Miembroxtransaccion> miembroxtransaccionList) {
        this.miembroxtransaccionList = miembroxtransaccionList;
    }

    @XmlTransient
    public List<Miembroxfactura> getMiembroxfacturaList() {
        return miembroxfacturaList;
    }

    public void setMiembroxfacturaList(List<Miembroxfactura> miembroxfacturaList) {
        this.miembroxfacturaList = miembroxfacturaList;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miembroId != null ? miembroId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembro)) {
            return false;
        }
        Miembro other = (Miembro) object;
        if ((this.miembroId == null && other.miembroId != null) || (this.miembroId != null && !this.miembroId.equals(other.miembroId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Miembro[ miembroId=" + miembroId + " ]";
    }
    
}
