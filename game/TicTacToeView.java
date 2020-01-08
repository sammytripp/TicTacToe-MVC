import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TicTacToeView extends JFrame{

    // Model reference
    private TicTacToeModel model;

    // Layout manager
    private GridLayout gridLayout;

    // JPanels
    private JPanel buttonGrid;
    private JPanel messagePanel;

    // JLabel
    private JLabel message;

    // JMenuItems
    private JMenuItem saveMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem restartMenuItem;
    private JMenuItem quitMenuItem;
    private JMenuItem gridSizeMenuItem;

    // JButtons
    private final int BUTTON_SIZE = 100;
    private JButton[][] buttons;

    /**
     * Constructor for TicTacToeView objects.
     *
     * @param model
     * @param size of TicTacToeModel grid
     */
    public TicTacToeView(TicTacToeModel model, int size) {
        this.model = model;
        this.buttons = new JButton[size][size];
        gridLayout = new GridLayout(size, size);
        initializeGUI();
    }

    /**
     * Initializes the JFrame of the GUI
     */
    private void initializeGUI() {

        model.addView(this); // Subscribe to model
        setTitle("Tic Tac Toe");

        // Initialize button grid layout for Tic Tac Toe game grid
        buttonGrid = new JPanel();
        buttonGrid.setLayout(gridLayout);

        //Initialize output message panel
        messagePanel = new JPanel();
        message = new JLabel(model.getTurn() + ": Click where you would like to mark.");
        messagePanel.add(message);

        // Add GUI components to JFrame
        add(buttonGrid, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        initializeMenuBar();
        initializeButtons();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(model.getSize() * BUTTON_SIZE, (model.getSize() * BUTTON_SIZE) + BUTTON_SIZE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Initialize JFrame menu system.
     */
    private void initializeMenuBar() {
        JMenuBar menu = new JMenuBar(); // Create menu bar
        setJMenuBar(menu); // Attach menu bar to the frame

        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);
        this.saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);
        this.openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);
        this.restartMenuItem = new JMenuItem("Restart Game");
        fileMenu.add(restartMenuItem);
        this.quitMenuItem = new JMenuItem("Quit");
        fileMenu.add(quitMenuItem);


        JMenu editMenu = new JMenu("Edit");
        menu.add(editMenu);
        this.gridSizeMenuItem = new JMenuItem("Change Grid Size");
        editMenu.add(gridSizeMenuItem);
    }

    private void initializeButtons() {
        // Create each JButton in the array
        for (int i = 0; i < this.model.getSize(); i++){
            for (int j = 0; j < this.model.getSize(); j++){
                JButton button = new JButton();
                buttons[i][j] = button;
                button.setSize(BUTTON_SIZE, BUTTON_SIZE);
                button.setIcon(null);
                buttonGrid.add(button);
            }
        }
        updateButtons();
    }

    public void update(TicTacToeEnum gameState) {
        updateButtons();
        if(gameState.equals(TicTacToeEnum.O_WON)) {
            gameWinner("O");
        } else if(gameState.equals(TicTacToeEnum.X_WON)) {
            gameWinner("X");
        } else if (gameState.equals(TicTacToeEnum.DRAW)) {
            gameDraw();
        } else { // Game in progress
            updateMessagePanel();
        }
    }

    public void updateButtons() {
        // Get string representation of game grid
        String  grid = model.toString();
        // Debugging
        System.out.println(grid);

        // Update any buttons with the corresponding 'X' or 'O' marker
        int offset = 0;
        for (int i = 0; i < this.model.getSize(); i++) {
            for (int j = 0; j < this.model.getSize(); j++) {
                offset = j + (i * model.getSize());
                // Select character
                String character = String.valueOf(grid.charAt(offset));

                // Set character text at JButton
                switch (character) {
                    case "X":
                        buttons[i][j].setText("X");
                        break;
                    case "O":
                        buttons[i][j].setText("O");
                        break;
                    case " ":
                        buttons[i][j].setText(" ");
                }
            }
        }
    }

    /**
     * Updates message panel with instructions for the current player.
     *
     */
    public void updateMessagePanel() {
        message.setText(model.getTurn() + ": Click where you would like to mark.");
    }

    /**
     * Returns the 2D array of JButton objects.
     *
     * @return buttons
     */
    public JButton[][] getButtons(){
        return this.buttons;
    }

    /**
     * Prompt the user to enter a file name.
     *
     * @return fileName
     */
    public String getFileName() {
        String fileName = JOptionPane.showInputDialog("Save file as:");
        return fileName + ".txt";
    }

    /**
     * GUI to select file to open.
     *
     * @return fileName
     */
    public String openFile() {
        JFileChooser fileChooser = new JFileChooser();
        // Only allow .txt files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        // Start in home directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            return fileChooser.getSelectedFile().getName();
        } else {
            // no file selected successfully
            return null;
        }
    }

    /**
     * Prompts user to enter an integer indicating the desired square grid dimensions.
     *
     * @return New grid dimension size
     */
    public int changeGridSize() {
        String size = JOptionPane.showInputDialog("Enter new grid size (integer): ");
        return Integer.parseInt(size);
    }

    /**
     * Displays winner and prompts user to start a new game.
     *
     * @param winner
     */
    public void gameWinner(String winner){
        int result = JOptionPane.showConfirmDialog(this ,
                winner + " is the winner! Would you like to play again?",
                winner + "won", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Reset game
            restartGame();
        } else {
            // close window
            closeWindow();
        }

    }

    /**
     * Notifies the user of a draw and prompts user to start a new game.
     *
     */
    public void gameDraw(){
        int result = JOptionPane.showConfirmDialog(this,
                "Draw! Would you like to play again?",
                "Draw", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // Reset game
            restartGame();
        } else {
            // close window
            closeWindow();

        }
    }

    /**
     * Restarts the TicTacToe game by resetting the model and updating the buttons.
     *
     */
    public void restartGame() {
        // Randomize initial turn
        if (((int) Math.random() % 2) == 0) {
            model.reset('X');
        } else {
            model.reset('O');
        }
        updateButtons();
    }

    /**
     * Creates a WindowEvent to close the frame and end program execution.
     *
     */
    public void closeWindow() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }



    /**
     * Displays an error message to the user (i.e. illegal move, invalid file name, etc).
     *
     * @param errorMessage
     */
    public void showErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    /**
     * Returns "Save" JMenuItem.
     *
     * @return saveMenuItem
     */
    public JMenuItem getSaveMenuItem() { return this.saveMenuItem; }

    /**
     * Returns "Open" JMenuItem.
     *
     * @return openMenuItem
     */
    public JMenuItem getOpenMenuItem() { return this.openMenuItem; }

    /**
     * Returns "Restart" JMenuItem.
     *
     * @return restartMenuItem
     */
    public JMenuItem getRestartMenuItem() { return this.restartMenuItem; }

    /**
     * Returns "Quit" JMenuItem.
     *
     * @return quitMenuItem
     */
    public JMenuItem getQuitMenuItem() { return this.quitMenuItem; }

    /**
     * Returns "Change Grid Size" JMenuItem.
     *
     * @return gridSizeMenuItem
     */
    public JMenuItem getGridSizeMenuItem() { return this.gridSizeMenuItem; }

}
