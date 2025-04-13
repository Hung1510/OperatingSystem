public class Assignment1 {
    public static void main(String[] args) {
        int address = 19986;
        int page = 4096;
        int pageNumber = address / page;
        int offset = address % page;
        System.out.println("address is: " + address);
        System.out.println("page number = " + pageNumber);
        System.out.println("offset = " + offset);
    }
}