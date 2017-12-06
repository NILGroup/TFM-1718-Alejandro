package PhraseAnalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class ExecuteScript {
	
	public ArrayList<String> analyser(String text) throws IOException{
		ArrayList<String> result = new ArrayList<>();
		String[] cmd = {"python","/Users/Alex/Desktop/prueba.py",text.toLowerCase()};
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        //BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String res = null;
        while ((res = stdInput.readLine()) != null) {
            result.add(res);
        }		
		return result;
	}
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		String frase = "Al lado de ellos vimos a varias mujeres mayores hablando de las elecciones entretenidamente. Pompas de jabón estallaban continuamente sobre la bañera, mientras que el niño perplejo las miraba.";
		String[] cmd = {"python","/Users/Alex/Desktop/prueba.py",frase.toLowerCase()};
		Process p = Runtime.getRuntime().exec(cmd);
		String s = null;
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        ArrayList<String> res = new ArrayList<>();
        while ((s = stdInput.readLine()) != null) {
            res.add(s);
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
