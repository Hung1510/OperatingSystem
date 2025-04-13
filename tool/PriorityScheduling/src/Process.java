public class Process implements Cloneable{
    String id;
    int arrivalTime;
    int burstTime;
    int tempBurstTime;
    int priority;
    int remainingTime;
    int finishTime;

    public Process(String id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.tempBurstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }

    @Override
    public Process clone() {
        try {
            return (Process) super.clone(); // Sử dụng clone của Object
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Không xảy ra vì lớp này implement Cloneable
        }
    }
}
