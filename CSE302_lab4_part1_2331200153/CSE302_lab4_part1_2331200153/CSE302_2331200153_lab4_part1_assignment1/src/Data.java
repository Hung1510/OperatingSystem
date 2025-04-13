// ReentrantLock
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Data {
    private int data;
    private Lock lock = new ReentrantLock();

    public Data(int d) {
        this.data = d;
    }

    

    public void increase() {
        this.lock.lock();
        this.data = data + 1;
        this.lock.unlock();
    }

    public void decrease() {
        this.lock.lock();
        this.data = data - 1;
        this.lock.unlock();
    }

    public int getData() {
        return data;
    }
}


// synchronized
/* 
public class Data {
    private int data;

    public Data(int d) {
        this.data = d;
    }
    
    public synchronized void increase() {
        this.data = data + 1;
    }

    public synchronized void decrease() {
        this.data = data - 1;
    }

    public int getData() {
        return data;
    }
}
*/

//semaphore
/* 
import java.util.concurrent.Semaphore;
public class Data {
    private int data;
    private Semaphore sem = new Semaphore(1);

    public Data(int d) {
        this.data = d;
    }

    public void increase() throws InterruptedException{
        this.sem.acquire();
        try{
        this.data = data + 1;
        } finally{
            this.sem.release();
        }
    }

    public void decrease() throws InterruptedException{
        this.sem.acquire();
        try{
        this.data = data - 1;
        } finally{
            this.sem.release();
        }
    }

    public int getData() {
        return data;
    }
}
*/