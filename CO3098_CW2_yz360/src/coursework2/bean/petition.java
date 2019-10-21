package coursework2.bean;

import java.sql.Date;

public class petition {
	
	private int ID;
	private String title;
	private String content;
	private Date data;
	private String Creator;
	private int sign=0;
	private int comment=0;
	
	public petition(){
		
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
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
	public Date getDate() {
		return data;
	}
	public void setDate(Date data) {
		this.data = data;
	}
	public String getCreator() {
		return Creator;
	}
	public void setCreator(String Creator) {
		this.Creator = Creator;
	}
	public int getSign() {
		return sign;
	}
	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}
	
	

}
