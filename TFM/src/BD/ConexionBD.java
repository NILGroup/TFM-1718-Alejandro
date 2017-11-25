package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexionBD {

	Connection conexion = null;
    Statement comando = null;
    ResultSet registro;
	
	 public static void main(String[] args) throws SQLException {
		 ConexionBD db = new ConexionBD();
	     db.MySQLConnect();
	     
	    String NombreDB = "palabras";
        
        String Query = "SELECT * FROM " + NombreDB + " where id_palabra = 1";
        
        
        db.comando = db.conexion.createStatement();
        db.registro = db.comando.executeQuery(Query);
        
        while (db.registro.next()) {
            
            System.out.println(db.registro.getString(2));
            
            System.out.println("------------------------------------------");
        }
	}

 
    public Connection MySQLConnect() {
 
        try {
            //Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
         
            String servidor = "jdbc:mysql://localhost:3306/ssiipicto_bbdd";
            //El root es el nombre de usuario por default. No hay contraseña
            String usuario = "hypatia";
            String pass = "hypatia";
            //Se inicia la conexión
            conexion = DriverManager.getConnection(servidor, usuario, pass);
 
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
            JOptionPane.showMessageDialog(null, "Conexión Exitosa");
            return conexion;
        }
    }

}
