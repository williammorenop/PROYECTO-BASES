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
public class ContactosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "USUARIO_NICK_NAME")
    private short usuarioNickName;
    @Basic(optional = false)
    @Column(name = "USUARIO_NICK_NAME1")
    private short usuarioNickName1;

    public ContactosPK() {
    }

    public ContactosPK(short usuarioNickName, short usuarioNickName1) {
        this.usuarioNickName = usuarioNickName;
        this.usuarioNickName1 = usuarioNickName1;
    }

    public short getUsuarioNickName() {
        return usuarioNickName;
    }

    public void setUsuarioNickName(short usuarioNickName) {
        this.usuarioNickName = usuarioNickName;
    }

    public short getUsuarioNickName1() {
        return usuarioNickName1;
    }

    public void setUsuarioNickName1(short usuarioNickName1) {
        this.usuarioNickName1 = usuarioNickName1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuarioNickName;
        hash += (int) usuarioNickName1;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactosPK)) {
            return false;
        }
        ContactosPK other = (ContactosPK) object;
        if (this.usuarioNickName != other.usuarioNickName) {
            return false;
        }
        if (this.usuarioNickName1 != other.usuarioNickName1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ContactosPK[ usuarioNickName=" + usuarioNickName + ", usuarioNickName1=" + usuarioNickName1 + " ]";
    }
    
}
