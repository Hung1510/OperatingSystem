import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        FirstReaderWriters rw = new FirstReaderWriters();

        List<Thread> readThreads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new ReadThread(rw);
            readThreads.add(t);
            t.start();
        }

        List<Thread> writeThreads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread t = new WriteThread(rw);
            writeThreads.add(t);
            t.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        for (Thread t : readThreads) {
            ((ReadThread) t).shutdown();
        }
        for (Thread t : writeThreads) {
            ((WriteThread) t).shutdown();
        }
    }
}

class ReadThread extends Thread {
    private FirstReaderWriters rw;
    private volatile boolean running;

    public ReadThread(FirstReaderWriters rw) {
        this.rw = rw;
        this.running = true;
    }

    @Override
    public void run() {
        Random rd = new Random();
        while (this.running) {
            try {
                this.rw.read_enter();
                System.out.println(Thread.currentThread().getName() + " is reading...");
                Thread.sleep(rd.nextInt(100));
                System.out.println(Thread.currentThread().getName() + " done ");
                this.rw.read_exit();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void shutdown() {
        this.running = false;
        this.interrupt();
    }
}

class WriteThread extends Thread {
    private FirstReaderWriters rw;
    private volatile boolean running;

    public WriteThread(FirstReaderWriters rw) {
        this.rw = rw;
        this.running = true;
    }

    @Override
    public void run() {
        Random rd = new Random();
        while (this.running) {
            try {
                this.rw.write_enter();
                System.out.println(Thread.currentThread().getName() + " is writing...");
                Thread.sleep(rd.nextInt(100));
                System.out.println(Thread.currentThread().getName() + " done ");
                this.rw.write_exit();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void shutdown() {
        this.running = false;
        this.interrupt();
    }
}
