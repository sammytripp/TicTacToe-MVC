public class PlayGame {

    private static TicTacToeModel game;
    private static TicTacToeController controller;

    public static void main(String[] args){
        // TODO randomize start player
        game = new TicTacToeModel('X');
        controller = new TicTacToeController(game);
    }
}
