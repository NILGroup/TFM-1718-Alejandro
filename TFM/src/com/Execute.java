package com;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;


import AnalisisFrase.ExecuteScript;
import AnalisisFrase.StanfordService;
import BD.DatabaseConnection;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Execute {
	
	
	
	public static void main(String[] args) throws IOException {
		long time_start_init_Stanford, time_end_init_Stanford, time_start_exec_Stanford, time_end_exec_Stanford;
		time_start_init_Stanford = System.currentTimeMillis();
		
		StanfordService ss = new StanfordService();
		StanfordCoreNLP pipeline = ss.initializeStanford();
		
		time_end_init_Stanford = System.currentTimeMillis();
		System.out.println("The Stanford initialization has taken "+ ( time_end_init_Stanford - time_start_init_Stanford ) +" milliseconds");
		
		time_start_exec_Stanford = System.currentTimeMillis();
		ArrayList<String> res = new ArrayList<>();
		res = ss.parser(pipeline,"La mayoría sabían lo que pasaba pero muchos se negaban a reconocerlo.");
		for(int i=0; i<res.size();i++){
			System.out.println(res.get(i));
		}
		time_end_exec_Stanford = System.currentTimeMillis();
		System.out.println("The Stanford execution has taken "+ ( time_end_exec_Stanford - time_start_exec_Stanford ) +" milliseconds");

		
		
		/*ExecuteScript eS = new ExecuteScript();
		ArrayList<String> res = new ArrayList<>();
		res = eS.analyser("La mayoría sabían lo que pasaba pero muchos se negaban a reconocerlo.");
		for(int i=0; i<res.size();i++){
			 System.out.println(res.get(i));
		}*/
		DatabaseConnection dC = new DatabaseConnection();
		Connection conBD = dC.MySQLConnect();
		
		
		
	}

}
