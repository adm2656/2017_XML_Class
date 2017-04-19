package project1;

public class Doctor {
	String firstName;
	String lastName;
	String lastLogin;
	
	Doctor (String firstName, String lastName, String lastLogin){
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastLogin = lastLogin;
	}
	
	String getFirst(){
		return firstName;
	}
	
	String getLast(){
		return lastName;
	}
	
	String getLastLogin(){
		return lastLogin;
	}
}
