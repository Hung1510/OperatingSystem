
import java.util.ArrayList;
import java.util.Arrays;

public class main1 {
    public static void main(String[] args) {
        int[] available = {3, 3, 0};
        int[][] allocation = {
            {1, 0, 1},
            {1, 1, 2},
            {1, 0, 3},
            {2, 0, 0},
        };
        int[][] maximum = {
            {4, 3, 1},
            {2, 1, 4},
            {1, 3, 3},
            {5, 4, 1},
        };
        
        try {
            Banker banker = new Banker(available, maximum, allocation);
            System.out.println("Need: " + Arrays.deepToString(banker.getNeed())+"\n");
            System.out.println("Initial Safe State: " + banker.isSafeState());

            //
            int[] request = {3, 3, 1};
            ArrayList<Integer> result = banker.request(3, request);
            if (result != null) {
                System.out.println("Request granted. System remains in a safe state.");
                System.out.println("Initial Safe State: " + result);
                System.out.println("Current state: ");
                System.out.println("Available: " + Arrays.toString(banker.getAvailable()));
                System.out.println("Allocation: " + Arrays.deepToString(banker.getAllocation()));
                System.out.println("Need: " + Arrays.deepToString(banker.getNeed()));
            } else {
                System.out.println("Request denied. System would be in an unsafe state.");
            }

        } catch (Exception e) {
        }
    }
}
