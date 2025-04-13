import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;;

public class MyBarrier {
    private int parties;
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition waitCond = this.lock.newCondition();

    public MyBarrier(int parties) {
        this.parties = parties;
    }

    public void await() throws InterruptedException {
        this.lock.lock();
        try {
            this.count++;
            if (this.count < this.parties)
                this.waitCond.await();
            else {
                this.count = 0;
                this.waitCond.signalAll();
            }
        } finally {
            this.lock.unlock();
        }
    }

}