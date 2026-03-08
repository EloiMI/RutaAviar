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



public class controladorPrincipal {
 
 public static Session session; 
//declara los objetos DAO
 public static AvistamientosDAO aviDAO;
 public static PajarosDAO pajDAO;
 public static UsuariosDAO usuDAO;
   

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
}
