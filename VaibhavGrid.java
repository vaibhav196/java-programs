import java.util.*;
public class VaibhavGrid {

    public final static int DEAD    = 0x00;
    public final static int LIVE    = 0x01;
    public final static void main(String[] args) {
        VaibhavGrid user = new VaibhavGrid();
        user.test(5);
    }

    private void test(int nrIterations) {

        int[][] grid = {{DEAD, DEAD, DEAD, DEAD, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, LIVE, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, LIVE, DEAD},
                         {DEAD, DEAD, DEAD, DEAD, DEAD}}; 
        int state;
        int row;
        int col;
        String[] cellStates = {"DEAD", "LIVE"};
        System.out.println("Grid Representation");
        System.out.println("Current generation is:");
        printBoard(grid);
        System.out.println("Press 1 for next generation, 2 for checking for cell state, 3 for Exit");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        do{
            switch(choice){
                case 1:
                    grid = getNextGrid(grid);
                    printBoard(grid);
                    break;
                case 2:
                    System.out.println("Enter the row number, please note that the row index starts from 0");
                    row = sc.nextInt();
                    System.out.println("Enter the column number, please note that the column index starts from 0");
                    col = sc.nextInt();
                    state = getNewCellState(grid[row][col], getLiveNeighbours(row, col, grid));
                    System.out.println("Cell state is : " + cellStates[state]);                
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please enter correct input");
            }
            System.out.println("Press 1 for next generation, 2 for checking for cell state, 3 for Exit");
            choice = sc.nextInt();
        }while(choice!=0);
    }

    private void printBoard(int[][] grid) {

        for (int i = 0, e = grid.length ; i < e ; i++) {

            for (int j = 0, f = grid[i].length ; j < f ; j++) {
                System.out.print(Integer.toString(grid[i][j]) + ",");
            } 
            System.out.println();
        }
    }

    public int[][] getNextGrid(int[][] grid) {

        if (grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Board must have a positive amount of rows and/or columns");
        }

        int nrRows = grid.length;
        int nrCols = grid[0].length;

        int[][] buf = new int[nrRows][nrCols];

        for (int row = 0 ; row < nrRows ; row++) {

            for (int col = 0 ; col < nrCols ; col++) {
                buf[row][col] = getNewCellState(grid[row][col], getLiveNeighbours(row, col, grid));
            }
        }   
        return buf;
    }

    private int getLiveNeighbours(int cellRow, int cellCol, int[][] grid)
 {

        int liveNeighbours = 0;
        int rowEnd = Math.min(grid.length , cellRow + 2);
        int colEnd = Math.min(grid[0].length, cellCol + 2);

        for (int row = Math.max(0, cellRow - 1) ; row < rowEnd ; row++) {

            for (int col = Math.max(0, cellCol - 1) ; col < colEnd ; col++) {

                // make sure to exclude the cell itself from calculation
                if ((row != cellRow || col != cellCol) && grid[row][col] == LIVE) 
                  {
                    liveNeighbours++;
                }
            }
        }
        return liveNeighbours;
    }

    private int getNewCellState(int curState, int liveNeighbours) {

        int newState = curState;

        switch (curState) {
        case LIVE:

            if (liveNeighbours < 2) {
                newState = DEAD;
            }

            if (liveNeighbours == 2 || liveNeighbours == 3) {
                newState = LIVE;
            }


            if (liveNeighbours > 3) {
                newState = DEAD;
            }
            break;

        case DEAD:
            if (liveNeighbours == 3) {
                newState = LIVE;
            }
            break;

        default:
            throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
        }			
        return newState;
    }
}