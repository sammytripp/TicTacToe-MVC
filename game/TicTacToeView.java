import java.awt.*;
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
     * Displays winner and prompts user to start a new game.
     *
     * @param winner
     */
    public void gameWinner(String winner){
        JOptionPane.showInternalConfirmDialog(this,
                winner + " is the winner! Would you like to play again?",
                winner + "won", JOptionPane.YES_NO_OPTION);

    }

    public void gameDraw(){

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
