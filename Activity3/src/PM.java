
public class PM {
	String city = "";
    String pmNum = "";
    public PM (String c, String p){
        city = c;
        pmNum = p;
    }
    String getPmNum(){
        return pmNum;
    }
    String getCity(){
        return city;
    }
    void setPmNum(String n){
        pmNum = n;
    }
    void setCity(String c){
        city = c;
    }
}

