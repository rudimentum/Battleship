package battleship;

public class Main {

    public static void main(String[] args) {
        GameField gameField = GameField.getGameField();
        gameField.printGameField();
        GameLogic.placeShips();
    }
}
