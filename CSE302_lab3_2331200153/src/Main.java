import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String task_file = "schedule.txt";

    public static void main(String[] args) {
        List<Task> queue = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Main.task_file));
            String line;
            line = reader.readLine();
            while (line != null) {
                String[] component = line.split(",");
                if (component.length != 3) {
                    System.out.println("invalid format");
                    return;
                }
                String name = component[0].trim();
                int priority = Integer.parseInt(component[1].trim());
                int burst = Integer.parseInt(component[2].trim());
                Task task = new Task(name, priority, burst);
                queue.add(task);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        for (Task task : queue) {
            System.out.println(task);
        }

        // this part to implement 5 assignment(FCFS,SJF,...)
        Scheduler scheduler = new PriorityRoundRobin_scheduler();
        // Scheduler scheduler = new FCFS_scheduler();
        // Scheduler scheduler = new SJF_scheduler();
        // Scheduler scheduler = new Priority_scheduler();
        // Scheduler scheduler = new RoundRobin_scheduler();
        // ap dung tinh da hinh, thay doi scheduler ko can thay doi 3 dong code duoi de
        // doi qua may cai khac
        List<String> results = scheduler.schedule(queue);
        for (String string : results) {
            System.out.println(string);
        }
    }
}