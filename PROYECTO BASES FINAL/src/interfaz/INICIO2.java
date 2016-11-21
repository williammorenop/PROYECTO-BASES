package interfaz;

import controller.UsuarioJpaController;
import entities.Usuario;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class INICIO2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					INICIO2 frame = new INICIO2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public INICIO2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Ingresar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 41));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ventanalogin ventana= new ventanalogin();
				ventana.frame.setVisible(true);
                                
                        }
		});
		btnNewButton.setBounds(21, 220, 291, 124);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Registrarse");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 41));
		btnNewButton_1.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent e) {
				
                            
                                Usuario hola=new Usuario("pirdo", "hater", "brayan");
                                 EntityManagerFactory emf = Persistence.createEntityManagerFactory("entrega_3PU");
        UsuarioJpaController controlador = new UsuarioJpaController(emf);
        
                            try {
                                controlador.create(hola);
                            } catch (Exception ex) {
                                Logger.getLogger(INICIO2.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                                
				JOptionPane.showMessageDialog(null,"PROXIMAMENTE","Registrarse",JOptionPane.INFORMATION_MESSAGE);
				
                                
                                
                                
                                
                        
                        }
		});
		btnNewButton_1.setBounds(332, 220, 243, 124);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("BIENVENIDO!!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 43));
		lblNewLabel.setBounds(181, 73, 282, 72);
		contentPane.add(lblNewLabel);
	}
}
