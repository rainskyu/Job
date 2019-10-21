package Web.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Options {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="options")
	private String options;
	
	@Column(name="tally")
	private int tally;
	
	@Column(name="S")
	private String S;
	
	public String getS() {
		return S;
	}

	public void setS(String s) {
		S = s;
	}

	public int getTally() {
		return tally;
	}

	public void setTally(int tally) {
		this.tally = tally;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	
	
	
	

}
