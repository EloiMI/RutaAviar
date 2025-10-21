/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.AvistamientosDAO;
import modelo.dao.PajarosDAO;
import modelo.dao.UsuariosDAO;
import modelo.vo.Pajaros;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import vista.MenuGestion;
import vista.Principal;

/**
 *
 * @author catenaria
 */
public class controladorMenus {
    
 public static Session session; 
//declara los objetos DAO
 public static AvistamientosDAO aviDAO;
 public static PajarosDAO pajDAO;
 public static UsuariosDAO usuDAO;
 public static Usuarios current;
 public static Principal ventanaLogin;
    static DefaultListModel<String> listUsers = new DefaultListModel<>();
    static DefaultTableModel tableUserHistory=new DefaultTableModel();
    static DefaultTableModel tableBirHistory=new DefaultTableModel();
 
 public static MenuGestion ventana = new MenuGestion();
  public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getUserList().setModel(listUsers);
        tableUserHistory=(DefaultTableModel)ventana.getTblUserHistory().getModel();
        tableBirHistory=(DefaultTableModel) ventana.getTablaBirdList().getModel();
        cargarUsuarios();
        cargarPajaros();
    }
  
    public static void cargarUsuarios(){
        usuDAO.cargarUsuariosList(session, listUsers);
    }
    
    public static void cargarPajaros(){
        pajDAO.cargarBirList(session, tableBirHistory);
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
    
    public static void Enter(Usuarios usu, Principal venLog) {
        current=usu;
        ventanaLogin=venLog;
        iniciar();
        ventana.setTitle("Sesión de "+current.getNombre());
    }
    
    public static void Logout(){
        cerrarSession();
        ventana.dispose();
        ventanaLogin.dispose();
    }

    public static void addUser() {
        String strung=new String(ventana.getTxtUserPass().getPassword());
        if(ventana.getTxtUserNom().getText().isEmpty() || strung.isEmpty()){
            JOptionPane.showMessageDialog(null, "Faltan datos.");
            return;
        }
        try{
        HibernateUtil.beginTx(session);
        boolean b=usuDAO.comprobarUsuario(session, ventana.getTxtUserNom().getText());
        if(b){
            JOptionPane.showMessageDialog(null, "El usuario ya existe.");
            HibernateUtil.rollbackTx(session);
        }else{
            strung = BCrypt.hashpw(strung, BCrypt.gensalt());
            Usuarios u=new Usuarios(ventana.getTxtUserNom().getText(), strung, ventana.getCheckAdmin().isSelected());
            
            usuDAO.crearUsuario(session, u);
            VaciarUser();
            ventana.getTxtUserNom().setText("");
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(null, "Usuario creado.");
            cargarUsuarios();
            
        }
        }catch(Exception e){
            HibernateUtil.rollbackTx(session);
        }
    }

    public static void deleteUser() {
        if(ventana.getTxtUserNom().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Introduce un usuario.");
            return;
        }     
        try{
        HibernateUtil.beginTx(session);
        Usuarios b=usuDAO.consultarUsuario(session, ventana.getTxtUserNom().getText());
        if(b!=null){
            if(b.getId()==1 || b.getId()==2){
                JOptionPane.showMessageDialog(null, "No se puede borrar.");
            }else{
                if(b.getId()==current.getId()){
                    usuDAO.borrarUsuario(session, b);
                    HibernateUtil.commitTx(session);
                    Logout();
                    return;
                }else{
                    usuDAO.borrarUsuario(session, b);
                    JOptionPane.showMessageDialog(null, "Usuario borrado.");
                    HibernateUtil.commitTx(session);
                    cargarUsuarios();
                    VaciarUser();
                    ventana.getTxtUserNom().setText("");
                    return;
                }
            }            
        }else{
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
            HibernateUtil.rollbackTx(session);
        }
        }catch(Exception e){
            HibernateUtil.rollbackTx(session);
        }
    }

    public static void modUser() {
        String strung=new String(ventana.getTxtUserPass().getPassword());
        
        if(ventana.getTxtUserNom().getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Faltan datos.");
        }
        
        try{
        HibernateUtil.beginTx(session);

        Usuarios b=usuDAO.consultarUsuario(session, ventana.getTxtUserNom().getText());
        if(b!=null){
            if(b.getId()==1 ){
                JOptionPane.showMessageDialog(null, "No se puede modificar.");
                HibernateUtil.rollbackTx(session);
                return;
            }else{
                if(!strung.isEmpty()){
                    strung = BCrypt.hashpw(strung, BCrypt.gensalt());
                     b.setPassword(strung);
                }
                 b.setAdmin(ventana.getCheckAdmin().isSelected());
                
                if(b.getId()==current.getId() && b.getAdmin()==false){
                    usuDAO.modificarUsuario(session, b);
                    HibernateUtil.commitTx(session);
                    Logout();
                    return;
                }else{
                    usuDAO.modificarUsuario(session, b);
                    JOptionPane.showMessageDialog(null, "Usuario modificado.");
                    HibernateUtil.commitTx(session);
                    cargarUsuarios();
                    return;
                }
            }            
        }else{
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
            HibernateUtil.rollbackTx(session);
        }
        }catch(Exception e){
            HibernateUtil.rollbackTx(session);
        }
    }

    public static void cargaUserHistorial() {
        String username=ventana.getUserList().getSelectedValue();
        Usuarios u=usuDAO.consultarUsuario(session, username);
        usuDAO.cargaUserHist(session, u, tableUserHistory);
    }

    public static void borrarHistorial() {
        String username=ventana.getUserList().getSelectedValue();
        Usuarios u=usuDAO.consultarUsuario(session, username);
        try {
            HibernateUtil.beginTx(session);
            usuDAO.borrarHistorialUsuario(session, u, tableUserHistory);
            HibernateUtil.commitTx(session);
        } catch (Exception e) {
            HibernateUtil.rollbackTx(session);
        }
    }

    public static void VaciarUser() {
        ventana.getTxtUserPass().setText("");
        ventana.getCheckAdmin().setSelected(false);
    }
    
    public static void VaciarBir() {
        ventana.getTxtUserNom().setText("");
        ventana.getTxtBirdSCName().setText("");
    }

    public static void addBird() {
        if(ventana.getTxtBirdCommonName().getText().isEmpty() ||ventana.getTxtBirdSCName().getText().isEmpty() ){
            JOptionPane.showMessageDialog(null, "Faltan datos.");
            return;
        }
        try{
        HibernateUtil.beginTx(session);
        boolean b=pajDAO.comprobarPajaro(session, ventana.getTxtBirdSCName().getText());
        if(b){
            JOptionPane.showMessageDialog(null, "Pajaro ya registrado.");
            HibernateUtil.rollbackTx(session);
        }else{
            Pajaros p=new Pajaros(ventana.getTxtBirdCommonName().getText(), ventana.getTxtBirdSCName().getText());
            
            pajDAO.crearPajaro(session, p);
            VaciarBir();
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(null, "Pájaro añadido.");
            cargarPajaros();
        }
        }catch(Exception e){
            HibernateUtil.rollbackTx(session);
        }
    }
    
    private static int IdMod=0;

    public static void cargaB() {
        int selectedRow = ventana.getTablaBirdList().getSelectedRow();

        if (selectedRow == -1) {
            return;
        }
        int modelRow = ventana.getTablaBirdList().convertRowIndexToModel(selectedRow);

        ventana.getTxtBirdCommonName().setText((String) tableBirHistory.getValueAt(modelRow, 1));
        ventana.getTxtBirdSCName().setText((String) tableBirHistory.getValueAt(modelRow, 2));
        IdMod=Integer.valueOf(tableBirHistory.getValueAt(modelRow, 0).toString());
    }

    public static void modBird() {
        if(IdMod<1){
            JOptionPane.showMessageDialog(null, "Selecciona un pájaro de la tabla.");
            return;
        }        
        try{
            HibernateUtil.beginTx(session);

            Pajaros p=pajDAO.buscarPajaro(session, IdMod);

            if(!ventana.getTxtBirdCommonName().getText().isEmpty())
            {
                p.setNombre(ventana.getTxtBirdCommonName().getText());
            }

            if(!ventana.getTxtBirdSCName().getText().isEmpty())
            {
                p.setNombre(ventana.getTxtBirdSCName().getText());
            }
            pajDAO.modBird(session, p);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(null, "Pájaro modificado.");
            cargarPajaros();
            IdMod=0;
        }catch(Exception e){
            HibernateUtil.rollbackTx(session);
        }
    }

    public static void borBird() {
        if(IdMod<1){
            JOptionPane.showMessageDialog(null, "Selecciona un pájaro de la tabla.");
            return;
        }        
        try{
            HibernateUtil.beginTx(session);

            Pajaros p=pajDAO.buscarPajaro(session, IdMod);
            aviDAO.borrarAvis(session, p);
            pajDAO.borBird(session, p);
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(null, "Pájaro borrado.");
            cargarPajaros();
            IdMod=0;
        }catch(Exception e){
            HibernateUtil.rollbackTx(session);
        }
    }

}
