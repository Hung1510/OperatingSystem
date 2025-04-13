public class Main {
    public static final int N = 1000000;

    public static void main(String[] args) throws InterruptedException{
        Data shareData = new Data(0);
        Thread t1 = new IncreaseThread(shareData);
        Thread t2 = new DecreaseThread(shareData);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(shareData.getData());
    }


    ///////////
    static class IncreaseThread extends Thread {
        private Data shareData;

        public IncreaseThread(Data d) {
            this.shareData = d;
        }

        @Override
        public void run() {
            for (int i = 0; i < Main.N; i++) {
                this.shareData.increase();
            }
        }

    }

    ///////////
    static class DecreaseThread extends Thread {
        private Data shareData;

        public DecreaseThread(Data d) {
            this.shareData = d;
        }

        @Override
        public void run() {
            for (int i = 0; i < Main.N; i++) {
                this.shareData.decrease();
            }
        }
    }
}
    /////////////




    
/* 
// Main for Semaphore
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