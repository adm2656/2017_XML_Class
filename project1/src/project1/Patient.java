package project1;

public class Patient {
	String Name;
	String doctor;
	String ssn;
	String address;
	String phone;
	String note;
	
	Patient (String Name, String ssn, String address, String phone, String note){
		this.Name = Name;
		this.ssn = ssn;
		this.address = address;
		this.phone = phone;
		this.note = note;
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
	
	String getnote(){
		return note;
	}
}
