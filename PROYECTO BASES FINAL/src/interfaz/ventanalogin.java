package interfaz;



import controller.UsuarioJpaController;
import entities.Usuario;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ventanalogin {

	public JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

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
	public ventanalogin() {
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
		
		textField = new JTextField();
		textField.setBounds(54, 90, 140, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("HOLA !!");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 32));
		lblNewLabel.setBounds(128, 11, 183, 36);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(54, 66, 114, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblClave = new JLabel("Clave");
		lblClave.setBounds(54, 125, 46, 14);
		frame.getContentPane().add(lblClave);
		
		JButton btnNewButton = new JButton("ENTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
                           EntityManagerFactory emf = Persistence.createEntityManagerFactory("entrega_3PU");
        UsuarioJpaController controlador = new UsuarioJpaController(emf);
        
                            System.out.println(textField.getText());
                           System.out.println(passwordField.getText());
                           
                         
                        
                            System.out.println(Login(textField.getText(), passwordField.getText(), controlador));
                            
                            if(Login(textField.getText().trim(), passwordField.getText().trim(), controlador).compareTo("EXITO!!")==0)
                            {
                                System.out.println("aaaaaaa");
                                JOptionPane.showMessageDialog(null, "EXITO!!","Inicio de sesion exitoso.",JOptionPane.INFORMATION_MESSAGE);
                                menu ventanamenu= new menu();
           			ventanamenu.frame.setVisible(true);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "ERROR!!","Errpr al iniciar sesion.",JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }
		});
		btnNewButton.setBounds(253, 77, 127, 46);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblIncorrecto = new JLabel("INCORRECTO!!");
		lblIncorrecto.setEnabled(false);
		lblIncorrecto.setForeground(Color.RED);
		lblIncorrecto.setBounds(265, 153, 115, 14);
		frame.getContentPane().add(lblIncorrecto);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('w');
		passwordField.setBounds(54, 160, 140, 20);
		frame.getContentPane().add(passwordField);
	}
        
               public static String Login(String nickname,String Password,UsuarioJpaController controlador)
    {
        String retorno;
            retorno = "Error en la contraseña o en el nombre de usuario.\n\t\t Intente de nuevo\n";
        
    
           Usuario usuario=null;
                  usuario= controlador.findUsuario(nickname);

     
            if(usuario!=null)
            {
                //System.out.println("aaaa1");
                if(usuario.getClave().compareTo(Password)==0)               //CAMBIAR PoR CLAVE
                {
                    //System.out.println("aaaa2");
                    return "EXITO!!";
                }
            }
         
     return retorno;
    }
}
