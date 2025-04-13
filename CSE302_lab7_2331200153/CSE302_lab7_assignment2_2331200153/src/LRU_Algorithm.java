import java.util.LinkedHashMap;
import java.util.Map;

public class LRU_Algorithm implements ReplacementPolicy {
    private int entryNum;
    private LinkedHashMap<Integer, Integer> lruMap;

    public LRU_Algorithm(int entryNum) {
        this.entryNum = entryNum;
        this.lruMap = new LinkedHashMap<>(entryNum, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > entryNum; // loai bo muc cu khi vuot qua suc chua
            }
        };
    }

    @Override
    public void remove(int value) {
        if (lruMap.containsKey(value)) {
            lruMap.remove(value);
        }
    }

    @Override
    public Result refer(int value) {
        if (lruMap.containsKey(value)) {
            int position = lruMap.get(value);
            return new Result(true, value, position, -1);
        }
        int replacePosition = -1;
        int replaceValue = -1;

        if (lruMap.size() == entryNum) {
            Map.Entry<Integer, Integer> eldest = lruMap.entrySet().iterator().next();
            replaceValue = eldest.getKey();
            replacePosition = eldest.getValue();
            lruMap.remove(replaceValue); // xoa trang cu nhat
        }

        replacePosition = (replacePosition == -1) ? lruMap.size() : replacePosition;
        lruMap.put(value, replacePosition);

        return new Result(false, value, replacePosition, replaceValue);
    }

    public static void main(String[] args) {
        int[] references = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };
        ReplacementPolicy policy = new LRU_Algorithm(3);
        int pageFaultsCount = 0;

        for (int i = 0; i < references.length; i++) {
            Result res = policy.refer(references[i]);
            if (!res.isFound()) {
                pageFaultsCount++;
            }
        }

        System.out.println("LRU Page Replacement: " + pageFaultsCount);
    }
}
