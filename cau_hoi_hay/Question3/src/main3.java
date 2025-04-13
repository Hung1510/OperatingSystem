public class main3 {
    public static void main(String[] args) throws InterruptedException{
        ShareTable st = new ShareTable();
        Consumer c1 = new Consumer("A",st);
        Consumer c2 = new Consumer("B",st);
        Consumer c3 = new Consumer("C",st);
        c1.start();
        c2.start();
        c3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        c1.shutdown();
        c2.shutdown();
        c3.shutdown();

        try {
            c1.join(); // Wait for c1 to finish
            c2.join(); // Wait for c2 to finish
            c3.join(); // Wait for c3 to finish
        } catch (InterruptedException e) {
        }
        System.out.println("done");
    }
}
class Consumer extends Thread {
    private final String neededIngredient;
    private final ShareTable st;
    private volatile boolean running = true;

    public Consumer(String neededIngredient, ShareTable st) {
        this.neededIngredient = neededIngredient;
        this.st = st;
    }

    @Override
    public void run() {
        while (running) {
            st.agent();
            st.takeIngredient(neededIngredient);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }
}


