
import java.util.ArrayList;

public class DiskScheduling {

    int currentPosition;
    int[] requests;
    int timeMove;
    int cylinders;
    //

    public DiskScheduling(int currentPosition, int[] requests, int timeMove, int cylinders) {
        this.currentPosition = currentPosition;
        this.requests = requests;
        this.timeMove = timeMove;
        this.cylinders = cylinders;
    }
    //Scan

    public void sstf() {
        System.out.println("==== Shortest Seek Time First ====\n");
        int tempCurrentPosition = currentPosition;
        ArrayList<Integer> requestList = new ArrayList<>();
        for (int request : requests) {
            requestList.add(request);
        }

        int totalSeekTime = 0;
        System.out.println("Move" + " ".repeat(20) + "Distance");

        while (!requestList.isEmpty()) {
            // Find the closest request to the current position
            int closestRequest = findClosest(requestList, tempCurrentPosition);
            int distance = Math.abs(tempCurrentPosition - closestRequest);

            // Print the move and distance
            print(tempCurrentPosition, closestRequest, distance);

            // Update total seek time and current position
            totalSeekTime += distance;
            tempCurrentPosition = closestRequest;

            // Remove the processed request
            requestList.remove((Integer) closestRequest);
        }

        // Calculate total time in nanoseconds
        int totalTime = totalSeekTime * timeMove;

        System.out.println("\nTotal seek time = (" + totalSeekTime + ") * " + timeMove + " = " + totalTime + " ns\n");
    }
    static void running(ArrayList<Integer> requestList, int tempCurrentPosition, int timeMove){
        int totalSeekTime = 0;
        while (!requestList.isEmpty()) {
            int nextRequest = requestList.get(0);
            int distance = Math.abs(tempCurrentPosition - nextRequest);
            print(tempCurrentPosition, nextRequest, distance);
            totalSeekTime += distance;
            tempCurrentPosition = nextRequest;
            requestList.remove((Integer) nextRequest);
        }
        int totalTime = totalSeekTime * timeMove;

        System.out.println("\nTotal seek time = (" + totalSeekTime + ") * " + timeMove + " = " + totalTime + " ns\n");
    }

    //FIFS
    public void fifs() {
        System.out.println("==== First In First Serve ====\n");
        int tempCurrentPosition = currentPosition;
        ArrayList<Integer> requestList = new ArrayList<>();
        for (int request : requests) {
            requestList.add(request);
        }
        System.out.println("Move" + " ".repeat(20) + "Distance");
        running(requestList, tempCurrentPosition, timeMove);

    }

    //Scan
    public void scan() {
        System.out.println("==== Scan ====\n");
        ArrayList<Integer> requestList = new ArrayList<>();
        int tempCurrentPosition = currentPosition;
        requestList.add(currentPosition);
        for (int request : requests) {
            requestList.add(request);
        }
        requestList.sort(null);
        ArrayList<Integer> newRequestList = new ArrayList<>();
        for(int i=requestList.indexOf(currentPosition)+1; i<requestList.size(); i++){
            newRequestList.add(requestList.get(i));
        }
        newRequestList.add(cylinders - 1);
        for(int i=requestList.indexOf(currentPosition)-1; i>=0; i--){
            newRequestList.add(requestList.get(i));
        }
        System.out.println("Move" + " ".repeat(20) + "Distance");
        running(newRequestList, tempCurrentPosition, timeMove);
    }

    // C-Scan
    public void Cscan() {
        System.out.println("==== C-Scan ====\n");
        ArrayList<Integer> requestList = new ArrayList<>();
        int tempCurrentPosition = currentPosition;
        requestList.add(currentPosition);
        for (int request : requests) {
            requestList.add(request);
        }
        requestList.sort(null);
        ArrayList<Integer> newRequestList = new ArrayList<>();
        for(int i=requestList.indexOf(currentPosition)+1; i<requestList.size(); i++){
            newRequestList.add(requestList.get(i));
        }
        newRequestList.add(cylinders - 1);
        newRequestList.add(0);
        for(int i=0; i<requestList.indexOf(currentPosition); i++){
            newRequestList.add(requestList.get(i));
        }
        System.out.println("Move" + " ".repeat(20) + "Distance");
        running(newRequestList, tempCurrentPosition, timeMove);

    }

    //look
    public void look() {
        System.out.println("==== Look ====\n");
        ArrayList<Integer> requestList = new ArrayList<>();
        int tempCurrentPosition = currentPosition;
        requestList.add(currentPosition);
        for (int request : requests) {
            requestList.add(request);
        }
        requestList.sort(null);
        ArrayList<Integer> newRequestList = new ArrayList<>();
        for(int i=requestList.indexOf(currentPosition)+1; i<requestList.size(); i++){
            newRequestList.add(requestList.get(i));
        }
        for(int i=requestList.indexOf(currentPosition)-1; i>=0; i--){
            newRequestList.add(requestList.get(i));
        }
        System.out.println("Move" + " ".repeat(20) + "Distance");
        running(newRequestList, tempCurrentPosition, timeMove);
    }

    // C-Look
    public void Clook() {
        System.out.println("==== C-Look ====\n");
        ArrayList<Integer> requestList = new ArrayList<>();
        int tempCurrentPosition = currentPosition;
        requestList.add(currentPosition);
        for (int request : requests) {
            requestList.add(request);
        }
        requestList.sort(null);
        ArrayList<Integer> newRequestList = new ArrayList<>();
        for(int i=requestList.indexOf(currentPosition)+1; i<requestList.size(); i++){
            newRequestList.add(requestList.get(i));
        }
        newRequestList.add(0);
        for(int i=0; i<requestList.indexOf(currentPosition); i++){
            newRequestList.add(requestList.get(i));
        }
        System.out.println("Move" + " ".repeat(20) + "Distance");
        running(newRequestList, tempCurrentPosition, timeMove);

    }

    // Helper function to find the closest request to the current position
    private static int findClosest(ArrayList<Integer> requests, int currentPosition) {
        int closest = requests.get(0);
        int minDistance = Math.abs(currentPosition - closest);

        for (int request : requests) {
            int distance = Math.abs(currentPosition - request);
            if (distance < minDistance) {
                closest = request;
                minDistance = distance;
            }
        }

        return closest;
    }

    static void print(int from, int to, int distance) {
        String a = from + " -> " + to;
        a += " ".repeat(16 - a.length()) + "|" + " ".repeat(7) + distance;
        System.out.println(a);
    }
    
}
