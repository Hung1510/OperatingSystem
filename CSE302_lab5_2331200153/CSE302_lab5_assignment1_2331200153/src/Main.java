public class Main {
    public static void main(String[] args) {
        DiningPhilosopher1 dp1 = new DiningPhilosopher1();
        Philosopher1[] philosophers = new Philosopher1[5];

        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Philosopher1(i, dp1);
            philosophers[i].start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Philosopher1 philosopher : philosophers) {
            philosopher.shutdown();
        }
    }
}

class Philosopher1 extends Thread {
    private final int id;
    private final DiningPhilosopher1 dp1;
    private volatile boolean stop = true;

    public Philosopher1(int id, DiningPhilosopher1 dp1) {
        this.id = id;
        this.dp1 = dp1;
    }

    @Override
    public void run() {
        while (stop) {
            try {
                System.out.println("philosipher " + id + " is thinking");
                Thread.sleep(100);

                System.out.println("philosipher " + id + " is hungry");
                dp1.getForks(id);

                System.out.println("philosipher " + id + " is eating");
                Thread.sleep(50);

                System.out.println("philosipher " + id + " is done eating");
                dp1.releaseForks(id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void shutdown() {
        stop = false;
    }
}
