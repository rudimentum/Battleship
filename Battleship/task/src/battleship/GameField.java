package battleship;

/**
 * Singleton Lazy Initialization
 */
public class GameField {
    private static GameField gameField;
    final String EMPTY = "~";
    final String PLACE = "O";
    final String MISS = "M";
    final String HIT = "X";

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
                    field[i][j] = EMPTY;
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
                field[startRow][startColumn] = PLACE;
            }
        } else {
            for (; startRow <= finalRow; startRow++) {
                field[startRow][startColumn] = PLACE;
            }
        }
    }

    public boolean checkSurroundings(int[] coordinates) {
        boolean isFree = true;

        int startRow = coordinates[0] == 1 ? 1 : coordinates[0] - 1;
        int startColumn = coordinates[1] == 1? 1 : coordinates[1] - 1;
        int finalRow = coordinates[2] == 10 ? 10 : coordinates[2] + 1;
        int finalColumn = coordinates[3] == 10 ? 10 : coordinates[3] + 1;

        boolean isHorizontal = startRow == finalRow;
        if (isHorizontal) {
            for (; startColumn <= finalColumn; startColumn++) {
                if (field[startRow][startColumn].equals(PLACE)) {
                    isFree = false;
                    break;
                }
            }
        } else {
            for (; startRow <= finalRow; startRow++) {
                if (field[startRow][startColumn].equals(PLACE)) {
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
        switch (field[row][column]) {
            case PLACE:
                System.out.println("You hit a ship!");
                field[row][column] = HIT;
                break;
            case EMPTY:
                System.out.println("You missed!");
                field[row][column] = MISS;
                break;
        }
    }

    public void printGameField() {
        for (String[] x : field) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
