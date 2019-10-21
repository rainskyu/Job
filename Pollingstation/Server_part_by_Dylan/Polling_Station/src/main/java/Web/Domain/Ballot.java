package Web.Domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ballot {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Column(name="ballotid")
	private int ballotid;

	@Column(name="title")
	private String title;

	@Column(name="random")
	private String random;

	@Column(name="state")
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="v")
	private int v;

	@Column(name="R")
	private String R;

	@Column(name="Z")
	private String Z;

	@Column(name="C1")
	private String c1;

	@Column(name="C2")
	private String c2;

	@Column(name="r1")
	private String r1;

	@Column(name="r2")
	private String r2;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="ballot_option",joinColumns= {@JoinColumn(name="ballotid",referencedColumnName="id")},inverseJoinColumns={@JoinColumn(name="option_id",referencedColumnName="id")})
	private Options options;

	@ManyToOne(cascade=CascadeType.ALL)
	private Topic topic;

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
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

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public String getR() {
		return R;
	}

	public void setR(String r) {
		R = r;
	}

	public String getZ() {
		return Z;
	}

	public void setZ(String z) {
		Z = z;
	}

	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public String getR1() {
		return r1;
	}

	public void setR1(String r1) {
		this.r1 = r1;
	}

	public String getR2() {
		return r2;
	}

	public void setR2(String r2) {
		this.r2 = r2;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}


	public int getBallotid() {
		return ballotid;
	}

	public void setBallotid(int ballotid) {
		this.ballotid = ballotid;
	}
}
