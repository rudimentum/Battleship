package battleship;

public class Main {

    public static void main(String[] args) {
        GameField gameField = GameField.getGameField();
        GameLogic game = new GameLogic();
        gameField.printField();
        game.play();
    }
}
