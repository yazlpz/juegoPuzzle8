import puzzle.PilaEstatica;
import puzzle.Puzzle8;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        PilaEstatica<Integer> pilaEstatica = new PilaEstatica<>(1);
        SwingUtilities.invokeLater(() -> {
            Puzzle8 puzzle = new Puzzle8();
            puzzle.setVisible(true);
        });
    }
}