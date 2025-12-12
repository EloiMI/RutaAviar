/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.swing.JOptionPane;
import modelo.dao.AvistamientosDAO;
import modelo.dao.PajarosDAO;
import modelo.dao.UsuariosDAO;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import vista.Principal;

/**
 *
 * @author acceso a datos
 */
public class controladorPrincipal {
 
 public static Session session; 
//declara los objetos DAO
 public static AvistamientosDAO aviDAO;
 public static PajarosDAO pajDAO;
 public static UsuariosDAO usuDAO;
    
 public static Principal ventana = new Principal();
  public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciaSession() {
        session=HibernateUtil.getSessionFactory().openSession();
        //crear los objetos DAO  
        aviDAO=HibernateUtil.getAvistamientosDAO();
        pajDAO=HibernateUtil.getPajarosDAO();
        usuDAO=HibernateUtil.getUsuariosDAO();
    }

    public static void cerrarSession() {
        session.close();       
    }    
    
    public static void EnterGestor(Usuarios usu){
        ventana.setVisible(false);
        controladorMenus.Enter(usu, ventana);
    }
    
    public static void logUsuario(){
        Usuarios log=usuDAO.consultarUsuario(session, ventana.getTxtUser().getText());
        
        String password = new String(ventana.getTxtPass().getPassword());
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        if(log!=null){            
                if (true) { 
                    if(true){
                        ventana.getTxtUser().setText("");
                        ventana.getTxtPass().setText("");
                        EnterGestor(log);
                        return;
                    }else{
                        JOptionPane.showMessageDialog(null, "Solo se puede acceder con una cuenta de administrador.");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
                    return;
                }
        }else{
            JOptionPane.showMessageDialog(null, "No existe el usuario.");
            return;
        }  
    }
   
}
