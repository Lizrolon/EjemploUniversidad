
package EjemploUniversidad.AccesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import EjemploUniversidad.Entidades.Alumno;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class AlumnoData {
    private Connection con= null;

    public AlumnoData() {
        con = Conexion.getConexion();
    }
    
    public void guardarAlumno(Alumno alumno){
        
        String sql = "INSERT INTO alumno(dni,apellido,nombre,fechaNacimiento,estado)"
                + " VALUES(?,?,?,?,?)";
        
       
        
        try {
            
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNac()));
            ps.setBoolean(5, alumno.isActivo());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
         if(rs.next()){
             alumno.setIdAlumno(rs.getInt(1));
             JOptionPane.showMessageDialog(null, "Alumno agregado éxitosamente");
         
         
         }   
            
         ps.close();   
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Error al ingresar a la tabla Alumnos");
        }
      
    }
    
    public Alumno buscarAlumno(int id){
    Alumno alumno = null;
    
       String sql = "SELECT * FROM alumno WHERE estado = 1"; 
      PreparedStatement ps = null;
      
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
         ResultSet rs = ps.executeQuery();
         
         if(rs.next()){
              alumno = new Alumno();
              alumno.setIdAlumno(id);
              alumno.setDni(rs.getInt("dni"));
              alumno.setApellido(rs.getString("spellido"));
              alumno.setNombre(rs.getString("nombre"));
              alumno.setFechaNac(rs.getDate("fechanacimiento").toLocalDate());
              alumno.setActivo(true);
         
         }else{
         JOptionPane.showMessageDialog(null, "El alumno que busca no se encuentra en la Base de Datos");
         
         }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    return alumno;
    
    }
    public Alumno buscarAlumnoPorDni(int dni){
    Alumno alumno = null;
    String sql ="SELECT * FROM alumno WHERE dni=? AND estado = 1";
    
    PreparedStatement ps = null;
    
    try{
    
    ps = con.prepareStatement(sql);
    ps.setInt(1, dni);
    
    ResultSet rs = ps.executeQuery();
    
        if(rs.next()){
        alumno = new Alumno();
        alumno.setDni(dni);
        alumno.setIdAlumno(rs.getInt("idAlumno"));
        alumno.setDni(rs.getInt("dni"));
        alumno.setApellido(rs.getString("apellido"));
        alumno.setNombre(rs.getString("nombre"));
        alumno.setFechaNac(rs.getDate("fechanacimiento").toLocalDate());
        alumno.setActivo(true);
                }else{
        JOptionPane.showMessageDialog(null, "El número de dni indicado no coincide con ningún Alumno registrado");
        
        
        
        }
    ps.close();
    }catch(SQLException ex){
    JOptionPane.showMessageDialog(null, "Error al ingresar a la tabla Alumno");
    
    }
         return alumno;
    
    }
    
   public List<Alumno> listarAlumno(){
   
      List<Alumno> alumnos = new ArrayList<>();
      
      
        try {
             String sql = "SELECT * FROM alumno WHERE estado = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          
            while(rs.next()){
            Alumno alumno = new Alumno();
            alumno.setIdAlumno(rs.getInt("idAlumno"));
            alumno.setDni(rs.getInt("dni"));
            alumno.setApellido(rs.getString("apellido"));
            alumno.setNombre(rs.getString("nombre"));
            alumno.setFechaNac(rs.getDate("fechanacimiento").toLocalDate());
            alumno.setActivo(rs.getBoolean("estado"));
            alumnos.add(alumno);
            }
          ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ingresar a la tabla Alumno");
        }
      
       
      
       
   return alumnos;
   
   }
   public void modificarAlumno(Alumno alumno){
       
       String sql = "UPDATE alumno SET dni = ?, apellido = ?, nombre = ?, fechanacimiento = ?, estado = ? WHERE idAlumno = ?";
       PreparedStatement ps = null;
        try {
             ps = con.prepareStatement(sql);
             ps.setInt(1, alumno.getDni());
             ps.setString(2, alumno.getApellido());
             ps.setString(3, alumno.getNombre());
             ps.setDate(4, Date.valueOf(alumno.getFechaNac()));
             ps.setInt(5, alumno.getIdAlumno());
             int exito = ps.executeUpdate();
           if(exito==1){
               
               JOptionPane.showMessageDialog(null, "Alumno modificado éxitosamente");
           
           }else{
           JOptionPane.showMessageDialog(null, "Error al intentar modificar los datos en la tabla Alumno");
           }
   ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar conectarse a la tabla Alumno");
        }
   }
   public void eliminarAlumno(int id){
       
       String sql = "UPDATE alumno SET estado = 0, WHERE idAlumno= ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int fila = ps.executeUpdate();
            if(fila==1){
            
                JOptionPane.showMessageDialog(null, "Alumno eliminado");
            
            
            }else{
            JOptionPane.showMessageDialog(null, "Error al intentar eliminar al Alumno");
            
            }
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error al intentar ingresar a la tabla Alumno");
        }
       
   
   
   } 
}
