import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FIFO_Algorithm implements PageReplacementAlgorithm {
    private int frameNum;
    private Result res;
    private Queue<Integer> queue = new LinkedList<>();// check fifo order
    private HashSet<Integer> memory_lookUp = new HashSet<>();// check existance of an page
    // we have { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 }
    // with 3 frame a time

    public FIFO_Algorithm(int frameNum) {
        this.frameNum = frameNum;
    }

    @Override
    public Result refer(int page) {
        boolean pageFault = false;
        int replacedPage = -1;
        if (!memory_lookUp.contains(page)) { // if page not in memory, page fault occurs
            pageFault = true;
            if (queue.size() >= frameNum) {
                replacedPage = queue.poll();// remove the oldest page from queue
                memory_lookUp.remove(replacedPage);// remove the same page from memory
            }
            queue.add(page);// add a new page
            memory_lookUp.add(page);
        }
        res = new Result(pageFault, page, replacedPage);
        return res;
    }
}
/*
private ArrayLIst<Integer> frames= new ArrayList<>;
@Override
public Result refer(int page) {
        int frame = frames.indexOf(page);
        
        if (frame >= 0) { // Page is already in memory (No page fault)
            // Move page to the end of the queue (Most Recently Used)
            queue.remove((Integer) page);
            queue.addLast(page);
            return new Result(false, frame, -1);
        } else { // Page fault occurs
            if (frames.size() < frameNum) { // Still space available
                frames.add(page);
                queue.addLast(page);
                return new Result(true, frames.size() - 1, -1);
            } else { // Replace LRU page
                int replacedPage = queue.removeFirst(); // Remove Least Recently Used page
                int replacedIndex = frames.indexOf(replacedPage); // Find its index in frames
                
                frames.set(replacedIndex, page); // Replace in memory
                queue.addLast(page); // Update usage order
                
                return new Result(true, replacedIndex, replacedPage);
            }
        }
    }
}
 */