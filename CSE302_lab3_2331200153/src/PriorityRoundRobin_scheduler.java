import java.util.*;

public class PriorityRoundRobin_scheduler implements Scheduler {
    private static int TIME_QUANTUM = 10;

    @Override
    public List<String> schedule(List<Task> queue) {
        queue.sort(Comparator.comparingInt(Task::getPriority));

        List<String> output = new ArrayList<>();
        int currentTime = 0;
        Map<Integer, Queue<Task>> priorityGroups = new LinkedHashMap<>();
        for (Task task : queue) {
            priorityGroups.computeIfAbsent(task.getPriority(), k -> new LinkedList<>()).add(task);
        }
        for (Queue<Task> taskQueue : priorityGroups.values()) {
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
        }

        return output;
    }
}