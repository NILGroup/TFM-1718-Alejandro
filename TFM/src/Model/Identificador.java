package Model;

import java.util.ArrayList;

public class Identificador {

	private int id;
	private ArrayList<String> id_url;
	
	public Identificador(int id) {
		this.id = id;
		this.id_url = new ArrayList<String>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<String> getId_url() {
		return id_url;
	}

	public void setId_url(String url) {
		this.id_url.add(url);
	}
	
	public void cleanId_url(){
		for(int i=0; i<this.id_url.size(); i++){
			id_url.remove(i);
		}
	}


}
