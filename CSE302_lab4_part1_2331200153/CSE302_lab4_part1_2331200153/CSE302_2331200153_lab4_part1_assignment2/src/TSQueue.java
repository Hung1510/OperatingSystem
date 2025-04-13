import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TSQueue {
    private Lock lock = new ReentrantLock();
    private LinkedList<Integer> list = new LinkedList<>();

    public TSQueue() {

    }

    public void addLast(int v) {
        this.lock.lock();
        try {
            this.list.addLast(v);
        } finally {
            this.lock.unlock();
        }
    }

    public int removeFirst() {
        int value;
        this.lock.lock();
        try {
            if (this.list.isEmpty() == false) {
                value = this.list.removeFirst();
                return value;
            }

        } finally {
            this.lock.unlock();
        }
        return 0;
    }

    public boolean isEmpty() {
        this.lock.lock();
        try {
            return this.list.isEmpty();
        } finally {
            this.lock.unlock();
        }
    }

    public LinkedList<Integer> getList() {
        LinkedList<Integer> returnList = new LinkedList<>();
        this.lock.lock();
        try {
            Iterator<Integer> iter = this.list.iterator();
            while (iter.hasNext() == true) {
                int e = iter.next();
                returnList.addFirst(e);
            }
        } finally {
            this.lock.unlock();
        }
        return returnList;
    }

}
