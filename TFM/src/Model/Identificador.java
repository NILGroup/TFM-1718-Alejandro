package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Identificador {

	private int id;
	private HashMap<ClaveUrls, ArrayList<String>> id_url;
	private int finalVersion;
	
	public Identificador(){
		
	}
	
	public Identificador(int id) {
		this.id = id;
		this.id_url = new HashMap<ClaveUrls, ArrayList<String>>();
		this.finalVersion = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getFinalVersion() {
		return finalVersion;
	}

	public void setFinalVersion(int fv) {
		this.finalVersion = fv;
	}

	public HashMap<ClaveUrls, ArrayList<String>> getId_url() {
		return id_url;
	}

	public void setId_url(int version, String tag, ArrayList<String> urls) {
		ClaveUrls cU = new ClaveUrls(version, tag);
		this.id_url.put(cU, urls);
	}
	
}
