/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.JTextArea;
import modelo.vo.Avistamientos;
import modelo.vo.Pajaros;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author catenaria
 */
public class AvistamientosDAO {

    public void borrarAvis(Session session, Pajaros p) {
        String consulta="from Avistamientos a where a.pajaroId = :bi";
        Query q=session.createQuery(consulta);
        q.setParameter("bi", p);
        Iterator it=q.list().iterator();
         Avistamientos a;
         while(it.hasNext()){
             a = (Avistamientos) it.next();
             session.delete(a);
         }
    }

    public void crearAvistamiento(Session session, Avistamientos a) {
        session.save(a);
    }

    public void cargarListA(Session session, JTextArea tabla, Usuarios b, Pajaros p, String loc) {
        tabla.setText("");
        
        
        String consulta="from Avistamientos a where a.pajaroId = :p, a.usuarioId = :b, a.lugar = :loc";
        Query q=session.createQuery(consulta); 
        
        q.setParameter("p", p);
        q.setParameter("b", b);
        q.setParameter("loc", loc);
        
        Iterator it=q.list().iterator();
        Object[] rowData;
        Avistamientos a;
        while(it.hasNext()){
            a=(Avistamientos) it.next();
            tabla.append(a.getFechaAvistamiento()+"\n");
        }
    }
    
}
