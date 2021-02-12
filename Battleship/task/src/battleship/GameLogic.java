package battleship;

import java.util.*;

public class GameLogic {
    final static Scanner scanner = new Scanner(System.in);

    private static ArrayList<String> makeCoordinatesList() {
        ArrayList<String> coordinates = new ArrayList<>();

        for (char start = 'A'; start <= 'J'; start++) {
            for (int i = 1; i < 11; i++) {
                coordinates.add(String.format("%c%d", start, i));
            }
        }
        return coordinates;
    }

    public static void play() {
        for (Ship ship : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getShipName(), ship.getSells());
            GameField.getGameField().placeShip(getCoordinates(ship.getShipName(), ship.getSells()));
            GameField.getGameField().printGameField();
        }
        System.out.println("The game starts!");
        System.out.println("Take a shot!");
        GameField.getGameField().shooting(setShot());
        GameField.getGameField().printGameField();
    }

    public static int[] setShot() {
        String shot = scanner.nextLine().toUpperCase().trim();

        if (!makeCoordinatesList().contains(shot)) {
            System.out.println("Error! You entered wrong coordinates! Try again:");
            setShot();
        }
        int row = shot.charAt(0) - 'A' + 1;
        int column = Integer.parseInt(shot.substring(1));
        return new int[]{row, column};
    }

    private static String[] setCoordinates() {
        String[] shipCoordinates;
        while (true) {
            try {
                shipCoordinates = scanner.nextLine().toUpperCase().split("\\s+");
                for (int i = 0; i < 2; i++) {
                    if (!makeCoordinatesList().contains(shipCoordinates[i])) {
                        System.out.println("Error! Wrong ship location! Try again:");
                        setCoordinates();
                    }
                }
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
        }
        return shipCoordinates;
    }

    /**
     * Implement a method for check entered coordinates
     * @param sells of ship (5 - aircraft carrier, 4 - battleship, 3 - submarine, 3 - cruiser, 2 - destroyer)
     * @return int[4] with start and final coordinates of ship
     */
    private static int[] getCoordinates(String shipName, int sells) {
        String[]shipCoordinates = setCoordinates();
        String startCoordinate = shipCoordinates[0];
        String finalCoordinate = shipCoordinates[1];

        int startRow = startCoordinate.charAt(0) - 'A' + 1;
        int startColumn = Integer.parseInt(startCoordinate.substring(1));
        int finalRow = finalCoordinate.charAt(0) - 'A' + 1;
        int finalColumn = Integer.parseInt(finalCoordinate.substring(1));

        int[] coordinates = fixDirection(startRow, startColumn, finalRow, finalColumn);

        boolean isCorrectLocation = startRow == finalRow || startColumn == finalColumn;
        boolean isRightSize = finalRow - startRow + 1 == sells || Math.abs(finalColumn - startColumn) + 1 == sells;
        boolean isNotTouchOthers = GameField.getGameField().checkSurroundings(coordinates);

        if (isCorrectLocation && isRightSize && isNotTouchOthers) {
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
            return getCoordinates(shipName, sells);
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