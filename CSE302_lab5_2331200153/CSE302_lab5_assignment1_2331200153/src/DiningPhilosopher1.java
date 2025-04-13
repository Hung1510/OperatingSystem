import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopher1 {

    private final int N = 5;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition[] conditions;
    private final boolean[] forkAvailable;

    public DiningPhilosopher1() {
        conditions = new Condition[N];
        forkAvailable = new boolean[N];

        for (int i = 0; i < N; i++) {
            conditions[i] = lock.newCondition();
            forkAvailable[i] = true;
        }
    }

    public void getForks(int id) throws InterruptedException {
        lock.lock();
        try {
            int rightFork = id;
            int leftFork = (id + 1) % N;

            while (!forkAvailable[rightFork] || !forkAvailable[leftFork]) {
                conditions[id].await();
            }

            forkAvailable[rightFork] = false;
            forkAvailable[leftFork] = false;
        } finally {
            lock.unlock();
        }
    }

    public void releaseForks(int id) {
        lock.lock();
        try {
            int rightFork = id;
            int leftFork = (id + 1) % N;

            forkAvailable[rightFork] = true;
            forkAvailable[leftFork] = true;

            conditions[(id + 1) % N].signal();
            conditions[(id + N - 1) % N].signal();
        } finally {
            lock.unlock();
        }
    }
}