import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJF_scheduler implements Scheduler {

    @Override
    public List<String> schedule(List<Task> queue) {
        List<String> output = new ArrayList<>();
        Collections.sort(queue, Comparator.comparingInt(Task::getBurst));
        int currentTime = 0;
        for (Task task : queue) {
            int duration = task.getBurst();
            output.add(currentTime + task.toString() + " - Duration: " + duration);
            currentTime += duration;
        }
        return output;
    }
}
