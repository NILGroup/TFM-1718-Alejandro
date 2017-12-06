package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Identificador;

public class DatabaseConnection {

	private Connection conexion = null;
    private Statement comando = null;
    private ResultSet registro;
 
    public Connection MySQLConnect() {
 
        try {
            //Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost:3306/ssiipicto_bbdd";
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
            //JOptionPane.showMessageDialog(null, "Conexión Exitosa");
            return conexion;
        }
    }
    
    public ArrayList<Identificador> nGrama (Connection con, String content) throws SQLException{
    	String[] phrase = content.split(" ");
    	boolean find, end= false;
    	int elementIndex = phrase.length;
    	int i=0;
    	ArrayList<Identificador> result = new ArrayList<Identificador>();
    	while(i<phrase.length && !end){
    		int j=i;
    		String res = "";
    		String aux = "";
    		Identificador ident;
    		find = true;
    		while(j< phrase.length && find){
	            if(j==i){
	            	ident = new Identificador(i);
	            	res = res + phrase[j];
	            	aux = "=";
	            }
	            else{
	            	res = res + " " + phrase[j] + "%";
	            	ident = result.get(result.size()-1);
	            	aux = "LIKE";
	            }
	    		String query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre " + aux + " '" + res + "' and palabras.id_url = pictogramas.id_pictograma";
	    		comando = con.createStatement();
	            registro = comando.executeQuery(query);
	            if(!registro.next()){
	            	if(result.size()==0){
	            		result.add(ident);
	            	}        	
	            	find = false;
	            	i=ident.getId()+1;
	            	elementIndex = phrase.length-ident.getId()-1;
	            }
	            else{
	            	ident.cleanId_url();
	            	ident.setId(j);
	            	while(registro.next()){
	            		ident.setId_url(registro.getString(1));
	            	}
	            	result.add(ident);
	            	j++;
	            }   
    		}
    		if(j==phrase.length){
    			end = true;
    		}
    	}
    	return result;
    }
    
    public static void main(String[] args) throws SQLException {
		
		DatabaseConnection db = new DatabaseConnection();
	    db.MySQLConnect();
	     
	    //nGrama(db.conexion,"Acompañar el filete de tenera con agua");
	    /*String NombreDB = "palabras";
        String palabra = "aguass";
        String Query = "SELECT url FROM palabras,pictogramas where palabras.nombre = '"+ palabra+"' and palabras.id_url = pictogramas.id_pictograma";
        System.out.println(Query);
        
        db.comando = db.conexion.createStatement();
        db.registro = db.comando.executeQuery(Query);
        
        
        while (db.registro.next()) {
            
            System.out.println(db.registro.getString(1));
            
            System.out.println("------------------------------------------");
        }*/
	}

}
