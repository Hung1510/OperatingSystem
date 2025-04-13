import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin_scheduler implements Scheduler {
    private static int TIME_QUANTUM = 10;

    @Override
    public List<String> schedule(List<Task> queue) {
        List<String> output = new ArrayList<>();
        Queue<Task> taskQueue = new LinkedList<>(queue);
        int currentTime = 0;

        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            int burst = task.getBurst();

            if (burst <= TIME_QUANTUM) {
                output.add(currentTime + " : " + task.toString() + " - Duration: " + burst);
                currentTime += burst;
            } else {
                output.add(currentTime + " : " + task.toString() + " - Duration: " + TIME_QUANTUM);
                currentTime += TIME_QUANTUM;
                task.setBurst(burst - TIME_QUANTUM);
                taskQueue.add(task);
            }
        }

        return output;
    }
}