package coursework2.bean;

public class sign {
	
	private int id=0;
	private String title;
	private String content;
	private String NIC;
	
	public sign() {
		
	}
	
	public sign(String title,String content,String NIC) {
		this.title=title;
		this.content=content;
		this.NIC=NIC;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNIC() {
		return NIC;
	}

	public void setNIC(String nIC) {
		NIC = nIC;
	}

}
