package Web.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Receipt {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@ManyToOne(cascade=CascadeType.ALL)
	private Topic topic;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="receipt_ballot",joinColumns= {@JoinColumn(name="receipt_id",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="ballot_id",referencedColumnName="id")})
	private List<Ballot> ballot = new ArrayList<>();

	public List<Ballot> getBallot() {
		return ballot;
	}

	public void setBallot(List<Ballot> ballot) {
		this.ballot = ballot;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}



	@Column(name="passcodeid")
	private int passcodeid;
	
	@Column(name="StageOneHash")
	private String StageOneHash;
	
	@Column(name="StageOneSign")
	private String StageOneSign;
	
	@Column(name="StageTwoSign")
	private String StageTwoSign;

	@Column(name="t1")
	private String t1;
	
	@Column(name="t2")
	private String t2;
	
	@Column(name="t")
	private String t;
	
	@Column(name="state")
	private String state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPasscodeid() {
		return passcodeid;
	}

	public void setPasscodeid(int passcodeid) {
		this.passcodeid = passcodeid;
	}

	public String getStageOneHash() {
		return StageOneHash;
	}

	public void setStageOneHash(String stageOneHash) {
		StageOneHash = stageOneHash;
	}

	public String getStageOneSign() {
		return StageOneSign;
	}

	public void setStageOneSign(String stageOneSign) {
		StageOneSign = stageOneSign;
	}

	public String getStageTwoSign() {
		return StageTwoSign;
	}

	public void setStageTwoSign(String stageTwoSign) {
		StageTwoSign = stageTwoSign;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
}
