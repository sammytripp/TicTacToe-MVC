import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TicTacToeController extends DefaultHandler {

    private TicTacToeModel model;
    private TicTacToeView view;

    public TicTacToeController(TicTacToeModel model) {
        this.model = model;
        this.view = new TicTacToeView(model, model.getSize());
        setActionListeners();
    }

    private void setActionListeners() {

        // Buttons
        JButton[][] buttons = view.getButtons();
        for (int i = 0; i < model.getSize(); i++) {
            for (int j = 0; j < model.getSize(); j++) {
                // Add ActionListener to button
                int inner_i = i;
                int inner_j = j;
                buttons[i][j].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        try {
                            model.takeTurn(inner_i, inner_j);
                            model.notifyViews();
                        } catch (IllegalArgumentException e) {
                            view.showErrorMessage(e.getMessage());
                        }
                    }
                });
            }
        }

        // Save game
        JMenuItem saveMenuItem = view.getSaveMenuItem();
        saveMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                String fileName = view.getFileName();
                if ((fileName != null) && (fileName.length() > 1)) {
                    try {
                        FileOutputStream file = new FileOutputStream(fileName);
                        ObjectOutputStream out = new ObjectOutputStream(file);
                        out.writeObject(model);
                        out.close();
                        file.close();
                    } catch (IOException e) {
                        System.out.println("Save file - IOException caught");
                    }
                } else {
                    view.showErrorMessage("Please enter a valid file name.");
                }
            }

        });

        // Open game
        JMenuItem openMenuItem = view.getOpenMenuItem();
        openMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                // Get file name to open
                String fileName = view.openFile();
                //
                TicTacToeModel newModel = null;
                if ((fileName != null) && (fileName.length() > 1)) {
                    try {
                        FileInputStream file = new FileInputStream(fileName);
                        ObjectInputStream in = new ObjectInputStream(file);
                        newModel = (TicTacToeModel) in.readObject();
                        in.close();
                        file.close();
                    } catch (IOException e) {
                        System.out.println("Open file - IOException caught.");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Open file - ClassNotFoundException caught.");
                    }
                    model.removeView(view);
                    view.dispose();
                    new TicTacToeController(newModel);
                } else {
                    view.showErrorMessage("Unable to open game file. Please enter a valid file name.");
                }
            }
        });

    }
}
