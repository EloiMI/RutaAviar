/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import modelo.vo.Avistamientos;
import modelo.vo.Pajaros;
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
    
}
