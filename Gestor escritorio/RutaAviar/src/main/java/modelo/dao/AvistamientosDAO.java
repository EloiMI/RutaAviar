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

    public void crearAvistamiento(Session session, Avistamientos a, JTextArea tableSightHistory) {
        session.save(a);
    }

    public void cargarListA(Session session, JTextArea tabla, Usuarios b, Pajaros p, String loc) {
        tabla.setText("");
        
        
        String consulta="from Avistamientos a where a.pajaroId = :p and  a.usuarioId = :b and  a.lugar = :loc";
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
    
    

    public void cargarListU(Session session, JTextArea tabla, Usuarios b) {
        tabla.setText("");        
        
        String consulta="from Avistamientos a where a.usuarioId = :b";
        Query q=session.createQuery(consulta); 
        
        q.setParameter("b", b);
        
        Iterator it=q.list().iterator();
        Object[] rowData;
        Avistamientos a;
        while(it.hasNext()){
            a=(Avistamientos) it.next();
            tabla.append("Fecha: "+a.getFechaAvistamiento()+"----Pajaro: "+a.getPajaroId().getNombre()+"\n");
        }
    }
    
    
    public void cargarListP(Session session, JTextArea tabla, Pajaros p) {
        tabla.setText("");        
        
        String consulta="SELECT from Avistamientos a where a.pajaroId = :p";
        //ST_X(a.lugar) AS lon, ST_Y(a.lugar) AS lat
        Query q=session.createQuery(consulta); 
        
        q.setParameter("p", p);
        
        Iterator it=q.list().iterator();
        Object[] rowData;
        Avistamientos a;
        while(it.hasNext()){
            a=(Avistamientos) it.next();
            tabla.append("Fecha: "+a.getFechaAvistamiento()+"----Usuario: "+a.getUsuarioId().getNombre()+"\n");
        }
    }
    
}
