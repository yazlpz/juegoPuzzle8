package puzzle;
import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Puzzle8 extends JFrame {
    private Integer[] currentState = {(Integer) 1, (Integer) 2, (Integer) 3, (Integer) 4, (Integer) 5, (Integer) 6, (Integer) 7, (Integer) 8, null};
    private JButton[] buttons = new JButton[9];

    Stack<Integer> s = new Stack<>();

    private int emptyIndex = 8;
    private Stack<Integer[]> undoStack = new Stack<>();
    private Stack<Integer[]> redoStack = new Stack<>();

    public Puzzle8() {
        setTitle("Puzzle 8");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].setFocusPainted(false);
            int index = i; // Variable final para el listener
            buttons[i].addActionListener(e -> showMessage("Boton " + index + " clickeado"));
            add(buttons[i]);
        }

        JButton undoButton = new JButton("Deshacer");
        undoButton.addActionListener(e -> showMessage("Deshacer"));
        add(undoButton);

        JButton redoButton = new JButton("Rehacer");
        redoButton.addActionListener(e -> showMessage("Rehacer"));
        add(redoButton);

        updateBoard();
    }

    private int[][] updateBoard() {
        for (int i = 0; i < 9; i++) {
            s.push(Integer.valueOf(10));
            buttons[i].setText(currentState[i] != null ? currentState[i].toString() : "");
        }
        return null;
    }
    //recorrer arreglo y meter a la pila el elemento<-----

    //busca el espacio en blanco en el tablero
    private int buscarVacio() {
        for (int i = 0; i < currentState.length; i++) {
            if (currentState[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(currentState.clone());
            currentState = undoStack.pop();
            emptyIndex = buscarVacio();
            updateBoard();
        }
    }

    private void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(currentState.clone());
            currentState = redoStack.pop();
            emptyIndex = buscarVacio();
            updateBoard();
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


}