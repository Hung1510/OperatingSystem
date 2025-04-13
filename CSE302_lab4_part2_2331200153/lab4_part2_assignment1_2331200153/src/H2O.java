
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class H2O {

    private boolean fairness;
    private int countH = 0;
    private int countO = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition waitCondH = this.lock.newCondition();
    private Condition waitCondO = this.lock.newCondition();
    private LinkedBlockingQueue<Thread> queueH = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Thread> queueO = new LinkedBlockingQueue<>();

    public H2O(boolean fairness) {
        this.fairness = fairness;
    }

    public void hydrogen() throws InterruptedException {
        this.lock.lock();
        try {
            this.countH++;
            if (this.countO < 1 || this.countH < 2) {
                this.awaitH(fairness);
            } else {
                this.countH -= 2;
                this.countO--;
                this.signalH(fairness);
                this.signalO(fairness);

            }
        } finally {
            this.lock.unlock();
        }
    }

    public void oxygen() throws InterruptedException {
        this.lock.lock();
        try {
            this.countO++;
            if (countH < 2) {
                this.awaitO(fairness);
            } else {
                this.countH -= 2;
                this.countO--;
                this.signalH(fairness);
                this.signalH(fairness);
            }
        } finally {
            this.lock.unlock();
        }
    }

    // base on fairness
    public void awaitH(boolean fairness) throws InterruptedException {
        this.lock.lock();
        try {
            if (fairness) {
                this.queueH.put(Thread.currentThread());
                this.waitCondH.await();

            } else {
                this.waitCondH.await();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void signalH(boolean fairness) {
        this.lock.lock();
        try {
            if (fairness) {
                Thread thread = queueH.poll();
                if (thread != null) {
                    this.waitCondH.signal();
                }
            } else {
                this.waitCondH.signal();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void awaitO(boolean fairness) throws InterruptedException {
        this.lock.lock();
        try {
            if (fairness) {
                this.queueO.put(Thread.currentThread());
                this.waitCondO.await();

            } else {
                this.waitCondO.await();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public void signalO(boolean fairness) {
        this.lock.lock();
        try {
            if (fairness) {
                Thread thread = queueO.poll();
                if (thread != null) {
                    this.waitCondO.signal();
                }
            } else {
                this.waitCondO.signal();
            }
        } finally {
            this.lock.unlock();
        }
    }
}
