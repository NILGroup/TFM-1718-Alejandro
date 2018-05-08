package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import BD.DatabaseConnection;
import Model.ClaveUrls;
import Model.Identificador;
import PhraseAnalysis.ExecuteScript;
import PhraseAnalysis.Lemma;
import PhraseAnalysis.StanfordService;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@Path("/traducir")
public class Execute {
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMsg(@PathParam("param") String msg) {
		long time_start_exec_Spacy, time_end_exec_Spacy ,time_start_exec_NGrama, time_end_exec_NGrama ;
		
		String phrase = msg.replaceAll("[^\\dA-Za-z-ñÑáéíóúÁÉÍÓÚ\\s]", "");
		phrase = phrase.replaceAll(" +", " ");
		phrase = phrase .replaceAll("^\\s*","");
		DatabaseConnection dC = new DatabaseConnection();
		Connection conBD = dC.MySQLConnect();
		time_start_exec_Spacy = System.currentTimeMillis();
		ExecuteScript eS = new ExecuteScript();
		ArrayList<String> processedPhrase = new ArrayList<>();
		try {
			processedPhrase = eS.analyser(conBD, phrase);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time_end_exec_Spacy = System.currentTimeMillis();
		System.out.println("The Spacy execution has taken "+ ( time_end_exec_Spacy - time_start_exec_Spacy ) +" milliseconds");
		
		for(int i=0; i<processedPhrase.size(); i++){
			System.out.println(processedPhrase.get(i));
		}
		
		time_start_exec_NGrama = System.currentTimeMillis();
		ArrayList<Identificador> res = null;
		try {
			res = dC.nGrama(conBD, processedPhrase);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time_end_exec_NGrama = System.currentTimeMillis();
		System.out.println("The NGrama execution has taken "+ ( time_end_exec_NGrama - time_start_exec_NGrama ) +" milliseconds");
		int finalVersion;
		ArrayList<Object> result = new ArrayList();
		for(int j=0; j<res.size();j++){
			finalVersion = res.get(j).getFinalVersion();
			Iterator it = res.get(j).getId_url().entrySet().iterator();
			while (it.hasNext()) {
    		    Map.Entry e = (Map.Entry)it.next();
    		    ClaveUrls cU = (ClaveUrls) e.getKey();
    		    if(e.getValue()== null){
    		    	result.add("Word not found: " + cU.getOriginalWord());
    		    	System.out.println("Word not found: " + cU.getOriginalWord());
    		    }else{
    		    	if(finalVersion==cU.getId()){
    		    		result.add(cU.getId() + " " +cU.getTag() + e.getValue() + " " + cU.getOriginalWord());
        		    	System.out.println(cU.getId() + " " +cU.getTag() + e.getValue() + " " + cU.getOriginalWord());
        		    }
    		    }
    		}
		}
		Gson gson = new Gson(); 
		String finalResult = gson.toJson(result);
		System.out.println(finalResult);
		return Response.status(200).entity(finalResult).build();
	}
	
	
	public static void main(String[] args) throws IOException {
		long time_start_init_Stanford, time_end_init_Stanford, time_start_exec_Stanford, time_end_exec_Stanford,time_start_exec_Spacy, time_end_exec_Spacy ,time_start_exec_NGrama, time_end_exec_NGrama ;
		/*
		 
		time_start_init_Stanford = System.currentTimeMillis();
		
		StanfordService ss = new StanfordService();
		StanfordCoreNLP pipeline = ss.initializeStanford();
		
		time_end_init_Stanford = System.currentTimeMillis();
		System.out.println("The Stanford initialization has taken "+ ( time_end_init_Stanford - time_start_init_Stanford ) +" milliseconds");
		
		time_start_exec_Stanford = System.currentTimeMillis();
		ArrayList<String> stanfordResult = new ArrayList<>();
		stanfordResult = ss.parser(pipeline,"Los estados partes asegurarán el acceso a programas de vivienda pública y a apoyos en la jubilación");
		String phraseStanford = "";
		for(int i=0; i<stanfordResult.size();i++){
			System.out.println(stanfordResult.get(i));
			phraseStanford = phraseStanford + stanfordResult.get(i).split(" ")[0] + " ";
		}
		time_end_exec_Stanford = System.currentTimeMillis();
		System.out.println("The Stanford execution has taken "+ ( time_end_exec_Stanford - time_start_exec_Stanford ) +" milliseconds");

		Lemma lemma = new Lemma();
		HashMap<String, String> dictionary = new HashMap<String, String>();
		dictionary = lemma.initializeLemmas();
		ArrayList<String> processedPhrase = lemma.lemmatization(dictionary, stanfordResult);
		System.out.println(phraseStanford);
		
		for(int i=0; i<processedPhrase.size(); i++){
			System.out.println(processedPhrase.get(i));
		}
		
		*/
		
		String phrase = "saltar a la pata coja";
		DatabaseConnection dC = new DatabaseConnection();
		Connection conBD = dC.MySQLConnect();
		
		time_start_exec_Spacy = System.currentTimeMillis();
		ExecuteScript eS = new ExecuteScript();
		ArrayList<String> processedPhrase = new ArrayList<>();
		try {
			processedPhrase = eS.analyser(conBD, phrase);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		time_end_exec_Spacy = System.currentTimeMillis();
		System.out.println("The Spacy execution has taken "+ ( time_end_exec_Spacy - time_start_exec_Spacy ) +" milliseconds");
		
		for(int i=0; i<processedPhrase.size(); i++){
			System.out.println(processedPhrase.get(i));
		}
		
		
		time_start_exec_NGrama = System.currentTimeMillis();
		ArrayList<Identificador> res = null;
		try {
			res = dC.nGrama(conBD, processedPhrase);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		time_end_exec_NGrama = System.currentTimeMillis();
		System.out.println("The NGrama execution has taken "+ ( time_end_exec_NGrama - time_start_exec_NGrama ) +" milliseconds");
		int finalVersion;
		for(int j=0; j<res.size();j++){
			finalVersion = res.get(j).getFinalVersion();
			Iterator it = res.get(j).getId_url().entrySet().iterator();
			while (it.hasNext()) {
    		    Map.Entry e = (Map.Entry)it.next();
    		    ClaveUrls cU = (ClaveUrls) e.getKey();
    		    if(e.getValue()== null){
    		    	System.out.println("Word not found: " + cU.getOriginalWord());
    		    }else{
    		    	if(finalVersion==cU.getId()){
        		    	System.out.println(cU.getId() + " " +cU.getTag() + e.getValue() + " " + cU.getOriginalWord());
        		    }
    		    }
    		}
		}
		
	}

}
