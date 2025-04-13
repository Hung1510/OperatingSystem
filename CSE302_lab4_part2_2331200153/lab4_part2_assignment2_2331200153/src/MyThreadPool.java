import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {

    private volatile boolean running = true;
    private int size;
    private ArrayList<Thread> threads = new ArrayList<>();
    private LinkedList<Runnable> tasks = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    public MyThreadPool(int size) {
        this.size = size;
        for (int i = 0; i < this.size; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    Runnable task = null;
                    lock.lock();
                    try {
                        while (tasks.isEmpty() && running) {
                            cond.await();
                        }
                        if (!running && tasks.isEmpty()) {
                            break;
                        }
                        task = tasks.poll();
                    } catch (InterruptedException e) {
                        break;
                    } finally {
                        lock.unlock();
                    }
                    if (task != null) {
                        task.run();
                    }
                }
            });
            threads.add(t);
            t.start();
        }
    }

    public MyThreadPool() {
        this(3);
    }

    public void add(Runnable task) {
        lock.lock();
        try {
            tasks.add(task);
            cond.signal();
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() throws InterruptedException {
        lock.lock();
        try {
            running = false;
            cond.signalAll();
        } finally {
            lock.unlock();
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println(" shutdown complete ");
    }
}
