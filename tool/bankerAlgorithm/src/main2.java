
import java.util.ArrayList;
import java.util.Arrays;

public class main2 {

    static int[] available = {3, 3, 0};

    static int[][] allocation = {
        {1, 0, 1},
        {1, 1, 2},
        {1, 0, 3},
        {2, 0, 0},};

    static int[][] maximum = {
        {4, 3, 1},
        {2, 1, 4},
        {1, 3, 3},
        {5, 4, 1},};

    // request
    static int[] request = {};

    public static void main(String[] args) {
        try {
            Banker banker = new Banker(available, maximum, allocation);
            System.out.println("Need: " + Arrays.deepToString(banker.getNeed()) + "\n");
            System.out.println("Initial Safe State: " + banker.isSafeState() + "\n");

            //
            if(request.length>0){
                System.out.println("-".repeat(50));
                System.out.println("- requset:");
            }
            ArrayList<Integer> result = banker.request(3, request);
            if (result != null) {
                System.out.println("Request granted. System remains in a safe state.");
                System.out.println("Initial Safe State: " + result + "\n");
            } else {
                System.out.println("Request denied. System would be in an unsafe state.\n");
            }

        } catch (Exception e) {
        }
    }
}
