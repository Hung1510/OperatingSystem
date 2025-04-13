
import java.util.*;

public class PageReplacement {

    // Thuật toán Optimal
    public static void optimalAlgorithm(int[] ref, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        List<Integer> arrFrame = new ArrayList<>();
        int pageFaults = 0;
        String[] faultStatus = new String[ref.length];

        // Xử lý từng tham chiếu
        for (int i = 0; i < ref.length; i++) {
            int currentPage = ref[i];
            if (!frames.contains(currentPage)) {
                if (frames.size() < frameSize) {
                    frames.add(currentPage);
                } else {
                    int farthest = -1;
                    int pageToReplace = -1;
                    for (int framePage : frames) {
                        int nextUse = findNextUse(ref, i + 1, framePage);
                        if (nextUse == -1) {
                            pageToReplace = framePage;
                            break;
                        }
                        if (nextUse > farthest) {
                            farthest = nextUse;
                            pageToReplace = framePage;
                        }
                    }
                    int index = frames.indexOf(pageToReplace);
                    frames.set(index, currentPage);
                }
                pageFaults++;
                frames.forEach(p -> arrFrame.add(p));
                faultStatus[i] = "yes";
            } else {
                frames.forEach(p -> arrFrame.add(p));
                faultStatus[i] = "no ";
            }
        }
        List<List<Integer>> result = splitArray(arrFrame, frameSize);

        printTable(result, frameSize, ref, faultStatus, pageFaults);
    }

    // Thuật toán LRU
    static void lruAlgorithm(int[] ref, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        List<Integer> arrFrame = new ArrayList<>();
        int pageFaults = 0;
        String[] faultStatus = new String[ref.length];

        // Xử lý từng tham chiếu
        for (int i = 0; i < ref.length; i++) {
            int currentPage = ref[i];
            if (!frames.contains(currentPage)) {
                if (frames.size() < frameSize) {
                    frames.add(currentPage);
                } else {
                    int leastRecent = -1;
                    int pageToReplace = -1;

                    for (int framePage : frames) {
                        int lastUse = findLastUse(ref, i - 1, framePage);
                        if (lastUse == -1) { // Nếu trang chưa từng được sử dụng trước đó
                            pageToReplace = framePage;
                            break;
                        }
                        if (lastUse < leastRecent || leastRecent == -1) { // Tìm trang ít sử dụng nhất trong quá khứ
                            leastRecent = lastUse;
                            pageToReplace = framePage;
                        }
                    }

                    // Thay thế trang ít sử dụng nhất trong quá khứ
                    int index = frames.indexOf(pageToReplace);
                    frames.set(index, currentPage);
                }
                pageFaults++;
                frames.forEach(p -> arrFrame.add(p));
                faultStatus[i] = "yes";
            } else {
                frames.forEach(p -> arrFrame.add(p));
                faultStatus[i] = "no ";
            }
        }
        // In bảng
        List<List<Integer>> result = splitArray(arrFrame, frameSize);
        printTable(result, frameSize, ref, faultStatus, pageFaults);
    }

    // Thuật toán FIFO
    public static void fifoAlgorithm(int[] ref, int frameSize) {
        List<Integer> frames = new ArrayList<>();
        List<Integer> arrFrame = new ArrayList<>();
        String[] faultStatus = new String[ref.length];
        int pageFaults = 0;
        int index = 0;
        // Xử lý từng tham chiếu
        for (int i = 0; i < ref.length; i++) {
            int currentPage = ref[i];

            // Kiểm tra xem trang có trong frame không
            if (!frames.contains(currentPage)) {
                // Nếu frame chưa đầy, thêm trang vào
                if (frames.size() < frameSize) {
                    frames.add(currentPage);
                } else {
                    // Loại bỏ trang theo FIFO và thêm trang mới
                    frames.set(index++%frameSize, currentPage);
                }
                frames.forEach(p -> arrFrame.add(p));
                faultStatus[i] = "yes";
                pageFaults++;
            } else {
                frames.forEach(p -> arrFrame.add(p));
                faultStatus[i] = "no ";
            }
        }

        // In kết quả theo định dạng yêu cầu
        List<List<Integer>> result = splitArray(arrFrame, frameSize);
        printTable(result, frameSize, ref, faultStatus, pageFaults);
    }

    // Tìm vị trí sử dụng tiếp theo
    private static int findNextUse(int[] ref, int start, int page) {
        for (int i = start; i < ref.length; i++) {
            if (ref[i] == page) {
                return i;
            }
        }
        return -1;
    }

    private static int findLastUse(int[] ref, int currentIndex, int page) {
        for (int i = currentIndex; i >= 0; i--) {
            if (ref[i] == page) {
                return i;
            }
        }
        return -1; // Nếu trang chưa từng được sử dụng trước đó
    }

    public static List<List<Integer>> splitArray(List<Integer> arr, int page) {
        List<List<Integer>> result = new ArrayList<>();
        int n = (page + 1) * page / 2;
        int index = 0;
        int k = 1;
        for (int i = 0; i < page; i++) {
            List<Integer> currentGroup = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                if (currentGroup.contains(arr.get(index))) {
                    break;
                }
                currentGroup.add(arr.get(index));
                index++;
            }
            k++;
            result.add(currentGroup);
        }

        while (index < arr.size()) {
            List<Integer> currentGroup = new ArrayList<>();
            for (int j = 0; j < page; j++) {
                currentGroup.add(arr.get(index));
                index++;
            }
            result.add(currentGroup);
        }
        return result;
    }

    public static void printTable(List<List<Integer>> arr, int page, int[] ref, String[] faultStatus, int pageFaults) {
        StringBuilder sb = new StringBuilder();
        for (var t : ref) {
            sb.append(" " + t + "  ");
        }
        sb.append("\n");
        //
        int k = 0;
        int a = 1;
        for (int i = 0; i < page; i++) {

            for (int j = Math.min(page, k); j < arr.size(); j++) {
                sb.append(" " + arr.get(j).get(i) + " |");
            }
            if (arr.get(k).size() == arr.get(k + 1).size()) {
                a++;
                k++;
            }
            k++;
            if (i < page - 1) {
                sb.append("\n" + "   |".repeat(a));
                sb.append("   |".repeat(i));
            }
        }
        //
        sb.append("\n");
        for (var t : faultStatus) {
            sb.append(t + "|");
        }
        sb.append("\n=> number of page faults: " + pageFaults);
        System.out.println(sb);
    }

}
