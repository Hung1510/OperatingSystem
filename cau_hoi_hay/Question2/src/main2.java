
public class main2 {

    public boolean isSudoku = false;

    public static void main(String[] args) throws InterruptedException {
        int[][] arr_sudoku = {
            {6, 2, 4, 5, 3, 9, 1, 8, 7},
            {5, 1, 9, 7, 2, 8, 6, 3, 4},
            {8, 3, 7, 6, 1, 4, 2, 9, 5},
            {1, 4, 3, 8, 6, 5, 7, 2, 9},
            {9, 5, 8, 2, 4, 7, 3, 6, 1},
            {7, 6, 2, 3, 9, 1, 4, 5, 8},
            {3, 7, 1, 9, 5, 6, 8, 4, 2},
            {4, 9, 6, 1, 8, 2, 5, 7, 3},
            {2, 8, 5, 4, 7, 3, 9, 1, 6}
        };
        int size = arr_sudoku.length;
        int SUBGRID_SIZE = 3;

        Sudoku sudoku = new Sudoku(arr_sudoku, SUBGRID_SIZE);
        Thread column = new CheckColumn(sudoku, size);
        column.start();
        column.join();

        Thread row = new CheckRow(sudoku, size);
        row.start();
        row.join();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Thread subgrid = new checkSubgrid(sudoku, SUBGRID_SIZE * i, SUBGRID_SIZE * j);
                subgrid.start();
                subgrid.join();
            }
        }

        if (sudoku.isCheck()) {
            System.out.println("Sudoku puzzle is valid");
        }
    }
}

class CheckColumn extends Thread {

    private Sudoku sudoku;
    private int numColumn;

    public CheckColumn(Sudoku sudoku, int numColumn) {
        this.sudoku = sudoku;
        this.numColumn = numColumn;
    }

    @Override
    public void run() {
        for (int i = 0; i < numColumn; i++) {
            if (!sudoku.checkColumn(i)) {
                sudoku.setCheck(false);
                throw new IllegalAccessError("Sudoku puzzle is not valid wrong in column: " + (i + 1));
            }
        }
    }
}

class CheckRow extends Thread {

    private Sudoku sudoku;
    private int numRow;

    public CheckRow(Sudoku sudoku, int numRow) {
        this.sudoku = sudoku;
        this.numRow = numRow;
    }

    @Override
    public void run() {
        for (int i = 0; i < numRow; i++) {
            if (!sudoku.checkRow(i)) {
                sudoku.setCheck(false);
                throw new IllegalAccessError("Sudoku puzzle is not valid wrong in row: " + (i + 1));
            }
        }
    }
}

class checkSubgrid extends Thread {

    private int startCol;
    private int startRow;
    private Sudoku sudoku;

    public checkSubgrid(Sudoku sudoku, int startRow, int startCol) {
        this.sudoku = sudoku;
        this.startCol = startCol;
        this.startRow = startRow;
    }

    @Override
    public void run() {
        if (!sudoku.checkSubgrid(startRow, startCol)) {
            sudoku.setCheck(false);
            throw new IllegalAccessError("Sudoku puzzle is not valid wrong Subgrid with start Row: "+(startRow+1)+" , start column: "+(startCol+1));
        }
    }
}
