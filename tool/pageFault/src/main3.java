
public class main3 {

    static int[] referenceString
            = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
    static int frameSize = 3;

    // 1: true,    0:false
    static int OPT = 1;
    static int LRU = 0;
    static int FIFO = 0;

    
    public static void main(String[] args) {
        PageReplacement pr = new PageReplacement();
        //
        if (OPT==1) {
            System.out.println("=== Optimal Page Replacement ===");
            pr.optimalAlgorithm(referenceString, frameSize);
        }
        if (LRU==1) {
            System.out.println("\n=== Least Recently Used (LRU) Page Replacement ===");
            pr.lruAlgorithm(referenceString, frameSize);
        }
        if (FIFO==1) {
            System.out.println("\n=== First In First Out (FIFO) Page Replacement ===");
            pr.fifoAlgorithm(referenceString, frameSize);
        }
   }
}
