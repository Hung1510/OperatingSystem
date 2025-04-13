
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Boat {
    // 0: linux
    // 1: window
    public static final int MAX = 4;
    private int count0 = 0;
    private int count1 = 0;
    private ReentrantLock lock = new ReentrantLock();
    private Condition waitCond0 = this.lock.newCondition();
    private Condition waitCond1 = this.lock.newCondition();

    public Boat() {

    }

    public void arriveBoat(int direction) throws InterruptedException {
        this.lock.lock();
        try {
            if (direction == 0) {
                while (true) {
                    if (count1 > 0) {
                        this.waitCond0.await();
                    } else if (count0 == MAX) {
                        this.waitCond0.await();
                    } else {
                        this.count0++;
                        break;
                    }
                }

            } else {
                while (true) {
                    if (count0 > 0) {
                        this.waitCond1.await();
                    } else if (count1 == MAX) {
                        this.waitCond1.await();
                    } else {
                        this.count1++;
                        break;
                    }
                }
            }
            System.out.println("on: " + count0 + " " + count1);
        } finally {
            this.lock.unlock();
        }
    }

    public void exitBoat(int direction) throws InterruptedException {
        this.lock.lock();
        try {
            if (direction == 0) {
                this.count0--;
                this.waitCond0.signalAll();
                this.waitCond1.signalAll();
            } else {
                this.count1--;
                this.waitCond1.signalAll();
                this.waitCond0.signalAll();
            }
            System.out.println("out: " + count0 + " " + count1);
        } finally {
            this.lock.unlock();
        }

    }

    public int getCount0() {
        return count0;
    }

    public int getCount1() {
        return count1;
    }
}
