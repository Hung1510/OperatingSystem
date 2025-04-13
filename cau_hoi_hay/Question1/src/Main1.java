import java.util.ArrayList;
import java.util.Random;


public class Main1 {

    public static void main(String[] args) throws InterruptedException{
        Random rd = new Random();
        ArrayList<Thread> threads = new ArrayList<>();
        Boat boat = new Boat();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run(){
                    try {
                        int dir = rd.nextInt();
                        boat.arriveBoat(dir%2);
                        // on the boat
                        Thread.sleep(10);
                        boat.exitBoat(dir%2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(t);
            t.start();
        }
        for(Thread t:threads){
            t.join();
        }
        System.out.println();
      
    }
}
