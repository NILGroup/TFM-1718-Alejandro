package Model;

public class ClaveUrls implements Comparable<ClaveUrls> {
	
	private int id;
	private String tag;
	
	public ClaveUrls() {
		// TODO Auto-generated constructor stub
	}
	
	public ClaveUrls(int id, String tag){
		this.id = id;
		this.tag = tag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public int compareTo(ClaveUrls o) {
		 int compareId=((ClaveUrls)o).getId();
		 return this.id-compareId;
	}

}
