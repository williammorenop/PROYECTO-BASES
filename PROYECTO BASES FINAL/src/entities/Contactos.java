/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JAIRINHO
 */
@Entity
@Table(name = "CONTACTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contactos.findAll", query = "SELECT c FROM Contactos c"),
    @NamedQuery(name = "Contactos.findByUsuarioNickName", query = "SELECT c FROM Contactos c WHERE c.contactosPK.usuarioNickName = :usuarioNickName"),
    @NamedQuery(name = "Contactos.findByUsuarioNickName1", query = "SELECT c FROM Contactos c WHERE c.contactosPK.usuarioNickName1 = :usuarioNickName1")})
public class Contactos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContactosPK contactosPK;

    public Contactos() {
    }

    public Contactos(ContactosPK contactosPK) {
        this.contactosPK = contactosPK;
    }

    public Contactos(short usuarioNickName, short usuarioNickName1) {
        this.contactosPK = new ContactosPK(usuarioNickName, usuarioNickName1);
    }

    public ContactosPK getContactosPK() {
        return contactosPK;
    }

    public void setContactosPK(ContactosPK contactosPK) {
        this.contactosPK = contactosPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contactosPK != null ? contactosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contactos)) {
            return false;
        }
        Contactos other = (Contactos) object;
        if ((this.contactosPK == null && other.contactosPK != null) || (this.contactosPK != null && !this.contactosPK.equals(other.contactosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contactos[ contactosPK=" + contactosPK + " ]";
    }
    
}
