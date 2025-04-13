import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        DiningPhilosophers2 dp2 = new DiningPhilosophers2();
        ArrayList<Philosopher> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Philosopher(dp2, i));
        }
        list.forEach(Thread::start);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        list.forEach(Philosopher::shutdown);
    }
}

class Philosopher extends Thread {
    static Random rd = new Random();
    private DiningPhilosophers2 dp2;
    private boolean stop = true;
    private int id;

    public Philosopher(DiningPhilosophers2 dp2, int id) {
        this.dp2 = dp2;
        this.id = id;
    }

    @Override
    public void run() {
        while (this.stop) {
            try {
                System.out.println("philosopher " + id + " thinking");
                Thread.sleep(rd.nextInt(100));
                this.dp2.getForks(id);
                Thread.sleep(rd.nextInt(100));
                this.dp2.releaseForks(id);
            } catch (InterruptedException e) {
            }
        }
    }

    public void shutdown() {
        this.stop = false;
    }
}
