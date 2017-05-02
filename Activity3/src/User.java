
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User implements Runnable{
    PMData data;
    Random ran;
    String key;
    int index;
    User(PMData _data, String _key){
        this.data = _data;
        this.key = _key;
    }
    public void run(){
    	try {
    		index++;
            	ran = new Random();
            	int update = ran.nextInt(1001) + 500;
    		Thread.sleep(update);
    		if(index < 100){
    			System.out.println(data.search(key));
    			this.run();
    		}
    		else{
    			Thread.interrupted();
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
    }
}
