import java.util.*;

public class OPT_Algorithm implements PageReplacementAlgorithm {
    private int frameNum;
    private List<Integer> pageList;
    private List<Integer> timeMap;

    public OPT_Algorithm(int frameNum, int[] refer) {
        this.frameNum = frameNum;
        this.timeMap = new ArrayList<>();
        this.pageList = new ArrayList<>();
        for (int page : refer) {
            pageList.add(page);
        }
    }

    @Override
    public Result refer(int page) {
        boolean pageFault = !timeMap.contains(page);
        int replacedPage = -1;

        if (pageFault) {
            if (timeMap.size() == frameNum) {
                replacedPage = findReplacedPage(pageList, timeMap);
                timeMap.set(timeMap.indexOf(replacedPage), page);
            } else {
                timeMap.add(page);
            }
        }
        pageList.remove(0); // loai bo page vua duoc xu ly
        return new Result(pageFault, page, replacedPage);
    }

    private static int findReplacedPage(List<Integer> list, List<Integer> timeMap) {
        int maxIndex = -1;
        int replacedPage = timeMap.get(0);
        for (int page : timeMap) {
            int index = list.indexOf(page);
            if (index == -1) {
                return page; // neu page ko o tuong lai, thay the lap tuc
            }
            if (index > maxIndex) {
                maxIndex = index;
                replacedPage = page;
            }
        }
        return replacedPage;
    }
}