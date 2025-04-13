import java.util.Scanner;

public class Assignment2_Main {
    public static void main(String[] args) {
        ContiguousAllocator cma = new ContiguousAllocator(4096);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("allocator> ");
            String cmdLine = sc.nextLine().trim().toUpperCase();
            if (cmdLine.equals("Q")) {
                break;
            }
            String[] a = cmdLine.split("\\s+");
            boolean result = false;
            switch (a[0]) {
                case "SIZE":
                    if (a.length != 2)
                        continue;
                    try {
                        cma.changeSize(Integer.parseInt(a[1]));
                        System.out.println("change size done");
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    break;
                case "RQ":
                    if (a.length != 4)
                        continue;
                    try {
                        switch (a[3]) {
                            case "F":
                                result = cma.requestFirstFit(a[1], Integer.parseInt(a[2]));
                                break;
                            case "B":
                                result = cma.requestBestFit(a[1], Integer.parseInt(a[2]));
                                break;
                            case "W":
                                result = cma.requestWorstFit(a[1], Integer.parseInt(a[2]));
                                break;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    System.out.println(result ? "request done" : "cant request");
                    break;
                case "RL":
                    if (a.length != 2)
                        continue;
                    cma.release(a[1]);
                    System.out.println("release done");
                    break;
                case "C":
                    cma.compact();
                    System.out.println("compact done");
                    break;
                case "STAT":
                    System.out.println(cma.state());
                    break;
            }
        }
    }
}