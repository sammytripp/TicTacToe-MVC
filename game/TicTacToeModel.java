
import java.io.Serializable;
import java.util.*;


public class TicTacToeModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private int size; // Dimensions of square grid (size x size)
    private int numToWin;        // Number of adjacent checkers to be winner
    private int nMarks;          // Number of plays made
    private char turn;          // 'X' or 'O'
    private char[][] grid;      // EMPTY, 'X' or 'O'

    private TicTacToeEnum gameState;

    // View objects subscribed to the Model
    private List<TicTacToeView> views;


    /**Default TicTacToe constructor of a 3x3 game grid
     *
     * @param initialTurn indicates whether 'X' or 'O' plays first
     *
     */
    public TicTacToeModel(char initialTurn){

        this(3, initialTurn);
    }

    /**Four-argument constructor of a nRows by nColumns game grid
     *
     * @param size
     * @param initialTurn
     * @throws IllegalArgumentException
     */
    public TicTacToeModel(int size, char initialTurn) throws IllegalArgumentException{

        if(size < 0)
            throw new IllegalArgumentException("Grid must be a positive size.");
        this.size = size;
        this.numToWin = size;
        this.grid = new char[size][size];
        this.views = new ArrayList<>();
        reset(initialTurn);
    }

    /**reset() resets the TicTacToe game
     *
     * @param initialTurn indicates whether 'X' or 'O' will play first during
     * the next game
     */
    public void reset(char initialTurn) {

        for(int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++){
                this.grid[i][j] = ' ';
            }
        }
        this.turn = initialTurn;
        this.nMarks = 0;
        this.gameState = TicTacToeEnum.IN_PROGRESS;
    }

    /**
     * Add a TicTacToeView object to the notification list.
     *
     * @param view
     */
    public void addView(TicTacToeView view){
        this.views.add(view);
    }

    /**
     * Remove a TicTacToeView object from the notification list.
     * @param view
     */
    public void removeView(TicTacToeView view){
        this.views.remove(view);
    }

    /**
     * Returns the notification list of TicTacToeView objects.
     *
     * @return list of subscribed TicTacToeView objects
     */
    public List<TicTacToeView> getViews() {
        return this.views;
    }

    /**
     * Notify TicTacToeView objects of any game state changes and update grid.
     *
     */
    public void notifyViews(){
        for (TicTacToeView view: this.views){
            view.update(getGameState());
        }
    }

    /**getTurn() returns the current player
     *
     * @return 'X' or 'O'
     */
    public char getTurn(){
        return this.turn;
    }

    /**
     * Returns the TicTacToe ENUM to indicate if there is a
     * winner, draw, or if the game is still in progress
     *
     * @return TicTacToe ENUM Value
     */
    public TicTacToeEnum getGameState(){
        return this.gameState;
    }

    /**
     * Returns the dimension size of the game grid.
     *
     * @return
     */
    public int getSize() { return this.size; }


    /**charToEnum facilitates the return of the corresponding ENUM to inform
     * the findWinner() method which updates the game state
     *
     * @param player 'X' or player 'O'
     * @throws IllegalArgumentException
     * @return TicTacToe ENUM
     */
    private TicTacToeEnum charToEnum(char player) throws IllegalArgumentException {
        if(player == 'X') return TicTacToeEnum.X_WON;
        if(player == 'O') return TicTacToeEnum.O_WON;
        throw new IllegalArgumentException("charToEnum("+player+"): player must be either X or O");
    }

    /**takeTurn() places the players 'X' or 'O' at the desired location on the
     * game grid, updates the current turn to point to the next player,
     * and updates the current game status after this move
     *
     * @param row entered by player
     * @param column entered by player
     * @throws IllegalArgumentException
     * @return resulting game status
     */
    public TicTacToeEnum takeTurn(int row, int column) throws IllegalArgumentException {
        if(this.gameState != TicTacToeEnum.IN_PROGRESS)
            throw new IllegalArgumentException("Game is over.");
        if(row < 0 || row > this.size || column < 0 || column > this.size)
            throw new IllegalArgumentException("Grid is " + this.size + " by " + this.size);
        if(this.grid[row][column] != ' ')
            throw new IllegalArgumentException("Location is already full.");

        // Legal location - take turn
        this.grid[row][column] = getTurn();
        // Increment moves taken
        this.nMarks++;
        // Switch player
        if (getTurn() == 'X'){
            this.turn = 'O';
        } else if (getTurn() == 'O') {
            this.turn = 'X';
        }
        // Determine resulting game state
        this.gameState = findWinner();
        return this.gameState;
    }

    /** findWinner() iterates through the game grid first by rows,
     * then by columns, then diagonally, to determine if there is
     * an incidence of a full consecutive row, column, or diagonal
     * line of 'X' or 'O' characters.
     *
     * @return charToEnum value
     */
    private TicTacToeEnum findWinner(){
        TicTacToeEnum newGameState;

        // else, search for a winner
        for (int i = 0; i < this.size; i++){
            for (int j = 0; j < this.size; j++){
                if (grid[i][j] != ' '){
                    newGameState = findWinnerFrom(i,j);
                    if (newGameState != TicTacToeEnum.IN_PROGRESS)
                        return newGameState;
                }
            }
        }
        // If the game board is full, return DRAW
        if (this.nMarks == (this.size * this.size)) return TicTacToeEnum.DRAW;

        // Game is still in progress
        return TicTacToeEnum.IN_PROGRESS;
    }


    /** Internal method to determine if there is a game winner
     * by first searching horizontally, then vertically, then diagonally,
     * from the indicated starting position on the game grid.
     *
     * @param    row
     * @param    column
     * @return   IN_PROGRESS, X_WON, 0_WON, DRAW
     */
    private TicTacToeEnum findWinnerFrom(int row, int column) {
        int count;

        // Horizontal check - look left
        count = 1;
        for (int c = column - 1; c > 0; c--) {
            if (this.grid[row][column] == this.grid[row][c]) {
                count++;
                if (count == this.numToWin) {
                    return charToEnum(grid[row][column]);
                }
            }
        }

        // Horizontal check - look right
        count = 1;
        for (int c = column + 1; c < this.size; c++) {
            if (this.grid[row][column] == this.grid[row][c]) {
                count++;
                if (count == this.numToWin) {
                    return charToEnum(grid[row][column]);
                }
            }
        }

        // Vertical check - look up
        count = 1;
        for (int r = row - 1; r > 0; r--) {
            if (this.grid[r][column] == this.grid[row][column]) {
                count++;
                if (count == this.numToWin) {
                    return charToEnum(grid[row][column]);
                }
            }
        }

        // Vertical check - look down
        count = 1;
        for (int r = row + 1; r < this.size; r++) {
            if (this.grid[row][column] == this.grid[r][column]) {
                count++;
                if (count == this.numToWin) {
                    return charToEnum(grid[row][column]);
                }
            }
        }

        // Diagonal check - upper left corner to lower right corner
        count = 0;
        for (int r = 0, c = 0; r < this.size; r++){
            if (this.grid[row][column] == this.grid[r][c]) {
                count++;
                if (count == this.numToWin) {
                    return charToEnum(grid[row][column]);
                }
            }
            c++;
        }

        // Diagonal check - upper right corner to lower left corner
        count = 0;
        for (int r = 0, c = this.size - 1; r < this.size; r++){
            if (this.grid[row][column] == this.grid[r][c]) {
                count++;
                if (count == this.numToWin) {
                    return charToEnum(grid[row][column]);
                }
            }
            c--;
        }

        // No winner detected - the game is still in progress
        return TicTacToeEnum.IN_PROGRESS;
    }


    /** toString formats the 2-dimensional TicTacToe grid array as a string
     *
     * @return TicTacToe board in string formatting
     */
    @Override
    public String toString(){
        String s = "";
        for (int i = 0; i < this.size; i++){
            for (int j = 0; j < this.size; j++){
                s += grid[i][j];
            }
        }
        return s;
    }


    /** TicTacToe simulation example
     *
     * @param args
     */
    public static void main(String args[]) {
        TicTacToeModel game = new TicTacToeModel('X');
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println(game.toString());
            System.out.println(game.getTurn() +
                    ": Where do you want to mark? Enter row column (0, 1, or 2)");
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            scanner.nextLine();
            game.takeTurn(row, column);
        } while (game.getGameState() == TicTacToeEnum.IN_PROGRESS);
        System.out.println( game.getGameState());
    }

}
