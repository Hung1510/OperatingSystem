import java.util.*;

public class LRU_Algorithm implements PageReplacementAlgorithm {
    private int frameNum;
    private TreeMap<Long, Integer> pageMap; // Timestamp → Page
    private HashMap<Integer, Long> timeMap; // Page → Timestamp
    private long timeCounter; // simulate timestamp

    public LRU_Algorithm(int frameNum) {
        this.frameNum = frameNum;
        this.pageMap = new TreeMap<>(); // automaticaly sort by timestamp
        this.timeMap = new HashMap<>(); // lookup for page timestamps
        this.timeCounter = 0;
    }

    @Override
    public Result refer(int page) {
        int replacedPage = -1;
        boolean pageFault = false;

        if (!timeMap.containsKey(page)) { // Page Fault
            pageFault = true;

            if (timeMap.size() == frameNum) { // f full, remove lru page
                Map.Entry<Long, Integer> lruEntry = pageMap.firstEntry(); // the oldeest timestamp
                replacedPage = lruEntry.getValue();
                pageMap.remove(lruEntry.getKey()); // remove from TreeMap
                timeMap.remove(replacedPage); // remove from HashMap
            }
        } else {
            pageMap.remove(timeMap.get(page));// remove old timestamp before update new page
        }
        timeMap.put(page, timeCounter);// update the new timestamp for this page
        pageMap.put(timeCounter, page);
        timeCounter++; // increment timestamp
        Result res = new Result(pageFault, page, replacedPage);
        return res;
    }
}
