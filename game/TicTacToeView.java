import java.awt.*;
import javax.swing.*;

public class TicTacToeView extends JFrame{

    // Model reference
    private TicTacToeModel model;

    // Layout manager
    private GridLayout gridLayout;

    // JPanels
    private JPanel buttonGrid;
    private JPanel statusMessage;

    // JMenuItems
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;

    // JLabel game message
    private JLabel message;

    /**
     * Constructor for TicTacToeView objects.
     *
     * @param model
     * @param nRows
     * @param nColumns
     */
    public TicTacToeView(TicTacToeModel model, int nRows, int nColumns) {
        this.model = model;
        this.gridLayout = new GridLayout(nRows, nColumns);
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
        statusMessage = new JPanel();
        message = new JLabel(model.getTurn() + ": Click where you would like to mark.");
        statusMessage.add(message);

        // Add GUI components to JFrame
        add(buttonGrid, BorderLayout.CENTER);
        add(statusMessage, BorderLayout.SOUTH);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 700);
        setVisible(true);
        setLocationRelativeTo(null);

        initializeMenuBar();
        initializeButtons();
    }

    /**
     * Initialize JFrame menu system.
     */
    private void initializeMenuBar() {
        JMenuBar menu = new JMenuBar(); // Create menu bar
        setJMenuBar(menu); // Attach menu bar to the frame

        JMenu fileMenu = new JMenu("File");

    }

    private void initializeButtons() {

    }

    public void update(TicTacToeMoveEvent event){

    }



}
