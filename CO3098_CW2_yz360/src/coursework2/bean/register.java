package coursework2.bean;

public class register {

	public static int ID=0;
	private String email;
	private String dataofbirth;
	private String fulladdress;
	private String password;
	private String NIC;
	private String fname;
	private String sname;
	private int id;
	
	
	
	public register() {
		
	}
	
	public register(String email,  String passwordHash,String fname,String sname,String address,String nic) {
		super();
		this.email = email;
		this.password = passwordHash;
		this.fname=fname;
		this.sname=sname;
		this.fulladdress=address;
		this.NIC=nic;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataofbirth() {
		return dataofbirth;
	}
	public void setDataofbirth(String dataofbirth) {
		this.dataofbirth = dataofbirth;
	}
	public String getFulladdress() {
		return fulladdress;
	}
	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNIC() {
		return NIC;
	}
	public void setNIC(String nIC) {
		NIC = nIC;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getID() {
		return id;
	}

	public void setID(int iD) {
		id = iD;
	}

	
}
