package interfaz;



import javax.swing.JFrame;

import javax.swing.JLabel;
import java.awt.Font;


public class menu {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanalogin window = new ventanalogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MENU");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 32));
		lblNewLabel.setBounds(128, 11, 183, 36);
		frame.getContentPane().add(lblNewLabel);
	}
        
           /* public static String Login(String nickname,String Password,UsuarioJpaController controlador)
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
    }*/
}
