/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starter;

import controlador.controladorPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelo.dao.UsuariosDAO;
import modelo.vo.Avistamientos;
import modelo.vo.Pajaros;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/db")
public class ControlPrincipal {
    private static final String URL = "jdbc:mysql://192.168.1.131:3306/avistamientosdb"; 
    private static final String USER = "root";
    private static final String PASS = "root";
    Connection conn ;
     public static Session session; 
     
     /*
     //check conexion
     public static void mainj(String[] args) {
        
         try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://192.168.1.131:3306/avistamientosdb?zeroDateTimeBehavior=CONVERT_TO_NULL", 
                "root", "root")) {
            System.out.println("Database connected!");
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
     

    public ControlPrincipal() {
        try {
            conn= DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    //hace GET en el protocolo HTTP: devuelve el array.
    @GetMapping
    public List<Usuarios> getUsuarios() {
        List <Usuarios> usuarios=new ArrayList<>();   
        
        String selectSQL = "SELECT id, nombre, password, admin FROM usuarios";
        try ( Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                usuarios.add(new Usuarios( rs.getInt("id"),rs.getString("nombre"),rs.getString("password"),rs.getBoolean("admin")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Collections.sort(usuarios,new Comparator<Usuarios>() {
            @Override
           public int compare(Usuarios s1, Usuarios s2) {
                   return s1.compareTo(s2);
           }
        });
        return usuarios;
    }
    
   
    
    //hace GET con un id fijo
     @GetMapping("/{login}")
    public Usuarios getUsuarios(@RequestParam String nombre) {
        Usuarios a = new Usuarios(-1,"","", true);
        String selectSQL = "SELECT id, nombre, password, admin FROM usuarios WHERE nombre LIKE '" + nombre + "'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            if (rs.next()) {
                a = new Usuarios(rs.getInt("id"), rs.getString("nombre"), rs.getString("password"), rs.getBoolean("admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!a.getNombre().isEmpty()) return a;
        return null;
    }
    
     @GetMapping("/{aves}")
    public List <Pajaros> getPajaros() {
        List <Pajaros> ps=new ArrayList<>();   
        String selectSQL = "SELECT id, nombre, raza FROM pajaros";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            if (rs.next()) {
                ps.add(new Pajaros(rs.getInt("id"), rs.getString("nombre"), rs.getString("raza")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    //avistamientos
    /*
     @GetMapping("/{avistamientosPajaro}")
    public Usuarios checkBirdSightings(@RequestParam int idp) {
        List <Avistamientos> ps=new ArrayList<>();   
        String selectSQL = "SELECT id, lugar, fechaAvistamiento, pajaroId, usuarioId FROM avistamientos where PajaroId ="+idp;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            if (rs.next()) {
                ps.add(new Avistamientos(rs.getInt("id"), rs.getString("lugar"), rs.getDate("fechaAvistamiento"), rs.getInt("pajaroId"), rs.getInt("usuarioId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!a.getNombre().isEmpty()) return a;
        return null;
    }
    
     @GetMapping("/{avistamientosUsuario}")
    public Usuarios checkUserSightings(@RequestParam int idu) {
        List <Avistamientos> ps=new ArrayList<>();   
        String selectSQL = "SELECT id, lugar, fechaAvistamiento, pajaroId, usuarioId FROM avistamientos where PajaroId ="+idu;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            if (rs.next()) {
                ps.add(new Avistamientos(rs.getInt("id"), rs.getString("lugar"), rs.getDate("fechaAvistamiento"), rs.getInt("pajaroId"), rs.getInt("usuarioId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!a.getNombre().isEmpty()) return a;
        return null;
    }
*/
}
