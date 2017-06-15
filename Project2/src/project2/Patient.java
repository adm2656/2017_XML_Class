package project2;

public class Patient {
	String Name;
	String doctor;
	String ssn;
	String address;
	String phone;
	
	Patient (String Name, String ssn, String address, String phone){
		this.Name = Name;
		this.ssn = ssn;
		this.address = address;
		this.phone = phone;
	}
	
	String getname(){
		return Name;
	}
	
	String getSsn(){
		return ssn;
	}
	
	String getAddress(){
		return address;
	}
	
	String getphone(){
		return phone;
	}
	
}
