package io.coconut.jlite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import io.coconut.jlite.files.Files;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {
    private JTextArea txt = new JTextArea();

    private JMenuBar newMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] titles = {"File", "About"};
        String[][] elements = {{"New", "Open", "Save"}, {"Info", "Github"}};
        for(int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String[] elems = elements[i];
            menuBar.add(newMenu(title, elems));
        }
        return menuBar;
    }

    private JMenu newMenu(String title, String[] elements) {
        JMenu menu = new JMenu(title);
        for(String element : elements) {
            JMenuItem menuItem = new JMenuItem(element);
            menu.add(menuItem);
            menuItem.addActionListener(this);
        }
        return menu;
    }

    private Main() {
        setTitle("untitled - JLite");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(newMenuBar());
        JScrollPane scroller = new JScrollPane(txt);
        add(scroller);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Desktop desktop = java.awt.Desktop.getDesktop();
        String cmd = actionEvent.getActionCommand();
        if(cmd.equals("Save")) {
            Files.saveFile(txt, this);
        } else if(cmd.equals("Open")) {
            Files.openFile(txt, this);
        } else if(cmd.equals("New")) {
            String[] options = {"OK", "Cancel"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "All changes will be not saved",
                    "Attention",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == 0) {
                txt.setText("");
                setTitle("untitled - JLite");
            } else if (choice == 1) {

            }
        } else if(cmd.equals("Info")) {
            JOptionPane.showMessageDialog(this, "JLite Alpha 0.0.1 Simple Java IDE");
        } else if(cmd.equals("Github")) {
            try {
                URI oURL = new URI("https://github.com/PlusMarden17/jlite");
                desktop.browse(oURL);
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening browser: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

