package battleship;

import static battleship.CellState.*;

/**
 * Singleton Lazy Initialization
 */
public class GameField {
    private static GameField gameField;

    private final String[][] emptyField = makeField();
    private String[][] field = makeField();

    private GameField() {}

    public static synchronized GameField getGameField() {
        if (gameField == null) {
            gameField = new GameField();
        }
        return gameField;
    }

    private String[][] makeField() {
        field = new String[11][11];
        char start = 'A';
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i == 0 && j == 0) {
                    field[i][j] = " ";
                }
                else if (i == 0) {
                    field[i][j] = String.valueOf(j);
                } else if (j == 0){
                    field[i][j] = String.valueOf(start++);
                } else {
                    field[i][j] = EMPTY.getState();
                }
            }
        }
        return field;
    }

    public void placeShip(int[] coordinates) {
        int startRow = coordinates[0];
        int startColumn = coordinates[1];
        int finalRow = coordinates[2];
        int finalColumn = coordinates[3];

        boolean isHorizontal = startRow == finalRow;
        if (isHorizontal) {
            for (; startColumn <= finalColumn; startColumn++) {
                field[startRow][startColumn] = FILL.getState();
            }
        } else {
            for (; startRow <= finalRow; startRow++) {
                field[startRow][startColumn] = FILL.getState();
            }
        }

        printField();
    }

    public boolean checkSurroundings(int... coordinates) {
        boolean isFree = true;

        int startRow = coordinates[0] == 1 ? 1 : coordinates[0] - 1;
        int startColumn = coordinates[1] == 1? 1 : coordinates[1] - 1;
        int finalRow = coordinates[2] == 10 ? 10 : coordinates[2] + 1;
        int finalColumn = coordinates[3] == 10 ? 10 : coordinates[3] + 1;

        boolean isHorizontal = startRow == finalRow;
        if (isHorizontal) {
            for (; startColumn <= finalColumn; startColumn++) {
                if (field[startRow][startColumn].equals(FILL.getState())) {
                    isFree = false;
                    break;
                }
            }
        } else {
            for (; startRow <= finalRow; startRow++) {
                if (field[startRow][startColumn].equals(FILL.getState())) {
                    isFree = false;
                    break;
                }
            }
        }
        return isFree;
    }

    public void shooting(int[] shot) {
        int row = shot[0];
        int column = shot[1];

        if (field[row][column].equals(FILL.getState())) {
            System.out.println("You hit a ship!");
            emptyField[row][column] = HIT.getState();
            if (checkSurroundings(row, column, row, column)) {
                System.out.println("You sank a ship! Specify a new target:");
            }
            field[row][column] = HIT.getState();
        } else if (field[row][column].equals(EMPTY.getState())) {
            System.out.println("You missed!");
            emptyField[row][column] = MISS.getState();
        }
        printEmptyField();
    }

    public boolean isEndOfGame() {
        boolean isEnd = true;
        for (String[] x : field) {
            for (String y : x) {
                if (y.equals(FILL.getState())) {
                    isEnd = false;
                    break;
                }
            }
        }
        return isEnd;
    }

    public void printField() {
        for (String[] x : field) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public void printEmptyField() {
        for (String[] x : emptyField) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
