
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main1 {

    // nhập dữ liệu vào file ScheduleInput.txt

    // 1: true,    0:false
    static int priority = 1;
    static int shortestJobFirst = 0;
    static int firstInFirstServe = 0;
    static int roundRobin = 0;              static int quantum = 10;
    static int priority_RoundRobin = 0;     static int quantumPr = 10;

    public static void main(String[] args) throws IOException {
        String filename = "ScheduleInput.txt";
        List<Process> processes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if(!line.equals("")){
                String[] a = line.split("\\s");
                processes.add(new Process(a[0], Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3])));
            } 
        }
        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));
        Scheduling sch = new Scheduling();

        //priority
        if (priority==1) {
            List<Process> result = sch.priority(processes);
            print(result, sch);
            System.out.println("-".repeat(50));
        }

        //SJF
        if (shortestJobFirst==1) {
            sch.sjf(processes);
            System.out.println("-".repeat(50));
        }

        //FIFS
        if (firstInFirstServe==1) {
            sch.fifs(processes);
            System.out.println("-".repeat(50));
        }

        //Round robin
        if (roundRobin==1) {
            System.out.println("Gantt Chart of round robin:");
            List<Process> result1 = sch.rr(processes, quantum, 0);
            sch.calculateTimes(result1);
            System.out.println("-".repeat(50));
        }

        //Priority with round robin
        if (priority_RoundRobin==1) {
            List<Process> result2 = sch.priorityRR(processes, quantumPr);
            sch.calculateTimes(result2);
        }

    }

    static void print(List<Process> result, Scheduling sch) {
        result.forEach(p -> System.out.println(p.id + ": " + p.remainingTime + " - " + p.finishTime));
        sch.calculateTimes(result);
    }
}
