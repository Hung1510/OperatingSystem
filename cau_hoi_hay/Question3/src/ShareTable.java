import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShareTable {
    private final String[] ingredients = {"A", "B", "C"};
    private final Random random = new Random();
    private final Lock lock = new ReentrantLock();
    private final Condition ingredientAvailable = lock.newCondition();
    private String currentIngredient = null;
    private boolean shuttingDown = false;

    public void takeIngredient(String neededIngredient) {
        lock.lock();
        try {
            while (!shuttingDown && !neededIngredient.equals(currentIngredient)) {
                ingredientAvailable.await();
            }
            if (shuttingDown) {
                return;
            }
            System.out.println("Consumer " + neededIngredient + " picked up ingredient " + currentIngredient);
            currentIngredient = null;
            ingredientAvailable.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void agent() {
        lock.lock();
        try {
            if (currentIngredient == null && !shuttingDown) {
                currentIngredient = ingredients[random.nextInt(3)];
                System.out.println("Agent produced ingredient " + currentIngredient);
                ingredientAvailable.signalAll();
            }
        } finally {
            lock.unlock();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
