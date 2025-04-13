public class Main {
    static int N;

    public static void main(String[] args) throws InterruptedException {
        N = 100000;
        BoundedBuffer queue = new BoundedBuffer(100);
        Thread t1 = new addThread(queue);
        Thread t2 = new removeThread(queue);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(queue.getBufer().size());
    }
}

class addThread extends Thread {
    BoundedBuffer queue;

    public addThread(BoundedBuffer queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.N; i++) {
            try {
                queue.add(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class removeThread extends Thread {
    BoundedBuffer queue;

    public removeThread(BoundedBuffer queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < Main.N; i++) {
            try {
                queue.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
