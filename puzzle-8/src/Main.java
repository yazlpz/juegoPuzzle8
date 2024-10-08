import puzzle.ExcepcionDePilaLlena;
import puzzle.ExcepcionDePilaVacia;
import puzzle.PilaEstatica;
import puzzle.Puzzle8;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        PilaEstatica<Integer> pilaEstatica = new PilaEstatica<>(1);
        SwingUtilities.invokeLater(() -> {
            Puzzle8 puzzle = null;
            try {
                puzzle = new Puzzle8();
            } catch (ExcepcionDePilaLlena e) {
                throw new RuntimeException(e);
            } catch (ExcepcionDePilaVacia e) {
                throw new RuntimeException(e);
            }
            puzzle.setVisible(true);
        });
    }
}