
import java.util.Arrays;

public class main2 {
    public static void main(String[] args) {
        int[] available = {3, 2, 1, 1};
        int[][] allocation = {
            {4, 0, 0, 1},
            {1, 1, 0, 0},
            {1, 2, 5, 4},
            {0, 5, 3, 3},
            {0, 2, 1, 2}
        };
        int[][] maximum = {
            {6, 0, 1, 2},
            {1, 7, 5, 0},
            {2, 3, 6, 6},
            {1, 6, 5, 3},
            {1, 6, 5, 6}
        };
        
        try {
            Banker banker = new Banker(available, maximum, allocation);
            System.out.println("Need: " + Arrays.deepToString(banker.getNeed())+"\n");
            System.out.println("Initial Safe State: " + banker.isSafeState());
        } catch (Exception e) {
        }
    }
}
