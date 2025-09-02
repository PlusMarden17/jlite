package io.coconut.jlite;

import javax.swing.*;
import io.coconut.jlite.ui.UI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UI().setVisible(true);
        });
    }
}
