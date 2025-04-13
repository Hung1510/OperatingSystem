
import java.util.ArrayList;

public class Banker {

    private int resourceTypeNum;
    private int customerNum;
    private int[] available;
    private int[][] maximum;
    private int[][] allocation;
    private int[][] need;

    public Banker(int[] avail, int[][] max, int[][] alloc) throws Exception {
        this.available = avail;
        this.maximum = max;
        this.allocation = alloc;
        this.customerNum = max.length;
        this.resourceTypeNum = max[0].length;
        this.need = new int[customerNum][resourceTypeNum];
        for (int i = 0; i < customerNum; i++) {
            for (int j = 0; j < resourceTypeNum; j++) {
                this.need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    public ArrayList<Integer> isSafeState() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> safe = new ArrayList<>();
        int[] work = available.clone();
        boolean[] finish = new boolean[customerNum];
        int count = 0;
        while (count < this.customerNum) {
            for (int i = 0; i < this.customerNum; i++) {
                if (!finish[i]) {
                    sb.append("P"+i+": "+arrToString(need[i])+"<= "+arrToString(work)+": ");
                    boolean canFinish = true;
                    // check
                    for (int j = 0; j < this.resourceTypeNum; j++) {
                        if (need[i][j] > work[j]) {
                            canFinish = false;
                            break;
                        }

                    }
                    sb.append(""+canFinish);
                    if (canFinish) {
                        sb.append(" -> new available = "+arrToString(work)+"+ "+arrToString(allocation[i])+"= ");
                        // update
                        safe.add(i);
                        finish[i] = true;
                        count++;
                        for (int j = 0; j < this.resourceTypeNum; j++) {
                            work[j] += this.allocation[i][j];
                        }
                        sb.append(arrToString(work));
                    }
                    sb.append("\n");
                }
            }
        }
        System.out.println(sb);
        if (safe.size() < customerNum) {
            return null;
        }
        return safe;
    }

    public ArrayList<Integer> request(int custId, int[] request) {
        // request phải <= need và available thì mới đủ bộ nhớ đê cấp
        for (int i = 0; i < this.resourceTypeNum; i++) {
            if (request[i] > this.need[custId][i] || request[i] > this.available[i]) {
                return null;
            }

        }

        // cấp phát tạm thời
        for (int i = 0; i < this.resourceTypeNum; i++) {
            this.available[i] -= request[i];
            this.allocation[custId][i] += request[i];
            this.need[custId][i] -= request[i];
        }

        // kiểm tra safe
        ArrayList<Integer> safe = isSafeState();
        if (safe == null) {
            // khôi phục
            for (int i = 0; i < this.resourceTypeNum; i++) {
                this.available[i] += request[i];
                this.allocation[custId][i] -= request[i];
                this.need[custId][i] += request[i];
            }
            return null;
        }
        return safe;
    }

    public int[] getAvailable() {
        return this.available;
    }

    public int[][] getMaximum() {
        return this.maximum;
    }

    public int[][] getAllocation() {
        return this.allocation;
    }

    public int[][] getNeed() {
        return need;
    }

    public String arrToString(int[] arr){
        String a ="";
        for(var t : arr){
            a+=t;
            a+=" ";
        }
        return a;
    }
}
