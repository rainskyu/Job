package Web.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Topic {
	
	@Id
	@Column(name="electionid")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinTable(name="topic_option",joinColumns= {@JoinColumn(name="electionid",referencedColumnName="electionid")},inverseJoinColumns={@JoinColumn(name="option_id",referencedColumnName="id")})
	List<Options> options =new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinTable(name="topic_passcode",joinColumns= {@JoinColumn(name="electionid",referencedColumnName="electionid")},inverseJoinColumns={@JoinColumn(name="passcode_id",referencedColumnName="id")})
	List<Passcode> passcode =new ArrayList<>();
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinTable(name="topic_receipt",joinColumns= {@JoinColumn(name="electionid",referencedColumnName="electionid")},inverseJoinColumns={@JoinColumn(name="receipt_id",referencedColumnName="id")})
	List<Receipt> receipt =new ArrayList<>();
	
	/*public List<TemporaryPasscode> getTemporarypasscode() {
		return temporarypasscode;
	}

	public void setTemporarypasscode(List<TemporaryPasscode> temporarypasscode) {
		this.temporarypasscode = temporarypasscode;
	}*/

	public List<Ballot> getBallot() {
		return ballot;
	}

	public void setBallot(List<Ballot> ballot) {
		this.ballot = ballot;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="topic_ballot",joinColumns= {@JoinColumn(name="electionid",referencedColumnName="electionid")},inverseJoinColumns={@JoinColumn(name="ballotid",referencedColumnName="id")})
	List<Ballot> ballot=new ArrayList<>();
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinTable(name="coordinator_topic",joinColumns= {@JoinColumn(name="electionid",referencedColumnName="electionid")},inverseJoinColumns={@JoinColumn(name="coordinator_id",referencedColumnName="id")})
	private Coordinator coordinator;
	
	public Coordinator getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	@Column(name="duration")
	private String duration;
	
	@Column(name="state")
	private String state;
	
	@Column(name="stime")
	private String stime;
	
	@Column(name="etime")
	private String etime;
	
	@Column(name="g1")
	private String g1;
	
	@Column(name="g2")
	private String g2;
	
	@Column(name="privatekey")
	private String privatekey;
	
	@Column(name="publickey")
	private String publickey;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}

	public List<Passcode> getPasscode() {
		return passcode;
	}

	public void setPasscode(List<Passcode> passcode) {
		this.passcode = passcode;
	}


	public List<Receipt> getReceipt() {
		return receipt;
	}

	public void setReceipt(List<Receipt> receipt) {
		this.receipt = receipt;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getG1() {
		return g1;
	}

	public void setG1(String g1) {
		this.g1 = g1;
	}

	public String getG2() {
		return g2;
	}

	public void setG2(String g2) {
		this.g2 = g2;
	}

	public String getPrivatekey() {
		return privatekey;
	}

	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getPublickey() {
		return publickey;
	}

	public void setPublickey(String publickey) {
		this.publickey = publickey;
	}

}
