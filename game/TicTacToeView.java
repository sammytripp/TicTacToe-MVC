import java.awt.*;
import javax.swing.*;

public class TicTacToeView extends JFrame{

    // Model reference
    private TicTacToeModel model;

    // Layout manager
    private GridLayout gridLayout;

    // JPanels
    private JPanel buttonGrid;
    private JPanel messagePanel;

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
        JLabel message = new JLabel(model.getTurn() + ": Click where you would like to mark.");
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

    public void update(TicTacToeMoveEvent event){

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
