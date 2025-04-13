public class Sudoku {
    private int[][] arr;
    private boolean check;
    private int subSize;
    public Sudoku(int[][] arr, int subSize) {
        this.arr = arr;
        this.check=true;
        this.subSize = subSize;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public boolean checkColumn(int col) {
        boolean[] seen = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i][col];
            if (num < 1 || num > arr.length || seen[num - 1]) {
                return false;
            }
            seen[num - 1] = true;
        }
        return true;
    }

   public boolean checkRow(int row) {
        boolean[] seen = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int num = arr[row][i];
            if (num < 1 || num > arr.length || seen[num - 1]) {
                return false;
            }
            seen[num - 1] = true;
        }
        return true;
    }
    public boolean checkSubgrid(int startRow, int startCol) {
        boolean[] seen = new boolean[arr.length];
        for (int i = 0; i < subSize; i++) {
            for (int j = 0; j < subSize; j++) {
                int num = arr[startRow + i][startCol + j];
                if (num < 1 || num > arr.length || seen[num - 1]) {
                    return false;
                }
                seen[num - 1] = true;
            }
        }
        return true;
    }
    
}

