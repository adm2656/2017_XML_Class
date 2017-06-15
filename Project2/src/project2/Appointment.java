package project2;

public class Appointment {
	
	String date;
	String note;
	String prescription;
	
	Appointment(String date, String note, String prescription){
		this.date = date;
		this.note = note;
		this.prescription = prescription;
	}
	
	String getdate(){
		return date;
	}
	
	String getnote(){
		return note;
	}
	
	String getpresception(){
		return prescription;
	}
}
