package tictactoe;
import java.util.Scanner;
enum gameState {
    NOT_FINISHED,
    DRAW,
    X_WINS,
    O_WINS,
    FINISHED
}
public class Main {
    static gameState currentState = gameState.DRAW;
    static String[] cells = {" ", " ", " ", " ", " ", " ", " ", " ", " ",};
    static String[] coOrds = new String[2];
    static String[][] grid = new String[3][3];
    static int playerTurn = 0;
    static String row1;
    static String row2;
    static String row3;
    static String column1;
    static String column2;
    static String column3;
    static String diagonal1;
    static String diagonal2;
    static String[] possibleLines = new String[8];

    static Scanner userInput = new Scanner(System.in);
    static int index = 0;

    static StringBuilder row1Build = new StringBuilder();
    static StringBuilder row2Build = new StringBuilder();
    static StringBuilder row3Build = new StringBuilder();
    static StringBuilder column1Build = new StringBuilder();
    static StringBuilder column2Build = new StringBuilder();
    static StringBuilder column3Build = new StringBuilder();
    static StringBuilder diagonalTopLeftBuild = new StringBuilder();
    static StringBuilder diagonalTopRightBuild = new StringBuilder();
    //Start Methods
    static void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("| \n");
        }
        System.out.println("---------");
    }
    static void getField() {

        // transfer array of cell data to multidimensional array grid
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                grid[row][column] = cells[index];
                index++;
            }
        }
        buildRowsColumnsDiagonals();
        //gather the coordinates of the desired location for the move

    }
    static void getCoordinates() {
        //create loop to collect input catching any errors within
        //repeat loop to gather correct input
        boolean isValidInput = false;

        do {
            System.out.print("Enter the coordinates: ");
            String coOrdinates = userInput.nextLine();
            if(coOrdinates.contains(" ") && coOrdinates.length() == 3){
                coOrds = coOrdinates.split(" ");

                for (String i : coOrds) {
                    if (i.matches("[1-3]+")) {
                        isValidInput = true;
                    } else {
                        System.out.println("Coordinates should be from 1 - 3");
                        isValidInput = false;
                        break;
                    }
                }
            } else {
                System.out.println("You should enter two numbers separated by one space");
                isValidInput = false;
            }
            if (isValidInput) {
                isValidInput = checkCoordinates();
            }

        } while (!isValidInput);

    }
    static boolean checkCoordinates() {
        int x = Integer.parseInt(coOrds[0]) - 1;
        int y = Integer.parseInt(coOrds[1]) - 1;

            if (grid[x][y].equals(" ") || grid[x][y].equals("_")) {
                switch (playerTurn) {
                    case 0:
                        grid[x][y] = "X";
                        playerTurn = 1;
                        return true;
                    case 1:
                        grid[x][y] = "O";
                        playerTurn = 0;
                        return true;
                }

            }
            System.out.println("That cell is taken already");
            return false;

    }
    static void checkGameState() {
        //Check cells first for impossible situation
        checkCells();
        checkWin();
    }
    static void buildRowsColumnsDiagonals() {
        row1Build.delete(0, 3);
        row2Build.delete(0, 3);
        row3Build.delete(0, 3);
        column1Build.delete(0, 3);
        column2Build.delete(0, 3);
        column3Build.delete(0, 3);
        diagonalTopLeftBuild.delete(0, 3);
        diagonalTopRightBuild.delete(0, 3);
        //build three rows as strings
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                //build rows as strings
                switch (row) {
                    case 0:
                        row1 = row1Build.append(grid[row][column]).toString();
                        possibleLines[row] = row1;
                        break;
                    case 1:
                        row2 = row2Build.append(grid[row][column]).toString();
                        possibleLines[row] = row2;
                        break;
                    case 2:
                        row3 = row3Build.append(grid[row][column]).toString();
                        possibleLines[row] = row3;
                        break;
                }
            }

        }
        //build three columns as strings
        for (int column = 0; column < 3; column++) {
            for (int row = 0; row < 3; row++) {

                //build rows as strings
                switch (column) {
                    case 0:
                        column1 = column1Build.append(grid[row][column]).toString();
                        possibleLines[column + 3] = column1;
                        break;
                    case 1:
                        column2 = column2Build.append(grid[row][column]).toString();
                        possibleLines[column + 3] = column2;
                        break;
                    case 2:
                        column3 = column3Build.append(grid[row][column]).toString();
                        possibleLines[column + 3] = column3;
                        break;
                }
            }

        }
        //build diagonals as strings
        diagonal1 = diagonalTopLeftBuild.append(grid[0][0]).append(grid[1][1]).append(grid[2][2]).toString();
        possibleLines[6] = diagonal1;
        diagonal2 = diagonalTopRightBuild.append(grid[0][2]).append(grid[1][1]).append(grid[2][0]).toString();
        possibleLines[7] = diagonal2;

    }
    static void checkWin() {

        for (int i = 0; i < 8; i++) {

            if (possibleLines[i].equals("XXX")) {
                currentState = gameState.X_WINS;
                return;
            } else if (possibleLines[i].equals("OOO")) {
                currentState = gameState.O_WINS;
                return;
            }

        }

    }
    static void checkCells() {
        int numX = 0;
        int numO = 0;
        int numEmpty = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (grid[row][column].equals("X")) {
                    numX++;
                } else if (grid[row][column].equals("O")) {
                    numO++;
                } else if (grid[row][column].equals(" ") || grid[row][column].equals("_") ) {
                    numEmpty++;
                }
            }

        }
        if (numEmpty > 0) {
            currentState = gameState.NOT_FINISHED;
        } else {
            currentState = gameState.FINISHED;
        }
    }
    public static void main(String[] args) {
        // write your code here
        getField();

        do {
            printBoard();
            getCoordinates();
            buildRowsColumnsDiagonals();
            checkGameState();

        } while (currentState == gameState.NOT_FINISHED);

        // System.out.println(currentState);
        printBoard();
        //evaluate winning condition / print to screen / exit
        switch (currentState) {
            case X_WINS:
                System.out.println("X wins");
                break;
            case O_WINS:
                System.out.println("O wins");
                break;
            default:
                System.out.println("Draw");
        }
        System.exit(0);

    }
}
