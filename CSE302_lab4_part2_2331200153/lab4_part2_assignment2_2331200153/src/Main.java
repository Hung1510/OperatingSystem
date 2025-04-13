public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThreadPool tp = new MyThreadPool(5);

        for (int i = 0; i < 100; i++) {
            Task task = new Task(i);
            tp.add(task);
        }

        Thread.sleep(100);
        tp.shutdown();
    }
}

class Task implements Runnable {
    private int index;

    public Task(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        System.out.println(" running: " + this.index + " by " + Thread.currentThread().getName());
    }
}
