public class Schedule_Info { // dung lop nay neu ko muon toString in output ra
    private int time;
    private Task task;
    private int duration;
    public Schedule_Info(int time, Task task, int duration) {
        this.time = time;
        this.task = task;
        this.duration = duration;
    }
    public int getTime() {
        return time;
    }
    public Task getTask() {
        return task;
    }
    public int getDuration() {
        return duration;
    }
    @Override
    public String toString() {
        return this.time + " : " + this.task + " - duration " + this.duration;
    }
    
}
