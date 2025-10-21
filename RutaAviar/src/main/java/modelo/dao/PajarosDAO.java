/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import modelo.vo.Pajaros;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author catenaria
 */
public class PajarosDAO {

    public Pajaros buscarPajaro(Session session, int id) {
        String consulta="from Pajaros p where p.id= :Id";
        Query q=session.createQuery(consulta);
        q.setParameter("Id", id);
        
        Iterator it=q.list().iterator();
        Pajaros p;
        if(it.hasNext()){
            p=(Pajaros) it.next();
            return p;
        }
        return null;     
    }

    public boolean comprobarPajaro(Session session, String sctf) {
        String consulta="from Pajaros p where p.raza=:n2";
        Query q=session.createQuery(consulta); 
        q.setParameter("n2", sctf);
        
        Iterator it=q.list().iterator();
        
        if(it.hasNext()){
            return true;
        }
        return false;     
    }

    public void crearPajaro(Session session, Pajaros p) {
        session.save(p);
    }

    public void cargarBirList(Session session, DefaultTableModel table) {
        table.setRowCount(0);
        String consulta="from Pajaros p";
        Query q=session.createQuery(consulta); 
        
        Iterator it=q.list().iterator();
        Object[] rowData;
        Pajaros p;
        while(it.hasNext()){
            p=(Pajaros) it.next();
            rowData = new Object[]{ p.getId(), p.getNombre(), p.getRaza(), p.getAvistamientosList().size()};
            table.addRow(rowData);
        }
    }

    public void modBird(Session s, Pajaros p) {
        s.update(p);
    }

    public void borBird(Session session, Pajaros p) {
        session.delete(p);
    }
    
}
