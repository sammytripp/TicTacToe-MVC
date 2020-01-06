/**
 * Provides the main method to begin execution of TicTacToe game.
 *
 */
public class PlayGame {

    private static TicTacToeModel game;
    private static TicTacToeController controller;

    public static void main(String[] args){

        // Randomize starting player and initialize game board
        if (((int) Math.random() % 2) == 0) {
            game = new TicTacToeModel('X');
        } else {
            game = new TicTacToeModel('O');
        }

        // Initialize controller
        controller = new TicTacToeController(game);
    }
}
