import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FirstReaderWriters {
    private int readCount = 0;
    private int writeCount = 0;
    private int waitingWriters = 0;
    private Lock lock = new ReentrantLock();
    private Condition readCond = lock.newCondition();
    private Condition writeCond = lock.newCondition();

    public void read_enter() throws InterruptedException {
        lock.lock();
        try {
            while (writeCount > 0 || waitingWriters > 0) {
                readCond.await();
            }
            readCount++;
        } finally {
            lock.unlock();
        }
    }

    public void read_exit() {
        lock.lock();
        try {
            readCount--;
            if (readCount == 0) {
                writeCond.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void write_enter() throws InterruptedException {
        lock.lock();
        try {
            waitingWriters++;
            while (readCount > 0 || writeCount > 0) {
                writeCond.await();
            }
            waitingWriters--;
            writeCount = 1;
        } finally {
            lock.unlock();
        }
    }

    public void write_exit() {
        lock.lock();
        try {
            writeCount = 0;
            if (waitingWriters > 0) {
                writeCond.signal();
            } else {
                readCond.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
