package AnalisisFrase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lemma {

	public static void main(String[] args) throws IOException {

		String path = "/Users/Alex/Downloads/lemmatization-es.txt";
	    HashMap<String, String> map = new HashMap<String, String>();

	    String line;
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			try {
				while ((line=reader.readLine()) != null) {
				    String[] parts = line.split("\t");
			        String key = parts[1];
			        String value = parts[0];
			        map.put(key, value);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		    try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	    System.out.println("He terminado de crear el diccionario");
	    
	    ArrayList<String> tokens = new ArrayList<String>();
	    tokens.add("amante");
	    tokens.add("supe");
	    tokens.add("perros");
	    tokens.add("casitas");
	    tokens.add("espaciado");
	    tokens.add("consternado");
	    
	    for(int i=0; i<tokens.size(); i++){
	    	if(map.containsKey(tokens.get(i))){
	    		System.out.println(map.get(tokens.get(i)));
	    	}
	    }
	}

}
