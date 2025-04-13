import java.io.IOException;
import java.io.RandomAccessFile;

public class VirtualMemoryManager {

    private int pageSize;
    private TLB tlb;
    private PageTable pageTable;
    private PhysicalMemory physicalMemory;
    private BackingStore backingStore;
    private int pageFaultsMiss = 0;
    private int pageFaultsCount = 0;

    public VirtualMemoryManager(int pageSize, int pageNum, int tlbEntry, int frameNum,
            String policyName, String backingStroreFilename) throws IOException {
        this.pageSize = pageSize;
        this.tlb = new TLB(tlbEntry, policyName);
        this.pageTable = new PageTable(pageNum);
        this.physicalMemory = new PhysicalMemory(pageSize, frameNum, policyName);
        this.backingStore = new BackingStore(pageSize, pageNum, backingStroreFilename);

    }

    public byte read(int logicalAdress) throws IOException {
        int page = logicalAdress / this.pageSize;
        int offset = logicalAdress % this.pageSize;
        TLB.TLBResult tLBResult = this.tlb.refer(page);
        if (tLBResult.hit == true) {
            this.physicalMemory.refer(page);
            return this.physicalMemory.getByte(tLBResult.frame, offset);
        }
        // tlb miss
        this.pageFaultsMiss++;
        int frame = this.pageTable.refer(page);
        if (frame != -1) {
            // update
            this.tlb.update(tLBResult.position, page, frame);
            this.physicalMemory.refer(page);
            return this.physicalMemory.getByte(frame, offset);
        }
        // page fault
        this.pageFaultsCount++;
        ReplacementPolicy.Result result = this.physicalMemory.refer(page);
        if (result.isFound() == true) {
            throw new IllegalAccessError(" not found in physical memory");
        }
        frame = result.getPosition();
        int replacedPage = result.getReplaceValue();
        // swap
        this.backingStore.swapIn(page, this.physicalMemory.getMemory(), frame);
        // Update page table
        this.pageTable.update(page, frame, replacedPage);
        // Update TLB
        this.tlb.update(tLBResult.position, page, frame);
        return this.physicalMemory.getByte(frame, offset);
    }

    public int getPageFaultsMiss() {
        return pageFaultsMiss;
    }

    public int getPageFaultsCount() {
        return pageFaultsCount;
    }

}

class TLB {
    private int entryNum;
    private int[][] table;
    private ReplacementPolicy replacePolicy;

    public TLB(int tlbEntry, String policyName) {
        this.entryNum = tlbEntry;
        this.table = new int[this.entryNum][2];
        for (int i = 0; i < this.entryNum; i++) {
            this.table[i][0] = -1;
            this.table[i][1] = -1;
        }
        if (policyName.trim().equalsIgnoreCase("FIFO") == true) {
            this.replacePolicy = new FIFO_Algorithm(this.entryNum);
        } else if (policyName.trim().equalsIgnoreCase("LRU") == true) {
            this.replacePolicy = new LRU_Algorithm(this.entryNum);
        } else {
            throw new IllegalAccessError("unknown page replacement policy: " + policyName);
        }
    }

    public TLBResult refer(int page) {
        ReplacementPolicy.Result policyResult = this.replacePolicy.refer(page);
        TLBResult tlbResult = new TLBResult();
        tlbResult.hit = policyResult.isFound();
        tlbResult.position = policyResult.getPosition();
        tlbResult.page = page;
        tlbResult.frame = this.table[tlbResult.position][1];
        tlbResult.replacePage = policyResult.getReplaceValue();
        return tlbResult;
    }

    public class TLBResult {
        boolean hit;
        int page;
        int frame;
        int position;
        int replacePage;
    }

    public void update(int position, int page, int frame) {
        if (position < 0 || position >= this.entryNum) {
            position = this.replacePolicy.refer(page).getPosition();
        }
        // update TLB
        this.table[position][0] = page;
        this.table[position][1] = frame;
    }
}

class PageTable {

    private int pageNum;
    private int[] frames;

    public PageTable(int pageNum) {
        this.pageNum = pageNum;
        this.frames = new int[this.pageNum];
        for (int i = 0; i < this.pageNum; i++) {
            frames[i] = -1;
        }
    }

    public int refer(int page) {
        return this.frames[page];
    }

    public void update(int page, int frame, int replacedPage) {
        if (replacedPage != -1) {
            this.frames[replacedPage] = -1;
        }
        this.frames[page] = frame;
    }

}

// add lru
class PhysicalMemory {

    private int frameSize;
    private int frameNum;
    private byte[] memory;
    private ReplacementPolicy replacePolicy;

    public PhysicalMemory(int pageSize, int frameNum, String policyName) {
        this.frameSize = pageSize;
        this.frameNum = frameNum;
        this.memory = new byte[this.frameNum * this.frameSize];
        if (policyName.trim().equalsIgnoreCase("FIFO") == true) {
            this.replacePolicy = new FIFO_Algorithm(this.frameNum);
        } else if (policyName.trim().equalsIgnoreCase("LRU") == true) {
            this.replacePolicy = new LRU_Algorithm(this.frameNum);
        } else {
            throw new IllegalAccessError("unknown page replacement policy: " + policyName);
        }
    }

    public byte getByte(int frame, int offset) {
        return this.memory[this.frameSize * frame + offset];
    }

    public ReplacementPolicy.Result refer(int page) {
        ReplacementPolicy.Result result = this.replacePolicy.refer(page);
        return result;
    }

    public byte[] getMemory() {
        return this.memory;
    }

}

class BackingStore {

    private int pageSize;
    private int pageNum;
    private RandomAccessFile backingFile;

    public BackingStore(int pageSize, int pageNum, String backingStroreFilename) throws IOException {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.backingFile = new RandomAccessFile(backingStroreFilename, "r");
        if (backingFile.length() != this.pageSize * this.pageNum) {
            throw new IllegalAccessError(
                    "Backing file: " + backingStroreFilename + " inavalid size: " + backingFile.length());
        }
    }

    public void swapIn(int page, byte[] memory, int frame) throws IOException {
        try {
            this.backingFile.seek((long) this.pageSize * page);
            this.backingFile.readFully(memory, this.pageSize * frame, this.pageSize);
        } catch (IOException e) {
            throw new IOException("Error reading from backing store for page " + page, e);
        }

    }
}