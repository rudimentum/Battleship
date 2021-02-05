package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
    public static final Scanner scanner = new Scanner(System.in);
    public static String[] ship;
    public static ArrayList<String> makeCoordinatesList() {
        ArrayList<String> coordinates = new ArrayList<>();
        char start = 'A';

        for (int i = 0; i < 10; i++) {
            int index = 1;
            for (int j = 0; j < 10; j++) {
                String temp = String.format("%c%d", start, index++);
                coordinates.add(temp);
            }
            start++;
        }
        return coordinates;
    }

    public static void placeShips() {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        GameField.changeGameField(getCoordinates(5));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        GameField.changeGameField(getCoordinates(4));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        GameField.changeGameField(getCoordinates(3));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        GameField.changeGameField(getCoordinates(3));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        GameField.changeGameField(getCoordinates(2));
        GameField.printGameField();
    }

    /**
     *
     * @param sells of ship (5 - aircraft carrier, 4 - battleship, 3 - submarine, 3 - cruiser, 2 - destroyer)
     * @return int[4] with start and final coordinates of ship
     */
    public static int[] getCoordinates(int sells) {
        ArrayList<String> coordinates = makeCoordinatesList();
        while (true) {
            try {
                ship = scanner.nextLine().split("\\s+");
                break;
            } catch (NullPointerException e) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
        }

        String startCoordinate = ship[0];
        String finalCoordinate = ship[1];

        int startRow = startCoordinate.charAt(0) - 'A' + 1;
        int startColumn = Integer.parseInt(startCoordinate.substring(1));
        int finalRow = finalCoordinate.charAt(0) - 'A' + 1;
        int finalColumn = Integer.parseInt(finalCoordinate.substring(1));

        boolean isCorrectLocation = startRow == finalRow || startColumn == finalColumn;
        boolean isRightSize = Math.abs(finalRow - startRow) + 1 == sells || Math.abs(finalColumn - startColumn) + 1 == sells;
        System.out.println(isRightSize);
        boolean isExistCoordinate = coordinates.contains(startCoordinate) && coordinates.contains(finalCoordinate);

        if (isCorrectLocation && isRightSize && isExistCoordinate) {
            return finalRow > startRow ? new int[]{startRow, startColumn, finalRow, finalColumn} :
                    finalColumn > startColumn ? new int[]{startRow, startColumn, finalRow, finalColumn} :
                            finalRow < startRow ? new int[]{finalRow, startColumn, startRow, finalColumn} :
                                    new int[]{startRow, finalColumn, finalRow, startColumn};
        } else {
            return getCoordinates(sells);
        }
    }
}
