
public class Activity4 {
    public static void main(String[] args) {
        PMData pm = new PMData();
        User user = new User(pm,"���x");
       
        pm.start();
        user.run();
    }
    
}
