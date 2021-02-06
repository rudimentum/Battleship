package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class GameLogic {
    final static Scanner scanner = new Scanner(System.in);
    private static String[] shipCoordinates;
    private final static ArrayList<String> allCoordinates = makeCoordinatesList();

    private static ArrayList<String> makeCoordinatesList() {
        ArrayList<String> coordinates = new ArrayList<>();

        for (char start = 'A'; start <= 'J'; start++) {
            for (int i = 1; i < 11; i++) {
                coordinates.add(String.format("%c%d", start, i));
            }
        }
        return coordinates;
    }

    public static void arrangeShips() {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        GameField.placeShip(getCoordinates(5, "Aircraft Carrier"));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        GameField.placeShip(getCoordinates(4, "Battleship"));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        GameField.placeShip(getCoordinates(3, "Submarine"));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        GameField.placeShip(getCoordinates(3, "Cruiser"));
        GameField.printGameField();
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        GameField.placeShip(getCoordinates(2, "Destroyer"));
        GameField.printGameField();
    }

    private static String[] setCoordinates() {
        while (true) {
            try {
                shipCoordinates = scanner.nextLine().split("\\s+");
                break;
            } catch (NullPointerException e) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
        }
        return shipCoordinates;
    }

    /**
     * Implement a method for check entered coordinates
     * @param sells of ship (5 - aircraft carrier, 4 - battleship, 3 - submarine, 3 - cruiser, 2 - destroyer)
     * @param shipName
     * @return int[4] with start and final coordinates of ship
     */
    private static int[] getCoordinates(int sells, String shipName) {
        shipCoordinates = setCoordinates();
        String startCoordinate = shipCoordinates[0];
        String finalCoordinate = shipCoordinates[1];

        int startRow = startCoordinate.charAt(0) - 'A' + 1;
        int startColumn = Integer.parseInt(startCoordinate.substring(1));
        int finalRow = finalCoordinate.charAt(0) - 'A' + 1;
        int finalColumn = Integer.parseInt(finalCoordinate.substring(1));

        int[] coordinates = fixDirection(startRow, startColumn, finalRow, finalColumn);

        boolean isCorrectLocation = startRow == finalRow || startColumn == finalColumn;
        boolean isRightSize = Math.abs(finalRow - startRow) + 1 == sells || Math.abs(finalColumn - startColumn) + 1 == sells;
        boolean isExist = allCoordinates.contains(startCoordinate) && allCoordinates.contains(finalCoordinate);
        boolean isNotTouchOthers = GameField.checkSurroundings(coordinates);

        if (isCorrectLocation && isRightSize && isExist && isNotTouchOthers) {
            return coordinates;
        } else {
            if (!isCorrectLocation) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
            if (!isRightSize) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n", shipName);
            }
            if (!isNotTouchOthers) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
            return getCoordinates(sells, shipName);
        }
    }

    /**
     * @return coordinates in the right direction
     * Example: E6 D6 -> D6 E6; F7 F3 -> F3 F7
     */
    private static int[] fixDirection(int startRow, int startColumn, int finalRow, int finalColumn) {
        return finalRow > startRow ? new int[]{startRow, startColumn, finalRow, finalColumn} :
                finalColumn > startColumn ? new int[]{startRow, startColumn, finalRow, finalColumn} :
                finalRow < startRow ? new int[]{finalRow, startColumn, startRow, finalColumn} :
                new int[]{startRow, finalColumn, finalRow, startColumn};
    }
}
