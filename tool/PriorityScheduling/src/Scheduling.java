
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Scheduling {

    static int currentTime;

    public List<Process> printGanttChart(List<String> ganttChart, List<Process> processes) {
        int rank = 0;
        String previous = "";
        int count = 0;
        List<Process> result = new ArrayList<>();
        for (String process : ganttChart) {
            if (!process.equals(previous)) {
                if (count > 0) {
                    rank += count;
                    if (!previous.toLowerCase().equals("null")) {
                        for (var t : processes) {
                            if (t.id.toLowerCase().equals(previous.toLowerCase())) {
                                Process pr = t.clone();
                                pr.remainingTime = (rank - count);
                                pr.finishTime = rank;
                                pr.tempBurstTime = count;
                                result.add(pr);
                                break;
                            }
                        }
                    }
                }
                previous = process;
                count = 1;
            } else {
                count++;
            }
        }
        if (count > 0) {
            rank += count;
            for (var t : processes) {
                if (t.id.toLowerCase().equals(previous.toLowerCase())) {
                    Process pr = t.clone();
                    pr.remainingTime = (rank - count);
                    pr.finishTime = rank;
                    pr.tempBurstTime = count;
                    result.add(pr);
                    break;
                }
            }
        }
        return result;
    }

    // Priority
    public List<Process> priority(List<Process> a) {
        List<Process> processes = new ArrayList<>();
        for (Process p : a) {
            processes.add(p.clone());
        }
        currentTime = 0;
        List<String> ganttChart = new ArrayList<>();

        while (processes.stream().anyMatch(p -> p.remainingTime > 0)) {
            Process currentProcess = processes.stream()
                    .filter(p -> p.arrivalTime <= currentTime && p.remainingTime > 0)
                    .min(Comparator.comparingInt(p -> p.priority))
                    .orElse(null);

            if (currentProcess != null) {
                ganttChart.add(currentProcess.id);
                currentProcess.remainingTime--;

                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime + 1;
                }
            } else {
                ganttChart.add("null");
            }

            currentTime++;
        }
        System.out.println("Gantt Chart of priority:");
        List<Process> result = this.printGanttChart(ganttChart, processes);
        return result;
    }

    // Shortest Job First (SJF)
    public void sjf(List<Process> a) {
        List<Process> processes = new ArrayList<>();
        for (Process p : a) {
            processes.add(p.clone());
        }

        List<Process> result = new ArrayList<>();
        processes.get(0).remainingTime = 0;
        processes.get(0).finishTime = processes.get(0).burstTime;
        result.add(processes.get(0));
        int currentTime = processes.get(0).burstTime;
        processes.remove(0);
        while (!processes.isEmpty()) {
            if (processes.get(0).arrivalTime > currentTime) {
                currentTime = processes.get(0).arrivalTime;
            }
            Process min = processes.get(0);
            for (var t : processes) {
                if (t.arrivalTime <= currentTime && t.burstTime < min.burstTime) {
                    min = t;
                }
            }
            min.remainingTime = currentTime;
            currentTime += min.burstTime;
            min.finishTime = currentTime;
            result.add(min);
            processes.remove(min);
        }
        System.out.println("Gantt Chart of SJF:");
        result.forEach(p -> System.out.println(p.id + ": " + p.remainingTime + " - " + p.finishTime));

        this.calculateTimes(result); // Tính toán thời gian chờ, thời gian hoàn thành, v.v.
    }

    // FIFS
    public void fifs(List<Process> a) {
        List<Process> processes = new ArrayList<>();
        for (Process p : a) {
            processes.add(p.clone());
        }
        List<Process> result = new ArrayList<>();
        int currentTime = 0;

        while (!processes.isEmpty()) {
            if (processes.get(0).arrivalTime > currentTime) {
                currentTime = processes.get(0).arrivalTime;
            }
            Process p = processes.get(0);
            p.remainingTime = currentTime;
            currentTime += p.burstTime;
            p.finishTime = currentTime;
            result.add(p);
            processes.remove(p);
        }
        System.out.println("Gantt Chart of FIFS:");
        result.forEach(p -> System.out.println(p.id + ": " + p.remainingTime + " - " + p.finishTime));

        this.calculateTimes(result);
    }

    // RR
    public List<Process> rr(List<Process> a, int quantum, int currentTime) {
        List<Process> processes = new ArrayList<>();
        for (Process p : a) {
            processes.add(p.clone());
        }
        List<String> table = new ArrayList<>();
        List<Process> result = new ArrayList<>();
        Queue<Process> taskQueue = new LinkedList<>(processes);
        while (!taskQueue.isEmpty()) {
            Process currentTask = taskQueue.poll();
            if (currentTask.arrivalTime > currentTime) {
                taskQueue.add(currentTask);
                continue;
            }
            if (currentTask.tempBurstTime > quantum) {
                table.add(currentTask.id + ": " + currentTime + " - " + (currentTime + quantum));
                currentTime += quantum;
                currentTask.tempBurstTime -= quantum;
                currentTask.finishTime = currentTime;
                taskQueue.add(currentTask);

            } else {
                table.add(currentTask.id + ": " + currentTime + " - " + (currentTime + currentTask.tempBurstTime));
                currentTime += currentTask.tempBurstTime;
                int graph = 0;
                if (taskQueue.peek() != null) {
                    graph = taskQueue.peek().arrivalTime;
                }
                currentTask.finishTime = currentTime;
                currentTime = Math.max(currentTime, graph);
                result.add(currentTask);
            }
        }
        table.forEach(p -> System.out.println(p));
        result.sort((p1, p2) -> p1.id.compareTo(p2.id));
        return result;
    }

    // priority with round robin
    public List<Process> priorityRR(List<Process> a, int quantum) {
        List<Process> processes = new ArrayList<>();
        for (Process p : a) {
            processes.add(p.clone());
        }
        currentTime = 0;
        List<String> ganttChart = new ArrayList<>();

        while (processes.stream().anyMatch(p -> p.remainingTime > 0)) {
            Process currentProcess = processes.stream()
                    .filter(p -> p.arrivalTime <= currentTime && p.remainingTime > 0)
                    .min(Comparator.comparingInt(p -> p.priority))
                    .orElse(null);

            if (currentProcess != null) {
                ganttChart.add(currentProcess.id);
                currentProcess.remainingTime--;

                if (currentProcess.remainingTime == 0) {
                    currentProcess.finishTime = currentTime + 1;
                }
            } else {
                ganttChart.add("null");
            }

            currentTime++;
        }
        System.out.println("Gantt Chart of priority with round robin:");
        List<Process> result = this.printGanttChart(ganttChart, processes);
        List<Process> kq = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            List<Process> roundrr = new ArrayList<>();
            roundrr.add(result.get(i));

            for (int j = i + 1; j < result.size();) {
                if (result.get(i).priority == result.get(j).priority) {
                    if(!result.get(i).id.equals(result.get(j).id)){
                        roundrr.add(result.get(j));
                        result.remove(j);
                    }
                    else{
                        roundrr.clear();
                        roundrr.add(result.get(i));
                        break;
                    }
                } else {
                    j++;
                }
            }

            // Nếu có nhiều hơn 1 phần tử trong nhóm, thực hiện rr
            if (roundrr.size() > 1) {

                List<Process> temp = this.rr(roundrr, quantum, roundrr.get(0).remainingTime);
                kq.addAll(temp); // Thêm tất cả kết quả của rr vào kq
            } else {
                // Nếu chỉ có 1 phần tử, thêm trực tiếp vào kq
                kq.add(result.get(i));
                System.out.println(result.get(i).id + ": " + result.get(i).remainingTime + " - " + result.get(i).finishTime);
            }
        }
        return kq;

    }

    public void calculateTimes(List<Process> processes) {
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;
        Map<String, Process> lastOccurrence = new HashMap<>();

        for (Process process : processes) {
            // Ghi đè phần tử có cùng ID trong Map
            lastOccurrence.put(process.id, process);
        }


        List<Process> uniqueProcesses = new ArrayList<>(lastOccurrence.values());

        System.out.println("\nTurnaround Time:");
        for (Process process : uniqueProcesses) {
            int turnaroundTime = process.finishTime - process.arrivalTime;
            totalTurnaroundTime += turnaroundTime;
            System.out.printf("%s: %d - %d = %d\n", process.id, process.finishTime, process.arrivalTime, turnaroundTime);
        }
        System.out.printf("Average Turnaround Time: %d / %d = %.2f\n", (int) totalTurnaroundTime, processes.size(), totalTurnaroundTime / processes.size());
        System.out.println("\nWaiting Time Time:");
        for (Process process : uniqueProcesses) {
            int waitingTime = process.finishTime - process.arrivalTime - process.burstTime;
            totalWaitingTime += waitingTime;
            System.out.printf("%s: %d - %d = %d\n", process.id, process.finishTime - process.arrivalTime, process.burstTime, waitingTime);
        }
        System.out.printf("Average Waiting Time: %d / %d = %.2f\n\n", (int) totalWaitingTime, processes.size(), totalWaitingTime / processes.size());
    }
}
