
package universidadmaterias.AccesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import universidadmaterias.Entidades.Alumno;


public class AlumnoData {
    private Connection con= null;

    public AlumnoData() {
        con = Conexion.getConexion();
    }
    
    public void guardaralumno(Alumno alumno){
        
        String sql = "INSERT INTO `alumno`( `dni`, `apellido`, `nombre`, "
                + "`fechaNacimiento`, `estado`) VALUES ("
                +String.valueOf(alumno.getDni())
                +alumno.getNombre()
                +alumno.getNombre()
                +Date.valueOf(alumno.getFechaNac())
                +String.valueOf(alumno.isActivo())
                + ")";
        
        try {
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
