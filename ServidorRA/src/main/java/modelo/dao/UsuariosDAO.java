/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Avistamientos;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class UsuariosDAO {

    public Usuarios consultarUsuario(Session session, String nombre) {
        //String consulta="select count(*) from Coches c right join c.reparacionesList r where c.codcli=:cod and r.fechaf is null group by c.codcli";
        String consulta="from Usuarios u where u.nombre = :nom";
        Query q=session.createQuery(consulta);
        q.setParameter("nom", nombre);
        
        Iterator it=q.list().iterator();
        Usuarios u;
        if(it.hasNext()){
            u=(Usuarios) it.next();
            return u;
        }
        return null;        
    }

    public boolean comprobarUsuario(Session session, String text) {
        String consulta="from Usuarios u where u.nombre = :nom";
        Query q=session.createQuery(consulta);
        q.setParameter("nom", text);
        
        Iterator it=q.list().iterator();
        
        if(it.hasNext()){
            return true;
        }
        return false;       
    }

    public void crearUsuario(Session session, Usuarios u) {
        session.save(u);
    }
    

    public static List<Usuarios> cargarUsuariosList(Session session, List <Usuarios> usuarios) {
        usuarios.clear();
        String consulta="from Usuarios u";
        Query q=session.createQuery(consulta);
        
        Iterator it=q.list().iterator();
        Usuarios u;
        while(it.hasNext()){
            u=(Usuarios) it.next();
            usuarios.add(u);
        }
        return usuarios;
    }

    public void borrarUsuario(Session session, Usuarios u) {
        session.delete(u);
    }

    public void modificarUsuario(Session session, Usuarios b) {
         session.update(b);
    }

    public void cargaUserHist(Session session, Usuarios u, DefaultTableModel table) {
        table.setRowCount(0);
        String consulta="select p.nombre, a.lugar, a.fechaAvistamiento from Avistamientos a inner join a.pajaroId p where a.usuarioId = :user";
        Query q=session.createQuery(consulta);
        q.setParameter("user", u);
       
        Iterator it=q.list().iterator();
        Object[] rowData;
        while(it.hasNext()){
            rowData = (Object[]) it.next();
            table.addRow(rowData);
        }
        
    }

    public void borrarHistorialUsuario(Session session, Usuarios u, DefaultTableModel table) {
        table.setRowCount(0);
        String consulta="from Avistamientos a where a.usuarioId = :user";
        Query q=session.createQuery(consulta);
        q.setParameter("user", u);
        Iterator it=q.list().iterator();
         Avistamientos a;
         while(it.hasNext()){
             a = (Avistamientos) it.next();
             session.delete(a);
         }
    }

    public void cargarUsuariosListCombo(Session session, DefaultComboBoxModel cmbUser) {
        cmbUser.removeAllElements();
        String consulta="from Usuarios u";
        Query q=session.createQuery(consulta);
        Iterator it=q.list().iterator();
        Usuarios us;
        while(it.hasNext()){
            us=(Usuarios) it.next();
            cmbUser.addElement(us.getNombre());
        }
    }
    
}
