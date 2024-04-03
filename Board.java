import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Board {
    private int rows;
    private int cols;
    private int numMines;
    private int cellsRemain;
    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int[][] surroundingMines;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = 0;
        cellsRemain = rows * cols;
        mines = new boolean[rows][cols];
        revealed = new boolean[rows][cols];
        flagged = new boolean[rows][cols];
        surroundingMines = new int[rows][cols];

        loadBoard();
        updateSurroundingMines();
    }

    private void loadBoard() {
        File f = new File("./map.txt");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(f));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                Scanner sc = new Scanner(line);
                int x = sc.nextInt() - 1;
                int y = sc.nextLine().strip().charAt(0) - 'A';
                this.placeMine(x, y);
                sc.close();
                numMines++;
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void placeMine(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            mines[row][col] = true;

            updateSurroundingMines();
        }
    }

    private void updateSurroundingMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                surroundingMines[i][j] = countSurroundingMines(i, j);
            }
        }
    }

    private int countSurroundingMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < rows && c >= 0 && c < cols && mines[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void revealCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols && !revealed[row][col]) {
            revealed[row][col] = true;
            cellsRemain--;

            if (surroundingMines[row][col] == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int r = row + i;
                        int c = col + j;
                        if (r >= 0 && r < rows && c >= 0 && c < cols) {
                            revealCell(r, c);
                        }
                    }
                }
            }
        }
    }

    public void toggleFlag(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            flagged[row][col] = !flagged[row][col];
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mines[i][j] && revealed[i][j]) {
                    System.out.println("\nLose!");
                    return true;
                }
            }
        }
        if (cellsRemain == numMines) {
            System.out.println("\nWin!");
            return true;
        }
        return false;
    }

    public void printBoard() {
        System.out.print("   ");
        for (int i = 0; i < cols; i++) {
            char c = 'A';
            System.out.printf("%c ", c + i);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < cols; j++) {
                if (!revealed[i][j]) {
                    System.out.print("* ");
                } else {
                    System.out.print(surroundingMines[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Board b = new Board(8, 8);
        b.printBoard();

        while (!b.isGameOver()) {
            System.out.println("Enter x y to reveal a cell. E.g., 3 A");
            System.out.print("Your move: ");

            int x = sc.nextInt() - 1;
            int y = sc.nextLine().strip().charAt(0) - 'A';

            if (x < 0 || y < 0 || x >= 8 || y >= 8) {
                System.out.println("Invalid move!");
                continue;
            }

            b.revealCell(x, y);
            b.printBoard();
        }
        sc.close();
    }
}
