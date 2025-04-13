import java.util.TreeSet;

public class ContiguousAllocator {
    private int memorySize;
    private TreeSet<Block> list;

    public ContiguousAllocator(int memorySize) {
        this.memorySize = memorySize;
        this.list = new TreeSet<>((p1, p2) -> Integer.compare(p1.getAddress(), p2.getAddress()));
        this.list.add(new Block(null, 0, this.memorySize));
    }

    public void changeSize(int newSize) {
        this.memorySize = newSize;
        this.list.clear();
        this.list.add(new Block(null, 0, this.memorySize));
    }

    public boolean requestFirstFit(String name, int size) {
        for (Block b : this.list) {
            if (b.getName() == null && size <= b.getSize()) {
                Block newBlock = new Block(null, b.getAddress() + size, b.getSize() - size);
                b.setName(name);
                b.setSize(size);
                if (newBlock.getSize() > 0)
                    this.list.add(newBlock);
                return true;
            }
        }
        return false;
    }

    public boolean requestBestFit(String name, int size) {
        Block minBlock = null;
        for (Block b : this.list) {
            if (b.getName() == null && size <= b.getSize() && (minBlock == null || b.getSize() < minBlock.getSize())) {
                minBlock = b;
            }
        }
        if (minBlock == null)
            return false;
        Block newBlock = new Block(null, minBlock.getAddress() + size, minBlock.getSize() - size);
        minBlock.setName(name);
        minBlock.setSize(size);
        if (newBlock.getSize() > 0)
            this.list.add(newBlock);
        return true;
    }

    public boolean requestWorstFit(String name, int size) {
        Block maxBlock = null;
        for (Block b : this.list) {
            if (b.getName() == null && size <= b.getSize() && (maxBlock == null || b.getSize() > maxBlock.getSize())) {
                maxBlock = b;
            }
        }
        if (maxBlock == null)
            return false;
        Block newBlock = new Block(null, maxBlock.getAddress() + size, maxBlock.getSize() - size);
        maxBlock.setName(name);
        maxBlock.setSize(size);
        if (newBlock.getSize() > 0)
            this.list.add(newBlock);
        return true;
    }

    public void release(String name) {
        for (Block b : this.list) {
            if (name.equals(b.getName())) {
                b.setName(null);
                return;
            }
        }
    }

    public void compact() {
        int currentAddress = 0;
        TreeSet<Block> newList = new TreeSet<>((p1, p2) -> Integer.compare(p1.getAddress(), p2.getAddress()));
        for (Block b : this.list) {
            if (b.getName() != null) {
                b.setAddress(currentAddress);
                currentAddress += b.getSize();
                newList.add(b);
            }
        }
        if (currentAddress < this.memorySize) {
            newList.add(new Block(null, currentAddress, this.memorySize - currentAddress));
        }
        this.list = newList;
    }

    public String state() {
        StringBuilder result = new StringBuilder();
        for (Block b : this.list) {
            result.append("Addresses [").append(b.getAddress()).append(":")
                    .append(b.getAddress() + b.getSize() - 1).append("] ")
                    .append(b.getName() != null ? "Process " + b.getName() : "Unused").append("\n");
        }
        return result.toString();
    }
}