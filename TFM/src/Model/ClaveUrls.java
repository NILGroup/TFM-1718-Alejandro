package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ClaveUrls")
public class ClaveUrls implements Comparable<ClaveUrls> {
	
	private int id;
	private String tag;
	private String originalWord;
	private String modifiedWord;

	public ClaveUrls() {
		// TODO Auto-generated constructor stub
	}
	
	/*public ClaveUrls(int id, String tag, String originalWord){
		this.id = id;
		this.tag = tag;
		this.originalWord = originalWord;
	}*/
	
	public ClaveUrls(int id, String tag, String originalWord, String modifiedWord){
		this.id = id;
		this.tag = tag;
		this.originalWord = originalWord;
		this.modifiedWord = modifiedWord;
	}


	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}
	@XmlElement
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getOriginalWord() {
		return originalWord;
	}
	@XmlElement
	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

	public String getModifiedWord() {
		return modifiedWord;
	}
	@XmlElement
	public void setModifiedWord(String modifiedWord) {
		this.modifiedWord = modifiedWord;
	}
	
	@Override
	public int compareTo(ClaveUrls o) {
		 int compareId=((ClaveUrls)o).getId();
		 return this.id-compareId;
	}

}
