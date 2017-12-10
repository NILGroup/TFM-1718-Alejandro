package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import Model.ClaveUrls;
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
    
    public void checkNGramaCorrect(Identificador ident){
    	if(ident.getId_url().size()>1){
    		Iterator it = ident.getId_url().entrySet().iterator();
    		ArrayList<ClaveUrls> cUs = new ArrayList<ClaveUrls>();
    		while (it.hasNext()) {
    		    Map.Entry e = (Map.Entry)it.next();
    		    cUs.add((ClaveUrls) e.getKey());
    		    //System.out.println(cU.getId() + " " +cU.getTag() + e.getValue());
    		}
    		Collections.sort(cUs);
    		String lastTag = cUs.get(cUs.size()-1).getTag();
    		String penultimateTag = "";
    		char firstCharacterLT = lastTag.charAt(0);
    		char firstCharacterPT = ' ';
    		int back=1;
    		if(firstCharacterLT=='c' || firstCharacterLT=='d'|| firstCharacterLT=='f' || firstCharacterLT=='s'){
    			for(int i=cUs.size()-2; i>=0; i--){
    				penultimateTag = cUs.get(i).getTag();
    				firstCharacterPT = penultimateTag.charAt(0);
    				if(firstCharacterPT=='a' || firstCharacterPT=='n' || firstCharacterPT=='v'){
        				ident.setFinalVersion(cUs.get(i).getId());
        				ident.setId(ident.getId()-back);
        			}else{
        				back++;
        			}
    			}
    		}else{
    			ident.setFinalVersion(cUs.get(cUs.size()-1).getId());
    		}
    	}
    }
    
    public ArrayList<Identificador> nGrama (Connection con, ArrayList<String> content) throws SQLException{
    	ArrayList<Identificador> result = new ArrayList<Identificador>();
    	boolean find, end= false;
    	//int elementIndex = content.size();
    	int i=0;
    	while(i<content.size() && !end){
    		int j=i;
    		String res = "";
    		String aux = "";
    		Identificador ident = null;
    		find = true;
    		int version = 0;
    		while(j< content.size() && find){
    			String[] wT = content.get(j).split(" ");
    			String word = wT[0];
    			String tag = wT[1];
	            if(j==i){
	            	ident = new Identificador(i);
	            	res = res + word;
	            	aux = "=";
	            }
	            else{
	            	//Cuando se trata de signos de puntuación han de ir junto con la palabra
	            	if(tag.charAt(0)=='f'){
	            		res = res + "%" + word + "%";
	            	}
	            	else{
	            		res = res + "% " + word + "%";
	            	}
	            	//ident = result.get(result.size()-1);
	            	aux = "LIKE";
	            }
	            String query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre " + aux + " '" + res + "' and palabras.id_url = pictogramas.id_pictograma";
	    		comando = con.createStatement();
	            registro = comando.executeQuery(query);
	            if(!registro.next()){
	            	/*if(result.size()==0){
	            		result.add(ident);
	            	} */       	
	            	find = false;
	            	//checkNGramaCorrect(ident);
	            	//i=ident.getId()+1;
	            	//elementIndex = content.size()-ident.getId()-1;
	            }else{
	            	ident.setId(j);
	            	ArrayList<String> urls = new ArrayList<String>();
	            	registro.beforeFirst();
	            	while(registro.next()){
	            		urls.add(registro.getString(1));
	            	}
	            	//No es esto lo que quiero, tiene que checkear la version y en caso contrario volver al i correspondiente
	            	ident.setId_url(version,tag,urls);
	            	//result.set tener en cuenta cuando se repita el mismo valor 
	            	//result.add(ident);
	            	version++;
	            	j++;
	            }
    		}
    		checkNGramaCorrect(ident);
    		result.add(ident);
    		i=ident.getId()+1;
    		if(j==content.size()){
    			end = true;
    		}
    	}
    	return result;
    }
            	
    
    public static void main(String[] args) throws SQLException {
		
		DatabaseConnection db = new DatabaseConnection();
	    db.MySQLConnect();
	     
	    //nGrama(db.conexion,"Acompañar el filete de tenera con agua");
	    String NombreDB = "palabras";
        String palabra = "filete";
        String Query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre = '"+ palabra+"' and palabras.id_url = pictogramas.id_pictograma";
        System.out.println(Query);
        
        db.comando = db.conexion.createStatement();
        db.registro = db.comando.executeQuery(Query);
        
        
        while (db.registro.next()) {
            
            System.out.println(db.registro.getString(1));
            
            System.out.println("------------------------------------------");
        }
	}

}
