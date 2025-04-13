import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        boolean fairness = true;
        List<Thread> threads = new ArrayList<>();
        H2O h2o = new H2O(fairness);
        for (int i = 0; i < 6; i++) {
            HydrogenTask H = new HydrogenTask(h2o);
            Thread t = new Thread(H);
            threads.add(t);
            t.start();
        }
        for (int i = 0; i < 3; i++) {
            OxygenTask O = new OxygenTask(h2o);
            Thread t = new Thread(O);
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println(" done ");
    }
}

class HydrogenTask implements Runnable {
    H2O h2o;

    public HydrogenTask(H2O h2o) {
        this.h2o = h2o;
    }

    @Override
    public void run() {
        try {
            h2o.hydrogen();
            System.out.println("Hydrogen released");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hydrogen thread interrupted: " + e.getMessage());
        }
    }
}

class OxygenTask implements Runnable {
    H2O h2o;

    public OxygenTask(H2O h2o) {
        this.h2o = h2o;
    }

    @Override
    public void run() {
        try {
            h2o.oxygen();
            System.out.println("Oxygen released");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Oxygen thread interrupted: " + e.getMessage());
        }
    }
}