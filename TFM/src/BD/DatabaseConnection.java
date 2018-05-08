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
    private Statement comando_aux = null;
    private ResultSet registro_aux;
 
    public Connection MySQLConnect() {
 
        try {
            //Driver JDBC
            Class.forName("com.mysql.jdbc.Driver");
            /*String servidor = "jdbc:mysql://localhost:3306/ssiipicto_bbdd";
            String usuario = "hypatia";
            String pass = "hypatia";*/
            String servidor = "jdbc:mysql://localhost:3306/tfmpictar_bbdd";
            String usuario = "tfmpictar";
            String pass = "tfmp1ct4r";
            
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
    
    /*public void checkNGramaCorrect(Identificador ident){
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
    		//char firstCharacterLT = lastTag.charAt(0);
    		//char firstCharacterPT = ' ';
    		int back=1;
    		if(lastTag.equalsIgnoreCase("conj") || lastTag.equalsIgnoreCase("det")|| lastTag.equalsIgnoreCase("punct") || lastTag.equalsIgnoreCase("adp")){
    			int i=cUs.size()-2;
    			boolean fin = false;
    			while(i>=0 && !fin){
    				penultimateTag = cUs.get(i).getTag();
    				if(penultimateTag.equalsIgnoreCase("adj") || penultimateTag.equalsIgnoreCase("noun") || penultimateTag.equalsIgnoreCase("verb") || penultimateTag.equalsIgnoreCase("propn")){
        				ident.setFinalVersion(cUs.get(i).getId());
        				ident.setId(ident.getId()-back);
        				fin = true;
        			}else{
        				back++;
        			}
    				i--;
    			}
    			//for(int i=cUs.size()-2; i>=0; i--){
    			//	penultimateTag = cUs.get(i).getTag();
    				//firstCharacterPT = penultimateTag.charAt(0);
    			//	if(penultimateTag.equalsIgnoreCase("adj") || penultimateTag.equalsIgnoreCase("noun") || penultimateTag.equalsIgnoreCase("verb") || penultimateTag.equalsIgnoreCase("propn")){
        		//		ident.setFinalVersion(cUs.get(i).getId());
        		//		ident.setId(ident.getId()-back);
        		//	}else{
        		//		back++;
        		//	}
    			// }
    		}else{
    			ident.setFinalVersion(cUs.get(cUs.size()-1).getId());
    		}
    	}
    }*/
    
    /*public ArrayList<Identificador> nGrama (Connection con, ArrayList<String> content) throws SQLException{
    	ArrayList<Identificador> result = new ArrayList<Identificador>();
    	boolean find, end= false;
    	int i=0;
    	while(i<content.size() && !end){
    		int j=i;
    		String res = "";
    		String aux = "";
    		String originalWordResult="";
    		Identificador ident = null;
    		find = true;
    		int version = 0;
    		while(j< content.size() && find){
    			String[] wT = content.get(j).split(" ");
    			String word = wT[0];
    			String tag = wT[1];
    			String originalWord = wT[2];
	            if(j==i){
	            	ident = new Identificador(i);
	            	res = res + word;
	            	originalWordResult = originalWord;
	            	aux = "=";
	            }
	            else{
	            	//Cuando se trata de signos de puntuación han de ir junto con la palabra
	            	if(tag.equalsIgnoreCase("punct")){
	            		res = res + "%" + word + "%";
	            	}
	            	else{
	            		res = res + "% " + word + "%";
	            	}
	            	originalWordResult = originalWordResult + " " +originalWord;
	            	aux = "LIKE";
	            }
	            String query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre " + aux + " '" + res + "' and palabras.id_url = pictogramas.id_pictograma";
	    		comando = con.createStatement();
	            registro = comando.executeQuery(query);
	            if(!registro.next()){    	
	            	find = false;
	            	//Caso de que no se encuentre la palabra en la BD la primera vez
	            	if(i==j){
	            		ident.setId_url(version,tag,originalWordResult,null);
	            	}
	            }else{
	            	ident.setId(j);
	            	ArrayList<String> urls = new ArrayList<String>();
	            	registro.beforeFirst();
	            	while(registro.next()){
	            		urls.add(registro.getString(1));
	            	}
	            	ident.setId_url(version,tag,originalWordResult,urls);
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
    }*/
    
    public void checkNGramaCorrect(Identificador ident, Connection con) throws SQLException{
        Statement comando = null;
        ResultSet registro = null;
    	if(ident.getId_url().size()>1){
    		Iterator it = ident.getId_url().entrySet().iterator();
    		ArrayList<ClaveUrls> cUs = new ArrayList<ClaveUrls>();
    		while (it.hasNext()) {
    		    Map.Entry e = (Map.Entry)it.next();
    		    cUs.add((ClaveUrls) e.getKey());
    		    //System.out.println(cU.getId() + " " +cU.getTag() + e.getValue());
    		}
    		Collections.sort(cUs);
    		int back=0;
			int i=cUs.size()-1;
			boolean fin = false;
			while(i>=0 && !fin){
				String query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre = '" + cUs.get(i).getModifiedWord() + "' and palabras.id_url = pictogramas.id_pictograma";
	    		comando = con.createStatement();
	            registro = comando.executeQuery(query);
	            if(!registro.next()){ 
	            	query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre = '" + cUs.get(i).getOriginalWord() + "' and palabras.id_url = pictogramas.id_pictograma";
    	    		comando = con.createStatement();
    	            registro = comando.executeQuery(query);
    	            if(!registro.next()){ 
    	            	i--;
    	            	back++;
    	            }else{
    	            	fin = true;
    	            }
	            }else{
    				fin = true;
	            }
			}
			if(fin){
				ArrayList<String> urls = new ArrayList<String>();
            	registro.beforeFirst();
            	while(registro.next()){
            		urls.add(registro.getString(1));
            	}
            	ident.setUrls(cUs.get(i), urls);
				ident.setFinalVersion(cUs.get(i).getId());
				ident.setId(ident.getId()-back);
			}
    		
    	}
    }
    
    public ArrayList<Identificador> nGrama (Connection con, ArrayList<String> content) throws SQLException{
    	ArrayList<Identificador> result = new ArrayList<Identificador>();
    	boolean find, end= false;
    	int i=0;
    	while(i<content.size() && !end){
    		int j=i;
    		String res = "";
    		String aux = "";
    		String originalWordResult="";
    		String modifiedWordResult="";
    		Identificador ident = null;
    		find = true;
    		int version = 0;
    		while(j< content.size() && find){
    			String[] wT = content.get(j).split(" ");
    			String word = wT[0];
    			String tag = wT[1];
    			String originalWord = wT[2];
	            if(j==i){
	            	ident = new Identificador(i);
	            	res = res + word;
	            	originalWordResult = originalWord;
	            	modifiedWordResult = word;
	            	aux = "=";
	            }
	            else{
	            	//Cuando se trata de signos de puntuación han de ir junto con la palabra
	            	if(tag.equalsIgnoreCase("punct")){
	            		res = res + "%" + word + "%";
	            	}
	            	else{
	            		res = res + "% " + word+ "%";
	            	}
	            	originalWordResult = originalWordResult + " " + originalWord;
	            	modifiedWordResult = modifiedWordResult + " " + word;
	            	aux = "LIKE";
	            }
	            String query = "SELECT id_url FROM palabras,pictogramas where palabras.nombre " + aux + " '" + res + "' and palabras.id_url = pictogramas.id_pictograma";
	    		comando = con.createStatement();
	            registro = comando.executeQuery(query);
	            if(!registro.next()){ 
	            	//find = false;
	            	//Caso de que no se encuentre la palabra en la BD la primera vez
	            	if(i==j){
	            		res = originalWord;
	            		//ident.setId_url(version,tag,originalWordResult,null);
	            	}else{
	            		String[] res_aux = res.split("%");
		            	res = res.replace(res_aux[res_aux.length-1], originalWord);
		            	
		            }
	            	String query_aux = "SELECT id_url FROM palabras,pictogramas where palabras.nombre " + aux + " '" + res + "' and palabras.id_url = pictogramas.id_pictograma";
	            	comando_aux = con.createStatement();
		            registro_aux = comando_aux.executeQuery(query_aux);
		            if(!registro_aux.next()){ 
		            	find = false;
		            	if(i==j){
		            		res = res.replace("%", " ");
		            		res = res.replaceAll(" +", " ");  
		            		ident.setId_url(version,tag,originalWordResult,res,null);
		            	}
		            }else{
		            	ident.setId(j);
		            	ArrayList<String> urls = new ArrayList<String>();
		            	registro_aux.beforeFirst();
		            	while(registro_aux.next()){
		            		urls.add(registro_aux.getString(1));
		            	}
		            	res = res.replace("%", " ");
		            	res = res.replaceAll(" +", " "); 
		            	ident.setId_url(version,tag,originalWordResult,res,urls);
		            	version++;
		            	j++;
            		}
	            }else{
	            	ident.setId(j);
	            	ArrayList<String> urls = new ArrayList<String>();
	            	registro.beforeFirst();
	            	while(registro.next()){
	            		urls.add(registro.getString(1));
	            	}
	            	ident.setId_url(version,tag,originalWordResult,modifiedWordResult,urls);
	            	version++;
	            	j++;
	            }
    		}
    		checkNGramaCorrect(ident,con);
    		result.add(ident);
    		i=ident.getId()+1;
    		/*if(j==content.size()){
    			end = true;
    		}*/
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
