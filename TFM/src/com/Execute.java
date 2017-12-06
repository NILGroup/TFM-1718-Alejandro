package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import BD.DatabaseConnection;
import Model.ClaveUrls;
import Model.Identificador;
import PhraseAnalysis.ExecuteScript;
import PhraseAnalysis.Lemma;
import PhraseAnalysis.StanfordService;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Execute {
	
	
	public static void main(String[] args) throws IOException {
		long time_start_init_Stanford, time_end_init_Stanford, time_start_exec_Stanford, time_end_exec_Stanford, time_start_exec_NGrama, time_end_exec_NGrama ;
		time_start_init_Stanford = System.currentTimeMillis();
		
		StanfordService ss = new StanfordService();
		StanfordCoreNLP pipeline = ss.initializeStanford();
		
		time_end_init_Stanford = System.currentTimeMillis();
		System.out.println("The Stanford initialization has taken "+ ( time_end_init_Stanford - time_start_init_Stanford ) +" milliseconds");
		
		time_start_exec_Stanford = System.currentTimeMillis();
		ArrayList<String> stanfordResult = new ArrayList<>();
		stanfordResult = ss.parser(pipeline,"Acompañó el filete de carne con agua viendo los fuegos artificiales");
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
		
		
		/*ExecuteScript eS = new ExecuteScript();
		ArrayList<String> res = new ArrayList<>();
		res = eS.analyser(standfordResult);
		for(int i=0; i<res.size();i++){
			 System.out.println(res.get(i));
		}*/
		DatabaseConnection dC = new DatabaseConnection();
		Connection conBD = dC.MySQLConnect();
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
    		    if(finalVersion==cU.getId()){
    		    	System.out.println(cU.getId() + " " +cU.getTag() + e.getValue());
    		    }
    		}
		}
		
		
	}

}
