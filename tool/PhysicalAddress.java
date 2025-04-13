
import java.util.Arrays;

public class PhysicalAddress {

    static int pageSize = 512;   //byte
    static int logicalAddress = 1124;

    static int[] page = {0, 1, 2, 3};
    static int[] frame = {5, 4, 6, 1};

    public static void main(String[] args) {

        int pageNumber = logicalAddress / pageSize;
        int offset = logicalAddress % pageSize;
        System.out.printf("Page Number = %d / %d = %d\n", logicalAddress, pageSize, pageNumber);
        System.out.printf("Offset = %d %% %d = %d\n", logicalAddress, pageSize, offset);
        Integer[] pageWrapper = Arrays.stream(page).boxed().toArray(Integer[]::new);
        int index = Arrays.asList(pageWrapper).indexOf(pageNumber);
        int physicalAddress = frame[index] * pageSize + offset;
        System.out.printf("Physical Address = (%d * %d) + %d = %d", frame[index], pageSize, offset, physicalAddress);

    }
}
