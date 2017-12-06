package PhraseAnalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Lemma {
	
	private static String path =  System.getProperty("user.dir") + "/src/PhraseAnalysis/lemmatization-es.txt";
	
	public HashMap<String, String> initializeLemmas(){
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
		
	    System.out.println("Dictionary created");
		
		return map;
	}
	//Tener en cuenta cuando la palabra ya está como lemma
	
	public boolean checkLemma(HashMap<String, String> map, String word){
		if(map.containsValue(word)){
			return true;
		}else{
			return false;
		}
	}
	////
	public String checkLemma(HashMap<String, String> map, String word, String res){
		if(map.containsKey(word)){
			res = res + map.get(word) + " ";
		}else{
			res = res + "? ";
		}
		return res;
	}
	///
	public String lemmatization(HashMap<String, String> map, ArrayList<String> phrase){
		String res = "";
		for(int i=0; i<phrase.size();i++){
			String[] aux = phrase.get(i).split(" ");
			String word = aux[0].toLowerCase();
			String tag = aux[1].replace("0", "");
			switch(tag.charAt(0)){
				case 'n': 
					if (tag.equalsIgnoreCase("ncp")){
						res = checkLemma(map,word,res);
					}else{
						res = res + word + " ";
					} break;
				case 'v': 
					if(tag.equalsIgnoreCase("van")||tag.equalsIgnoreCase("vmn")||tag.equalsIgnoreCase("vsn")){
						res = res + word + " ";
					}else{
						res = checkLemma(map,word,res);
					}break;
				case 'a': 
					res = checkLemma(map,word,res);
					break;
				default: res = res + word + " "; break;
			}
			/*if(tag.charAt(0)=='n' || tag.charAt(0)=='v' || tag.charAt(0)=='a'){
				if(checkLemma(map, word)){
					res = res + word + " ";
				}else{
					if(map.containsKey(word)){
						res = res + map.get(word) + " ";
					}else{
						res = res + "? ";
					}
				}
			}else{
				res = res + word + " ";
			}*/
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException {
		
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
	    /*tokens.add("La da0000");
	    tokens.add("niña nc0s000");
	    tokens.add("lista aq0000");
	    tokens.add("sabía vmii000");
	    tokens.add("lo da0000");
	    tokens.add("veces nc0p000");*/
	    tokens.add("sabría");
	    tokens.add("quedó");
	    tokens.add("casitas");
	    tokens.add("últimos");
	    
	    
	    
	    for(int i=0; i<tokens.size(); i++){
	    	if(map.containsKey(tokens.get(i))){
	    		System.out.println(map.get(tokens.get(i)));
	    	}
	    }
	}

}
