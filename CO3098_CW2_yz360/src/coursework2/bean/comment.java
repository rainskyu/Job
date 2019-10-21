package coursework2.bean;

public class comment {
	
	private int id=0;
	private String content;
	private String mps;
	
	
	
	public comment() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMps() {
		return mps;
	}
	public void setMps(String mps) {
		this.mps = mps;
	}
	public comment(int id, String content, String mps) {
		super();
		this.id = id;
		this.content = content;
		this.mps = mps;
	}

}
