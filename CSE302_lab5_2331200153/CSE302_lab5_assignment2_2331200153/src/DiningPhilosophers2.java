import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers2 {
    private final int N = 5;
    private int remainForks = N;
    private int eatingCount = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond = this.lock.newCondition();

    public DiningPhilosophers2() {
    }

    public void getForks(int id) throws InterruptedException {
        this.lock.lock();
        try {
            System.out.println("philosopher " + id + " hungry");
            // Get first fork
            while (remainForks < 2) {
                cond.await();
            }
            remainForks--;
            // Get second fork
            while (remainForks < 1) {
                remainForks++; // Release first fork
                cond.signalAll();
                cond.await();
            }
            remainForks--;
            eatingCount++;
            System.out.println("philosopher " + id + " eating");
        } finally {
            this.lock.unlock();
        }
    }

    public void releaseForks(int id) {
        this.lock.lock();
        try {
            eatingCount--;
            remainForks = Math.min(remainForks + 2, N);
            System.out.println("philosopher " + id + " finished eating,now thinking");
            cond.signalAll();
        } finally {
            this.lock.unlock();
        }
    }
}
