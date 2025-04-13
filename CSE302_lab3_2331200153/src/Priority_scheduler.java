import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Priority_scheduler implements Scheduler {

    @Override
    public List<String> schedule(List<Task> queue) {
        Collections.sort(queue, Comparator.comparingInt(Task::getPriority));

        List<String> output = new ArrayList<>();
        int currentTime = 0;

        for (Task task : queue) {
            int duration = task.getBurst();
            output.add(currentTime + task.toString() + " - Duration: " + duration);
            currentTime += duration;
        }

        return output;
    }
}
