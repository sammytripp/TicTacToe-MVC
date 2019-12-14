import java.util.EventObject;

public class TicTacToeMoveEvent extends EventObject {

    // Status of game completion
    private boolean gameComplete;

    // Success or failure of player move
    private boolean moveSuccess;

    /**
     * Constructor for TicTacToeMoveEvent objects for notifying the TicTacToeView class.
     * @param source
     * @param gameComplete
     */
    public TicTacToeMoveEvent(TicTacToeModel source, boolean moveSuccess, boolean gameComplete){
        super(source);
        this.moveSuccess = moveSuccess;
        this.gameComplete = gameComplete;
    }

    /**
     * Returns the boolean value of whether or not a move was successfully made.
     * @return moveSuccess
     */
    public boolean isMoveSuccess(){
        return this.moveSuccess;
    }


    /**
     * Returns the boolean value of whether or not the game is complete.
     *
     * @return gameComplete
     */
    public boolean gameStatus(){
        return this.gameComplete;
    }
}
