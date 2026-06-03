/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starter;

import controlador.controladorPrincipal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import modelo.dao.UsuariosDAO;
import modelo.vo.AvistamientoDTO;
import modelo.vo.Avistamientos;
import modelo.vo.AvistamientosList;
import modelo.vo.AvistamientosUser;
import modelo.vo.Pajaros;
import modelo.vo.Usuarios;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
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
    private static final String URL = "jdbc:mysql://192.168.1.135:3306/avistamientosdb"; //ip maquina virtual 
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
    
   
    public static String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
    
    
    //hace GET de un usuario
    @PostMapping("/login")
    public ResponseEntity<Usuarios> login(@RequestBody Usuarios user) {
        String sql = "SELECT id, nombre, password, admin FROM usuarios WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNombre());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String inputHash = sha256(user.getPassword());
                    String dbHash = rs.getString("password");
                    if (inputHash.equals(dbHash)) {
                        Usuarios u = new Usuarios(rs.getInt("id"),rs.getString("nombre"),null, rs.getBoolean("admin"));
                        return ResponseEntity.ok(u);
                    }
                    return ResponseEntity.status(401).build();
                }
                return ResponseEntity.status(404).build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    
     @GetMapping("/aves")
    public List <Pajaros> getPajaros() {
        List <Pajaros> ps=new ArrayList<>();   
        String selectSQL = "SELECT id, nombre, raza FROM pajaros";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                ps.add(new Pajaros(rs.getInt("id"), rs.getString("nombre"), rs.getString("raza")));
            }
            //System.out.println(ps.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    public Boolean checkUser(String username){
        String sql = "SELECT id, nombre, password, admin FROM usuarios WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }else{
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    //crear usuarios
    @PostMapping("/signup")
    public Usuarios crearUsuario(@RequestBody Usuarios usuario) {
        Boolean check=checkUser(usuario.getNombre());
        
        if(!check){
            String query ="INSERT INTO usuarios(nombre, password, admin) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, usuario.getNombre());
                stmt.setString(2, usuario.getPassword());
                stmt.setBoolean(3, usuario.getAdmin());

                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getInt(1));
                    }
                }

                return usuario;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
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
    
    */
    
    
    //avistamientos pajaros
    @GetMapping("/avistamientosB")
    public List<AvistamientosList> checkBirdSightings(@RequestParam int pjid) {
        List<AvistamientosList> ps = new ArrayList<>();
        String selectSQL ="SELECT pajaro_id, usuario_id, fecha_avistamiento, ST_X(lugar) AS lon, ST_Y(lugar) AS lat  FROM avistamientos WHERE pajaro_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, pjid);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ps.add(new AvistamientosList(rs.getInt("pajaro_id"), rs.getInt("usuario_id"),rs.getDate("fecha_avistamiento"), rs.getDouble("lon"),rs.getDouble("lat")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }
    
    //avistamientos usuarios
    @GetMapping("/avistamientosU")
    public List<AvistamientosUser> checkUserSightings(@RequestParam int usid) {
        List<AvistamientosUser> ps = new ArrayList<>();
        String selectSQL ="SELECT a.id, ST_X(a.lugar) AS lon, ST_Y(a.lugar) AS lat, a.fecha_avistamiento, p.id AS pajaro_id, p.nombre, p.raza FROM avistamientos a "
                + "JOIN pajaros p ON a.pajaro_id = p.id WHERE usuario_id = ? ORDER BY fecha_avistamiento DESC";
        
        try (PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
            stmt.setInt(1, usid);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ps.add(new AvistamientosUser(rs.getString("nombre"),rs.getString("raza"),rs.getDate("fecha_avistamiento"),rs.getDouble("lat"), rs.getDouble("lon")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ps;
    }

    @PostMapping("/regavistamientos")  //crear avistamiento
    public ResponseEntity<String> createUserSighting(@RequestBody AvistamientoDTO av) {

        String sql = "INSERT INTO avistamientos (lugar, fecha_avistamiento, usuario_id, pajaro_id) VALUES (ST_GeomFromText(?), ?, ?, ?)";
        String point = "POINT(" + av.getLon() + " " + av.getLat() + ")";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, point);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, av.getPajaroId());
            stmt.setInt(4, av.getUsuarioId());

            stmt.executeUpdate();

            return ResponseEntity.ok("Insertado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al insertar");
        }
    }
    
}
