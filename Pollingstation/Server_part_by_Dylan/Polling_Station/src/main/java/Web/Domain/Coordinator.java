package Web.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity 
public class Coordinator {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	/*@OneToMany(cascade=CascadeType.ALL,orphanRemoval=true)
	
	List <Topic> topic=new ArrayList<>();*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public List<Topic> getTopic() {
		return topic;
	}

	public void setTopic(List<Topic> topic) {
		this.topic = topic;
	}*/
	


	
}
