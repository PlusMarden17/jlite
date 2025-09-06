package io.coconut.jlite.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import io.coconut.jlite.files.Files;
import io.coconut.jlite.files.Build;
import java.io.IOException;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

public class UI extends JFrame implements ActionListener {
    private RSyntaxTextArea txt = new RSyntaxTextArea();
    private boolean isSaved = false;

    public UI() {
        initializeUI();
    }

    private void initializeUI() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        setTitle("untitled - JLite");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        txt.setSyntaxEditingStyle(RSyntaxTextArea.SYNTAX_STYLE_JAVA);
        JScrollPane scroller = new JScrollPane(txt);
        add(scroller);
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/monokai.xml"));
            theme.apply(txt);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] titles = {"File", "Build", "About"};
        String[][] elements = {{"New", "Open", "Save"}, {"Run", "Compile"}, {"Info", "Github"}};

        for(int i = 0; i < titles.length; i++) {
            String title = titles[i];
            String[] elems = elements[i];
            menuBar.add(createMenu(title, elems));
        }
        return menuBar;
    }

    private JMenu createMenu(String title, String[] elements) {
        JMenu menu = new JMenu(title);
        for(String element : elements) {
            JMenuItem menuItem = new JMenuItem(element);
            menu.add(menuItem);
            menuItem.addActionListener(this);
        }
        return menu;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Desktop desktop = Desktop.getDesktop();
        String cmd = actionEvent.getActionCommand();

        switch(cmd) {
            case "Save":
                Files.saveFile(txt, this);
                isSaved = true;
                break;
            case "Open":
                Files.openFile(txt, this);
                break;

            case "New":
                handleNewFile();
                break;

            case "Run":
                if (isSaved) {
                    Build.runCode();
                } else {
                    JOptionPane.showMessageDialog(this, "Save file before running");
                }
                break;
            case "Info":
                JOptionPane.showMessageDialog(this, "JLite Alpha 0.0.1 Simple Java IDE");
                break;

            case "Github":
                openGithubPage(desktop);
                break;
        }
    }

    private void handleNewFile() {
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
        }
    }

    private void openGithubPage(Desktop desktop) {
        try {
            URI oURL = new URI("https://github.com/PlusMarden17/jlite");
            desktop.browse(oURL);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error opening browser: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
