package Web.Domain;

import javax.persistence.*;

@Entity
public class Passcode {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="passcode")
	private String passcode;

	@Column(name="state")
	private boolean state;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Topic topic;

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	
	
}
