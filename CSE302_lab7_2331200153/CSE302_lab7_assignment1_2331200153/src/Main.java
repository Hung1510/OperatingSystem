public class Main {
    public static void main(String[] args) {
        int[] references = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};

        //FIFO
        PageReplacementAlgorithm fifoPolicy = new FIFO_Algorithm(3);
        System.out.println("FIFO Page Replacement:");
        executeAlgorithm(fifoPolicy, references);

        //LRU
        PageReplacementAlgorithm lruPolicy = new LRU_Algorithm(3);
        System.out.println("\nLRU Page Replacement:");
        executeAlgorithm(lruPolicy, references);

        //OPT
        PageReplacementAlgorithm optPolicy = new OPT_Algorithm(3, references);
        System.out.println("\nOPT Page Replacement:");
        executeAlgorithm(optPolicy, references);
    }

    private static void executeAlgorithm(PageReplacementAlgorithm policy, int[] references) {
        int pageFaults = 0;
        for (int page : references) {
            Result res = policy.refer(page);
            if (res.isPageFault()) {
                pageFaults++;
            }
            System.out.println("page:" + page + " => " + res);
        }
        System.out.println("total page faults: " + pageFaults);
    }
}