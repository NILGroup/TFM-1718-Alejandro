package com;

import java.io.IOException;
import java.util.ArrayList;

import AnalisisFrase.StanfordService;

public class Execute {

	public static void main(String[] args) throws IOException {
		StanfordService ss = new StanfordService();
		ArrayList<String> res = new ArrayList<>();
		res = ss.parser("La mayoría sabían lo que pasaba pero muchos se negaban a reconocerlo.");
		for(int i=0; i<res.size();i++){
			 System.out.println(res.get(i));
		 }
	}

}
