package battleship;

/**
 * Singleton Lazy Initialization
 */
public class GameField {
    private static GameField gameField;
    private static String[][] field = makeField();

    private GameField() {}

    public static synchronized GameField getGameField() {
        if (gameField == null) {
            gameField = new GameField();
        }
        return gameField;
    }

    private static String[][] makeField() {
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
                    field[i][j] = "~";
                }
            }
        }
        return field;
    }

    public static void placeShip(int[] coordinates) {
        int startRow = coordinates[0];
        int startColumn = coordinates[1];
        int finalRow = coordinates[2];
        int finalColumn = coordinates[3];

        boolean isHorizontal = startRow == finalRow;
        if (isHorizontal) {
            for (; startColumn <= finalColumn; startColumn++) {
                field[startRow][startColumn] = "O";
            }
        } else {
            for (; startRow <= finalRow; startRow++) {
                field[startRow][startColumn] = "O";
            }
        }
    }

    public static boolean checkSurroundings(int[] coordinates) {
        boolean isFree = true;

        int startRow = coordinates[0] == 1 ? 1 : coordinates[0] - 1;
        int startColumn = coordinates[1] == 1? 1 : coordinates[1] - 1;
        int finalRow = coordinates[2] == 10 ? 10 : coordinates[2] + 1;
        int finalColumn = coordinates[3] == 10 ? 10 : coordinates[3] + 1;

        boolean isHorizontal = startRow == finalRow;
        if (isHorizontal) {
            for (; startColumn <= finalColumn; startColumn++) {
                if (field[startRow][startColumn].equals("O")) {
                    isFree = false;
                    break;
                }
            }
        } else {
            for (; startRow <= finalRow; startRow++) {
                if (field[startRow][startColumn].equals("O")) {
                    isFree = false;
                    break;
                }
            }
        }
        return isFree;
    }

    public static void printGameField() {
        for (String[] x : field) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
