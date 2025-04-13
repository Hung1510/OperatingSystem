import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
    private int size;
    private Lock lock;
    private LinkedList<Integer> buffer;
    private Condition fullcond;
    private Condition emptycond;

    public BoundedBuffer(int size) {
        this.size = size;
        this.lock = new ReentrantLock();
        this.buffer = new LinkedList<>();
        this.fullcond = lock.newCondition();
        this.emptycond = lock.newCondition();
    }

    public void add(int v) throws InterruptedException {
        this.lock.lock();
        try {
            while (this.buffer.size() == size) {
                this.fullcond.await();
            }
            this.buffer.add(v);
            this.emptycond.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public int remove() throws InterruptedException {
        int value;
        this.lock.lock();
        try {
            while (this.buffer.isEmpty() == true) {
                this.emptycond.await();
            }
            value = this.buffer.removeFirst();
            this.fullcond.signal();
            return value;
        } finally {
            this.lock.unlock();
        }

    }

    public LinkedList<Integer> getBufer() {
        return (LinkedList<Integer>) this.buffer.clone();
    }
}
