import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String backingFilename = "BACKING_STORE.bin";
        int pageSize = 256;
        int pageNum = 256;
        int frameNum = 32;
        int TLBEntryNum = 16;
        String policyName = "FIFO";
        // String policyName = "LRU";

        BufferedReader addressReader = null;
        try {
            VirtualMemoryManager vmm = new VirtualMemoryManager(pageSize, pageNum, TLBEntryNum,
                    frameNum, policyName, backingFilename);
            byte[] backingStore = Files.readAllBytes(Paths.get(backingFilename));
            addressReader = new BufferedReader(new FileReader("addresses.txt"));
            String line = addressReader.readLine();
            int lineCount = 1;
            while (line != null) {
                int logicalAdress = Integer.parseInt(line);
                System.out.println("line " + lineCount + ": " + logicalAdress);
                byte value = vmm.read(logicalAdress);
                if (value != backingStore[logicalAdress]) {
                    System.err.println("line: " + lineCount + " - " + logicalAdress + " - read value: " + value);
                }
                line = addressReader.readLine();
                lineCount++;

            }
            System.out.println("done");
            System.out.println("miss page: " + vmm.getPageFaultsMiss());
            System.out.println("page Fault: " + vmm.getPageFaultsCount());
        } finally {
            if (addressReader != null) {
                addressReader.close();
            }
        }

    }
}