
public class main4 {

    static int cylinders = 100;
    static int currentPosition = 49;
    static int[] requests = {20, 34, 12, 50, 24, 44, 65, 78, 33};
    static int timeMove = 2;

    // 1: true,    0:false
    static int SSTF = 1;
    static int FIFS = 0;
    static int Scan = 0;
    static int C_Scan = 0;
    static int Look = 0;
    static int C_Look = 0;

    public static void main(String[] args) {
        DiskScheduling disk = new DiskScheduling(currentPosition, requests, timeMove, cylinders);
        if (SSTF==1) {
            disk.sstf();
            System.out.println("-".repeat(50));
        }
        if (FIFS==1) {
            disk.fifs();
            System.out.println("-".repeat(50));
        }
        if (Scan==1) {
            disk.scan();
            System.out.println("-".repeat(50));
        }
        if (C_Scan==1) {
            disk.Cscan();
            System.out.println("-".repeat(50));
        }
        if (Look==1) {
            disk.look();
            System.out.println("-".repeat(50));
        }
        if (C_Look==1) {
            disk.Clook();
        }
    }
}
