/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import controllers.UsuarioJpaController;
import entities.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author willi
 */
public class PROYECTOBASESFINAL {

    public static UsuarioJpaController controlador;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PROYECTO_BASES_FINALPU");
        UsuarioJpaController controlador = new UsuarioJpaController(emf);
        
        String nickname = null,Password = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Ya tenemos el "lector"

        mostrarTabla(controlador);

        System.err.print("INGRSE NICK: ");
        nickname = br.readLine();
        System.err.print("INGRSE Clave: ");
        Password=br.readLine();
        System.out.println(Login(nickname, Password, controlador));
        
        
        
    }
    
    public static String Login(String nickname,String Password,UsuarioJpaController controlador)
    {
        String retorno="Error en la contraseña o en el nombre de usuario.\n\t\t Intente de nuevo\n";
        List<Usuario> findUsuarioEntities;
    findUsuarioEntities = controlador.findUsuarioEntities();

     for (Usuario usuario : findUsuarioEntities) {
           // System.out.println(usuario.getNombre().trim()+"--->");
            if(usuario.getNombre().trim().compareTo(nickname.trim())==0)
            {
                //System.out.println("aaaa1");
                if(usuario.getUsuarioPaypal().compareTo(Password)==0)               //CAMBIAR PoR CLAVE
                {
                    //System.out.println("aaaa2");
                    return "EXITO!!";
                }
            }
         }
     return retorno;
    }

    private static void mostrarTabla(UsuarioJpaController controlador) {
       
        List<Usuario> findUsuarioEntities;
    findUsuarioEntities = controlador.findUsuarioEntities();
        for (Usuario usuario : findUsuarioEntities) {
            System.out.print(usuario.getNombre());
            System.out.print("\t"+usuario.getUsuarioPaypal()+"\n");
        }
           
    }
    
}
