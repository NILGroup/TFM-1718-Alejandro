package PhraseAnalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import BD.DatabaseConnection;

public class ExecuteScript {
	
	public ArrayList<String> analyser(Connection con, String text) throws IOException, SQLException{
		ArrayList<String> result = new ArrayList<>();
		String[] cmd = {"python","/Users/Alex/Desktop/prueba.py",text.toLowerCase()};
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String res = null;
        int init=0;
        while ((res = stdInput.readLine()) != null) {
        	if(init>0){
        		res = convertHextoString(res);
        		res = filter(con,res);
        		result.add(res);
        	}
        	init++;
        }		
		return result;
	}
	
	// (u'el', u'DET', u'el', u'DET__Definite=Def|Gender=Masc|Number=Sing|PronType=Art')
	// Esta función se queda con los dos primeros campos el lemma y el analisis morfologico
	
	public static String filter(String result){
		result = result.replaceAll("\\(", "");
		result = result.replaceAll("\\)", "");
		String[] fields= result.split(" ");
		result = "";
		for(int i=0; i<2; i++){
			result = result + fields[i].substring(2,fields[i].length()-2).toLowerCase() + " ";
		}
		return result;
	}
	
	public static String filter(Connection con, String result) throws SQLException{
		Statement comando = null;
	    ResultSet registro;
		result = result.replaceAll("\\(", "");
		result = result.replaceAll("\\)", "");
		String[] fields= result.split(" ");
		String tag = fields[1].substring(2,fields[1].length()-2).toLowerCase();
		result="";
		if(tag.equalsIgnoreCase("noun")){
			String word = fields[2].substring(2,fields[2].length()-2);
			if(fields[3].contains("Number") && fields[3].contains("Plur")){
				String aux = word.substring(word.length()-2,word.length());
				if(word.substring(word.length()-2,word.length()).equalsIgnoreCase("es")){
					String query = "SELECT * FROM palabras,pictogramas where palabras.nombre = '" + word.substring(0, word.length()-2) + "' and palabras.id_url = pictogramas.id_pictograma";
		    		comando = con.createStatement();
		            registro = comando.executeQuery(query);
		            if(!registro.next()){
		            	result = word.substring(0,word.length()-1) + " " + tag;
		            }else{
		            	result = word.substring(0,word.length()-2) + " " + tag;
		            }
				}else{
					result = word.substring(0,word.length()-1) + " " + tag;
				}
			}else{
				result = word + " " + tag;
			}
		}else{
			for(int i=0; i<2; i++){
				result = result + fields[i].substring(2,fields[i].length()-2).toLowerCase() + " ";
			}
		}
		return result;
	}
	
	public static String convertHextoString(String result){
		if(result.contains("\\")){
			String [] hexCharacter = result.split(Pattern.quote("\\"));
			String hexValue = "", hexConverted="";
			result = "";
			for(int i=0; i<hexCharacter.length; i++){
				if(hexCharacter[i].toLowerCase().charAt(0)=='x'){
					hexValue = hexCharacter[i].substring(1, 3);
					hexConverted = hexToAscii(hexValue);
					hexCharacter[i] = hexConverted + hexCharacter[i].substring(3, hexCharacter[i].length());
				}
				result = result + hexCharacter[i];
			}
		}
		return result;
	}
	
	private static String hexToAscii(String hexStr) {
	    StringBuilder output = new StringBuilder("");
	     
	    for (int i = 0; i < hexStr.length(); i += 2) {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }
	     
	    return output.toString();
	}
	
	public static void main(String[] args) throws IOException {
		DatabaseConnection dC = new DatabaseConnection();
		Connection conBD = dC.MySQLConnect();
		long startTime = System.nanoTime();
		String frase = "La niña guapa mira el cielo";
		String[] cmd = {"python","/Users/Alex/Desktop/prueba.py",frase.toLowerCase()};
		Process p = Runtime.getRuntime().exec(cmd);
		String s = null;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        ArrayList<String> res = new ArrayList<>();
        int init=0;
        while ((s = stdInput.readLine()) != null) {
        	if(init>0){
	        	s = convertHextoString(s);
	        	//System.out.println(s);
	        	try {
					s= filter(conBD,s);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            res.add(s);
        	}
        	init++;
            System.out.println(s);
        	
        }
       
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        long endTime = System.nanoTime();
		System.out.println("Duración: " + (endTime-startTime)/1e6 + " ms");
		
		
		
		/*PythonInterpreter interpreter = new PythonInterpreter(null, new PySystemState());
		PySystemState sys = Py.getSystemState();
		System.out.println(sys);
		interpreter.exec("# -*- coding: utf-8 -*- ");
		interpreter.exec("import sys\n");
		interpreter.exec("import os\n");
		interpreter.exec("PATH = os.path.dirname(os.path.abspath('/Users/Alex/anaconda/lib/python2.7/site-packages/es_core_news_sm'))");
		interpreter.exec("sys.path.insert(0, PATH)");
		interpreter.exec("import es_core_news_sm\n");
	    interpreter.execfile("/Users/Alex/Desktop/analizador.py");*/
	    //PyObject str = interpreter.eval("repr(analyse("La vida es bella"))");
	    //System.out.println(str.toString());
		
	}

}
