package io.coconut.jlite.files;

import javax.swing.*;
import java.util.Scanner;
import java.io.*;

public class Files {
    public static void saveFile(JTextArea txt, JFrame parentFrame) {
        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(parentFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter buf = new BufferedWriter(new FileWriter(chooser.getSelectedFile()))) {
                buf.write(txt.getText());
                parentFrame.setTitle(chooser.getSelectedFile().getName() + " - JLite");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void openFile(JTextArea txt, JFrame parentFrame) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new Filter());
        int option = chooser.showOpenDialog(parentFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (Scanner scanner = new Scanner(chooser.getSelectedFile())) {
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                txt.setText(content.toString());
                parentFrame.setTitle(chooser.getSelectedFile().getName() + " - JLite");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    static class Filter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File file) {
            return file.isDirectory() || file.getName().endsWith(".java");
        }

        @Override
        public String getDescription() {
            return "Java files (*.java)";
        }
    }
}
