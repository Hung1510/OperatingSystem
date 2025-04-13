import java.util.LinkedList;

public class FIFO_Algorithm implements ReplacementPolicy {
    private int entryNum;
    private int[] values;
    private LinkedList<Integer> positionQue = new LinkedList<>();

    public FIFO_Algorithm(int entryNum) {
        this.entryNum = entryNum;
        this.values = new int[this.entryNum];
        for (int i = 0; i < this.entryNum; i++) {
            this.values[i] = -1;
        }
        for (int i = 0; i < this.entryNum; i++) {
            this.positionQue.add(i);
        }
    }

    @Override
    public void remove(int value) {
        for (int i = 0; i < this.entryNum; i++) {
            if (this.values[i] == value) {
                this.values[i] = -1;
                this.positionQue.remove(i);
                this.positionQue.addFirst(i);
                return;
            }
        }
    }

    @Override
    public Result refer(int value) {
        Result res;
        for (int i = 0; i < this.entryNum; i++) {
            if (this.values[i] == value) {
                res = new Result(true, value, i, -1);
                return res;
            }
        }
        int replacePosition = this.positionQue.removeFirst();
        int replaceValue = this.values[replacePosition];
        this.values[replacePosition] = value;
        this.positionQue.addLast(replacePosition);
        res = new Result(false, value, replacePosition, replaceValue);
        return res;
    }

    public static void main(String[] args) {
        int[] references = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };
        ReplacementPolicy Policy = new FIFO_Algorithm(3);
        int pageFaultsCount = 0;
        for (int i = 0; i < references.length; i++) {
            Result res = Policy.refer(references[i]);
            if (res.isFound() == false) {
                pageFaultsCount++;
            }
        }
        System.out.println("FIFO Page Replacement: " + pageFaultsCount);
    }

}
